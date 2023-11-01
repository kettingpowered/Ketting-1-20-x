package org.bukkit.craftbukkit.v1_20_R2.block.data.type;

import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.bukkit.block.data.Brushable;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;

public abstract class CraftBrushable extends CraftBlockData implements Brushable {

    private static final IntegerProperty DUSTED = getInteger("dusted");

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
