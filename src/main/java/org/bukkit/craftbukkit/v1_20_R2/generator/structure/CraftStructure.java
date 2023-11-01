package org.bukkit.craftbukkit.v1_20_R2.generator.structure;

import com.google.common.base.Preconditions;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import org.bukkit.NamespacedKey;
import org.bukkit.craftbukkit.v1_20_R2.CraftRegistry;
import org.bukkit.craftbukkit.v1_20_R2.util.CraftNamespacedKey;
import org.bukkit.generator.structure.Structure;
import org.bukkit.generator.structure.StructureType;

public class CraftStructure extends Structure {

    private final NamespacedKey key;
    private final net.minecraft.world.level.levelgen.structure.Structure structure;
    private final StructureType structureType;

    public static Structure minecraftToBukkit(net.minecraft.world.level.levelgen.structure.Structure minecraft, RegistryAccess registryHolder) {
        Preconditions.checkArgument(minecraft != null);
        Registry registry = CraftRegistry.getMinecraftRegistry(Registries.STRUCTURE);
        Structure bukkit = (Structure) org.bukkit.Registry.STRUCTURE.get(CraftNamespacedKey.fromMinecraft(((ResourceKey) registry.getResourceKey(minecraft).orElseThrow()).location()));

        Preconditions.checkArgument(bukkit != null);
        return bukkit;
    }

    public static net.minecraft.world.level.levelgen.structure.Structure bukkitToMinecraft(Structure bukkit) {
        Preconditions.checkArgument(bukkit != null);
        return ((CraftStructure) bukkit).getHandle();
    }

    public CraftStructure(NamespacedKey key, net.minecraft.world.level.levelgen.structure.Structure structure) {
        this.key = key;
        this.structure = structure;
        this.structureType = CraftStructureType.minecraftToBukkit(structure.type());
    }

    public net.minecraft.world.level.levelgen.structure.Structure getHandle() {
        return this.structure;
    }

    public StructureType getStructureType() {
        return this.structureType;
    }

    public NamespacedKey getKey() {
        return this.key;
    }
}
