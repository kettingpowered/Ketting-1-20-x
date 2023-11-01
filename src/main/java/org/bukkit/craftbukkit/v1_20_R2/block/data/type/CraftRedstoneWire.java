package org.bukkit.craftbukkit.v1_20_R2.block.data.type;

import com.google.common.collect.ImmutableSet;
import java.util.Set;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.RedstoneWire;
import org.bukkit.block.data.type.RedstoneWire.Connection;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;

public abstract class CraftRedstoneWire extends CraftBlockData implements RedstoneWire {

    private static final EnumProperty NORTH = getEnum("north");
    private static final EnumProperty EAST = getEnum("east");
    private static final EnumProperty SOUTH = getEnum("south");
    private static final EnumProperty WEST = getEnum("west");
    private static volatile int[] $SWITCH_TABLE$org$bukkit$block$BlockFace;

    public Connection getFace(BlockFace face) {
        switch ($SWITCH_TABLE$org$bukkit$block$BlockFace()[face.ordinal()]) {
            case 1:
                return (Connection) this.get(CraftRedstoneWire.NORTH, Connection.class);
            case 2:
                return (Connection) this.get(CraftRedstoneWire.EAST, Connection.class);
            case 3:
                return (Connection) this.get(CraftRedstoneWire.SOUTH, Connection.class);
            case 4:
                return (Connection) this.get(CraftRedstoneWire.WEST, Connection.class);
            default:
                throw new IllegalArgumentException("Cannot have face " + face);
        }
    }

    public void setFace(BlockFace face, Connection connection) {
        switch ($SWITCH_TABLE$org$bukkit$block$BlockFace()[face.ordinal()]) {
            case 1:
                this.set(CraftRedstoneWire.NORTH, (Enum) connection);
                break;
            case 2:
                this.set(CraftRedstoneWire.EAST, (Enum) connection);
                break;
            case 3:
                this.set(CraftRedstoneWire.SOUTH, (Enum) connection);
                break;
            case 4:
                this.set(CraftRedstoneWire.WEST, (Enum) connection);
                break;
            default:
                throw new IllegalArgumentException("Cannot have face " + face);
        }

    }

    public Set getAllowedFaces() {
        return ImmutableSet.of(BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST);
    }

    static int[] $SWITCH_TABLE$org$bukkit$block$BlockFace() {
        int[] aint = CraftRedstoneWire.$SWITCH_TABLE$org$bukkit$block$BlockFace;

        if (aint != null) {
            return aint;
        } else {
            int[] aint1 = new int[BlockFace.values().length];

            try {
                aint1[BlockFace.DOWN.ordinal()] = 6;
            } catch (NoSuchFieldError nosuchfielderror) {
                ;
            }

            try {
                aint1[BlockFace.EAST.ordinal()] = 2;
            } catch (NoSuchFieldError nosuchfielderror1) {
                ;
            }

            try {
                aint1[BlockFace.EAST_NORTH_EAST.ordinal()] = 14;
            } catch (NoSuchFieldError nosuchfielderror2) {
                ;
            }

            try {
                aint1[BlockFace.EAST_SOUTH_EAST.ordinal()] = 15;
            } catch (NoSuchFieldError nosuchfielderror3) {
                ;
            }

            try {
                aint1[BlockFace.NORTH.ordinal()] = 1;
            } catch (NoSuchFieldError nosuchfielderror4) {
                ;
            }

            try {
                aint1[BlockFace.NORTH_EAST.ordinal()] = 7;
            } catch (NoSuchFieldError nosuchfielderror5) {
                ;
            }

            try {
                aint1[BlockFace.NORTH_NORTH_EAST.ordinal()] = 13;
            } catch (NoSuchFieldError nosuchfielderror6) {
                ;
            }

            try {
                aint1[BlockFace.NORTH_NORTH_WEST.ordinal()] = 12;
            } catch (NoSuchFieldError nosuchfielderror7) {
                ;
            }

            try {
                aint1[BlockFace.NORTH_WEST.ordinal()] = 8;
            } catch (NoSuchFieldError nosuchfielderror8) {
                ;
            }

            try {
                aint1[BlockFace.SELF.ordinal()] = 19;
            } catch (NoSuchFieldError nosuchfielderror9) {
                ;
            }

            try {
                aint1[BlockFace.SOUTH.ordinal()] = 3;
            } catch (NoSuchFieldError nosuchfielderror10) {
                ;
            }

            try {
                aint1[BlockFace.SOUTH_EAST.ordinal()] = 9;
            } catch (NoSuchFieldError nosuchfielderror11) {
                ;
            }

            try {
                aint1[BlockFace.SOUTH_SOUTH_EAST.ordinal()] = 16;
            } catch (NoSuchFieldError nosuchfielderror12) {
                ;
            }

            try {
                aint1[BlockFace.SOUTH_SOUTH_WEST.ordinal()] = 17;
            } catch (NoSuchFieldError nosuchfielderror13) {
                ;
            }

            try {
                aint1[BlockFace.SOUTH_WEST.ordinal()] = 10;
            } catch (NoSuchFieldError nosuchfielderror14) {
                ;
            }

            try {
                aint1[BlockFace.UP.ordinal()] = 5;
            } catch (NoSuchFieldError nosuchfielderror15) {
                ;
            }

            try {
                aint1[BlockFace.WEST.ordinal()] = 4;
            } catch (NoSuchFieldError nosuchfielderror16) {
                ;
            }

            try {
                aint1[BlockFace.WEST_NORTH_WEST.ordinal()] = 11;
            } catch (NoSuchFieldError nosuchfielderror17) {
                ;
            }

            try {
                aint1[BlockFace.WEST_SOUTH_WEST.ordinal()] = 18;
            } catch (NoSuchFieldError nosuchfielderror18) {
                ;
            }

            CraftRedstoneWire.$SWITCH_TABLE$org$bukkit$block$BlockFace = aint1;
            return aint1;
        }
    }
}
