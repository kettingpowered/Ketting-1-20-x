package org.bukkit.craftbukkit.v1_20_R2.block.impl;

import java.util.Set;
import net.minecraft.world.level.block.ChainBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.bukkit.Axis;
import org.bukkit.block.data.Orientable;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.block.data.type.Chain;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;

public final class CraftChain extends CraftBlockData implements Chain, Orientable, Waterlogged {

    private static final EnumProperty AXIS = getEnum(ChainBlock.class, "axis");
    private static final BooleanProperty WATERLOGGED = getBoolean(ChainBlock.class, "waterlogged");

    public CraftChain() {}

    public CraftChain(BlockState state) {
        super(state);
    }

    public Axis getAxis() {
        return (Axis) this.get(CraftChain.AXIS, Axis.class);
    }

    public void setAxis(Axis axis) {
        this.set(CraftChain.AXIS, (Enum) axis);
    }

    public Set getAxes() {
        return this.getValues(CraftChain.AXIS, Axis.class);
    }

    public boolean isWaterlogged() {
        return (Boolean) this.get(CraftChain.WATERLOGGED);
    }

    public void setWaterlogged(boolean waterlogged) {
        this.set((Property) CraftChain.WATERLOGGED, (Comparable) waterlogged);
    }
}
