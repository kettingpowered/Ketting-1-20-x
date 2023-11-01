package org.bukkit.craftbukkit.v1_20_R2.entity;

import net.minecraft.world.entity.animal.AbstractFish;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.entity.Tadpole;

public class CraftTadpole extends CraftFish implements Tadpole {

    public CraftTadpole(CraftServer server, net.minecraft.world.entity.animal.frog.Tadpole entity) {
        super(server, (AbstractFish) entity);
    }

    public net.minecraft.world.entity.animal.frog.Tadpole getHandle() {
        return (net.minecraft.world.entity.animal.frog.Tadpole) this.entity;
    }

    public String toString() {
        return "CraftTadpole";
    }

    public int getAge() {
        return this.getHandle().age;
    }

    public void setAge(int age) {
        this.getHandle().age = age;
    }
}
