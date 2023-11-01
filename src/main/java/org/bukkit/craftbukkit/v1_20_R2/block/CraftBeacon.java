package org.bukkit.craftbukkit.v1_20_R2.block;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import net.minecraft.world.LockCode;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BeaconBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.bukkit.World;
import org.bukkit.block.Beacon;
import org.bukkit.craftbukkit.v1_20_R2.potion.CraftPotionEffectType;
import org.bukkit.craftbukkit.v1_20_R2.util.CraftChatMessage;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class CraftBeacon extends CraftBlockEntityState implements Beacon {

    public CraftBeacon(World world, BeaconBlockEntity tileEntity) {
        super(world, tileEntity);
    }

    protected CraftBeacon(CraftBeacon state) {
        super((CraftBlockEntityState) state);
    }

    public Collection getEntitiesInRange() {
        this.ensureNoWorldGeneration();
        BlockEntity tileEntity = this.getTileEntityFromWorld();

        if (!(tileEntity instanceof BeaconBlockEntity)) {
            return new ArrayList();
        } else {
            BeaconBlockEntity beacon = (BeaconBlockEntity) tileEntity;
            List nms = BeaconBlockEntity.getHumansInRange(beacon.getLevel(), beacon.getBlockPos(), beacon.levels);
            ArrayList bukkit = new ArrayList(nms.size());
            Iterator iterator = nms.iterator();

            while (iterator.hasNext()) {
                Player human = (Player) iterator.next();

                bukkit.add(human.getBukkitEntity());
            }

            return bukkit;
        }
    }

    public int getTier() {
        return ((BeaconBlockEntity) this.getSnapshot()).levels;
    }

    public PotionEffect getPrimaryEffect() {
        return ((BeaconBlockEntity) this.getSnapshot()).getPrimaryEffect();
    }

    public void setPrimaryEffect(PotionEffectType effect) {
        ((BeaconBlockEntity) this.getSnapshot()).primaryPower = effect != null ? CraftPotionEffectType.bukkitToMinecraft(effect) : null;
    }

    public PotionEffect getSecondaryEffect() {
        return ((BeaconBlockEntity) this.getSnapshot()).getSecondaryEffect();
    }

    public void setSecondaryEffect(PotionEffectType effect) {
        ((BeaconBlockEntity) this.getSnapshot()).secondaryPower = effect != null ? CraftPotionEffectType.bukkitToMinecraft(effect) : null;
    }

    public String getCustomName() {
        BeaconBlockEntity beacon = (BeaconBlockEntity) this.getSnapshot();

        return beacon.name != null ? CraftChatMessage.fromComponent(beacon.name) : null;
    }

    public void setCustomName(String name) {
        ((BeaconBlockEntity) this.getSnapshot()).setCustomName(CraftChatMessage.fromStringOrNull(name));
    }

    public boolean isLocked() {
        return !((BeaconBlockEntity) this.getSnapshot()).lockKey.key.isEmpty();
    }

    public String getLock() {
        return ((BeaconBlockEntity) this.getSnapshot()).lockKey.key;
    }

    public void setLock(String key) {
        ((BeaconBlockEntity) this.getSnapshot()).lockKey = key == null ? LockCode.NO_LOCK : new LockCode(key);
    }

    public CraftBeacon copy() {
        return new CraftBeacon(this);
    }
}
