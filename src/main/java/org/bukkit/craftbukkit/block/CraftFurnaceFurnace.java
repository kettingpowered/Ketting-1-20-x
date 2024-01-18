package org.bukkit.craftbukkit.block;

import net.minecraft.world.level.block.entity.FurnaceBlockEntity;
import org.bukkit.World;

public class CraftFurnaceFurnace extends CraftFurnace<FurnaceBlockEntity> {

    public CraftFurnaceFurnace(World world, FurnaceBlockEntity tileEntity) {
        super(world, tileEntity);
    }
}
