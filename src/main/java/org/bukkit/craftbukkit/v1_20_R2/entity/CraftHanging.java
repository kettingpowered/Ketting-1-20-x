package org.bukkit.craftbukkit.v1_20_R2.entity;

import net.minecraft.core.Direction;
import net.minecraft.world.entity.decoration.HangingEntity;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.craftbukkit.v1_20_R2.block.CraftBlock;
import org.bukkit.entity.Hanging;

public class CraftHanging extends CraftEntity implements Hanging {

    private static volatile int[] $SWITCH_TABLE$org$bukkit$block$BlockFace;

    public CraftHanging(CraftServer server, HangingEntity entity) {
        super(server, entity);
    }

    public BlockFace getAttachedFace() {
        return this.getFacing().getOppositeFace();
    }

    public void setFacingDirection(BlockFace face) {
        this.setFacingDirection(face, false);
    }

    public boolean setFacingDirection(BlockFace face, boolean force) {
        HangingEntity hanging = this.getHandle();
        Direction dir = hanging.getDirection();

        switch ($SWITCH_TABLE$org$bukkit$block$BlockFace()[face.ordinal()]) {
            case 1:
                this.getHandle().setDirection(Direction.NORTH);
                break;
            case 2:
                this.getHandle().setDirection(Direction.EAST);
                break;
            case 3:
                this.getHandle().setDirection(Direction.SOUTH);
                break;
            case 4:
                this.getHandle().setDirection(Direction.WEST);
                break;
            default:
                throw new IllegalArgumentException(String.format("%s is not a valid facing direction", face));
        }

        if (!force && !this.getHandle().generation && !hanging.survives()) {
            hanging.setDirection(dir);
            return false;
        } else {
            return true;
        }
    }

    public BlockFace getFacing() {
        Direction direction = this.getHandle().getDirection();

        return direction == null ? BlockFace.SELF : CraftBlock.notchToBlockFace(direction);
    }

    public HangingEntity getHandle() {
        return (HangingEntity) this.entity;
    }

    public String toString() {
        return "CraftHanging";
    }

    static int[] $SWITCH_TABLE$org$bukkit$block$BlockFace() {
        int[] aint = CraftHanging.$SWITCH_TABLE$org$bukkit$block$BlockFace;

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

            CraftHanging.$SWITCH_TABLE$org$bukkit$block$BlockFace = aint1;
            return aint1;
        }
    }
}
