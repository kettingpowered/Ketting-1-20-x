package org.bukkit.craftbukkit.v1_20_R2.block.data.type;

import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.bukkit.block.data.type.Farmland;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;

public abstract class CraftFarmland extends CraftBlockData implements Farmland {

    private static final IntegerProperty MOISTURE = getInteger("moisture");

    public int getMoisture() {
        return (Integer) this.get(CraftFarmland.MOISTURE);
    }

    public void setMoisture(int moisture) {
        this.set((Property) CraftFarmland.MOISTURE, (Comparable) moisture);
    }

    public int getMaximumMoisture() {
        return getMax(CraftFarmland.MOISTURE);
    }
}
