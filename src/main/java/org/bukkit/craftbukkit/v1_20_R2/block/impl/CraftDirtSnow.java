package org.bukkit.craftbukkit.v1_20_R2.block.impl;

import net.minecraft.world.level.block.SnowyDirtBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.bukkit.block.data.Snowable;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;

public final class CraftDirtSnow extends CraftBlockData implements Snowable {

    private static final BooleanProperty SNOWY = getBoolean(SnowyDirtBlock.class, "snowy");

    public CraftDirtSnow() {}

    public CraftDirtSnow(BlockState state) {
        super(state);
    }

    public boolean isSnowy() {
        return (Boolean) this.get(CraftDirtSnow.SNOWY);
    }

    public void setSnowy(boolean snowy) {
        this.set((Property) CraftDirtSnow.SNOWY, (Comparable) snowy);
    }
}
