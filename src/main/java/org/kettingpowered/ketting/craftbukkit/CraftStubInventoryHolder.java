package org.kettingpowered.ketting.craftbukkit;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

public class CraftStubInventoryHolder implements InventoryHolder {
    public static final CraftStubInventoryHolder INSTANCE = new CraftStubInventoryHolder();
    private CraftStubInventoryHolder(){}
    @Override
    public @NotNull Inventory getInventory() {
        return CraftStubInventory.INSTANCE;
    }
}
