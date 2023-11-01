package org.bukkit.craftbukkit.v1_20_R2.block.impl;

import java.util.Set;
import net.minecraft.world.level.block.BeehiveBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.type.Beehive;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;

public final class CraftBeehive extends CraftBlockData implements Beehive, Directional {

    private static final IntegerProperty HONEY_LEVEL = getInteger(BeehiveBlock.class, "honey_level");
    private static final EnumProperty FACING = getEnum(BeehiveBlock.class, "facing");

    public CraftBeehive() {}

    public CraftBeehive(BlockState state) {
        super(state);
    }

    public int getHoneyLevel() {
        return (Integer) this.get(CraftBeehive.HONEY_LEVEL);
    }

    public void setHoneyLevel(int honeyLevel) {
        this.set((Property) CraftBeehive.HONEY_LEVEL, (Comparable) honeyLevel);
    }

    public int getMaximumHoneyLevel() {
        return getMax(CraftBeehive.HONEY_LEVEL);
    }

    public BlockFace getFacing() {
        return (BlockFace) this.get(CraftBeehive.FACING, BlockFace.class);
    }

    public void setFacing(BlockFace facing) {
        this.set(CraftBeehive.FACING, (Enum) facing);
    }

    public Set getFaces() {
        return this.getValues(CraftBeehive.FACING, BlockFace.class);
    }
}
