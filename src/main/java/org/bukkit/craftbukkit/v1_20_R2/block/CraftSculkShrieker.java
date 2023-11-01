package org.bukkit.craftbukkit.v1_20_R2.block;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.entity.SculkShriekerBlockEntity;
import org.bukkit.World;
import org.bukkit.block.SculkShrieker;
import org.bukkit.craftbukkit.v1_20_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class CraftSculkShrieker extends CraftBlockEntityState implements SculkShrieker {

    public CraftSculkShrieker(World world, SculkShriekerBlockEntity tileEntity) {
        super(world, tileEntity);
    }

    protected CraftSculkShrieker(CraftSculkShrieker state) {
        super((CraftBlockEntityState) state);
    }

    public int getWarningLevel() {
        return ((SculkShriekerBlockEntity) this.getSnapshot()).warningLevel;
    }

    public void setWarningLevel(int level) {
        ((SculkShriekerBlockEntity) this.getSnapshot()).warningLevel = level;
    }

    public void tryShriek(Player player) {
        this.requirePlaced();
        ServerPlayer entityPlayer = player == null ? null : ((CraftPlayer) player).getHandle();

        ((SculkShriekerBlockEntity) this.getTileEntity()).tryShriek(this.world.getHandle(), entityPlayer);
    }

    public CraftSculkShrieker copy() {
        return new CraftSculkShrieker(this);
    }
}
