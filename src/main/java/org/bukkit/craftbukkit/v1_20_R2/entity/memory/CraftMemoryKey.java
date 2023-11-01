package org.bukkit.craftbukkit.v1_20_R2.entity.memory;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import org.bukkit.craftbukkit.v1_20_R2.CraftRegistry;
import org.bukkit.craftbukkit.v1_20_R2.util.CraftNamespacedKey;
import org.bukkit.entity.memory.MemoryKey;

public final class CraftMemoryKey {

    private CraftMemoryKey() {}

    public static MemoryKey minecraftToBukkit(MemoryModuleType minecraft) {
        if (minecraft == null) {
            return null;
        } else {
            Registry registry = CraftRegistry.getMinecraftRegistry(Registries.MEMORY_MODULE_TYPE);
            MemoryKey bukkit = (MemoryKey) org.bukkit.Registry.MEMORY_MODULE_TYPE.get(CraftNamespacedKey.fromMinecraft(((ResourceKey) registry.getResourceKey(minecraft).orElseThrow()).location()));

            return bukkit;
        }
    }

    public static MemoryModuleType bukkitToMinecraft(MemoryKey bukkit) {
        return bukkit == null ? null : (MemoryModuleType) CraftRegistry.getMinecraftRegistry(Registries.MEMORY_MODULE_TYPE).getOptional(CraftNamespacedKey.toMinecraft(bukkit.getKey())).orElseThrow();
    }
}
