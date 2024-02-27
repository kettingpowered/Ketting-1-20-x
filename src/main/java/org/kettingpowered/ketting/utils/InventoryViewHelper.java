package org.kettingpowered.ketting.utils;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import org.bukkit.craftbukkit.v1_20_R1.inventory.CraftInventory;
import org.bukkit.craftbukkit.v1_20_R1.inventory.CraftInventoryView;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.InventoryView;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public final class InventoryViewHelper {

    private static Player containerOwner;

    public static void setContainerOwner(Player owner) {
        containerOwner = owner;
    }

    public static Player getContainerOwner() {
        return containerOwner;
    }

    public static void clearContainerOwner() {
        containerOwner = null;
    }

    public static InventoryView createView(@NotNull AbstractContainerMenu containerMenu) {
        Player owner = getContainerOwner();
        Inventory view = new CraftInventory(new ContainerWrapper(containerMenu, owner));
        return new CraftInventoryView(owner.getBukkitEntity(), view, containerMenu);
    }

    private static class ContainerWrapper implements Container {

        private final AbstractContainerMenu containerMenu;
        private final InventoryHolder owner;

        public ContainerWrapper(@NotNull AbstractContainerMenu containerMenu, @NotNull Player owner) {
            this.containerMenu = containerMenu;
            this.owner = owner.getBukkitEntity();
        }

        public int getContainerSize() {
            return containerMenu.lastSlots.size();
        }

        public boolean isEmpty() {
            return containerMenu.getItems().stream().allMatch(ItemStack::isEmpty);
        }

        public void clearContent() {
            containerMenu.slots.forEach(slot -> slot.set(ItemStack.EMPTY));
        }

        public @NotNull ItemStack getItem(int p_18941_) {
            if (p_18941_ >= getContainerSize())
                return ItemStack.EMPTY;
            return containerMenu.getSlot(p_18941_).getItem();
        }

        public @NotNull ItemStack removeItem(int p_18942_, int p_18943_) {
            if (p_18942_ >= getContainerSize())
                return ItemStack.EMPTY;
            return containerMenu.getSlot(p_18942_).remove(p_18943_);
        }

        public @NotNull ItemStack removeItemNoUpdate(int p_18951_) {
            if (p_18951_ >= getContainerSize())
                return ItemStack.EMPTY;
            return containerMenu.getSlot(p_18951_).container.removeItemNoUpdate(p_18951_);
        }

        public void setItem(int p_18944_, @NotNull ItemStack p_18945_) {
            if (p_18944_ < getContainerSize())
                containerMenu.getSlot(p_18944_).set(p_18945_);
        }

        public int getMaxStackSize() {
            if (getContainerSize() <= 0)
                return 0;
            return containerMenu.getSlot(0).getMaxStackSize();
        }

        public void setChanged() {
            containerMenu.broadcastChanges();
        }

        public boolean stillValid(@NotNull Player p_18946_) {
            return containerMenu.stillValid(p_18946_);
        }

        //Bukkit things
        public @NotNull List<ItemStack> getContents() {
            setChanged(); //Sync the container first
            return containerMenu.lastSlots.subList(0, getContainerSize());
        }

        public InventoryHolder getOwner() {
            return owner;
        }
    }
}
