package org.bukkit.craftbukkit.v1_20_R2.block.impl;

import java.util.Set;
import net.minecraft.world.level.block.BaseCoralWallFanBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.block.data.type.CoralWallFan;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;

public final class CraftCoralFanWallAbstract extends CraftBlockData implements CoralWallFan, Directional, Waterlogged {

    private static final EnumProperty FACING = getEnum(BaseCoralWallFanBlock.class, "facing");
    private static final BooleanProperty WATERLOGGED = getBoolean(BaseCoralWallFanBlock.class, "waterlogged");

    public CraftCoralFanWallAbstract() {}

    public CraftCoralFanWallAbstract(BlockState state) {
        super(state);
    }

    public BlockFace getFacing() {
        return (BlockFace) this.get(CraftCoralFanWallAbstract.FACING, BlockFace.class);
    }

    public void setFacing(BlockFace facing) {
        this.set(CraftCoralFanWallAbstract.FACING, (Enum) facing);
    }

    public Set getFaces() {
        return this.getValues(CraftCoralFanWallAbstract.FACING, BlockFace.class);
    }

    public boolean isWaterlogged() {
        return (Boolean) this.get(CraftCoralFanWallAbstract.WATERLOGGED);
    }

    public void setWaterlogged(boolean waterlogged) {
        this.set((Property) CraftCoralFanWallAbstract.WATERLOGGED, (Comparable) waterlogged);
    }
}
