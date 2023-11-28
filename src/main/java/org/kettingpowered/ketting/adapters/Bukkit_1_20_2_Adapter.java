package org.kettingpowered.ketting.adapters;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginDescriptionFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.kettingpowered.ketting.adapter.BukkitAdapter;
import org.kettingpowered.ketting.types.Plugin;
import org.kettingpowered.ketting.types.PluginInfo;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Bukkit_1_20_2_Adapter implements BukkitAdapter {

    private final Map<String, Plugin> pluginCache = new HashMap<>();

    public String getMcVersion() {
        return "1.20.2";
    }

    public void reload() {
        int size = pluginCache.size();
        pluginCache.clear();
        LoggerFactory.getLogger(getClass()).info("Released cache of {} plugins", size);
    }

    public @Nullable Plugin getPlugin(String name) {
        if (pluginCache.containsKey(name))
            return pluginCache.get(name);

        final org.bukkit.plugin.Plugin plugin = Bukkit.getPluginManager().getPlugin(name);
        if (plugin == null)
            return null;

        final Plugin converted = new Plugin(
                getPluginInfo(plugin.getDescription()),
                () -> Bukkit.getPluginManager().isPluginEnabled(name),
                plugin.getDataFolder(),
                plugin.getLogger()
        );
        pluginCache.put(name, converted);
        return converted;
    }

    private PluginInfo getPluginInfo(PluginDescriptionFile descriptionFile) {
        return new PluginInfo(
                descriptionFile.getName(),
                descriptionFile.getProvides(),
                descriptionFile.getMain(),
                descriptionFile.getClassLoaderOf(),
                descriptionFile.getDepend(),
                descriptionFile.getSoftDepend(),
                descriptionFile.getLoadBefore(),
                descriptionFile.getVersion(),
                descriptionFile.getCommands(),
                descriptionFile.getDescription(),
                descriptionFile.getAuthors(),
                descriptionFile.getContributors(),
                descriptionFile.getWebsite(),
                descriptionFile.getPrefix(),
                convertLoadOrder(descriptionFile.getLoad()),
                descriptionFile.getAPIVersion(),
                descriptionFile.getLibraries()
        );
    }

    private PluginInfo.PluginLoadOrder convertLoadOrder(org.bukkit.plugin.PluginLoadOrder loadOrder) {
        return switch (loadOrder) {
            case STARTUP -> PluginInfo.PluginLoadOrder.STARTUP;
            default -> PluginInfo.PluginLoadOrder.POSTWORLD;
        };
    }

    public @Nullable List<Plugin> getPlugins() {
        return Arrays.stream(Bukkit.getPluginManager().getPlugins()).map(org.bukkit.plugin.Plugin::getName).map(this::getPlugin).toList();
    }

    public @Nullable String getPluginName(String name) {
        final Plugin plugin = getPlugin(name);
        return plugin == null ? null : plugin.info().name();
    }

    public @Nullable String getPluginName(@NotNull Plugin plugin) {
        return plugin.info().name();
    }

    public void loadPlugin(@NotNull File pluginFile) throws Exception {
        Bukkit.getPluginManager().loadPlugin(pluginFile);
    }

    public void loadPlugins(@NotNull File pluginDirectory) throws Exception {
        Bukkit.getPluginManager().loadPlugins(pluginDirectory);
    }

    public void enablePlugin(@NotNull Plugin plugin) {
        final org.bukkit.plugin.Plugin bukkitPlugin = Bukkit.getPluginManager().getPlugin(plugin.info().name());
        if (bukkitPlugin == null) return;
        Bukkit.getPluginManager().enablePlugin(bukkitPlugin);
    }

    public void disablePlugin(@NotNull Plugin plugin) {
        final org.bukkit.plugin.Plugin bukkitPlugin = Bukkit.getPluginManager().getPlugin(plugin.info().name());
        if (bukkitPlugin == null) return;
        Bukkit.getPluginManager().disablePlugin(bukkitPlugin);
    }

    public void disablePlugins() {
        Bukkit.getPluginManager().disablePlugins();
    }
}
