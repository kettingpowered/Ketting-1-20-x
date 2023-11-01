package org.bukkit.craftbukkit.v1_20_R2.block.data;

import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.bukkit.block.data.Ageable;

public abstract class CraftAgeable extends CraftBlockData implements Ageable {

    private static final IntegerProperty AGE = getInteger("age");

    public int getAge() {
        return (Integer) this.get(CraftAgeable.AGE);
    }

    public void setAge(int age) {
        this.set((Property) CraftAgeable.AGE, (Comparable) age);
    }

    public int getMaximumAge() {
        return getMax(CraftAgeable.AGE);
    }
}
