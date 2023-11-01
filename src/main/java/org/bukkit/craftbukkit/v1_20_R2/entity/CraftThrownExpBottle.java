package org.bukkit.craftbukkit.v1_20_R2.entity;

import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.entity.projectile.ThrownExperienceBottle;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.entity.ThrownExpBottle;

public class CraftThrownExpBottle extends CraftThrowableProjectile implements ThrownExpBottle {

    public CraftThrownExpBottle(CraftServer server, ThrownExperienceBottle entity) {
        super(server, (ThrowableItemProjectile) entity);
    }

    public ThrownExperienceBottle getHandle() {
        return (ThrownExperienceBottle) this.entity;
    }

    public String toString() {
        return "EntityThrownExpBottle";
    }
}
