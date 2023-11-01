package org.bukkit.craftbukkit.v1_20_R2.entity;

import net.minecraft.world.entity.Entity;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.entity.Projectile;

public abstract class AbstractProjectile extends CraftEntity implements Projectile {

    public AbstractProjectile(CraftServer server, Entity entity) {
        super(server, entity);
    }

    public boolean doesBounce() {
        return false;
    }

    public void setBounce(boolean doesBounce) {}
}
