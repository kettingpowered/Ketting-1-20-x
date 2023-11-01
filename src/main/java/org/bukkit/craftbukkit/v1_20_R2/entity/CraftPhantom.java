package org.bukkit.craftbukkit.v1_20_R2.entity;

import net.minecraft.world.entity.FlyingMob;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.entity.Phantom;

public class CraftPhantom extends CraftFlying implements Phantom, CraftEnemy {

    public CraftPhantom(CraftServer server, net.minecraft.world.entity.monster.Phantom entity) {
        super(server, (FlyingMob) entity);
    }

    public net.minecraft.world.entity.monster.Phantom getHandle() {
        return (net.minecraft.world.entity.monster.Phantom) super.getHandle();
    }

    public int getSize() {
        return this.getHandle().getPhantomSize();
    }

    public void setSize(int sz) {
        this.getHandle().setPhantomSize(sz);
    }

    public String toString() {
        return "CraftPhantom";
    }
}
