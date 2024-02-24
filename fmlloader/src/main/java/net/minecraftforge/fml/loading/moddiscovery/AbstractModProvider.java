/*
 * Copyright (c) Forge Development LLC and contributors
 * SPDX-License-Identifier: LGPL-2.1-only
 */

package net.minecraftforge.fml.loading.moddiscovery;

import com.mojang.logging.LogUtils;
import cpw.mods.jarhandling.JarMetadata;
import cpw.mods.jarhandling.SecureJar;
import net.minecraftforge.fml.loading.LogMarkers;
import net.minecraftforge.forgespi.language.IConfigurable;
import net.minecraftforge.forgespi.language.IModFileInfo;
import net.minecraftforge.forgespi.language.IModInfo;
import net.minecraftforge.forgespi.locating.IModFile;
import net.minecraftforge.forgespi.locating.IModLocator;
import net.minecraftforge.forgespi.locating.IModProvider;
import net.minecraftforge.forgespi.locating.ModFileLoadingException;
import org.slf4j.Logger;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.jar.Manifest;

//Ketting start
import java.util.HashMap;
import java.nio.file.Files;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;
//Ketting end

public abstract class AbstractModProvider implements IModProvider
{
    private static final   Logger LOGGER    = LogUtils.getLogger();
    protected static final String MODS_TOML = "META-INF/mods.toml";
    protected static final String MANIFEST = "META-INF/MANIFEST.MF";

    //Ketting start
    private static boolean isExcluded(Path path, ConcurrentHashMap<String, Boolean> hm) {
        String path_str = path.toString().replace('/', '.');
        if (path_str.endsWith(".")) path_str = path_str.substring(0, path_str.length()-1);
        Boolean ret = hm.get(path.toString());
        //todo: not ideal, as this depends on modules naming themselves properly...
        if ((ret != null && !ret) || parentLoaders.containsKey(path_str)) {
            try (Stream<Path> walk = Files.walk(path)){
                walk.forEach(pth -> hm.put(pth.toString(), false));
            } catch (Throwable e) {
                LOGGER.debug(LogMarkers.DYNAMIC_EXCLUDE_ERRORS, "Could not mark sub-entries of {} as excluded: ", path);
            }
            return true;
        }
        return false;
    }
    private static boolean anyParentExcluded(Path path, ConcurrentHashMap<String, Boolean> hm) {
        Path path_iter = path;
        while (path_iter != null) {
            if (isExcluded(path_iter, hm)) {
                return true;
            }
            path_iter = path_iter.getParent();
        }
        return false;
    }
    private static boolean shouldBeIncluded(Path path, ConcurrentHashMap<String, Boolean> hm){
        //we assume, that no wrong mappings are ever put into the hashmap. Thus, all mappings should be taken to be final.
        final String path_str = path.toString();
        Boolean ret = hm.get(path_str);
        if (ret != null) return ret;

        //Check, if any of the super-packages are excluded.
        if (anyParentExcluded(path, hm)) return false;

        //populate caches.
        //this will check, if any package is excluded, and mark all the sub-packages/classes also as excluded.
        //(may end up setting keys multiple times to the same value)
        try (Stream<Path> walk = Files.walk(path)){
            walk.forEach(path1 -> isExcluded(path1, hm));
        }catch (Throwable throwable){
            LOGGER.debug(LogMarkers.DYNAMIC_EXCLUDE_ERRORS, "Error whilst trying to determine include status: ", throwable);
            return true;
        }

        //no super-packages are excluded.
        //we include this class, if no other class exists on this classloader
        //we include this package, if there is any class anywhere below this package, that is included.
        try (Stream<Path> walk = Files.walk(path)){
            boolean include = walk
                    .anyMatch(path1 -> {
                                //return the cache value, if present.
                                final String name = path1.toString();
                                Boolean val = hm.get(name);
                                if (val != null) return val;
                                //otherwise, we have not been excluded. So check, if that class is already present in the classloader, and if not add it.
                                boolean include_runnable = 
                                        name.startsWith("META-INF") ||
                                        name.chars().filter(chr -> chr == '/').count() <= 1 ||
                                        AbstractModProvider.class.getClassLoader().getResource(name) == null;
                                //don't update the map unconditionally at this point, because this could very well be a package.
                                //for packages this is actually incorrect.
                                if (name.endsWith(".class")) hm.put(name, include_runnable);
                                return include_runnable;
                        }
                    );
            if (!hm.containsKey(path_str)) hm.put(path_str, include);
            return include;
        }catch (Throwable throwable){
            LOGGER.debug(LogMarkers.DYNAMIC_EXCLUDE_ERRORS, "Error whilst trying to determine include status: ", throwable);
            return true;
        }
    }
    
    static {
        if (AbstractModProvider.class.getClassLoader() instanceof cpw.mods.cl.ModuleClassLoader mcl){
            Map<String, ClassLoader> loader;
            try {
                loader = (Map<String, ClassLoader>) org.kettingpowered.ketting.internal.hacks.Unsafe.lookup().findGetter(cpw.mods.cl.ModuleClassLoader.class, "parentLoaders", Map.class).invoke(mcl);
            } catch (Throwable e) {
                loader = new HashMap<>();
            }
            parentLoaders = loader;
        } else {
            parentLoaders = new HashMap<>();
        }
    }
    private static final Map<String, ClassLoader> parentLoaders;
    
    //Ketting mark as nullable for things we do not want loaded, because they already are loaded, but still continue starting.
    protected @org.jetbrains.annotations.Nullable IModLocator.ModFileOrException createMod(Path... path) {
        var mjm = new ModJarMetadata();
        final SecureJar unfiltered_sj = SecureJar.from(
                Manifest::new,
                jar -> jar.moduleDataProvider().findFile(MODS_TOML).isPresent() ? mjm : JarMetadata.from(jar, path),
                (root, p) -> true,
                path
        );
        //If this jar is a module, which is already present, we can just ignore it.
        try{
            final String moduleName = unfiltered_sj.moduleDataProvider().name();
            if (parentLoaders.containsKey(moduleName)){
                LOGGER.warn("[Ketting] Tried to load duplicate module {}. Will ignore this module and all jarjar entries in this jar.", moduleName);
                return null;
            }
        }catch (Throwable ignored){}
        
        final ConcurrentHashMap<String, Boolean> hm = new ConcurrentHashMap<>();
        var sj = SecureJar.from(
                Manifest::new,
                jar -> jar.moduleDataProvider().findFile(MODS_TOML).isPresent() ? mjm : JarMetadata.from(jar, path),
                (root, p) -> {
                    boolean include = shouldBeIncluded(unfiltered_sj.getPath(root), hm);
                    if (!include) LOGGER.debug(LogMarkers.DYNAMIC_EXCLUDE_EXCLUDES, "excluding class {} for mod {}", root, path);
                    return include;
                    //Ketting end
                },
                path
        );
        

        IModFile mod;
        var type = sj.moduleDataProvider().getManifest().getMainAttributes().getValue(ModFile.TYPE);
        if (type == null) {
            type = getDefaultJarModType();
        }
        if (sj.moduleDataProvider().findFile(MODS_TOML).isPresent()) {
            LOGGER.debug(LogMarkers.SCAN, "Found {} mod of type {}: {}", MODS_TOML, type, path);
            mod = new ModFile(sj, this, ModFileParser::modsTomlParser);
        } else if (type != null) {
            LOGGER.debug(LogMarkers.SCAN, "Found {} mod of type {}: {}", MANIFEST, type, path);
            mod = new ModFile(sj, this, this::manifestParser, type);
        } else {
            return new IModLocator.ModFileOrException(null, new ModFileLoadingException("Invalid mod file found "+ Arrays.toString(path)));
        }

        mjm.setModFile(mod);
        return new IModLocator.ModFileOrException(mod, null);
    }

    protected IModFileInfo manifestParser(final IModFile mod) {
        Function<String, Optional<String>> cfg = name -> Optional.ofNullable(mod.getSecureJar().moduleDataProvider().getManifest().getMainAttributes().getValue(name));
        var license = cfg.apply("LICENSE").orElse("");
        var dummy = new IConfigurable() {
            @Override
            public <T> Optional<T> getConfigElement(String... key) {
                return Optional.empty();
            }
            @Override
            public List<? extends IConfigurable> getConfigList(String... key) {
                return Collections.emptyList();
            }
        };

        return new DefaultModFileInfo(mod, license, dummy);
    }

    @Override
    public boolean isValid(final IModFile modFile) {
        return true;
    }

    protected String getDefaultJarModType() {
        return null;
    }

    private record DefaultModFileInfo(IModFile mod, String license, IConfigurable configurable) implements IModFileInfo, IConfigurable {
        @Override
        public <T> Optional<T> getConfigElement(final String... strings)
        {
            return Optional.empty();
        }

        @Override
        public List<? extends IConfigurable> getConfigList(final String... strings)
        {
            return null;
        }

        @Override public List<IModInfo> getMods() { return Collections.emptyList(); }
        @Override public List<LanguageSpec> requiredLanguageLoaders() { return Collections.emptyList(); }
        @Override public boolean showAsResourcePack() { return false; }
        @Override public Map<String, Object> getFileProperties() { return Collections.emptyMap(); }
        @Override
        public String getLicense() { return license; }

        @Override public IModFile getFile() { return mod; }
        @Override public IConfigurable getConfig() { return configurable; }

        // These Should never be called as it's only called from ModJarMetadata.version and we bypass that
        @Override public String moduleName() { return mod.getSecureJar().name(); }
        @Override public String versionString() { return null; }
        @Override public List<String> usesServices() { return null; }

        @Override
        public String toString() {
            return "IModFileInfo(" + mod.getFilePath() + ")";
        }
    }
}
