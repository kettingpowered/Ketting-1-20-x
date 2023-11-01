package org.bukkit.craftbukkit.v1_20_R2;

import com.google.common.base.Preconditions;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Random;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Container;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_20_R2.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_20_R2.entity.CraftHumanEntity;
import org.bukkit.craftbukkit.v1_20_R2.inventory.CraftInventory;
import org.bukkit.craftbukkit.v1_20_R2.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_20_R2.util.CraftLocation;
import org.bukkit.inventory.Inventory;
import org.bukkit.loot.LootContext;
import org.bukkit.loot.LootContext.Builder;
import org.bukkit.loot.LootTable;

public class CraftLootTable implements LootTable {

    private final net.minecraft.world.level.storage.loot.LootTable handle;
    private final NamespacedKey key;

    public CraftLootTable(NamespacedKey key, net.minecraft.world.level.storage.loot.LootTable handle) {
        this.handle = handle;
        this.key = key;
    }

    public net.minecraft.world.level.storage.loot.LootTable getHandle() {
        return this.handle;
    }

    public Collection populateLoot(Random random, LootContext context) {
        Preconditions.checkArgument(context != null, "LootContext cannot be null");
        LootParams nmsContext = this.convertContext(context, random);
        ObjectArrayList nmsItems = this.handle.getRandomItems(nmsContext);
        ArrayList bukkit = new ArrayList(nmsItems.size());
        Iterator iterator = nmsItems.iterator();

        while (iterator.hasNext()) {
            ItemStack item = (ItemStack) iterator.next();

            if (!item.isEmpty()) {
                bukkit.add(CraftItemStack.asBukkitCopy(item));
            }
        }

        return bukkit;
    }

    public void fillInventory(Inventory inventory, Random random, LootContext context) {
        Preconditions.checkArgument(inventory != null, "Inventory cannot be null");
        Preconditions.checkArgument(context != null, "LootContext cannot be null");
        LootParams nmsContext = this.convertContext(context, random);
        CraftInventory craftInventory = (CraftInventory) inventory;
        Container handle = craftInventory.getInventory();

        this.getHandle().fillInventory(handle, nmsContext, random.nextLong(), true);
    }

    public NamespacedKey getKey() {
        return this.key;
    }

    private LootParams convertContext(LootContext context, Random random) {
        Preconditions.checkArgument(context != null, "LootContext cannot be null");
        Location loc = context.getLocation();

        Preconditions.checkArgument(loc.getWorld() != null, "LootContext.getLocation#getWorld cannot be null");
        ServerLevel handle = ((CraftWorld) loc.getWorld()).getHandle();
        LootParams.Builder builder = new LootParams.Builder(handle);

        this.setMaybe(builder, LootContextParams.ORIGIN, CraftLocation.toVec3D(loc));
        if (this.getHandle() != net.minecraft.world.level.storage.loot.LootTable.EMPTY) {
            if (context.getLootedEntity() != null) {
                Entity nmsLootedEntity = ((CraftEntity) context.getLootedEntity()).getHandle();

                this.setMaybe(builder, LootContextParams.THIS_ENTITY, nmsLootedEntity);
                this.setMaybe(builder, LootContextParams.DAMAGE_SOURCE, handle.damageSources().generic());
                this.setMaybe(builder, LootContextParams.ORIGIN, nmsLootedEntity.position());
            }

            if (context.getKiller() != null) {
                Player nmsKiller = ((CraftHumanEntity) context.getKiller()).getHandle();

                this.setMaybe(builder, LootContextParams.KILLER_ENTITY, nmsKiller);
                this.setMaybe(builder, LootContextParams.DAMAGE_SOURCE, handle.damageSources().playerAttack(nmsKiller));
                this.setMaybe(builder, LootContextParams.LAST_DAMAGE_PLAYER, nmsKiller);
                this.setMaybe(builder, LootContextParams.TOOL, nmsKiller.getUseItem());
            }

            if (context.getLootingModifier() != -1) {
                this.setMaybe(builder, LootContextParams.LOOTING_MOD, context.getLootingModifier());
            }
        }

        LootContextParamSet.Builder nmsBuilder = new LootContextParamSet.Builder();
        Iterator iterator = this.getHandle().getParamSet().getRequired().iterator();

        LootContextParam param;

        while (iterator.hasNext()) {
            param = (LootContextParam) iterator.next();
            nmsBuilder.required(param);
        }

        iterator = this.getHandle().getParamSet().getAllowed().iterator();

        while (iterator.hasNext()) {
            param = (LootContextParam) iterator.next();
            if (!this.getHandle().getParamSet().getRequired().contains(param)) {
                nmsBuilder.optional(param);
            }
        }

        nmsBuilder.optional(LootContextParams.LOOTING_MOD);
        return builder.create(this.getHandle().getParamSet());
    }

    private void setMaybe(LootParams.Builder builder, LootContextParam param, Object value) {
        if (this.getHandle().getParamSet().getRequired().contains(param) || this.getHandle().getParamSet().getAllowed().contains(param)) {
            builder.withParameter(param, value);
        }

    }

    public static LootContext convertContext(net.minecraft.world.level.storage.loot.LootContext info) {
        Vec3 position = (Vec3) info.getParamOrNull(LootContextParams.ORIGIN);

        if (position == null) {
            position = ((Entity) info.getParamOrNull(LootContextParams.THIS_ENTITY)).position();
        }

        Location location = CraftLocation.toBukkit(position, (World) info.getLevel().getWorld());
        Builder contextBuilder = new Builder(location);

        if (info.hasParam(LootContextParams.KILLER_ENTITY)) {
            CraftEntity killer = ((Entity) info.getParamOrNull(LootContextParams.KILLER_ENTITY)).getBukkitEntity();

            if (killer instanceof CraftHumanEntity) {
                contextBuilder.killer((CraftHumanEntity) killer);
            }
        }

        if (info.hasParam(LootContextParams.THIS_ENTITY)) {
            contextBuilder.lootedEntity(((Entity) info.getParamOrNull(LootContextParams.THIS_ENTITY)).getBukkitEntity());
        }

        if (info.hasParam(LootContextParams.LOOTING_MOD)) {
            contextBuilder.lootingModifier((Integer) info.getParamOrNull(LootContextParams.LOOTING_MOD));
        }

        contextBuilder.luck(info.getLuck());
        return contextBuilder.build();
    }

    public String toString() {
        return this.getKey().toString();
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof LootTable)) {
            return false;
        } else {
            LootTable table = (LootTable) obj;

            return table.getKey().equals(this.getKey());
        }
    }
}
