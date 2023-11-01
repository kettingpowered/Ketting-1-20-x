package org.bukkit.craftbukkit.v1_20_R2.generator;

import com.google.common.base.Preconditions;
import java.lang.ref.WeakReference;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.data.BlockData;
import org.bukkit.craftbukkit.v1_20_R2.block.CraftBiome;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;
import org.bukkit.craftbukkit.v1_20_R2.util.CraftMagicNumbers;
import org.bukkit.generator.ChunkGenerator.ChunkData;
import org.bukkit.material.MaterialData;

public final class CraftChunkData implements ChunkData {

    private final int maxHeight;
    private final int minHeight;
    private final WeakReference weakChunk;

    public CraftChunkData(World world, ChunkAccess chunkAccess) {
        this(world.getMaxHeight(), world.getMinHeight(), chunkAccess);
    }

    CraftChunkData(int maxHeight, int minHeight, ChunkAccess chunkAccess) {
        this.maxHeight = maxHeight;
        this.minHeight = minHeight;
        this.weakChunk = new WeakReference(chunkAccess);
    }

    public ChunkAccess getHandle() {
        ChunkAccess access = (ChunkAccess) this.weakChunk.get();

        Preconditions.checkState(access != null, "IChunkAccess no longer present, are you using it in a different tick?");
        return access;
    }

    public void breakLink() {
        this.weakChunk.clear();
    }

    public int getMaxHeight() {
        return this.maxHeight;
    }

    public int getMinHeight() {
        return this.minHeight;
    }

    public Biome getBiome(int x, int y, int z) {
        return CraftBiome.minecraftHolderToBukkit(this.getHandle().getNoiseBiome(x >> 2, y >> 2, z >> 2));
    }

    public void setBlock(int x, int y, int z, Material material) {
        this.setBlock(x, y, z, material.createBlockData());
    }

    public void setBlock(int x, int y, int z, MaterialData material) {
        this.setBlock(x, y, z, CraftMagicNumbers.getBlock(material));
    }

    public void setBlock(int x, int y, int z, BlockData blockData) {
        this.setBlock(x, y, z, ((CraftBlockData) blockData).getState());
    }

    public void setRegion(int xMin, int yMin, int zMin, int xMax, int yMax, int zMax, Material material) {
        this.setRegion(xMin, yMin, zMin, xMax, yMax, zMax, material.createBlockData());
    }

    public void setRegion(int xMin, int yMin, int zMin, int xMax, int yMax, int zMax, MaterialData material) {
        this.setRegion(xMin, yMin, zMin, xMax, yMax, zMax, CraftMagicNumbers.getBlock(material));
    }

    public void setRegion(int xMin, int yMin, int zMin, int xMax, int yMax, int zMax, BlockData blockData) {
        this.setRegion(xMin, yMin, zMin, xMax, yMax, zMax, ((CraftBlockData) blockData).getState());
    }

    public Material getType(int x, int y, int z) {
        return CraftMagicNumbers.getMaterial(this.getTypeId(x, y, z).getBlock());
    }

    public MaterialData getTypeAndData(int x, int y, int z) {
        return CraftMagicNumbers.getMaterial(this.getTypeId(x, y, z));
    }

    public BlockData getBlockData(int x, int y, int z) {
        return CraftBlockData.fromData(this.getTypeId(x, y, z));
    }

    public void setRegion(int xMin, int yMin, int zMin, int xMax, int yMax, int zMax, BlockState type) {
        if (xMin <= 15 && yMin < this.maxHeight && zMin <= 15) {
            if (xMin < 0) {
                xMin = 0;
            }

            if (yMin < this.minHeight) {
                yMin = this.minHeight;
            }

            if (zMin < 0) {
                zMin = 0;
            }

            if (xMax > 16) {
                xMax = 16;
            }

            if (yMax > this.maxHeight) {
                yMax = this.maxHeight;
            }

            if (zMax > 16) {
                zMax = 16;
            }

            if (xMin < xMax && yMin < yMax && zMin < zMax) {
                for (int y = yMin; y < yMax; ++y) {
                    for (int x = xMin; x < xMax; ++x) {
                        for (int z = zMin; z < zMax; ++z) {
                            this.setBlock(x, y, z, type);
                        }
                    }
                }

            }
        }
    }

    public BlockState getTypeId(int x, int y, int z) {
        if (x == (x & 15) && y >= this.minHeight && y < this.maxHeight && z == (z & 15)) {
            ChunkAccess access = this.getHandle();

            return access.getBlockState(new BlockPos(access.getPos().getMinBlockX() + x, y, access.getPos().getMinBlockZ() + z));
        } else {
            return Blocks.AIR.defaultBlockState();
        }
    }

    public byte getData(int x, int y, int z) {
        return CraftMagicNumbers.toLegacyData(this.getTypeId(x, y, z));
    }

    private void setBlock(int x, int y, int z, BlockState type) {
        if (x == (x & 15) && y >= this.minHeight && y < this.maxHeight && z == (z & 15)) {
            ChunkAccess access = this.getHandle();
            BlockPos blockPosition = new BlockPos(access.getPos().getMinBlockX() + x, y, access.getPos().getMinBlockZ() + z);
            BlockState oldBlockData = access.setBlockState(blockPosition, type, false);

            if (type.hasBlockEntity()) {
                BlockEntity tileEntity = ((EntityBlock) type.getBlock()).newBlockEntity(blockPosition, type);

                if (tileEntity == null) {
                    access.removeBlockEntity(blockPosition);
                } else {
                    access.setBlockEntity(tileEntity);
                }
            } else if (oldBlockData != null && oldBlockData.hasBlockEntity()) {
                access.removeBlockEntity(blockPosition);
            }

        }
    }
}
