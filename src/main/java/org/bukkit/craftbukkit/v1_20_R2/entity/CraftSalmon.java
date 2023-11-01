package org.bukkit.craftbukkit.v1_20_R2.entity;

import net.minecraft.world.entity.animal.AbstractFish;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.entity.Salmon;

public class CraftSalmon extends CraftFish implements Salmon {

    public CraftSalmon(CraftServer server, net.minecraft.world.entity.animal.Salmon entity) {
        super(server, (AbstractFish) entity);
    }

    public net.minecraft.world.entity.animal.Salmon getHandle() {
        return (net.minecraft.world.entity.animal.Salmon) super.getHandle();
    }

    public String toString() {
        return "CraftSalmon";
    }
}
