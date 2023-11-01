package org.bukkit.craftbukkit.v1_20_R2.generator;

import com.google.common.base.Preconditions;
import com.mojang.serialization.Codec;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.Climate;
import org.bukkit.block.Biome;
import org.bukkit.craftbukkit.v1_20_R2.block.CraftBiome;
import org.bukkit.generator.BiomeProvider;
import org.bukkit.generator.WorldInfo;

public class CustomWorldChunkManager extends BiomeSource {

    private final WorldInfo worldInfo;
    private final BiomeProvider biomeProvider;
    private final Registry registry;

    private static List biomeListToBiomeBaseList(List biomes, Registry registry) {
        ArrayList biomeBases = new ArrayList();
        Iterator iterator = biomes.iterator();

        while (iterator.hasNext()) {
            Biome biome = (Biome) iterator.next();

            Preconditions.checkArgument(biome != Biome.CUSTOM, "Cannot use the biome %s", biome);
            biomeBases.add(CraftBiome.bukkitToMinecraftHolder(biome));
        }

        return biomeBases;
    }

    public CustomWorldChunkManager(WorldInfo worldInfo, BiomeProvider biomeProvider, Registry registry) {
        this.worldInfo = worldInfo;
        this.biomeProvider = biomeProvider;
        this.registry = registry;
    }

    protected Codec codec() {
        throw new UnsupportedOperationException("Cannot serialize CustomWorldChunkManager");
    }

    public Holder getNoiseBiome(int x, int y, int z, Climate.Sampler sampler) {
        Biome biome = this.biomeProvider.getBiome(this.worldInfo, x << 2, y << 2, z << 2, CraftBiomeParameterPoint.createBiomeParameterPoint(sampler, sampler.sample(x, y, z)));

        Preconditions.checkArgument(biome != Biome.CUSTOM, "Cannot set the biome to %s", biome);
        return CraftBiome.bukkitToMinecraftHolder(biome);
    }

    protected Stream collectPossibleBiomes() {
        return biomeListToBiomeBaseList(this.biomeProvider.getBiomes(this.worldInfo), this.registry).stream();
    }
}
