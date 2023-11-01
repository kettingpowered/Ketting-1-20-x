package org.bukkit.craftbukkit.v1_20_R2.entity;

import net.minecraft.world.entity.animal.WaterAnimal;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.entity.Dolphin;

public class CraftDolphin extends CraftWaterMob implements Dolphin {

    public CraftDolphin(CraftServer server, net.minecraft.world.entity.animal.Dolphin entity) {
        super(server, (WaterAnimal) entity);
    }

    public net.minecraft.world.entity.animal.Dolphin getHandle() {
        return (net.minecraft.world.entity.animal.Dolphin) super.getHandle();
    }

    public String toString() {
        return "CraftDolphin";
    }
}
