package org.bukkit.craftbukkit.v1_20_R2.block;

import net.minecraft.world.level.block.entity.DaylightDetectorBlockEntity;
import org.bukkit.World;
import org.bukkit.block.DaylightDetector;

public class CraftDaylightDetector extends CraftBlockEntityState implements DaylightDetector {

    public CraftDaylightDetector(World world, DaylightDetectorBlockEntity tileEntity) {
        super(world, tileEntity);
    }

    protected CraftDaylightDetector(CraftDaylightDetector state) {
        super((CraftBlockEntityState) state);
    }

    public CraftDaylightDetector copy() {
        return new CraftDaylightDetector(this);
    }
}
