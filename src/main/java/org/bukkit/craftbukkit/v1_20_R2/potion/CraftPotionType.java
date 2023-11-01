package org.bukkit.craftbukkit.v1_20_R2.potion;

import com.google.common.base.Preconditions;
import com.google.common.base.Suppliers;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.alchemy.Potion;
import org.bukkit.NamespacedKey;
import org.bukkit.craftbukkit.v1_20_R2.CraftRegistry;
import org.bukkit.craftbukkit.v1_20_R2.util.CraftNamespacedKey;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import org.bukkit.potion.PotionType.InternalPotionData;

public class CraftPotionType implements InternalPotionData {

    private final NamespacedKey key;
    private final Potion potion;
    private final Supplier potionEffects;
    private final Supplier upgradeable;
    private final Supplier extendable;
    private final Supplier maxLevel;

    public static PotionType minecraftToBukkit(Potion minecraft) {
        Preconditions.checkArgument(minecraft != null);
        Registry registry = CraftRegistry.getMinecraftRegistry(Registries.POTION);
        PotionType bukkit = (PotionType) org.bukkit.Registry.POTION.get(CraftNamespacedKey.fromMinecraft(((ResourceKey) registry.getResourceKey(minecraft).orElseThrow()).location()));

        Preconditions.checkArgument(bukkit != null);
        return bukkit;
    }

    public static Potion bukkitToMinecraft(PotionType bukkit) {
        Preconditions.checkArgument(bukkit != null);
        return (Potion) CraftRegistry.getMinecraftRegistry(Registries.POTION).getOptional(CraftNamespacedKey.toMinecraft(bukkit.getKey())).orElseThrow();
    }

    public static String bukkitToString(PotionType potionType) {
        Preconditions.checkArgument(potionType != null);
        return potionType.getKey().toString();
    }

    public static PotionType stringToBukkit(String string) {
        Preconditions.checkArgument(string != null);
        return (PotionType) org.bukkit.Registry.POTION.get(NamespacedKey.fromString(string));
    }

    public CraftPotionType(NamespacedKey key, Potion potion) {
        this.key = key;
        this.potion = potion;
        this.potionEffects = Suppliers.memoize(() -> {
            return potion.getEffects().stream().map(CraftPotionUtil::toBukkit).toList();
        });
        this.upgradeable = Suppliers.memoize(() -> {
            return org.bukkit.Registry.POTION.get(new NamespacedKey(key.getNamespace(), "strong_" + key.getKey())) != null;
        });
        this.extendable = Suppliers.memoize(() -> {
            return org.bukkit.Registry.POTION.get(new NamespacedKey(key.getNamespace(), "long_" + key.getKey())) != null;
        });
        this.maxLevel = Suppliers.memoize(() -> {
            return this.isUpgradeable() ? 2 : 1;
        });
    }

    public PotionEffectType getEffectType() {
        return this.getPotionEffects().isEmpty() ? null : ((PotionEffect) this.getPotionEffects().get(0)).getType();
    }

    public List getPotionEffects() {
        return (List) this.potionEffects.get();
    }

    public boolean isInstant() {
        return this.potion.hasInstantEffects();
    }

    public boolean isUpgradeable() {
        return (Boolean) this.upgradeable.get();
    }

    public boolean isExtendable() {
        return (Boolean) this.extendable.get();
    }

    public int getMaxLevel() {
        return (Integer) this.maxLevel.get();
    }
}
