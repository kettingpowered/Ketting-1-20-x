package org.bukkit.craftbukkit.v1_20_R2.block.impl;

import net.minecraft.world.level.block.CoralFanBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;

public final class CraftCoralFan extends CraftBlockData implements Waterlogged {

    private static final BooleanProperty WATERLOGGED = getBoolean(CoralFanBlock.class, "waterlogged");

    public CraftCoralFan() {}

    public CraftCoralFan(BlockState state) {
        super(state);
    }

    public boolean isWaterlogged() {
        return (Boolean) this.get(CraftCoralFan.WATERLOGGED);
    }

    public void setWaterlogged(boolean waterlogged) {
        this.set((Property) CraftCoralFan.WATERLOGGED, (Comparable) waterlogged);
    }
}
