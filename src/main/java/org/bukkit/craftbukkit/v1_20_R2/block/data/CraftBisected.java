package org.bukkit.craftbukkit.v1_20_R2.block.data;

import net.minecraft.world.level.block.state.properties.EnumProperty;
import org.bukkit.block.data.Bisected;
import org.bukkit.block.data.Bisected.Half;

public class CraftBisected extends CraftBlockData implements Bisected {

    private static final EnumProperty HALF = getEnum("half");

    public Half getHalf() {
        return (Half) this.get(CraftBisected.HALF, Half.class);
    }

    public void setHalf(Half half) {
        this.set(CraftBisected.HALF, (Enum) half);
    }
}
