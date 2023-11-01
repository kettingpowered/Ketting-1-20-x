package org.bukkit.craftbukkit.v1_20_R2.block;

import com.google.common.base.Preconditions;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import org.bukkit.block.Biome;
import org.bukkit.craftbukkit.v1_20_R2.CraftRegistry;
import org.bukkit.craftbukkit.v1_20_R2.util.CraftNamespacedKey;

public class CraftBiome {

    public static Biome minecraftToBukkit(net.minecraft.world.level.biome.Biome minecraft) {
        Preconditions.checkArgument(minecraft != null);
        Registry registry = CraftRegistry.getMinecraftRegistry(Registries.BIOME);
        Biome bukkit = (Biome) org.bukkit.Registry.BIOME.get(CraftNamespacedKey.fromMinecraft(((ResourceKey) registry.getResourceKey(minecraft).orElseThrow()).location()));

        return bukkit == null ? Biome.CUSTOM : bukkit;
    }

    public static Biome minecraftHolderToBukkit(Holder minecraft) {
        return minecraftToBukkit((net.minecraft.world.level.biome.Biome) minecraft.value());
    }

    public static net.minecraft.world.level.biome.Biome bukkitToMinecraft(Biome bukkit) {
        return bukkit != null && bukkit != Biome.CUSTOM ? (net.minecraft.world.level.biome.Biome) CraftRegistry.getMinecraftRegistry(Registries.BIOME).getOptional(CraftNamespacedKey.toMinecraft(bukkit.getKey())).orElseThrow() : null;
    }

    public static Holder bukkitToMinecraftHolder(Biome bukkit) {
        if (bukkit != null && bukkit != Biome.CUSTOM) {
            Registry registry = CraftRegistry.getMinecraftRegistry(Registries.BIOME);
            Holder holder = registry.wrapAsHolder(bukkitToMinecraft(bukkit));
            Holder.Reference holder;

            if (holder instanceof Holder.Reference && (holder = (Holder.Reference) holder) == (Holder.Reference) holder) {
                return holder;
            } else {
                throw new IllegalArgumentException("No Reference holder found for " + bukkit + ", this can happen if a plugin creates its own biome base with out properly registering it.");
            }
        } else {
            return null;
        }
    }
}
