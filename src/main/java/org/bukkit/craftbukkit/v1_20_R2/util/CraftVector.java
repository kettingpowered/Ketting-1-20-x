package org.bukkit.craftbukkit.v1_20_R2.util;

import net.minecraft.world.phys.Vec3;
import org.bukkit.util.Vector;

public final class CraftVector {

    private CraftVector() {}

    public static Vector toBukkit(Vec3 nms) {
        return new Vector(nms.x, nms.y, nms.z);
    }

    public static Vec3 toNMS(Vector bukkit) {
        return new Vec3(bukkit.getX(), bukkit.getY(), bukkit.getZ());
    }
}
