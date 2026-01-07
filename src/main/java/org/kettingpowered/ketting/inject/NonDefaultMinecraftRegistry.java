package org.kettingpowered.ketting.inject;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistry;
import org.bukkit.NamespacedKey;

import java.util.*;

public final class NonDefaultMinecraftRegistry {

    private static final Map<ResourceKey<? extends Registry<?>>, List<ResourceLocation>> nonDefaultEntries = new HashMap<>();

    public static void addNonDefaultEntry(ResourceKey<? extends Registry<?>> registryKey, ResourceLocation entry) {
        nonDefaultEntries.computeIfAbsent(registryKey, k -> new java.util.ArrayList<>()).add(entry);
    }

    static void clear() {
        nonDefaultEntries.clear();
    }

    static <T> Set<Map.Entry<ResourceKey<T>, T>> getNonMinecraftEntries(IForgeRegistry<T> forgeRegistry) {
        Set<Map.Entry<ResourceKey<T>, T>> copy = new HashSet<>(forgeRegistry.getEntries());
        copy.removeIf(entry -> test(entry, forgeRegistry.getRegistryKey()));
        return Collections.unmodifiableSet(copy);
    }

    private static <T> boolean test(Map.Entry<ResourceKey<T>, T> entry, ResourceKey<? extends Registry<T>> registryKey) {
        ResourceLocation key = entry.getKey().location();
        if (!key.getNamespace().equals(NamespacedKey.MINECRAFT)) return false;
        return !nonDefaultEntries.getOrDefault(registryKey, List.of()).contains(key);
    }
}
