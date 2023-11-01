package org.bukkit.craftbukkit.v1_20_R2.block.impl;

import java.util.Set;
import net.minecraft.world.level.block.WallHangingSignBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.block.data.type.WallHangingSign;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;

public final class CraftWallHangingSign extends CraftBlockData implements WallHangingSign, Directional, Waterlogged {

    private static final EnumProperty FACING = getEnum(WallHangingSignBlock.class, "facing");
    private static final BooleanProperty WATERLOGGED = getBoolean(WallHangingSignBlock.class, "waterlogged");

    public CraftWallHangingSign() {}

    public CraftWallHangingSign(BlockState state) {
        super(state);
    }

    public BlockFace getFacing() {
        return (BlockFace) this.get(CraftWallHangingSign.FACING, BlockFace.class);
    }

    public void setFacing(BlockFace facing) {
        this.set(CraftWallHangingSign.FACING, (Enum) facing);
    }

    public Set getFaces() {
        return this.getValues(CraftWallHangingSign.FACING, BlockFace.class);
    }

    public boolean isWaterlogged() {
        return (Boolean) this.get(CraftWallHangingSign.WATERLOGGED);
    }

    public void setWaterlogged(boolean waterlogged) {
        this.set((Property) CraftWallHangingSign.WATERLOGGED, (Comparable) waterlogged);
    }
}
