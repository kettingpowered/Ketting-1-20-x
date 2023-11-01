package org.bukkit.craftbukkit.v1_20_R2.block.data.type;

import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.bukkit.block.data.type.SeaPickle;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;

public abstract class CraftSeaPickle extends CraftBlockData implements SeaPickle {

    private static final IntegerProperty PICKLES = getInteger("pickles");

    public int getPickles() {
        return (Integer) this.get(CraftSeaPickle.PICKLES);
    }

    public void setPickles(int pickles) {
        this.set((Property) CraftSeaPickle.PICKLES, (Comparable) pickles);
    }

    public int getMinimumPickles() {
        return getMin(CraftSeaPickle.PICKLES);
    }

    public int getMaximumPickles() {
        return getMax(CraftSeaPickle.PICKLES);
    }
}
