package org.bukkit.craftbukkit.v1_20_R2.block.impl;

import java.util.Set;
import net.minecraft.world.level.block.PointedDripstoneBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.block.data.type.PointedDripstone;
import org.bukkit.block.data.type.PointedDripstone.Thickness;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;

public final class CraftPointedDripstone extends CraftBlockData implements PointedDripstone, Waterlogged {

    private static final EnumProperty VERTICAL_DIRECTION = getEnum(PointedDripstoneBlock.class, "vertical_direction");
    private static final EnumProperty THICKNESS = getEnum(PointedDripstoneBlock.class, "thickness");
    private static final BooleanProperty WATERLOGGED = getBoolean(PointedDripstoneBlock.class, "waterlogged");

    public CraftPointedDripstone() {}

    public CraftPointedDripstone(BlockState state) {
        super(state);
    }

    public BlockFace getVerticalDirection() {
        return (BlockFace) this.get(CraftPointedDripstone.VERTICAL_DIRECTION, BlockFace.class);
    }

    public void setVerticalDirection(BlockFace direction) {
        this.set(CraftPointedDripstone.VERTICAL_DIRECTION, (Enum) direction);
    }

    public Set getVerticalDirections() {
        return this.getValues(CraftPointedDripstone.VERTICAL_DIRECTION, BlockFace.class);
    }

    public Thickness getThickness() {
        return (Thickness) this.get(CraftPointedDripstone.THICKNESS, Thickness.class);
    }

    public void setThickness(Thickness thickness) {
        this.set(CraftPointedDripstone.THICKNESS, (Enum) thickness);
    }

    public boolean isWaterlogged() {
        return (Boolean) this.get(CraftPointedDripstone.WATERLOGGED);
    }

    public void setWaterlogged(boolean waterlogged) {
        this.set((Property) CraftPointedDripstone.WATERLOGGED, (Comparable) waterlogged);
    }
}
