package org.bukkit.craftbukkit.v1_20_R2.block.impl;

import net.minecraft.world.level.block.MangroveLeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.block.data.type.Leaves;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;

public final class CraftMangroveLeaves extends CraftBlockData implements Leaves, Waterlogged {

    private static final IntegerProperty DISTANCE = getInteger(MangroveLeavesBlock.class, "distance");
    private static final BooleanProperty PERSISTENT = getBoolean(MangroveLeavesBlock.class, "persistent");
    private static final BooleanProperty WATERLOGGED = getBoolean(MangroveLeavesBlock.class, "waterlogged");

    public CraftMangroveLeaves() {}

    public CraftMangroveLeaves(BlockState state) {
        super(state);
    }

    public boolean isPersistent() {
        return (Boolean) this.get(CraftMangroveLeaves.PERSISTENT);
    }

    public void setPersistent(boolean persistent) {
        this.set((Property) CraftMangroveLeaves.PERSISTENT, (Comparable) persistent);
    }

    public int getDistance() {
        return (Integer) this.get(CraftMangroveLeaves.DISTANCE);
    }

    public void setDistance(int distance) {
        this.set((Property) CraftMangroveLeaves.DISTANCE, (Comparable) distance);
    }

    public boolean isWaterlogged() {
        return (Boolean) this.get(CraftMangroveLeaves.WATERLOGGED);
    }

    public void setWaterlogged(boolean waterlogged) {
        this.set((Property) CraftMangroveLeaves.WATERLOGGED, (Comparable) waterlogged);
    }
}
