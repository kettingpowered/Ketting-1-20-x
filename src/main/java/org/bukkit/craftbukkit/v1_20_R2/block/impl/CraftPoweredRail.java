package org.bukkit.craftbukkit.v1_20_R2.block.impl;

import java.util.Set;
import net.minecraft.world.level.block.PoweredRailBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.bukkit.block.data.Powerable;
import org.bukkit.block.data.Rail;
import org.bukkit.block.data.Rail.Shape;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.block.data.type.RedstoneRail;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;

public final class CraftPoweredRail extends CraftBlockData implements RedstoneRail, Powerable, Rail, Waterlogged {

    private static final BooleanProperty POWERED = getBoolean(PoweredRailBlock.class, "powered");
    private static final EnumProperty SHAPE = getEnum(PoweredRailBlock.class, "shape");
    private static final BooleanProperty WATERLOGGED = getBoolean(PoweredRailBlock.class, "waterlogged");

    public CraftPoweredRail() {}

    public CraftPoweredRail(BlockState state) {
        super(state);
    }

    public boolean isPowered() {
        return (Boolean) this.get(CraftPoweredRail.POWERED);
    }

    public void setPowered(boolean powered) {
        this.set((Property) CraftPoweredRail.POWERED, (Comparable) powered);
    }

    public Shape getShape() {
        return (Shape) this.get(CraftPoweredRail.SHAPE, Shape.class);
    }

    public void setShape(Shape shape) {
        this.set(CraftPoweredRail.SHAPE, (Enum) shape);
    }

    public Set getShapes() {
        return this.getValues(CraftPoweredRail.SHAPE, Shape.class);
    }

    public boolean isWaterlogged() {
        return (Boolean) this.get(CraftPoweredRail.WATERLOGGED);
    }

    public void setWaterlogged(boolean waterlogged) {
        this.set((Property) CraftPoweredRail.WATERLOGGED, (Comparable) waterlogged);
    }
}
