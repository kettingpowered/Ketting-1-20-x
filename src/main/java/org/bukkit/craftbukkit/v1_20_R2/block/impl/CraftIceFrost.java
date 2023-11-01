package org.bukkit.craftbukkit.v1_20_R2.block.impl;

import net.minecraft.world.level.block.FrostedIceBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.bukkit.block.data.Ageable;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;

public final class CraftIceFrost extends CraftBlockData implements Ageable {

    private static final IntegerProperty AGE = getInteger(FrostedIceBlock.class, "age");

    public CraftIceFrost() {}

    public CraftIceFrost(BlockState state) {
        super(state);
    }

    public int getAge() {
        return (Integer) this.get(CraftIceFrost.AGE);
    }

    public void setAge(int age) {
        this.set((Property) CraftIceFrost.AGE, (Comparable) age);
    }

    public int getMaximumAge() {
        return getMax(CraftIceFrost.AGE);
    }
}
