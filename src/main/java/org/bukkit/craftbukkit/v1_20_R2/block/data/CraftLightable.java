package org.bukkit.craftbukkit.v1_20_R2.block.data;

import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.bukkit.block.data.Lightable;

public abstract class CraftLightable extends CraftBlockData implements Lightable {

    private static final BooleanProperty LIT = getBoolean("lit");

    public boolean isLit() {
        return (Boolean) this.get(CraftLightable.LIT);
    }

    public void setLit(boolean lit) {
        this.set((Property) CraftLightable.LIT, (Comparable) lit);
    }
}
