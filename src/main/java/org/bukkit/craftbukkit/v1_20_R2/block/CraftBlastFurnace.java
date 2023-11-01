package org.bukkit.craftbukkit.v1_20_R2.block;

import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.BlastFurnaceBlockEntity;
import org.bukkit.World;
import org.bukkit.block.BlastFurnace;

public class CraftBlastFurnace extends CraftFurnace implements BlastFurnace {

    public CraftBlastFurnace(World world, BlastFurnaceBlockEntity tileEntity) {
        super(world, (AbstractFurnaceBlockEntity) tileEntity);
    }

    protected CraftBlastFurnace(CraftBlastFurnace state) {
        super((CraftFurnace) state);
    }

    public CraftBlastFurnace copy() {
        return new CraftBlastFurnace(this);
    }
}
