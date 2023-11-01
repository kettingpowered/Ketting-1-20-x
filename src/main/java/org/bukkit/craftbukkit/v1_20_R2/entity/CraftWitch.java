package org.bukkit.craftbukkit.v1_20_R2.entity;

import net.minecraft.world.entity.raid.Raider;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.entity.Witch;

public class CraftWitch extends CraftRaider implements Witch {

    public CraftWitch(CraftServer server, net.minecraft.world.entity.monster.Witch entity) {
        super(server, (Raider) entity);
    }

    public net.minecraft.world.entity.monster.Witch getHandle() {
        return (net.minecraft.world.entity.monster.Witch) this.entity;
    }

    public String toString() {
        return "CraftWitch";
    }

    public boolean isDrinkingPotion() {
        return this.getHandle().isDrinkingPotion();
    }
}
