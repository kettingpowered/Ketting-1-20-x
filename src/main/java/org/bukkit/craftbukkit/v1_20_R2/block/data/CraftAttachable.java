package org.bukkit.craftbukkit.v1_20_R2.block.data;

import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.bukkit.block.data.Attachable;

public abstract class CraftAttachable extends CraftBlockData implements Attachable {

    private static final BooleanProperty ATTACHED = getBoolean("attached");

    public boolean isAttached() {
        return (Boolean) this.get(CraftAttachable.ATTACHED);
    }

    public void setAttached(boolean attached) {
        this.set((Property) CraftAttachable.ATTACHED, (Comparable) attached);
    }
}
