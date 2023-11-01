package org.bukkit.craftbukkit.v1_20_R2.block.impl;

import java.util.Set;
import net.minecraft.world.level.block.LecternBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.Powerable;
import org.bukkit.block.data.type.Lectern;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;

public final class CraftLectern extends CraftBlockData implements Lectern, Directional, Powerable {

    private static final BooleanProperty HAS_BOOK = getBoolean(LecternBlock.class, "has_book");
    private static final EnumProperty FACING = getEnum(LecternBlock.class, "facing");
    private static final BooleanProperty POWERED = getBoolean(LecternBlock.class, "powered");

    public CraftLectern() {}

    public CraftLectern(BlockState state) {
        super(state);
    }

    public boolean hasBook() {
        return (Boolean) this.get(CraftLectern.HAS_BOOK);
    }

    public BlockFace getFacing() {
        return (BlockFace) this.get(CraftLectern.FACING, BlockFace.class);
    }

    public void setFacing(BlockFace facing) {
        this.set(CraftLectern.FACING, (Enum) facing);
    }

    public Set getFaces() {
        return this.getValues(CraftLectern.FACING, BlockFace.class);
    }

    public boolean isPowered() {
        return (Boolean) this.get(CraftLectern.POWERED);
    }

    public void setPowered(boolean powered) {
        this.set((Property) CraftLectern.POWERED, (Comparable) powered);
    }
}
