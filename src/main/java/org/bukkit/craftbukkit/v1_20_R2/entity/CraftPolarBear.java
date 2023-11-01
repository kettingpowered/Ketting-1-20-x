package org.bukkit.craftbukkit.v1_20_R2.entity;

import net.minecraft.world.entity.animal.Animal;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.entity.PolarBear;

public class CraftPolarBear extends CraftAnimals implements PolarBear {

    public CraftPolarBear(CraftServer server, net.minecraft.world.entity.animal.PolarBear entity) {
        super(server, (Animal) entity);
    }

    public net.minecraft.world.entity.animal.PolarBear getHandle() {
        return (net.minecraft.world.entity.animal.PolarBear) this.entity;
    }

    public String toString() {
        return "CraftPolarBear";
    }
}
