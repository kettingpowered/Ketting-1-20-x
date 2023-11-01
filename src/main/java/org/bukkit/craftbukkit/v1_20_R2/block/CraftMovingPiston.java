package org.bukkit.craftbukkit.v1_20_R2.block;

import net.minecraft.world.level.block.piston.PistonMovingBlockEntity;
import org.bukkit.World;

public class CraftMovingPiston extends CraftBlockEntityState {

    public CraftMovingPiston(World world, PistonMovingBlockEntity tileEntity) {
        super(world, tileEntity);
    }

    protected CraftMovingPiston(CraftMovingPiston state) {
        super((CraftBlockEntityState) state);
    }

    public CraftMovingPiston copy() {
        return new CraftMovingPiston(this);
    }
}
