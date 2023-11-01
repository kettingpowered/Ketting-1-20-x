package org.bukkit.craftbukkit.v1_20_R2.block.impl;

import net.minecraft.world.level.block.TntBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.bukkit.block.data.type.TNT;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;

public final class CraftTNT extends CraftBlockData implements TNT {

    private static final BooleanProperty UNSTABLE = getBoolean(TntBlock.class, "unstable");

    public CraftTNT() {}

    public CraftTNT(BlockState state) {
        super(state);
    }

    public boolean isUnstable() {
        return (Boolean) this.get(CraftTNT.UNSTABLE);
    }

    public void setUnstable(boolean unstable) {
        this.set((Property) CraftTNT.UNSTABLE, (Comparable) unstable);
    }
}
