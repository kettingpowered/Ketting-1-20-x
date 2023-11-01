package org.bukkit.craftbukkit.v1_20_R2.block.impl;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import org.bukkit.block.data.type.StructureBlock;
import org.bukkit.block.data.type.StructureBlock.Mode;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;

public final class CraftStructure extends CraftBlockData implements StructureBlock {

    private static final EnumProperty MODE = getEnum(net.minecraft.world.level.block.StructureBlock.class, "mode");

    public CraftStructure() {}

    public CraftStructure(BlockState state) {
        super(state);
    }

    public Mode getMode() {
        return (Mode) this.get(CraftStructure.MODE, Mode.class);
    }

    public void setMode(Mode mode) {
        this.set(CraftStructure.MODE, (Enum) mode);
    }
}
