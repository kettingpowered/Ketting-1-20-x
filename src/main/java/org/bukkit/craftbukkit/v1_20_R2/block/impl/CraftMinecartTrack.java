package org.bukkit.craftbukkit.v1_20_R2.block.impl;

import java.util.Set;
import net.minecraft.world.level.block.RailBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.bukkit.block.data.Rail;
import org.bukkit.block.data.Rail.Shape;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;

public final class CraftMinecartTrack extends CraftBlockData implements Rail, Waterlogged {

    private static final EnumProperty SHAPE = getEnum(RailBlock.class, "shape");
    private static final BooleanProperty WATERLOGGED = getBoolean(RailBlock.class, "waterlogged");

    public CraftMinecartTrack() {}

    public CraftMinecartTrack(BlockState state) {
        super(state);
    }

    public Shape getShape() {
        return (Shape) this.get(CraftMinecartTrack.SHAPE, Shape.class);
    }

    public void setShape(Shape shape) {
        this.set(CraftMinecartTrack.SHAPE, (Enum) shape);
    }

    public Set getShapes() {
        return this.getValues(CraftMinecartTrack.SHAPE, Shape.class);
    }

    public boolean isWaterlogged() {
        return (Boolean) this.get(CraftMinecartTrack.WATERLOGGED);
    }

    public void setWaterlogged(boolean waterlogged) {
        this.set((Property) CraftMinecartTrack.WATERLOGGED, (Comparable) waterlogged);
    }
}
