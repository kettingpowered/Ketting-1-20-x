package org.bukkit.craftbukkit.v1_20_R2.entity;

import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.animal.WaterAnimal;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.entity.WaterMob;

public class CraftWaterMob extends CraftCreature implements WaterMob {

    public CraftWaterMob(CraftServer server, WaterAnimal entity) {
        super(server, (PathfinderMob) entity);
    }

    public WaterAnimal getHandle() {
        return (WaterAnimal) this.entity;
    }

    public String toString() {
        return "CraftWaterMob";
    }
}
