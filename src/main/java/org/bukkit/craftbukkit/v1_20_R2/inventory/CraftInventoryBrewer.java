package org.bukkit.craftbukkit.v1_20_R2.inventory;

import net.minecraft.world.Container;
import org.bukkit.block.BrewingStand;
import org.bukkit.inventory.BrewerInventory;
import org.bukkit.inventory.ItemStack;

public class CraftInventoryBrewer extends CraftInventory implements BrewerInventory {

    public CraftInventoryBrewer(Container inventory) {
        super(inventory);
    }

    public ItemStack getIngredient() {
        return this.getItem(3);
    }

    public void setIngredient(ItemStack ingredient) {
        this.setItem(3, ingredient);
    }

    public BrewingStand getHolder() {
        return (BrewingStand) this.inventory.getOwner();
    }

    public ItemStack getFuel() {
        return this.getItem(4);
    }

    public void setFuel(ItemStack fuel) {
        this.setItem(4, fuel);
    }
}
