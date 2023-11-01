package org.bukkit.craftbukkit.v1_20_R2.entity;

import net.minecraft.world.entity.animal.AbstractGolem;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.entity.IronGolem;

public class CraftIronGolem extends CraftGolem implements IronGolem {

    public CraftIronGolem(CraftServer server, net.minecraft.world.entity.animal.IronGolem entity) {
        super(server, (AbstractGolem) entity);
    }

    public net.minecraft.world.entity.animal.IronGolem getHandle() {
        return (net.minecraft.world.entity.animal.IronGolem) this.entity;
    }

    public String toString() {
        return "CraftIronGolem";
    }

    public boolean isPlayerCreated() {
        return this.getHandle().isPlayerCreated();
    }

    public void setPlayerCreated(boolean playerCreated) {
        this.getHandle().setPlayerCreated(playerCreated);
    }
}
