package org.bukkit.craftbukkit.v1_20_R2.entity;

import net.minecraft.world.entity.animal.Animal;
import org.bukkit.DyeColor;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.entity.Sheep;

public class CraftSheep extends CraftAnimals implements Sheep {

    public CraftSheep(CraftServer server, net.minecraft.world.entity.animal.Sheep entity) {
        super(server, (Animal) entity);
    }

    public DyeColor getColor() {
        return DyeColor.getByWoolData((byte) this.getHandle().getColor().getId());
    }

    public void setColor(DyeColor color) {
        this.getHandle().setColor(net.minecraft.world.item.DyeColor.byId(color.getWoolData()));
    }

    public boolean isSheared() {
        return this.getHandle().isSheared();
    }

    public void setSheared(boolean flag) {
        this.getHandle().setSheared(flag);
    }

    public net.minecraft.world.entity.animal.Sheep getHandle() {
        return (net.minecraft.world.entity.animal.Sheep) this.entity;
    }

    public String toString() {
        return "CraftSheep";
    }
}
