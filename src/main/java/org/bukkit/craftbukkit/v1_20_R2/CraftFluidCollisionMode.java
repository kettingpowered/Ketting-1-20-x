package org.bukkit.craftbukkit.v1_20_R2;

import net.minecraft.world.level.ClipContext;
import org.bukkit.FluidCollisionMode;

public final class CraftFluidCollisionMode {

    private static volatile int[] $SWITCH_TABLE$org$bukkit$FluidCollisionMode;

    private CraftFluidCollisionMode() {}

    public static ClipContext.Fluid toNMS(FluidCollisionMode fluidCollisionMode) {
        if (fluidCollisionMode == null) {
            return null;
        } else {
            switch ($SWITCH_TABLE$org$bukkit$FluidCollisionMode()[fluidCollisionMode.ordinal()]) {
                case 1:
                    return ClipContext.Fluid.NONE;
                case 2:
                    return ClipContext.Fluid.SOURCE_ONLY;
                case 3:
                    return ClipContext.Fluid.ANY;
                default:
                    return null;
            }
        }
    }

    static int[] $SWITCH_TABLE$org$bukkit$FluidCollisionMode() {
        int[] aint = CraftFluidCollisionMode.$SWITCH_TABLE$org$bukkit$FluidCollisionMode;

        if (aint != null) {
            return aint;
        } else {
            int[] aint1 = new int[FluidCollisionMode.values().length];

            try {
                aint1[FluidCollisionMode.ALWAYS.ordinal()] = 3;
            } catch (NoSuchFieldError nosuchfielderror) {
                ;
            }

            try {
                aint1[FluidCollisionMode.NEVER.ordinal()] = 1;
            } catch (NoSuchFieldError nosuchfielderror1) {
                ;
            }

            try {
                aint1[FluidCollisionMode.SOURCE_ONLY.ordinal()] = 2;
            } catch (NoSuchFieldError nosuchfielderror2) {
                ;
            }

            CraftFluidCollisionMode.$SWITCH_TABLE$org$bukkit$FluidCollisionMode = aint1;
            return aint1;
        }
    }
}
