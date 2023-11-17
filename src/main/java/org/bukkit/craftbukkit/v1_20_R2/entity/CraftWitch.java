package org.bukkit.craftbukkit.v1_20_R2.entity;

import net.minecraft.world.entity.monster.EntityWitch;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.entity.Witch;

public class CraftWitch extends CraftRaider implements Witch {
    public CraftWitch(CraftServer server, EntityWitch entity) {
        super(server, entity);
    }

    @Override
    public EntityWitch getHandle() {
        return (EntityWitch) entity;
    }

    @Override
    public String toString() {
        return "CraftWitch";
    }

    @Override
    public boolean isDrinkingPotion() {
        return getHandle().isDrinkingPotion();
    }
}
