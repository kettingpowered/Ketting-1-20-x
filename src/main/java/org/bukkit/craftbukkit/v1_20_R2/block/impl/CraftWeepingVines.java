package org.bukkit.craftbukkit.v1_20_R2.block.impl;

import net.minecraft.world.level.block.WeepingVinesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.bukkit.block.data.Ageable;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;

public final class CraftWeepingVines extends CraftBlockData implements Ageable {

    private static final IntegerProperty AGE = getInteger(WeepingVinesBlock.class, "age");

    public CraftWeepingVines() {}

    public CraftWeepingVines(BlockState state) {
        super(state);
    }

    public int getAge() {
        return (Integer) this.get(CraftWeepingVines.AGE);
    }

    public void setAge(int age) {
        this.set((Property) CraftWeepingVines.AGE, (Comparable) age);
    }

    public int getMaximumAge() {
        return getMax(CraftWeepingVines.AGE);
    }
}
