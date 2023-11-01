package org.bukkit.craftbukkit.v1_20_R2.block.impl;

import net.minecraft.world.level.block.ConduitBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;

public final class CraftConduit extends CraftBlockData implements Waterlogged {

    private static final BooleanProperty WATERLOGGED = getBoolean(ConduitBlock.class, "waterlogged");

    public CraftConduit() {}

    public CraftConduit(BlockState state) {
        super(state);
    }

    public boolean isWaterlogged() {
        return (Boolean) this.get(CraftConduit.WATERLOGGED);
    }

    public void setWaterlogged(boolean waterlogged) {
        this.set((Property) CraftConduit.WATERLOGGED, (Comparable) waterlogged);
    }
}
