package org.bukkit.craftbukkit.v1_20_R2.block.data.type;

import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.bukkit.block.data.type.Cake;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;

public abstract class CraftCake extends CraftBlockData implements Cake {

    private static final IntegerProperty BITES = getInteger("bites");

    public int getBites() {
        return (Integer) this.get(CraftCake.BITES);
    }

    public void setBites(int bites) {
        this.set((Property) CraftCake.BITES, (Comparable) bites);
    }

    public int getMaximumBites() {
        return getMax(CraftCake.BITES);
    }
}
