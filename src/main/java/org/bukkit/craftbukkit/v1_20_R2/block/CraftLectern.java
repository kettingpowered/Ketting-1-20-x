package org.bukkit.craftbukkit.v1_20_R2.block;

import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LecternBlock;
import net.minecraft.world.level.block.entity.LecternBlockEntity;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Lectern;
import org.bukkit.craftbukkit.v1_20_R2.inventory.CraftInventoryLectern;
import org.bukkit.inventory.Inventory;

public class CraftLectern extends CraftBlockEntityState implements Lectern {

    public CraftLectern(World world, LecternBlockEntity tileEntity) {
        super(world, tileEntity);
    }

    protected CraftLectern(CraftLectern state) {
        super((CraftBlockEntityState) state);
    }

    public int getPage() {
        return ((LecternBlockEntity) this.getSnapshot()).getPage();
    }

    public void setPage(int page) {
        ((LecternBlockEntity) this.getSnapshot()).setPage(page);
    }

    public Inventory getSnapshotInventory() {
        return new CraftInventoryLectern(((LecternBlockEntity) this.getSnapshot()).bookAccess);
    }

    public Inventory getInventory() {
        return (Inventory) (!this.isPlaced() ? this.getSnapshotInventory() : new CraftInventoryLectern(((LecternBlockEntity) this.getTileEntity()).bookAccess));
    }

    public boolean update(boolean force, boolean applyPhysics) {
        boolean result = super.update(force, applyPhysics);

        if (result && this.getType() == Material.LECTERN && this.getWorldHandle() instanceof Level) {
            LecternBlock.signalPageChange(this.world.getHandle(), this.getPosition(), this.getHandle());
        }

        return result;
    }

    public CraftLectern copy() {
        return new CraftLectern(this);
    }
}
