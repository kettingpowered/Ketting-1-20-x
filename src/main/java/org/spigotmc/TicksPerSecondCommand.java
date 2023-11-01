package org.spigotmc;

import net.minecraft.server.MinecraftServer;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class TicksPerSecondCommand extends Command {

    public TicksPerSecondCommand(String name) {
        super(name);
        this.description = "Gets the current ticks per second for the server";
        this.usageMessage = "/tps";
        this.setPermission("bukkit.command.tps");
    }

    public boolean execute(CommandSender sender, String currentAlias, String[] args) {
        if (!this.testPermission(sender)) {
            return true;
        } else {
            StringBuilder sb = new StringBuilder(ChatColor.GOLD + "TPS from last 1m, 5m, 15m: ");
            double[] adouble;
            int i = (adouble = MinecraftServer.getServer().recentTps).length;

            for (int j = 0; j < i; ++j) {
                double tps = adouble[j];

                sb.append(this.format(tps));
                sb.append(", ");
            }

            sender.sendMessage(sb.substring(0, sb.length() - 2));
            sender.sendMessage(ChatColor.GOLD + "Current Memory Usage: " + ChatColor.GREEN + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1048576L + "/" + Runtime.getRuntime().totalMemory() / 1048576L + " mb (Max: " + Runtime.getRuntime().maxMemory() / 1048576L + " mb)");
            return true;
        }
    }

    private String format(double tps) {
        return (tps > 18.0D ? ChatColor.GREEN : (tps > 16.0D ? ChatColor.YELLOW : ChatColor.RED)).toString() + (tps > 20.0D ? "*" : "") + Math.min((double) Math.round(tps * 100.0D) / 100.0D, 20.0D);
    }
}
