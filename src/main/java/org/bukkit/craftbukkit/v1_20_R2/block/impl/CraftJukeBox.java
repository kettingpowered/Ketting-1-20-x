package org.bukkit.craftbukkit.v1_20_R2.block.impl;

import net.minecraft.world.level.block.JukeboxBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import org.bukkit.block.data.type.Jukebox;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;

public final class CraftJukeBox extends CraftBlockData implements Jukebox {

    private static final BooleanProperty HAS_RECORD = getBoolean(JukeboxBlock.class, "has_record");

    public CraftJukeBox() {}

    public CraftJukeBox(BlockState state) {
        super(state);
    }

    public boolean hasRecord() {
        return (Boolean) this.get(CraftJukeBox.HAS_RECORD);
    }
}
