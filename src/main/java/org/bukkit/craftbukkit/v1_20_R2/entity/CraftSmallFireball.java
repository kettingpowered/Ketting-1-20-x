package org.bukkit.craftbukkit.v1_20_R2.entity;

import net.minecraft.world.entity.projectile.Fireball;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.entity.SmallFireball;

public class CraftSmallFireball extends CraftSizedFireball implements SmallFireball {

    public CraftSmallFireball(CraftServer server, net.minecraft.world.entity.projectile.SmallFireball entity) {
        super(server, (Fireball) entity);
    }

    public net.minecraft.world.entity.projectile.SmallFireball getHandle() {
        return (net.minecraft.world.entity.projectile.SmallFireball) this.entity;
    }

    public String toString() {
        return "CraftSmallFireball";
    }
}
