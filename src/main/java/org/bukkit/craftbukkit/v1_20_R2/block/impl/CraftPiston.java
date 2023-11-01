package org.bukkit.craftbukkit.v1_20_R2.block.impl;

import java.util.Set;
import net.minecraft.world.level.block.piston.PistonBaseBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.type.Piston;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;

public final class CraftPiston extends CraftBlockData implements Piston, Directional {

    private static final BooleanProperty EXTENDED = getBoolean(PistonBaseBlock.class, "extended");
    private static final EnumProperty FACING = getEnum(PistonBaseBlock.class, "facing");

    public CraftPiston() {}

    public CraftPiston(BlockState state) {
        super(state);
    }

    public boolean isExtended() {
        return (Boolean) this.get(CraftPiston.EXTENDED);
    }

    public void setExtended(boolean extended) {
        this.set((Property) CraftPiston.EXTENDED, (Comparable) extended);
    }

    public BlockFace getFacing() {
        return (BlockFace) this.get(CraftPiston.FACING, BlockFace.class);
    }

    public void setFacing(BlockFace facing) {
        this.set(CraftPiston.FACING, (Enum) facing);
    }

    public Set getFaces() {
        return this.getValues(CraftPiston.FACING, BlockFace.class);
    }
}
