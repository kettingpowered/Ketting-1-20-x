package org.bukkit.craftbukkit.v1_20_R2.block.data;

import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.bukkit.block.data.Hatchable;

public abstract class CraftHatchable extends CraftBlockData implements Hatchable {

    private static final IntegerProperty HATCH = getInteger("hatch");

    public int getHatch() {
        return (Integer) this.get(CraftHatchable.HATCH);
    }

    public void setHatch(int hatch) {
        this.set((Property) CraftHatchable.HATCH, (Comparable) hatch);
    }

    public int getMaximumHatch() {
        return getMax(CraftHatchable.HATCH);
    }
}
