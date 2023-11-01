package org.bukkit.craftbukkit.v1_20_R2.entity;

import net.minecraft.world.entity.monster.Zombie;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.entity.Drowned;

public class CraftDrowned extends CraftZombie implements Drowned {

    public CraftDrowned(CraftServer server, net.minecraft.world.entity.monster.Drowned entity) {
        super(server, (Zombie) entity);
    }

    public net.minecraft.world.entity.monster.Drowned getHandle() {
        return (net.minecraft.world.entity.monster.Drowned) this.entity;
    }

    public String toString() {
        return "CraftDrowned";
    }
}
