package org.bukkit.craftbukkit.v1_20_R2.block.impl;

import net.minecraft.world.level.block.LightBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.bukkit.block.data.Levelled;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.block.data.type.Light;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;

public final class CraftLight extends CraftBlockData implements Light, Levelled, Waterlogged {

    private static final IntegerProperty LEVEL = getInteger(LightBlock.class, "level");
    private static final BooleanProperty WATERLOGGED = getBoolean(LightBlock.class, "waterlogged");

    public CraftLight() {}

    public CraftLight(BlockState state) {
        super(state);
    }

    public int getLevel() {
        return (Integer) this.get(CraftLight.LEVEL);
    }

    public void setLevel(int level) {
        this.set((Property) CraftLight.LEVEL, (Comparable) level);
    }

    public int getMaximumLevel() {
        return getMax(CraftLight.LEVEL);
    }

    public boolean isWaterlogged() {
        return (Boolean) this.get(CraftLight.WATERLOGGED);
    }

    public void setWaterlogged(boolean waterlogged) {
        this.set((Property) CraftLight.WATERLOGGED, (Comparable) waterlogged);
    }
}
