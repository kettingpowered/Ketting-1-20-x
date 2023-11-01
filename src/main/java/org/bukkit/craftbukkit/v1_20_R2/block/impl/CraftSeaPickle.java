package org.bukkit.craftbukkit.v1_20_R2.block.impl;

import net.minecraft.world.level.block.SeaPickleBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.block.data.type.SeaPickle;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;

public final class CraftSeaPickle extends CraftBlockData implements SeaPickle, Waterlogged {

    private static final IntegerProperty PICKLES = getInteger(SeaPickleBlock.class, "pickles");
    private static final BooleanProperty WATERLOGGED = getBoolean(SeaPickleBlock.class, "waterlogged");

    public CraftSeaPickle() {}

    public CraftSeaPickle(BlockState state) {
        super(state);
    }

    public int getPickles() {
        return (Integer) this.get(CraftSeaPickle.PICKLES);
    }

    public void setPickles(int pickles) {
        this.set((Property) CraftSeaPickle.PICKLES, (Comparable) pickles);
    }

    public int getMinimumPickles() {
        return getMin(CraftSeaPickle.PICKLES);
    }

    public int getMaximumPickles() {
        return getMax(CraftSeaPickle.PICKLES);
    }

    public boolean isWaterlogged() {
        return (Boolean) this.get(CraftSeaPickle.WATERLOGGED);
    }

    public void setWaterlogged(boolean waterlogged) {
        this.set((Property) CraftSeaPickle.WATERLOGGED, (Comparable) waterlogged);
    }
}
