package org.bukkit.craftbukkit.v1_20_R2.block.impl;

import net.minecraft.world.level.block.BarrierBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;

public final class CraftBarrier extends CraftBlockData implements Waterlogged {

    private static final BooleanProperty WATERLOGGED = getBoolean(BarrierBlock.class, "waterlogged");

    public CraftBarrier() {}

    public CraftBarrier(BlockState state) {
        super(state);
    }

    public boolean isWaterlogged() {
        return (Boolean) this.get(CraftBarrier.WATERLOGGED);
    }

    public void setWaterlogged(boolean waterlogged) {
        this.set((Property) CraftBarrier.WATERLOGGED, (Comparable) waterlogged);
    }
}
