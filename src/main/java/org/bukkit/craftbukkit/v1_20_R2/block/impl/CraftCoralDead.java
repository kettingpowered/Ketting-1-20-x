package org.bukkit.craftbukkit.v1_20_R2.block.impl;

import net.minecraft.world.level.block.BaseCoralPlantBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;

public final class CraftCoralDead extends CraftBlockData implements Waterlogged {

    private static final BooleanProperty WATERLOGGED = getBoolean(BaseCoralPlantBlock.class, "waterlogged");

    public CraftCoralDead() {}

    public CraftCoralDead(BlockState state) {
        super(state);
    }

    public boolean isWaterlogged() {
        return (Boolean) this.get(CraftCoralDead.WATERLOGGED);
    }

    public void setWaterlogged(boolean waterlogged) {
        this.set((Property) CraftCoralDead.WATERLOGGED, (Comparable) waterlogged);
    }
}
