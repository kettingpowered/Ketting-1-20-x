package org.kettingpowered.ketting;

import org.kettingpowered.ketting.common.betterui.BetterUI;
import org.kettingpowered.ketting.utils.FileUtils;
import org.kettingpowered.ketting.utils.ServerInitHelper;
import org.kettingpowered.ketting.utils.Unsafe;

import javax.annotation.Nonnull;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandlerFactory;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.spi.FileSystemProvider;
import java.security.NoSuchAlgorithmException;
import java.util.*;

import static org.kettingpowered.ketting.internal.KettingConstants.*;

public class KettingLauncher {

    //Libs added here will get ignored by getClassPathFromShim
    public static final String[] MANUALLY_PATCHED_LIBS = {
            "com/mojang/brigadier",
    };

    private static List<String> args;
    public static boolean enableUpdate;
    private static String target = "forge_server";

    public static void main(String[] args) throws Exception {
        KettingLauncher.args = new ArrayList<>();
        Collections.addAll(KettingLauncher.args, args);
        //this callback's core_init, when keting_core is loaded (which is done eagerly)
        //this callback's lib_init, when all libs are loaded
        Libraries.setup();

        launch();
    }
    
    public static void core_init() throws IOException, NoSuchAlgorithmException {
        Path eula = Paths.get("eula.txt");
        parseArgs(eula);
        BetterUI.printTitle(NAME, BRAND, System.getProperty("java.version") + " (" + System.getProperty("java.vendor") + ")", VERSION, BUKKIT_PACKAGE_VERSION, FORGE_VERSION);
        if(!BetterUI.checkEula(eula)) System.exit(0);

        if (Patcher.updateNeeded()) {
            //prematurely delete files to prevent errors
            FileUtils.deleteDir(KettingFiles.NMS_PATCHES_DIR);
            FileUtils.deleteDir(KettingFiles.FORGE_BASE_DIR);
        }
    }
    
    public static void lib_init() throws IOException, NoSuchAlgorithmException {
        if (enableUpdate) {
            setStreamFactory();
            new UpdateChecker();
            removeStreamFactory();
        }
        new Patcher();
    }

    private static void parseArgs(Path eula) throws IOException {
        if (containsArg("-help") || containsArg("--help")) {
            BetterUI.printHelp(NAME);
            System.exit(0);
        }

        if (containsArg("-noui"))
            BetterUI.setEnabled(false);

        if (containsArg("-nologo"))
            BetterUI.setEnableBigLogo(false);

        if (containsArg("-accepteula"))
            BetterUI.forceAcceptEULA(eula);

        enableUpdate = !containsArg("-dau");

        String launchTarget = getArg("--launchTarget");
        if (launchTarget != null)
            target = launchTarget;
    }

    private static String getArg(@Nonnull String arg) {
        if (args.isEmpty()) return null ;

        arg = arg.toLowerCase();

        int index = args.indexOf(arg);
        if (index < 0) return null;
        args.remove(index); //remove arg
        return args.remove(index); //this should be the value to that arg
    }
    private static boolean containsArg(String arg) {
        if (args.isEmpty()) return false;

        arg = arg.toLowerCase();

        int index = args.indexOf(arg);
        if (index < 0) return false;
        args.remove(index);
        return true;
    }

    private static void launch() {
        System.out.println("Launching Ketting...");

        args.add("--launchTarget");
        args.add(target);

        try (URLClassLoader loader = new LibraryClassLoader()) {
            loadExternalFileSystems(loader);
            clearReservedIdentifiers();
            setProperties();

            Class.forName("net.minecraftforge.bootstrap.ForgeBootstrap", true, loader)
                    .getMethod("main", String[].class)
                    .invoke(null, (Object) args.toArray(String[]::new));
        } catch (Throwable t) {
            throw new RuntimeException("Could not launch server", t);
        }
    }

    private static void setProperties() {
        System.setProperty("java.class.path", getClassPathFromShim());
        System.setProperty("ketting.remapper.dump", "./.mixin.out/plugin_classes");
    }

    private static String getClassPathFromShim() {
        InputStream stream = KettingLauncher.class.getClassLoader().getResourceAsStream("data/bootstrap-shim.list");
        if (stream == null) throw new RuntimeException("Could not find bootstrap-shim.list");

        StringBuilder builder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream))) {
            String line;
            for(line = reader.readLine(); line != null; line = reader.readLine()) {
                String shim = line.split("\t")[2];
                for (String lib : MANUALLY_PATCHED_LIBS) {
                    if (shim.startsWith(lib)) {
                        shim = null;
                        break;
                    }
                }

                if (shim == null) continue;

                File target = new File(KettingFiles.LIBRARIES_PATH, shim);
                if (!target.exists()) {
                    System.err.println("Could not find " + shim + " in " + INSTALLER_LIBRARIES_FOLDER);
                    continue;
                }

                builder.append(File.pathSeparator).append(target.getAbsolutePath());
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not read bootstrap-shim.list", e);
        }

        return builder.toString();
    }

    //We love hacks
    public static void setStreamFactory() {
        try {
            var factory = Unsafe.lookup().findStaticGetter(URL.class, "defaultFactory", URLStreamHandlerFactory.class).invoke();
            Unsafe.lookup().findStaticSetter(URL.class, "factory", URLStreamHandlerFactory.class).invoke(factory);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public static void removeStreamFactory() {
        try {
            Unsafe.lookup().findStaticSetter(URL.class, "factory", URLStreamHandlerFactory.class).invoke((Object) null);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    private static void loadExternalFileSystems(URLClassLoader loader) {
        try {
            ServerInitHelper.addOpens("java.base", "java.nio.file.spi", "ALL-UNNAMED");
            List<String> knownSchemes = FileSystemProvider.installedProviders().stream().map(FileSystemProvider::getScheme).toList();
            ServiceLoader<FileSystemProvider> sl = ServiceLoader.load(FileSystemProvider.class, loader);
            List<FileSystemProvider> newProviders = sl.stream().map(ServiceLoader.Provider::get).filter(provider -> !knownSchemes.contains(provider.getScheme())).toList();

            final Field installedProviders = FileSystemProvider.class.getDeclaredField("installedProviders");
            installedProviders.setAccessible(true);
            List<FileSystemProvider> providers = new ArrayList<>((List<FileSystemProvider>) installedProviders.get(null));
            providers.addAll(newProviders);
            installedProviders.set(null, providers);
        } catch (Exception e) {
            throw new RuntimeException("Could not load new file systems", e);
        }
    }

    private static void clearReservedIdentifiers() {
        try {
            Unsafe.lookup().findStaticSetter(Class.forName("jdk.internal.module.Checks"), "RESERVED", Set.class).invoke(Set.of());
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}
