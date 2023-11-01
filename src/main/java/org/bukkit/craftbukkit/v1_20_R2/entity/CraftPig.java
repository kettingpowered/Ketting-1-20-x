package org.bukkit.craftbukkit.v1_20_R2.entity;

import com.google.common.base.Preconditions;
import net.minecraft.world.entity.animal.Animal;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.entity.Pig;

public class CraftPig extends CraftAnimals implements Pig {

    public CraftPig(CraftServer server, net.minecraft.world.entity.animal.Pig entity) {
        super(server, (Animal) entity);
    }

    public boolean hasSaddle() {
        return this.getHandle().isSaddled();
    }

    public void setSaddle(boolean saddled) {
        this.getHandle().steering.setSaddle(saddled);
    }

    public int getBoostTicks() {
        return this.getHandle().steering.boosting ? this.getHandle().steering.boostTimeTotal() : 0;
    }

    public void setBoostTicks(int ticks) {
        Preconditions.checkArgument(ticks >= 0, "ticks must be >= 0");
        this.getHandle().steering.setBoostTicks(ticks);
    }

    public int getCurrentBoostTicks() {
        return this.getHandle().steering.boosting ? this.getHandle().steering.boostTime : 0;
    }

    public void setCurrentBoostTicks(int ticks) {
        if (this.getHandle().steering.boosting) {
            int max = this.getHandle().steering.boostTimeTotal();

            Preconditions.checkArgument(ticks >= 0 && ticks <= max, "boost ticks must not exceed 0 or %d (inclusive)", max);
            this.getHandle().steering.boostTime = ticks;
        }
    }

    public Material getSteerMaterial() {
        return Material.CARROT_ON_A_STICK;
    }

    public net.minecraft.world.entity.animal.Pig getHandle() {
        return (net.minecraft.world.entity.animal.Pig) this.entity;
    }

    public String toString() {
        return "CraftPig";
    }
}
