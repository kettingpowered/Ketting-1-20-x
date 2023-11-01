package org.bukkit.craftbukkit.v1_20_R2.block.impl;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSet.Builder;
import java.util.Set;
import net.minecraft.world.level.block.IronBarsBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.MultipleFacing;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.block.data.type.Fence;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;

public final class CraftIronBars extends CraftBlockData implements Fence, MultipleFacing, Waterlogged {

    private static final BooleanProperty[] FACES = new BooleanProperty[]{getBoolean(IronBarsBlock.class, "north", true), getBoolean(IronBarsBlock.class, "east", true), getBoolean(IronBarsBlock.class, "south", true), getBoolean(IronBarsBlock.class, "west", true), getBoolean(IronBarsBlock.class, "up", true), getBoolean(IronBarsBlock.class, "down", true)};
    private static final BooleanProperty WATERLOGGED = getBoolean(IronBarsBlock.class, "waterlogged");

    public CraftIronBars() {}

    public CraftIronBars(BlockState state) {
        super(state);
    }

    public boolean hasFace(BlockFace face) {
        BooleanProperty state = CraftIronBars.FACES[face.ordinal()];

        if (state == null) {
            throw new IllegalArgumentException("Non-allowed face " + face + ". Check MultipleFacing.getAllowedFaces.");
        } else {
            return (Boolean) this.get(state);
        }
    }

    public void setFace(BlockFace face, boolean has) {
        BooleanProperty state = CraftIronBars.FACES[face.ordinal()];

        if (state == null) {
            throw new IllegalArgumentException("Non-allowed face " + face + ". Check MultipleFacing.getAllowedFaces.");
        } else {
            this.set((Property) state, (Comparable) has);
        }
    }

    public Set getFaces() {
        Builder faces = ImmutableSet.builder();

        for (int i = 0; i < CraftIronBars.FACES.length; ++i) {
            if (CraftIronBars.FACES[i] != null && (Boolean) this.get(CraftIronBars.FACES[i])) {
                faces.add(BlockFace.values()[i]);
            }
        }

        return faces.build();
    }

    public Set getAllowedFaces() {
        Builder faces = ImmutableSet.builder();

        for (int i = 0; i < CraftIronBars.FACES.length; ++i) {
            if (CraftIronBars.FACES[i] != null) {
                faces.add(BlockFace.values()[i]);
            }
        }

        return faces.build();
    }

    public boolean isWaterlogged() {
        return (Boolean) this.get(CraftIronBars.WATERLOGGED);
    }

    public void setWaterlogged(boolean waterlogged) {
        this.set((Property) CraftIronBars.WATERLOGGED, (Comparable) waterlogged);
    }
}
