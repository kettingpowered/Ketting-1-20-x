package org.bukkit.craftbukkit.v1_20_R2.block.impl;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSet.Builder;
import java.util.Set;
import net.minecraft.world.level.block.VineBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.MultipleFacing;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;

public final class CraftVine extends CraftBlockData implements MultipleFacing {

    private static final BooleanProperty[] FACES = new BooleanProperty[]{getBoolean(VineBlock.class, "north", true), getBoolean(VineBlock.class, "east", true), getBoolean(VineBlock.class, "south", true), getBoolean(VineBlock.class, "west", true), getBoolean(VineBlock.class, "up", true), getBoolean(VineBlock.class, "down", true)};

    public CraftVine() {}

    public CraftVine(BlockState state) {
        super(state);
    }

    public boolean hasFace(BlockFace face) {
        BooleanProperty state = CraftVine.FACES[face.ordinal()];

        if (state == null) {
            throw new IllegalArgumentException("Non-allowed face " + face + ". Check MultipleFacing.getAllowedFaces.");
        } else {
            return (Boolean) this.get(state);
        }
    }

    public void setFace(BlockFace face, boolean has) {
        BooleanProperty state = CraftVine.FACES[face.ordinal()];

        if (state == null) {
            throw new IllegalArgumentException("Non-allowed face " + face + ". Check MultipleFacing.getAllowedFaces.");
        } else {
            this.set((Property) state, (Comparable) has);
        }
    }

    public Set getFaces() {
        Builder faces = ImmutableSet.builder();

        for (int i = 0; i < CraftVine.FACES.length; ++i) {
            if (CraftVine.FACES[i] != null && (Boolean) this.get(CraftVine.FACES[i])) {
                faces.add(BlockFace.values()[i]);
            }
        }

        return faces.build();
    }

    public Set getAllowedFaces() {
        Builder faces = ImmutableSet.builder();

        for (int i = 0; i < CraftVine.FACES.length; ++i) {
            if (CraftVine.FACES[i] != null) {
                faces.add(BlockFace.values()[i]);
            }
        }

        return faces.build();
    }
}
