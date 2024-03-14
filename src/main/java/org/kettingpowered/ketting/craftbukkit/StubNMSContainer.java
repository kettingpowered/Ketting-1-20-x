package org.kettingpowered.ketting.craftbukkit;

import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.Container;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_20_R1.entity.CraftHumanEntity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.InventoryHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class StubNMSContainer extends Inventory {
    public static final StubNMSContainer INSTANCE = new StubNMSContainer();
    private StubNMSContainer(){
        super(StubNMSServerPlayer.INSTANCE);
    }
    
    @Override
    public int getContainerSize() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public net.minecraft.world.item.ItemStack getItem(int p_18941_) {
        return net.minecraft.world.item.ItemStack.EMPTY;
    }

    @Override
    public Component getName() {
        return super.getName();
    }

    @Override
    public ItemStack getArmor(int p_36053_) {
        return ItemStack.EMPTY;
    }

    @Override
    public void hurtArmor(DamageSource p_150073_, float p_150074_, int[] p_150075_) {
        super.hurtArmor(p_150073_, p_150074_, p_150075_);
    }

    @Override
    public void dropAll() {
        super.dropAll();
    }

    @Override
    public List<ItemStack> getContents() {
        return new ArrayList<>();
    }

    @Override
    public List<ItemStack> getArmorContents() {
        return new ArrayList<>();
    }

    @Override
    public InventoryHolder getOwner() {
        return CraftStubInventoryHolder.INSTANCE;
    }

    @Override
    public int getMaxStackSize() {
        return 0;
    }

    @Override
    public void setMaxStackSize(int size) {}

    @Override
    public Location getLocation() {
        return new Location(DedicatedServer.getServer().overworld().getWorld(), 0, 0, 0);
    }

    @Override
    public ItemStack getSelected() {
        return ItemStack.EMPTY;
    }

    @Override
    public int canHold(ItemStack itemstack) {
        return 0;
    }

    @Override
    public int getFreeSlot() {
        return 0;
    }

    @Override
    public void setPickedItem(ItemStack p_36013_) {}

    @Override
    public void pickSlot(int p_36039_) {}

    @Override
    public int findSlotMatchingItem(ItemStack p_36031_) {
        return 0;
    }

    @Override
    public int findSlotMatchingUnusedItem(ItemStack p_36044_) {
        return 0;
    }

    @Override
    public int getSuitableHotbarSlot() {
        return 0;
    }

    @Override
    public void swapPaint(double p_35989_) {}

    @Override
    public int clearOrCountMatchingItems(Predicate<ItemStack> p_36023_, int p_36024_, Container p_36025_) {
        return 0;
    }

    @Override
    public int getSlotWithRemainingSpace(ItemStack p_36051_) {
        return 0;
    }

    @Override
    public void tick() {}

    @Override
    public boolean add(ItemStack p_36055_) {
        return false;
    }

    @Override
    public boolean add(int p_36041_, ItemStack p_36042_) {
        return false;
    }

    @Override
    public void placeItemBackInInventory(ItemStack p_150080_) {}

    @Override
    public void placeItemBackInInventory(ItemStack p_150077_, boolean p_150078_) {}

    @Override
    public net.minecraft.world.item.ItemStack removeItem(int p_18942_, int p_18943_) {
        return net.minecraft.world.item.ItemStack.EMPTY;
    }

    @Override
    public void removeItem(ItemStack p_36058_) {}

    @Override
    public net.minecraft.world.item.ItemStack removeItemNoUpdate(int p_18951_) {
        return net.minecraft.world.item.ItemStack.EMPTY;
    }

    @Override
    public void setItem(int p_18944_, net.minecraft.world.item.ItemStack p_18945_) {}

    @Override
    public float getDestroySpeed(BlockState p_36021_) {
        return 0.0f;
    }

    @Override
    public ListTag save(ListTag p_36027_) {
        return p_36027_;
    }

    @Override
    public void load(ListTag p_36036_) {}

    @Override
    public void setChanged() {}

    @Override
    public int getTimesChanged() {
        return 0;
    }

    @Override
    public boolean stillValid(Player p_18946_) {
        return false;
    }

    @Override
    public boolean contains(ItemStack p_36064_) {
        return false;
    }

    @Override
    public boolean contains(TagKey<Item> p_204076_) {
        return false;
    }

    @Override
    public void replaceWith(Inventory p_36007_) {}

    @Override
    public void clearContent() {}

    @Override
    public void fillStackedContents(StackedContents p_36011_) {}

    @Override
    public ItemStack removeFromSelected(boolean p_182404_) {
        return ItemStack.EMPTY;
    }
}
