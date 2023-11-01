package org.bukkit.craftbukkit.v1_20_R2.block.impl;

import net.minecraft.world.level.block.CherryLeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.block.data.type.Leaves;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;

public final class CraftCherryLeaves extends CraftBlockData implements Leaves, Waterlogged {

    private static final IntegerProperty DISTANCE = getInteger(CherryLeavesBlock.class, "distance");
    private static final BooleanProperty PERSISTENT = getBoolean(CherryLeavesBlock.class, "persistent");
    private static final BooleanProperty WATERLOGGED = getBoolean(CherryLeavesBlock.class, "waterlogged");

    public CraftCherryLeaves() {}

    public CraftCherryLeaves(BlockState state) {
        super(state);
    }

    public boolean isPersistent() {
        return (Boolean) this.get(CraftCherryLeaves.PERSISTENT);
    }

    public void setPersistent(boolean persistent) {
        this.set((Property) CraftCherryLeaves.PERSISTENT, (Comparable) persistent);
    }

    public int getDistance() {
        return (Integer) this.get(CraftCherryLeaves.DISTANCE);
    }

    public void setDistance(int distance) {
        this.set((Property) CraftCherryLeaves.DISTANCE, (Comparable) distance);
    }

    public boolean isWaterlogged() {
        return (Boolean) this.get(CraftCherryLeaves.WATERLOGGED);
    }

    public void setWaterlogged(boolean waterlogged) {
        this.set((Property) CraftCherryLeaves.WATERLOGGED, (Comparable) waterlogged);
    }
}
