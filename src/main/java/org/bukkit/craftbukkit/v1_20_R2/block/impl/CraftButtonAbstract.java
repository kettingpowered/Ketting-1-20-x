package org.bukkit.craftbukkit.v1_20_R2.block.impl;

import java.util.Set;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.FaceAttachable;
import org.bukkit.block.data.FaceAttachable.AttachedFace;
import org.bukkit.block.data.Powerable;
import org.bukkit.block.data.type.Switch;
import org.bukkit.block.data.type.Switch.Face;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;

public final class CraftButtonAbstract extends CraftBlockData implements Switch, Directional, FaceAttachable, Powerable {

    private static final EnumProperty FACE = getEnum(ButtonBlock.class, "face");
    private static final EnumProperty FACING = getEnum(ButtonBlock.class, "facing");
    private static final EnumProperty ATTACH_FACE = getEnum(ButtonBlock.class, "face");
    private static final BooleanProperty POWERED = getBoolean(ButtonBlock.class, "powered");

    public CraftButtonAbstract() {}

    public CraftButtonAbstract(BlockState state) {
        super(state);
    }

    public Face getFace() {
        return (Face) this.get(CraftButtonAbstract.FACE, Face.class);
    }

    public void setFace(Face face) {
        this.set(CraftButtonAbstract.FACE, (Enum) face);
    }

    public BlockFace getFacing() {
        return (BlockFace) this.get(CraftButtonAbstract.FACING, BlockFace.class);
    }

    public void setFacing(BlockFace facing) {
        this.set(CraftButtonAbstract.FACING, (Enum) facing);
    }

    public Set getFaces() {
        return this.getValues(CraftButtonAbstract.FACING, BlockFace.class);
    }

    public AttachedFace getAttachedFace() {
        return (AttachedFace) this.get(CraftButtonAbstract.ATTACH_FACE, AttachedFace.class);
    }

    public void setAttachedFace(AttachedFace face) {
        this.set(CraftButtonAbstract.ATTACH_FACE, (Enum) face);
    }

    public boolean isPowered() {
        return (Boolean) this.get(CraftButtonAbstract.POWERED);
    }

    public void setPowered(boolean powered) {
        this.set((Property) CraftButtonAbstract.POWERED, (Comparable) powered);
    }
}
