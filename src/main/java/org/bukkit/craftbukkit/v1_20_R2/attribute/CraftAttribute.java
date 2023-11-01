package org.bukkit.craftbukkit.v1_20_R2.attribute;

import com.google.common.base.Preconditions;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.craftbukkit.v1_20_R2.CraftRegistry;
import org.bukkit.craftbukkit.v1_20_R2.util.CraftNamespacedKey;

public class CraftAttribute {

    public static Attribute minecraftToBukkit(net.minecraft.world.entity.ai.attributes.Attribute minecraft) {
        Preconditions.checkArgument(minecraft != null);
        Registry registry = CraftRegistry.getMinecraftRegistry(Registries.ATTRIBUTE);
        Attribute bukkit = (Attribute) org.bukkit.Registry.ATTRIBUTE.get(CraftNamespacedKey.fromMinecraft(((ResourceKey) registry.getResourceKey(minecraft).orElseThrow()).location()));

        Preconditions.checkArgument(bukkit != null);
        return bukkit;
    }

    public static Attribute stringToBukkit(String bukkit) {
        Preconditions.checkArgument(bukkit != null);
        return (Attribute) org.bukkit.Registry.ATTRIBUTE.get(NamespacedKey.fromString(bukkit));
    }

    public static net.minecraft.world.entity.ai.attributes.Attribute bukkitToMinecraft(Attribute bukkit) {
        Preconditions.checkArgument(bukkit != null);
        return (net.minecraft.world.entity.ai.attributes.Attribute) CraftRegistry.getMinecraftRegistry(Registries.ATTRIBUTE).getOptional(CraftNamespacedKey.toMinecraft(bukkit.getKey())).orElseThrow();
    }
}
