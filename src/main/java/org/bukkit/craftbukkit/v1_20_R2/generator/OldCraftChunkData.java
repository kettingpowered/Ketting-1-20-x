package org.bukkit.craftbukkit.v1_20_R2.generator;

import java.util.HashSet;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunkSection;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.block.data.BlockData;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;
import org.bukkit.craftbukkit.v1_20_R2.util.CraftMagicNumbers;
import org.bukkit.generator.ChunkGenerator.ChunkData;
import org.bukkit.material.MaterialData;

/** @deprecated */
@Deprecated
public final class OldCraftChunkData implements ChunkData {

    private final int minHeight;
    private final int maxHeight;
    private final LevelChunkSection[] sections;
    private final Registry biomes;
    private Set tiles;
    private final Set lights = new HashSet();

    public OldCraftChunkData(int minHeight, int maxHeight, Registry biomes) {
        this.minHeight = minHeight;
        this.maxHeight = maxHeight;
        this.biomes = biomes;
        this.sections = new LevelChunkSection[(maxHeight - 1 >> 4) + 1 - (minHeight >> 4)];
    }

    public int getMinHeight() {
        return this.minHeight;
    }

    public int getMaxHeight() {
        return this.maxHeight;
    }

    public Biome getBiome(int x, int y, int z) {
        throw new UnsupportedOperationException("Unsupported, in older chunk generator api");
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
                    LevelChunkSection section = this.getChunkSection(y, true);
                    int offsetBase = y & 15;

                    for (int x = xMin; x < xMax; ++x) {
                        for (int z = zMin; z < zMax; ++z) {
                            section.setBlockState(x, offsetBase, z, type);
                        }
                    }
                }

            }
        }
    }

    public BlockState getTypeId(int x, int y, int z) {
        if (x == (x & 15) && y >= this.minHeight && y < this.maxHeight && z == (z & 15)) {
            LevelChunkSection section = this.getChunkSection(y, false);

            return section == null ? Blocks.AIR.defaultBlockState() : section.getBlockState(x, y & 15, z);
        } else {
            return Blocks.AIR.defaultBlockState();
        }
    }

    public byte getData(int x, int y, int z) {
        return CraftMagicNumbers.toLegacyData(this.getTypeId(x, y, z));
    }

    private void setBlock(int x, int y, int z, BlockState type) {
        if (x == (x & 15) && y >= this.minHeight && y < this.maxHeight && z == (z & 15)) {
            LevelChunkSection section = this.getChunkSection(y, true);

            section.setBlockState(x, y & 15, z, type);
            if (type.getLightEmission() > 0) {
                this.lights.add(new BlockPos(x, y, z));
            } else {
                this.lights.remove(new BlockPos(x, y, z));
            }

            if (type.hasBlockEntity()) {
                if (this.tiles == null) {
                    this.tiles = new HashSet();
                }

                this.tiles.add(new BlockPos(x, y, z));
            }

        }
    }

    private LevelChunkSection getChunkSection(int y, boolean create) {
        int offset = y - this.minHeight >> 4;
        LevelChunkSection section = this.sections[offset];

        if (create && section == null) {
            this.sections[offset] = section = new LevelChunkSection(this.biomes);
        }

        return section;
    }

    LevelChunkSection[] getRawChunkData() {
        return this.sections;
    }

    Set getTiles() {
        return this.tiles;
    }

    Set getLights() {
        return this.lights;
    }
}
