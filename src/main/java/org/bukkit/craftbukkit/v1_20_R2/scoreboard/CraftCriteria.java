package org.bukkit.craftbukkit.v1_20_R2.scoreboard;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.world.scores.Objective;
import net.minecraft.world.scores.criteria.ObjectiveCriteria;
import org.bukkit.scoreboard.Criteria;
import org.bukkit.scoreboard.RenderType;

public final class CraftCriteria implements Criteria {

    static final Map DEFAULTS;
    static final CraftCriteria DUMMY;
    final ObjectiveCriteria criteria;
    final String bukkitName;

    static {
        Builder defaults = ImmutableMap.builder();
        Iterator iterator = ObjectiveCriteria.CRITERIA_CACHE.entrySet().iterator();

        while (iterator.hasNext()) {
            Entry entry = (Entry) iterator.next();
            String name = (String) entry.getKey();
            ObjectiveCriteria criteria = (ObjectiveCriteria) entry.getValue();

            defaults.put(name, new CraftCriteria(criteria));
        }

        DEFAULTS = defaults.build();
        DUMMY = (CraftCriteria) CraftCriteria.DEFAULTS.get("dummy");
    }

    private CraftCriteria(String bukkitName) {
        this.bukkitName = bukkitName;
        this.criteria = CraftCriteria.DUMMY.criteria;
    }

    private CraftCriteria(ObjectiveCriteria criteria) {
        this.criteria = criteria;
        this.bukkitName = criteria.getName();
    }

    public String getName() {
        return this.bukkitName;
    }

    public boolean isReadOnly() {
        return this.criteria.isReadOnly();
    }

    public RenderType getDefaultRenderType() {
        return RenderType.values()[this.criteria.getDefaultRenderType().ordinal()];
    }

    static CraftCriteria getFromNMS(Objective objective) {
        return (CraftCriteria) CraftCriteria.DEFAULTS.get(objective.getCriteria().getName());
    }

    public static CraftCriteria getFromBukkit(String name) {
        CraftCriteria criteria = (CraftCriteria) CraftCriteria.DEFAULTS.get(name);

        return criteria != null ? criteria : (CraftCriteria) ObjectiveCriteria.byName(name).map(CraftCriteria::new).orElseGet(() -> {
            return new CraftCriteria(name);
        });
    }

    public boolean equals(Object that) {
        return !(that instanceof CraftCriteria) ? false : ((CraftCriteria) that).bukkitName.equals(this.bukkitName);
    }

    public int hashCode() {
        return this.bukkitName.hashCode() ^ CraftCriteria.class.hashCode();
    }
}
