package org.bukkit.craftbukkit.v1_20_R2.entity;

import com.google.common.base.Preconditions;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.entity.vehicle.MinecartTNT;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.entity.minecart.ExplosiveMinecart;

public final class CraftMinecartTNT extends CraftMinecart implements ExplosiveMinecart {

    CraftMinecartTNT(CraftServer server, MinecartTNT entity) {
        super(server, (AbstractMinecart) entity);
    }

    public void setFuseTicks(int ticks) {
        this.getHandle().fuse = ticks;
    }

    public int getFuseTicks() {
        return this.getHandle().getFuse();
    }

    public void ignite() {
        this.getHandle().primeFuse();
    }

    public boolean isIgnited() {
        return this.getHandle().isPrimed();
    }

    public void explode() {
        this.getHandle().explode(this.getHandle().getDeltaMovement().horizontalDistanceSqr());
    }

    public void explode(double power) {
        Preconditions.checkArgument(0.0D <= power && power <= 5.0D, "Power must be in range [0, 5] (got %s)", power);
        this.getHandle().explode(power);
    }

    public MinecartTNT getHandle() {
        return (MinecartTNT) super.getHandle();
    }

    public String toString() {
        return "CraftMinecartTNT";
    }
}
