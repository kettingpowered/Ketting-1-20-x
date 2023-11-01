package org.bukkit.craftbukkit.v1_20_R2.block.impl;

import net.minecraft.world.level.block.ScaffoldingBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.block.data.type.Scaffolding;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;

public final class CraftScaffolding extends CraftBlockData implements Scaffolding, Waterlogged {

    private static final BooleanProperty BOTTOM = getBoolean(ScaffoldingBlock.class, "bottom");
    private static final IntegerProperty DISTANCE = getInteger(ScaffoldingBlock.class, "distance");
    private static final BooleanProperty WATERLOGGED = getBoolean(ScaffoldingBlock.class, "waterlogged");

    public CraftScaffolding() {}

    public CraftScaffolding(BlockState state) {
        super(state);
    }

    public boolean isBottom() {
        return (Boolean) this.get(CraftScaffolding.BOTTOM);
    }

    public void setBottom(boolean bottom) {
        this.set((Property) CraftScaffolding.BOTTOM, (Comparable) bottom);
    }

    public int getDistance() {
        return (Integer) this.get(CraftScaffolding.DISTANCE);
    }

    public void setDistance(int distance) {
        this.set((Property) CraftScaffolding.DISTANCE, (Comparable) distance);
    }

    public int getMaximumDistance() {
        return getMax(CraftScaffolding.DISTANCE);
    }

    public boolean isWaterlogged() {
        return (Boolean) this.get(CraftScaffolding.WATERLOGGED);
    }

    public void setWaterlogged(boolean waterlogged) {
        this.set((Property) CraftScaffolding.WATERLOGGED, (Comparable) waterlogged);
    }
}
