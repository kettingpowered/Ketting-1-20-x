package org.spigotmc;

import java.io.File;
import java.util.Iterator;
import java.util.Locale;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class RestartCommand extends Command {

    public RestartCommand(String name) {
        super(name);
        this.description = "Restarts the server";
        this.usageMessage = "/restart";
        this.setPermission("bukkit.command.restart");
    }

    public boolean execute(CommandSender sender, String currentAlias, String[] args) {
        if (this.testPermission(sender)) {
            MinecraftServer.getServer().processQueue.add(new Runnable() {
                public void run() {
                    RestartCommand.restart();
                }
            });
        }

        return true;
    }

    public static void restart() {
        restart(SpigotConfig.restartScript);
    }

    private static void restart(final String restartScript) {
        AsyncCatcher.enabled = false;

        try {
            String[] split = restartScript.split(" ");

            if (split.length > 0 && (new File(split[0])).isFile()) {
                System.out.println("Attempting to restart with " + restartScript);
                WatchdogThread.doStop();
                Iterator iterator = MinecraftServer.getServer().getPlayerList().players.iterator();

                while (iterator.hasNext()) {
                    ServerPlayer p = (ServerPlayer) iterator.next();

                    p.connection.disconnect(SpigotConfig.restartMessage);
                }

                try {
                    Thread.sleep(100L);
                } catch (InterruptedException interruptedexception) {
                    ;
                }

                MinecraftServer.getServer().getConnection().stop();

                try {
                    Thread.sleep(100L);
                } catch (InterruptedException interruptedexception1) {
                    ;
                }

                try {
                    MinecraftServer.getServer().close();
                } catch (Throwable throwable) {
                    ;
                }

                Thread shutdownHook = new Thread() {
                    public void run() {
                        try {
                            String os = System.getProperty("os.name").toLowerCase(Locale.ENGLISH);

                            if (os.contains("win")) {
                                Runtime.getRuntime().exec("cmd /c start " + restartScript);
                            } else {
                                Runtime.getRuntime().exec("sh " + restartScript);
                            }
                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }

                    }
                };

                shutdownHook.setDaemon(true);
                Runtime.getRuntime().addShutdownHook(shutdownHook);
            } else {
                System.out.println("Startup script '" + SpigotConfig.restartScript + "' does not exist! Stopping server.");

                try {
                    MinecraftServer.getServer().close();
                } catch (Throwable throwable1) {
                    ;
                }
            }

            System.exit(0);
        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }
}
