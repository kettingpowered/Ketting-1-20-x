package org.bukkit.craftbukkit.v1_20_R2.block.data;

import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.bukkit.block.data.Levelled;

public abstract class CraftLevelled extends CraftBlockData implements Levelled {

    private static final IntegerProperty LEVEL = getInteger("level");

    public int getLevel() {
        return (Integer) this.get(CraftLevelled.LEVEL);
    }

    public void setLevel(int level) {
        this.set((Property) CraftLevelled.LEVEL, (Comparable) level);
    }

    public int getMaximumLevel() {
        return getMax(CraftLevelled.LEVEL);
    }
}
