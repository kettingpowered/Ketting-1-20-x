package org.bukkit.craftbukkit.v1_20_R2.entity;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import java.util.Collection;
import java.util.Iterator;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.alchemy.PotionUtils;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.craftbukkit.v1_20_R2.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_20_R2.potion.CraftPotionUtil;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.inventory.ItemStack;

public class CraftThrownPotion extends CraftThrowableProjectile implements ThrownPotion {

    public CraftThrownPotion(CraftServer server, net.minecraft.world.entity.projectile.ThrownPotion entity) {
        super(server, (ThrowableItemProjectile) entity);
    }

    public Collection getEffects() {
        Builder builder = ImmutableList.builder();
        Iterator iterator = PotionUtils.getMobEffects(this.getHandle().getItemRaw()).iterator();

        while (iterator.hasNext()) {
            MobEffectInstance effect = (MobEffectInstance) iterator.next();

            builder.add(CraftPotionUtil.toBukkit(effect));
        }

        return builder.build();
    }

    public ItemStack getItem() {
        return CraftItemStack.asBukkitCopy(this.getHandle().getItemRaw());
    }

    public void setItem(ItemStack item) {
        Preconditions.checkArgument(item != null, "ItemStack cannot be null");
        Preconditions.checkArgument(item.getType() == Material.LINGERING_POTION || item.getType() == Material.SPLASH_POTION, "ItemStack material must be Material.LINGERING_POTION or Material.SPLASH_POTION but was Material.%s", item.getType());
        this.getHandle().setItem(CraftItemStack.asNMSCopy(item));
    }

    public net.minecraft.world.entity.projectile.ThrownPotion getHandle() {
        return (net.minecraft.world.entity.projectile.ThrownPotion) this.entity;
    }
}
