package org.bukkit.craftbukkit.v1_20_R2.entity;

import com.google.common.base.Preconditions;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import org.bukkit.craftbukkit.v1_20_R2.CraftRegistry;
import org.bukkit.craftbukkit.v1_20_R2.util.CraftNamespacedKey;
import org.bukkit.entity.EntityType;

public class CraftEntityType {

    public static EntityType minecraftToBukkit(net.minecraft.world.entity.EntityType minecraft) {
        Preconditions.checkArgument(minecraft != null);
        Registry registry = CraftRegistry.getMinecraftRegistry(Registries.ENTITY_TYPE);
        EntityType bukkit = (EntityType) org.bukkit.Registry.ENTITY_TYPE.get(CraftNamespacedKey.fromMinecraft(((ResourceKey) registry.getResourceKey(minecraft).orElseThrow()).location()));

        Preconditions.checkArgument(bukkit != null);
        return bukkit;
    }

    public static net.minecraft.world.entity.EntityType bukkitToMinecraft(EntityType bukkit) {
        Preconditions.checkArgument(bukkit != null);
        return (net.minecraft.world.entity.EntityType) CraftRegistry.getMinecraftRegistry(Registries.ENTITY_TYPE).getOptional(CraftNamespacedKey.toMinecraft(bukkit.getKey())).orElseThrow();
    }
}
