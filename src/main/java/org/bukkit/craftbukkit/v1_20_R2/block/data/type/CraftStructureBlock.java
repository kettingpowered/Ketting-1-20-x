package org.bukkit.craftbukkit.v1_20_R2.block.data.type;

import net.minecraft.world.level.block.state.properties.EnumProperty;
import org.bukkit.block.data.type.StructureBlock;
import org.bukkit.block.data.type.StructureBlock.Mode;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;

public abstract class CraftStructureBlock extends CraftBlockData implements StructureBlock {

    private static final EnumProperty MODE = getEnum("mode");

    public Mode getMode() {
        return (Mode) this.get(CraftStructureBlock.MODE, Mode.class);
    }

    public void setMode(Mode mode) {
        this.set(CraftStructureBlock.MODE, (Enum) mode);
    }
}
