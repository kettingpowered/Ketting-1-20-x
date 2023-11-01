package org.bukkit.craftbukkit.v1_20_R2.block.impl;

import net.minecraft.world.level.block.TargetBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.bukkit.block.data.AnaloguePowerable;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;

public final class CraftTarget extends CraftBlockData implements AnaloguePowerable {

    private static final IntegerProperty POWER = getInteger(TargetBlock.class, "power");

    public CraftTarget() {}

    public CraftTarget(BlockState state) {
        super(state);
    }

    public int getPower() {
        return (Integer) this.get(CraftTarget.POWER);
    }

    public void setPower(int power) {
        this.set((Property) CraftTarget.POWER, (Comparable) power);
    }

    public int getMaximumPower() {
        return getMax(CraftTarget.POWER);
    }
}
