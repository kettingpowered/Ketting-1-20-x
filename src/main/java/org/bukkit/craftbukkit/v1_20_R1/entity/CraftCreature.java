package org.bukkit.craftbukkit.v1_20_R1.entity;

import net.minecraft.world.entity.Mob;
import org.bukkit.craftbukkit.v1_20_R1.CraftServer;
import org.bukkit.entity.Creature;

public class CraftCreature extends CraftMob implements Creature {
    public CraftCreature(CraftServer server, Mob entity) {
        super(server, entity);
    }

    @Override
    public Mob getHandle() {
        return (Mob) entity;
    }

    @Override
    public String toString() {
        return "CraftCreature";
    }
}
