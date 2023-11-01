package org.bukkit.craftbukkit;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import jline.UnsupportedTerminal;
import joptsimple.OptionException;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.util.PathConverter;
import joptsimple.util.PathProperties;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.fusesource.jansi.AnsiConsole;

public class Main {

    public static boolean useJline = true;
    public static boolean useConsole = true;

    public static void main(String[] args) {
        OptionParser parser = new OptionParser() {
            {
                this.acceptsAll(Main.asList("?", "help"), "Show the help");
                this.acceptsAll(Main.asList("c", "config"), "Properties file to use").withRequiredArg().ofType(File.class).defaultsTo(new File("server.properties"), new File[0]).describedAs("Properties file");
                this.acceptsAll(Main.asList("P", "plugins"), "Plugin directory to use").withRequiredArg().ofType(File.class).defaultsTo(new File("plugins"), new File[0]).describedAs("Plugin directory");
                this.acceptsAll(Main.asList("h", "host", "server-ip"), "Host to listen on").withRequiredArg().ofType(String.class).describedAs("Hostname or IP");
                this.acceptsAll(Main.asList("W", "world-dir", "universe", "world-container"), "World container").withRequiredArg().ofType(File.class).defaultsTo(new File("."), new File[0]).describedAs("Directory containing worlds");
                this.acceptsAll(Main.asList("w", "world", "level-name"), "World name").withRequiredArg().ofType(String.class).describedAs("World name");
                this.acceptsAll(Main.asList("p", "port", "server-port"), "Port to listen on").withRequiredArg().ofType(Integer.class).describedAs("Port");
                this.accepts("serverId", "Server ID").withRequiredArg();
                this.accepts("jfrProfile", "Enable JFR profiling");
                this.accepts("pidFile", "pid File").withRequiredArg().withValuesConvertedBy(new PathConverter(new PathProperties[0]));
                this.acceptsAll(Main.asList("o", "online-mode"), "Whether to use online authentication").withRequiredArg().ofType(Boolean.class).describedAs("Authentication");
                this.acceptsAll(Main.asList("s", "size", "max-players"), "Maximum amount of players").withRequiredArg().ofType(Integer.class).describedAs("Server size");
                this.acceptsAll(Main.asList("d", "date-format"), "Format of the date to display in the console (for log entries)").withRequiredArg().ofType(SimpleDateFormat.class).describedAs("Log date format");
                this.acceptsAll(Main.asList("log-pattern"), "Specfies the log filename pattern").withRequiredArg().ofType(String.class).defaultsTo("server.log", new String[0]).describedAs("Log filename");
                this.acceptsAll(Main.asList("log-limit"), "Limits the maximum size of the log file (0 = unlimited)").withRequiredArg().ofType(Integer.class).defaultsTo(0, new Integer[0]).describedAs("Max log size");
                this.acceptsAll(Main.asList("log-count"), "Specified how many log files to cycle through").withRequiredArg().ofType(Integer.class).defaultsTo(1, new Integer[0]).describedAs("Log count");
                this.acceptsAll(Main.asList("log-append"), "Whether to append to the log file").withRequiredArg().ofType(Boolean.class).defaultsTo(true, new Boolean[0]).describedAs("Log append");
                this.acceptsAll(Main.asList("log-strip-color"), "Strips color codes from log file");
                this.acceptsAll(Main.asList("b", "bukkit-settings"), "File for bukkit settings").withRequiredArg().ofType(File.class).defaultsTo(new File("bukkit.yml"), new File[0]).describedAs("Yml file");
                this.acceptsAll(Main.asList("C", "commands-settings"), "File for command settings").withRequiredArg().ofType(File.class).defaultsTo(new File("commands.yml"), new File[0]).describedAs("Yml file");
                this.acceptsAll(Main.asList("forceUpgrade"), "Whether to force a world upgrade");
                this.acceptsAll(Main.asList("eraseCache"), "Whether to force cache erase during world upgrade");
                this.acceptsAll(Main.asList("nogui"), "Disables the graphical console");
                this.acceptsAll(Main.asList("nojline"), "Disables jline and emulates the vanilla console");
                this.acceptsAll(Main.asList("noconsole"), "Disables the console");
                this.acceptsAll(Main.asList("v", "version"), "Show the CraftBukkit Version");
                this.acceptsAll(Main.asList("demo"), "Demo mode");
                this.acceptsAll(Main.asList("initSettings"), "Only create configuration files and then exit");
                this.acceptsAll(Main.asList("S", "spigot-settings"), "File for spigot settings").withRequiredArg().ofType(File.class).defaultsTo(new File("spigot.yml"), new File[0]).describedAs("Yml file");
            }
        };
        OptionSet options = null;

        try {
            options = parser.parse(args);
        } catch (OptionException optionexception) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, optionexception.getLocalizedMessage());
        }

        if (options != null && !options.has("?")) {
            if (options.has("v")) {
                System.out.println(CraftServer.class.getPackage().getImplementationVersion());
            } else {
                String path = (new File(".")).getAbsolutePath();

                if (path.contains("!") || path.contains("+")) {
                    System.err.println("Cannot run server in a directory with ! or + in the pathname. Please rename the affected folders and try again.");
                    return;
                }

                float javaVersion = Float.parseFloat(System.getProperty("java.class.version"));

                if ((double) javaVersion < 61.0D) {
                    System.err.println("Unsupported Java detected (" + javaVersion + "). This version of Minecraft requires at least Java 17. Check your Java version with the command 'java -version'.");
                    return;
                }

                if ((double) javaVersion > 65.0D) {
                    System.err.println("Unsupported Java detected (" + javaVersion + "). Only up to Java 21 is supported.");
                    return;
                }

                String javaVersionName = System.getProperty("java.version");
                boolean isPreRelease = javaVersionName.contains("-");

                if (isPreRelease && (double) javaVersion == 61.0D) {
                    System.err.println("Unsupported Java detected (" + javaVersionName + "). You are running an outdated, pre-release version. Only general availability versions of Java are supported. Please update your Java version.");
                    return;
                }

                try {
                    String jline_UnsupportedTerminal = new String(new char[]{'j', 'l', 'i', 'n', 'e', '.', 'U', 'n', 's', 'u', 'p', 'p', 'o', 'r', 't', 'e', 'd', 'T', 'e', 'r', 'm', 'i', 'n', 'a', 'l'});
                    String jline_terminal = new String(new char[]{'j', 'l', 'i', 'n', 'e', '.', 't', 'e', 'r', 'm', 'i', 'n', 'a', 'l'});

                    Main.useJline = !jline_UnsupportedTerminal.equals(System.getProperty(jline_terminal));
                    if (options.has("nojline")) {
                        System.setProperty("user.language", "en");
                        Main.useJline = false;
                    }

                    if (Main.useJline) {
                        AnsiConsole.systemInstall();
                    } else {
                        System.setProperty("jline.terminal", UnsupportedTerminal.class.getName());
                    }

                    if (options.has("noconsole")) {
                        Main.useConsole = false;
                    }

                    if (Main.class.getPackage().getImplementationVendor() != null && System.getProperty("IReallyKnowWhatIAmDoingISwear") == null) {
                        Date buildDate = new Date((long) Integer.parseInt(Main.class.getPackage().getImplementationVendor()) * 1000L);
                        Calendar deadline = Calendar.getInstance();

                        deadline.add(6, -21);
                        if (buildDate.before(deadline.getTime())) {
                            System.err.println("*** Error, this build is outdated ***");
                            System.err.println("*** Please download a new build as per instructions from https://www.spigotmc.org/go/outdated-spigot ***");
                            System.err.println("*** Server will start in 20 seconds ***");
                            Thread.sleep(TimeUnit.SECONDS.toMillis(20L));
                        }
                    }

                    System.out.println("Loading libraries, please wait...");
                    //net.minecraft.server.Main.main(options); //Ketting: We boot from here instead
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
        } else {
            try {
                parser.printHelpOn(System.out);
            } catch (IOException ioexception) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, (String) null, ioexception);
            }
        }

    }

    private static List asList(String... params) {
        return Arrays.asList(params);
    }
}
