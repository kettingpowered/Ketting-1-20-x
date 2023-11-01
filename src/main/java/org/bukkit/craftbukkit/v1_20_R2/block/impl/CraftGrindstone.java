package org.bukkit.craftbukkit.v1_20_R2.block.impl;

import java.util.Set;
import net.minecraft.world.level.block.GrindstoneBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.FaceAttachable;
import org.bukkit.block.data.FaceAttachable.AttachedFace;
import org.bukkit.block.data.type.Grindstone;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;

public final class CraftGrindstone extends CraftBlockData implements Grindstone, Directional, FaceAttachable {

    private static final EnumProperty FACING = getEnum(GrindstoneBlock.class, "facing");
    private static final EnumProperty ATTACH_FACE = getEnum(GrindstoneBlock.class, "face");

    public CraftGrindstone() {}

    public CraftGrindstone(BlockState state) {
        super(state);
    }

    public BlockFace getFacing() {
        return (BlockFace) this.get(CraftGrindstone.FACING, BlockFace.class);
    }

    public void setFacing(BlockFace facing) {
        this.set(CraftGrindstone.FACING, (Enum) facing);
    }

    public Set getFaces() {
        return this.getValues(CraftGrindstone.FACING, BlockFace.class);
    }

    public AttachedFace getAttachedFace() {
        return (AttachedFace) this.get(CraftGrindstone.ATTACH_FACE, AttachedFace.class);
    }

    public void setAttachedFace(AttachedFace face) {
        this.set(CraftGrindstone.ATTACH_FACE, (Enum) face);
    }
}
