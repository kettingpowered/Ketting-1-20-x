package org.bukkit.craftbukkit.v1_20_R2.block;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.ShulkerBoxBlock;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.entity.ShulkerBoxBlockEntity;
import org.bukkit.DyeColor;
import org.bukkit.World;
import org.bukkit.block.ShulkerBox;
import org.bukkit.craftbukkit.v1_20_R2.inventory.CraftInventory;
import org.bukkit.craftbukkit.v1_20_R2.util.CraftMagicNumbers;
import org.bukkit.inventory.Inventory;

public class CraftShulkerBox extends CraftLootable implements ShulkerBox {

    public CraftShulkerBox(World world, ShulkerBoxBlockEntity tileEntity) {
        super(world, (RandomizableContainerBlockEntity) tileEntity);
    }

    protected CraftShulkerBox(CraftShulkerBox state) {
        super((CraftLootable) state);
    }

    public Inventory getSnapshotInventory() {
        return new CraftInventory((Container) this.getSnapshot());
    }

    public Inventory getInventory() {
        return (Inventory) (!this.isPlaced() ? this.getSnapshotInventory() : new CraftInventory((Container) this.getTileEntity()));
    }

    public DyeColor getColor() {
        net.minecraft.world.item.DyeColor color = ((ShulkerBoxBlock) CraftMagicNumbers.getBlock(this.getType())).color;

        return color == null ? null : DyeColor.getByWoolData((byte) color.getId());
    }

    public void open() {
        this.requirePlaced();
        if (!((ShulkerBoxBlockEntity) this.getTileEntity()).opened && this.getWorldHandle() instanceof Level) {
            Level world = ((ShulkerBoxBlockEntity) this.getTileEntity()).getLevel();

            world.blockEvent(this.getPosition(), ((ShulkerBoxBlockEntity) this.getTileEntity()).getBlockState().getBlock(), 1, 1);
            world.playSound((Player) null, this.getPosition(), SoundEvents.SHULKER_BOX_OPEN, SoundSource.BLOCKS, 0.5F, world.random.nextFloat() * 0.1F + 0.9F);
        }

        ((ShulkerBoxBlockEntity) this.getTileEntity()).opened = true;
    }

    public void close() {
        this.requirePlaced();
        if (((ShulkerBoxBlockEntity) this.getTileEntity()).opened && this.getWorldHandle() instanceof Level) {
            Level world = ((ShulkerBoxBlockEntity) this.getTileEntity()).getLevel();

            world.blockEvent(this.getPosition(), ((ShulkerBoxBlockEntity) this.getTileEntity()).getBlockState().getBlock(), 1, 0);
            world.playSound((Player) null, this.getPosition(), SoundEvents.SHULKER_BOX_OPEN, SoundSource.BLOCKS, 0.5F, world.random.nextFloat() * 0.1F + 0.9F);
        }

        ((ShulkerBoxBlockEntity) this.getTileEntity()).opened = false;
    }

    public CraftShulkerBox copy() {
        return new CraftShulkerBox(this);
    }
}
