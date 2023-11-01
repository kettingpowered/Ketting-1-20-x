package org.bukkit.craftbukkit.v1_20_R2.util;

import net.minecraft.world.entity.MobCategory;
import org.bukkit.entity.SpawnCategory;

public class CraftSpawnCategory {

    private static volatile int[] $SWITCH_TABLE$org$bukkit$entity$SpawnCategory;
    private static volatile int[] $SWITCH_TABLE$net$minecraft$world$entity$EnumCreatureType;

    public static boolean isValidForLimits(SpawnCategory spawnCategory) {
        return spawnCategory != null && spawnCategory != SpawnCategory.MISC;
    }

    public static String getConfigNameSpawnLimit(SpawnCategory spawnCategory) {
        String s;

        switch ($SWITCH_TABLE$org$bukkit$entity$SpawnCategory()[spawnCategory.ordinal()]) {
            case 1:
                s = "spawn-limits.monsters";
                break;
            case 2:
                s = "spawn-limits.animals";
                break;
            case 3:
                s = "spawn-limits.water-animals";
                break;
            case 4:
                s = "spawn-limits.water-ambient";
                break;
            case 5:
                s = "spawn-limits.water-underground-creature";
                break;
            case 6:
                s = "spawn-limits.ambient";
                break;
            case 7:
                s = "spawn-limits.axolotls";
                break;
            default:
                throw new UnsupportedOperationException("Unknown Config value " + spawnCategory + " for spawn-limits");
        }

        return s;
    }

    public static String getConfigNameTicksPerSpawn(SpawnCategory spawnCategory) {
        String s;

        switch ($SWITCH_TABLE$org$bukkit$entity$SpawnCategory()[spawnCategory.ordinal()]) {
            case 1:
                s = "ticks-per.monster-spawns";
                break;
            case 2:
                s = "ticks-per.animal-spawns";
                break;
            case 3:
                s = "ticks-per.water-spawns";
                break;
            case 4:
                s = "ticks-per.water-ambient-spawns";
                break;
            case 5:
                s = "ticks-per.water-underground-creature-spawns";
                break;
            case 6:
                s = "ticks-per.ambient-spawns";
                break;
            case 7:
                s = "ticks-per.axolotl-spawns";
                break;
            default:
                throw new UnsupportedOperationException("Unknown Config value " + spawnCategory + " for ticks-per");
        }

        return s;
    }

    public static long getDefaultTicksPerSpawn(SpawnCategory spawnCategory) {
        long i;

        switch ($SWITCH_TABLE$org$bukkit$entity$SpawnCategory()[spawnCategory.ordinal()]) {
            case 1:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
                i = 1L;
                break;
            case 2:
                i = 400L;
                break;
            default:
                throw new UnsupportedOperationException("Unknown Config value " + spawnCategory + " for ticks-per");
        }

        return i;
    }

    public static SpawnCategory toBukkit(MobCategory enumCreatureType) {
        SpawnCategory spawncategory;

        switch ($SWITCH_TABLE$net$minecraft$world$entity$EnumCreatureType()[enumCreatureType.ordinal()]) {
            case 1:
                spawncategory = SpawnCategory.MONSTER;
                break;
            case 2:
                spawncategory = SpawnCategory.ANIMAL;
                break;
            case 3:
                spawncategory = SpawnCategory.AMBIENT;
                break;
            case 4:
                spawncategory = SpawnCategory.AXOLOTL;
                break;
            case 5:
                spawncategory = SpawnCategory.WATER_UNDERGROUND_CREATURE;
                break;
            case 6:
                spawncategory = SpawnCategory.WATER_ANIMAL;
                break;
            case 7:
                spawncategory = SpawnCategory.WATER_AMBIENT;
                break;
            case 8:
                spawncategory = SpawnCategory.MISC;
                break;
            default:
                throw new UnsupportedOperationException("Unknown EnumCreatureType " + enumCreatureType + " for SpawnCategory");
        }

        return spawncategory;
    }

    public static MobCategory toNMS(SpawnCategory spawnCategory) {
        MobCategory mobcategory;

        switch ($SWITCH_TABLE$org$bukkit$entity$SpawnCategory()[spawnCategory.ordinal()]) {
            case 1:
                mobcategory = MobCategory.MONSTER;
                break;
            case 2:
                mobcategory = MobCategory.CREATURE;
                break;
            case 3:
                mobcategory = MobCategory.WATER_CREATURE;
                break;
            case 4:
                mobcategory = MobCategory.WATER_AMBIENT;
                break;
            case 5:
                mobcategory = MobCategory.UNDERGROUND_WATER_CREATURE;
                break;
            case 6:
                mobcategory = MobCategory.AMBIENT;
                break;
            case 7:
                mobcategory = MobCategory.AXOLOTLS;
                break;
            case 8:
                mobcategory = MobCategory.MISC;
                break;
            default:
                throw new UnsupportedOperationException("Unknown SpawnCategory " + spawnCategory + " for EnumCreatureType");
        }

        return mobcategory;
    }

    static int[] $SWITCH_TABLE$org$bukkit$entity$SpawnCategory() {
        int[] aint = CraftSpawnCategory.$SWITCH_TABLE$org$bukkit$entity$SpawnCategory;

        if (aint != null) {
            return aint;
        } else {
            int[] aint1 = new int[SpawnCategory.values().length];

            try {
                aint1[SpawnCategory.AMBIENT.ordinal()] = 6;
            } catch (NoSuchFieldError nosuchfielderror) {
                ;
            }

            try {
                aint1[SpawnCategory.ANIMAL.ordinal()] = 2;
            } catch (NoSuchFieldError nosuchfielderror1) {
                ;
            }

            try {
                aint1[SpawnCategory.AXOLOTL.ordinal()] = 7;
            } catch (NoSuchFieldError nosuchfielderror2) {
                ;
            }

            try {
                aint1[SpawnCategory.MISC.ordinal()] = 8;
            } catch (NoSuchFieldError nosuchfielderror3) {
                ;
            }

            try {
                aint1[SpawnCategory.MONSTER.ordinal()] = 1;
            } catch (NoSuchFieldError nosuchfielderror4) {
                ;
            }

            try {
                aint1[SpawnCategory.WATER_AMBIENT.ordinal()] = 4;
            } catch (NoSuchFieldError nosuchfielderror5) {
                ;
            }

            try {
                aint1[SpawnCategory.WATER_ANIMAL.ordinal()] = 3;
            } catch (NoSuchFieldError nosuchfielderror6) {
                ;
            }

            try {
                aint1[SpawnCategory.WATER_UNDERGROUND_CREATURE.ordinal()] = 5;
            } catch (NoSuchFieldError nosuchfielderror7) {
                ;
            }

            CraftSpawnCategory.$SWITCH_TABLE$org$bukkit$entity$SpawnCategory = aint1;
            return aint1;
        }
    }

    static int[] $SWITCH_TABLE$net$minecraft$world$entity$EnumCreatureType() {
        int[] aint = CraftSpawnCategory.$SWITCH_TABLE$net$minecraft$world$entity$EnumCreatureType;

        if (aint != null) {
            return aint;
        } else {
            int[] aint1 = new int[MobCategory.values().length];

            try {
                aint1[MobCategory.AMBIENT.ordinal()] = 3;
            } catch (NoSuchFieldError nosuchfielderror) {
                ;
            }

            try {
                aint1[MobCategory.AXOLOTLS.ordinal()] = 4;
            } catch (NoSuchFieldError nosuchfielderror1) {
                ;
            }

            try {
                aint1[MobCategory.CREATURE.ordinal()] = 2;
            } catch (NoSuchFieldError nosuchfielderror2) {
                ;
            }

            try {
                aint1[MobCategory.MISC.ordinal()] = 8;
            } catch (NoSuchFieldError nosuchfielderror3) {
                ;
            }

            try {
                aint1[MobCategory.MONSTER.ordinal()] = 1;
            } catch (NoSuchFieldError nosuchfielderror4) {
                ;
            }

            try {
                aint1[MobCategory.UNDERGROUND_WATER_CREATURE.ordinal()] = 5;
            } catch (NoSuchFieldError nosuchfielderror5) {
                ;
            }

            try {
                aint1[MobCategory.WATER_AMBIENT.ordinal()] = 7;
            } catch (NoSuchFieldError nosuchfielderror6) {
                ;
            }

            try {
                aint1[MobCategory.WATER_CREATURE.ordinal()] = 6;
            } catch (NoSuchFieldError nosuchfielderror7) {
                ;
            }

            CraftSpawnCategory.$SWITCH_TABLE$net$minecraft$world$entity$EnumCreatureType = aint1;
            return aint1;
        }
    }
}
