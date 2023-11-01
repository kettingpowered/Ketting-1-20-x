package org.bukkit.craftbukkit.v1_20_R2.inventory;

import net.minecraft.world.Container;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.LlamaInventory;

public class CraftInventoryLlama extends CraftInventoryAbstractHorse implements LlamaInventory {

    public CraftInventoryLlama(Container inventory) {
        super(inventory);
    }

    public ItemStack getDecor() {
        return this.getItem(1);
    }

    public void setDecor(ItemStack stack) {
        this.setItem(1, stack);
    }
}
