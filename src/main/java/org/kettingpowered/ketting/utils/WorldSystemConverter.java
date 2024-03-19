package org.kettingpowered.ketting.utils;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.LevelStorageSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;

public final class WorldSystemConverter {

    private static final Logger LOGGER = LoggerFactory.getLogger(WorldSystemConverter.class);

    public static boolean needsConversion(LevelStorageSource.LevelStorageAccess oldSource, ResourceKey<Level> key) {
        boolean ldExists = Files.exists(oldSource.levelDirectory.path(), LinkOption.NOFOLLOW_LINKS);
        if (!ldExists) //Should always be true if the session lock exists, but just in case
            return false;

        boolean dimExists = Files.exists(oldSource.getDimensionPath(key), LinkOption.NOFOLLOW_LINKS);
        if (!dimExists) deleteOldWorld(oldSource);

        return dimExists;
    }

    public static void convert(LevelStorageSource.LevelStorageAccess oldSource, LevelStorageSource.LevelStorageAccess newSource, ResourceKey<Level> key) {
        LOGGER.warn("Converting world from old system to new system");

        final Path oldSrc = oldSource.getDimensionPath(key);
        final Path newSrc = newSource.getDimensionPath(key);

        LOGGER.debug("Old source: " + oldSrc);
        LOGGER.debug("New source: " + newSrc);

        if (exists(newSource.levelDirectory.path()) && exists(newSrc)) {
            LOGGER.warn("A dimension at '{}' already exists, aborting conversion", newSrc);
            return;
        }

        try {
            if (!exists(newSrc.getParent()))
                Files.createDirectories(newSrc.getParent());

            Files.move(oldSrc, newSrc);
        } catch (IOException e) {
            LOGGER.error("Failed to move old world to new world", e);
            return;
        }

        deleteOldWorld(oldSource);
        LOGGER.info("World conversion complete");
    }

    private static boolean exists(Path path) {
        return Files.exists(path, LinkOption.NOFOLLOW_LINKS);
    }

    private static void deleteOldWorld(LevelStorageSource.LevelStorageAccess oldSource) {
        final String worldName = oldSource.getLevelId();
        try {
            LOGGER.debug("Deleting old world '{}'", worldName);
            oldSource.deleteLevelSilent();
        } catch (IOException e) {
            LOGGER.error("Failed to delete old world, this error can be ignored... just manually delete '{}' in your server folder", worldName, e);
        }
    }
}
