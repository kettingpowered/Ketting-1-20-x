package org.bukkit.craftbukkit.v1_20_R2.inventory;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.Merchant;
import org.bukkit.inventory.MerchantRecipe;

public class CraftMerchant implements Merchant {

    protected final net.minecraft.world.item.trading.Merchant merchant;

    public CraftMerchant(net.minecraft.world.item.trading.Merchant merchant) {
        this.merchant = merchant;
    }

    public net.minecraft.world.item.trading.Merchant getMerchant() {
        return this.merchant;
    }

    public List getRecipes() {
        return Collections.unmodifiableList(Lists.transform(this.merchant.getOffers(), new Function() {
            public MerchantRecipe apply(MerchantOffer recipe) {
                return recipe.asBukkit();
            }
        }));
    }

    public void setRecipes(List recipes) {
        MerchantOffers recipesList = this.merchant.getOffers();

        recipesList.clear();
        Iterator iterator = recipes.iterator();

        while (iterator.hasNext()) {
            MerchantRecipe recipe = (MerchantRecipe) iterator.next();

            recipesList.add(CraftMerchantRecipe.fromBukkit(recipe).toMinecraft());
        }

    }

    public MerchantRecipe getRecipe(int i) {
        return ((MerchantOffer) this.merchant.getOffers().get(i)).asBukkit();
    }

    public void setRecipe(int i, MerchantRecipe merchantRecipe) {
        this.merchant.getOffers().set(i, CraftMerchantRecipe.fromBukkit(merchantRecipe).toMinecraft());
    }

    public int getRecipeCount() {
        return this.merchant.getOffers().size();
    }

    public boolean isTrading() {
        return this.getTrader() != null;
    }

    public HumanEntity getTrader() {
        Player eh = this.merchant.getTradingPlayer();

        return eh == null ? null : eh.getBukkitEntity();
    }

    public int hashCode() {
        return this.merchant.hashCode();
    }

    public boolean equals(Object obj) {
        return obj instanceof CraftMerchant && ((CraftMerchant) obj).merchant.equals(this.merchant);
    }
}
