package org.bukkit.craftbukkit.v1_20_R2.entity;

import net.minecraft.world.entity.animal.AbstractFish;
import net.minecraft.world.entity.animal.Pufferfish;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.entity.PufferFish;

public class CraftPufferFish extends CraftFish implements PufferFish {

    public CraftPufferFish(CraftServer server, Pufferfish entity) {
        super(server, (AbstractFish) entity);
    }

    public Pufferfish getHandle() {
        return (Pufferfish) super.getHandle();
    }

    public int getPuffState() {
        return this.getHandle().getPuffState();
    }

    public void setPuffState(int state) {
        this.getHandle().setPuffState(state);
    }

    public String toString() {
        return "CraftPufferFish";
    }
}
