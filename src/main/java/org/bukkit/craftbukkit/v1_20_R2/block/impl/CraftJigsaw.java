package org.bukkit.craftbukkit.v1_20_R2.block.impl;

import net.minecraft.world.level.block.JigsawBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import org.bukkit.block.data.type.Jigsaw;
import org.bukkit.block.data.type.Jigsaw.Orientation;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;

public final class CraftJigsaw extends CraftBlockData implements Jigsaw {

    private static final EnumProperty ORIENTATION = getEnum(JigsawBlock.class, "orientation");

    public CraftJigsaw() {}

    public CraftJigsaw(BlockState state) {
        super(state);
    }

    public Orientation getOrientation() {
        return (Orientation) this.get(CraftJigsaw.ORIENTATION, Orientation.class);
    }

    public void setOrientation(Orientation orientation) {
        this.set(CraftJigsaw.ORIENTATION, (Enum) orientation);
    }
}
