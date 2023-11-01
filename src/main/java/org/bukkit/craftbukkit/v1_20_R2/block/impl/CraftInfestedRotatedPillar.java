package org.bukkit.craftbukkit.v1_20_R2.block.impl;

import java.util.Set;
import net.minecraft.world.level.block.InfestedRotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import org.bukkit.Axis;
import org.bukkit.block.data.Orientable;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;

public final class CraftInfestedRotatedPillar extends CraftBlockData implements Orientable {

    private static final EnumProperty AXIS = getEnum(InfestedRotatedPillarBlock.class, "axis");

    public CraftInfestedRotatedPillar() {}

    public CraftInfestedRotatedPillar(BlockState state) {
        super(state);
    }

    public Axis getAxis() {
        return (Axis) this.get(CraftInfestedRotatedPillar.AXIS, Axis.class);
    }

    public void setAxis(Axis axis) {
        this.set(CraftInfestedRotatedPillar.AXIS, (Enum) axis);
    }

    public Set getAxes() {
        return this.getValues(CraftInfestedRotatedPillar.AXIS, Axis.class);
    }
}
