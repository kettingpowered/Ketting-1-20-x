package org.bukkit.craftbukkit.v1_20_R2.block.impl;

import java.util.Set;
import net.minecraft.world.level.block.BigDripleafBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.block.data.type.BigDripleaf;
import org.bukkit.block.data.type.BigDripleaf.Tilt;
import org.bukkit.block.data.type.Dripleaf;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;

public final class CraftBigDripleaf extends CraftBlockData implements BigDripleaf, Dripleaf, Directional, Waterlogged {

    private static final EnumProperty TILT = getEnum(BigDripleafBlock.class, "tilt");
    private static final EnumProperty FACING = getEnum(BigDripleafBlock.class, "facing");
    private static final BooleanProperty WATERLOGGED = getBoolean(BigDripleafBlock.class, "waterlogged");

    public CraftBigDripleaf() {}

    public CraftBigDripleaf(BlockState state) {
        super(state);
    }

    public Tilt getTilt() {
        return (Tilt) this.get(CraftBigDripleaf.TILT, Tilt.class);
    }

    public void setTilt(Tilt tilt) {
        this.set(CraftBigDripleaf.TILT, (Enum) tilt);
    }

    public BlockFace getFacing() {
        return (BlockFace) this.get(CraftBigDripleaf.FACING, BlockFace.class);
    }

    public void setFacing(BlockFace facing) {
        this.set(CraftBigDripleaf.FACING, (Enum) facing);
    }

    public Set getFaces() {
        return this.getValues(CraftBigDripleaf.FACING, BlockFace.class);
    }

    public boolean isWaterlogged() {
        return (Boolean) this.get(CraftBigDripleaf.WATERLOGGED);
    }

    public void setWaterlogged(boolean waterlogged) {
        this.set((Property) CraftBigDripleaf.WATERLOGGED, (Comparable) waterlogged);
    }
}
