package org.bukkit.craftbukkit.v1_20_R2.block.impl;

import java.util.Set;
import net.minecraft.world.level.block.BellBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.Powerable;
import org.bukkit.block.data.type.Bell;
import org.bukkit.block.data.type.Bell.Attachment;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;

public final class CraftBell extends CraftBlockData implements Bell, Directional, Powerable {

    private static final EnumProperty ATTACHMENT = getEnum(BellBlock.class, "attachment");
    private static final EnumProperty FACING = getEnum(BellBlock.class, "facing");
    private static final BooleanProperty POWERED = getBoolean(BellBlock.class, "powered");

    public CraftBell() {}

    public CraftBell(BlockState state) {
        super(state);
    }

    public Attachment getAttachment() {
        return (Attachment) this.get(CraftBell.ATTACHMENT, Attachment.class);
    }

    public void setAttachment(Attachment leaves) {
        this.set(CraftBell.ATTACHMENT, (Enum) leaves);
    }

    public BlockFace getFacing() {
        return (BlockFace) this.get(CraftBell.FACING, BlockFace.class);
    }

    public void setFacing(BlockFace facing) {
        this.set(CraftBell.FACING, (Enum) facing);
    }

    public Set getFaces() {
        return this.getValues(CraftBell.FACING, BlockFace.class);
    }

    public boolean isPowered() {
        return (Boolean) this.get(CraftBell.POWERED);
    }

    public void setPowered(boolean powered) {
        this.set((Property) CraftBell.POWERED, (Comparable) powered);
    }
}
