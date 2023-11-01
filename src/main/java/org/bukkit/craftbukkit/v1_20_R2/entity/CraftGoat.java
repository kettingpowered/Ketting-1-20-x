package org.bukkit.craftbukkit.v1_20_R2.entity;

import net.minecraft.world.entity.animal.Animal;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.entity.Goat;

public class CraftGoat extends CraftAnimals implements Goat {

    public CraftGoat(CraftServer server, net.minecraft.world.entity.animal.goat.Goat entity) {
        super(server, (Animal) entity);
    }

    public net.minecraft.world.entity.animal.goat.Goat getHandle() {
        return (net.minecraft.world.entity.animal.goat.Goat) super.getHandle();
    }

    public String toString() {
        return "CraftGoat";
    }

    public boolean hasLeftHorn() {
        return this.getHandle().hasLeftHorn();
    }

    public void setLeftHorn(boolean hasHorn) {
        this.getHandle().getEntityData().set(net.minecraft.world.entity.animal.goat.Goat.DATA_HAS_LEFT_HORN, hasHorn);
    }

    public boolean hasRightHorn() {
        return this.getHandle().hasRightHorn();
    }

    public void setRightHorn(boolean hasHorn) {
        this.getHandle().getEntityData().set(net.minecraft.world.entity.animal.goat.Goat.DATA_HAS_RIGHT_HORN, hasHorn);
    }

    public boolean isScreaming() {
        return this.getHandle().isScreamingGoat();
    }

    public void setScreaming(boolean screaming) {
        this.getHandle().setScreamingGoat(screaming);
    }
}
