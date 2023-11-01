package org.bukkit.craftbukkit.v1_20_R2.entity;

import net.minecraft.world.entity.animal.WaterAnimal;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.entity.Squid;

public class CraftSquid extends CraftWaterMob implements Squid {

    public CraftSquid(CraftServer server, net.minecraft.world.entity.animal.Squid entity) {
        super(server, (WaterAnimal) entity);
    }

    public net.minecraft.world.entity.animal.Squid getHandle() {
        return (net.minecraft.world.entity.animal.Squid) this.entity;
    }

    public String toString() {
        return "CraftSquid";
    }
}
