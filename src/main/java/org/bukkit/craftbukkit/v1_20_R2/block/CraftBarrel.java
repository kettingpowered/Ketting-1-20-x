package org.bukkit.craftbukkit.v1_20_R2.block;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.Container;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BarrelBlock;
import net.minecraft.world.level.block.entity.BarrelBlockEntity;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.bukkit.World;
import org.bukkit.block.Barrel;
import org.bukkit.craftbukkit.v1_20_R2.inventory.CraftInventory;
import org.bukkit.inventory.Inventory;

public class CraftBarrel extends CraftLootable implements Barrel {

    public CraftBarrel(World world, BarrelBlockEntity tileEntity) {
        super(world, (RandomizableContainerBlockEntity) tileEntity);
    }

    protected CraftBarrel(CraftBarrel state) {
        super((CraftLootable) state);
    }

    public Inventory getSnapshotInventory() {
        return new CraftInventory((Container) this.getSnapshot());
    }

    public Inventory getInventory() {
        return (Inventory) (!this.isPlaced() ? this.getSnapshotInventory() : new CraftInventory((Container) this.getTileEntity()));
    }

    public void open() {
        this.requirePlaced();
        if (!((BarrelBlockEntity) this.getTileEntity()).openersCounter.opened) {
            BlockState blockData = ((BarrelBlockEntity) this.getTileEntity()).getBlockState();
            boolean open = (Boolean) blockData.getValue(BarrelBlock.OPEN);

            if (!open) {
                ((BarrelBlockEntity) this.getTileEntity()).updateBlockState(blockData, true);
                if (this.getWorldHandle() instanceof Level) {
                    ((BarrelBlockEntity) this.getTileEntity()).playSound(blockData, SoundEvents.BARREL_OPEN);
                }
            }
        }

        ((BarrelBlockEntity) this.getTileEntity()).openersCounter.opened = true;
    }

    public void close() {
        this.requirePlaced();
        if (((BarrelBlockEntity) this.getTileEntity()).openersCounter.opened) {
            BlockState blockData = ((BarrelBlockEntity) this.getTileEntity()).getBlockState();

            ((BarrelBlockEntity) this.getTileEntity()).updateBlockState(blockData, false);
            if (this.getWorldHandle() instanceof Level) {
                ((BarrelBlockEntity) this.getTileEntity()).playSound(blockData, SoundEvents.BARREL_CLOSE);
            }
        }

        ((BarrelBlockEntity) this.getTileEntity()).openersCounter.opened = false;
    }

    public CraftBarrel copy() {
        return new CraftBarrel(this);
    }
}
