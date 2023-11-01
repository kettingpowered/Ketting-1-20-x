package org.bukkit.craftbukkit.v1_20_R2.entity;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.vehicle.Boat;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.craftbukkit.v1_20_R2.inventory.CraftInventory;
import org.bukkit.craftbukkit.v1_20_R2.util.CraftNamespacedKey;
import org.bukkit.entity.ChestBoat;
import org.bukkit.inventory.Inventory;
import org.bukkit.loot.LootTable;

public class CraftChestBoat extends CraftBoat implements ChestBoat {

    private final Inventory inventory;

    public CraftChestBoat(CraftServer server, net.minecraft.world.entity.vehicle.ChestBoat entity) {
        super(server, (Boat) entity);
        this.inventory = new CraftInventory(entity);
    }

    public net.minecraft.world.entity.vehicle.ChestBoat getHandle() {
        return (net.minecraft.world.entity.vehicle.ChestBoat) this.entity;
    }

    public String toString() {
        return "CraftChestBoat";
    }

    public Inventory getInventory() {
        return this.inventory;
    }

    public void setLootTable(LootTable table) {
        this.setLootTable(table, this.getSeed());
    }

    public LootTable getLootTable() {
        ResourceLocation nmsTable = this.getHandle().getLootTable();

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
        return this.getHandle().getLootTableSeed();
    }

    private void setLootTable(LootTable table, long seed) {
        ResourceLocation newKey = table == null ? null : CraftNamespacedKey.toMinecraft(table.getKey());

        this.getHandle().setLootTable(newKey);
        this.getHandle().setLootTableSeed(seed);
    }
}
