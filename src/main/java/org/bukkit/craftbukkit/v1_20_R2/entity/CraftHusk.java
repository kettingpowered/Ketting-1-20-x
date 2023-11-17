package org.bukkit.craftbukkit.v1_20_R2.entity;

import net.minecraft.world.entity.monster.EntityZombieHusk;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.entity.Husk;

public class CraftHusk extends CraftZombie implements Husk {

    public CraftHusk(CraftServer server, EntityZombieHusk entity) {
        super(server, entity);
    }

    @Override
    public String toString() {
        return "CraftHusk";
    }
}
