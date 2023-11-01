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

public abstract class CraftLootable extends CraftContainer implements Nameable, Lootable {

    public CraftLootable(World world, RandomizableContainerBlockEntity tileEntity) {
        super(world, (BaseContainerBlockEntity) tileEntity);
    }

    protected CraftLootable(CraftLootable state) {
        super((CraftContainer) state);
    }

    public void applyTo(RandomizableContainerBlockEntity lootable) {
        super.applyTo((BaseContainerBlockEntity) lootable);
        if (((RandomizableContainerBlockEntity) this.getSnapshot()).lootTable == null) {
            lootable.setLootTable((ResourceLocation) null, 0L);
        }

    }

    public LootTable getLootTable() {
        if (((RandomizableContainerBlockEntity) this.getSnapshot()).lootTable == null) {
            return null;
        } else {
            ResourceLocation key = ((RandomizableContainerBlockEntity) this.getSnapshot()).lootTable;

            return Bukkit.getLootTable(CraftNamespacedKey.fromMinecraft(key));
        }
    }

    public void setLootTable(LootTable table) {
        this.setLootTable(table, this.getSeed());
    }

    public long getSeed() {
        return ((RandomizableContainerBlockEntity) this.getSnapshot()).lootTableSeed;
    }

    public void setSeed(long seed) {
        this.setLootTable(this.getLootTable(), seed);
    }

    private void setLootTable(LootTable table, long seed) {
        ResourceLocation key = table == null ? null : CraftNamespacedKey.toMinecraft(table.getKey());

        ((RandomizableContainerBlockEntity) this.getSnapshot()).setLootTable(key, seed);
    }

    public abstract CraftLootable copy();
}
