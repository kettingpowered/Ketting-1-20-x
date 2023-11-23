package org.kettingpowered.ketting.inject;

import com.google.common.base.Preconditions;
import net.minecraft.resources.ResourceLocation;
import org.bukkit.NamespacedKey;
import org.jetbrains.annotations.NotNull;
import org.kettingpowered.ketting.config.KettingConfig;
import org.kettingpowered.ketting.core.Ketting;

import java.util.Locale;

public class ForgeInject {

    public static void debug(String message) {
        if (KettingConfig.getInstance().PRINT_INJECTIONS.getValue())
            Ketting.LOGGER.info(message);
        else
            Ketting.LOGGER.debug(message);
    }

    public static void debug(String message, Object... args) {
        if (KettingConfig.getInstance().PRINT_INJECTIONS.getValue())
            Ketting.LOGGER.info(message, args);
        else
            Ketting.LOGGER.debug(message, args);
    }

    public static void debugWarn(String message) {
        if (KettingConfig.getInstance().PRINT_INJECTIONS.getValue())
            Ketting.LOGGER.warn(message);
        else
            Ketting.LOGGER.debug("WARN - " + message);
    }

    public static void debugWarn(String message, Object... args) {
        if (KettingConfig.getInstance().PRINT_INJECTIONS.getValue())
            Ketting.LOGGER.warn(message, args);
        else
            Ketting.LOGGER.debug("WARN - " + message, args);
    }

    public static String standardize(@NotNull ResourceLocation location) {
        Preconditions.checkNotNull(location, "location");
        return (location.getNamespace().equals(NamespacedKey.MINECRAFT) ? location.getPath() : location.toString())
                .replace(':', '_')
                .replaceAll("\\s+", "_")
                .replaceAll("\\W", "")
                .toUpperCase(Locale.ENGLISH);
    }

    public static String standardizeLower(@NotNull ResourceLocation location) {
        return (location.getNamespace().equals(NamespacedKey.MINECRAFT) ? location.getPath() : location.toString())
                .replace(':', '_')
                .replaceAll("\\s+", "_")
                .replaceAll("\\W", "")
                .toLowerCase(Locale.ENGLISH);
    }
}
