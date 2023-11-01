package org.bukkit.craftbukkit.v1_20_R2.block.impl;

import java.util.Set;
import net.minecraft.world.level.block.NetherPortalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import org.bukkit.Axis;
import org.bukkit.block.data.Orientable;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;

public final class CraftPortal extends CraftBlockData implements Orientable {

    private static final EnumProperty AXIS = getEnum(NetherPortalBlock.class, "axis");

    public CraftPortal() {}

    public CraftPortal(BlockState state) {
        super(state);
    }

    public Axis getAxis() {
        return (Axis) this.get(CraftPortal.AXIS, Axis.class);
    }

    public void setAxis(Axis axis) {
        this.set(CraftPortal.AXIS, (Enum) axis);
    }

    public Set getAxes() {
        return this.getValues(CraftPortal.AXIS, Axis.class);
    }
}
