package org.bukkit.craftbukkit.v1_20_R2.entity;

import com.google.common.base.Preconditions;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ShulkerBullet;
import org.bukkit.projectiles.ProjectileSource;

public class CraftShulkerBullet extends AbstractProjectile implements ShulkerBullet {

    public CraftShulkerBullet(CraftServer server, net.minecraft.world.entity.projectile.ShulkerBullet entity) {
        super(server, entity);
    }

    public ProjectileSource getShooter() {
        return this.getHandle().projectileSource;
    }

    public void setShooter(ProjectileSource shooter) {
        if (shooter instanceof Entity) {
            this.getHandle().setOwner(((CraftEntity) shooter).getHandle());
        } else {
            this.getHandle().setOwner((net.minecraft.world.entity.Entity) null);
        }

        this.getHandle().projectileSource = shooter;
    }

    public Entity getTarget() {
        return this.getHandle().getTarget() != null ? this.getHandle().getTarget().getBukkitEntity() : null;
    }

    public void setTarget(Entity target) {
        Preconditions.checkState(!this.getHandle().generation, "Cannot set target during world generation");
        this.getHandle().setTarget(target == null ? null : ((CraftEntity) target).getHandle());
    }

    public String toString() {
        return "CraftShulkerBullet";
    }

    public net.minecraft.world.entity.projectile.ShulkerBullet getHandle() {
        return (net.minecraft.world.entity.projectile.ShulkerBullet) this.entity;
    }
}
