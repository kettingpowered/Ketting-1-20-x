package org.bukkit.craftbukkit.v1_20_R2.entity;

import net.minecraft.world.entity.PathfinderMob;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.entity.Monster;

public class CraftMonster extends CraftCreature implements Monster, CraftEnemy {

    public CraftMonster(CraftServer server, net.minecraft.world.entity.monster.Monster entity) {
        super(server, (PathfinderMob) entity);
    }

    public net.minecraft.world.entity.monster.Monster getHandle() {
        return (net.minecraft.world.entity.monster.Monster) this.entity;
    }

    public String toString() {
        return "CraftMonster";
    }
}
