package org.bukkit.craftbukkit.v1_20_R2.entity;

import net.minecraft.world.entity.projectile.AbstractArrow;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.entity.SpectralArrow;

public class CraftSpectralArrow extends CraftArrow implements SpectralArrow {

    public CraftSpectralArrow(CraftServer server, net.minecraft.world.entity.projectile.SpectralArrow entity) {
        super(server, (AbstractArrow) entity);
    }

    public net.minecraft.world.entity.projectile.SpectralArrow getHandle() {
        return (net.minecraft.world.entity.projectile.SpectralArrow) this.entity;
    }

    public String toString() {
        return "CraftSpectralArrow";
    }

    public int getGlowingTicks() {
        return this.getHandle().duration;
    }

    public void setGlowingTicks(int duration) {
        this.getHandle().duration = duration;
    }
}
