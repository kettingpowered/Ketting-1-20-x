package org.bukkit.craftbukkit.v1_20_R2.entity;

import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.entity.DragonFireball;

public class CraftDragonFireball extends CraftFireball implements DragonFireball {

    public CraftDragonFireball(CraftServer server, net.minecraft.world.entity.projectile.DragonFireball entity) {
        super(server, (AbstractHurtingProjectile) entity);
    }

    public String toString() {
        return "CraftDragonFireball";
    }
}
