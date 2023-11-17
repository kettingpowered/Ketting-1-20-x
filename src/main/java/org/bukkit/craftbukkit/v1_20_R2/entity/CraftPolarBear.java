package org.bukkit.craftbukkit.v1_20_R2.entity;

import net.minecraft.world.entity.animal.EntityPolarBear;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.entity.PolarBear;

public class CraftPolarBear extends CraftAnimals implements PolarBear {

    public CraftPolarBear(CraftServer server, EntityPolarBear entity) {
        super(server, entity);
    }
    @Override
    public EntityPolarBear getHandle() {
        return (EntityPolarBear) entity;
    }

    @Override
    public String toString() {
        return "CraftPolarBear";
    }
}
