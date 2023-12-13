package org.bukkit.craftbukkit.block;

import net.minecraft.world.level.block.entity.ConduitBlockEntity;
import org.bukkit.World;
import org.bukkit.block.Conduit;

public class CraftConduit extends CraftBlockEntityState<ConduitBlockEntity> implements Conduit {

    public CraftConduit(World world, ConduitBlockEntity tileEntity) {
        super(world, tileEntity);
    }

    protected CraftConduit(CraftConduit state) {
        super(state);
    }

    @Override
    public CraftConduit copy() {
        return new CraftConduit(this);
    }
}
