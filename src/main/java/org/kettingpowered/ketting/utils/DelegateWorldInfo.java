package org.kettingpowered.ketting.utils;

import com.mojang.serialization.Lifecycle;
import net.minecraft.core.BlockPos;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.Difficulty;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.LevelSettings;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.level.levelgen.WorldOptions;
import net.minecraft.world.level.storage.DerivedLevelData;
import net.minecraft.world.level.storage.PrimaryLevelData;
import net.minecraft.world.level.storage.ServerLevelData;
import net.minecraft.world.level.storage.WritableLevelData;
import net.minecraft.world.level.timers.TimerQueue;
import org.kettingpowered.ketting.core.Ketting;
import org.kettingpowered.ketting.craftbukkit.ITypedLevelData;

import java.util.UUID;

@SuppressWarnings("all")
public class DelegateWorldInfo extends PrimaryLevelData implements ITypedLevelData {

    private final DerivedLevelData derivedWorldInfo;

    public DelegateWorldInfo(LevelSettings p_251081_, WorldOptions p_251666_, SpecialWorldProperty p_252268_, Lifecycle p_251714_, DerivedLevelData derivedLevelData) {
        super(p_251081_, p_251666_, p_252268_, p_251714_);
        this.derivedWorldInfo = derivedLevelData;
    }

    public String getOriginalLevelName() {
        return derivedWorldInfo.getOriginalLevelName();
    }

    public net.minecraft.resources.ResourceKey<net.minecraft.world.level.dimension.LevelStem> getTypeKey() {
        return derivedWorldInfo.getTypeKey();
    }

    public void setTypeKey(net.minecraft.resources.ResourceKey<net.minecraft.world.level.dimension.LevelStem> typeKey) {
        derivedWorldInfo.setTypeKey(typeKey);
    }

    @Override
    public int getXSpawn() {
        return derivedWorldInfo.getXSpawn();
    }

    @Override
    public int getYSpawn() {
        return derivedWorldInfo.getYSpawn();
    }

    @Override
    public int getZSpawn() {
        return derivedWorldInfo.getZSpawn();
    }

    @Override
    public float getSpawnAngle() {
        return derivedWorldInfo.getSpawnAngle();
    }

    @Override
    public long getGameTime() {
        return derivedWorldInfo.getGameTime();
    }

    @Override
    public long getDayTime() {
        return derivedWorldInfo.getDayTime();
    }

    @Override
    public String getLevelName() {
        return derivedWorldInfo.getLevelName();
    }

    @Override
    public int getClearWeatherTime() {
        return derivedWorldInfo.getClearWeatherTime();
    }

    @Override
    public void setClearWeatherTime(int time) {
        derivedWorldInfo.setClearWeatherTime(time);
    }

    @Override
    public boolean isThundering() {
        return derivedWorldInfo.isThundering();
    }

    @Override
    public int getThunderTime() {
        return derivedWorldInfo.getThunderTime();
    }

    @Override
    public boolean isRaining() {
        return derivedWorldInfo.isRaining();
    }

    @Override
    public int getRainTime() {
        return derivedWorldInfo.getRainTime();
    }

    @Override
    public GameType getGameType() {
        return derivedWorldInfo.getGameType();
    }

    @Override
    public void setXSpawn(int x) {
        derivedWorldInfo.setXSpawn(x);
    }

    @Override
    public void setYSpawn(int y) {
        derivedWorldInfo.setYSpawn(y);
    }

    @Override
    public void setZSpawn(int z) {
        derivedWorldInfo.setZSpawn(z);
    }

    @Override
    public void setSpawnAngle(float angle) {
        derivedWorldInfo.setSpawnAngle(angle);
    }

    @Override
    public void setGameTime(long time) {
        derivedWorldInfo.setGameTime(time);
    }

    @Override
    public void setDayTime(long time) {
        derivedWorldInfo.setDayTime(time);
    }

    @Override
    public void setSpawn(BlockPos spawnPoint, float angle) {
        derivedWorldInfo.setSpawn(spawnPoint, angle);
    }

    @Override
    public void setThundering(boolean thunderingIn) {
        derivedWorldInfo.setThundering(thunderingIn);
    }

    @Override
    public void setThunderTime(int time) {
        derivedWorldInfo.setThunderTime(time);
    }

    @Override
    public void setRaining(boolean isRaining) {
        derivedWorldInfo.setRaining(isRaining);
    }

    @Override
    public void setRainTime(int time) {
        derivedWorldInfo.setRainTime(time);
    }

    @Override
    public void setGameType(GameType type) {
        derivedWorldInfo.setGameType(type);
    }

    @Override
    public boolean isHardcore() {
        return derivedWorldInfo.isHardcore();
    }

    @Override
    public boolean getAllowCommands() {
        return derivedWorldInfo.getAllowCommands();
    }

    @Override
    public boolean isInitialized() {
        return derivedWorldInfo.isInitialized();
    }

    @Override
    public void setInitialized(boolean initializedIn) {
        derivedWorldInfo.setInitialized(initializedIn);
    }

    @Override
    public GameRules getGameRules() {
        return derivedWorldInfo.getGameRules();
    }

    @Override
    public WorldBorder.Settings getWorldBorder() {
        return derivedWorldInfo.getWorldBorder();
    }

    @Override
    public void setWorldBorder(WorldBorder.Settings serializer) {
        derivedWorldInfo.setWorldBorder(serializer);
    }

    @Override
    public Difficulty getDifficulty() {
        return derivedWorldInfo.getDifficulty();
    }

    @Override
    public boolean isDifficultyLocked() {
        return derivedWorldInfo.isDifficultyLocked();
    }

    @Override
    public TimerQueue<MinecraftServer> getScheduledEvents() {
        return derivedWorldInfo.getScheduledEvents();
    }

    @Override
    public int getWanderingTraderSpawnDelay() {
        return derivedWorldInfo.getWanderingTraderSpawnDelay();
    }

    @Override
    public void setWanderingTraderSpawnDelay(int delay) {
        derivedWorldInfo.setWanderingTraderSpawnDelay(delay);
    }

    @Override
    public int getWanderingTraderSpawnChance() {
        return derivedWorldInfo.getWanderingTraderSpawnChance();
    }

    @Override
    public void setWanderingTraderSpawnChance(int chance) {
        derivedWorldInfo.setWanderingTraderSpawnChance(chance);
    }

    @Override
    public void setWanderingTraderId(UUID id) {
        derivedWorldInfo.setWanderingTraderId(id);
    }

    public static DelegateWorldInfo wrap(DerivedLevelData worldInfo) {
        return new DelegateWorldInfo(worldSettings(worldInfo), generatorSettings(worldInfo), specialWorldProperty(worldInfo), lifecycle(worldInfo), worldInfo);
    }
    
    public static ServerLevelData getServerLevelData(WritableLevelData levelData) {
        ServerLevelData serverLevelData = null;

        if (levelData instanceof org.kettingpowered.ketting.utils.DelegateWorldInfo delegate) {
            serverLevelData = delegate;
        }else if (levelData instanceof net.minecraft.world.level.storage.PrimaryLevelData primary) {
            serverLevelData = primary;
        } else if (levelData instanceof net.minecraft.world.level.storage.DerivedLevelData derived) {
            serverLevelData = org.kettingpowered.ketting.utils.DelegateWorldInfo.wrap(derived);
        } else if (levelData instanceof ServerLevelData serverlevelData1) {
            if (!levelData.getClass().getName().startsWith("net.mehvahdjukaar.moonlight.api")) //Moonlight Lib is fine
                org.kettingpowered.ketting.core.Ketting.LOGGER.warn("Could not wrap level data, this can cause some problems with bukkit", new UnsupportedOperationException());
            serverLevelData = serverlevelData1;
        } else {
            Ketting.LOGGER.error("Could not get a ServerLevelData from a WritableLevelData in Level constructor", new IllegalArgumentException("A WritableLevelData in the Level constructor wasn't a ServerLevelData"));
        }

        return serverLevelData;
    }

    private static LevelSettings worldSettings(ServerLevelData worldInfo) {
        if (worldInfo instanceof PrimaryLevelData data) {
            return data.getLevelSettings();
        } else {
            return worldSettings(((DerivedLevelData) worldInfo).getDelegate());
        }
    }

    private static WorldOptions generatorSettings(ServerLevelData worldInfo) {
        if (worldInfo instanceof PrimaryLevelData) {
            return ((PrimaryLevelData) worldInfo).worldGenOptions();
        } else {
            return generatorSettings(((DerivedLevelData) worldInfo).getDelegate());
        }
    }

    private static SpecialWorldProperty specialWorldProperty(ServerLevelData serverLevelData) {
        if (serverLevelData instanceof PrimaryLevelData) {
            return ((PrimaryLevelData) serverLevelData).isFlatWorld() ?
                    SpecialWorldProperty.FLAT : (
                    ((PrimaryLevelData) serverLevelData).isDebugWorld() ? SpecialWorldProperty.DEBUG : SpecialWorldProperty.NONE
            );
        } else {
            return specialWorldProperty(((DerivedLevelData) serverLevelData).getDelegate());
        }
    }

    private static Lifecycle lifecycle(ServerLevelData worldInfo) {
        if (worldInfo instanceof PrimaryLevelData data) {
            return data.worldGenSettingsLifecycle();
        } else {
            return lifecycle(((DerivedLevelData) worldInfo).getDelegate());
        }
    }
}
