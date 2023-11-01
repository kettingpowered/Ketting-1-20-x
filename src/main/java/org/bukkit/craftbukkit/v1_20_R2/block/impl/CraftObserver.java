package org.bukkit.craftbukkit.v1_20_R2.block.impl;

import java.util.Set;
import net.minecraft.world.level.block.ObserverBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.Powerable;
import org.bukkit.block.data.type.Observer;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;

public final class CraftObserver extends CraftBlockData implements Observer, Directional, Powerable {

    private static final EnumProperty FACING = getEnum(ObserverBlock.class, "facing");
    private static final BooleanProperty POWERED = getBoolean(ObserverBlock.class, "powered");

    public CraftObserver() {}

    public CraftObserver(BlockState state) {
        super(state);
    }

    public BlockFace getFacing() {
        return (BlockFace) this.get(CraftObserver.FACING, BlockFace.class);
    }

    public void setFacing(BlockFace facing) {
        this.set(CraftObserver.FACING, (Enum) facing);
    }

    public Set getFaces() {
        return this.getValues(CraftObserver.FACING, BlockFace.class);
    }

    public boolean isPowered() {
        return (Boolean) this.get(CraftObserver.POWERED);
    }

    public void setPowered(boolean powered) {
        this.set((Property) CraftObserver.POWERED, (Comparable) powered);
    }
}
