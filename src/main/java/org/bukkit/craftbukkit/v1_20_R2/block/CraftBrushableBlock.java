package org.bukkit.craftbukkit.v1_20_R2.block;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BrushableBlockEntity;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.block.BrushableBlock;
import org.bukkit.craftbukkit.v1_20_R2.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_20_R2.util.CraftNamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.loot.LootTable;

public class CraftBrushableBlock extends CraftBlockEntityState implements BrushableBlock {

    public CraftBrushableBlock(World world, BrushableBlockEntity tileEntity) {
        super(world, tileEntity);
    }

    protected CraftBrushableBlock(CraftBrushableBlock state) {
        super((CraftBlockEntityState) state);
    }

    public ItemStack getItem() {
        return CraftItemStack.asBukkitCopy(((BrushableBlockEntity) this.getSnapshot()).getItem());
    }

    public void setItem(ItemStack item) {
        ((BrushableBlockEntity) this.getSnapshot()).item = CraftItemStack.asNMSCopy(item);
    }

    public void applyTo(BrushableBlockEntity lootable) {
        super.applyTo(lootable);
        if (((BrushableBlockEntity) this.getSnapshot()).lootTable == null) {
            lootable.setLootTable((ResourceLocation) null, 0L);
        }

    }

    public LootTable getLootTable() {
        if (((BrushableBlockEntity) this.getSnapshot()).lootTable == null) {
            return null;
        } else {
            ResourceLocation key = ((BrushableBlockEntity) this.getSnapshot()).lootTable;

            return Bukkit.getLootTable(CraftNamespacedKey.fromMinecraft(key));
        }
    }

    public void setLootTable(LootTable table) {
        this.setLootTable(table, this.getSeed());
    }

    public long getSeed() {
        return ((BrushableBlockEntity) this.getSnapshot()).lootTableSeed;
    }

    public void setSeed(long seed) {
        this.setLootTable(this.getLootTable(), seed);
    }

    private void setLootTable(LootTable table, long seed) {
        ResourceLocation key = table == null ? null : CraftNamespacedKey.toMinecraft(table.getKey());

        ((BrushableBlockEntity) this.getSnapshot()).setLootTable(key, seed);
    }

    public CraftBrushableBlock copy() {
        return new CraftBrushableBlock(this);
    }
}
