package org.bukkit.craftbukkit.v1_20_R2.generator;

import java.util.UUID;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.storage.LevelStorageSource;
import net.minecraft.world.level.storage.PrimaryLevelData;
import net.minecraft.world.level.storage.ServerLevelData;
import org.bukkit.World.Environment;
import org.bukkit.craftbukkit.v1_20_R2.util.WorldUUID;
import org.bukkit.generator.WorldInfo;

public class CraftWorldInfo implements WorldInfo {

    private final String name;
    private final UUID uuid;
    private final Environment environment;
    private final long seed;
    private final int minHeight;
    private final int maxHeight;

    public CraftWorldInfo(ServerLevelData worldDataServer, LevelStorageSource.LevelStorageAccess session, Environment environment, DimensionType dimensionManager) {
        this.name = worldDataServer.getLevelName();
        this.uuid = WorldUUID.getUUID(session.levelDirectory.path().toFile());
        this.environment = environment;
        this.seed = ((PrimaryLevelData) worldDataServer).worldGenOptions().seed();
        this.minHeight = dimensionManager.minY();
        this.maxHeight = dimensionManager.minY() + dimensionManager.height();
    }

    public CraftWorldInfo(String name, UUID uuid, Environment environment, long seed, int minHeight, int maxHeight) {
        this.name = name;
        this.uuid = uuid;
        this.environment = environment;
        this.seed = seed;
        this.minHeight = minHeight;
        this.maxHeight = maxHeight;
    }

    public String getName() {
        return this.name;
    }

    public UUID getUID() {
        return this.uuid;
    }

    public Environment getEnvironment() {
        return this.environment;
    }

    public long getSeed() {
        return this.seed;
    }

    public int getMinHeight() {
        return this.minHeight;
    }

    public int getMaxHeight() {
        return this.maxHeight;
    }
}
