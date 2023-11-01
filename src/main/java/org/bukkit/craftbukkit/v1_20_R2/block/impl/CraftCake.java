package org.bukkit.craftbukkit.v1_20_R2.block.impl;

import net.minecraft.world.level.block.CakeBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.bukkit.block.data.type.Cake;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;

public final class CraftCake extends CraftBlockData implements Cake {

    private static final IntegerProperty BITES = getInteger(CakeBlock.class, "bites");

    public CraftCake() {}

    public CraftCake(BlockState state) {
        super(state);
    }

    public int getBites() {
        return (Integer) this.get(CraftCake.BITES);
    }

    public void setBites(int bites) {
        this.set((Property) CraftCake.BITES, (Comparable) bites);
    }

    public int getMaximumBites() {
        return getMax(CraftCake.BITES);
    }
}
