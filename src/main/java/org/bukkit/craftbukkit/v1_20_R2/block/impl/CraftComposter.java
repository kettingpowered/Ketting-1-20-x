package org.bukkit.craftbukkit.v1_20_R2.block.impl;

import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.bukkit.block.data.Levelled;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;

public final class CraftComposter extends CraftBlockData implements Levelled {

    private static final IntegerProperty LEVEL = getInteger(ComposterBlock.class, "level");

    public CraftComposter() {}

    public CraftComposter(BlockState state) {
        super(state);
    }

    public int getLevel() {
        return (Integer) this.get(CraftComposter.LEVEL);
    }

    public void setLevel(int level) {
        this.set((Property) CraftComposter.LEVEL, (Comparable) level);
    }

    public int getMaximumLevel() {
        return getMax(CraftComposter.LEVEL);
    }
}
