package org.bukkit.craftbukkit.v1_20_R2.block.impl;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSet.Builder;
import java.util.Set;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.MultipleFacing;
import org.bukkit.block.data.type.Fire;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;

public final class CraftFire extends CraftBlockData implements Fire, Ageable, MultipleFacing {

    private static final IntegerProperty AGE = getInteger(FireBlock.class, "age");
    private static final BooleanProperty[] FACES = new BooleanProperty[]{getBoolean(FireBlock.class, "north", true), getBoolean(FireBlock.class, "east", true), getBoolean(FireBlock.class, "south", true), getBoolean(FireBlock.class, "west", true), getBoolean(FireBlock.class, "up", true), getBoolean(FireBlock.class, "down", true)};

    public CraftFire() {}

    public CraftFire(BlockState state) {
        super(state);
    }

    public int getAge() {
        return (Integer) this.get(CraftFire.AGE);
    }

    public void setAge(int age) {
        this.set((Property) CraftFire.AGE, (Comparable) age);
    }

    public int getMaximumAge() {
        return getMax(CraftFire.AGE);
    }

    public boolean hasFace(BlockFace face) {
        BooleanProperty state = CraftFire.FACES[face.ordinal()];

        if (state == null) {
            throw new IllegalArgumentException("Non-allowed face " + face + ". Check MultipleFacing.getAllowedFaces.");
        } else {
            return (Boolean) this.get(state);
        }
    }

    public void setFace(BlockFace face, boolean has) {
        BooleanProperty state = CraftFire.FACES[face.ordinal()];

        if (state == null) {
            throw new IllegalArgumentException("Non-allowed face " + face + ". Check MultipleFacing.getAllowedFaces.");
        } else {
            this.set((Property) state, (Comparable) has);
        }
    }

    public Set getFaces() {
        Builder faces = ImmutableSet.builder();

        for (int i = 0; i < CraftFire.FACES.length; ++i) {
            if (CraftFire.FACES[i] != null && (Boolean) this.get(CraftFire.FACES[i])) {
                faces.add(BlockFace.values()[i]);
            }
        }

        return faces.build();
    }

    public Set getAllowedFaces() {
        Builder faces = ImmutableSet.builder();

        for (int i = 0; i < CraftFire.FACES.length; ++i) {
            if (CraftFire.FACES[i] != null) {
                faces.add(BlockFace.values()[i]);
            }
        }

        return faces.build();
    }
}
