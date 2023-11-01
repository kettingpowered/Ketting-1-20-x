package org.bukkit.craftbukkit.v1_20_R2.block.impl;

import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.block.data.type.Wall;
import org.bukkit.block.data.type.Wall.Height;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;

public final class CraftCobbleWall extends CraftBlockData implements Wall, Waterlogged {

    private static final BooleanProperty UP = getBoolean(WallBlock.class, "up");
    private static final EnumProperty[] HEIGHTS = new EnumProperty[]{getEnum(WallBlock.class, "north"), getEnum(WallBlock.class, "east"), getEnum(WallBlock.class, "south"), getEnum(WallBlock.class, "west")};
    private static final BooleanProperty WATERLOGGED = getBoolean(WallBlock.class, "waterlogged");

    public CraftCobbleWall() {}

    public CraftCobbleWall(BlockState state) {
        super(state);
    }

    public boolean isUp() {
        return (Boolean) this.get(CraftCobbleWall.UP);
    }

    public void setUp(boolean up) {
        this.set((Property) CraftCobbleWall.UP, (Comparable) up);
    }

    public Height getHeight(BlockFace face) {
        return (Height) this.get(CraftCobbleWall.HEIGHTS[face.ordinal()], Height.class);
    }

    public void setHeight(BlockFace face, Height height) {
        this.set(CraftCobbleWall.HEIGHTS[face.ordinal()], (Enum) height);
    }

    public boolean isWaterlogged() {
        return (Boolean) this.get(CraftCobbleWall.WATERLOGGED);
    }

    public void setWaterlogged(boolean waterlogged) {
        this.set((Property) CraftCobbleWall.WATERLOGGED, (Comparable) waterlogged);
    }
}
