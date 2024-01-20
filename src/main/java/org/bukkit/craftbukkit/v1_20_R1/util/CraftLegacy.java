package org.bukkit.craftbukkit.v1_20_R1.util;

import java.util.Arrays;
import org.bukkit.Material;
import org.bukkit.material.MaterialData;

/**
 * @deprecated legacy use only
 */
@Deprecated
public final class CraftLegacy {

    private static Material[] modernMaterials; // Ketting
    private static int offset; // Ketting

    private CraftLegacy() {
        //
    }

    public static Material fromLegacy(Material material) {
        if (material == null || !material.isLegacy()) {
            return material;
        }

        return org.bukkit.craftbukkit.v1_20_R1.legacy.CraftLegacy.fromLegacy(material);
    }

    public static Material fromLegacy(MaterialData materialData) {
        return org.bukkit.craftbukkit.v1_20_R1.legacy.CraftLegacy.fromLegacy(materialData);
    }

    public static Material[] modern_values() {
        // Ketting start
        if (modernMaterials == null) {
            int origin = Material.values().length;
            modernMaterials = Arrays.stream(Material.values()).filter(it -> !it.isLegacy()).toArray(Material[]::new);
            offset = origin - modernMaterials.length;
        }
        return Arrays.copyOf(modernMaterials, modernMaterials.length);
        // Ketting end
    }

    public static int modern_ordinal(Material material) {
        // Ketting start
        if (modernMaterials == null) {
            modern_values();
        }
        if (material.isLegacy()) {
            throw new NoSuchFieldError("Legacy field ordinal: " + material);
        } else {
            int ordinal = material.ordinal();
            return ordinal < Material.LEGACY_AIR.ordinal() ? ordinal : ordinal - offset;
        }
        // Ketting end
    }
}