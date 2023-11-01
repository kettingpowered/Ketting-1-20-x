package org.bukkit.craftbukkit.v1_20_R2.block.data;

import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.bukkit.block.data.AnaloguePowerable;

public abstract class CraftAnaloguePowerable extends CraftBlockData implements AnaloguePowerable {

    private static final IntegerProperty POWER = getInteger("power");

    public int getPower() {
        return (Integer) this.get(CraftAnaloguePowerable.POWER);
    }

    public void setPower(int power) {
        this.set((Property) CraftAnaloguePowerable.POWER, (Comparable) power);
    }

    public int getMaximumPower() {
        return getMax(CraftAnaloguePowerable.POWER);
    }
}
