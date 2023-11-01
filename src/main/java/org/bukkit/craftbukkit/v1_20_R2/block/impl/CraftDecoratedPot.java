package org.bukkit.craftbukkit.v1_20_R2.block.impl;

import java.util.Set;
import net.minecraft.world.level.block.DecoratedPotBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.block.data.type.DecoratedPot;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;

public final class CraftDecoratedPot extends CraftBlockData implements DecoratedPot, Directional, Waterlogged {

    private static final EnumProperty FACING = getEnum(DecoratedPotBlock.class, "facing");
    private static final BooleanProperty WATERLOGGED = getBoolean(DecoratedPotBlock.class, "waterlogged");

    public CraftDecoratedPot() {}

    public CraftDecoratedPot(BlockState state) {
        super(state);
    }

    public BlockFace getFacing() {
        return (BlockFace) this.get(CraftDecoratedPot.FACING, BlockFace.class);
    }

    public void setFacing(BlockFace facing) {
        this.set(CraftDecoratedPot.FACING, (Enum) facing);
    }

    public Set getFaces() {
        return this.getValues(CraftDecoratedPot.FACING, BlockFace.class);
    }

    public boolean isWaterlogged() {
        return (Boolean) this.get(CraftDecoratedPot.WATERLOGGED);
    }

    public void setWaterlogged(boolean waterlogged) {
        this.set((Property) CraftDecoratedPot.WATERLOGGED, (Comparable) waterlogged);
    }
}
