package org.bukkit.craftbukkit.v1_20_R2.entity;

import net.minecraft.world.entity.npc.AbstractVillager;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.entity.WanderingTrader;

public class CraftWanderingTrader extends CraftAbstractVillager implements WanderingTrader {

    public CraftWanderingTrader(CraftServer server, net.minecraft.world.entity.npc.WanderingTrader entity) {
        super(server, (AbstractVillager) entity);
    }

    public net.minecraft.world.entity.npc.WanderingTrader getHandle() {
        return (net.minecraft.world.entity.npc.WanderingTrader) this.entity;
    }

    public String toString() {
        return "CraftWanderingTrader";
    }

    public int getDespawnDelay() {
        return this.getHandle().getDespawnDelay();
    }

    public void setDespawnDelay(int despawnDelay) {
        this.getHandle().setDespawnDelay(despawnDelay);
    }
}
