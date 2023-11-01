package org.bukkit.craftbukkit.v1_20_R2.block.impl;

import java.util.Set;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Bisected;
import org.bukkit.block.data.Bisected.Half;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.Openable;
import org.bukkit.block.data.Powerable;
import org.bukkit.block.data.type.Door;
import org.bukkit.block.data.type.Door.Hinge;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;

public final class CraftDoor extends CraftBlockData implements Door, Bisected, Directional, Openable, Powerable {

    private static final EnumProperty HINGE = getEnum(DoorBlock.class, "hinge");
    private static final EnumProperty HALF = getEnum(DoorBlock.class, "half");
    private static final EnumProperty FACING = getEnum(DoorBlock.class, "facing");
    private static final BooleanProperty OPEN = getBoolean(DoorBlock.class, "open");
    private static final BooleanProperty POWERED = getBoolean(DoorBlock.class, "powered");

    public CraftDoor() {}

    public CraftDoor(BlockState state) {
        super(state);
    }

    public Hinge getHinge() {
        return (Hinge) this.get(CraftDoor.HINGE, Hinge.class);
    }

    public void setHinge(Hinge hinge) {
        this.set(CraftDoor.HINGE, (Enum) hinge);
    }

    public Half getHalf() {
        return (Half) this.get(CraftDoor.HALF, Half.class);
    }

    public void setHalf(Half half) {
        this.set(CraftDoor.HALF, (Enum) half);
    }

    public BlockFace getFacing() {
        return (BlockFace) this.get(CraftDoor.FACING, BlockFace.class);
    }

    public void setFacing(BlockFace facing) {
        this.set(CraftDoor.FACING, (Enum) facing);
    }

    public Set getFaces() {
        return this.getValues(CraftDoor.FACING, BlockFace.class);
    }

    public boolean isOpen() {
        return (Boolean) this.get(CraftDoor.OPEN);
    }

    public void setOpen(boolean open) {
        this.set((Property) CraftDoor.OPEN, (Comparable) open);
    }

    public boolean isPowered() {
        return (Boolean) this.get(CraftDoor.POWERED);
    }

    public void setPowered(boolean powered) {
        this.set((Property) CraftDoor.POWERED, (Comparable) powered);
    }
}
