package org.bukkit.craftbukkit.v1_20_R2.block;

import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.FurnaceBlockEntity;
import org.bukkit.World;

public class CraftFurnaceFurnace extends CraftFurnace {

    public CraftFurnaceFurnace(World world, FurnaceBlockEntity tileEntity) {
        super(world, (AbstractFurnaceBlockEntity) tileEntity);
    }

    protected CraftFurnaceFurnace(CraftFurnaceFurnace state) {
        super((CraftFurnace) state);
    }

    public CraftFurnaceFurnace copy() {
        return new CraftFurnaceFurnace(this);
    }
}
