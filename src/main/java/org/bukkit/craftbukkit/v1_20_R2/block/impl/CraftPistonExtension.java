package org.bukkit.craftbukkit.v1_20_R2.block.impl;

import java.util.Set;
import net.minecraft.world.level.block.piston.PistonHeadBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.type.PistonHead;
import org.bukkit.block.data.type.TechnicalPiston;
import org.bukkit.block.data.type.TechnicalPiston.Type;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;

public final class CraftPistonExtension extends CraftBlockData implements PistonHead, TechnicalPiston, Directional {

    private static final BooleanProperty SHORT = getBoolean(PistonHeadBlock.class, "short");
    private static final EnumProperty TYPE = getEnum(PistonHeadBlock.class, "type");
    private static final EnumProperty FACING = getEnum(PistonHeadBlock.class, "facing");

    public CraftPistonExtension() {}

    public CraftPistonExtension(BlockState state) {
        super(state);
    }

    public boolean isShort() {
        return (Boolean) this.get(CraftPistonExtension.SHORT);
    }

    public void setShort(boolean _short) {
        this.set((Property) CraftPistonExtension.SHORT, (Comparable) _short);
    }

    public Type getType() {
        return (Type) this.get(CraftPistonExtension.TYPE, Type.class);
    }

    public void setType(Type type) {
        this.set(CraftPistonExtension.TYPE, (Enum) type);
    }

    public BlockFace getFacing() {
        return (BlockFace) this.get(CraftPistonExtension.FACING, BlockFace.class);
    }

    public void setFacing(BlockFace facing) {
        this.set(CraftPistonExtension.FACING, (Enum) facing);
    }

    public Set getFaces() {
        return this.getValues(CraftPistonExtension.FACING, BlockFace.class);
    }
}
