package org.bukkit.craftbukkit.v1_20_R2.block.data.type;

import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.bukkit.block.data.type.CommandBlock;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;

public abstract class CraftCommandBlock extends CraftBlockData implements CommandBlock {

    private static final BooleanProperty CONDITIONAL = getBoolean("conditional");

    public boolean isConditional() {
        return (Boolean) this.get(CraftCommandBlock.CONDITIONAL);
    }

    public void setConditional(boolean conditional) {
        this.set((Property) CraftCommandBlock.CONDITIONAL, (Comparable) conditional);
    }
}
