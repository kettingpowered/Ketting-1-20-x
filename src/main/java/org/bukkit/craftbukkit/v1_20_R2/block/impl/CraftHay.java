package org.bukkit.craftbukkit.v1_20_R2.block.impl;

import java.util.Set;
import net.minecraft.world.level.block.HayBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import org.bukkit.Axis;
import org.bukkit.block.data.Orientable;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;

public final class CraftHay extends CraftBlockData implements Orientable {

    private static final EnumProperty AXIS = getEnum(HayBlock.class, "axis");

    public CraftHay() {}

    public CraftHay(BlockState state) {
        super(state);
    }

    public Axis getAxis() {
        return (Axis) this.get(CraftHay.AXIS, Axis.class);
    }

    public void setAxis(Axis axis) {
        this.set(CraftHay.AXIS, (Enum) axis);
    }

    public Set getAxes() {
        return this.getValues(CraftHay.AXIS, Axis.class);
    }
}
