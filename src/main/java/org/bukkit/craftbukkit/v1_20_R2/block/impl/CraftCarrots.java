package org.bukkit.craftbukkit.v1_20_R2.block.impl;

import net.minecraft.world.level.block.CarrotBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.bukkit.block.data.Ageable;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;

public final class CraftCarrots extends CraftBlockData implements Ageable {

    private static final IntegerProperty AGE = getInteger(CarrotBlock.class, "age");

    public CraftCarrots() {}

    public CraftCarrots(BlockState state) {
        super(state);
    }

    public int getAge() {
        return (Integer) this.get(CraftCarrots.AGE);
    }

    public void setAge(int age) {
        this.set((Property) CraftCarrots.AGE, (Comparable) age);
    }

    public int getMaximumAge() {
        return getMax(CraftCarrots.AGE);
    }
}
