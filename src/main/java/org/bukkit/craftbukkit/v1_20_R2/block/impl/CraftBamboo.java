package org.bukkit.craftbukkit.v1_20_R2.block.impl;

import net.minecraft.world.level.block.BambooStalkBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.type.Bamboo;
import org.bukkit.block.data.type.Bamboo.Leaves;
import org.bukkit.block.data.type.Sapling;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;

public final class CraftBamboo extends CraftBlockData implements Bamboo, Ageable, Sapling {

    private static final EnumProperty LEAVES = getEnum(BambooStalkBlock.class, "leaves");
    private static final IntegerProperty AGE = getInteger(BambooStalkBlock.class, "age");
    private static final IntegerProperty STAGE = getInteger(BambooStalkBlock.class, "stage");

    public CraftBamboo() {}

    public CraftBamboo(BlockState state) {
        super(state);
    }

    public Leaves getLeaves() {
        return (Leaves) this.get(CraftBamboo.LEAVES, Leaves.class);
    }

    public void setLeaves(Leaves leaves) {
        this.set(CraftBamboo.LEAVES, (Enum) leaves);
    }

    public int getAge() {
        return (Integer) this.get(CraftBamboo.AGE);
    }

    public void setAge(int age) {
        this.set((Property) CraftBamboo.AGE, (Comparable) age);
    }

    public int getMaximumAge() {
        return getMax(CraftBamboo.AGE);
    }

    public int getStage() {
        return (Integer) this.get(CraftBamboo.STAGE);
    }

    public void setStage(int stage) {
        this.set((Property) CraftBamboo.STAGE, (Comparable) stage);
    }

    public int getMaximumStage() {
        return getMax(CraftBamboo.STAGE);
    }
}
