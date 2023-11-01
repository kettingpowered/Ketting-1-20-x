package org.bukkit.craftbukkit.v1_20_R2.block.impl;

import java.util.Set;
import net.minecraft.world.level.block.WeatheringCopperStairBlock;
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

public final class CraftWeatheringCopperStair extends CraftBlockData implements Stairs, Bisected, Directional, Waterlogged {

    private static final EnumProperty SHAPE = getEnum(WeatheringCopperStairBlock.class, "shape");
    private static final EnumProperty HALF = getEnum(WeatheringCopperStairBlock.class, "half");
    private static final EnumProperty FACING = getEnum(WeatheringCopperStairBlock.class, "facing");
    private static final BooleanProperty WATERLOGGED = getBoolean(WeatheringCopperStairBlock.class, "waterlogged");

    public CraftWeatheringCopperStair() {}

    public CraftWeatheringCopperStair(BlockState state) {
        super(state);
    }

    public Shape getShape() {
        return (Shape) this.get(CraftWeatheringCopperStair.SHAPE, Shape.class);
    }

    public void setShape(Shape shape) {
        this.set(CraftWeatheringCopperStair.SHAPE, (Enum) shape);
    }

    public Half getHalf() {
        return (Half) this.get(CraftWeatheringCopperStair.HALF, Half.class);
    }

    public void setHalf(Half half) {
        this.set(CraftWeatheringCopperStair.HALF, (Enum) half);
    }

    public BlockFace getFacing() {
        return (BlockFace) this.get(CraftWeatheringCopperStair.FACING, BlockFace.class);
    }

    public void setFacing(BlockFace facing) {
        this.set(CraftWeatheringCopperStair.FACING, (Enum) facing);
    }

    public Set getFaces() {
        return this.getValues(CraftWeatheringCopperStair.FACING, BlockFace.class);
    }

    public boolean isWaterlogged() {
        return (Boolean) this.get(CraftWeatheringCopperStair.WATERLOGGED);
    }

    public void setWaterlogged(boolean waterlogged) {
        this.set((Property) CraftWeatheringCopperStair.WATERLOGGED, (Comparable) waterlogged);
    }
}
