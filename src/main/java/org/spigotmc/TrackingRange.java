package org.spigotmc;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Display;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.entity.decoration.Painting;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Ghast;

public class TrackingRange {

    public static int getEntityTrackingRange(Entity entity, int defaultRange) {
        if (defaultRange == 0) {
            return defaultRange;
        } else {
            SpigotWorldConfig config = entity.level().spigotConfig;

            return entity instanceof ServerPlayer ? config.playerTrackingRange : (entity.activationType != ActivationRange.ActivationType.MONSTER && entity.activationType != ActivationRange.ActivationType.RAIDER ? (entity instanceof Ghast ? (config.monsterTrackingRange > config.monsterActivationRange ? config.monsterTrackingRange : config.monsterActivationRange) : (entity.activationType == ActivationRange.ActivationType.ANIMAL ? config.animalTrackingRange : (!(entity instanceof ItemFrame) && !(entity instanceof Painting) && !(entity instanceof ItemEntity) && !(entity instanceof ExperienceOrb) ? (entity instanceof Display ? config.displayTrackingRange : config.otherTrackingRange) : config.miscTrackingRange))) : config.monsterTrackingRange);
        }
    }
}
