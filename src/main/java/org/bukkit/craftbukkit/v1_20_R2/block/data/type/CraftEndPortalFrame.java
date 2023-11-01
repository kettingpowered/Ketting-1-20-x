package org.bukkit.craftbukkit.v1_20_R2.block.data.type;

import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.bukkit.block.data.type.EndPortalFrame;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;

public abstract class CraftEndPortalFrame extends CraftBlockData implements EndPortalFrame {

    private static final BooleanProperty EYE = getBoolean("eye");

    public boolean hasEye() {
        return (Boolean) this.get(CraftEndPortalFrame.EYE);
    }

    public void setEye(boolean eye) {
        this.set((Property) CraftEndPortalFrame.EYE, (Comparable) eye);
    }
}
