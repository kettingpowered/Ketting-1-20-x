package org.bukkit.craftbukkit.v1_20_R2.entity;

import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.entity.LlamaSpit;
import org.bukkit.projectiles.ProjectileSource;

public class CraftLlamaSpit extends AbstractProjectile implements LlamaSpit {

    public CraftLlamaSpit(CraftServer server, net.minecraft.world.entity.projectile.LlamaSpit entity) {
        super(server, entity);
    }

    public net.minecraft.world.entity.projectile.LlamaSpit getHandle() {
        return (net.minecraft.world.entity.projectile.LlamaSpit) super.getHandle();
    }

    public String toString() {
        return "CraftLlamaSpit";
    }

    public ProjectileSource getShooter() {
        return this.getHandle().getOwner() != null ? (ProjectileSource) this.getHandle().getOwner().getBukkitEntity() : null;
    }

    public void setShooter(ProjectileSource source) {
        this.getHandle().setOwner(source != null ? ((CraftLivingEntity) source).getHandle() : null);
    }
}
