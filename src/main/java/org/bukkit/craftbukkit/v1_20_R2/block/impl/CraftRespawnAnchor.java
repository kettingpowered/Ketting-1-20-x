package org.bukkit.craftbukkit.v1_20_R2.block.impl;

import net.minecraft.world.level.block.RespawnAnchorBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.bukkit.block.data.type.RespawnAnchor;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;

public final class CraftRespawnAnchor extends CraftBlockData implements RespawnAnchor {

    private static final IntegerProperty CHARGES = getInteger(RespawnAnchorBlock.class, "charges");

    public CraftRespawnAnchor() {}

    public CraftRespawnAnchor(BlockState state) {
        super(state);
    }

    public int getCharges() {
        return (Integer) this.get(CraftRespawnAnchor.CHARGES);
    }

    public void setCharges(int charges) {
        this.set((Property) CraftRespawnAnchor.CHARGES, (Comparable) charges);
    }

    public int getMaximumCharges() {
        return getMax(CraftRespawnAnchor.CHARGES);
    }
}
