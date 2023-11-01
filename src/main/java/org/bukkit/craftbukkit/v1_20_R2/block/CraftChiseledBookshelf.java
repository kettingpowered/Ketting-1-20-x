package org.bukkit.craftbukkit.v1_20_R2.block;

import net.minecraft.world.level.block.ChiseledBookShelfBlock;
import net.minecraft.world.level.block.entity.ChiseledBookShelfBlockEntity;
import net.minecraft.world.phys.Vec2;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.block.ChiseledBookshelf;
import org.bukkit.block.data.Directional;
import org.bukkit.craftbukkit.v1_20_R2.inventory.CraftInventoryChiseledBookshelf;
import org.bukkit.inventory.ChiseledBookshelfInventory;
import org.bukkit.util.Vector;

public class CraftChiseledBookshelf extends CraftBlockEntityState implements ChiseledBookshelf {

    private static volatile int[] $SWITCH_TABLE$org$bukkit$block$BlockFace;

    public CraftChiseledBookshelf(World world, ChiseledBookShelfBlockEntity tileEntity) {
        super(world, tileEntity);
    }

    protected CraftChiseledBookshelf(CraftChiseledBookshelf state) {
        super((CraftBlockEntityState) state);
    }

    public int getLastInteractedSlot() {
        return ((ChiseledBookShelfBlockEntity) this.getSnapshot()).getLastInteractedSlot();
    }

    public void setLastInteractedSlot(int lastInteractedSlot) {
        ((ChiseledBookShelfBlockEntity) this.getSnapshot()).lastInteractedSlot = lastInteractedSlot;
    }

    public ChiseledBookshelfInventory getSnapshotInventory() {
        return new CraftInventoryChiseledBookshelf((ChiseledBookShelfBlockEntity) this.getSnapshot());
    }

    public ChiseledBookshelfInventory getInventory() {
        return (ChiseledBookshelfInventory) (!this.isPlaced() ? this.getSnapshotInventory() : new CraftInventoryChiseledBookshelf((ChiseledBookShelfBlockEntity) this.getTileEntity()));
    }

    public int getSlot(Vector clickVector) {
        BlockFace facing = ((Directional) this.getBlockData()).getFacing();
        Vec2 faceVector;

        switch ($SWITCH_TABLE$org$bukkit$block$BlockFace()[facing.ordinal()]) {
            case 1:
                faceVector = new Vec2((float) (1.0D - clickVector.getX()), (float) clickVector.getY());
                break;
            case 2:
                faceVector = new Vec2((float) (1.0D - clickVector.getZ()), (float) clickVector.getY());
                break;
            case 3:
                faceVector = new Vec2((float) clickVector.getX(), (float) clickVector.getY());
                break;
            case 4:
                faceVector = new Vec2((float) clickVector.getZ(), (float) clickVector.getY());
                break;
            case 5:
            case 6:
            default:
                return -1;
        }

        return ChiseledBookShelfBlock.getHitSlot(faceVector);
    }

    public CraftChiseledBookshelf copy() {
        return new CraftChiseledBookshelf(this);
    }

    static int[] $SWITCH_TABLE$org$bukkit$block$BlockFace() {
        int[] aint = CraftChiseledBookshelf.$SWITCH_TABLE$org$bukkit$block$BlockFace;

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

            CraftChiseledBookshelf.$SWITCH_TABLE$org$bukkit$block$BlockFace = aint1;
            return aint1;
        }
    }
}
