package org.kettingpowered.ketting.common.betterui;

import org.kettingpowered.ketting.common.utils.ShortenedStackTrace;

import java.util.Scanner;

import java.io.*;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BetterUI {

    private static boolean enabled = true,  enableBigLogo = true;

    private static final String[] bigLogo = {
            " /$$   /$$             /$$     /$$     /$$                         ",
            "| $$  /$$/            | $$    | $$    |__/                         ",
            "| $$ /$$/   /$$$$$$  /$$$$$$ /$$$$$$   /$$ /$$$$$$$   /$$$$$$      ",
            "| $$$$$/   /$$__  $$|_  $$_/|_  $$_/  | $$| $$__  $$ /$$__  $$     ",
            "| $$  $$  | $$$$$$$$  | $$    | $$    | $$| $$  \\ $$| $$  \\ $$   ",
            "| $$\\  $$ | $$_____/  | $$ /$$| $$ /$$| $$| $$  | $$| $$  | $$    ",
            "| $$ \\  $$|  $$$$$$$  |  $$$$/|  $$$$/| $$| $$  | $$|  $$$$$$$    ",
            "|__/  \\__/ \\_______/   \\___/   \\___/  |__/|__/  |__/ \\____  $$",
            "                                                     /$$  \\ $$    ",
            "                                                    |  $$$$$$/     ",
            "                                                     \\______/     "
    };

    public static void printTitle(String name, String brand, String javaVersion, String version, String bukkitVersion, String forgeVersion) {
        if (!enabled)
            return;

        String[] split = forgeVersion.split("-");
        if (split.length > 1)
            forgeVersion = forgeVersion.substring(forgeVersion.indexOf("-") + 1, forgeVersion.lastIndexOf("-"));

        String divider = "-".repeat(45);
        String copyright = "Copyright (c) " + new SimpleDateFormat("yyyy").format(new Date()) + " " + brand;
        String java = "Running on Java " + javaVersion;
        String server = name + " version " + version;
        String bukkit = "Bukkit version  " + bukkitVersion;
        String forge = "Forge version   " + forgeVersion;

        if (enableBigLogo) {
            System.out.println();
            for (int i = 0; i < bigLogo.length; i++) {
                if (i < 9)
                    System.out.println(bigLogo[i]);
                else {
                    int limit = divider.length();

                    if (i == 9)
                        printPartial(copyright, limit, bigLogo[i]);
                    else
                        printPartial(divider, limit, bigLogo[i]);
                }
            }
        } else {
            System.out.println(name);
            System.out.println(copyright);
            System.out.println(divider);
        }

        System.out.println(java);
        System.out.println(server);
        System.out.println(bukkit);
        System.out.println(forge);
        System.out.println(divider);
    }

    private static void printPartial(String s, int limit, String logo) {
        int l = Math.min(s.length(), limit);
        System.out.println(s.substring(0, l) + logo.substring(l));
    }

    public static void printError(String name, String errorType, String cause, ShortenedStackTrace... trace) {
        if (!enabled)
            return;
        System.err.println("------------------------------------------------------------");
        System.err.println("A critical error has occurred and your server was shut down.");
        System.err.println("Please send this info to the " + name + " team on Github    ");
        System.err.println("Please also include the full log file!                      ");
        System.err.println("------------------------------------------------------------");
        System.err.println("Error type: " + errorType);
        System.err.println("Caused by: " + (cause == null ? "Unknown reason" : cause));
        System.err.println();
        System.err.println("Short stack trace(s):");
        for (ShortenedStackTrace s : trace) {
            s.print();
            System.err.println();
        }
        System.err.println("------------------------------------------------------------");
    }

    public static void printHelp(String name) {
        System.out.println(name + " Help");
        System.out.println("-".repeat(name.length() + 5));
        System.out.println("Usage: java -jar your-jar-name.jar [options]");
        System.out.println("Options:");
        System.out.println("  -noui        Disables the fancy UI");
        System.out.println("  -nologo      Disables the big logo");
        System.out.println("  -accepteula  Accepts the EULA automatically");
        System.out.println("  -dau         Disables automatic updates");
        System.out.println("  -help        Shows this help message");
    }

    public static boolean checkEula(Path path_to_eula) throws IOException {
        File file = path_to_eula.toFile();
        ServerEula eula = new ServerEula(path_to_eula);

        if (!enabled)
            return eula.hasAgreedToEULA();

        if (!eula.hasAgreedToEULA()) {
            System.out.println("WARNING: It appears you have not agreed to the EULA.\nPlease read the EULA (https://account.mojang.com/documents/minecraft_eula) and type 'yes' to continue.");
            System.out.print("Do you accept? (yes/no): ");

            int wrong = 0;

            Scanner console = new Scanner(System.in);
            while (true) {
                String answer = console.nextLine();
                if (answer == null || answer.isBlank()) {
                    if (wrong++ >= 2) {
                        System.err.println("You have typed the wrong answer too many times. Exiting.");
                        return false;
                    }
                    System.out.println("Please type 'yes' or 'no'.");
                    System.out.print("Do you accept? (yes/no): ");
                    continue;
                }

                switch (answer.toLowerCase()) {
                    case "y", "yes" -> {
                        file.delete();
                        file.createNewFile();
                        try (FileWriter writer = new FileWriter(file)) {
                            writer.write("eula=true");
                        }
                        return true;
                    }
                    case "n", "no" -> {
                        System.err.println("You must accept the EULA to continue. Exiting.");
                        return false;
                    }
                    default -> {
                        if (wrong++ >= 2) {
                            System.err.println("You have typed the wrong answer too many times. Exiting.");
                            return false;
                        }
                        System.out.println("Please type 'yes' or 'no'.");
                        System.out.print("Do you accept? (yes/no): ");
                    }
                }
            }

        } else return true;
    }

    public static void forceAcceptEULA(Path path_to_eula) throws IOException {
        File file = path_to_eula.toFile();
        if (file.exists())
            file.delete();
        file.createNewFile();
        try (FileWriter writer = new FileWriter(file)) {
            writer.write("eula=true");
        }
    }

    public static void setEnabled(boolean enabled) {
        BetterUI.enabled = enabled;
    }

    public static void setEnableBigLogo(boolean enableBigLogo) {
        BetterUI.enableBigLogo = enableBigLogo;
    }

    public static boolean isEnabled() {
        return enabled;
    }
}

