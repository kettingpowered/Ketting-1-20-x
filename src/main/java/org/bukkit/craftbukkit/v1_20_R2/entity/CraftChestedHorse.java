package org.bukkit.craftbukkit.v1_20_R2.entity;

import net.minecraft.world.entity.animal.horse.EntityHorseChestedAbstract;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.entity.ChestedHorse;

public abstract class CraftChestedHorse extends CraftAbstractHorse implements ChestedHorse {

    public CraftChestedHorse(CraftServer server, EntityHorseChestedAbstract entity) {
        super(server, entity);
    }

    @Override
    public EntityHorseChestedAbstract getHandle() {
        return (EntityHorseChestedAbstract) super.getHandle();
    }

    @Override
    public boolean isCarryingChest() {
        return getHandle().hasChest();
    }

    @Override
    public void setCarryingChest(boolean chest) {
        if (chest == isCarryingChest()) return;
        getHandle().setChest(chest);
        getHandle().createInventory();
    }
}
