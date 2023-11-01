package org.bukkit.craftbukkit.v1_20_R2.block.data.type;

import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.bukkit.block.data.type.TurtleEgg;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;

public abstract class CraftTurtleEgg extends CraftBlockData implements TurtleEgg {

    private static final IntegerProperty EGGS = getInteger("eggs");

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
}
