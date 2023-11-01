package org.bukkit.craftbukkit.v1_20_R2;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.function.Supplier;
import net.minecraft.server.MinecraftServer;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_20_R2.util.CraftMagicNumbers;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;

public class CraftCrashReport implements Supplier {

    public String get() {
        StringWriter value = new StringWriter();

        try {
            value.append("\n   Running: ").append(Bukkit.getName()).append(" version ").append(Bukkit.getVersion()).append(" (Implementing API version ").append(Bukkit.getBukkitVersion()).append(") ").append(String.valueOf(MinecraftServer.getServer().usesAuthentication()));
            value.append("\n   Plugins: {");
            Plugin[] aplugin;
            int i = (aplugin = Bukkit.getPluginManager().getPlugins()).length;

            for (int j = 0; j < i; ++j) {
                Plugin plugin = aplugin[j];
                PluginDescriptionFile description = plugin.getDescription();
                boolean legacy = CraftMagicNumbers.isLegacy(description);

                value.append(' ').append(description.getFullName()).append(legacy ? "*" : "").append(' ').append(description.getMain()).append(' ').append(Arrays.toString(description.getAuthors().toArray())).append(',');
            }

            value.append("}\n   Warnings: ").append(Bukkit.getWarningState().name());
            value.append("\n   Reload Count: ").append(String.valueOf(MinecraftServer.getServer().server.reloadCount));
            value.append("\n   Threads: {");
            Iterator iterator = Thread.getAllStackTraces().entrySet().iterator();

            while (iterator.hasNext()) {
                Entry entry = (Entry) iterator.next();

                value.append(' ').append(((Thread) entry.getKey()).getState().name()).append(' ').append(((Thread) entry.getKey()).getName()).append(": ").append(Arrays.toString((Object[]) entry.getValue())).append(',');
            }

            value.append("}\n   ").append(Bukkit.getScheduler().toString());
            value.append("\n   Force Loaded Chunks: {");
            iterator = Bukkit.getWorlds().iterator();

            while (iterator.hasNext()) {
                World world = (World) iterator.next();

                value.append(' ').append(world.getName()).append(": {");
                Iterator iterator1 = world.getPluginChunkTickets().entrySet().iterator();

                while (iterator1.hasNext()) {
                    Entry entry = (Entry) iterator1.next();

                    value.append(' ').append(((Plugin) entry.getKey()).getDescription().getFullName()).append(": ").append(Integer.toString(((Collection) entry.getValue()).size())).append(',');
                }

                value.append("},");
            }

            value.append("}");
        } catch (Throwable throwable) {
            value.append("\n   Failed to handle CraftCrashReport:\n");
            PrintWriter writer = new PrintWriter(value);

            throwable.printStackTrace(writer);
            writer.flush();
        }

        return value.toString();
    }
}
