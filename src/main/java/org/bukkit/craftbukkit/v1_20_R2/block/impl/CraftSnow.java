package org.bukkit.craftbukkit.v1_20_R2.block.impl;

import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.bukkit.block.data.type.Snow;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;

public final class CraftSnow extends CraftBlockData implements Snow {

    private static final IntegerProperty LAYERS = getInteger(SnowLayerBlock.class, "layers");

    public CraftSnow() {}

    public CraftSnow(BlockState state) {
        super(state);
    }

    public int getLayers() {
        return (Integer) this.get(CraftSnow.LAYERS);
    }

    public void setLayers(int layers) {
        this.set((Property) CraftSnow.LAYERS, (Comparable) layers);
    }

    public int getMinimumLayers() {
        return getMin(CraftSnow.LAYERS);
    }

    public int getMaximumLayers() {
        return getMax(CraftSnow.LAYERS);
    }
}
