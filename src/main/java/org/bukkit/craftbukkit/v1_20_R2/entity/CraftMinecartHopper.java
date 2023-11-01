package org.bukkit.craftbukkit.v1_20_R2.entity;

import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.entity.vehicle.MinecartHopper;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.craftbukkit.v1_20_R2.inventory.CraftInventory;
import org.bukkit.entity.minecart.HopperMinecart;
import org.bukkit.inventory.Inventory;

public final class CraftMinecartHopper extends CraftMinecartContainer implements HopperMinecart {

    private final CraftInventory inventory;

    public CraftMinecartHopper(CraftServer server, MinecartHopper entity) {
        super(server, (AbstractMinecart) entity);
        this.inventory = new CraftInventory(entity);
    }

    public String toString() {
        return "CraftMinecartHopper{inventory=" + this.inventory + '}';
    }

    public Inventory getInventory() {
        return this.inventory;
    }

    public boolean isEnabled() {
        return ((MinecartHopper) this.getHandle()).isEnabled();
    }

    public void setEnabled(boolean enabled) {
        ((MinecartHopper) this.getHandle()).setEnabled(enabled);
    }
}
