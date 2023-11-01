package org.bukkit.craftbukkit.v1_20_R2;

import com.google.common.base.Preconditions;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.stream.Stream;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import org.bukkit.GameEvent;
import org.bukkit.Keyed;
import org.bukkit.MusicInstrument;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.craftbukkit.v1_20_R2.generator.structure.CraftStructure;
import org.bukkit.craftbukkit.v1_20_R2.generator.structure.CraftStructureType;
import org.bukkit.craftbukkit.v1_20_R2.inventory.trim.CraftTrimMaterial;
import org.bukkit.craftbukkit.v1_20_R2.inventory.trim.CraftTrimPattern;
import org.bukkit.craftbukkit.v1_20_R2.util.CraftNamespacedKey;
import org.bukkit.generator.structure.Structure;
import org.bukkit.generator.structure.StructureType;
import org.bukkit.inventory.meta.trim.TrimMaterial;
import org.bukkit.inventory.meta.trim.TrimPattern;
import org.jetbrains.annotations.NotNull;

public class CraftRegistry implements Registry {

    private static RegistryAccess registry;
    private final Class bukkitClass;
    private final Map cache = new HashMap();
    private final net.minecraft.core.Registry minecraftRegistry;
    private final BiFunction minecraftToBukkit;
    private boolean init;

    public static void setMinecraftRegistry(RegistryAccess registry) {
        Preconditions.checkState(CraftRegistry.registry == null, "Registry already set");
        CraftRegistry.registry = registry;
    }

    public static RegistryAccess getMinecraftRegistry() {
        return CraftRegistry.registry;
    }

    public static net.minecraft.core.Registry getMinecraftRegistry(ResourceKey key) {
        return getMinecraftRegistry().registryOrThrow(key);
    }

    public static Registry createRegistry(Class bukkitClass, RegistryAccess registryHolder) {
        return bukkitClass == GameEvent.class ? new CraftRegistry(GameEvent.class, registryHolder.registryOrThrow(Registries.GAME_EVENT), CraftGameEvent::new) : (bukkitClass == MusicInstrument.class ? new CraftRegistry(MusicInstrument.class, registryHolder.registryOrThrow(Registries.INSTRUMENT), CraftMusicInstrument::new) : (bukkitClass == Structure.class ? new CraftRegistry(Structure.class, registryHolder.registryOrThrow(Registries.STRUCTURE), CraftStructure::new) : (bukkitClass == StructureType.class ? new CraftRegistry(StructureType.class, BuiltInRegistries.STRUCTURE_TYPE, CraftStructureType::new) : (bukkitClass == TrimMaterial.class ? new CraftRegistry(TrimMaterial.class, registryHolder.registryOrThrow(Registries.TRIM_MATERIAL), CraftTrimMaterial::new) : (bukkitClass == TrimPattern.class ? new CraftRegistry(TrimPattern.class, registryHolder.registryOrThrow(Registries.TRIM_PATTERN), CraftTrimPattern::new) : null)))));
    }

    public CraftRegistry(Class bukkitClass, net.minecraft.core.Registry minecraftRegistry, BiFunction minecraftToBukkit) {
        this.bukkitClass = bukkitClass;
        this.minecraftRegistry = minecraftRegistry;
        this.minecraftToBukkit = minecraftToBukkit;
    }

    public Keyed get(NamespacedKey namespacedKey) {
        Keyed cached = (Keyed) this.cache.get(namespacedKey);

        if (cached != null) {
            return cached;
        } else if (!this.init) {
            this.init = true;

            try {
                Class.forName(this.bukkitClass.getName());
            } catch (ClassNotFoundException classnotfoundexception) {
                throw new RuntimeException("Could not load registry class " + this.bukkitClass, classnotfoundexception);
            }

            return this.get(namespacedKey);
        } else {
            Keyed bukkit = this.createBukkit(namespacedKey, this.minecraftRegistry.getOptional(CraftNamespacedKey.toMinecraft(namespacedKey)).orElse((Object) null));

            if (bukkit == null) {
                return null;
            } else {
                this.cache.put(namespacedKey, bukkit);
                return bukkit;
            }
        }
    }

    @NotNull
    public Stream stream() {
        return this.minecraftRegistry.keySet().stream().map((minecraftKeyx) -> {
            return this.get(CraftNamespacedKey.fromMinecraft(minecraftKeyx));
        });
    }

    public Iterator iterator() {
        return this.stream().iterator();
    }

    public Keyed createBukkit(NamespacedKey namespacedKey, Object minecraft) {
        return minecraft == null ? null : (Keyed) this.minecraftToBukkit.apply(namespacedKey, minecraft);
    }
}
