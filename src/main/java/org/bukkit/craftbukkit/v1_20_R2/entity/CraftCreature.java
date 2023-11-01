package org.bukkit.craftbukkit.v1_20_R2.entity;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.entity.Creature;

public class CraftCreature extends CraftMob implements Creature {

    public CraftCreature(CraftServer server, PathfinderMob entity) {
        super(server, (Mob) entity);
    }

    public PathfinderMob getHandle() {
        return (PathfinderMob) this.entity;
    }

    public String toString() {
        return "CraftCreature";
    }
}
