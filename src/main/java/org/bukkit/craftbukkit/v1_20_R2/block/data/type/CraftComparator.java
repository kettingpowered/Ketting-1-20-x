package org.bukkit.craftbukkit.v1_20_R2.block.data.type;

import net.minecraft.world.level.block.state.properties.EnumProperty;
import org.bukkit.block.data.type.Comparator;
import org.bukkit.block.data.type.Comparator.Mode;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;

public abstract class CraftComparator extends CraftBlockData implements Comparator {

    private static final EnumProperty MODE = getEnum("mode");

    public Mode getMode() {
        return (Mode) this.get(CraftComparator.MODE, Mode.class);
    }

    public void setMode(Mode mode) {
        this.set(CraftComparator.MODE, (Enum) mode);
    }
}
