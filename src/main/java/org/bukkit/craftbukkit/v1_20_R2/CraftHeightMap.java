package org.bukkit.craftbukkit.v1_20_R2;

import net.minecraft.world.level.levelgen.Heightmap;
import org.bukkit.HeightMap;

public final class CraftHeightMap {

    private static volatile int[] $SWITCH_TABLE$org$bukkit$HeightMap;
    private static volatile int[] $SWITCH_TABLE$net$minecraft$world$level$levelgen$HeightMap$Type;

    private CraftHeightMap() {}

    public static Heightmap.Types toNMS(HeightMap bukkitHeightMap) {
        switch ($SWITCH_TABLE$org$bukkit$HeightMap()[bukkitHeightMap.ordinal()]) {
            case 1:
                return Heightmap.Types.MOTION_BLOCKING;
            case 2:
                return Heightmap.Types.MOTION_BLOCKING_NO_LEAVES;
            case 3:
                return Heightmap.Types.OCEAN_FLOOR;
            case 4:
                return Heightmap.Types.OCEAN_FLOOR_WG;
            case 5:
                return Heightmap.Types.WORLD_SURFACE;
            case 6:
                return Heightmap.Types.WORLD_SURFACE_WG;
            default:
                throw new EnumConstantNotPresentException(Heightmap.Types.class, bukkitHeightMap.name());
        }
    }

    public static HeightMap fromNMS(Heightmap.Types nmsHeightMapType) {
        switch ($SWITCH_TABLE$net$minecraft$world$level$levelgen$HeightMap$Type()[nmsHeightMapType.ordinal()]) {
            case 1:
                return HeightMap.WORLD_SURFACE_WG;
            case 2:
                return HeightMap.WORLD_SURFACE;
            case 3:
                return HeightMap.OCEAN_FLOOR_WG;
            case 4:
                return HeightMap.OCEAN_FLOOR;
            case 5:
                return HeightMap.MOTION_BLOCKING;
            case 6:
                return HeightMap.MOTION_BLOCKING_NO_LEAVES;
            default:
                throw new EnumConstantNotPresentException(HeightMap.class, nmsHeightMapType.name());
        }
    }

    static int[] $SWITCH_TABLE$org$bukkit$HeightMap() {
        int[] aint = CraftHeightMap.$SWITCH_TABLE$org$bukkit$HeightMap;

        if (aint != null) {
            return aint;
        } else {
            int[] aint1 = new int[HeightMap.values().length];

            try {
                aint1[HeightMap.MOTION_BLOCKING.ordinal()] = 1;
            } catch (NoSuchFieldError nosuchfielderror) {
                ;
            }

            try {
                aint1[HeightMap.MOTION_BLOCKING_NO_LEAVES.ordinal()] = 2;
            } catch (NoSuchFieldError nosuchfielderror1) {
                ;
            }

            try {
                aint1[HeightMap.OCEAN_FLOOR.ordinal()] = 3;
            } catch (NoSuchFieldError nosuchfielderror2) {
                ;
            }

            try {
                aint1[HeightMap.OCEAN_FLOOR_WG.ordinal()] = 4;
            } catch (NoSuchFieldError nosuchfielderror3) {
                ;
            }

            try {
                aint1[HeightMap.WORLD_SURFACE.ordinal()] = 5;
            } catch (NoSuchFieldError nosuchfielderror4) {
                ;
            }

            try {
                aint1[HeightMap.WORLD_SURFACE_WG.ordinal()] = 6;
            } catch (NoSuchFieldError nosuchfielderror5) {
                ;
            }

            CraftHeightMap.$SWITCH_TABLE$org$bukkit$HeightMap = aint1;
            return aint1;
        }
    }

    static int[] $SWITCH_TABLE$net$minecraft$world$level$levelgen$HeightMap$Type() {
        int[] aint = CraftHeightMap.$SWITCH_TABLE$net$minecraft$world$level$levelgen$HeightMap$Type;

        if (aint != null) {
            return aint;
        } else {
            int[] aint1 = new int[Heightmap.Types.values().length];

            try {
                aint1[Heightmap.Types.MOTION_BLOCKING.ordinal()] = 5;
            } catch (NoSuchFieldError nosuchfielderror) {
                ;
            }

            try {
                aint1[Heightmap.Types.MOTION_BLOCKING_NO_LEAVES.ordinal()] = 6;
            } catch (NoSuchFieldError nosuchfielderror1) {
                ;
            }

            try {
                aint1[Heightmap.Types.OCEAN_FLOOR.ordinal()] = 4;
            } catch (NoSuchFieldError nosuchfielderror2) {
                ;
            }

            try {
                aint1[Heightmap.Types.OCEAN_FLOOR_WG.ordinal()] = 3;
            } catch (NoSuchFieldError nosuchfielderror3) {
                ;
            }

            try {
                aint1[Heightmap.Types.WORLD_SURFACE.ordinal()] = 2;
            } catch (NoSuchFieldError nosuchfielderror4) {
                ;
            }

            try {
                aint1[Heightmap.Types.WORLD_SURFACE_WG.ordinal()] = 1;
            } catch (NoSuchFieldError nosuchfielderror5) {
                ;
            }

            CraftHeightMap.$SWITCH_TABLE$net$minecraft$world$level$levelgen$HeightMap$Type = aint1;
            return aint1;
        }
    }
}
