package org.bukkit.craftbukkit.v1_20_R2.block.impl;

import java.util.Set;
import net.minecraft.world.level.block.EndRodBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;

public final class CraftEndRod extends CraftBlockData implements Directional {

    private static final EnumProperty FACING = getEnum(EndRodBlock.class, "facing");

    public CraftEndRod() {}

    public CraftEndRod(BlockState state) {
        super(state);
    }

    public BlockFace getFacing() {
        return (BlockFace) this.get(CraftEndRod.FACING, BlockFace.class);
    }

    public void setFacing(BlockFace facing) {
        this.set(CraftEndRod.FACING, (Enum) facing);
    }

    public Set getFaces() {
        return this.getValues(CraftEndRod.FACING, BlockFace.class);
    }
}
