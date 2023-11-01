package org.bukkit.craftbukkit.v1_20_R2.entity;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.entity.vehicle.AbstractMinecartContainer;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.craftbukkit.v1_20_R2.util.CraftNamespacedKey;
import org.bukkit.loot.LootTable;
import org.bukkit.loot.Lootable;

public abstract class CraftMinecartContainer extends CraftMinecart implements Lootable {

    public CraftMinecartContainer(CraftServer server, AbstractMinecart entity) {
        super(server, entity);
    }

    public AbstractMinecartContainer getHandle() {
        return (AbstractMinecartContainer) this.entity;
    }

    public void setLootTable(LootTable table) {
        this.setLootTable(table, this.getSeed());
    }

    public LootTable getLootTable() {
        ResourceLocation nmsTable = this.getHandle().lootTable;

        if (nmsTable == null) {
            return null;
        } else {
            NamespacedKey key = CraftNamespacedKey.fromMinecraft(nmsTable);

            return Bukkit.getLootTable(key);
        }
    }

    public void setSeed(long seed) {
        this.setLootTable(this.getLootTable(), seed);
    }

    public long getSeed() {
        return this.getHandle().lootTableSeed;
    }

    private void setLootTable(LootTable table, long seed) {
        ResourceLocation newKey = table == null ? null : CraftNamespacedKey.toMinecraft(table.getKey());

        this.getHandle().setLootTable(newKey, seed);
    }
}
