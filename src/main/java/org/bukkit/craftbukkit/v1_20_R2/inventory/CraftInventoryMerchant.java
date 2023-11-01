package org.bukkit.craftbukkit.v1_20_R2.inventory;

import net.minecraft.world.inventory.MerchantContainer;
import net.minecraft.world.item.trading.Merchant;
import net.minecraft.world.item.trading.MerchantOffer;
import org.bukkit.inventory.MerchantInventory;
import org.bukkit.inventory.MerchantRecipe;

public class CraftInventoryMerchant extends CraftInventory implements MerchantInventory {

    private final Merchant merchant;

    public CraftInventoryMerchant(Merchant merchant, MerchantContainer inventory) {
        super(inventory);
        this.merchant = merchant;
    }

    public int getSelectedRecipeIndex() {
        return this.getInventory().selectionHint;
    }

    public MerchantRecipe getSelectedRecipe() {
        MerchantOffer nmsRecipe = this.getInventory().getActiveOffer();

        return nmsRecipe == null ? null : nmsRecipe.asBukkit();
    }

    public MerchantContainer getInventory() {
        return (MerchantContainer) this.inventory;
    }

    public org.bukkit.inventory.Merchant getMerchant() {
        return this.merchant.getCraftMerchant();
    }
}
