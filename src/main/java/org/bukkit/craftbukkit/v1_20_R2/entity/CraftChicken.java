package org.bukkit.craftbukkit.v1_20_R2.entity;

import net.minecraft.world.entity.animal.Animal;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.entity.Chicken;

public class CraftChicken extends CraftAnimals implements Chicken {

    public CraftChicken(CraftServer server, net.minecraft.world.entity.animal.Chicken entity) {
        super(server, (Animal) entity);
    }

    public net.minecraft.world.entity.animal.Chicken getHandle() {
        return (net.minecraft.world.entity.animal.Chicken) this.entity;
    }

    public String toString() {
        return "CraftChicken";
    }
}
