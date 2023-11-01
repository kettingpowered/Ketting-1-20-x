package org.bukkit.craftbukkit.v1_20_R2.block.impl;

import net.minecraft.world.level.block.PitcherCropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.Bisected;
import org.bukkit.block.data.Bisected.Half;
import org.bukkit.block.data.type.PitcherCrop;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;

public final class CraftPitcherCrop extends CraftBlockData implements PitcherCrop, Ageable, Bisected {

    private static final IntegerProperty AGE = getInteger(PitcherCropBlock.class, "age");
    private static final EnumProperty HALF = getEnum(PitcherCropBlock.class, "half");

    public CraftPitcherCrop() {}

    public CraftPitcherCrop(BlockState state) {
        super(state);
    }

    public int getAge() {
        return (Integer) this.get(CraftPitcherCrop.AGE);
    }

    public void setAge(int age) {
        this.set((Property) CraftPitcherCrop.AGE, (Comparable) age);
    }

    public int getMaximumAge() {
        return getMax(CraftPitcherCrop.AGE);
    }

    public Half getHalf() {
        return (Half) this.get(CraftPitcherCrop.HALF, Half.class);
    }

    public void setHalf(Half half) {
        this.set(CraftPitcherCrop.HALF, (Enum) half);
    }
}
