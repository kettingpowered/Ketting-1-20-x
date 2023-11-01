package org.bukkit.craftbukkit.v1_20_R2.inventory;

import com.google.common.base.Preconditions;
import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket;
import net.minecraft.network.protocol.game.ClientboundSetCarriedItemPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import org.bukkit.craftbukkit.v1_20_R2.entity.CraftPlayer;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class CraftInventoryPlayer extends CraftInventory implements PlayerInventory, EntityEquipment {

    private static volatile int[] $SWITCH_TABLE$org$bukkit$inventory$EquipmentSlot;

    public CraftInventoryPlayer(Inventory inventory) {
        super(inventory);
    }

    public Inventory getInventory() {
        return (Inventory) this.inventory;
    }

    public ItemStack[] getStorageContents() {
        return this.asCraftMirror(this.getInventory().items);
    }

    public ItemStack getItemInMainHand() {
        return CraftItemStack.asCraftMirror(this.getInventory().getSelected());
    }

    public void setItemInMainHand(ItemStack item) {
        this.setItem(this.getHeldItemSlot(), item);
    }

    public void setItemInMainHand(ItemStack item, boolean silent) {
        this.setItemInMainHand(item);
    }

    public ItemStack getItemInOffHand() {
        return CraftItemStack.asCraftMirror((net.minecraft.world.item.ItemStack) this.getInventory().offhand.get(0));
    }

    public void setItemInOffHand(ItemStack item) {
        ItemStack[] extra = this.getExtraContents();

        extra[0] = item;
        this.setExtraContents(extra);
    }

    public void setItemInOffHand(ItemStack item, boolean silent) {
        this.setItemInOffHand(item);
    }

    public ItemStack getItemInHand() {
        return this.getItemInMainHand();
    }

    public void setItemInHand(ItemStack stack) {
        this.setItemInMainHand(stack);
    }

    public void setItem(int index, ItemStack item) {
        super.setItem(index, item);
        if (this.getHolder() != null) {
            ServerPlayer player = ((CraftPlayer) this.getHolder()).getHandle();

            if (player.connection != null) {
                if (index < Inventory.getSelectionSize()) {
                    index += 36;
                } else if (index > 39) {
                    index += 5;
                } else if (index > 35) {
                    index = 8 - (index - 36);
                }

                player.connection.send(new ClientboundContainerSetSlotPacket(player.inventoryMenu.containerId, player.inventoryMenu.incrementStateId(), index, CraftItemStack.asNMSCopy(item)));
            }
        }
    }

    public void setItem(EquipmentSlot slot, ItemStack item) {
        Preconditions.checkArgument(slot != null, "slot must not be null");
        switch ($SWITCH_TABLE$org$bukkit$inventory$EquipmentSlot()[slot.ordinal()]) {
            case 1:
                this.setItemInMainHand(item);
                break;
            case 2:
                this.setItemInOffHand(item);
                break;
            case 3:
                this.setBoots(item);
                break;
            case 4:
                this.setLeggings(item);
                break;
            case 5:
                this.setChestplate(item);
                break;
            case 6:
                this.setHelmet(item);
                break;
            default:
                throw new IllegalArgumentException("Not implemented. This is a bug");
        }

    }

    public void setItem(EquipmentSlot slot, ItemStack item, boolean silent) {
        this.setItem(slot, item);
    }

    public ItemStack getItem(EquipmentSlot slot) {
        Preconditions.checkArgument(slot != null, "slot must not be null");
        switch ($SWITCH_TABLE$org$bukkit$inventory$EquipmentSlot()[slot.ordinal()]) {
            case 1:
                return this.getItemInMainHand();
            case 2:
                return this.getItemInOffHand();
            case 3:
                return this.getBoots();
            case 4:
                return this.getLeggings();
            case 5:
                return this.getChestplate();
            case 6:
                return this.getHelmet();
            default:
                throw new IllegalArgumentException("Not implemented. This is a bug");
        }
    }

    public int getHeldItemSlot() {
        return this.getInventory().selected;
    }

    public void setHeldItemSlot(int slot) {
        Preconditions.checkArgument(slot >= 0 && slot < Inventory.getSelectionSize(), "Slot (%s) is not between 0 and %s inclusive", slot, Inventory.getSelectionSize() - 1);
        this.getInventory().selected = slot;
        ((CraftPlayer) this.getHolder()).getHandle().connection.send(new ClientboundSetCarriedItemPacket(slot));
    }

    public ItemStack getHelmet() {
        return this.getItem(this.getSize() - 2);
    }

    public ItemStack getChestplate() {
        return this.getItem(this.getSize() - 3);
    }

    public ItemStack getLeggings() {
        return this.getItem(this.getSize() - 4);
    }

    public ItemStack getBoots() {
        return this.getItem(this.getSize() - 5);
    }

    public void setHelmet(ItemStack helmet) {
        this.setItem(this.getSize() - 2, helmet);
    }

    public void setHelmet(ItemStack helmet, boolean silent) {
        this.setHelmet(helmet);
    }

    public void setChestplate(ItemStack chestplate) {
        this.setItem(this.getSize() - 3, chestplate);
    }

    public void setChestplate(ItemStack chestplate, boolean silent) {
        this.setChestplate(chestplate);
    }

    public void setLeggings(ItemStack leggings) {
        this.setItem(this.getSize() - 4, leggings);
    }

    public void setLeggings(ItemStack leggings, boolean silent) {
        this.setLeggings(leggings);
    }

    public void setBoots(ItemStack boots) {
        this.setItem(this.getSize() - 5, boots);
    }

    public void setBoots(ItemStack boots, boolean silent) {
        this.setBoots(boots);
    }

    public ItemStack[] getArmorContents() {
        return this.asCraftMirror(this.getInventory().armor);
    }

    private void setSlots(ItemStack[] items, int baseSlot, int length) {
        if (items == null) {
            items = new ItemStack[length];
        }

        Preconditions.checkArgument(items.length <= length, "items.length must be <= %s", length);

        for (int i = 0; i < length; ++i) {
            if (i >= items.length) {
                this.setItem(baseSlot + i, (ItemStack) null);
            } else {
                this.setItem(baseSlot + i, items[i]);
            }
        }

    }

    public void setStorageContents(ItemStack[] items) throws IllegalArgumentException {
        this.setSlots(items, 0, this.getInventory().items.size());
    }

    public void setArmorContents(ItemStack[] items) {
        this.setSlots(items, this.getInventory().items.size(), this.getInventory().armor.size());
    }

    public ItemStack[] getExtraContents() {
        return this.asCraftMirror(this.getInventory().offhand);
    }

    public void setExtraContents(ItemStack[] items) {
        this.setSlots(items, this.getInventory().items.size() + this.getInventory().armor.size(), this.getInventory().offhand.size());
    }

    public HumanEntity getHolder() {
        return (HumanEntity) this.inventory.getOwner();
    }

    public float getItemInHandDropChance() {
        return this.getItemInMainHandDropChance();
    }

    public void setItemInHandDropChance(float chance) {
        this.setItemInMainHandDropChance(chance);
    }

    public float getItemInMainHandDropChance() {
        return 1.0F;
    }

    public void setItemInMainHandDropChance(float chance) {
        throw new UnsupportedOperationException("Cannot set drop chance for PlayerInventory");
    }

    public float getItemInOffHandDropChance() {
        return 1.0F;
    }

    public void setItemInOffHandDropChance(float chance) {
        throw new UnsupportedOperationException("Cannot set drop chance for PlayerInventory");
    }

    public float getHelmetDropChance() {
        return 1.0F;
    }

    public void setHelmetDropChance(float chance) {
        throw new UnsupportedOperationException("Cannot set drop chance for PlayerInventory");
    }

    public float getChestplateDropChance() {
        return 1.0F;
    }

    public void setChestplateDropChance(float chance) {
        throw new UnsupportedOperationException("Cannot set drop chance for PlayerInventory");
    }

    public float getLeggingsDropChance() {
        return 1.0F;
    }

    public void setLeggingsDropChance(float chance) {
        throw new UnsupportedOperationException("Cannot set drop chance for PlayerInventory");
    }

    public float getBootsDropChance() {
        return 1.0F;
    }

    public void setBootsDropChance(float chance) {
        throw new UnsupportedOperationException("Cannot set drop chance for PlayerInventory");
    }

    static int[] $SWITCH_TABLE$org$bukkit$inventory$EquipmentSlot() {
        int[] aint = CraftInventoryPlayer.$SWITCH_TABLE$org$bukkit$inventory$EquipmentSlot;

        if (aint != null) {
            return aint;
        } else {
            int[] aint1 = new int[EquipmentSlot.values().length];

            try {
                aint1[EquipmentSlot.CHEST.ordinal()] = 5;
            } catch (NoSuchFieldError nosuchfielderror) {
                ;
            }

            try {
                aint1[EquipmentSlot.FEET.ordinal()] = 3;
            } catch (NoSuchFieldError nosuchfielderror1) {
                ;
            }

            try {
                aint1[EquipmentSlot.HAND.ordinal()] = 1;
            } catch (NoSuchFieldError nosuchfielderror2) {
                ;
            }

            try {
                aint1[EquipmentSlot.HEAD.ordinal()] = 6;
            } catch (NoSuchFieldError nosuchfielderror3) {
                ;
            }

            try {
                aint1[EquipmentSlot.LEGS.ordinal()] = 4;
            } catch (NoSuchFieldError nosuchfielderror4) {
                ;
            }

            try {
                aint1[EquipmentSlot.OFF_HAND.ordinal()] = 2;
            } catch (NoSuchFieldError nosuchfielderror5) {
                ;
            }

            CraftInventoryPlayer.$SWITCH_TABLE$org$bukkit$inventory$EquipmentSlot = aint1;
            return aint1;
        }
    }
}
