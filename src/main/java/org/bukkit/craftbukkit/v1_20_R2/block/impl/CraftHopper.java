package org.bukkit.craftbukkit.v1_20_R2.block.impl;

import java.util.Set;
import net.minecraft.world.level.block.HopperBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.type.Hopper;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;

public final class CraftHopper extends CraftBlockData implements Hopper, Directional {

    private static final BooleanProperty ENABLED = getBoolean(HopperBlock.class, "enabled");
    private static final EnumProperty FACING = getEnum(HopperBlock.class, "facing");

    public CraftHopper() {}

    public CraftHopper(BlockState state) {
        super(state);
    }

    public boolean isEnabled() {
        return (Boolean) this.get(CraftHopper.ENABLED);
    }

    public void setEnabled(boolean enabled) {
        this.set((Property) CraftHopper.ENABLED, (Comparable) enabled);
    }

    public BlockFace getFacing() {
        return (BlockFace) this.get(CraftHopper.FACING, BlockFace.class);
    }

    public void setFacing(BlockFace facing) {
        this.set(CraftHopper.FACING, (Enum) facing);
    }

    public Set getFaces() {
        return this.getValues(CraftHopper.FACING, BlockFace.class);
    }
}
