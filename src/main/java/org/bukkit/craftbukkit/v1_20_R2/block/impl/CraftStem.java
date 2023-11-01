package org.bukkit.craftbukkit.v1_20_R2.block.impl;

import net.minecraft.world.level.block.StemBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.bukkit.block.data.Ageable;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;

public final class CraftStem extends CraftBlockData implements Ageable {

    private static final IntegerProperty AGE = getInteger(StemBlock.class, "age");

    public CraftStem() {}

    public CraftStem(BlockState state) {
        super(state);
    }

    public int getAge() {
        return (Integer) this.get(CraftStem.AGE);
    }

    public void setAge(int age) {
        this.set((Property) CraftStem.AGE, (Comparable) age);
    }

    public int getMaximumAge() {
        return getMax(CraftStem.AGE);
    }
}
