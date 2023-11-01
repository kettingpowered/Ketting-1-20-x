package org.bukkit.craftbukkit.v1_20_R2.block.impl;

import net.minecraft.world.level.block.LanternBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.bukkit.block.data.Hangable;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.block.data.type.Lantern;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;

public final class CraftLantern extends CraftBlockData implements Lantern, Hangable, Waterlogged {

    private static final BooleanProperty HANGING = getBoolean(LanternBlock.class, "hanging");
    private static final BooleanProperty WATERLOGGED = getBoolean(LanternBlock.class, "waterlogged");

    public CraftLantern() {}

    public CraftLantern(BlockState state) {
        super(state);
    }

    public boolean isHanging() {
        return (Boolean) this.get(CraftLantern.HANGING);
    }

    public void setHanging(boolean hanging) {
        this.set((Property) CraftLantern.HANGING, (Comparable) hanging);
    }

    public boolean isWaterlogged() {
        return (Boolean) this.get(CraftLantern.WATERLOGGED);
    }

    public void setWaterlogged(boolean waterlogged) {
        this.set((Property) CraftLantern.WATERLOGGED, (Comparable) waterlogged);
    }
}
