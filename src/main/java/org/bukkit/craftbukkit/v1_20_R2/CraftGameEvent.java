package org.bukkit.craftbukkit.v1_20_R2;

import com.google.common.base.Preconditions;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import org.bukkit.GameEvent;
import org.bukkit.NamespacedKey;
import org.bukkit.craftbukkit.v1_20_R2.util.CraftNamespacedKey;
import org.jetbrains.annotations.NotNull;

public class CraftGameEvent extends GameEvent {

    private final NamespacedKey key;
    private final net.minecraft.world.level.gameevent.GameEvent handle;

    public static GameEvent minecraftToBukkit(net.minecraft.world.level.gameevent.GameEvent minecraft) {
        Preconditions.checkArgument(minecraft != null);
        Registry registry = CraftRegistry.getMinecraftRegistry(Registries.GAME_EVENT);
        GameEvent bukkit = (GameEvent) org.bukkit.Registry.GAME_EVENT.get(CraftNamespacedKey.fromMinecraft(registry.getKey(minecraft)));

        Preconditions.checkArgument(bukkit != null);
        return bukkit;
    }

    public static net.minecraft.world.level.gameevent.GameEvent bukkitToMinecraft(GameEvent bukkit) {
        Preconditions.checkArgument(bukkit != null);
        return ((CraftGameEvent) bukkit).getHandle();
    }

    public CraftGameEvent(NamespacedKey key, net.minecraft.world.level.gameevent.GameEvent handle) {
        this.key = key;
        this.handle = handle;
    }

    public net.minecraft.world.level.gameevent.GameEvent getHandle() {
        return this.handle;
    }

    @NotNull
    public NamespacedKey getKey() {
        return this.key;
    }

    public boolean equals(Object other) {
        return this == other ? true : (!(other instanceof CraftGameEvent) ? false : this.getKey().equals(((GameEvent) other).getKey()));
    }

    public int hashCode() {
        return this.getKey().hashCode();
    }

    public String toString() {
        return "CraftGameEvent{key=" + this.key + "}";
    }
}
