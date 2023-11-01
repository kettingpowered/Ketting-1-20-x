package org.bukkit.craftbukkit.v1_20_R2.block;

import net.minecraft.world.level.block.entity.ComparatorBlockEntity;
import org.bukkit.World;
import org.bukkit.block.Comparator;

public class CraftComparator extends CraftBlockEntityState implements Comparator {

    public CraftComparator(World world, ComparatorBlockEntity tileEntity) {
        super(world, tileEntity);
    }

    protected CraftComparator(CraftComparator state) {
        super((CraftBlockEntityState) state);
    }

    public CraftComparator copy() {
        return new CraftComparator(this);
    }
}
