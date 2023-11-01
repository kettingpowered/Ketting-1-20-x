package org.bukkit.craftbukkit.v1_20_R2.entity;

import net.minecraft.world.entity.animal.Animal;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.entity.Turtle;

public class CraftTurtle extends CraftAnimals implements Turtle {

    public CraftTurtle(CraftServer server, net.minecraft.world.entity.animal.Turtle entity) {
        super(server, (Animal) entity);
    }

    public net.minecraft.world.entity.animal.Turtle getHandle() {
        return (net.minecraft.world.entity.animal.Turtle) super.getHandle();
    }

    public String toString() {
        return "CraftTurtle";
    }

    public boolean hasEgg() {
        return this.getHandle().hasEgg();
    }

    public boolean isLayingEgg() {
        return this.getHandle().isLayingEgg();
    }
}
