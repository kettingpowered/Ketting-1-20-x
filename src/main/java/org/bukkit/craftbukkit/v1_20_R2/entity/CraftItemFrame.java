package org.bukkit.craftbukkit.v1_20_R2.entity;

import com.google.common.base.Preconditions;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.decoration.HangingEntity;
import net.minecraft.world.level.block.Blocks;
import org.bukkit.Rotation;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.craftbukkit.v1_20_R2.block.CraftBlock;
import org.bukkit.craftbukkit.v1_20_R2.inventory.CraftItemStack;
import org.bukkit.entity.ItemFrame;
import org.bukkit.inventory.ItemStack;

public class CraftItemFrame extends CraftHanging implements ItemFrame {

    private static volatile int[] $SWITCH_TABLE$org$bukkit$Rotation;

    public CraftItemFrame(CraftServer server, net.minecraft.world.entity.decoration.ItemFrame entity) {
        super(server, (HangingEntity) entity);
    }

    public boolean setFacingDirection(BlockFace face, boolean force) {
        net.minecraft.world.entity.decoration.ItemFrame hanging = this.getHandle();
        Direction oldDir = hanging.getDirection();
        Direction newDir = CraftBlock.blockFaceToNotch(face);

        Preconditions.checkArgument(newDir != null, "%s is not a valid facing direction", face);
        this.getHandle().setDirection(newDir);
        if (!force && !this.getHandle().generation && !hanging.survives()) {
            hanging.setDirection(oldDir);
            return false;
        } else {
            this.update();
            return true;
        }
    }

    protected void update() {
        super.update();
        this.getHandle().getEntityData().markDirty(net.minecraft.world.entity.decoration.ItemFrame.DATA_ITEM);
        this.getHandle().getEntityData().markDirty(net.minecraft.world.entity.decoration.ItemFrame.DATA_ROTATION);
        if (!this.getHandle().generation) {
            this.getHandle().level().updateNeighbourForOutputSignal(this.getHandle().pos, Blocks.AIR);
        }

    }

    public void setItem(ItemStack item) {
        this.setItem(item, true);
    }

    public void setItem(ItemStack item, boolean playSound) {
        this.getHandle().setItem(CraftItemStack.asNMSCopy(item), !this.getHandle().generation, !this.getHandle().generation && playSound);
    }

    public ItemStack getItem() {
        return CraftItemStack.asBukkitCopy(this.getHandle().getItem());
    }

    public float getItemDropChance() {
        return this.getHandle().dropChance;
    }

    public void setItemDropChance(float chance) {
        Preconditions.checkArgument(0.0D <= (double) chance && (double) chance <= 1.0D, "Chance (%s) outside range [0, 1]", chance);
        this.getHandle().dropChance = chance;
    }

    public Rotation getRotation() {
        return this.toBukkitRotation(this.getHandle().getRotation());
    }

    Rotation toBukkitRotation(int value) {
        switch (value) {
            case 0:
                return Rotation.NONE;
            case 1:
                return Rotation.CLOCKWISE_45;
            case 2:
                return Rotation.CLOCKWISE;
            case 3:
                return Rotation.CLOCKWISE_135;
            case 4:
                return Rotation.FLIPPED;
            case 5:
                return Rotation.FLIPPED_45;
            case 6:
                return Rotation.COUNTER_CLOCKWISE;
            case 7:
                return Rotation.COUNTER_CLOCKWISE_45;
            default:
                throw new AssertionError("Unknown rotation " + value + " for " + this.getHandle());
        }
    }

    public void setRotation(Rotation rotation) {
        Preconditions.checkArgument(rotation != null, "Rotation cannot be null");
        this.getHandle().setRotation(toInteger(rotation));
    }

    static int toInteger(Rotation rotation) {
        switch ($SWITCH_TABLE$org$bukkit$Rotation()[rotation.ordinal()]) {
            case 1:
                return 0;
            case 2:
                return 1;
            case 3:
                return 2;
            case 4:
                return 3;
            case 5:
                return 4;
            case 6:
                return 5;
            case 7:
                return 6;
            case 8:
                return 7;
            default:
                throw new IllegalArgumentException(rotation + " is not applicable to an ItemFrame");
        }
    }

    public boolean isVisible() {
        return !this.getHandle().isInvisible();
    }

    public void setVisible(boolean visible) {
        this.getHandle().setInvisible(!visible);
    }

    public boolean isFixed() {
        return this.getHandle().fixed;
    }

    public void setFixed(boolean fixed) {
        this.getHandle().fixed = fixed;
    }

    public net.minecraft.world.entity.decoration.ItemFrame getHandle() {
        return (net.minecraft.world.entity.decoration.ItemFrame) this.entity;
    }

    public String toString() {
        return "CraftItemFrame{item=" + this.getItem() + ", rotation=" + this.getRotation() + "}";
    }

    static int[] $SWITCH_TABLE$org$bukkit$Rotation() {
        int[] aint = CraftItemFrame.$SWITCH_TABLE$org$bukkit$Rotation;

        if (aint != null) {
            return aint;
        } else {
            int[] aint1 = new int[Rotation.values().length];

            try {
                aint1[Rotation.CLOCKWISE.ordinal()] = 3;
            } catch (NoSuchFieldError nosuchfielderror) {
                ;
            }

            try {
                aint1[Rotation.CLOCKWISE_135.ordinal()] = 4;
            } catch (NoSuchFieldError nosuchfielderror1) {
                ;
            }

            try {
                aint1[Rotation.CLOCKWISE_45.ordinal()] = 2;
            } catch (NoSuchFieldError nosuchfielderror2) {
                ;
            }

            try {
                aint1[Rotation.COUNTER_CLOCKWISE.ordinal()] = 7;
            } catch (NoSuchFieldError nosuchfielderror3) {
                ;
            }

            try {
                aint1[Rotation.COUNTER_CLOCKWISE_45.ordinal()] = 8;
            } catch (NoSuchFieldError nosuchfielderror4) {
                ;
            }

            try {
                aint1[Rotation.FLIPPED.ordinal()] = 5;
            } catch (NoSuchFieldError nosuchfielderror5) {
                ;
            }

            try {
                aint1[Rotation.FLIPPED_45.ordinal()] = 6;
            } catch (NoSuchFieldError nosuchfielderror6) {
                ;
            }

            try {
                aint1[Rotation.NONE.ordinal()] = 1;
            } catch (NoSuchFieldError nosuchfielderror7) {
                ;
            }

            CraftItemFrame.$SWITCH_TABLE$org$bukkit$Rotation = aint1;
            return aint1;
        }
    }
}
