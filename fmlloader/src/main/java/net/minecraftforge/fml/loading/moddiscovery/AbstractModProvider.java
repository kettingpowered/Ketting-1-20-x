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

import java.io.IOException;
import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;
import java.util.jar.Manifest;

//Ketting start
import java.nio.file.Files;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;
//Ketting end

public abstract class AbstractModProvider implements IModProvider
{
    private static final   Logger LOGGER    = LogUtils.getLogger();
    protected static final String MODS_TOML = "META-INF/mods.toml";
    protected static final String MANIFEST = "META-INF/MANIFEST.MF";

    //Ketting start

    /**
     * Checks, if a given path is the name of an already present module.
     * If so, it will mark that package (and everything below it) as excluded.
     * else does nothing.
     * @param path Path to check
     * @param hm Cache
     */
    private static void populateIsExcludedPackage(Path path, ConcurrentHashMap<String, Boolean> hm) {
        //this package has already been excluded
        Boolean ret = hm.get(path.toString());
        if (ret != null && !ret) return;
        String path_str = path.toString().replace('/', '.');
        if (path_str.endsWith(".")) path_str = path_str.substring(0, path_str.length()-1);
        //todo: not ideal, as this depends on modules naming themselves properly...
        if (parentLoaders.containsKey(path_str)) {
            hm.put(path.toString(), false);
            try (Stream<Path> walk = Files.walk(path)){
                LOGGER.debug(LogMarkers.DYNAMIC_EXCLUDE_EXCLUDES, "Marking {} as excluded.", path);
                //filter to dir's here, so that individual class-changes are ignored.
                // We really only care about package correctness here, because modules.
                walk.filter(Files::isDirectory) 
                        .forEach(dir -> {
                            boolean included = AbstractModProvider.class.getClassLoader().getResource(dir.toString()) == null;
                            //sometimes modules include other modules, which are subpathed. e.g. org.joml and org.joml.primitives
                            //if org.joml is present, org.joml.primitives would always get (wrongly) excluded without this check.
                            if (included){
                                LOGGER.debug(LogMarkers.DYNAMIC_EXCLUDE_EXCLUDES, "Including {} instead, as that path is not found in the main ClassLoader.", dir);
                            }
                            try (Stream<Path> walk2 = Files.walk(dir)){
                                walk2.filter(Files::isRegularFile)
                                        .forEach(pth->hm.put(pth.toString(), included));
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        });
                LOGGER.trace(LogMarkers.DYNAMIC_EXCLUDE_EXCLUDES, "hashmap after exclude: {}", hm.entrySet().stream().map(entry -> entry.getKey() + ": " + entry.getValue().toString()).collect(Collectors.joining(", ")));
            } catch (Throwable e) {
                LOGGER.debug(LogMarkers.DYNAMIC_EXCLUDE_ERRORS, "Could not mark sub-entries of {} as excluded: ", path);
            }
        }
    }

    private static Optional<Boolean> shouldBeIncludedCache(Path path, ConcurrentHashMap<String, Boolean> hm){
        final String name = path.toString();
        //we assume, that no wrong mappings are ever put into the hashmap. Thus, all mappings should be taken to be final.
        Boolean ret = hm.get(name);
        if (ret != null) {
            return Optional.of(ret);
        }

        //if the parent is excluded, we do not need not bother checking anything else. 
        // the file is not going to be excluded anyway
        Path parent = path.getParent();
        if (parent != null){
            Boolean ret_parent = hm.get(parent.toString());
            if (ret_parent != null && !ret_parent) {
                LOGGER.warn(LogMarkers.DYNAMIC_EXCLUDE_ERRORS, "{} was not marked as excluded, even-though {} is.", path, parent);
                hm.put(path.toString(), false);
                return Optional.of(false);
            }
            //parent = parent.getParent();
        }
        return Optional.empty();
    }
    private static boolean shouldBeIncludedFile(Path path, ConcurrentHashMap<String, Boolean> hm){
        final String name = path.toString();

        Optional<Boolean> val = shouldBeIncludedCache(path, hm);
        if (val.isPresent()) return val.get();
        if (!CheckIndividualFiles) return true;

        boolean ret = name.startsWith("META-INF") ||
                name.chars().filter(chr -> chr == '/').count() <= 1 ||
                AbstractModProvider.class.getClassLoader().getResource(name) == null;
        hm.put(name, ret);
        return ret;
    }
    private static boolean shouldBeIncluded(Path path, ConcurrentHashMap<String, Boolean> hm){
        {
            Optional<Boolean> ret = shouldBeIncludedCache(path, hm);
            if (ret.isPresent()) return ret.get();
        }
        if (!CheckIndividualFiles) return true;
        if (Files.isRegularFile(path)){
            return shouldBeIncludedFile(path, hm);
        }

        //no super-packages are excluded.
        //we include this class, if no other class exists on this classloader
        //we include this package, if there is any class anywhere below this package, that is included.
        try (Stream<Path> walk = Files.walk(path)){
            boolean include = walk
                    .filter(Files::isRegularFile)
                    .anyMatch(path1->shouldBeIncludedFile(path1, hm));
            final String path_str = path.toString();
            if (!hm.containsKey(path_str)) hm.put(path_str, include);
            return include;
        }catch (Throwable throwable){
            LOGGER.debug(LogMarkers.DYNAMIC_EXCLUDE_ERRORS, "Error whilst trying to determine include status: ", throwable);
            return true;
        }
    }
    /**
     * Will call {@link #shouldBeIncluded(Path, ConcurrentHashMap)} with all files as part of Cache-Population.
     * May be VERY expensive depending on the amount of files/mods.
     */
    private static final boolean PrePopulateCacheFiles = Boolean.parseBoolean(System.getProperty("org.kettingpowered.ketting.dynamicExcludes.prePopulateCache"));;
    /**
     * When false will not check individual files for inclusion status and only go off of package excludes
     * , which are populated by {@link #populateIsExcludedPackage(Path, ConcurrentHashMap)}.
     */
    private static final boolean CheckIndividualFiles = Boolean.parseBoolean(System.getProperty("org.kettingpowered.ketting.dynamicExcludes.checkIndividualFiles"));
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
        if (PrePopulateCacheFiles) LOGGER.warn("The option \"org.kettingpowered.ketting.dynamicExcludes.prePopulateCache\" is experimental, because it will significantly slow down the server-startup at no cost." +
                "If the server launches fine without that flag set, please consider removing it.");
        if (CheckIndividualFiles) LOGGER.warn("The option \"org.kettingpowered.ketting.dynamicExcludes.checkIndividualFiles\" is experimental, because it will significantly slow down the server-startup at no cost." +
                "If the server launches fine without that flag set, please consider removing it.");
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
        boolean forceInclude = false;
        //If this jar is a module, which is already present, we can just ignore it.
        try{
            final String moduleName = unfiltered_sj.moduleDataProvider().name();
            if (parentLoaders.containsKey(moduleName)){
                LOGGER.warn("[Ketting] Tried to load duplicate module {}. Will ignore this module and all jarjar entries in this jar.", moduleName);
                return null;
            }else{
                forceInclude = true;
                LOGGER.info("[Ketting] Force including module {} from {}.", moduleName, path);
            }
        }catch (Throwable ignored){}
        final boolean finalForceInclude = forceInclude;
        
        final ConcurrentHashMap<String, Boolean> hm = new ConcurrentHashMap<>();

        AtomicBoolean hmInit = new AtomicBoolean(false);
        Optional<ForkJoinTask<?>> hmInitTask;
        if (!forceInclude)
            hmInitTask = Optional.of(ForkJoinPool.commonPool().submit(() -> {
                Instant start = Instant.now();
                try (Stream<Path> walk = Files.walk(unfiltered_sj.getPath("/"))){
                    walk.filter(p->!p.toString().endsWith(".class"))
                        .filter(Files::isDirectory)
                        .forEach(p->populateIsExcludedPackage(p, hm));
                    hmInit.setRelease(true);
                }catch (Throwable throwable) {
                    LOGGER.debug(LogMarkers.DYNAMIC_EXCLUDE_ERRORS, "Error whilst trying to determine include status: ", throwable);
                    throw new RuntimeException(throwable);
                }
                
                if (PrePopulateCacheFiles && CheckIndividualFiles){
                    try (Stream<Path> walk = Files.walk(unfiltered_sj.getPath("/"))){
                        walk.map(p->ForkJoinPool.commonPool().submit(()->shouldBeIncluded(p, hm)).fork())
                            .forEach(ForkJoinTask::quietlyJoin);
                    }catch (Throwable throwable) {
                        LOGGER.debug(LogMarkers.DYNAMIC_EXCLUDE_ERRORS, "Error whilst trying to determine include status: ", throwable);
                        throw new RuntimeException(throwable);
                    }
                }
                LOGGER.debug(LogMarkers.DYNAMIC_EXCLUDE_EXCLUDES, "Initializing excludes for {} took: {}", path, Duration.between(start, Instant.now()));
                hmInit.setRelease(true);
            }).fork());
        else hmInitTask = Optional.empty();
        
        var sj = SecureJar.from(
                Manifest::new,
                jar -> jar.moduleDataProvider().findFile(MODS_TOML).isPresent() ? mjm : JarMetadata.from(jar, path),
                (root, p) -> {
                    if (finalForceInclude) return true;
                    //fast path. This particularly allows for access to all files in META-INF and all top-level files 
                    // (e.g. mixin declarations, mixin refmaps, mod icons, accesswideners, pack.mcmeta and others)
                    // without having to wait for the cache initialization.
                    if (root != null && (root.startsWith("META-INF") || root.chars().filter(chr -> '/' == chr).count() <= 1)) return true;
                    //noinspection ConstantValue
                    if (hmInitTask.isPresent() && !hmInit.get()) {
                        LOGGER.debug("Waiting on hm init for {}", root);
                        hmInitTask.get().join();
                    }
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
