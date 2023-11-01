package org.bukkit.craftbukkit.v1_20_R2.block.data.type;

import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.bukkit.block.data.type.Gate;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;

public abstract class CraftGate extends CraftBlockData implements Gate {

    private static final BooleanProperty IN_WALL = getBoolean("in_wall");

    public boolean isInWall() {
        return (Boolean) this.get(CraftGate.IN_WALL);
    }

    public void setInWall(boolean inWall) {
        this.set((Property) CraftGate.IN_WALL, (Comparable) inWall);
    }
}
