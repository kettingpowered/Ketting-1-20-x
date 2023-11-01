package org.bukkit.craftbukkit.v1_20_R2.entity;

import net.minecraft.world.entity.raid.Raider;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.entity.Ravager;

public class CraftRavager extends CraftRaider implements Ravager {

    public CraftRavager(CraftServer server, net.minecraft.world.entity.monster.Ravager entity) {
        super(server, (Raider) entity);
    }

    public net.minecraft.world.entity.monster.Ravager getHandle() {
        return (net.minecraft.world.entity.monster.Ravager) super.getHandle();
    }

    public String toString() {
        return "CraftRavager";
    }
}
