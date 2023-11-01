package org.bukkit.craftbukkit.v1_20_R2.inventory;

import com.google.common.base.Preconditions;
import java.util.List;
import net.minecraft.world.item.trading.MerchantOffer;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;

public class CraftMerchantRecipe extends MerchantRecipe {

    private final MerchantOffer handle;

    public CraftMerchantRecipe(MerchantOffer merchantRecipe) {
        super(CraftItemStack.asBukkitCopy(merchantRecipe.result), 0);
        this.handle = merchantRecipe;
        this.addIngredient(CraftItemStack.asBukkitCopy(merchantRecipe.baseCostA));
        this.addIngredient(CraftItemStack.asBukkitCopy(merchantRecipe.costB));
    }

    /** @deprecated */
    @Deprecated
    public CraftMerchantRecipe(ItemStack result, int uses, int maxUses, boolean experienceReward, int experience, float priceMultiplier) {
        this(result, uses, maxUses, experienceReward, experience, priceMultiplier, 0, 0);
    }

    public CraftMerchantRecipe(ItemStack result, int uses, int maxUses, boolean experienceReward, int experience, float priceMultiplier, int demand, int specialPrice) {
        super(result, uses, maxUses, experienceReward, experience, priceMultiplier, demand, specialPrice);
        this.handle = new MerchantOffer(net.minecraft.world.item.ItemStack.EMPTY, net.minecraft.world.item.ItemStack.EMPTY, CraftItemStack.asNMSCopy(result), uses, maxUses, experience, priceMultiplier, demand, this);
        this.setSpecialPrice(specialPrice);
        this.setExperienceReward(experienceReward);
    }

    public int getSpecialPrice() {
        return this.handle.getSpecialPriceDiff();
    }

    public void setSpecialPrice(int specialPrice) {
        this.handle.specialPriceDiff = specialPrice;
    }

    public int getDemand() {
        return this.handle.demand;
    }

    public void setDemand(int demand) {
        this.handle.demand = demand;
    }

    public int getUses() {
        return this.handle.uses;
    }

    public void setUses(int uses) {
        this.handle.uses = uses;
    }

    public int getMaxUses() {
        return this.handle.maxUses;
    }

    public void setMaxUses(int maxUses) {
        this.handle.maxUses = maxUses;
    }

    public boolean hasExperienceReward() {
        return this.handle.rewardExp;
    }

    public void setExperienceReward(boolean flag) {
        this.handle.rewardExp = flag;
    }

    public int getVillagerExperience() {
        return this.handle.xp;
    }

    public void setVillagerExperience(int villagerExperience) {
        this.handle.xp = villagerExperience;
    }

    public float getPriceMultiplier() {
        return this.handle.priceMultiplier;
    }

    public void setPriceMultiplier(float priceMultiplier) {
        this.handle.priceMultiplier = priceMultiplier;
    }

    public MerchantOffer toMinecraft() {
        List ingredients = this.getIngredients();

        Preconditions.checkState(!ingredients.isEmpty(), "No offered ingredients");
        this.handle.baseCostA = CraftItemStack.asNMSCopy((ItemStack) ingredients.get(0));
        if (ingredients.size() > 1) {
            this.handle.costB = CraftItemStack.asNMSCopy((ItemStack) ingredients.get(1));
        }

        return this.handle;
    }

    public static CraftMerchantRecipe fromBukkit(MerchantRecipe recipe) {
        if (recipe instanceof CraftMerchantRecipe) {
            return (CraftMerchantRecipe) recipe;
        } else {
            CraftMerchantRecipe craft = new CraftMerchantRecipe(recipe.getResult(), recipe.getUses(), recipe.getMaxUses(), recipe.hasExperienceReward(), recipe.getVillagerExperience(), recipe.getPriceMultiplier(), recipe.getDemand(), recipe.getSpecialPrice());

            craft.setIngredients(recipe.getIngredients());
            return craft;
        }
    }
}
