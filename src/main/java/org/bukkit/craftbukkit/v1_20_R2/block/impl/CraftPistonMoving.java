package org.bukkit.craftbukkit.v1_20_R2.block.impl;

import java.util.Set;
import net.minecraft.world.level.block.piston.MovingPistonBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.type.TechnicalPiston;
import org.bukkit.block.data.type.TechnicalPiston.Type;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;

public final class CraftPistonMoving extends CraftBlockData implements TechnicalPiston, Directional {

    private static final EnumProperty TYPE = getEnum(MovingPistonBlock.class, "type");
    private static final EnumProperty FACING = getEnum(MovingPistonBlock.class, "facing");

    public CraftPistonMoving() {}

    public CraftPistonMoving(BlockState state) {
        super(state);
    }

    public Type getType() {
        return (Type) this.get(CraftPistonMoving.TYPE, Type.class);
    }

    public void setType(Type type) {
        this.set(CraftPistonMoving.TYPE, (Enum) type);
    }

    public BlockFace getFacing() {
        return (BlockFace) this.get(CraftPistonMoving.FACING, BlockFace.class);
    }

    public void setFacing(BlockFace facing) {
        this.set(CraftPistonMoving.FACING, (Enum) facing);
    }

    public Set getFaces() {
        return this.getValues(CraftPistonMoving.FACING, BlockFace.class);
    }
}
