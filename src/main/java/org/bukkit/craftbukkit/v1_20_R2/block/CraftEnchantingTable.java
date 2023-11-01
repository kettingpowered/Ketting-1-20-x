package org.bukkit.craftbukkit.v1_20_R2.block;

import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.EnchantmentTableBlockEntity;
import org.bukkit.World;
import org.bukkit.block.EnchantingTable;
import org.bukkit.craftbukkit.v1_20_R2.util.CraftChatMessage;

public class CraftEnchantingTable extends CraftBlockEntityState implements EnchantingTable {

    public CraftEnchantingTable(World world, EnchantmentTableBlockEntity tileEntity) {
        super(world, tileEntity);
    }

    protected CraftEnchantingTable(CraftEnchantingTable state) {
        super((CraftBlockEntityState) state);
    }

    public String getCustomName() {
        EnchantmentTableBlockEntity enchant = (EnchantmentTableBlockEntity) this.getSnapshot();

        return enchant.hasCustomName() ? CraftChatMessage.fromComponent(enchant.getCustomName()) : null;
    }

    public void setCustomName(String name) {
        ((EnchantmentTableBlockEntity) this.getSnapshot()).setCustomName(CraftChatMessage.fromStringOrNull(name));
    }

    public void applyTo(EnchantmentTableBlockEntity enchantingTable) {
        super.applyTo(enchantingTable);
        if (!((EnchantmentTableBlockEntity) this.getSnapshot()).hasCustomName()) {
            enchantingTable.setCustomName((Component) null);
        }

    }

    public CraftEnchantingTable copy() {
        return new CraftEnchantingTable(this);
    }
}
