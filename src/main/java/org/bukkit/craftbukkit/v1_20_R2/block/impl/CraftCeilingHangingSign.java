package org.bukkit.craftbukkit.v1_20_R2.block.impl;

import net.minecraft.world.level.block.CeilingHangingSignBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Attachable;
import org.bukkit.block.data.Rotatable;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.block.data.type.HangingSign;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;

public final class CraftCeilingHangingSign extends CraftBlockData implements HangingSign, Attachable, Rotatable, Waterlogged {

    private static final BooleanProperty ATTACHED = getBoolean(CeilingHangingSignBlock.class, "attached");
    private static final IntegerProperty ROTATION = getInteger(CeilingHangingSignBlock.class, "rotation");
    private static final BooleanProperty WATERLOGGED = getBoolean(CeilingHangingSignBlock.class, "waterlogged");
    private static volatile int[] $SWITCH_TABLE$org$bukkit$block$BlockFace;

    public CraftCeilingHangingSign() {}

    public CraftCeilingHangingSign(BlockState state) {
        super(state);
    }

    public boolean isAttached() {
        return (Boolean) this.get(CraftCeilingHangingSign.ATTACHED);
    }

    public void setAttached(boolean attached) {
        this.set((Property) CraftCeilingHangingSign.ATTACHED, (Comparable) attached);
    }

    public BlockFace getRotation() {
        int data = (Integer) this.get(CraftCeilingHangingSign.ROTATION);

        switch (data) {
            case 0:
                return BlockFace.SOUTH;
            case 1:
                return BlockFace.SOUTH_SOUTH_WEST;
            case 2:
                return BlockFace.SOUTH_WEST;
            case 3:
                return BlockFace.WEST_SOUTH_WEST;
            case 4:
                return BlockFace.WEST;
            case 5:
                return BlockFace.WEST_NORTH_WEST;
            case 6:
                return BlockFace.NORTH_WEST;
            case 7:
                return BlockFace.NORTH_NORTH_WEST;
            case 8:
                return BlockFace.NORTH;
            case 9:
                return BlockFace.NORTH_NORTH_EAST;
            case 10:
                return BlockFace.NORTH_EAST;
            case 11:
                return BlockFace.EAST_NORTH_EAST;
            case 12:
                return BlockFace.EAST;
            case 13:
                return BlockFace.EAST_SOUTH_EAST;
            case 14:
                return BlockFace.SOUTH_EAST;
            case 15:
                return BlockFace.SOUTH_SOUTH_EAST;
            default:
                throw new IllegalArgumentException("Unknown rotation " + data);
        }
    }

    public void setRotation(BlockFace rotation) {
        byte val;

        switch ($SWITCH_TABLE$org$bukkit$block$BlockFace()[rotation.ordinal()]) {
            case 1:
                val = 8;
                break;
            case 2:
                val = 12;
                break;
            case 3:
                val = 0;
                break;
            case 4:
                val = 4;
                break;
            case 5:
            case 6:
            default:
                throw new IllegalArgumentException("Illegal rotation " + rotation);
            case 7:
                val = 10;
                break;
            case 8:
                val = 6;
                break;
            case 9:
                val = 14;
                break;
            case 10:
                val = 2;
                break;
            case 11:
                val = 5;
                break;
            case 12:
                val = 7;
                break;
            case 13:
                val = 9;
                break;
            case 14:
                val = 11;
                break;
            case 15:
                val = 13;
                break;
            case 16:
                val = 15;
                break;
            case 17:
                val = 1;
                break;
            case 18:
                val = 3;
        }

        this.set((Property) CraftCeilingHangingSign.ROTATION, (Comparable) Integer.valueOf(val));
    }

    public boolean isWaterlogged() {
        return (Boolean) this.get(CraftCeilingHangingSign.WATERLOGGED);
    }

    public void setWaterlogged(boolean waterlogged) {
        this.set((Property) CraftCeilingHangingSign.WATERLOGGED, (Comparable) waterlogged);
    }

    static int[] $SWITCH_TABLE$org$bukkit$block$BlockFace() {
        int[] aint = CraftCeilingHangingSign.$SWITCH_TABLE$org$bukkit$block$BlockFace;

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

            CraftCeilingHangingSign.$SWITCH_TABLE$org$bukkit$block$BlockFace = aint1;
            return aint1;
        }
    }
}
