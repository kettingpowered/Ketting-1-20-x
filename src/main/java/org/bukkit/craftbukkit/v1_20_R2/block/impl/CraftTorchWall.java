package org.bukkit.craftbukkit.v1_20_R2.block.impl;

import java.util.Set;
import net.minecraft.world.level.block.WallTorchBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;

public final class CraftTorchWall extends CraftBlockData implements Directional {

    private static final EnumProperty FACING = getEnum(WallTorchBlock.class, "facing");

    public CraftTorchWall() {}

    public CraftTorchWall(BlockState state) {
        super(state);
    }

    public BlockFace getFacing() {
        return (BlockFace) this.get(CraftTorchWall.FACING, BlockFace.class);
    }

    public void setFacing(BlockFace facing) {
        this.set(CraftTorchWall.FACING, (Enum) facing);
    }

    public Set getFaces() {
        return this.getValues(CraftTorchWall.FACING, BlockFace.class);
    }
}
