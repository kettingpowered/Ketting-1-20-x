package org.bukkit.craftbukkit.v1_20_R2.block.data.type;

import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.bukkit.block.data.type.BubbleColumn;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;

public abstract class CraftBubbleColumn extends CraftBlockData implements BubbleColumn {

    private static final BooleanProperty DRAG = getBoolean("drag");

    public boolean isDrag() {
        return (Boolean) this.get(CraftBubbleColumn.DRAG);
    }

    public void setDrag(boolean drag) {
        this.set((Property) CraftBubbleColumn.DRAG, (Comparable) drag);
    }
}
