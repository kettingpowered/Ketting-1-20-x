package org.bukkit.craftbukkit.v1_20_R2.block.impl;

import java.util.Set;
import net.minecraft.world.level.block.RedstoneWallTorchBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.Lightable;
import org.bukkit.block.data.type.RedstoneWallTorch;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;

public final class CraftRedstoneTorchWall extends CraftBlockData implements RedstoneWallTorch, Directional, Lightable {

    private static final EnumProperty FACING = getEnum(RedstoneWallTorchBlock.class, "facing");
    private static final BooleanProperty LIT = getBoolean(RedstoneWallTorchBlock.class, "lit");

    public CraftRedstoneTorchWall() {}

    public CraftRedstoneTorchWall(BlockState state) {
        super(state);
    }

    public BlockFace getFacing() {
        return (BlockFace) this.get(CraftRedstoneTorchWall.FACING, BlockFace.class);
    }

    public void setFacing(BlockFace facing) {
        this.set(CraftRedstoneTorchWall.FACING, (Enum) facing);
    }

    public Set getFaces() {
        return this.getValues(CraftRedstoneTorchWall.FACING, BlockFace.class);
    }

    public boolean isLit() {
        return (Boolean) this.get(CraftRedstoneTorchWall.LIT);
    }

    public void setLit(boolean lit) {
        this.set((Property) CraftRedstoneTorchWall.LIT, (Comparable) lit);
    }
}
