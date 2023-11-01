package org.bukkit.craftbukkit.v1_20_R2.entity;

import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.craftbukkit.v1_20_R2.inventory.CraftItemStack;
import org.bukkit.entity.ThrowableProjectile;
import org.bukkit.inventory.ItemStack;

public abstract class CraftThrowableProjectile extends CraftProjectile implements ThrowableProjectile {

    public CraftThrowableProjectile(CraftServer server, ThrowableItemProjectile entity) {
        super(server, (Projectile) entity);
    }

    public ItemStack getItem() {
        return this.getHandle().getItemRaw().isEmpty() ? CraftItemStack.asBukkitCopy(new net.minecraft.world.item.ItemStack(this.getHandle().getDefaultItemPublic())) : CraftItemStack.asBukkitCopy(this.getHandle().getItemRaw());
    }

    public void setItem(ItemStack item) {
        this.getHandle().setItem(CraftItemStack.asNMSCopy(item));
    }

    public ThrowableItemProjectile getHandle() {
        return (ThrowableItemProjectile) this.entity;
    }
}
