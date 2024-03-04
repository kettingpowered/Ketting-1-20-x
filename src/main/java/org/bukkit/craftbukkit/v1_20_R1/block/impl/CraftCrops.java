/**
 * Automatically generated file, changes will be lost.
 */
package org.bukkit.craftbukkit.v1_20_R1.block.impl;

import net.minecraft.world.level.block.CropBlock;
import org.bukkit.craftbukkit.v1_20_R1.block.data.CraftBlockData;

public final class CraftCrops extends CraftBlockData implements org.bukkit.block.data.Ageable {

    public CraftCrops() {
        super();
    }

    public CraftCrops(net.minecraft.world.level.block.state.BlockState state) {
        super(state);
    }

    // org.bukkit.craftbukkit.block.data.CraftAgeable

    private static final net.minecraft.world.level.block.state.properties.IntegerProperty AGE = getInteger(net.minecraft.world.level.block.CropBlock.class, "age");

    @Override
    public int getAge() {
        if (getState() != null && getState().getBlock() instanceof CropBlock crop)
            return getOrFallback(AGE, crop.getAgePropertyCB());
        else
            return get(AGE);
    }

    @Override
    public void setAge(int age) {
        if (getState() != null && getState().getBlock() instanceof CropBlock crop)
            setOrFallback(AGE, crop.getAgePropertyCB(), age);
        else
            set(AGE, age);
    }

    @Override
    public int getMaximumAge() {
        if (getState() != null && getState().getBlock() instanceof CropBlock crop)
            return crop.getMaxAge();
        else
            return getMax(AGE);
    }
}
