package org.bukkit.craftbukkit.v1_20_R2.block.data.type;

import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import org.bukkit.block.data.type.Bed;
import org.bukkit.block.data.type.Bed.Part;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;

public abstract class CraftBed extends CraftBlockData implements Bed {

    private static final EnumProperty PART = getEnum("part");
    private static final BooleanProperty OCCUPIED = getBoolean("occupied");

    public Part getPart() {
        return (Part) this.get(CraftBed.PART, Part.class);
    }

    public void setPart(Part part) {
        this.set(CraftBed.PART, (Enum) part);
    }

    public boolean isOccupied() {
        return (Boolean) this.get(CraftBed.OCCUPIED);
    }
}
