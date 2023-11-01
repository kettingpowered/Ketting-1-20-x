package org.bukkit.craftbukkit.v1_20_R2.inventory.trim;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.meta.trim.TrimMaterial;
import org.jetbrains.annotations.NotNull;

public class CraftTrimMaterial implements TrimMaterial {

    private final NamespacedKey key;
    private final net.minecraft.world.item.armortrim.TrimMaterial handle;

    public CraftTrimMaterial(NamespacedKey key, net.minecraft.world.item.armortrim.TrimMaterial handle) {
        this.key = key;
        this.handle = handle;
    }

    @NotNull
    public NamespacedKey getKey() {
        return this.key;
    }

    public net.minecraft.world.item.armortrim.TrimMaterial getHandle() {
        return this.handle;
    }
}
