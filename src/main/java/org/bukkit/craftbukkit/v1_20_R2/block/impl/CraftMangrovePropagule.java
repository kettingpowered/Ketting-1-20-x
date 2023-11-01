package org.bukkit.craftbukkit.v1_20_R2.block.impl;

import net.minecraft.world.level.block.MangrovePropaguleBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.Hangable;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.block.data.type.MangrovePropagule;
import org.bukkit.block.data.type.Sapling;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;

public final class CraftMangrovePropagule extends CraftBlockData implements MangrovePropagule, Ageable, Hangable, Sapling, Waterlogged {

    private static final IntegerProperty AGE = getInteger(MangrovePropaguleBlock.class, "age");
    private static final BooleanProperty HANGING = getBoolean(MangrovePropaguleBlock.class, "hanging");
    private static final IntegerProperty STAGE = getInteger(MangrovePropaguleBlock.class, "stage");
    private static final BooleanProperty WATERLOGGED = getBoolean(MangrovePropaguleBlock.class, "waterlogged");

    public CraftMangrovePropagule() {}

    public CraftMangrovePropagule(BlockState state) {
        super(state);
    }

    public int getAge() {
        return (Integer) this.get(CraftMangrovePropagule.AGE);
    }

    public void setAge(int age) {
        this.set((Property) CraftMangrovePropagule.AGE, (Comparable) age);
    }

    public int getMaximumAge() {
        return getMax(CraftMangrovePropagule.AGE);
    }

    public boolean isHanging() {
        return (Boolean) this.get(CraftMangrovePropagule.HANGING);
    }

    public void setHanging(boolean hanging) {
        this.set((Property) CraftMangrovePropagule.HANGING, (Comparable) hanging);
    }

    public int getStage() {
        return (Integer) this.get(CraftMangrovePropagule.STAGE);
    }

    public void setStage(int stage) {
        this.set((Property) CraftMangrovePropagule.STAGE, (Comparable) stage);
    }

    public int getMaximumStage() {
        return getMax(CraftMangrovePropagule.STAGE);
    }

    public boolean isWaterlogged() {
        return (Boolean) this.get(CraftMangrovePropagule.WATERLOGGED);
    }

    public void setWaterlogged(boolean waterlogged) {
        this.set((Property) CraftMangrovePropagule.WATERLOGGED, (Comparable) waterlogged);
    }
}
