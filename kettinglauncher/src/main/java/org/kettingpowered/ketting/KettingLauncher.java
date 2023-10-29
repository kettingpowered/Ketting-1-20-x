package org.kettingpowered.ketting;

import org.kettingpowered.ketting.common.betterui.BetterUI;
import org.kettingpowered.ketting.utils.FileUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.kettingpowered.ketting.common.KettingConstants.*;

public class KettingLauncher {

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
        //TODO: Launch Ketting
        System.exit(0);
    }
}
