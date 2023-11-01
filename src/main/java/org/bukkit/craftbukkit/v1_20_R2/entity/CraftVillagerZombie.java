package org.bukkit.craftbukkit.v1_20_R2.entity;

import com.google.common.base.Preconditions;
import java.util.UUID;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.monster.Zombie;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.entity.Villager.Profession;
import org.bukkit.entity.Villager.Type;
import org.bukkit.entity.ZombieVillager;
import org.bukkit.event.entity.EntityPotionEffectEvent.Cause;

public class CraftVillagerZombie extends CraftZombie implements ZombieVillager {

    public CraftVillagerZombie(CraftServer server, net.minecraft.world.entity.monster.ZombieVillager entity) {
        super(server, (Zombie) entity);
    }

    public net.minecraft.world.entity.monster.ZombieVillager getHandle() {
        return (net.minecraft.world.entity.monster.ZombieVillager) super.getHandle();
    }

    public String toString() {
        return "CraftVillagerZombie";
    }

    public Profession getVillagerProfession() {
        return CraftVillager.CraftProfession.minecraftToBukkit(this.getHandle().getVillagerData().getProfession());
    }

    public void setVillagerProfession(Profession profession) {
        Preconditions.checkArgument(profession != null, "Villager.Profession cannot be null");
        this.getHandle().setVillagerData(this.getHandle().getVillagerData().setProfession(CraftVillager.CraftProfession.bukkitToMinecraft(profession)));
    }

    public Type getVillagerType() {
        return CraftVillager.CraftType.minecraftToBukkit(this.getHandle().getVillagerData().getType());
    }

    public void setVillagerType(Type type) {
        Preconditions.checkArgument(type != null, "Villager.Type cannot be null");
        this.getHandle().setVillagerData(this.getHandle().getVillagerData().setType(CraftVillager.CraftType.bukkitToMinecraft(type)));
    }

    public boolean isConverting() {
        return this.getHandle().isConverting();
    }

    public int getConversionTime() {
        Preconditions.checkState(this.isConverting(), "Entity not converting");
        return this.getHandle().villagerConversionTime;
    }

    public void setConversionTime(int time) {
        if (time < 0) {
            this.getHandle().villagerConversionTime = -1;
            this.getHandle().getEntityData().set(net.minecraft.world.entity.monster.ZombieVillager.DATA_CONVERTING_ID, false);
            this.getHandle().conversionStarter = null;
            this.getHandle().removeEffect(MobEffects.DAMAGE_BOOST, Cause.CONVERSION);
        } else {
            this.getHandle().startConverting((UUID) null, time);
        }

    }

    public OfflinePlayer getConversionPlayer() {
        return this.getHandle().conversionStarter == null ? null : Bukkit.getOfflinePlayer(this.getHandle().conversionStarter);
    }

    public void setConversionPlayer(OfflinePlayer conversionPlayer) {
        if (this.isConverting()) {
            this.getHandle().conversionStarter = conversionPlayer == null ? null : conversionPlayer.getUniqueId();
        }
    }
}
