package org.bukkit.craftbukkit.v1_20_R2.advancement;

import com.google.common.collect.Lists;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import net.minecraft.advancements.CriterionProgress;
import net.minecraft.server.PlayerAdvancements;
import org.bukkit.advancement.Advancement;
import org.bukkit.advancement.AdvancementProgress;

public class CraftAdvancementProgress implements AdvancementProgress {

    private final CraftAdvancement advancement;
    private final PlayerAdvancements playerData;
    private final net.minecraft.advancements.AdvancementProgress handle;

    public CraftAdvancementProgress(CraftAdvancement advancement, PlayerAdvancements player, net.minecraft.advancements.AdvancementProgress handle) {
        this.advancement = advancement;
        this.playerData = player;
        this.handle = handle;
    }

    public Advancement getAdvancement() {
        return this.advancement;
    }

    public boolean isDone() {
        return this.handle.isDone();
    }

    public boolean awardCriteria(String criteria) {
        return this.playerData.award(this.advancement.getHandle(), criteria);
    }

    public boolean revokeCriteria(String criteria) {
        return this.playerData.revoke(this.advancement.getHandle(), criteria);
    }

    public Date getDateAwarded(String criteria) {
        CriterionProgress criterion = this.handle.getCriterion(criteria);

        return criterion == null ? null : Date.from(criterion.getObtained());
    }

    public Collection getRemainingCriteria() {
        return Collections.unmodifiableCollection(Lists.newArrayList(this.handle.getRemainingCriteria()));
    }

    public Collection getAwardedCriteria() {
        return Collections.unmodifiableCollection(Lists.newArrayList(this.handle.getCompletedCriteria()));
    }
}
