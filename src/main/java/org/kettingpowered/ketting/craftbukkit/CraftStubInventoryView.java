package org.kettingpowered.ketting.craftbukkit;

import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.jetbrains.annotations.NotNull;

public class CraftStubInventoryView extends InventoryView {
    public static final CraftStubInventoryView INSTANCE = new CraftStubInventoryView();
    private CraftStubInventoryView(){}
    @Override
    public @NotNull Inventory getTopInventory() {
        return CraftStubInventory.INSTANCE;
    }

    @Override
    public @NotNull Inventory getBottomInventory() {
        return CraftStubInventory.INSTANCE;
    }

    @Override
    public @NotNull HumanEntity getPlayer() {
        return CraftStubPlayer.INSTANCE;
    }

    @Override
    public @NotNull InventoryType getType() {
        return InventoryType.Stub;
    }

    @Override
    public @NotNull String getTitle() {
        return getType().getDefaultTitle();
    }

    @Override
    public @NotNull String getOriginalTitle() {
        return getType().getDefaultTitle();
    }

    @Override
    public void setTitle(@NotNull String title) {}
}
