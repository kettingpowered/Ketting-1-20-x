package org.bukkit.craftbukkit.v1_20_R2.entity;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSet.Builder;
import java.util.Set;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.boss.EnderDragonPart;
import net.minecraft.world.entity.boss.enderdragon.phases.EnderDragonPhase;
import org.bukkit.boss.BossBar;
import org.bukkit.boss.DragonBattle;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.craftbukkit.v1_20_R2.boss.CraftDragonBattle;
import org.bukkit.entity.ComplexEntityPart;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.EnderDragon.Phase;

public class CraftEnderDragon extends CraftMob implements EnderDragon, CraftEnemy {

    public CraftEnderDragon(CraftServer server, net.minecraft.world.entity.boss.enderdragon.EnderDragon entity) {
        super(server, (Mob) entity);
    }

    public Set getParts() {
        Builder builder = ImmutableSet.builder();
        EnderDragonPart[] aenderdragonpart;
        int i = (aenderdragonpart = this.getHandle().subEntities).length;

        for (int j = 0; j < i; ++j) {
            EnderDragonPart part = aenderdragonpart[j];

            builder.add((ComplexEntityPart) part.getBukkitEntity());
        }

        return builder.build();
    }

    public net.minecraft.world.entity.boss.enderdragon.EnderDragon getHandle() {
        return (net.minecraft.world.entity.boss.enderdragon.EnderDragon) this.entity;
    }

    public String toString() {
        return "CraftEnderDragon";
    }

    public Phase getPhase() {
        //Ketting start
        int phaseId = this.getHandle().getEntityData().get(net.minecraft.world.entity.boss.enderdragon.EnderDragon.DATA_PHASE);
        return phaseId > Phase.values().length - 1 ? Phase.MODDED : Phase.values()[phaseId];
        //Ketting end
    }

    public void setPhase(Phase phase) {
        this.getHandle().getPhaseManager().setPhase(getMinecraftPhase(phase));
    }

    public static Phase getBukkitPhase(EnderDragonPhase phase) {
        //Ketting start
        return phase.getId() > Phase.values().length - 1 ? Phase.MODDED : Phase.values()[phase.getId()];
        //Ketting end
    }

    public static EnderDragonPhase getMinecraftPhase(Phase phase) {
        if (phase == Phase.MODDED) return null; //Ketting
        return EnderDragonPhase.getById(phase.ordinal());
    }

    public BossBar getBossBar() {
        DragonBattle battle = this.getDragonBattle();

        return battle != null ? battle.getBossBar() : null;
    }

    public DragonBattle getDragonBattle() {
        return this.getHandle().getDragonFight() != null ? new CraftDragonBattle(this.getHandle().getDragonFight()) : null;
    }

    public int getDeathAnimationTicks() {
        return this.getHandle().dragonDeathTime;
    }
}
