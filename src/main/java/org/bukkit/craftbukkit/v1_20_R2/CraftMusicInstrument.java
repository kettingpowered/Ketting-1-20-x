package org.bukkit.craftbukkit.v1_20_R2;

import com.google.common.base.Preconditions;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Instrument;
import org.bukkit.MusicInstrument;
import org.bukkit.NamespacedKey;
import org.bukkit.craftbukkit.v1_20_R2.util.CraftNamespacedKey;
import org.jetbrains.annotations.NotNull;

public class CraftMusicInstrument extends MusicInstrument {

    private final NamespacedKey key;
    private final Instrument handle;

    public static MusicInstrument minecraftToBukkit(Instrument minecraft) {
        Preconditions.checkArgument(minecraft != null);
        Registry registry = CraftRegistry.getMinecraftRegistry(Registries.INSTRUMENT);
        MusicInstrument bukkit = (MusicInstrument) org.bukkit.Registry.INSTRUMENT.get(CraftNamespacedKey.fromMinecraft(registry.getKey(minecraft)));

        Preconditions.checkArgument(bukkit != null);
        return bukkit;
    }

    public static Instrument bukkitToMinecraft(MusicInstrument bukkit) {
        Preconditions.checkArgument(bukkit != null);
        return ((CraftMusicInstrument) bukkit).getHandle();
    }

    public CraftMusicInstrument(NamespacedKey key, Instrument handle) {
        this.key = key;
        this.handle = handle;
    }

    public Instrument getHandle() {
        return this.handle;
    }

    @NotNull
    public NamespacedKey getKey() {
        return this.key;
    }

    public boolean equals(Object other) {
        return this == other ? true : (!(other instanceof CraftMusicInstrument) ? false : this.getKey().equals(((MusicInstrument) other).getKey()));
    }

    public int hashCode() {
        return this.getKey().hashCode();
    }

    public String toString() {
        return "CraftMusicInstrument{key=" + this.key + "}";
    }
}
