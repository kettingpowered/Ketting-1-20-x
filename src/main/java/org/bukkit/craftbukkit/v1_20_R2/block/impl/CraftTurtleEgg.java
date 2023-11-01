package org.bukkit.craftbukkit.v1_20_R2.block.impl;

import net.minecraft.world.level.block.TurtleEggBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.bukkit.block.data.Hatchable;
import org.bukkit.block.data.type.TurtleEgg;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;

public final class CraftTurtleEgg extends CraftBlockData implements TurtleEgg, Hatchable {

    private static final IntegerProperty EGGS = getInteger(TurtleEggBlock.class, "eggs");
    private static final IntegerProperty HATCH = getInteger(TurtleEggBlock.class, "hatch");

    public CraftTurtleEgg() {}

    public CraftTurtleEgg(BlockState state) {
        super(state);
    }

    public int getEggs() {
        return (Integer) this.get(CraftTurtleEgg.EGGS);
    }

    public void setEggs(int eggs) {
        this.set((Property) CraftTurtleEgg.EGGS, (Comparable) eggs);
    }

    public int getMinimumEggs() {
        return getMin(CraftTurtleEgg.EGGS);
    }

    public int getMaximumEggs() {
        return getMax(CraftTurtleEgg.EGGS);
    }

    public int getHatch() {
        return (Integer) this.get(CraftTurtleEgg.HATCH);
    }

    public void setHatch(int hatch) {
        this.set((Property) CraftTurtleEgg.HATCH, (Comparable) hatch);
    }

    public int getMaximumHatch() {
        return getMax(CraftTurtleEgg.HATCH);
    }
}
