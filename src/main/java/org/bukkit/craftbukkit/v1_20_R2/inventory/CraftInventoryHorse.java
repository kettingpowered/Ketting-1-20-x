package org.bukkit.craftbukkit.v1_20_R2.inventory;

import net.minecraft.world.Container;
import org.bukkit.inventory.HorseInventory;
import org.bukkit.inventory.ItemStack;

public class CraftInventoryHorse extends CraftInventoryAbstractHorse implements HorseInventory {

    public CraftInventoryHorse(Container inventory) {
        super(inventory);
    }

    public ItemStack getArmor() {
        return this.getItem(1);
    }

    public void setArmor(ItemStack stack) {
        this.setItem(1, stack);
    }
}
