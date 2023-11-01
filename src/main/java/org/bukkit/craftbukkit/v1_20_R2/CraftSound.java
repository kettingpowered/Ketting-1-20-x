package org.bukkit.craftbukkit.v1_20_R2;

import com.google.common.base.Preconditions;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_20_R2.util.CraftNamespacedKey;

public class CraftSound {

    public static Sound minecraftToBukkit(SoundEvent minecraft) {
        Preconditions.checkArgument(minecraft != null);
        Registry registry = CraftRegistry.getMinecraftRegistry(Registries.SOUND_EVENT);
        Sound bukkit = (Sound) org.bukkit.Registry.SOUNDS.get(CraftNamespacedKey.fromMinecraft(((ResourceKey) registry.getResourceKey(minecraft).orElseThrow()).location()));

        Preconditions.checkArgument(bukkit != null);
        return bukkit;
    }

    public static SoundEvent bukkitToMinecraft(Sound bukkit) {
        Preconditions.checkArgument(bukkit != null);
        return (SoundEvent) CraftRegistry.getMinecraftRegistry(Registries.SOUND_EVENT).getOptional(CraftNamespacedKey.toMinecraft(bukkit.getKey())).orElseThrow();
    }

    public static Holder bukkitToMinecraftHolder(Sound bukkit) {
        Preconditions.checkArgument(bukkit != null);
        Registry registry = CraftRegistry.getMinecraftRegistry(Registries.SOUND_EVENT);
        Holder holder = registry.wrapAsHolder(bukkitToMinecraft(bukkit));
        Holder.Reference holder;

        if (holder instanceof Holder.Reference && (holder = (Holder.Reference) holder) == (Holder.Reference) holder) {
            return holder;
        } else {
            throw new IllegalArgumentException("No Reference holder found for " + bukkit + ", this can happen if a plugin creates its own sound effect with out properly registering it.");
        }
    }
}
