package org.bukkit.craftbukkit.v1_20_R2.block.data.type;

import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.bukkit.block.data.type.PinkPetals;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;

public abstract class CraftPinkPetals extends CraftBlockData implements PinkPetals {

    private static final IntegerProperty FLOWER_AMOUNT = getInteger("flower_amount");

    public int getFlowerAmount() {
        return (Integer) this.get(CraftPinkPetals.FLOWER_AMOUNT);
    }

    public void setFlowerAmount(int flower_amount) {
        this.set((Property) CraftPinkPetals.FLOWER_AMOUNT, (Comparable) flower_amount);
    }

    public int getMaximumFlowerAmount() {
        return getMax(CraftPinkPetals.FLOWER_AMOUNT);
    }
}
