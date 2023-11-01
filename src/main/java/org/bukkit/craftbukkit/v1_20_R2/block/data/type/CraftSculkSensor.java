package org.bukkit.craftbukkit.v1_20_R2.block.data.type;

import net.minecraft.world.level.block.state.properties.EnumProperty;
import org.bukkit.block.data.type.SculkSensor;
import org.bukkit.block.data.type.SculkSensor.Phase;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;

public abstract class CraftSculkSensor extends CraftBlockData implements SculkSensor {

    private static final EnumProperty PHASE = getEnum("sculk_sensor_phase");

    public Phase getPhase() {
        return (Phase) this.get(CraftSculkSensor.PHASE, Phase.class);
    }

    public void setPhase(Phase phase) {
        this.set(CraftSculkSensor.PHASE, (Enum) phase);
    }
}
