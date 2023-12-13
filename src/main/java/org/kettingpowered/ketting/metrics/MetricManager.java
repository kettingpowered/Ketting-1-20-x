package org.kettingpowered.ketting.metrics;

import net.minecraft.server.dedicated.DedicatedServer;
import org.bukkit.Bukkit;
import org.kettingpowered.ketting.internal.KettingConstants;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public final class MetricManager {

    private static Metrics metrics;
    private static File configFile;
    private static MetricsConfig config;

    public static void init(DedicatedServer server) {
        if (configFile == null) {
            File pluginsDir = (File) server.options.valueOf("plugins");
            configFile = new File(pluginsDir, "bstats" + File.separator + "config.yml");
        }

        reload();

        if (config != null && config.isEnabled()) {
            metrics = new Metrics(KettingConstants.NAME.toLowerCase(), config.getServerUUID(), config.isLogErrorsEnabled());
            addCharts();
        }
    }

    private static void addCharts() {
        metrics.addCustomChart(new Metrics.SingleLineChart("players", () -> Bukkit.getOnlinePlayers().size()));
        metrics.addCustomChart(new Metrics.SimplePie("minecraft_version", () -> {
            String version = Bukkit.getVersion();
            version = version.substring(version.indexOf("MC: ") + 4, version.length() - 1);
            return version;
        }));
        metrics.addCustomChart(new Metrics.SimplePie("online_mode", () -> Bukkit.getOnlineMode() ?  "online": "offline"));
        metrics.addCustomChart(new Metrics.DrilldownPie("java_version", () -> {
            Map<String, Map<String, Integer>> map = new HashMap<>();
            Map<String, Integer> entry = new HashMap<>();
            entry.put(System.getProperty("java.version"), 1);
            map.put(System.getProperty("java.vendor"), entry);
            return map;
        }));
    }

    public static void reload() {
        try {
            config = new MetricsConfig(configFile, true);

            // Opted out after a reload -> disable
            if (metrics != null && !config.isEnabled())
                metrics.disable();
        } catch (IOException e) {
            LoggerFactory.getLogger(MetricManager.class).error("Failed to load metrics config", e);
        }
    }
}
