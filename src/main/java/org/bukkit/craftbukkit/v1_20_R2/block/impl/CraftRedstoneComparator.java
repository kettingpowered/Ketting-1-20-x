package org.bukkit.craftbukkit.v1_20_R2.block.impl;

import java.util.Set;
import net.minecraft.world.level.block.ComparatorBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.Powerable;
import org.bukkit.block.data.type.Comparator;
import org.bukkit.block.data.type.Comparator.Mode;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;

public final class CraftRedstoneComparator extends CraftBlockData implements Comparator, Directional, Powerable {

    private static final EnumProperty MODE = getEnum(ComparatorBlock.class, "mode");
    private static final EnumProperty FACING = getEnum(ComparatorBlock.class, "facing");
    private static final BooleanProperty POWERED = getBoolean(ComparatorBlock.class, "powered");

    public CraftRedstoneComparator() {}

    public CraftRedstoneComparator(BlockState state) {
        super(state);
    }

    public Mode getMode() {
        return (Mode) this.get(CraftRedstoneComparator.MODE, Mode.class);
    }

    public void setMode(Mode mode) {
        this.set(CraftRedstoneComparator.MODE, (Enum) mode);
    }

    public BlockFace getFacing() {
        return (BlockFace) this.get(CraftRedstoneComparator.FACING, BlockFace.class);
    }

    public void setFacing(BlockFace facing) {
        this.set(CraftRedstoneComparator.FACING, (Enum) facing);
    }

    public Set getFaces() {
        return this.getValues(CraftRedstoneComparator.FACING, BlockFace.class);
    }

    public boolean isPowered() {
        return (Boolean) this.get(CraftRedstoneComparator.POWERED);
    }

    public void setPowered(boolean powered) {
        this.set((Property) CraftRedstoneComparator.POWERED, (Comparable) powered);
    }
}
