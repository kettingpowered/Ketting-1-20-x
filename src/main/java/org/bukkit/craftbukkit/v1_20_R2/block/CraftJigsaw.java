package org.bukkit.craftbukkit.v1_20_R2.block;

import net.minecraft.world.level.block.entity.JigsawBlockEntity;
import org.bukkit.World;
import org.bukkit.block.Jigsaw;

public class CraftJigsaw extends CraftBlockEntityState implements Jigsaw {

    public CraftJigsaw(World world, JigsawBlockEntity tileEntity) {
        super(world, tileEntity);
    }

    protected CraftJigsaw(CraftJigsaw state) {
        super((CraftBlockEntityState) state);
    }

    public CraftJigsaw copy() {
        return new CraftJigsaw(this);
    }
}
