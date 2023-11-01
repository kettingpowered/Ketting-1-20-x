package org.bukkit.craftbukkit.v1_20_R2.block.impl;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSet.Builder;
import java.util.Set;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.MultipleFacing;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.block.data.type.Fence;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;

public final class CraftFence extends CraftBlockData implements Fence, MultipleFacing, Waterlogged {

    private static final BooleanProperty[] FACES = new BooleanProperty[]{getBoolean(FenceBlock.class, "north", true), getBoolean(FenceBlock.class, "east", true), getBoolean(FenceBlock.class, "south", true), getBoolean(FenceBlock.class, "west", true), getBoolean(FenceBlock.class, "up", true), getBoolean(FenceBlock.class, "down", true)};
    private static final BooleanProperty WATERLOGGED = getBoolean(FenceBlock.class, "waterlogged");

    public CraftFence() {}

    public CraftFence(BlockState state) {
        super(state);
    }

    public boolean hasFace(BlockFace face) {
        BooleanProperty state = CraftFence.FACES[face.ordinal()];

        if (state == null) {
            throw new IllegalArgumentException("Non-allowed face " + face + ". Check MultipleFacing.getAllowedFaces.");
        } else {
            return (Boolean) this.get(state);
        }
    }

    public void setFace(BlockFace face, boolean has) {
        BooleanProperty state = CraftFence.FACES[face.ordinal()];

        if (state == null) {
            throw new IllegalArgumentException("Non-allowed face " + face + ". Check MultipleFacing.getAllowedFaces.");
        } else {
            this.set((Property) state, (Comparable) has);
        }
    }

    public Set getFaces() {
        Builder faces = ImmutableSet.builder();

        for (int i = 0; i < CraftFence.FACES.length; ++i) {
            if (CraftFence.FACES[i] != null && (Boolean) this.get(CraftFence.FACES[i])) {
                faces.add(BlockFace.values()[i]);
            }
        }

        return faces.build();
    }

    public Set getAllowedFaces() {
        Builder faces = ImmutableSet.builder();

        for (int i = 0; i < CraftFence.FACES.length; ++i) {
            if (CraftFence.FACES[i] != null) {
                faces.add(BlockFace.values()[i]);
            }
        }

        return faces.build();
    }

    public boolean isWaterlogged() {
        return (Boolean) this.get(CraftFence.WATERLOGGED);
    }

    public void setWaterlogged(boolean waterlogged) {
        this.set((Property) CraftFence.WATERLOGGED, (Comparable) waterlogged);
    }
}
