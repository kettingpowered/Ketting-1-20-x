package org.bukkit.craftbukkit.v1_20_R2.block.impl;

import java.util.Set;
import net.minecraft.world.level.block.TripWireHookBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Attachable;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.Powerable;
import org.bukkit.block.data.type.TripwireHook;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;

public final class CraftTripwireHook extends CraftBlockData implements TripwireHook, Attachable, Directional, Powerable {

    private static final BooleanProperty ATTACHED = getBoolean(TripWireHookBlock.class, "attached");
    private static final EnumProperty FACING = getEnum(TripWireHookBlock.class, "facing");
    private static final BooleanProperty POWERED = getBoolean(TripWireHookBlock.class, "powered");

    public CraftTripwireHook() {}

    public CraftTripwireHook(BlockState state) {
        super(state);
    }

    public boolean isAttached() {
        return (Boolean) this.get(CraftTripwireHook.ATTACHED);
    }

    public void setAttached(boolean attached) {
        this.set((Property) CraftTripwireHook.ATTACHED, (Comparable) attached);
    }

    public BlockFace getFacing() {
        return (BlockFace) this.get(CraftTripwireHook.FACING, BlockFace.class);
    }

    public void setFacing(BlockFace facing) {
        this.set(CraftTripwireHook.FACING, (Enum) facing);
    }

    public Set getFaces() {
        return this.getValues(CraftTripwireHook.FACING, BlockFace.class);
    }

    public boolean isPowered() {
        return (Boolean) this.get(CraftTripwireHook.POWERED);
    }

    public void setPowered(boolean powered) {
        this.set((Property) CraftTripwireHook.POWERED, (Comparable) powered);
    }
}
