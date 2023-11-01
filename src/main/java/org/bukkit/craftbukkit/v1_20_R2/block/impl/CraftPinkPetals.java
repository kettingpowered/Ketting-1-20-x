package org.bukkit.craftbukkit.v1_20_R2.block.impl;

import java.util.Set;
import net.minecraft.world.level.block.PinkPetalsBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.type.PinkPetals;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;

public final class CraftPinkPetals extends CraftBlockData implements PinkPetals, Directional {

    private static final IntegerProperty FLOWER_AMOUNT = getInteger(PinkPetalsBlock.class, "flower_amount");
    private static final EnumProperty FACING = getEnum(PinkPetalsBlock.class, "facing");

    public CraftPinkPetals() {}

    public CraftPinkPetals(BlockState state) {
        super(state);
    }

    public int getFlowerAmount() {
        return (Integer) this.get(CraftPinkPetals.FLOWER_AMOUNT);
    }

    public void setFlowerAmount(int flower_amount) {
        this.set((Property) CraftPinkPetals.FLOWER_AMOUNT, (Comparable) flower_amount);
    }

    public int getMaximumFlowerAmount() {
        return getMax(CraftPinkPetals.FLOWER_AMOUNT);
    }

    public BlockFace getFacing() {
        return (BlockFace) this.get(CraftPinkPetals.FACING, BlockFace.class);
    }

    public void setFacing(BlockFace facing) {
        this.set(CraftPinkPetals.FACING, (Enum) facing);
    }

    public Set getFaces() {
        return this.getValues(CraftPinkPetals.FACING, BlockFace.class);
    }
}
