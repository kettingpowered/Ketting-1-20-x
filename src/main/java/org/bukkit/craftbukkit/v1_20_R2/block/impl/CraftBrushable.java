package org.bukkit.craftbukkit.v1_20_R2.block.impl;

import net.minecraft.world.level.block.BrushableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.bukkit.block.data.Brushable;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;

public final class CraftBrushable extends CraftBlockData implements Brushable {

    private static final IntegerProperty DUSTED = getInteger(BrushableBlock.class, "dusted");

    public CraftBrushable() {}

    public CraftBrushable(BlockState state) {
        super(state);
    }

    public int getDusted() {
        return (Integer) this.get(CraftBrushable.DUSTED);
    }

    public void setDusted(int dusted) {
        this.set((Property) CraftBrushable.DUSTED, (Comparable) dusted);
    }

    public int getMaximumDusted() {
        return getMax(CraftBrushable.DUSTED);
    }
}
