package org.bukkit.craftbukkit.v1_20_R2.entity;

import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.entity.Marker;

public class CraftMarker extends CraftEntity implements Marker {

    public CraftMarker(CraftServer server, net.minecraft.world.entity.Marker entity) {
        super(server, entity);
    }

    public net.minecraft.world.entity.Marker getHandle() {
        return (net.minecraft.world.entity.Marker) super.getHandle();
    }

    public String toString() {
        return "CraftMarker";
    }
}