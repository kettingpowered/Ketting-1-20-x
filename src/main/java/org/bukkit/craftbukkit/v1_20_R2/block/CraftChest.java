package org.bukkit.craftbukkit.v1_20_R2.block;

import net.minecraft.world.Container;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Chest;
import org.bukkit.craftbukkit.v1_20_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_20_R2.inventory.CraftInventory;
import org.bukkit.craftbukkit.v1_20_R2.inventory.CraftInventoryDoubleChest;
import org.bukkit.inventory.Inventory;

public class CraftChest extends CraftLootable implements Chest {

    public CraftChest(World world, ChestBlockEntity tileEntity) {
        super(world, (RandomizableContainerBlockEntity) tileEntity);
    }

    protected CraftChest(CraftChest state) {
        super((CraftLootable) state);
    }

    public Inventory getSnapshotInventory() {
        return new CraftInventory((Container) this.getSnapshot());
    }

    public Inventory getBlockInventory() {
        return (Inventory) (!this.isPlaced() ? this.getSnapshotInventory() : new CraftInventory((Container) this.getTileEntity()));
    }

    public Inventory getInventory() {
        Object inventory = (CraftInventory) this.getBlockInventory();

        if (this.isPlaced() && !this.isWorldGeneration()) {
            CraftWorld world = (CraftWorld) this.getWorld();
            ChestBlock blockChest = (ChestBlock) (this.getType() == Material.CHEST ? Blocks.CHEST : Blocks.TRAPPED_CHEST);
            MenuProvider nms = blockChest.getMenuProvider(this.data, world.getHandle(), this.getPosition(), true);

            if (nms instanceof ChestBlock.DoubleInventory) {
                inventory = new CraftInventoryDoubleChest((ChestBlock.DoubleInventory) nms);
            }

            return (Inventory) inventory;
        } else {
            return (Inventory) inventory;
        }
    }

    public void open() {
        this.requirePlaced();
        if (!((ChestBlockEntity) this.getTileEntity()).openersCounter.opened && this.getWorldHandle() instanceof Level) {
            BlockState block = ((ChestBlockEntity) this.getTileEntity()).getBlockState();
            int openCount = ((ChestBlockEntity) this.getTileEntity()).openersCounter.getOpenerCount();

            ((ChestBlockEntity) this.getTileEntity()).openersCounter.onAPIOpen((Level) this.getWorldHandle(), this.getPosition(), block);
            ((ChestBlockEntity) this.getTileEntity()).openersCounter.openerAPICountChanged((Level) this.getWorldHandle(), this.getPosition(), block, openCount, openCount + 1);
        }

        ((ChestBlockEntity) this.getTileEntity()).openersCounter.opened = true;
    }

    public void close() {
        this.requirePlaced();
        if (((ChestBlockEntity) this.getTileEntity()).openersCounter.opened && this.getWorldHandle() instanceof Level) {
            BlockState block = ((ChestBlockEntity) this.getTileEntity()).getBlockState();
            int openCount = ((ChestBlockEntity) this.getTileEntity()).openersCounter.getOpenerCount();

            ((ChestBlockEntity) this.getTileEntity()).openersCounter.onAPIClose((Level) this.getWorldHandle(), this.getPosition(), block);
            ((ChestBlockEntity) this.getTileEntity()).openersCounter.openerAPICountChanged((Level) this.getWorldHandle(), this.getPosition(), block, openCount, 0);
        }

        ((ChestBlockEntity) this.getTileEntity()).openersCounter.opened = false;
    }

    public CraftChest copy() {
        return new CraftChest(this);
    }
}
