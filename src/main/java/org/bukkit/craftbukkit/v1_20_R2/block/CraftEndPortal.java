package org.bukkit.craftbukkit.v1_20_R2.block;

import net.minecraft.world.level.block.entity.TheEndPortalBlockEntity;
import org.bukkit.World;

public class CraftEndPortal extends CraftBlockEntityState {

    public CraftEndPortal(World world, TheEndPortalBlockEntity tileEntity) {
        super(world, tileEntity);
    }

    protected CraftEndPortal(CraftEndPortal state) {
        super((CraftBlockEntityState) state);
    }

    public CraftEndPortal copy() {
        return new CraftEndPortal(this);
    }
}
