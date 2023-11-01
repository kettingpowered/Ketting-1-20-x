package org.bukkit.craftbukkit.v1_20_R2.block.impl;

import java.util.Set;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Bisected;
import org.bukkit.block.data.Bisected.Half;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.block.data.type.Stairs;
import org.bukkit.block.data.type.Stairs.Shape;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;

public final class CraftStairs extends CraftBlockData implements Stairs, Bisected, Directional, Waterlogged {

    private static final EnumProperty SHAPE = getEnum(StairBlock.class, "shape");
    private static final EnumProperty HALF = getEnum(StairBlock.class, "half");
    private static final EnumProperty FACING = getEnum(StairBlock.class, "facing");
    private static final BooleanProperty WATERLOGGED = getBoolean(StairBlock.class, "waterlogged");

    public CraftStairs() {}

    public CraftStairs(BlockState state) {
        super(state);
    }

    public Shape getShape() {
        return (Shape) this.get(CraftStairs.SHAPE, Shape.class);
    }

    public void setShape(Shape shape) {
        this.set(CraftStairs.SHAPE, (Enum) shape);
    }

    public Half getHalf() {
        return (Half) this.get(CraftStairs.HALF, Half.class);
    }

    public void setHalf(Half half) {
        this.set(CraftStairs.HALF, (Enum) half);
    }

    public BlockFace getFacing() {
        return (BlockFace) this.get(CraftStairs.FACING, BlockFace.class);
    }

    public void setFacing(BlockFace facing) {
        this.set(CraftStairs.FACING, (Enum) facing);
    }

    public Set getFaces() {
        return this.getValues(CraftStairs.FACING, BlockFace.class);
    }

    public boolean isWaterlogged() {
        return (Boolean) this.get(CraftStairs.WATERLOGGED);
    }

    public void setWaterlogged(boolean waterlogged) {
        this.set((Property) CraftStairs.WATERLOGGED, (Comparable) waterlogged);
    }
}
