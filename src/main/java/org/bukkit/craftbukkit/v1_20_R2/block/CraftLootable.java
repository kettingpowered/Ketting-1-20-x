package org.bukkit.craftbukkit.v1_20_R2.block;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import org.bukkit.Bukkit;
import org.bukkit.Nameable;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_20_R2.util.CraftNamespacedKey;
import org.bukkit.loot.LootTable;
import org.bukkit.loot.Lootable;

public abstract class CraftLootable<T extends RandomizableContainerBlockEntity> extends CraftContainer<T> implements Nameable, Lootable {

    public CraftLootable(World world, T tileEntity) {
        super(world, (BaseContainerBlockEntity) tileEntity);
    }

    protected CraftLootable(CraftLootable<T> state) {
        super((CraftContainer) state);
    }

    public void applyTo(T lootable) {
        super.applyTo((BaseContainerBlockEntity) lootable);
        if (this.getSnapshot().lootTable == null) {
            lootable.setLootTable((ResourceLocation) null, 0L);
        }

    }

    public LootTable getLootTable() {
        if (this.getSnapshot().lootTable == null) {
            return null;
        } else {
            ResourceLocation key = this.getSnapshot().lootTable;
            return Bukkit.getLootTable(CraftNamespacedKey.fromMinecraft(key));
        }
    }

    public void setLootTable(LootTable table) {
        this.setLootTable(table, this.getSeed());
    }

    public long getSeed() {
        return this.getSnapshot().lootTableSeed;
    }

    public void setSeed(long seed) {
        this.setLootTable(this.getLootTable(), seed);
    }

    private void setLootTable(LootTable table, long seed) {
        ResourceLocation key = table == null ? null : CraftNamespacedKey.toMinecraft(table.getKey());

        this.getSnapshot().setLootTable(key, seed);
    }

    public abstract CraftLootable<T> copy();
}
