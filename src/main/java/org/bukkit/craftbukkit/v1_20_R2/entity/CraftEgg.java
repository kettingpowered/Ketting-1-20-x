package org.bukkit.craftbukkit.v1_20_R2.entity;

import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.entity.projectile.ThrownEgg;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.entity.Egg;

public class CraftEgg extends CraftThrowableProjectile implements Egg {

    public CraftEgg(CraftServer server, ThrownEgg entity) {
        super(server, (ThrowableItemProjectile) entity);
    }

    public ThrownEgg getHandle() {
        return (ThrownEgg) this.entity;
    }

    public String toString() {
        return "CraftEgg";
    }
}
