package org.bukkit.craftbukkit.v1_20_R2.util;

import net.minecraft.resources.ResourceLocation;
import org.bukkit.NamespacedKey;

public final class CraftNamespacedKey {

    public static NamespacedKey fromStringOrNull(String string) {
        if (string != null && !string.isEmpty()) {
            ResourceLocation minecraft = ResourceLocation.tryParse(string);

            return minecraft == null ? null : fromMinecraft(minecraft);
        } else {
            return null;
        }
    }

    public static NamespacedKey fromString(String string) {
        return fromMinecraft(new ResourceLocation(string));
    }

    public static NamespacedKey fromMinecraft(ResourceLocation minecraft) {
        return new NamespacedKey(minecraft.getNamespace(), minecraft.getPath());
    }

    public static ResourceLocation toMinecraft(NamespacedKey key) {
        return new ResourceLocation(key.getNamespace(), key.getKey());
    }
}
