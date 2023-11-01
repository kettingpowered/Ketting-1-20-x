package org.bukkit.craftbukkit.v1_20_R2.entity;

import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ThrownTrident;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.craftbukkit.v1_20_R2.inventory.CraftItemStack;
import org.bukkit.entity.Trident;
import org.bukkit.inventory.ItemStack;

public class CraftTrident extends CraftArrow implements Trident {

    public CraftTrident(CraftServer server, ThrownTrident entity) {
        super(server, (AbstractArrow) entity);
    }

    public ThrownTrident getHandle() {
        return (ThrownTrident) super.getHandle();
    }

    public ItemStack getItem() {
        return CraftItemStack.asBukkitCopy(this.getHandle().tridentItem);
    }

    public void setItem(ItemStack itemStack) {
        this.getHandle().tridentItem = CraftItemStack.asNMSCopy(itemStack);
    }

    public String toString() {
        return "CraftTrident";
    }
}
