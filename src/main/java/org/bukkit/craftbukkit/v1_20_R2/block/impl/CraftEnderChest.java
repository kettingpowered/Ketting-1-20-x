package org.bukkit.craftbukkit.v1_20_R2.block.impl;

import java.util.Set;
import net.minecraft.world.level.block.EnderChestBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.block.data.type.EnderChest;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;

public final class CraftEnderChest extends CraftBlockData implements EnderChest, Directional, Waterlogged {

    private static final EnumProperty FACING = getEnum(EnderChestBlock.class, "facing");
    private static final BooleanProperty WATERLOGGED = getBoolean(EnderChestBlock.class, "waterlogged");

    public CraftEnderChest() {}

    public CraftEnderChest(BlockState state) {
        super(state);
    }

    public BlockFace getFacing() {
        return (BlockFace) this.get(CraftEnderChest.FACING, BlockFace.class);
    }

    public void setFacing(BlockFace facing) {
        this.set(CraftEnderChest.FACING, (Enum) facing);
    }

    public Set getFaces() {
        return this.getValues(CraftEnderChest.FACING, BlockFace.class);
    }

    public boolean isWaterlogged() {
        return (Boolean) this.get(CraftEnderChest.WATERLOGGED);
    }

    public void setWaterlogged(boolean waterlogged) {
        this.set((Property) CraftEnderChest.WATERLOGGED, (Comparable) waterlogged);
    }
}
