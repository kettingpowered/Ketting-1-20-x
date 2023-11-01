package org.bukkit.craftbukkit.v1_20_R2.entity;

import net.minecraft.world.entity.FlyingMob;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.entity.Ghast;

public class CraftGhast extends CraftFlying implements Ghast, CraftEnemy {

    public CraftGhast(CraftServer server, net.minecraft.world.entity.monster.Ghast entity) {
        super(server, (FlyingMob) entity);
    }

    public net.minecraft.world.entity.monster.Ghast getHandle() {
        return (net.minecraft.world.entity.monster.Ghast) this.entity;
    }

    public String toString() {
        return "CraftGhast";
    }

    public boolean isCharging() {
        return this.getHandle().isCharging();
    }

    public void setCharging(boolean flag) {
        this.getHandle().setCharging(flag);
    }
}
