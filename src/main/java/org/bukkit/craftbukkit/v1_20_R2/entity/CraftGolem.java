package org.bukkit.craftbukkit.v1_20_R2.entity;

import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.animal.AbstractGolem;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.entity.Golem;

public class CraftGolem extends CraftCreature implements Golem {

    public CraftGolem(CraftServer server, AbstractGolem entity) {
        super(server, (PathfinderMob) entity);
    }

    public AbstractGolem getHandle() {
        return (AbstractGolem) this.entity;
    }

    public String toString() {
        return "CraftGolem";
    }
}
