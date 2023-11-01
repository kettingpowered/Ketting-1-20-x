package org.bukkit.craftbukkit.v1_20_R2.block.impl;

import java.util.Set;
import net.minecraft.world.level.block.SmokerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.Lightable;
import org.bukkit.block.data.type.Furnace;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;

public final class CraftSmoker extends CraftBlockData implements Furnace, Directional, Lightable {

    private static final EnumProperty FACING = getEnum(SmokerBlock.class, "facing");
    private static final BooleanProperty LIT = getBoolean(SmokerBlock.class, "lit");

    public CraftSmoker() {}

    public CraftSmoker(BlockState state) {
        super(state);
    }

    public BlockFace getFacing() {
        return (BlockFace) this.get(CraftSmoker.FACING, BlockFace.class);
    }

    public void setFacing(BlockFace facing) {
        this.set(CraftSmoker.FACING, (Enum) facing);
    }

    public Set getFaces() {
        return this.getValues(CraftSmoker.FACING, BlockFace.class);
    }

    public boolean isLit() {
        return (Boolean) this.get(CraftSmoker.LIT);
    }

    public void setLit(boolean lit) {
        this.set((Property) CraftSmoker.LIT, (Comparable) lit);
    }
}
