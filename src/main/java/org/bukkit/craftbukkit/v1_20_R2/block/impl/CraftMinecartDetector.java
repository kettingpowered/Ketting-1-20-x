package org.bukkit.craftbukkit.v1_20_R2.block.impl;

import java.util.Set;
import net.minecraft.world.level.block.DetectorRailBlock;
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

public final class CraftMinecartDetector extends CraftBlockData implements RedstoneRail, Powerable, Rail, Waterlogged {

    private static final BooleanProperty POWERED = getBoolean(DetectorRailBlock.class, "powered");
    private static final EnumProperty SHAPE = getEnum(DetectorRailBlock.class, "shape");
    private static final BooleanProperty WATERLOGGED = getBoolean(DetectorRailBlock.class, "waterlogged");

    public CraftMinecartDetector() {}

    public CraftMinecartDetector(BlockState state) {
        super(state);
    }

    public boolean isPowered() {
        return (Boolean) this.get(CraftMinecartDetector.POWERED);
    }

    public void setPowered(boolean powered) {
        this.set((Property) CraftMinecartDetector.POWERED, (Comparable) powered);
    }

    public Shape getShape() {
        return (Shape) this.get(CraftMinecartDetector.SHAPE, Shape.class);
    }

    public void setShape(Shape shape) {
        this.set(CraftMinecartDetector.SHAPE, (Enum) shape);
    }

    public Set getShapes() {
        return this.getValues(CraftMinecartDetector.SHAPE, Shape.class);
    }

    public boolean isWaterlogged() {
        return (Boolean) this.get(CraftMinecartDetector.WATERLOGGED);
    }

    public void setWaterlogged(boolean waterlogged) {
        this.set((Property) CraftMinecartDetector.WATERLOGGED, (Comparable) waterlogged);
    }
}
