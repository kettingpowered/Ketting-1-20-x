package org.bukkit.craftbukkit.v1_20_R2.block.impl;

import java.util.Set;
import net.minecraft.world.level.block.CocoaBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.type.Cocoa;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;

public final class CraftCocoa extends CraftBlockData implements Cocoa, Ageable, Directional {

    private static final IntegerProperty AGE = getInteger(CocoaBlock.class, "age");
    private static final EnumProperty FACING = getEnum(CocoaBlock.class, "facing");

    public CraftCocoa() {}

    public CraftCocoa(BlockState state) {
        super(state);
    }

    public int getAge() {
        return (Integer) this.get(CraftCocoa.AGE);
    }

    public void setAge(int age) {
        this.set((Property) CraftCocoa.AGE, (Comparable) age);
    }

    public int getMaximumAge() {
        return getMax(CraftCocoa.AGE);
    }

    public BlockFace getFacing() {
        return (BlockFace) this.get(CraftCocoa.FACING, BlockFace.class);
    }

    public void setFacing(BlockFace facing) {
        this.set(CraftCocoa.FACING, (Enum) facing);
    }

    public Set getFaces() {
        return this.getValues(CraftCocoa.FACING, BlockFace.class);
    }
}
