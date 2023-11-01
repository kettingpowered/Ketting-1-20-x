package org.bukkit.craftbukkit.v1_20_R2.block.data.type;

import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.bukkit.block.data.type.Beehive;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;

public abstract class CraftBeehive extends CraftBlockData implements Beehive {

    private static final IntegerProperty HONEY_LEVEL = getInteger("honey_level");

    public int getHoneyLevel() {
        return (Integer) this.get(CraftBeehive.HONEY_LEVEL);
    }

    public void setHoneyLevel(int honeyLevel) {
        this.set((Property) CraftBeehive.HONEY_LEVEL, (Comparable) honeyLevel);
    }

    public int getMaximumHoneyLevel() {
        return getMax(CraftBeehive.HONEY_LEVEL);
    }
}
