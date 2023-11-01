package org.bukkit.craftbukkit.v1_20_R2.entity;

import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.entity.EvokerFangs;
import org.bukkit.entity.LivingEntity;

public class CraftEvokerFangs extends CraftEntity implements EvokerFangs {

    public CraftEvokerFangs(CraftServer server, net.minecraft.world.entity.projectile.EvokerFangs entity) {
        super(server, entity);
    }

    public net.minecraft.world.entity.projectile.EvokerFangs getHandle() {
        return (net.minecraft.world.entity.projectile.EvokerFangs) super.getHandle();
    }

    public String toString() {
        return "CraftEvokerFangs";
    }

    public LivingEntity getOwner() {
        net.minecraft.world.entity.LivingEntity owner = this.getHandle().getOwner();

        return owner == null ? null : (LivingEntity) owner.getBukkitEntity();
    }

    public void setOwner(LivingEntity owner) {
        this.getHandle().setOwner(owner == null ? null : ((CraftLivingEntity) owner).getHandle());
    }
}
