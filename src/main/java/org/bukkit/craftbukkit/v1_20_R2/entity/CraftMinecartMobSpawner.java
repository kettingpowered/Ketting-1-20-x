package org.bukkit.craftbukkit.v1_20_R2.entity;

import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.entity.vehicle.MinecartSpawner;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.entity.minecart.SpawnerMinecart;

final class CraftMinecartMobSpawner extends CraftMinecart implements SpawnerMinecart {

    CraftMinecartMobSpawner(CraftServer server, MinecartSpawner entity) {
        super(server, (AbstractMinecart) entity);
    }

    public String toString() {
        return "CraftMinecartMobSpawner";
    }
}
