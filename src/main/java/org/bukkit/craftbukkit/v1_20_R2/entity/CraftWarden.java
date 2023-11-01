package org.bukkit.craftbukkit.v1_20_R2.entity;

import com.google.common.base.Preconditions;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.warden.WardenAi;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Warden;
import org.bukkit.entity.Warden.AngerLevel;

public class CraftWarden extends CraftMonster implements Warden {

    private static volatile int[] $SWITCH_TABLE$net$minecraft$world$entity$monster$warden$AngerLevel;

    public CraftWarden(CraftServer server, net.minecraft.world.entity.monster.warden.Warden entity) {
        super(server, (Monster) entity);
    }

    public net.minecraft.world.entity.monster.warden.Warden getHandle() {
        return (net.minecraft.world.entity.monster.warden.Warden) this.entity;
    }

    public String toString() {
        return "CraftWarden";
    }

    public int getAnger() {
        return this.getHandle().getAngerManagement().getActiveAnger(this.getHandle().getTarget());
    }

    public int getAnger(Entity entity) {
        Preconditions.checkArgument(entity != null, "Entity cannot be null");
        return this.getHandle().getAngerManagement().getActiveAnger(((CraftEntity) entity).getHandle());
    }

    public void increaseAnger(Entity entity, int increase) {
        Preconditions.checkArgument(entity != null, "Entity cannot be null");
        this.getHandle().getAngerManagement().increaseAnger(((CraftEntity) entity).getHandle(), increase);
    }

    public void setAnger(Entity entity, int anger) {
        Preconditions.checkArgument(entity != null, "Entity cannot be null");
        this.getHandle().clearAnger(((CraftEntity) entity).getHandle());
        this.getHandle().getAngerManagement().increaseAnger(((CraftEntity) entity).getHandle(), anger);
    }

    public void clearAnger(Entity entity) {
        Preconditions.checkArgument(entity != null, "Entity cannot be null");
        this.getHandle().clearAnger(((CraftEntity) entity).getHandle());
    }

    public LivingEntity getEntityAngryAt() {
        return (LivingEntity) this.getHandle().getEntityAngryAt().map(net.minecraft.world.entity.Entity::getBukkitEntity).orElse((Object) null);
    }

    public void setDisturbanceLocation(Location location) {
        Preconditions.checkArgument(location != null, "Location cannot be null");
        WardenAi.setDisturbanceLocation(this.getHandle(), BlockPos.containing(location.getX(), location.getY(), location.getZ()));
    }

    public AngerLevel getAngerLevel() {
        AngerLevel angerlevel;

        switch ($SWITCH_TABLE$net$minecraft$world$entity$monster$warden$AngerLevel()[this.getHandle().getAngerLevel().ordinal()]) {
            case 1:
                angerlevel = AngerLevel.CALM;
                break;
            case 2:
                angerlevel = AngerLevel.AGITATED;
                break;
            case 3:
                angerlevel = AngerLevel.ANGRY;
                break;
            default:
                throw new IncompatibleClassChangeError();
        }

        return angerlevel;
    }

    static int[] $SWITCH_TABLE$net$minecraft$world$entity$monster$warden$AngerLevel() {
        int[] aint = CraftWarden.$SWITCH_TABLE$net$minecraft$world$entity$monster$warden$AngerLevel;

        if (aint != null) {
            return aint;
        } else {
            int[] aint1 = new int[net.minecraft.world.entity.monster.warden.AngerLevel.values().length];

            try {
                aint1[net.minecraft.world.entity.monster.warden.AngerLevel.AGITATED.ordinal()] = 2;
            } catch (NoSuchFieldError nosuchfielderror) {
                ;
            }

            try {
                aint1[net.minecraft.world.entity.monster.warden.AngerLevel.ANGRY.ordinal()] = 3;
            } catch (NoSuchFieldError nosuchfielderror1) {
                ;
            }

            try {
                aint1[net.minecraft.world.entity.monster.warden.AngerLevel.CALM.ordinal()] = 1;
            } catch (NoSuchFieldError nosuchfielderror2) {
                ;
            }

            CraftWarden.$SWITCH_TABLE$net$minecraft$world$entity$monster$warden$AngerLevel = aint1;
            return aint1;
        }
    }
}
