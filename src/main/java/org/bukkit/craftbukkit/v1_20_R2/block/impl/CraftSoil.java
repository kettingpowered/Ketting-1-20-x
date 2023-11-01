package org.bukkit.craftbukkit.v1_20_R2.block.impl;

import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.bukkit.block.data.type.Farmland;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;

public final class CraftSoil extends CraftBlockData implements Farmland {

    private static final IntegerProperty MOISTURE = getInteger(FarmBlock.class, "moisture");

    public CraftSoil() {}

    public CraftSoil(BlockState state) {
        super(state);
    }

    public int getMoisture() {
        return (Integer) this.get(CraftSoil.MOISTURE);
    }

    public void setMoisture(int moisture) {
        this.set((Property) CraftSoil.MOISTURE, (Comparable) moisture);
    }

    public int getMaximumMoisture() {
        return getMax(CraftSoil.MOISTURE);
    }
}
