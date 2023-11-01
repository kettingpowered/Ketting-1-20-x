package org.bukkit.craftbukkit.v1_20_R2.block.impl;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSet.Builder;
import java.util.Set;
import net.minecraft.world.level.block.StainedGlassPaneBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.MultipleFacing;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.block.data.type.GlassPane;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;

public final class CraftStainedGlassPane extends CraftBlockData implements GlassPane, MultipleFacing, Waterlogged {

    private static final BooleanProperty[] FACES = new BooleanProperty[]{getBoolean(StainedGlassPaneBlock.class, "north", true), getBoolean(StainedGlassPaneBlock.class, "east", true), getBoolean(StainedGlassPaneBlock.class, "south", true), getBoolean(StainedGlassPaneBlock.class, "west", true), getBoolean(StainedGlassPaneBlock.class, "up", true), getBoolean(StainedGlassPaneBlock.class, "down", true)};
    private static final BooleanProperty WATERLOGGED = getBoolean(StainedGlassPaneBlock.class, "waterlogged");

    public CraftStainedGlassPane() {}

    public CraftStainedGlassPane(BlockState state) {
        super(state);
    }

    public boolean hasFace(BlockFace face) {
        BooleanProperty state = CraftStainedGlassPane.FACES[face.ordinal()];

        if (state == null) {
            throw new IllegalArgumentException("Non-allowed face " + face + ". Check MultipleFacing.getAllowedFaces.");
        } else {
            return (Boolean) this.get(state);
        }
    }

    public void setFace(BlockFace face, boolean has) {
        BooleanProperty state = CraftStainedGlassPane.FACES[face.ordinal()];

        if (state == null) {
            throw new IllegalArgumentException("Non-allowed face " + face + ". Check MultipleFacing.getAllowedFaces.");
        } else {
            this.set((Property) state, (Comparable) has);
        }
    }

    public Set getFaces() {
        Builder faces = ImmutableSet.builder();

        for (int i = 0; i < CraftStainedGlassPane.FACES.length; ++i) {
            if (CraftStainedGlassPane.FACES[i] != null && (Boolean) this.get(CraftStainedGlassPane.FACES[i])) {
                faces.add(BlockFace.values()[i]);
            }
        }

        return faces.build();
    }

    public Set getAllowedFaces() {
        Builder faces = ImmutableSet.builder();

        for (int i = 0; i < CraftStainedGlassPane.FACES.length; ++i) {
            if (CraftStainedGlassPane.FACES[i] != null) {
                faces.add(BlockFace.values()[i]);
            }
        }

        return faces.build();
    }

    public boolean isWaterlogged() {
        return (Boolean) this.get(CraftStainedGlassPane.WATERLOGGED);
    }

    public void setWaterlogged(boolean waterlogged) {
        this.set((Property) CraftStainedGlassPane.WATERLOGGED, (Comparable) waterlogged);
    }
}
