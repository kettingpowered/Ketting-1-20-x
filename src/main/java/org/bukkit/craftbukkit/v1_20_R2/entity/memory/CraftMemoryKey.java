package org.bukkit.craftbukkit.v1_20_R2.entity.memory;

import com.google.common.base.Preconditions;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import org.bukkit.Registry;
import org.bukkit.craftbukkit.v1_20_R2.CraftRegistry;
import org.bukkit.craftbukkit.v1_20_R2.util.CraftNamespacedKey;
import org.bukkit.entity.memory.MemoryKey;

public final class CraftMemoryKey {

    private CraftMemoryKey() {}

    public static <T, U> MemoryKey<U> minecraftToBukkit(MemoryModuleType<T> minecraft) {
        if (minecraft == null) {
            return null;
        }

        net.minecraft.core.Registry<MemoryModuleType<?>> registry = CraftRegistry.getMinecraftRegistry(Registries.MEMORY_MODULE_TYPE);
        MemoryKey<U> bukkit = Registry.MEMORY_MODULE_TYPE.get(CraftNamespacedKey.fromMinecraft(registry.getResourceKey(minecraft).orElseThrow().location()));

        return bukkit;
    }

    public static <T, U> MemoryModuleType<U> bukkitToMinecraft(MemoryKey<T> bukkit) {
        if (bukkit == null) {
            return null;
        }

        return (MemoryModuleType<U>) CraftRegistry.getMinecraftRegistry(Registries.MEMORY_MODULE_TYPE)
                .getOptional(CraftNamespacedKey.toMinecraft(bukkit.getKey())).orElseThrow();
    }
}