package org.bukkit.craftbukkit.v1_20_R2.entity;

import com.google.common.base.Preconditions;
import net.minecraft.core.BlockPos;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.entity.AbstractArrow;
import org.bukkit.entity.AbstractArrow.PickupStatus;
import org.bukkit.entity.Entity;
import org.bukkit.projectiles.ProjectileSource;

public class CraftArrow extends AbstractProjectile implements AbstractArrow {

    public CraftArrow(CraftServer server, net.minecraft.world.entity.projectile.AbstractArrow entity) {
        super(server, entity);
    }

    public void setKnockbackStrength(int knockbackStrength) {
        Preconditions.checkArgument(knockbackStrength >= 0, "Knockback value (%s) cannot be negative", knockbackStrength);
        this.getHandle().setKnockback(knockbackStrength);
    }

    public int getKnockbackStrength() {
        return this.getHandle().knockback;
    }

    public double getDamage() {
        return this.getHandle().getBaseDamage();
    }

    public void setDamage(double damage) {
        Preconditions.checkArgument(damage >= 0.0D, "Damage value (%s) must be positive", damage);
        this.getHandle().setBaseDamage(damage);
    }

    public int getPierceLevel() {
        return this.getHandle().getPierceLevel();
    }

    public void setPierceLevel(int pierceLevel) {
        Preconditions.checkArgument(pierceLevel >= 0 && pierceLevel <= 127, "Pierce level (%s) out of range, expected 0 < level < 127", pierceLevel);
        this.getHandle().setPierceLevel((byte) pierceLevel);
    }

    public boolean isCritical() {
        return this.getHandle().isCritArrow();
    }

    public void setCritical(boolean critical) {
        this.getHandle().setCritArrow(critical);
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

    public boolean isInBlock() {
        return this.getHandle().inGround;
    }

    public Block getAttachedBlock() {
        if (!this.isInBlock()) {
            return null;
        } else {
            BlockPos pos = this.getHandle().blockPosition();

            return this.getWorld().getBlockAt(pos.getX(), pos.getY(), pos.getZ());
        }
    }

    public PickupStatus getPickupStatus() {
        return PickupStatus.values()[this.getHandle().pickup.ordinal()];
    }

    public void setPickupStatus(PickupStatus status) {
        Preconditions.checkArgument(status != null, "PickupStatus cannot be null");
        this.getHandle().pickup = net.minecraft.world.entity.projectile.AbstractArrow.Pickup.byOrdinal(status.ordinal());
    }

    public void setTicksLived(int value) {
        super.setTicksLived(value);
        this.getHandle().life = value;
    }

    public boolean isShotFromCrossbow() {
        return this.getHandle().shotFromCrossbow();
    }

    public void setShotFromCrossbow(boolean shotFromCrossbow) {
        this.getHandle().setShotFromCrossbow(shotFromCrossbow);
    }

    public net.minecraft.world.entity.projectile.AbstractArrow getHandle() {
        return (net.minecraft.world.entity.projectile.AbstractArrow) this.entity;
    }

    public String toString() {
        return "CraftArrow";
    }
}
