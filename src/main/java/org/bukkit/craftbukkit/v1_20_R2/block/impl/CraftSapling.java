package org.bukkit.craftbukkit.v1_20_R2.block.impl;

import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.bukkit.block.data.type.Sapling;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;

public final class CraftSapling extends CraftBlockData implements Sapling {

    private static final IntegerProperty STAGE = getInteger(SaplingBlock.class, "stage");

    public CraftSapling() {}

    public CraftSapling(BlockState state) {
        super(state);
    }

    public int getStage() {
        return (Integer) this.get(CraftSapling.STAGE);
    }

    public void setStage(int stage) {
        this.set((Property) CraftSapling.STAGE, (Comparable) stage);
    }

    public int getMaximumStage() {
        return getMax(CraftSapling.STAGE);
    }
}
