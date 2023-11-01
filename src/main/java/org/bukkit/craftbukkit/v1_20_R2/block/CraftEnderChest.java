package org.bukkit.craftbukkit.v1_20_R2.block;

import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.EnderChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.bukkit.World;
import org.bukkit.block.EnderChest;

public class CraftEnderChest extends CraftBlockEntityState implements EnderChest {

    public CraftEnderChest(World world, EnderChestBlockEntity tileEntity) {
        super(world, tileEntity);
    }

    protected CraftEnderChest(CraftEnderChest state) {
        super((CraftBlockEntityState) state);
    }

    public void open() {
        this.requirePlaced();
        if (!((EnderChestBlockEntity) this.getTileEntity()).openersCounter.opened && this.getWorldHandle() instanceof Level) {
            BlockState block = ((EnderChestBlockEntity) this.getTileEntity()).getBlockState();
            int openCount = ((EnderChestBlockEntity) this.getTileEntity()).openersCounter.getOpenerCount();

            ((EnderChestBlockEntity) this.getTileEntity()).openersCounter.onAPIOpen((Level) this.getWorldHandle(), this.getPosition(), block);
            ((EnderChestBlockEntity) this.getTileEntity()).openersCounter.openerAPICountChanged((Level) this.getWorldHandle(), this.getPosition(), block, openCount, openCount + 1);
        }

        ((EnderChestBlockEntity) this.getTileEntity()).openersCounter.opened = true;
    }

    public void close() {
        this.requirePlaced();
        if (((EnderChestBlockEntity) this.getTileEntity()).openersCounter.opened && this.getWorldHandle() instanceof Level) {
            BlockState block = ((EnderChestBlockEntity) this.getTileEntity()).getBlockState();
            int openCount = ((EnderChestBlockEntity) this.getTileEntity()).openersCounter.getOpenerCount();

            ((EnderChestBlockEntity) this.getTileEntity()).openersCounter.onAPIClose((Level) this.getWorldHandle(), this.getPosition(), block);
            ((EnderChestBlockEntity) this.getTileEntity()).openersCounter.openerAPICountChanged((Level) this.getWorldHandle(), this.getPosition(), block, openCount, 0);
        }

        ((EnderChestBlockEntity) this.getTileEntity()).openersCounter.opened = false;
    }

    public CraftEnderChest copy() {
        return new CraftEnderChest(this);
    }
}
