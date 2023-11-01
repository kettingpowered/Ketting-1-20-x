package org.bukkit.craftbukkit.v1_20_R2.block.impl;

import java.util.Set;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.type.Dispenser;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;

public final class CraftDispenser extends CraftBlockData implements Dispenser, Directional {

    private static final BooleanProperty TRIGGERED = getBoolean(DispenserBlock.class, "triggered");
    private static final EnumProperty FACING = getEnum(DispenserBlock.class, "facing");

    public CraftDispenser() {}

    public CraftDispenser(BlockState state) {
        super(state);
    }

    public boolean isTriggered() {
        return (Boolean) this.get(CraftDispenser.TRIGGERED);
    }

    public void setTriggered(boolean triggered) {
        this.set((Property) CraftDispenser.TRIGGERED, (Comparable) triggered);
    }

    public BlockFace getFacing() {
        return (BlockFace) this.get(CraftDispenser.FACING, BlockFace.class);
    }

    public void setFacing(BlockFace facing) {
        this.set(CraftDispenser.FACING, (Enum) facing);
    }

    public Set getFaces() {
        return this.getValues(CraftDispenser.FACING, BlockFace.class);
    }
}
