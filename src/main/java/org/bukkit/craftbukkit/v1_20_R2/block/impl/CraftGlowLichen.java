package org.bukkit.craftbukkit.v1_20_R2.block.impl;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSet.Builder;
import java.util.Set;
import net.minecraft.world.level.block.GlowLichenBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.MultipleFacing;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.block.data.type.GlowLichen;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;

public final class CraftGlowLichen extends CraftBlockData implements GlowLichen, MultipleFacing, Waterlogged {

    private static final BooleanProperty[] FACES = new BooleanProperty[]{getBoolean(GlowLichenBlock.class, "north", true), getBoolean(GlowLichenBlock.class, "east", true), getBoolean(GlowLichenBlock.class, "south", true), getBoolean(GlowLichenBlock.class, "west", true), getBoolean(GlowLichenBlock.class, "up", true), getBoolean(GlowLichenBlock.class, "down", true)};
    private static final BooleanProperty WATERLOGGED = getBoolean(GlowLichenBlock.class, "waterlogged");

    public CraftGlowLichen() {}

    public CraftGlowLichen(BlockState state) {
        super(state);
    }

    public boolean hasFace(BlockFace face) {
        BooleanProperty state = CraftGlowLichen.FACES[face.ordinal()];

        if (state == null) {
            throw new IllegalArgumentException("Non-allowed face " + face + ". Check MultipleFacing.getAllowedFaces.");
        } else {
            return (Boolean) this.get(state);
        }
    }

    public void setFace(BlockFace face, boolean has) {
        BooleanProperty state = CraftGlowLichen.FACES[face.ordinal()];

        if (state == null) {
            throw new IllegalArgumentException("Non-allowed face " + face + ". Check MultipleFacing.getAllowedFaces.");
        } else {
            this.set((Property) state, (Comparable) has);
        }
    }

    public Set getFaces() {
        Builder faces = ImmutableSet.builder();

        for (int i = 0; i < CraftGlowLichen.FACES.length; ++i) {
            if (CraftGlowLichen.FACES[i] != null && (Boolean) this.get(CraftGlowLichen.FACES[i])) {
                faces.add(BlockFace.values()[i]);
            }
        }

        return faces.build();
    }

    public Set getAllowedFaces() {
        Builder faces = ImmutableSet.builder();

        for (int i = 0; i < CraftGlowLichen.FACES.length; ++i) {
            if (CraftGlowLichen.FACES[i] != null) {
                faces.add(BlockFace.values()[i]);
            }
        }

        return faces.build();
    }

    public boolean isWaterlogged() {
        return (Boolean) this.get(CraftGlowLichen.WATERLOGGED);
    }

    public void setWaterlogged(boolean waterlogged) {
        this.set((Property) CraftGlowLichen.WATERLOGGED, (Comparable) waterlogged);
    }
}
