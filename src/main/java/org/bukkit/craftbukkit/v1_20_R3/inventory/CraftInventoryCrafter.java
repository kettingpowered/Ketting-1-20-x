package org.bukkit.craftbukkit.v1_20_R3.inventory;

import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.inventory.ResultContainer;
import org.bukkit.inventory.CrafterInventory;

public class CraftInventoryCrafter extends CraftResultInventory implements CrafterInventory {

    public CraftInventoryCrafter(CraftingContainer inventory, ResultContainer resultInventory) {
        super(inventory, resultInventory);
    }
}
