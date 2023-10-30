package org.kettingpowered.ketting;

import org.kettingpowered.ketting.common.betterui.BetterUI;
import org.kettingpowered.ketting.common.utils.JarTool;
import org.kettingpowered.ketting.utils.FileUtils;
import org.kettingpowered.ketting.utils.ServerInitHelper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.spi.FileSystemProvider;
import java.util.*;
import java.util.stream.Stream;

import static org.kettingpowered.ketting.common.KettingConstants.*;

public class KettingLauncher {

    //Libs added here will get ignored by ServerInitHelper
    public static final String[] MANUALLY_PATCHED_LIBS = {
        //"com/mojang/brigadier" TODO: add this when we implement our custom command system
    };

    private static List<String> args;
    public static boolean enableUpdate;

    public static void main(String[] args) throws Exception {
        KettingLauncher.args = new ArrayList<>();
        Collections.addAll(KettingLauncher.args, args);

        Path eula = Paths.get("eula.txt");

        parseArgs(eula);

        BetterUI.printTitle(NAME, BRAND, System.getProperty("java.version") + " (" + System.getProperty("java.vendor") + ")", VERSION, BUKKIT_VERSION, FORGE_VERSION);
        if(!BetterUI.checkEula(eula)) System.exit(0);

        if (Patcher.updateNeeded()) {
            //prematurely delete files to prevent errors
            Files.deleteIfExists(KettingFiles.MCP_MAPPINGS.toPath());
            Files.deleteIfExists(KettingFiles.MERGED_MAPPINGS.toPath());
            FileUtils.deleteDir(KettingFiles.NMS_PATCHES_DIR);
        }

        Libraries.setup();
        Patcher.init();
        launch();
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
    }

    private static boolean containsArg(String arg) {
        if (args.isEmpty()) return false;

        arg = arg.toLowerCase();

        if (args.contains(arg)) {
            args.remove(arg);
            return true;
        }

        return false;
    }

    private static void launch() {
        System.out.println("Launching Ketting...");

        fsHacks();

        List<String> launchArgs = JarTool.readFileLinesFromJar("data/" + (System.getProperty("os.name").toLowerCase().contains("win") ? "win" : "unix") + "_args.txt");
        ServerInitHelper.init(launchArgs);

        List<String> forgeArgs = new ArrayList<>();
        launchArgs.stream().filter(s -> s.startsWith("--launchTarget") || s.startsWith("--fml.forgeVersion") || s.startsWith("--fml.mcVersion") || s.startsWith("--fml.forgeGroup") || s.startsWith("--fml.mcpVersion")).toList().forEach(arg -> {
            forgeArgs.add(arg.split(" ")[0]);
            forgeArgs.add(arg.split(" ")[1]);
        });

        String[] invokeArgs = Stream.concat(forgeArgs.stream(), KettingLauncher.args.stream()).toArray(String[]::new);

        try {
            Class.forName("net.minecraftforge.bootstrap.BootstrapLauncher")
                    .getMethod("main", String[].class)
                    .invoke(null, (Object) invokeArgs);
        } catch (Exception e) {
            throw new RuntimeException("Could not launch server", e);
        }
    }

    private static void fsHacks() {
        try {
            ServerInitHelper.addOpens("java.base", "java.nio.file.spi", "ALL-UNNAMED");
            var loadingProvidersField = FileSystemProvider.class.getDeclaredField("loadingProviders");
            loadingProvidersField.setAccessible(true);
            loadingProvidersField.set(null, false);
            var installedProvidersField = FileSystemProvider.class.getDeclaredField("installedProviders");
            installedProvidersField.setAccessible(true);
            installedProvidersField.set(null, null);
        } catch (Exception e) {
            throw new RuntimeException("Could not set filesystem", e);
        }
    }
}
