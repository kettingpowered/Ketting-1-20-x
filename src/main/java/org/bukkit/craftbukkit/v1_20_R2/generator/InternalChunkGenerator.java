package org.bukkit.craftbukkit.v1_20_R2.generator;

import java.util.function.Function;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.chunk.ChunkGenerator;

public abstract class InternalChunkGenerator extends ChunkGenerator {

    public InternalChunkGenerator(BiomeSource worldchunkmanager, Function function) {
        super(worldchunkmanager, function);
    }
}
