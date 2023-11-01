package org.bukkit.craftbukkit.v1_20_R2.entity;

import net.minecraft.world.entity.animal.Animal;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.entity.Cow;

public class CraftCow extends CraftAnimals implements Cow {

    public CraftCow(CraftServer server, net.minecraft.world.entity.animal.Cow entity) {
        super(server, (Animal) entity);
    }

    public net.minecraft.world.entity.animal.Cow getHandle() {
        return (net.minecraft.world.entity.animal.Cow) this.entity;
    }

    public String toString() {
        return "CraftCow";
    }
}
