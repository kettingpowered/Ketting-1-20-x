package org.bukkit.craftbukkit.v1_20_R2.inventory;

import com.google.common.base.Preconditions;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.inventory.MerchantContainer;
import net.minecraft.world.inventory.PlayerEnderChestContainer;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.BarrelBlockEntity;
import net.minecraft.world.level.block.entity.BlastFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.BrewingStandBlockEntity;
import net.minecraft.world.level.block.entity.ChiseledBookShelfBlockEntity;
import net.minecraft.world.level.block.entity.DispenserBlockEntity;
import net.minecraft.world.level.block.entity.DropperBlockEntity;
import net.minecraft.world.level.block.entity.Hopper;
import net.minecraft.world.level.block.entity.JukeboxBlockEntity;
import net.minecraft.world.level.block.entity.LecternBlockEntity;
import net.minecraft.world.level.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.world.level.block.entity.SmokerBlockEntity;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_20_R2.util.CraftLegacy;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public class CraftInventory implements Inventory {

    protected final Container inventory;

    public CraftInventory(Container inventory) {
        this.inventory = inventory;
    }

    public Container getInventory() {
        return this.inventory;
    }

    public int getSize() {
        return this.getInventory().getContainerSize();
    }

    public ItemStack getItem(int index) {
        net.minecraft.world.item.ItemStack item = this.getInventory().getItem(index);

        return item.isEmpty() ? null : CraftItemStack.asCraftMirror(item);
    }

    protected ItemStack[] asCraftMirror(List mcItems) {
        int size = mcItems.size();
        ItemStack[] items = new ItemStack[size];

        for (int i = 0; i < size; ++i) {
            net.minecraft.world.item.ItemStack mcItem = (net.minecraft.world.item.ItemStack) mcItems.get(i);

            items[i] = mcItem.isEmpty() ? null : CraftItemStack.asCraftMirror(mcItem);
        }

        return items;
    }

    public ItemStack[] getStorageContents() {
        return this.getContents();
    }

    public void setStorageContents(ItemStack[] items) throws IllegalArgumentException {
        this.setContents(items);
    }

    public ItemStack[] getContents() {
        List mcItems = this.getInventory().getContents();

        return this.asCraftMirror(mcItems);
    }

    public void setContents(ItemStack[] items) {
        Preconditions.checkArgument(items.length <= this.getSize(), "Invalid inventory size (%s); expected %s or less", items.length, this.getSize());

        for (int i = 0; i < this.getSize(); ++i) {
            if (i >= items.length) {
                this.setItem(i, (ItemStack) null);
            } else {
                this.setItem(i, items[i]);
            }
        }

    }

    public void setItem(int index, ItemStack item) {
        this.getInventory().setItem(index, CraftItemStack.asNMSCopy(item));
    }

    public boolean contains(Material material) {
        Preconditions.checkArgument(material != null, "Material cannot be null");
        material = CraftLegacy.fromLegacy(material);
        ItemStack[] aitemstack;
        int i = (aitemstack = this.getStorageContents()).length;

        for (int j = 0; j < i; ++j) {
            ItemStack item = aitemstack[j];

            if (item != null && item.getType() == material) {
                return true;
            }
        }

        return false;
    }

    public boolean contains(ItemStack item) {
        if (item == null) {
            return false;
        } else {
            ItemStack[] aitemstack;
            int i = (aitemstack = this.getStorageContents()).length;

            for (int j = 0; j < i; ++j) {
                ItemStack i = aitemstack[j];

                if (item.equals(i)) {
                    return true;
                }
            }

            return false;
        }
    }

    public boolean contains(Material material, int amount) {
        Preconditions.checkArgument(material != null, "Material cannot be null");
        material = CraftLegacy.fromLegacy(material);
        if (amount <= 0) {
            return true;
        } else {
            ItemStack[] aitemstack;
            int i = (aitemstack = this.getStorageContents()).length;

            for (int j = 0; j < i; ++j) {
                ItemStack item = aitemstack[j];

                if (item != null && item.getType() == material && (amount -= item.getAmount()) <= 0) {
                    return true;
                }
            }

            return false;
        }
    }

    public boolean contains(ItemStack item, int amount) {
        if (item == null) {
            return false;
        } else if (amount <= 0) {
            return true;
        } else {
            ItemStack[] aitemstack;
            int i = (aitemstack = this.getStorageContents()).length;

            for (int j = 0; j < i; ++j) {
                ItemStack i = aitemstack[j];

                if (item.equals(i)) {
                    --amount;
                    if (amount <= 0) {
                        return true;
                    }
                }
            }

            return false;
        }
    }

    public boolean containsAtLeast(ItemStack item, int amount) {
        if (item == null) {
            return false;
        } else if (amount <= 0) {
            return true;
        } else {
            ItemStack[] aitemstack;
            int i = (aitemstack = this.getStorageContents()).length;

            for (int j = 0; j < i; ++j) {
                ItemStack i = aitemstack[j];

                if (item.isSimilar(i) && (amount -= i.getAmount()) <= 0) {
                    return true;
                }
            }

            return false;
        }
    }

    public HashMap all(Material material) {
        Preconditions.checkArgument(material != null, "Material cannot be null");
        material = CraftLegacy.fromLegacy(material);
        HashMap slots = new HashMap();
        ItemStack[] inventory = this.getStorageContents();

        for (int i = 0; i < inventory.length; ++i) {
            ItemStack item = inventory[i];

            if (item != null && item.getType() == material) {
                slots.put(i, item);
            }
        }

        return slots;
    }

    public HashMap all(ItemStack item) {
        HashMap slots = new HashMap();

        if (item != null) {
            ItemStack[] inventory = this.getStorageContents();

            for (int i = 0; i < inventory.length; ++i) {
                if (item.equals(inventory[i])) {
                    slots.put(i, inventory[i]);
                }
            }
        }

        return slots;
    }

    public int first(Material material) {
        Preconditions.checkArgument(material != null, "Material cannot be null");
        material = CraftLegacy.fromLegacy(material);
        ItemStack[] inventory = this.getStorageContents();

        for (int i = 0; i < inventory.length; ++i) {
            ItemStack item = inventory[i];

            if (item != null && item.getType() == material) {
                return i;
            }
        }

        return -1;
    }

    public int first(ItemStack item) {
        return this.first(item, true);
    }

    private int first(ItemStack item, boolean withAmount) {
        if (item == null) {
            return -1;
        } else {
            ItemStack[] inventory = this.getStorageContents();
            int i = 0;

            while (true) {
                if (i >= inventory.length) {
                    return -1;
                }

                if (inventory[i] != null) {
                    if (withAmount) {
                        if (item.equals(inventory[i])) {
                            break;
                        }
                    } else if (item.isSimilar(inventory[i])) {
                        break;
                    }
                }

                ++i;
            }

            return i;
        }
    }

    public int firstEmpty() {
        ItemStack[] inventory = this.getStorageContents();

        for (int i = 0; i < inventory.length; ++i) {
            if (inventory[i] == null) {
                return i;
            }
        }

        return -1;
    }

    public boolean isEmpty() {
        return this.inventory.isEmpty();
    }

    public int firstPartial(Material material) {
        Preconditions.checkArgument(material != null, "Material cannot be null");
        material = CraftLegacy.fromLegacy(material);
        ItemStack[] inventory = this.getStorageContents();

        for (int i = 0; i < inventory.length; ++i) {
            ItemStack item = inventory[i];

            if (item != null && item.getType() == material && item.getAmount() < item.getMaxStackSize()) {
                return i;
            }
        }

        return -1;
    }

    private int firstPartial(ItemStack item) {
        ItemStack[] inventory = this.getStorageContents();
        CraftItemStack filteredItem = CraftItemStack.asCraftCopy(item);

        if (item == null) {
            return -1;
        } else {
            for (int i = 0; i < inventory.length; ++i) {
                ItemStack cItem = inventory[i];

                if (cItem != null && cItem.getAmount() < cItem.getMaxStackSize() && cItem.isSimilar(filteredItem)) {
                    return i;
                }
            }

            return -1;
        }
    }

    public HashMap addItem(ItemStack... items) {
        Preconditions.checkArgument(items != null, "items cannot be null");
        HashMap leftover = new HashMap();
        int i = 0;

        while (i < items.length) {
            ItemStack item = items[i];

            Preconditions.checkArgument(item != null, "ItemStack cannot be null");

            while (true) {
                int firstPartial = this.firstPartial(item);

                if (firstPartial == -1) {
                    int firstFree = this.firstEmpty();

                    if (firstFree == -1) {
                        leftover.put(i, item);
                    } else {
                        if (item.getAmount() > this.getMaxItemStack()) {
                            CraftItemStack stack = CraftItemStack.asCraftCopy(item);

                            stack.setAmount(this.getMaxItemStack());
                            this.setItem(firstFree, stack);
                            item.setAmount(item.getAmount() - this.getMaxItemStack());
                            continue;
                        }

                        this.setItem(firstFree, item);
                    }
                } else {
                    ItemStack partialItem = this.getItem(firstPartial);
                    int amount = item.getAmount();
                    int partialAmount = partialItem.getAmount();
                    int maxAmount = partialItem.getMaxStackSize();

                    if (amount + partialAmount > maxAmount) {
                        partialItem.setAmount(maxAmount);
                        this.setItem(firstPartial, partialItem);
                        item.setAmount(amount + partialAmount - maxAmount);
                        continue;
                    }

                    partialItem.setAmount(amount + partialAmount);
                    this.setItem(firstPartial, partialItem);
                }

                ++i;
                break;
            }
        }

        return leftover;
    }

    public HashMap removeItem(ItemStack... items) {
        Preconditions.checkArgument(items != null, "items cannot be null");
        HashMap leftover = new HashMap();
        int i = 0;

        while (i < items.length) {
            ItemStack item = items[i];

            Preconditions.checkArgument(item != null, "ItemStack cannot be null");
            int toDelete = item.getAmount();

            while (true) {
                int first = this.first(item, false);

                if (first == -1) {
                    item.setAmount(toDelete);
                    leftover.put(i, item);
                } else {
                    ItemStack itemStack = this.getItem(first);
                    int amount = itemStack.getAmount();

                    if (amount <= toDelete) {
                        toDelete -= amount;
                        this.clear(first);
                    } else {
                        itemStack.setAmount(amount - toDelete);
                        this.setItem(first, itemStack);
                        toDelete = 0;
                    }

                    if (toDelete > 0) {
                        continue;
                    }
                }

                ++i;
                break;
            }
        }

        return leftover;
    }

    private int getMaxItemStack() {
        return this.getInventory().getMaxStackSize();
    }

    public void remove(Material material) {
        Preconditions.checkArgument(material != null, "Material cannot be null");
        material = CraftLegacy.fromLegacy(material);
        ItemStack[] items = this.getStorageContents();

        for (int i = 0; i < items.length; ++i) {
            if (items[i] != null && items[i].getType() == material) {
                this.clear(i);
            }
        }

    }

    public void remove(ItemStack item) {
        ItemStack[] items = this.getStorageContents();

        for (int i = 0; i < items.length; ++i) {
            if (items[i] != null && items[i].equals(item)) {
                this.clear(i);
            }
        }

    }

    public void clear(int index) {
        this.setItem(index, (ItemStack) null);
    }

    public void clear() {
        for (int i = 0; i < this.getSize(); ++i) {
            this.clear(i);
        }

    }

    public ListIterator iterator() {
        return new InventoryIterator(this);
    }

    public ListIterator iterator(int index) {
        if (index < 0) {
            index += this.getSize() + 1;
        }

        return new InventoryIterator(this, index);
    }

    public List getViewers() {
        return this.inventory.getViewers();
    }

    public InventoryType getType() {
        return this.inventory instanceof CraftingContainer ? (this.inventory.getContainerSize() >= 9 ? InventoryType.WORKBENCH : InventoryType.CRAFTING) : (this.inventory instanceof net.minecraft.world.entity.player.Inventory ? InventoryType.PLAYER : (this.inventory instanceof DropperBlockEntity ? InventoryType.DROPPER : (this.inventory instanceof DispenserBlockEntity ? InventoryType.DISPENSER : (this.inventory instanceof BlastFurnaceBlockEntity ? InventoryType.BLAST_FURNACE : (this.inventory instanceof SmokerBlockEntity ? InventoryType.SMOKER : (this.inventory instanceof AbstractFurnaceBlockEntity ? InventoryType.FURNACE : (this instanceof CraftInventoryEnchanting ? InventoryType.ENCHANTING : (this.inventory instanceof BrewingStandBlockEntity ? InventoryType.BREWING : (this.inventory instanceof CraftInventoryCustom.MinecraftInventory ? ((CraftInventoryCustom.MinecraftInventory) this.inventory).getType() : (this.inventory instanceof PlayerEnderChestContainer ? InventoryType.ENDER_CHEST : (this.inventory instanceof MerchantContainer ? InventoryType.MERCHANT : (this instanceof CraftInventoryBeacon ? InventoryType.BEACON : (this instanceof CraftInventoryAnvil ? InventoryType.ANVIL : (this instanceof CraftInventorySmithing ? InventoryType.SMITHING : (this.inventory instanceof Hopper ? InventoryType.HOPPER : (this.inventory instanceof ShulkerBoxBlockEntity ? InventoryType.SHULKER_BOX : (this.inventory instanceof BarrelBlockEntity ? InventoryType.BARREL : (this.inventory instanceof LecternBlockEntity.LecternInventory ? InventoryType.LECTERN : (this.inventory instanceof ChiseledBookShelfBlockEntity ? InventoryType.CHISELED_BOOKSHELF : (this instanceof CraftInventoryLoom ? InventoryType.LOOM : (this instanceof CraftInventoryCartography ? InventoryType.CARTOGRAPHY : (this instanceof CraftInventoryGrindstone ? InventoryType.GRINDSTONE : (this instanceof CraftInventoryStonecutter ? InventoryType.STONECUTTER : (!(this.inventory instanceof ComposterBlock.EmptyContainer) && !(this.inventory instanceof ComposterBlock.InputContainer) && !(this.inventory instanceof ComposterBlock.OutputContainer) ? (this.inventory instanceof JukeboxBlockEntity ? InventoryType.JUKEBOX : InventoryType.CHEST) : InventoryType.COMPOSTER))))))))))))))))))))))));
    }

    public InventoryHolder getHolder() {
        return this.inventory.getOwner();
    }

    public int getMaxStackSize() {
        return this.inventory.getMaxStackSize();
    }

    public void setMaxStackSize(int size) {
        this.inventory.setMaxStackSize(size);
    }

    public int hashCode() {
        return this.inventory.hashCode();
    }

    public boolean equals(Object obj) {
        return obj instanceof CraftInventory && ((CraftInventory) obj).inventory.equals(this.inventory);
    }

    public Location getLocation() {
        return this.inventory.getLocation();
    }
}
