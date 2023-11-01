package org.bukkit.craftbukkit.v1_20_R2.block.data;

import java.util.Set;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;

public abstract class CraftDirectional extends CraftBlockData implements Directional {

    private static final EnumProperty FACING = getEnum("facing");

    public BlockFace getFacing() {
        return (BlockFace) this.get(CraftDirectional.FACING, BlockFace.class);
    }

    public void setFacing(BlockFace facing) {
        this.set(CraftDirectional.FACING, (Enum) facing);
    }

    public Set getFaces() {
        return this.getValues(CraftDirectional.FACING, BlockFace.class);
    }
}
