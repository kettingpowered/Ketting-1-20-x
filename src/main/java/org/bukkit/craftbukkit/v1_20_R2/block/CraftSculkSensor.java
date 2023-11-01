package org.bukkit.craftbukkit.v1_20_R2.block;

import com.google.common.base.Preconditions;
import net.minecraft.world.level.block.entity.SculkSensorBlockEntity;
import org.bukkit.World;
import org.bukkit.block.SculkSensor;

public class CraftSculkSensor extends CraftBlockEntityState implements SculkSensor {

    public CraftSculkSensor(World world, SculkSensorBlockEntity tileEntity) {
        super(world, tileEntity);
    }

    protected CraftSculkSensor(CraftSculkSensor state) {
        super((CraftBlockEntityState) state);
    }

    public int getLastVibrationFrequency() {
        return ((SculkSensorBlockEntity) this.getSnapshot()).getLastVibrationFrequency();
    }

    public void setLastVibrationFrequency(int lastVibrationFrequency) {
        Preconditions.checkArgument(lastVibrationFrequency >= 0 && lastVibrationFrequency <= 15, "Vibration frequency must be between 0-15");
        ((SculkSensorBlockEntity) this.getSnapshot()).lastVibrationFrequency = lastVibrationFrequency;
    }

    public CraftSculkSensor copy() {
        return new CraftSculkSensor(this);
    }
}
