package org.bukkit.craftbukkit.v1_20_R2.entity;

import com.google.common.base.Preconditions;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.entity.Fireball;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.util.Vector;

public class CraftFireball extends AbstractProjectile implements Fireball {

    public CraftFireball(CraftServer server, AbstractHurtingProjectile entity) {
        super(server, entity);
    }

    public float getYield() {
        return this.getHandle().bukkitYield;
    }

    public boolean isIncendiary() {
        return this.getHandle().isIncendiary;
    }

    public void setIsIncendiary(boolean isIncendiary) {
        this.getHandle().isIncendiary = isIncendiary;
    }

    public void setYield(float yield) {
        this.getHandle().bukkitYield = yield;
    }

    public ProjectileSource getShooter() {
        return this.getHandle().projectileSource;
    }

    public void setShooter(ProjectileSource shooter) {
        if (shooter instanceof CraftLivingEntity) {
            this.getHandle().setOwner(((CraftLivingEntity) shooter).getHandle());
        } else {
            this.getHandle().setOwner((Entity) null);
        }

        this.getHandle().projectileSource = shooter;
    }

    public Vector getDirection() {
        return new Vector(this.getHandle().xPower, this.getHandle().yPower, this.getHandle().zPower);
    }

    public void setDirection(Vector direction) {
        Preconditions.checkArgument(direction != null, "Vector direction cannot be null");
        this.getHandle().setDirection(direction.getX(), direction.getY(), direction.getZ());
        this.update();
    }

    public AbstractHurtingProjectile getHandle() {
        return (AbstractHurtingProjectile) this.entity;
    }

    public String toString() {
        return "CraftFireball";
    }
}
