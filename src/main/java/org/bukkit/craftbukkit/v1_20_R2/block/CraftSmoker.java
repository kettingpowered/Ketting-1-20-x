package org.bukkit.craftbukkit.v1_20_R2.block;

import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.SmokerBlockEntity;
import org.bukkit.World;
import org.bukkit.block.Smoker;

public class CraftSmoker extends CraftFurnace implements Smoker {

    public CraftSmoker(World world, SmokerBlockEntity tileEntity) {
        super(world, (AbstractFurnaceBlockEntity) tileEntity);
    }

    protected CraftSmoker(CraftSmoker state) {
        super((CraftFurnace) state);
    }

    public CraftSmoker copy() {
        return new CraftSmoker(this);
    }
}
