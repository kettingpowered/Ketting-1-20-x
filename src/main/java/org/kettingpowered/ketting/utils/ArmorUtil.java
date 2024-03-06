package org.kettingpowered.ketting.utils;

import net.minecraft.world.damagesource.DamageSource;

import java.util.ArrayList;
import java.util.List;

public final class ArmorUtil {

    private static final List<DamageSource> damageToIgnore = new ArrayList<>();

    public static void addIgnoreArmorDamage(DamageSource damageSource) {
        damageToIgnore.add(damageSource);
    }

    public static boolean shouldIgnoreArmorDamage(DamageSource damageSource) {
        return damageToIgnore.contains(damageSource);
    }

    public static void removeIgnoreArmorDamage(DamageSource damageSource) {
        damageToIgnore.remove(damageSource);
    }
}
