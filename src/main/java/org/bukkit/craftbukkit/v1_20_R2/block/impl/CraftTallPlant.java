package org.bukkit.craftbukkit.v1_20_R2.block.impl;

import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import org.bukkit.block.data.Bisected;
import org.bukkit.block.data.Bisected.Half;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;

public final class CraftTallPlant extends CraftBlockData implements Bisected {

    private static final EnumProperty HALF = getEnum(DoublePlantBlock.class, "half");

    public CraftTallPlant() {}

    public CraftTallPlant(BlockState state) {
        super(state);
    }

    public Half getHalf() {
        return (Half) this.get(CraftTallPlant.HALF, Half.class);
    }

    public void setHalf(Half half) {
        this.set(CraftTallPlant.HALF, (Enum) half);
    }
}
