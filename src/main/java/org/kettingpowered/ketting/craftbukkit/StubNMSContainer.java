package org.kettingpowered.ketting.craftbukkit;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;

public class StubNMSContainer implements Container {
    public static final StubNMSContainer INSTANCE = new StubNMSContainer();
    private StubNMSContainer(){}
    
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
    public net.minecraft.world.item.ItemStack removeItem(int p_18942_, int p_18943_) {
        return net.minecraft.world.item.ItemStack.EMPTY;
    }

    @Override
    public net.minecraft.world.item.ItemStack removeItemNoUpdate(int p_18951_) {
        return net.minecraft.world.item.ItemStack.EMPTY;
    }

    @Override
    public void setItem(int p_18944_, net.minecraft.world.item.ItemStack p_18945_) {

    }

    @Override
    public void setChanged() {

    }

    @Override
    public boolean stillValid(Player p_18946_) {
        return false;
    }

    @Override
    public void clearContent() {

    }
}
