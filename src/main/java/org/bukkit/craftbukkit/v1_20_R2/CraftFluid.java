package org.bukkit.craftbukkit.v1_20_R2;

import com.google.common.base.Preconditions;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import org.bukkit.Fluid;
import org.bukkit.craftbukkit.v1_20_R2.util.CraftNamespacedKey;

public class CraftFluid {

    public static Fluid minecraftToBukkit(net.minecraft.world.level.material.Fluid minecraft) {
        Preconditions.checkArgument(minecraft != null);
        Registry registry = CraftRegistry.getMinecraftRegistry(Registries.FLUID);
        Fluid bukkit = (Fluid) org.bukkit.Registry.FLUID.get(CraftNamespacedKey.fromMinecraft(((ResourceKey) registry.getResourceKey(minecraft).orElseThrow()).location()));

        Preconditions.checkArgument(bukkit != null);
        return bukkit;
    }

    public static net.minecraft.world.level.material.Fluid bukkitToMinecraft(Fluid bukkit) {
        Preconditions.checkArgument(bukkit != null);
        return (net.minecraft.world.level.material.Fluid) CraftRegistry.getMinecraftRegistry(Registries.FLUID).getOptional(CraftNamespacedKey.toMinecraft(bukkit.getKey())).orElseThrow();
    }
}
