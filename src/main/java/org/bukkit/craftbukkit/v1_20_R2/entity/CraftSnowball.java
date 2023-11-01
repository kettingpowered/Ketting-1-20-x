package org.bukkit.craftbukkit.v1_20_R2.entity;

import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.entity.Snowball;

public class CraftSnowball extends CraftThrowableProjectile implements Snowball {

    public CraftSnowball(CraftServer server, net.minecraft.world.entity.projectile.Snowball entity) {
        super(server, (ThrowableItemProjectile) entity);
    }

    public net.minecraft.world.entity.projectile.Snowball getHandle() {
        return (net.minecraft.world.entity.projectile.Snowball) this.entity;
    }

    public String toString() {
        return "CraftSnowball";
    }
}
