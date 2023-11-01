package org.bukkit.craftbukkit.v1_20_R2;

import java.util.HashSet;
import java.util.Set;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import org.bukkit.FeatureFlag;
import org.bukkit.NamespacedKey;
import org.bukkit.craftbukkit.v1_20_R2.util.CraftNamespacedKey;
import org.jetbrains.annotations.NotNull;

public class CraftFeatureFlag implements FeatureFlag {

    private final NamespacedKey namespacedKey;
    private final net.minecraft.world.flag.FeatureFlag featureFlag;

    public CraftFeatureFlag(ResourceLocation minecraftKey, net.minecraft.world.flag.FeatureFlag featureFlag) {
        this.namespacedKey = CraftNamespacedKey.fromMinecraft(minecraftKey);
        this.featureFlag = featureFlag;
    }

    public net.minecraft.world.flag.FeatureFlag getHandle() {
        return this.featureFlag;
    }

    @NotNull
    public NamespacedKey getKey() {
        return this.namespacedKey;
    }

    public String toString() {
        return "CraftFeatureFlag{key=" + this.getKey() + ",keyUniverse=" + this.getHandle().universe.toString() + "}";
    }

    public static Set getFromNMS(FeatureFlagSet featureFlagSet) {
        HashSet set = new HashSet();

        FeatureFlags.REGISTRY.names.forEach((minecraftkeyx, featureflagx) -> {
            if (featureFlagSet.contains(featureflagx)) {
                set.add(new CraftFeatureFlag(minecraftkeyx, featureflagx));
            }

        });
        return set;
    }

    public static CraftFeatureFlag getFromNMS(NamespacedKey namespacedKey) {
        return (CraftFeatureFlag) FeatureFlags.REGISTRY.names.entrySet().stream().filter((entryx) -> {
            return CraftNamespacedKey.fromMinecraft((ResourceLocation) entryx.getKey()).equals(namespacedKey);
        }).findFirst().map((entryx) -> {
            return new CraftFeatureFlag((ResourceLocation) entryx.getKey(), (net.minecraft.world.flag.FeatureFlag) entryx.getValue());
        }).orElse((Object) null);
    }
}
