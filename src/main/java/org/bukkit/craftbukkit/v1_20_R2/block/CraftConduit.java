package org.bukkit.craftbukkit.v1_20_R2.block;

import net.minecraft.world.level.block.entity.ConduitBlockEntity;
import org.bukkit.World;
import org.bukkit.block.Conduit;

public class CraftConduit extends CraftBlockEntityState implements Conduit {

    public CraftConduit(World world, ConduitBlockEntity tileEntity) {
        super(world, tileEntity);
    }

    protected CraftConduit(CraftConduit state) {
        super((CraftBlockEntityState) state);
    }

    public CraftConduit copy() {
        return new CraftConduit(this);
    }
}
