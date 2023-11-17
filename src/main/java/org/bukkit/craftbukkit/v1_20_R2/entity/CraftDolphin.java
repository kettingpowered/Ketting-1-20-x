package org.bukkit.craftbukkit.v1_20_R2.entity;

import net.minecraft.world.entity.animal.EntityDolphin;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.entity.Dolphin;

public class CraftDolphin extends CraftWaterMob implements Dolphin {

    public CraftDolphin(CraftServer server, EntityDolphin entity) {
        super(server, entity);
    }

    @Override
    public EntityDolphin getHandle() {
        return (EntityDolphin) super.getHandle();
    }

    @Override
    public String toString() {
        return "CraftDolphin";
    }
}
