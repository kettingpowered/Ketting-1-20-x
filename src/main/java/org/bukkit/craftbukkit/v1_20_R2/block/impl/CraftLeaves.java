package org.bukkit.craftbukkit.v1_20_R2.block.impl;

import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.block.data.type.Leaves;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;

public final class CraftLeaves extends CraftBlockData implements Leaves, Waterlogged {

    private static final IntegerProperty DISTANCE = getInteger(LeavesBlock.class, "distance");
    private static final BooleanProperty PERSISTENT = getBoolean(LeavesBlock.class, "persistent");
    private static final BooleanProperty WATERLOGGED = getBoolean(LeavesBlock.class, "waterlogged");

    public CraftLeaves() {}

    public CraftLeaves(BlockState state) {
        super(state);
    }

    public boolean isPersistent() {
        return (Boolean) this.get(CraftLeaves.PERSISTENT);
    }

    public void setPersistent(boolean persistent) {
        this.set((Property) CraftLeaves.PERSISTENT, (Comparable) persistent);
    }

    public int getDistance() {
        return (Integer) this.get(CraftLeaves.DISTANCE);
    }

    public void setDistance(int distance) {
        this.set((Property) CraftLeaves.DISTANCE, (Comparable) distance);
    }

    public boolean isWaterlogged() {
        return (Boolean) this.get(CraftLeaves.WATERLOGGED);
    }

    public void setWaterlogged(boolean waterlogged) {
        this.set((Property) CraftLeaves.WATERLOGGED, (Comparable) waterlogged);
    }
}
