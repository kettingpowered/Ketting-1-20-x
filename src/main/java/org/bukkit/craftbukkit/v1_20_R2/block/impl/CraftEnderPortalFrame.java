package org.bukkit.craftbukkit.v1_20_R2.block.impl;

import java.util.Set;
import net.minecraft.world.level.block.EndPortalFrameBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.type.EndPortalFrame;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;

public final class CraftEnderPortalFrame extends CraftBlockData implements EndPortalFrame, Directional {

    private static final BooleanProperty EYE = getBoolean(EndPortalFrameBlock.class, "eye");
    private static final EnumProperty FACING = getEnum(EndPortalFrameBlock.class, "facing");

    public CraftEnderPortalFrame() {}

    public CraftEnderPortalFrame(BlockState state) {
        super(state);
    }

    public boolean hasEye() {
        return (Boolean) this.get(CraftEnderPortalFrame.EYE);
    }

    public void setEye(boolean eye) {
        this.set((Property) CraftEnderPortalFrame.EYE, (Comparable) eye);
    }

    public BlockFace getFacing() {
        return (BlockFace) this.get(CraftEnderPortalFrame.FACING, BlockFace.class);
    }

    public void setFacing(BlockFace facing) {
        this.set(CraftEnderPortalFrame.FACING, (Enum) facing);
    }

    public Set getFaces() {
        return this.getValues(CraftEnderPortalFrame.FACING, BlockFace.class);
    }
}
