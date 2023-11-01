package org.bukkit.craftbukkit.v1_20_R2.block.data;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSet.Builder;
import java.util.Set;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.MultipleFacing;

public abstract class CraftMultipleFacing extends CraftBlockData implements MultipleFacing {

    private static final BooleanProperty[] FACES = new BooleanProperty[]{getBoolean("north", true), getBoolean("east", true), getBoolean("south", true), getBoolean("west", true), getBoolean("up", true), getBoolean("down", true)};

    public boolean hasFace(BlockFace face) {
        BooleanProperty state = CraftMultipleFacing.FACES[face.ordinal()];

        if (state == null) {
            throw new IllegalArgumentException("Non-allowed face " + face + ". Check MultipleFacing.getAllowedFaces.");
        } else {
            return (Boolean) this.get(state);
        }
    }

    public void setFace(BlockFace face, boolean has) {
        BooleanProperty state = CraftMultipleFacing.FACES[face.ordinal()];

        if (state == null) {
            throw new IllegalArgumentException("Non-allowed face " + face + ". Check MultipleFacing.getAllowedFaces.");
        } else {
            this.set((Property) state, (Comparable) has);
        }
    }

    public Set getFaces() {
        Builder faces = ImmutableSet.builder();

        for (int i = 0; i < CraftMultipleFacing.FACES.length; ++i) {
            if (CraftMultipleFacing.FACES[i] != null && (Boolean) this.get(CraftMultipleFacing.FACES[i])) {
                faces.add(BlockFace.values()[i]);
            }
        }

        return faces.build();
    }

    public Set getAllowedFaces() {
        Builder faces = ImmutableSet.builder();

        for (int i = 0; i < CraftMultipleFacing.FACES.length; ++i) {
            if (CraftMultipleFacing.FACES[i] != null) {
                faces.add(BlockFace.values()[i]);
            }
        }

        return faces.build();
    }
}
