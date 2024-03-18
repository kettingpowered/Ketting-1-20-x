package org.kettingpowered.ketting.spigot;

import net.minecraft.world.entity.Entity;

import java.util.ArrayList;
import java.util.List;

public class InactiveTickExclusions {

    private static final List<Class<? extends Entity>> EXCLUDED_ENTITIES = new ArrayList<>();

    static {
        EXCLUDED_ENTITIES.add(net.minecraft.world.entity.monster.warden.Warden.class);
    }

    public static boolean isExcluded(Entity entity) {
        return EXCLUDED_ENTITIES.contains(entity.getClass());
    }
}
