package org.bukkit.craftbukkit.v1_20_R2.block;

import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.JukeboxBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.JukeboxBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Jukebox;
import org.bukkit.craftbukkit.v1_20_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_20_R2.inventory.CraftInventoryJukebox;
import org.bukkit.craftbukkit.v1_20_R2.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_20_R2.util.CraftMagicNumbers;
import org.bukkit.inventory.JukeboxInventory;

public class CraftJukebox extends CraftBlockEntityState implements Jukebox {

    public CraftJukebox(World world, JukeboxBlockEntity tileEntity) {
        super(world, tileEntity);
    }

    protected CraftJukebox(CraftJukebox state) {
        super((CraftBlockEntityState) state);
    }

    public JukeboxInventory getSnapshotInventory() {
        return new CraftInventoryJukebox((Container) this.getSnapshot());
    }

    public JukeboxInventory getInventory() {
        return (JukeboxInventory) (!this.isPlaced() ? this.getSnapshotInventory() : new CraftInventoryJukebox((Container) this.getTileEntity()));
    }

    public boolean update(boolean force, boolean applyPhysics) {
        boolean result = super.update(force, applyPhysics);

        if (result && this.isPlaced() && this.getType() == Material.JUKEBOX) {
            Material record = this.getPlaying();

            this.getWorldHandle().setBlock(this.getPosition(), this.data, 3);
            BlockEntity tileEntity = this.getTileEntityFromWorld();
            JukeboxBlockEntity jukebox;

            if (tileEntity instanceof JukeboxBlockEntity && (jukebox = (JukeboxBlockEntity) tileEntity) == (JukeboxBlockEntity) tileEntity) {
                CraftWorld world = (CraftWorld) this.getWorld();

                if (record.isAir()) {
                    jukebox.setRecordWithoutPlaying(ItemStack.EMPTY);
                    world.playEffect(this.getLocation(), Effect.IRON_DOOR_CLOSE, 0);
                } else {
                    world.playEffect(this.getLocation(), Effect.RECORD_PLAY, record);
                }
            }
        }

        return result;
    }

    public Material getPlaying() {
        return this.getRecord().getType();
    }

    public void setPlaying(Material record) {
        if (record == null || CraftMagicNumbers.getItem(record) == null) {
            record = Material.AIR;
        }

        this.setRecord(new org.bukkit.inventory.ItemStack(record));
    }

    public boolean hasRecord() {
        return (Boolean) this.getHandle().getValue(JukeboxBlock.HAS_RECORD) && !this.getPlaying().isAir();
    }

    public org.bukkit.inventory.ItemStack getRecord() {
        ItemStack record = ((JukeboxBlockEntity) this.getSnapshot()).getFirstItem();

        return CraftItemStack.asBukkitCopy(record);
    }

    public void setRecord(org.bukkit.inventory.ItemStack record) {
        ItemStack nms = CraftItemStack.asNMSCopy(record);
        JukeboxBlockEntity snapshot = (JukeboxBlockEntity) this.getSnapshot();

        snapshot.setRecordWithoutPlaying(nms);
        snapshot.recordStartedTick = snapshot.tickCount;
        snapshot.isPlaying = !nms.isEmpty();
        this.data = (BlockState) this.data.setValue(JukeboxBlock.HAS_RECORD, !nms.isEmpty());
    }

    public boolean isPlaying() {
        this.requirePlaced();
        BlockEntity tileEntity = this.getTileEntityFromWorld();
        JukeboxBlockEntity jukebox;

        return tileEntity instanceof JukeboxBlockEntity && (jukebox = (JukeboxBlockEntity) tileEntity) == (JukeboxBlockEntity) tileEntity && jukebox.isRecordPlaying();
    }

    public boolean startPlaying() {
        this.requirePlaced();
        BlockEntity tileEntity = this.getTileEntityFromWorld();
        JukeboxBlockEntity jukebox;

        if (tileEntity instanceof JukeboxBlockEntity && (jukebox = (JukeboxBlockEntity) tileEntity) == (JukeboxBlockEntity) tileEntity) {
            ItemStack record = jukebox.getFirstItem();

            if (!record.isEmpty() && !this.isPlaying()) {
                jukebox.isPlaying = true;
                jukebox.recordStartedTick = jukebox.tickCount;
                this.getWorld().playEffect(this.getLocation(), Effect.RECORD_PLAY, CraftMagicNumbers.getMaterial(record.getItem()));
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public void stopPlaying() {
        this.requirePlaced();
        BlockEntity tileEntity = this.getTileEntityFromWorld();
        JukeboxBlockEntity jukebox;

        if (tileEntity instanceof JukeboxBlockEntity && (jukebox = (JukeboxBlockEntity) tileEntity) == (JukeboxBlockEntity) tileEntity) {
            jukebox.isPlaying = false;
            this.getWorld().playEffect(this.getLocation(), Effect.IRON_DOOR_CLOSE, 0);
        }
    }

    public boolean eject() {
        this.ensureNoWorldGeneration();
        BlockEntity tileEntity = this.getTileEntityFromWorld();

        if (!(tileEntity instanceof JukeboxBlockEntity)) {
            return false;
        } else {
            JukeboxBlockEntity jukebox = (JukeboxBlockEntity) tileEntity;
            boolean result = !jukebox.getFirstItem().isEmpty();

            jukebox.popOutRecord();
            return result;
        }
    }

    public CraftJukebox copy() {
        return new CraftJukebox(this);
    }
}
