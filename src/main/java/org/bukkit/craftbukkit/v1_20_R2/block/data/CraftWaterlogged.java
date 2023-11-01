package org.bukkit.craftbukkit.v1_20_R2.block.data;

import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.bukkit.block.data.Waterlogged;

public abstract class CraftWaterlogged extends CraftBlockData implements Waterlogged {

    private static final BooleanProperty WATERLOGGED = getBoolean("waterlogged");

    public boolean isWaterlogged() {
        return (Boolean) this.get(CraftWaterlogged.WATERLOGGED);
    }

    public void setWaterlogged(boolean waterlogged) {
        this.set((Property) CraftWaterlogged.WATERLOGGED, (Comparable) waterlogged);
    }
}
