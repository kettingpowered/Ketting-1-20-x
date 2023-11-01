package org.bukkit.craftbukkit.v1_20_R2.entity;

import net.minecraft.world.entity.animal.horse.AbstractChestedHorse;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.entity.ChestedHorse;

public abstract class CraftChestedHorse extends CraftAbstractHorse implements ChestedHorse {

    public CraftChestedHorse(CraftServer server, AbstractChestedHorse entity) {
        super(server, (AbstractHorse) entity);
    }

    public AbstractChestedHorse getHandle() {
        return (AbstractChestedHorse) super.getHandle();
    }

    public boolean isCarryingChest() {
        return this.getHandle().hasChest();
    }

    public void setCarryingChest(boolean chest) {
        if (chest != this.isCarryingChest()) {
            this.getHandle().setChest(chest);
            this.getHandle().createInventory();
        }
    }
}
