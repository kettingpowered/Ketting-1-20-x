package org.bukkit.craftbukkit.v1_20_R2.entity;

import com.google.common.base.Preconditions;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.LivingEntity;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.craftbukkit.v1_20_R2.CraftSound;
import org.bukkit.craftbukkit.v1_20_R2.util.CraftNamespacedKey;
import org.bukkit.entity.Mob;
import org.bukkit.event.entity.EntityTargetEvent.TargetReason;
import org.bukkit.loot.LootTable;

public abstract class CraftMob extends CraftLivingEntity implements Mob {

    public CraftMob(CraftServer server, net.minecraft.world.entity.Mob entity) {
        super(server, (LivingEntity) entity);
    }

    public void setTarget(org.bukkit.entity.LivingEntity target) {
        Preconditions.checkState(!this.getHandle().generation, "Cannot set target during world generation");
        net.minecraft.world.entity.Mob entity = this.getHandle();

        if (target == null) {
            entity.setTarget((LivingEntity) null, (TargetReason) null, false);
        } else if (target instanceof CraftLivingEntity) {
            entity.setTarget(((CraftLivingEntity) target).getHandle(), (TargetReason) null, false);
        }

    }

    public CraftLivingEntity getTarget() {
        return this.getHandle().getTarget() == null ? null : (CraftLivingEntity) this.getHandle().getTarget().getBukkitEntity();
    }

    public void setAware(boolean aware) {
        this.getHandle().aware = aware;
    }

    public boolean isAware() {
        return this.getHandle().aware;
    }

    public Sound getAmbientSound() {
        SoundEvent sound = this.getHandle().getAmbientSound0();

        return sound != null ? CraftSound.minecraftToBukkit(sound) : null;
    }

    public net.minecraft.world.entity.Mob getHandle() {
        return (net.minecraft.world.entity.Mob) this.entity;
    }

    public String toString() {
        return "CraftMob";
    }

    public void setLootTable(LootTable table) {
        this.getHandle().lootTable = table == null ? null : CraftNamespacedKey.toMinecraft(table.getKey());
    }

    public LootTable getLootTable() {
        NamespacedKey key = CraftNamespacedKey.fromMinecraft(this.getHandle().getLootTable());

        return Bukkit.getLootTable(key);
    }

    public void setSeed(long seed) {
        this.getHandle().lootTableSeed = seed;
    }

    public long getSeed() {
        return this.getHandle().lootTableSeed;
    }
}
