package org.bukkit.craftbukkit.v1_20_R2.entity;

import com.google.common.base.Preconditions;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.entity.vehicle.MinecartFurnace;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.entity.minecart.PoweredMinecart;

public class CraftMinecartFurnace extends CraftMinecart implements PoweredMinecart {

    public CraftMinecartFurnace(CraftServer server, MinecartFurnace entity) {
        super(server, (AbstractMinecart) entity);
    }

    public MinecartFurnace getHandle() {
        return (MinecartFurnace) this.entity;
    }

    public int getFuel() {
        return this.getHandle().fuel;
    }

    public void setFuel(int fuel) {
        Preconditions.checkArgument(fuel >= 0, "ticks cannot be negative");
        this.getHandle().fuel = fuel;
    }

    public String toString() {
        return "CraftMinecartFurnace";
    }
}
