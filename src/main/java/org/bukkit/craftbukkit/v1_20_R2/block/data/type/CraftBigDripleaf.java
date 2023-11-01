package org.bukkit.craftbukkit.v1_20_R2.block.data.type;

import net.minecraft.world.level.block.state.properties.EnumProperty;
import org.bukkit.block.data.type.BigDripleaf;
import org.bukkit.block.data.type.BigDripleaf.Tilt;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;

public abstract class CraftBigDripleaf extends CraftBlockData implements BigDripleaf {

    private static final EnumProperty TILT = getEnum("tilt");

    public Tilt getTilt() {
        return (Tilt) this.get(CraftBigDripleaf.TILT, Tilt.class);
    }

    public void setTilt(Tilt tilt) {
        this.set(CraftBigDripleaf.TILT, (Enum) tilt);
    }
}
