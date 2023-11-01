package org.bukkit.craftbukkit.v1_20_R2.entity;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.entity.Projectile;
import org.bukkit.projectiles.ProjectileSource;

public abstract class CraftProjectile extends AbstractProjectile implements Projectile {

    public CraftProjectile(CraftServer server, net.minecraft.world.entity.projectile.Projectile entity) {
        super(server, entity);
    }

    public ProjectileSource getShooter() {
        return this.getHandle().projectileSource;
    }

    public void setShooter(ProjectileSource shooter) {
        if (shooter instanceof CraftLivingEntity) {
            this.getHandle().setOwner((LivingEntity) ((CraftLivingEntity) shooter).entity);
        } else {
            this.getHandle().setOwner((Entity) null);
        }

        this.getHandle().projectileSource = shooter;
    }

    public net.minecraft.world.entity.projectile.Projectile getHandle() {
        return (net.minecraft.world.entity.projectile.Projectile) this.entity;
    }

    public String toString() {
        return "CraftProjectile";
    }
}
