package org.bukkit.craftbukkit.v1_20_R2.entity;

import net.minecraft.world.entity.animal.AbstractFish;
import net.minecraft.world.entity.animal.WaterAnimal;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.entity.Fish;

public class CraftFish extends CraftWaterMob implements Fish {

    public CraftFish(CraftServer server, AbstractFish entity) {
        super(server, (WaterAnimal) entity);
    }

    public AbstractFish getHandle() {
        return (AbstractFish) this.entity;
    }

    public String toString() {
        return "CraftFish";
    }
}
