package org.bukkit.craftbukkit.v1_20_R2.block.impl;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSet.Builder;
import java.util.Set;
import net.minecraft.world.level.block.ChiseledBookShelfBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.type.ChiseledBookshelf;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;

public final class CraftChiseledBookShelf extends CraftBlockData implements ChiseledBookshelf, Directional {

    private static final BooleanProperty[] SLOT_OCCUPIED = new BooleanProperty[]{getBoolean(ChiseledBookShelfBlock.class, "slot_0_occupied"), getBoolean(ChiseledBookShelfBlock.class, "slot_1_occupied"), getBoolean(ChiseledBookShelfBlock.class, "slot_2_occupied"), getBoolean(ChiseledBookShelfBlock.class, "slot_3_occupied"), getBoolean(ChiseledBookShelfBlock.class, "slot_4_occupied"), getBoolean(ChiseledBookShelfBlock.class, "slot_5_occupied")};
    private static final EnumProperty FACING = getEnum(ChiseledBookShelfBlock.class, "facing");

    public CraftChiseledBookShelf() {}

    public CraftChiseledBookShelf(BlockState state) {
        super(state);
    }

    public boolean isSlotOccupied(int slot) {
        return (Boolean) this.get(CraftChiseledBookShelf.SLOT_OCCUPIED[slot]);
    }

    public void setSlotOccupied(int slot, boolean has) {
        this.set((Property) CraftChiseledBookShelf.SLOT_OCCUPIED[slot], (Comparable) has);
    }

    public Set getOccupiedSlots() {
        Builder slots = ImmutableSet.builder();

        for (int index = 0; index < this.getMaximumOccupiedSlots(); ++index) {
            if (this.isSlotOccupied(index)) {
                slots.add(index);
            }
        }

        return slots.build();
    }

    public int getMaximumOccupiedSlots() {
        return CraftChiseledBookShelf.SLOT_OCCUPIED.length;
    }

    public BlockFace getFacing() {
        return (BlockFace) this.get(CraftChiseledBookShelf.FACING, BlockFace.class);
    }

    public void setFacing(BlockFace facing) {
        this.set(CraftChiseledBookShelf.FACING, (Enum) facing);
    }

    public Set getFaces() {
        return this.getValues(CraftChiseledBookShelf.FACING, BlockFace.class);
    }
}
