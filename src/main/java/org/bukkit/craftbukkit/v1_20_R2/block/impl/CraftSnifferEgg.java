package org.bukkit.craftbukkit.v1_20_R2.block.impl;

import net.minecraft.world.level.block.SnifferEggBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.bukkit.block.data.Hatchable;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;

public final class CraftSnifferEgg extends CraftBlockData implements Hatchable {

    private static final IntegerProperty HATCH = getInteger(SnifferEggBlock.class, "hatch");

    public CraftSnifferEgg() {}

    public CraftSnifferEgg(BlockState state) {
        super(state);
    }

    public int getHatch() {
        return (Integer) this.get(CraftSnifferEgg.HATCH);
    }

    public void setHatch(int hatch) {
        this.set((Property) CraftSnifferEgg.HATCH, (Comparable) hatch);
    }

    public int getMaximumHatch() {
        return getMax(CraftSnifferEgg.HATCH);
    }
}
