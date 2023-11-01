package org.bukkit.craftbukkit.v1_20_R2.inventory;

import net.minecraft.world.Container;
import org.bukkit.inventory.EnchantingInventory;
import org.bukkit.inventory.ItemStack;

public class CraftInventoryEnchanting extends CraftInventory implements EnchantingInventory {

    public CraftInventoryEnchanting(Container inventory) {
        super(inventory);
    }

    public void setItem(ItemStack item) {
        this.setItem(0, item);
    }

    public ItemStack getItem() {
        return this.getItem(0);
    }

    public void setSecondary(ItemStack item) {
        this.setItem(1, item);
    }

    public ItemStack getSecondary() {
        return this.getItem(1);
    }
}
