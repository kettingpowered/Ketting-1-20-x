package org.bukkit.craftbukkit.v1_20_R2.inventory;

import net.minecraft.world.Container;
import org.bukkit.inventory.AbstractHorseInventory;
import org.bukkit.inventory.ItemStack;

public class CraftInventoryAbstractHorse extends CraftInventory implements AbstractHorseInventory {

    public CraftInventoryAbstractHorse(Container inventory) {
        super(inventory);
    }

    public ItemStack getSaddle() {
        return this.getItem(0);
    }

    public void setSaddle(ItemStack stack) {
        this.setItem(0, stack);
    }
}
