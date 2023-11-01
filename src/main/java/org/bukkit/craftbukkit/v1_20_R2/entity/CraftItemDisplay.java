package org.bukkit.craftbukkit.v1_20_R2.entity;

import com.google.common.base.Preconditions;
import net.minecraft.world.entity.Display;
import net.minecraft.world.item.ItemDisplayContext;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.craftbukkit.v1_20_R2.inventory.CraftItemStack;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.ItemDisplay.ItemDisplayTransform;
import org.bukkit.inventory.ItemStack;

public class CraftItemDisplay extends CraftDisplay implements ItemDisplay {

    public CraftItemDisplay(CraftServer server, Display.ItemDisplay entity) {
        super(server, (Display) entity);
    }

    public Display.ItemDisplay getHandle() {
        return (Display.ItemDisplay) super.getHandle();
    }

    public String toString() {
        return "CraftItemDisplay";
    }

    public ItemStack getItemStack() {
        return CraftItemStack.asBukkitCopy(this.getHandle().getItemStack());
    }

    public void setItemStack(ItemStack item) {
        this.getHandle().setItemStack(CraftItemStack.asNMSCopy(item));
    }

    public ItemDisplayTransform getItemDisplayTransform() {
        return ItemDisplayTransform.values()[this.getHandle().getItemTransform().ordinal()];
    }

    public void setItemDisplayTransform(ItemDisplayTransform display) {
        Preconditions.checkArgument(display != null, "Display cannot be null");
        this.getHandle().setItemTransform((ItemDisplayContext) ItemDisplayContext.BY_ID.apply(display.ordinal()));
    }
}
