package org.bukkit.craftbukkit.v1_20_R2.block;

import net.minecraft.world.level.block.entity.HangingSignBlockEntity;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import org.bukkit.World;
import org.bukkit.block.HangingSign;

public class CraftHangingSign extends CraftSign implements HangingSign {

    public CraftHangingSign(World world, HangingSignBlockEntity tileEntity) {
        super(world, (SignBlockEntity) tileEntity);
    }

    protected CraftHangingSign(CraftHangingSign state) {
        super((CraftSign) state);
    }

    public CraftHangingSign copy() {
        return new CraftHangingSign(this);
    }
}
