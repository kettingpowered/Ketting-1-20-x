package org.bukkit.craftbukkit.v1_20_R2.generator;

import com.google.common.base.Preconditions;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraft.world.level.chunk.ProtoChunk;
import org.bukkit.HeightMap;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.TreeType;
import org.bukkit.block.Biome;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.BlockData;
import org.bukkit.craftbukkit.v1_20_R2.CraftRegionAccessor;
import org.bukkit.craftbukkit.v1_20_R2.CraftWorld;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.generator.LimitedRegion;
import org.bukkit.util.BoundingBox;

public class CraftLimitedRegion extends CraftRegionAccessor implements LimitedRegion {

    private final WeakReference weakAccess;
    private final int centerChunkX;
    private final int centerChunkZ;
    private final int buffer = 16;
    private final BoundingBox region;
    boolean entitiesLoaded = false;
    private final List entities = new ArrayList();
    private final List outsideEntities = new ArrayList();

    public CraftLimitedRegion(WorldGenLevel access, ChunkPos center) {
        this.weakAccess = new WeakReference(access);
        this.centerChunkX = center.x;
        this.centerChunkZ = center.z;
        CraftWorld world = access.getMinecraftWorld().getWorld();
        int xCenter = this.centerChunkX << 4;
        int zCenter = this.centerChunkZ << 4;
        int xMin = xCenter - this.getBuffer();
        int zMin = zCenter - this.getBuffer();
        int xMax = xCenter + this.getBuffer() + 16;
        int zMax = zCenter + this.getBuffer() + 16;

        this.region = new BoundingBox((double) xMin, (double) world.getMinHeight(), (double) zMin, (double) xMax, (double) world.getMaxHeight(), (double) zMax);
    }

    public WorldGenLevel getHandle() {
        WorldGenLevel handle = (WorldGenLevel) this.weakAccess.get();

        Preconditions.checkState(handle != null, "GeneratorAccessSeed no longer present, are you using it in a different tick?");
        return handle;
    }

    public void loadEntities() {
        if (!this.entitiesLoaded) {
            WorldGenLevel access = this.getHandle();

            for (int x = -1; x <= 1; ++x) {
                for (int z = -1; z <= 1; ++z) {
                    ProtoChunk chunk = (ProtoChunk) access.getChunk(this.centerChunkX + x, this.centerChunkZ + z);
                    Iterator iterator = chunk.getEntities().iterator();

                    while (iterator.hasNext()) {
                        CompoundTag compound = (CompoundTag) iterator.next();

                        EntityType.loadEntityRecursive(compound, access.getMinecraftWorld(), (entityx) -> {
                            if (this.region.contains(entityx.getX(), entityx.getY(), entityx.getZ())) {
                                entityx.generation = true;
                                this.entities.add(entityx);
                            } else {
                                this.outsideEntities.add(entityx);
                            }

                            return entityx;
                        });
                    }
                }
            }

            this.entitiesLoaded = true;
        }
    }

    public void saveEntities() {
        WorldGenLevel access = this.getHandle();

        if (this.entitiesLoaded) {
            for (int x = -1; x <= 1; ++x) {
                for (int z = -1; z <= 1; ++z) {
                    ProtoChunk chunk = (ProtoChunk) access.getChunk(this.centerChunkX + x, this.centerChunkZ + z);

                    chunk.getEntities().clear();
                }
            }
        }

        Iterator iterator = this.entities.iterator();

        Entity entity;

        while (iterator.hasNext()) {
            entity = (Entity) iterator.next();
            if (entity.isAlive()) {
                Preconditions.checkState(this.region.contains(entity.getX(), entity.getY(), entity.getZ()), "Entity %s is not in the region", entity);
                access.addFreshEntity(entity);
            }
        }

        iterator = this.outsideEntities.iterator();

        while (iterator.hasNext()) {
            entity = (Entity) iterator.next();
            access.addFreshEntity(entity);
        }

    }

    public void breakLink() {
        this.weakAccess.clear();
    }

    public int getBuffer() {
        return 16;
    }

    public boolean isInRegion(Location location) {
        return this.region.contains(location.getX(), location.getY(), location.getZ());
    }

    public boolean isInRegion(int x, int y, int z) {
        return this.region.contains((double) x, (double) y, (double) z);
    }

    public List getTileEntities() {
        ArrayList blockStates = new ArrayList();

        for (int x = -1; x <= 1; ++x) {
            for (int z = -1; z <= 1; ++z) {
                ProtoChunk chunk = (ProtoChunk) this.getHandle().getChunk(this.centerChunkX + x, this.centerChunkZ + z);
                Iterator iterator = chunk.getBlockEntitiesPos().iterator();

                while (iterator.hasNext()) {
                    BlockPos position = (BlockPos) iterator.next();

                    blockStates.add(this.getBlockState(position.getX(), position.getY(), position.getZ()));
                }
            }
        }

        return blockStates;
    }

    public Biome getBiome(int x, int y, int z) {
        Preconditions.checkArgument(this.isInRegion(x, y, z), "Coordinates %s, %s, %s are not in the region", x, y, z);
        return super.getBiome(x, y, z);
    }

    public void setBiome(int x, int y, int z, Holder biomeBase) {
        Preconditions.checkArgument(this.isInRegion(x, y, z), "Coordinates %s, %s, %s are not in the region", x, y, z);
        ChunkAccess chunk = this.getHandle().getChunk(x >> 4, z >> 4, ChunkStatus.EMPTY);

        chunk.setBiome(x >> 2, y >> 2, z >> 2, biomeBase);
    }

    public BlockState getBlockState(int x, int y, int z) {
        Preconditions.checkArgument(this.isInRegion(x, y, z), "Coordinates %s, %s, %s are not in the region", x, y, z);
        return super.getBlockState(x, y, z);
    }

    public BlockData getBlockData(int x, int y, int z) {
        Preconditions.checkArgument(this.isInRegion(x, y, z), "Coordinates %s, %s, %s are not in the region", x, y, z);
        return super.getBlockData(x, y, z);
    }

    public Material getType(int x, int y, int z) {
        Preconditions.checkArgument(this.isInRegion(x, y, z), "Coordinates %s, %s, %s are not in the region", x, y, z);
        return super.getType(x, y, z);
    }

    public void setBlockData(int x, int y, int z, BlockData blockData) {
        Preconditions.checkArgument(this.isInRegion(x, y, z), "Coordinates %s, %s, %s are not in the region", x, y, z);
        super.setBlockData(x, y, z, blockData);
    }

    public int getHighestBlockYAt(int x, int z) {
        Preconditions.checkArgument(this.isInRegion(x, this.region.getCenter().getBlockY(), z), "Coordinates %s, %s are not in the region", x, z);
        return super.getHighestBlockYAt(x, z);
    }

    public int getHighestBlockYAt(Location location) {
        Preconditions.checkArgument(this.isInRegion(location), "Coordinates %s, %s, %s are not in the region", location.getBlockX(), location.getBlockY(), location.getBlockZ());
        return super.getHighestBlockYAt(location);
    }

    public int getHighestBlockYAt(int x, int z, HeightMap heightMap) {
        Preconditions.checkArgument(this.isInRegion(x, this.region.getCenter().getBlockY(), z), "Coordinates %s, %s are not in the region", x, z);
        return super.getHighestBlockYAt(x, z, heightMap);
    }

    public int getHighestBlockYAt(Location location, HeightMap heightMap) {
        Preconditions.checkArgument(this.isInRegion(location), "Coordinates %s, %s, %s are not in the region", location.getBlockX(), location.getBlockY(), location.getBlockZ());
        return super.getHighestBlockYAt(location, heightMap);
    }

    public boolean generateTree(Location location, Random random, TreeType treeType) {
        Preconditions.checkArgument(this.isInRegion(location), "Coordinates %s, %s, %s are not in the region", location.getBlockX(), location.getBlockY(), location.getBlockZ());
        return super.generateTree(location, random, treeType);
    }

    public boolean generateTree(Location location, Random random, TreeType treeType, Consumer consumer) {
        Preconditions.checkArgument(this.isInRegion(location), "Coordinates %s, %s, %s are not in the region", location.getBlockX(), location.getBlockY(), location.getBlockZ());
        return super.generateTree(location, random, treeType, consumer);
    }

    public Collection getNMSEntities() {
        this.loadEntities();
        return new ArrayList(this.entities);
    }

    public org.bukkit.entity.Entity spawn(Location location, Class clazz, Consumer function, SpawnReason reason) throws IllegalArgumentException {
        Preconditions.checkArgument(this.isInRegion(location), "Coordinates %s, %s, %s are not in the region", location.getBlockX(), location.getBlockY(), location.getBlockZ());
        return super.spawn(location, clazz, function, reason);
    }

    public void addEntityToWorld(Entity entity, SpawnReason reason) {
        this.entities.add(entity);
    }
}
