package org.bukkit.craftbukkit.v1_20_R2.entity;

import com.google.common.base.Preconditions;
import net.minecraft.world.entity.animal.Animal;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.entity.Hoglin;

public class CraftHoglin extends CraftAnimals implements Hoglin, CraftEnemy {

    public CraftHoglin(CraftServer server, net.minecraft.world.entity.monster.hoglin.Hoglin entity) {
        super(server, (Animal) entity);
    }

    public boolean isImmuneToZombification() {
        return this.getHandle().isImmuneToZombification();
    }

    public void setImmuneToZombification(boolean flag) {
        this.getHandle().setImmuneToZombification(flag);
    }

    public boolean isAbleToBeHunted() {
        return this.getHandle().cannotBeHunted;
    }

    public void setIsAbleToBeHunted(boolean flag) {
        this.getHandle().cannotBeHunted = flag;
    }

    public int getConversionTime() {
        Preconditions.checkState(this.isConverting(), "Entity not converting");
        return this.getHandle().timeInOverworld;
    }

    public void setConversionTime(int time) {
        if (time < 0) {
            this.getHandle().timeInOverworld = -1;
            this.getHandle().setImmuneToZombification(false);
        } else {
            this.getHandle().timeInOverworld = time;
        }

    }

    public boolean isConverting() {
        return this.getHandle().isConverting();
    }

    public net.minecraft.world.entity.monster.hoglin.Hoglin getHandle() {
        return (net.minecraft.world.entity.monster.hoglin.Hoglin) this.entity;
    }

    public String toString() {
        return "CraftHoglin";
    }
}
