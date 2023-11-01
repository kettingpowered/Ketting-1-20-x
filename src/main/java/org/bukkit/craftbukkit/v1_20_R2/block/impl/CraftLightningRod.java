package org.bukkit.craftbukkit.v1_20_R2.block.impl;

import java.util.Set;
import net.minecraft.world.level.block.LightningRodBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.Powerable;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.block.data.type.LightningRod;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;

public final class CraftLightningRod extends CraftBlockData implements LightningRod, Directional, Powerable, Waterlogged {

    private static final EnumProperty FACING = getEnum(LightningRodBlock.class, "facing");
    private static final BooleanProperty POWERED = getBoolean(LightningRodBlock.class, "powered");
    private static final BooleanProperty WATERLOGGED = getBoolean(LightningRodBlock.class, "waterlogged");

    public CraftLightningRod() {}

    public CraftLightningRod(BlockState state) {
        super(state);
    }

    public BlockFace getFacing() {
        return (BlockFace) this.get(CraftLightningRod.FACING, BlockFace.class);
    }

    public void setFacing(BlockFace facing) {
        this.set(CraftLightningRod.FACING, (Enum) facing);
    }

    public Set getFaces() {
        return this.getValues(CraftLightningRod.FACING, BlockFace.class);
    }

    public boolean isPowered() {
        return (Boolean) this.get(CraftLightningRod.POWERED);
    }

    public void setPowered(boolean powered) {
        this.set((Property) CraftLightningRod.POWERED, (Comparable) powered);
    }

    public boolean isWaterlogged() {
        return (Boolean) this.get(CraftLightningRod.WATERLOGGED);
    }

    public void setWaterlogged(boolean waterlogged) {
        this.set((Property) CraftLightningRod.WATERLOGGED, (Comparable) waterlogged);
    }
}
