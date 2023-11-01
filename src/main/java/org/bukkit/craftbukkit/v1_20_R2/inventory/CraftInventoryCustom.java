package org.bukkit.craftbukkit.v1_20_R2.inventory;

import com.google.common.base.Preconditions;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.core.NonNullList;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_20_R2.entity.CraftHumanEntity;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.InventoryHolder;

public class CraftInventoryCustom extends CraftInventory {

    public CraftInventoryCustom(InventoryHolder owner, InventoryType type) {
        super(new CraftInventoryCustom.MinecraftInventory(owner, type));
    }

    public CraftInventoryCustom(InventoryHolder owner, InventoryType type, String title) {
        super(new CraftInventoryCustom.MinecraftInventory(owner, type, title));
    }

    public CraftInventoryCustom(InventoryHolder owner, int size) {
        super(new CraftInventoryCustom.MinecraftInventory(owner, size));
    }

    public CraftInventoryCustom(InventoryHolder owner, int size, String title) {
        super(new CraftInventoryCustom.MinecraftInventory(owner, size, title));
    }

    static class MinecraftInventory implements Container {

        private final NonNullList items;
        private int maxStack;
        private final List viewers;
        private final String title;
        private InventoryType type;
        private final InventoryHolder owner;

        public MinecraftInventory(InventoryHolder owner, InventoryType type) {
            this(owner, type.getDefaultSize(), type.getDefaultTitle());
            this.type = type;
        }

        public MinecraftInventory(InventoryHolder owner, InventoryType type, String title) {
            this(owner, type.getDefaultSize(), title);
            this.type = type;
        }

        public MinecraftInventory(InventoryHolder owner, int size) {
            this(owner, size, "Chest");
        }

        public MinecraftInventory(InventoryHolder owner, int size, String title) {
            this.maxStack = 64;
            Preconditions.checkArgument(title != null, "title cannot be null");
            this.items = NonNullList.withSize(size, ItemStack.EMPTY);
            this.title = title;
            this.viewers = new ArrayList();
            this.owner = owner;
            this.type = InventoryType.CHEST;
        }

        public int getContainerSize() {
            return this.items.size();
        }

        public ItemStack getItem(int i) {
            return (ItemStack) this.items.get(i);
        }

        public ItemStack removeItem(int i, int j) {
            ItemStack stack = this.getItem(i);

            if (stack == ItemStack.EMPTY) {
                return stack;
            } else {
                ItemStack result;

                if (stack.getCount() <= j) {
                    this.setItem(i, ItemStack.EMPTY);
                    result = stack;
                } else {
                    result = CraftItemStack.copyNMSStack(stack, j);
                    stack.shrink(j);
                }

                this.setChanged();
                return result;
            }
        }

        public ItemStack removeItemNoUpdate(int i) {
            ItemStack stack = this.getItem(i);

            if (stack == ItemStack.EMPTY) {
                return stack;
            } else {
                ItemStack result;

                if (stack.getCount() <= 1) {
                    this.setItem(i, (ItemStack) null);
                    result = stack;
                } else {
                    result = CraftItemStack.copyNMSStack(stack, 1);
                    stack.shrink(1);
                }

                return result;
            }
        }

        public void setItem(int i, ItemStack itemstack) {
            this.items.set(i, itemstack);
            if (itemstack != ItemStack.EMPTY && this.getMaxStackSize() > 0 && itemstack.getCount() > this.getMaxStackSize()) {
                itemstack.setCount(this.getMaxStackSize());
            }

        }

        public int getMaxStackSize() {
            return this.maxStack;
        }

        public void setMaxStackSize(int size) {
            this.maxStack = size;
        }

        public void setChanged() {}

        public boolean stillValid(Player entityhuman) {
            return true;
        }

        public List getContents() {
            return this.items;
        }

        public void onOpen(CraftHumanEntity who) {
            this.viewers.add(who);
        }

        public void onClose(CraftHumanEntity who) {
            this.viewers.remove(who);
        }

        public List getViewers() {
            return this.viewers;
        }

        public InventoryType getType() {
            return this.type;
        }

        public InventoryHolder getOwner() {
            return this.owner;
        }

        public boolean canPlaceItem(int i, ItemStack itemstack) {
            return true;
        }

        public void startOpen(Player entityHuman) {}

        public void stopOpen(Player entityHuman) {}

        public void clearContent() {
            this.items.clear();
        }

        public Location getLocation() {
            return null;
        }

        public String getTitle() {
            return this.title;
        }

        public boolean isEmpty() {
            Iterator iterator = this.items.iterator();

            while (iterator.hasNext()) {
                ItemStack itemstack = (ItemStack) iterator.next();

                if (!itemstack.isEmpty()) {
                    return false;
                }
            }

            return true;
        }
    }
}
