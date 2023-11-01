package org.bukkit.craftbukkit.v1_20_R2.block.data.type;

import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.bukkit.block.data.type.SculkShrieker;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;

public abstract class CraftSculkShrieker extends CraftBlockData implements SculkShrieker {

    private static final BooleanProperty CAN_SUMMON = getBoolean("can_summon");
    private static final BooleanProperty SHRIEKING = getBoolean("shrieking");

    public boolean isCanSummon() {
        return (Boolean) this.get(CraftSculkShrieker.CAN_SUMMON);
    }

    public void setCanSummon(boolean can_summon) {
        this.set((Property) CraftSculkShrieker.CAN_SUMMON, (Comparable) can_summon);
    }

    public boolean isShrieking() {
        return (Boolean) this.get(CraftSculkShrieker.SHRIEKING);
    }

    public void setShrieking(boolean shrieking) {
        this.set((Property) CraftSculkShrieker.SHRIEKING, (Comparable) shrieking);
    }
}
