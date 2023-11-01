package org.bukkit.craftbukkit.v1_20_R2.inventory;

import com.google.common.base.Preconditions;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.Merchant;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;
import org.bukkit.craftbukkit.v1_20_R2.util.CraftChatMessage;

public class CraftMerchantCustom extends CraftMerchant {

    public CraftMerchantCustom(String title) {
        super(new CraftMerchantCustom.MinecraftMerchant(title));
        this.getMerchant().craftMerchant = this;
    }

    public String toString() {
        return "CraftMerchantCustom";
    }

    public CraftMerchantCustom.MinecraftMerchant getMerchant() {
        return (CraftMerchantCustom.MinecraftMerchant) super.getMerchant();
    }

    public static class MinecraftMerchant implements Merchant {

        private final Component title;
        private final MerchantOffers trades = new MerchantOffers();
        private Player tradingPlayer;
        protected CraftMerchant craftMerchant;

        public MinecraftMerchant(String title) {
            Preconditions.checkArgument(title != null, "Title cannot be null");
            this.title = CraftChatMessage.fromString(title)[0];
        }

        public CraftMerchant getCraftMerchant() {
            return this.craftMerchant;
        }

        public void setTradingPlayer(Player entityhuman) {
            this.tradingPlayer = entityhuman;
        }

        public Player getTradingPlayer() {
            return this.tradingPlayer;
        }

        public MerchantOffers getOffers() {
            return this.trades;
        }

        public void notifyTrade(MerchantOffer merchantrecipe) {
            merchantrecipe.increaseUses();
        }

        public void notifyTradeUpdated(ItemStack itemstack) {}

        public Component getScoreboardDisplayName() {
            return this.title;
        }

        public int getVillagerXp() {
            return 0;
        }

        public void overrideXp(int i) {}

        public boolean showProgressBar() {
            return false;
        }

        public SoundEvent getNotifyTradeSound() {
            return SoundEvents.VILLAGER_YES;
        }

        public void overrideOffers(MerchantOffers merchantrecipelist) {}

        public boolean isClientSide() {
            return false;
        }
    }
}
