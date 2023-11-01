package org.bukkit.craftbukkit.v1_20_R2.entity;

import java.util.List;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.npc.Villager;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.craftbukkit.v1_20_R2.inventory.CraftInventory;
import org.bukkit.craftbukkit.v1_20_R2.inventory.CraftMerchant;
import org.bukkit.entity.AbstractVillager;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.MerchantRecipe;

public class CraftAbstractVillager extends CraftAgeable implements AbstractVillager, InventoryHolder {

    public CraftAbstractVillager(CraftServer server, net.minecraft.world.entity.npc.AbstractVillager entity) {
        super(server, (AgeableMob) entity);
    }

    public net.minecraft.world.entity.npc.AbstractVillager getHandle() {
        return (Villager) this.entity;
    }

    public String toString() {
        return "CraftAbstractVillager";
    }

    public Inventory getInventory() {
        return new CraftInventory(this.getHandle().getInventory());
    }

    private CraftMerchant getMerchant() {
        return this.getHandle().getCraftMerchant();
    }

    public List getRecipes() {
        return this.getMerchant().getRecipes();
    }

    public void setRecipes(List recipes) {
        this.getMerchant().setRecipes(recipes);
    }

    public MerchantRecipe getRecipe(int i) {
        return this.getMerchant().getRecipe(i);
    }

    public void setRecipe(int i, MerchantRecipe merchantRecipe) {
        this.getMerchant().setRecipe(i, merchantRecipe);
    }

    public int getRecipeCount() {
        return this.getMerchant().getRecipeCount();
    }

    public boolean isTrading() {
        return this.getTrader() != null;
    }

    public HumanEntity getTrader() {
        return this.getMerchant().getTrader();
    }
}
