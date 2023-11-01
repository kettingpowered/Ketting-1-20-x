package org.bukkit.craftbukkit.v1_20_R2.block.data.type;

import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Wall;
import org.bukkit.block.data.type.Wall.Height;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;

public abstract class CraftWall extends CraftBlockData implements Wall {

    private static final BooleanProperty UP = getBoolean("up");
    private static final EnumProperty[] HEIGHTS = new EnumProperty[]{getEnum("north"), getEnum("east"), getEnum("south"), getEnum("west")};

    public boolean isUp() {
        return (Boolean) this.get(CraftWall.UP);
    }

    public void setUp(boolean up) {
        this.set((Property) CraftWall.UP, (Comparable) up);
    }

    public Height getHeight(BlockFace face) {
        return (Height) this.get(CraftWall.HEIGHTS[face.ordinal()], Height.class);
    }

    public void setHeight(BlockFace face, Height height) {
        this.set(CraftWall.HEIGHTS[face.ordinal()], (Enum) height);
    }
}
