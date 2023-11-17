package org.bukkit.craftbukkit.v1_20_R2.entity;

import net.minecraft.world.entity.EntityCreature;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.entity.Creature;

public class CraftCreature extends CraftMob implements Creature {
    public CraftCreature(CraftServer server, EntityCreature entity) {
        super(server, entity);
    }

    @Override
    public EntityCreature getHandle() {
        return (EntityCreature) entity;
    }

    @Override
    public String toString() {
        return "CraftCreature";
    }
}
