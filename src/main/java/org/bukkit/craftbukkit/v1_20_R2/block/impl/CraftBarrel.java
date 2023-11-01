package org.bukkit.craftbukkit.v1_20_R2.block.impl;

import java.util.Set;
import net.minecraft.world.level.block.BarrelBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.Openable;
import org.bukkit.block.data.type.Barrel;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;

public final class CraftBarrel extends CraftBlockData implements Barrel, Directional, Openable {

    private static final EnumProperty FACING = getEnum(BarrelBlock.class, "facing");
    private static final BooleanProperty OPEN = getBoolean(BarrelBlock.class, "open");

    public CraftBarrel() {}

    public CraftBarrel(BlockState state) {
        super(state);
    }

    public BlockFace getFacing() {
        return (BlockFace) this.get(CraftBarrel.FACING, BlockFace.class);
    }

    public void setFacing(BlockFace facing) {
        this.set(CraftBarrel.FACING, (Enum) facing);
    }

    public Set getFaces() {
        return this.getValues(CraftBarrel.FACING, BlockFace.class);
    }

    public boolean isOpen() {
        return (Boolean) this.get(CraftBarrel.OPEN);
    }

    public void setOpen(boolean open) {
        this.set((Property) CraftBarrel.OPEN, (Comparable) open);
    }
}
