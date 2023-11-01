package org.bukkit.craftbukkit.v1_20_R2.entity;

import net.minecraft.world.entity.monster.Zombie;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.entity.Husk;

public class CraftHusk extends CraftZombie implements Husk {

    public CraftHusk(CraftServer server, net.minecraft.world.entity.monster.Husk entity) {
        super(server, (Zombie) entity);
    }

    public String toString() {
        return "CraftHusk";
    }
}
