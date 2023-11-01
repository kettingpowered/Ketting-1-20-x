package org.bukkit.craftbukkit.v1_20_R2.block.impl;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSet.Builder;
import java.util.Set;
import net.minecraft.world.level.block.HugeMushroomBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.MultipleFacing;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;

public final class CraftHugeMushroom extends CraftBlockData implements MultipleFacing {

    private static final BooleanProperty[] FACES = new BooleanProperty[]{getBoolean(HugeMushroomBlock.class, "north", true), getBoolean(HugeMushroomBlock.class, "east", true), getBoolean(HugeMushroomBlock.class, "south", true), getBoolean(HugeMushroomBlock.class, "west", true), getBoolean(HugeMushroomBlock.class, "up", true), getBoolean(HugeMushroomBlock.class, "down", true)};

    public CraftHugeMushroom() {}

    public CraftHugeMushroom(BlockState state) {
        super(state);
    }

    public boolean hasFace(BlockFace face) {
        BooleanProperty state = CraftHugeMushroom.FACES[face.ordinal()];

        if (state == null) {
            throw new IllegalArgumentException("Non-allowed face " + face + ". Check MultipleFacing.getAllowedFaces.");
        } else {
            return (Boolean) this.get(state);
        }
    }

    public void setFace(BlockFace face, boolean has) {
        BooleanProperty state = CraftHugeMushroom.FACES[face.ordinal()];

        if (state == null) {
            throw new IllegalArgumentException("Non-allowed face " + face + ". Check MultipleFacing.getAllowedFaces.");
        } else {
            this.set((Property) state, (Comparable) has);
        }
    }

    public Set getFaces() {
        Builder faces = ImmutableSet.builder();

        for (int i = 0; i < CraftHugeMushroom.FACES.length; ++i) {
            if (CraftHugeMushroom.FACES[i] != null && (Boolean) this.get(CraftHugeMushroom.FACES[i])) {
                faces.add(BlockFace.values()[i]);
            }
        }

        return faces.build();
    }

    public Set getAllowedFaces() {
        Builder faces = ImmutableSet.builder();

        for (int i = 0; i < CraftHugeMushroom.FACES.length; ++i) {
            if (CraftHugeMushroom.FACES[i] != null) {
                faces.add(BlockFace.values()[i]);
            }
        }

        return faces.build();
    }
}
