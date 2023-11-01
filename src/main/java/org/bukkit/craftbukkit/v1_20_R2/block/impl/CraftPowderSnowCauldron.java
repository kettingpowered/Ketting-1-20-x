package org.bukkit.craftbukkit.v1_20_R2.block.impl;

import net.minecraft.world.level.block.PowderSnowCauldronBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.bukkit.block.data.Levelled;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;

public final class CraftPowderSnowCauldron extends CraftBlockData implements Levelled {

    private static final IntegerProperty LEVEL = getInteger(PowderSnowCauldronBlock.class, "level");

    public CraftPowderSnowCauldron() {}

    public CraftPowderSnowCauldron(BlockState state) {
        super(state);
    }

    public int getLevel() {
        return (Integer) this.get(CraftPowderSnowCauldron.LEVEL);
    }

    public void setLevel(int level) {
        this.set((Property) CraftPowderSnowCauldron.LEVEL, (Comparable) level);
    }

    public int getMaximumLevel() {
        return getMax(CraftPowderSnowCauldron.LEVEL);
    }
}
