package org.bukkit.craftbukkit.v1_20_R2;

import com.google.common.base.Preconditions;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.decoration.PaintingVariant;
import org.bukkit.Art;
import org.bukkit.craftbukkit.v1_20_R2.util.CraftNamespacedKey;

public class CraftArt {

    public static Art minecraftToBukkit(PaintingVariant minecraft) {
        Preconditions.checkArgument(minecraft != null);
        Registry registry = CraftRegistry.getMinecraftRegistry(Registries.PAINTING_VARIANT);
        Art bukkit = (Art) org.bukkit.Registry.ART.get(CraftNamespacedKey.fromMinecraft(((ResourceKey) registry.getResourceKey(minecraft).orElseThrow()).location()));

        Preconditions.checkArgument(bukkit != null);
        return bukkit;
    }

    public static Art minecraftHolderToBukkit(Holder minecraft) {
        return minecraftToBukkit((PaintingVariant) minecraft.value());
    }

    public static PaintingVariant bukkitToMinecraft(Art bukkit) {
        Preconditions.checkArgument(bukkit != null);
        return (PaintingVariant) CraftRegistry.getMinecraftRegistry(Registries.PAINTING_VARIANT).getOptional(CraftNamespacedKey.toMinecraft(bukkit.getKey())).orElseThrow();
    }

    public static Holder bukkitToMinecraftHolder(Art bukkit) {
        Preconditions.checkArgument(bukkit != null);
        Registry registry = CraftRegistry.getMinecraftRegistry(Registries.PAINTING_VARIANT);
        Holder holder = registry.wrapAsHolder(bukkitToMinecraft(bukkit));
        Holder.Reference holder;

        if (holder instanceof Holder.Reference && (holder = (Holder.Reference) holder) == (Holder.Reference) holder) {
            return holder;
        } else {
            throw new IllegalArgumentException("No Reference holder found for " + bukkit + ", this can happen if a plugin creates its own painting variant with out properly registering it.");
        }
    }
}
