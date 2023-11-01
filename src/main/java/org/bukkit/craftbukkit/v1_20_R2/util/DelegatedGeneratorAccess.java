package org.bukkit.craftbukkit.v1_20_R2.util;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ClipBlockStateContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.ColorResolver;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkSource;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.lighting.LevelLightEngine;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.storage.LevelData;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.ticks.LevelTickAccess;
import net.minecraft.world.ticks.ScheduledTick;
import net.minecraft.world.ticks.TickPriority;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

public abstract class DelegatedGeneratorAccess implements WorldGenLevel {

    private WorldGenLevel handle;

    public void setHandle(WorldGenLevel worldAccess) {
        this.handle = worldAccess;
    }

    public WorldGenLevel getHandle() {
        return this.handle;
    }

    public long getSeed() {
        return this.handle.getSeed();
    }

    public void setCurrentlyGenerating(Supplier arg0) {
        this.handle.setCurrentlyGenerating(arg0);
    }

    public boolean ensureCanWrite(BlockPos arg0) {
        return this.handle.ensureCanWrite(arg0);
    }

    public ServerLevel getLevel() {
        return this.handle.getLevel();
    }

    public void addFreshEntityWithPassengers(Entity arg0, SpawnReason arg1) {
        this.handle.addFreshEntityWithPassengers(arg0, arg1);
    }

    public void addFreshEntityWithPassengers(Entity arg0) {
        this.handle.addFreshEntityWithPassengers(arg0);
    }

    public ServerLevel getMinecraftWorld() {
        return this.handle.getMinecraftWorld();
    }

    public DifficultyInstance getCurrentDifficultyAt(BlockPos arg0) {
        return this.handle.getCurrentDifficultyAt(arg0);
    }

    public void neighborShapeChanged(Direction arg0, BlockState arg1, BlockPos arg2, BlockPos arg3, int arg4, int arg5) {
        this.handle.neighborShapeChanged(arg0, arg1, arg2, arg3, arg4, arg5);
    }

    public long dayTime() {
        return this.handle.dayTime();
    }

    public LevelData getLevelData() {
        return this.handle.getLevelData();
    }

    public boolean hasChunk(int arg0, int arg1) {
        return this.handle.hasChunk(arg0, arg1);
    }

    public ChunkSource getChunkSource() {
        return this.handle.getChunkSource();
    }

    public void scheduleTick(BlockPos arg0, Block arg1, int arg2, TickPriority arg3) {
        this.handle.scheduleTick(arg0, arg1, arg2, arg3);
    }

    public void scheduleTick(BlockPos arg0, Block arg1, int arg2) {
        this.handle.scheduleTick(arg0, arg1, arg2);
    }

    public void scheduleTick(BlockPos arg0, Fluid arg1, int arg2, TickPriority arg3) {
        this.handle.scheduleTick(arg0, arg1, arg2, arg3);
    }

    public void scheduleTick(BlockPos arg0, Fluid arg1, int arg2) {
        this.handle.scheduleTick(arg0, arg1, arg2);
    }

    public Difficulty getDifficulty() {
        return this.handle.getDifficulty();
    }

    public void blockUpdated(BlockPos arg0, Block arg1) {
        this.handle.blockUpdated(arg0, arg1);
    }

    public MinecraftServer getServer() {
        return this.handle.getServer();
    }

    public RandomSource getRandom() {
        return this.handle.getRandom();
    }

    public LevelTickAccess getBlockTicks() {
        return this.handle.getBlockTicks();
    }

    public long nextSubTickCount() {
        return this.handle.nextSubTickCount();
    }

    public ScheduledTick createTick(BlockPos arg0, Object arg1, int arg2) {
        return this.handle.createTick(arg0, arg1, arg2);
    }

    public ScheduledTick createTick(BlockPos arg0, Object arg1, int arg2, TickPriority arg3) {
        return this.handle.createTick(arg0, arg1, arg2, arg3);
    }

    public LevelTickAccess getFluidTicks() {
        return this.handle.getFluidTicks();
    }

    public void playSound(Player arg0, BlockPos arg1, SoundEvent arg2, SoundSource arg3) {
        this.handle.playSound(arg0, arg1, arg2, arg3);
    }

    public void playSound(Player arg0, BlockPos arg1, SoundEvent arg2, SoundSource arg3, float arg4, float arg5) {
        this.handle.playSound(arg0, arg1, arg2, arg3, arg4, arg5);
    }

    public void levelEvent(int arg0, BlockPos arg1, int arg2) {
        this.handle.levelEvent(arg0, arg1, arg2);
    }

    public void levelEvent(Player arg0, int arg1, BlockPos arg2, int arg3) {
        this.handle.levelEvent(arg0, arg1, arg2, arg3);
    }

    public void addParticle(ParticleOptions arg0, double arg1, double arg2, double arg3, double arg4, double arg5, double arg6) {
        this.handle.addParticle(arg0, arg1, arg2, arg3, arg4, arg5, arg6);
    }

    public void gameEvent(GameEvent arg0, Vec3 arg1, GameEvent.Context arg2) {
        this.handle.gameEvent(arg0, arg1, arg2);
    }

    public void gameEvent(GameEvent arg0, BlockPos arg1, GameEvent.Context arg2) {
        this.handle.gameEvent(arg0, arg1, arg2);
    }

    public void gameEvent(Entity arg0, GameEvent arg1, BlockPos arg2) {
        this.handle.gameEvent(arg0, arg1, arg2);
    }

    public void gameEvent(Entity arg0, GameEvent arg1, Vec3 arg2) {
        this.handle.gameEvent(arg0, arg1, arg2);
    }

    public List getEntityCollisions(Entity arg0, AABB arg1) {
        return this.handle.getEntityCollisions(arg0, arg1);
    }

    public Optional getBlockEntity(BlockPos arg0, BlockEntityType arg1) {
        return this.handle.getBlockEntity(arg0, arg1);
    }

    public BlockPos getHeightmapPos(Heightmap.Types arg0, BlockPos arg1) {
        return this.handle.getHeightmapPos(arg0, arg1);
    }

    public boolean isUnobstructed(Entity arg0, VoxelShape arg1) {
        return this.handle.isUnobstructed(arg0, arg1);
    }

    public boolean hasNearbyAlivePlayer(double arg0, double arg1, double arg2, double arg3) {
        return this.handle.hasNearbyAlivePlayer(arg0, arg1, arg2, arg3);
    }

    public List players() {
        return this.handle.players();
    }

    public List getEntities(Entity arg0, AABB arg1, Predicate arg2) {
        return this.handle.getEntities(arg0, arg1, arg2);
    }

    public List getEntities(EntityTypeTest arg0, AABB arg1, Predicate arg2) {
        return this.handle.getEntities(arg0, arg1, arg2);
    }

    public List getEntities(Entity arg0, AABB arg1) {
        return this.handle.getEntities(arg0, arg1);
    }

    public List getEntitiesOfClass(Class arg0, AABB arg1) {
        return this.handle.getEntitiesOfClass(arg0, arg1);
    }

    public List getEntitiesOfClass(Class arg0, AABB arg1, Predicate arg2) {
        return this.handle.getEntitiesOfClass(arg0, arg1, arg2);
    }

    public Player getNearestPlayer(TargetingConditions arg0, LivingEntity arg1, double arg2, double arg3, double arg4) {
        return this.handle.getNearestPlayer(arg0, arg1, arg2, arg3, arg4);
    }

    public Player getNearestPlayer(TargetingConditions arg0, double arg1, double arg2, double arg3) {
        return this.handle.getNearestPlayer(arg0, arg1, arg2, arg3);
    }

    public Player getNearestPlayer(Entity arg0, double arg1) {
        return this.handle.getNearestPlayer(arg0, arg1);
    }

    public Player getNearestPlayer(double arg0, double arg1, double arg2, double arg3, Predicate arg4) {
        return this.handle.getNearestPlayer(arg0, arg1, arg2, arg3, arg4);
    }

    public Player getNearestPlayer(double arg0, double arg1, double arg2, double arg3, boolean arg4) {
        return this.handle.getNearestPlayer(arg0, arg1, arg2, arg3, arg4);
    }

    public Player getNearestPlayer(TargetingConditions arg0, LivingEntity arg1) {
        return this.handle.getNearestPlayer(arg0, arg1);
    }

    public LivingEntity getNearestEntity(Class arg0, TargetingConditions arg1, LivingEntity arg2, double arg3, double arg4, double arg5, AABB arg6) {
        return this.handle.getNearestEntity(arg0, arg1, arg2, arg3, arg4, arg5, arg6);
    }

    public LivingEntity getNearestEntity(List arg0, TargetingConditions arg1, LivingEntity arg2, double arg3, double arg4, double arg5) {
        return this.handle.getNearestEntity(arg0, arg1, arg2, arg3, arg4, arg5);
    }

    public Player getPlayerByUUID(UUID arg0) {
        return this.handle.getPlayerByUUID(arg0);
    }

    public List getNearbyPlayers(TargetingConditions arg0, LivingEntity arg1, AABB arg2) {
        return this.handle.getNearbyPlayers(arg0, arg1, arg2);
    }

    public List getNearbyEntities(Class arg0, TargetingConditions arg1, LivingEntity arg2, AABB arg3) {
        return this.handle.getNearbyEntities(arg0, arg1, arg2, arg3);
    }

    /** @deprecated */
    @Deprecated
    public float getLightLevelDependentMagicValue(BlockPos arg0) {
        return this.handle.getLightLevelDependentMagicValue(arg0);
    }

    public BlockGetter getChunkForCollisions(int arg0, int arg1) {
        return this.handle.getChunkForCollisions(arg0, arg1);
    }

    public int getMaxLocalRawBrightness(BlockPos arg0) {
        return this.handle.getMaxLocalRawBrightness(arg0);
    }

    public int getMaxLocalRawBrightness(BlockPos arg0, int arg1) {
        return this.handle.getMaxLocalRawBrightness(arg0, arg1);
    }

    public boolean canSeeSkyFromBelowWater(BlockPos arg0) {
        return this.handle.canSeeSkyFromBelowWater(arg0);
    }

    public float getPathfindingCostFromLightLevels(BlockPos arg0) {
        return this.handle.getPathfindingCostFromLightLevels(arg0);
    }

    public Stream getBlockStatesIfLoaded(AABB arg0) {
        return this.handle.getBlockStatesIfLoaded(arg0);
    }

    public Holder getUncachedNoiseBiome(int arg0, int arg1, int arg2) {
        return this.handle.getUncachedNoiseBiome(arg0, arg1, arg2);
    }

    /** @deprecated */
    @Deprecated
    public int getSeaLevel() {
        return this.handle.getSeaLevel();
    }

    public boolean containsAnyLiquid(AABB arg0) {
        return this.handle.containsAnyLiquid(arg0);
    }

    public int getMinBuildHeight() {
        return this.handle.getMinBuildHeight();
    }

    public boolean isWaterAt(BlockPos arg0) {
        return this.handle.isWaterAt(arg0);
    }

    public boolean isEmptyBlock(BlockPos arg0) {
        return this.handle.isEmptyBlock(arg0);
    }

    public boolean isClientSide() {
        return this.handle.isClientSide();
    }

    public DimensionType dimensionType() {
        return this.handle.dimensionType();
    }

    public FeatureFlagSet enabledFeatures() {
        return this.handle.enabledFeatures();
    }

    /** @deprecated */
    @Deprecated
    public boolean hasChunkAt(int arg0, int arg1) {
        return this.handle.hasChunkAt(arg0, arg1);
    }

    /** @deprecated */
    @Deprecated
    public boolean hasChunkAt(BlockPos arg0) {
        return this.handle.hasChunkAt(arg0);
    }

    public HolderLookup holderLookup(ResourceKey arg0) {
        return this.handle.holderLookup(arg0);
    }

    public RegistryAccess registryAccess() {
        return this.handle.registryAccess();
    }

    public Holder getNoiseBiome(int arg0, int arg1, int arg2) {
        return this.handle.getNoiseBiome(arg0, arg1, arg2);
    }

    public int getBlockTint(BlockPos arg0, ColorResolver arg1) {
        return this.handle.getBlockTint(arg0, arg1);
    }

    /** @deprecated */
    @Deprecated
    public boolean hasChunksAt(BlockPos arg0, BlockPos arg1) {
        return this.handle.hasChunksAt(arg0, arg1);
    }

    /** @deprecated */
    @Deprecated
    public boolean hasChunksAt(int arg0, int arg1, int arg2, int arg3, int arg4, int arg5) {
        return this.handle.hasChunksAt(arg0, arg1, arg2, arg3, arg4, arg5);
    }

    /** @deprecated */
    @Deprecated
    public boolean hasChunksAt(int arg0, int arg1, int arg2, int arg3) {
        return this.handle.hasChunksAt(arg0, arg1, arg2, arg3);
    }

    public ChunkAccess getChunk(int arg0, int arg1, ChunkStatus arg2, boolean arg3) {
        return this.handle.getChunk(arg0, arg1, arg2, arg3);
    }

    public ChunkAccess getChunk(int arg0, int arg1, ChunkStatus arg2) {
        return this.handle.getChunk(arg0, arg1, arg2);
    }

    public ChunkAccess getChunk(BlockPos arg0) {
        return this.handle.getChunk(arg0);
    }

    public ChunkAccess getChunk(int arg0, int arg1) {
        return this.handle.getChunk(arg0, arg1);
    }

    public int getHeight(Heightmap.Types arg0, int arg1, int arg2) {
        return this.handle.getHeight(arg0, arg1, arg2);
    }

    public int getHeight() {
        return this.handle.getHeight();
    }

    public Holder getBiome(BlockPos arg0) {
        return this.handle.getBiome(arg0);
    }

    public int getSkyDarken() {
        return this.handle.getSkyDarken();
    }

    public BiomeManager getBiomeManager() {
        return this.handle.getBiomeManager();
    }

    public boolean canSeeSky(BlockPos arg0) {
        return this.handle.canSeeSky(arg0);
    }

    public int getRawBrightness(BlockPos arg0, int arg1) {
        return this.handle.getRawBrightness(arg0, arg1);
    }

    public LevelLightEngine getLightEngine() {
        return this.handle.getLightEngine();
    }

    public int getBrightness(LightLayer arg0, BlockPos arg1) {
        return this.handle.getBrightness(arg0, arg1);
    }

    public float getShade(Direction arg0, boolean arg1) {
        return this.handle.getShade(arg0, arg1);
    }

    public BlockEntity getBlockEntity(BlockPos arg0) {
        return this.handle.getBlockEntity(arg0);
    }

    public double getBlockFloorHeight(VoxelShape arg0, Supplier arg1) {
        return this.handle.getBlockFloorHeight(arg0, arg1);
    }

    public double getBlockFloorHeight(BlockPos arg0) {
        return this.handle.getBlockFloorHeight(arg0);
    }

    public BlockHitResult clipWithInteractionOverride(Vec3 arg0, Vec3 arg1, BlockPos arg2, VoxelShape arg3, BlockState arg4) {
        return this.handle.clipWithInteractionOverride(arg0, arg1, arg2, arg3, arg4);
    }

    public BlockState getBlockState(BlockPos arg0) {
        return this.handle.getBlockState(arg0);
    }

    public FluidState getFluidState(BlockPos arg0) {
        return this.handle.getFluidState(arg0);
    }

    public int getLightEmission(BlockPos arg0) {
        return this.handle.getLightEmission(arg0);
    }

    public BlockHitResult clip(ClipContext arg0) {
        return this.handle.clip(arg0);
    }

    public BlockHitResult clip(ClipContext arg0, BlockPos arg1) {
        return this.handle.clip(arg0, arg1);
    }

    public int getMaxLightLevel() {
        return this.handle.getMaxLightLevel();
    }

    public BlockHitResult isBlockInLine(ClipBlockStateContext arg0) {
        return this.handle.isBlockInLine(arg0);
    }

    public Stream getBlockStates(AABB arg0) {
        return this.handle.getBlockStates(arg0);
    }

    public boolean isOutsideBuildHeight(int arg0) {
        return this.handle.isOutsideBuildHeight(arg0);
    }

    public boolean isOutsideBuildHeight(BlockPos arg0) {
        return this.handle.isOutsideBuildHeight(arg0);
    }

    public int getSectionIndexFromSectionY(int arg0) {
        return this.handle.getSectionIndexFromSectionY(arg0);
    }

    public int getSectionYFromSectionIndex(int arg0) {
        return this.handle.getSectionYFromSectionIndex(arg0);
    }

    public int getMaxSection() {
        return this.handle.getMaxSection();
    }

    public int getMinSection() {
        return this.handle.getMinSection();
    }

    public int getSectionIndex(int arg0) {
        return this.handle.getSectionIndex(arg0);
    }

    public int getSectionsCount() {
        return this.handle.getSectionsCount();
    }

    public int getMaxBuildHeight() {
        return this.handle.getMaxBuildHeight();
    }

    public boolean isUnobstructed(BlockState arg0, BlockPos arg1, CollisionContext arg2) {
        return this.handle.isUnobstructed(arg0, arg1, arg2);
    }

    public boolean isUnobstructed(Entity arg0) {
        return this.handle.isUnobstructed(arg0);
    }

    public WorldBorder getWorldBorder() {
        return this.handle.getWorldBorder();
    }

    public Optional findFreePosition(Entity arg0, VoxelShape arg1, Vec3 arg2, double arg3, double arg4, double arg5) {
        return this.handle.findFreePosition(arg0, arg1, arg2, arg3, arg4, arg5);
    }

    public Iterable getCollisions(Entity arg0, AABB arg1) {
        return this.handle.getCollisions(arg0, arg1);
    }

    public Iterable getBlockCollisions(Entity arg0, AABB arg1) {
        return this.handle.getBlockCollisions(arg0, arg1);
    }

    public boolean noCollision(AABB arg0) {
        return this.handle.noCollision(arg0);
    }

    public boolean noCollision(Entity arg0) {
        return this.handle.noCollision(arg0);
    }

    public boolean noCollision(Entity arg0, AABB arg1) {
        return this.handle.noCollision(arg0, arg1);
    }

    public boolean collidesWithSuffocatingBlock(Entity arg0, AABB arg1) {
        return this.handle.collidesWithSuffocatingBlock(arg0, arg1);
    }

    public Optional findSupportingBlock(Entity arg0, AABB arg1) {
        return this.handle.findSupportingBlock(arg0, arg1);
    }

    public int getBestNeighborSignal(BlockPos arg0) {
        return this.handle.getBestNeighborSignal(arg0);
    }

    public int getControlInputSignal(BlockPos arg0, Direction arg1, boolean arg2) {
        return this.handle.getControlInputSignal(arg0, arg1, arg2);
    }

    public int getDirectSignal(BlockPos arg0, Direction arg1) {
        return this.handle.getDirectSignal(arg0, arg1);
    }

    public int getDirectSignalTo(BlockPos arg0) {
        return this.handle.getDirectSignalTo(arg0);
    }

    public boolean hasNeighborSignal(BlockPos arg0) {
        return this.handle.hasNeighborSignal(arg0);
    }

    public boolean hasSignal(BlockPos arg0, Direction arg1) {
        return this.handle.hasSignal(arg0, arg1);
    }

    public int getSignal(BlockPos arg0, Direction arg1) {
        return this.handle.getSignal(arg0, arg1);
    }

    public boolean isStateAtPosition(BlockPos arg0, Predicate arg1) {
        return this.handle.isStateAtPosition(arg0, arg1);
    }

    public boolean isFluidAtPosition(BlockPos arg0, Predicate arg1) {
        return this.handle.isFluidAtPosition(arg0, arg1);
    }

    public boolean addFreshEntity(Entity arg0, SpawnReason arg1) {
        return this.handle.addFreshEntity(arg0, arg1);
    }

    public boolean addFreshEntity(Entity arg0) {
        return this.handle.addFreshEntity(arg0);
    }

    public boolean removeBlock(BlockPos arg0, boolean arg1) {
        return this.handle.removeBlock(arg0, arg1);
    }

    public boolean destroyBlock(BlockPos arg0, boolean arg1, Entity arg2, int arg3) {
        return this.handle.destroyBlock(arg0, arg1, arg2, arg3);
    }

    public boolean destroyBlock(BlockPos arg0, boolean arg1, Entity arg2) {
        return this.handle.destroyBlock(arg0, arg1, arg2);
    }

    public boolean destroyBlock(BlockPos arg0, boolean arg1) {
        return this.handle.destroyBlock(arg0, arg1);
    }

    public boolean setBlock(BlockPos arg0, BlockState arg1, int arg2) {
        return this.handle.setBlock(arg0, arg1, arg2);
    }

    public boolean setBlock(BlockPos arg0, BlockState arg1, int arg2, int arg3) {
        return this.handle.setBlock(arg0, arg1, arg2, arg3);
    }

    public float getTimeOfDay(float arg0) {
        return this.handle.getTimeOfDay(arg0);
    }

    public float getMoonBrightness() {
        return this.handle.getMoonBrightness();
    }

    public int getMoonPhase() {
        return this.handle.getMoonPhase();
    }
}
