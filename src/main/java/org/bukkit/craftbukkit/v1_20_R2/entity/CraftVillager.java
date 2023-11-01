package org.bukkit.craftbukkit.v1_20_R2.entity;

import com.google.common.base.Preconditions;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerType;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_20_R2.CraftRegistry;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.craftbukkit.v1_20_R2.util.CraftLocation;
import org.bukkit.craftbukkit.v1_20_R2.util.CraftNamespacedKey;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Villager.Profession;
import org.bukkit.entity.Villager.Type;
import org.bukkit.entity.ZombieVillager;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

public class CraftVillager extends CraftAbstractVillager implements Villager {

    public CraftVillager(CraftServer server, net.minecraft.world.entity.npc.Villager entity) {
        super(server, (AbstractVillager) entity);
    }

    public net.minecraft.world.entity.npc.Villager getHandle() {
        return (net.minecraft.world.entity.npc.Villager) this.entity;
    }

    public String toString() {
        return "CraftVillager";
    }

    public void remove() {
        this.getHandle().releaseAllPois();
        super.remove();
    }

    public Profession getProfession() {
        return CraftVillager.CraftProfession.minecraftToBukkit(this.getHandle().getVillagerData().getProfession());
    }

    public void setProfession(Profession profession) {
        Preconditions.checkArgument(profession != null, "Profession cannot be null");
        this.getHandle().setVillagerData(this.getHandle().getVillagerData().setProfession(CraftVillager.CraftProfession.bukkitToMinecraft(profession)));
    }

    public Type getVillagerType() {
        return CraftVillager.CraftType.minecraftToBukkit(this.getHandle().getVillagerData().getType());
    }

    public void setVillagerType(Type type) {
        Preconditions.checkArgument(type != null, "Type cannot be null");
        this.getHandle().setVillagerData(this.getHandle().getVillagerData().setType(CraftVillager.CraftType.bukkitToMinecraft(type)));
    }

    public int getVillagerLevel() {
        return this.getHandle().getVillagerData().getLevel();
    }

    public void setVillagerLevel(int level) {
        Preconditions.checkArgument(1 <= level && level <= 5, "level (%s) must be between [1, 5]", level);
        this.getHandle().setVillagerData(this.getHandle().getVillagerData().setLevel(level));
    }

    public int getVillagerExperience() {
        return this.getHandle().getVillagerXp();
    }

    public void setVillagerExperience(int experience) {
        Preconditions.checkArgument(experience >= 0, "Experience (%s) must be positive", experience);
        this.getHandle().setVillagerXp(experience);
    }

    public boolean sleep(Location location) {
        Preconditions.checkArgument(location != null, "Location cannot be null");
        Preconditions.checkArgument(location.getWorld() != null, "Location needs to be in a world");
        Preconditions.checkArgument(location.getWorld().equals(this.getWorld()), "Cannot sleep across worlds");
        Preconditions.checkState(!this.getHandle().generation, "Cannot sleep during world generation");
        BlockPos position = CraftLocation.toBlockPosition(location);
        BlockState iblockdata = this.getHandle().level().getBlockState(position);

        if (!(iblockdata.getBlock() instanceof BedBlock)) {
            return false;
        } else {
            this.getHandle().startSleeping(position);
            return true;
        }
    }

    public void wakeup() {
        Preconditions.checkState(this.isSleeping(), "Cannot wakeup if not sleeping");
        Preconditions.checkState(!this.getHandle().generation, "Cannot wakeup during world generation");
        this.getHandle().stopSleeping();
    }

    public void shakeHead() {
        this.getHandle().setUnhappy();
    }

    public ZombieVillager zombify() {
        net.minecraft.world.entity.monster.ZombieVillager entityzombievillager = Zombie.zombifyVillager(this.getHandle().level().getMinecraftWorld(), this.getHandle(), this.getHandle().blockPosition(), this.isSilent(), SpawnReason.CUSTOM);

        return entityzombievillager != null ? (ZombieVillager) entityzombievillager.getBukkitEntity() : null;
    }

    public static class CraftProfession {

        public static Profession minecraftToBukkit(VillagerProfession minecraft) {
            Preconditions.checkArgument(minecraft != null);
            Registry registry = CraftRegistry.getMinecraftRegistry(Registries.VILLAGER_PROFESSION);
            Profession bukkit = (Profession) org.bukkit.Registry.VILLAGER_PROFESSION.get(CraftNamespacedKey.fromMinecraft(((ResourceKey) registry.getResourceKey(minecraft).orElseThrow()).location()));

            Preconditions.checkArgument(bukkit != null);
            return bukkit;
        }

        public static VillagerProfession bukkitToMinecraft(Profession bukkit) {
            Preconditions.checkArgument(bukkit != null);
            return (VillagerProfession) CraftRegistry.getMinecraftRegistry(Registries.VILLAGER_PROFESSION).getOptional(CraftNamespacedKey.toMinecraft(bukkit.getKey())).orElseThrow();
        }
    }

    public static class CraftType {

        public static Type minecraftToBukkit(VillagerType minecraft) {
            Preconditions.checkArgument(minecraft != null);
            Registry registry = CraftRegistry.getMinecraftRegistry(Registries.VILLAGER_TYPE);
            Type bukkit = (Type) org.bukkit.Registry.VILLAGER_TYPE.get(CraftNamespacedKey.fromMinecraft(((ResourceKey) registry.getResourceKey(minecraft).orElseThrow()).location()));

            Preconditions.checkArgument(bukkit != null);
            return bukkit;
        }

        public static VillagerType bukkitToMinecraft(Type bukkit) {
            Preconditions.checkArgument(bukkit != null);
            return (VillagerType) CraftRegistry.getMinecraftRegistry(Registries.VILLAGER_TYPE).getOptional(CraftNamespacedKey.toMinecraft(bukkit.getKey())).orElseThrow();
        }
    }
}
