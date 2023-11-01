package org.bukkit.craftbukkit.v1_20_R2.block.impl;

import java.util.Set;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.type.Bed;
import org.bukkit.block.data.type.Bed.Part;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;

public final class CraftBed extends CraftBlockData implements Bed, Directional {

    private static final EnumProperty PART = getEnum(BedBlock.class, "part");
    private static final BooleanProperty OCCUPIED = getBoolean(BedBlock.class, "occupied");
    private static final EnumProperty FACING = getEnum(BedBlock.class, "facing");

    public CraftBed() {}

    public CraftBed(BlockState state) {
        super(state);
    }

    public Part getPart() {
        return (Part) this.get(CraftBed.PART, Part.class);
    }

    public void setPart(Part part) {
        this.set(CraftBed.PART, (Enum) part);
    }

    public boolean isOccupied() {
        return (Boolean) this.get(CraftBed.OCCUPIED);
    }

    public BlockFace getFacing() {
        return (BlockFace) this.get(CraftBed.FACING, BlockFace.class);
    }

    public void setFacing(BlockFace facing) {
        this.set(CraftBed.FACING, (Enum) facing);
    }

    public Set getFaces() {
        return this.getValues(CraftBed.FACING, BlockFace.class);
    }
}
