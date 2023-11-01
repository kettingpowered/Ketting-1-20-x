package org.bukkit.craftbukkit.v1_20_R2.entity;

import net.minecraft.world.entity.monster.AbstractIllager;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.craftbukkit.v1_20_R2.inventory.CraftInventory;
import org.bukkit.entity.Pillager;
import org.bukkit.inventory.Inventory;

public class CraftPillager extends CraftIllager implements Pillager {

    public CraftPillager(CraftServer server, net.minecraft.world.entity.monster.Pillager entity) {
        super(server, (AbstractIllager) entity);
    }

    public net.minecraft.world.entity.monster.Pillager getHandle() {
        return (net.minecraft.world.entity.monster.Pillager) super.getHandle();
    }

    public String toString() {
        return "CraftPillager";
    }

    public Inventory getInventory() {
        return new CraftInventory(this.getHandle().inventory);
    }
}
