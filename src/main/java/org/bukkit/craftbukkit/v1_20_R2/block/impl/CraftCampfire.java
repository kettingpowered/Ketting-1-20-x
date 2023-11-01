package org.bukkit.craftbukkit.v1_20_R2.block.impl;

import java.util.Set;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.Lightable;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.block.data.type.Campfire;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;

public final class CraftCampfire extends CraftBlockData implements Campfire, Directional, Lightable, Waterlogged {

    private static final BooleanProperty SIGNAL_FIRE = getBoolean(CampfireBlock.class, "signal_fire");
    private static final EnumProperty FACING = getEnum(CampfireBlock.class, "facing");
    private static final BooleanProperty LIT = getBoolean(CampfireBlock.class, "lit");
    private static final BooleanProperty WATERLOGGED = getBoolean(CampfireBlock.class, "waterlogged");

    public CraftCampfire() {}

    public CraftCampfire(BlockState state) {
        super(state);
    }

    public boolean isSignalFire() {
        return (Boolean) this.get(CraftCampfire.SIGNAL_FIRE);
    }

    public void setSignalFire(boolean signalFire) {
        this.set((Property) CraftCampfire.SIGNAL_FIRE, (Comparable) signalFire);
    }

    public BlockFace getFacing() {
        return (BlockFace) this.get(CraftCampfire.FACING, BlockFace.class);
    }

    public void setFacing(BlockFace facing) {
        this.set(CraftCampfire.FACING, (Enum) facing);
    }

    public Set getFaces() {
        return this.getValues(CraftCampfire.FACING, BlockFace.class);
    }

    public boolean isLit() {
        return (Boolean) this.get(CraftCampfire.LIT);
    }

    public void setLit(boolean lit) {
        this.set((Property) CraftCampfire.LIT, (Comparable) lit);
    }

    public boolean isWaterlogged() {
        return (Boolean) this.get(CraftCampfire.WATERLOGGED);
    }

    public void setWaterlogged(boolean waterlogged) {
        this.set((Property) CraftCampfire.WATERLOGGED, (Comparable) waterlogged);
    }
}
