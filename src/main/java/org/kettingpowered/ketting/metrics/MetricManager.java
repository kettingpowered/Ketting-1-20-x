package org.kettingpowered.ketting.metrics;

import org.kettingpowered.ketting.core.Ketting;
import org.kettingpowered.ketting.internal.KettingConstants;
import org.kettingpowered.ketting.metrics.config.MetricsConfig;
import org.kettingpowered.ketting.utils.JsonObjectBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

public final class MetricManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(MetricManager.class);
    private static final File configFile = new File("plugins/bstats/config.yml");

    private static MetricsBase metrics;
    private static MetricsConfig config;

    public static void init() {
        if (true) return; //Wait until we actually are a registered server implementation

        reload();

        if (config != null) {
            metrics = new MetricsBase(
                    KettingConstants.NAME.toLowerCase(),
                    config.getServerUUID(),
                    20462,
                    config.isEnabled(),
                    MetricManager::getPlatformData,
                    MetricManager::getServiceData,
                    null,
                    () -> config.isEnabled(),
                    (error, thr) -> LOGGER.warn("{}", error, thr),
                    info -> LOGGER.info("{}", info),
                    config.isLogErrorsEnabled(),
                    config.isLogSentDataEnabled(),
                    true
            );
        }
    }

    private static void getPlatformData(JsonObjectBuilder data) {
        if (config == null)
            return;

        data.appendField("serverUUID", config.getServerUUID());
        data.appendField("osName", System.getProperty("os.name"));
        data.appendField("osArch", System.getProperty("os.arch"));
        data.appendField("osVersion", System.getProperty("os.version"));
        data.appendField("coreCount", Runtime.getRuntime().availableProcessors());
    }

    private static void getServiceData(JsonObjectBuilder builder) {
        if (config == null)
            return;

        builder.appendField("version", KettingConstants.BUKKIT_VERSION);
    }

    public static void reload() {
        try {
            config = new MetricsConfig(configFile, true);
        } catch (IOException e) {
            LOGGER.error("Failed to load metrics config", e);
        }
    }

    public static void shutdown() {
        if (metrics != null)
            metrics.shutdown();
    }
}
