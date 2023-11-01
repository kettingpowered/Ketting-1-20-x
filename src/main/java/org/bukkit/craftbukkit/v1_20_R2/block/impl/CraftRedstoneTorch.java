package org.bukkit.craftbukkit.v1_20_R2.block.impl;

import net.minecraft.world.level.block.RedstoneTorchBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.bukkit.block.data.Lightable;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;

public final class CraftRedstoneTorch extends CraftBlockData implements Lightable {

    private static final BooleanProperty LIT = getBoolean(RedstoneTorchBlock.class, "lit");

    public CraftRedstoneTorch() {}

    public CraftRedstoneTorch(BlockState state) {
        super(state);
    }

    public boolean isLit() {
        return (Boolean) this.get(CraftRedstoneTorch.LIT);
    }

    public void setLit(boolean lit) {
        this.set((Property) CraftRedstoneTorch.LIT, (Comparable) lit);
    }
}
