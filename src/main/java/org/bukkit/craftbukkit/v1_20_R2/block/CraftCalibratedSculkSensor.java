package org.bukkit.craftbukkit.v1_20_R2.block;

import net.minecraft.world.level.block.entity.CalibratedSculkSensorBlockEntity;
import net.minecraft.world.level.block.entity.SculkSensorBlockEntity;
import org.bukkit.World;
import org.bukkit.block.CalibratedSculkSensor;

public class CraftCalibratedSculkSensor extends CraftSculkSensor implements CalibratedSculkSensor {

    public CraftCalibratedSculkSensor(World world, CalibratedSculkSensorBlockEntity tileEntity) {
        super(world, (SculkSensorBlockEntity) tileEntity);
    }

    protected CraftCalibratedSculkSensor(CraftCalibratedSculkSensor state) {
        super((CraftSculkSensor) state);
    }

    public CraftCalibratedSculkSensor copy() {
        return new CraftCalibratedSculkSensor(this);
    }
}
