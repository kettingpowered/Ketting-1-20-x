package org.bukkit.craftbukkit.v1_20_R2.inventory;

import com.google.common.base.Preconditions;
import net.minecraft.network.protocol.game.ClientboundOpenScreenPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import org.bukkit.GameMode;
import org.bukkit.craftbukkit.v1_20_R2.entity.CraftHumanEntity;
import org.bukkit.craftbukkit.v1_20_R2.util.CraftChatMessage;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

public class CraftInventoryView extends InventoryView {

    private final AbstractContainerMenu container;
    private final CraftHumanEntity player;
    private final CraftInventory viewing;
    private final String originalTitle;
    private String title;

    public CraftInventoryView(HumanEntity player, Inventory viewing, AbstractContainerMenu container) {
        this.player = (CraftHumanEntity) player;
        this.viewing = (CraftInventory) viewing;
        this.container = container;
        this.originalTitle = CraftChatMessage.fromComponent(container.getTitle());
        this.title = this.originalTitle;
    }

    public Inventory getTopInventory() {
        return this.viewing;
    }

    public Inventory getBottomInventory() {
        return this.player.getInventory();
    }

    public HumanEntity getPlayer() {
        return this.player;
    }

    public InventoryType getType() {
        InventoryType type = this.viewing.getType();

        return type == InventoryType.CRAFTING && this.player.getGameMode() == GameMode.CREATIVE ? InventoryType.CREATIVE : type;
    }

    public void setItem(int slot, ItemStack item) {
        net.minecraft.world.item.ItemStack stack = CraftItemStack.asNMSCopy(item);

        if (slot >= 0) {
            this.container.getSlot(slot).set(stack);
        } else {
            this.player.getHandle().drop(stack, false);
        }

    }

    public ItemStack getItem(int slot) {
        return slot < 0 ? null : CraftItemStack.asCraftMirror(this.container.getSlot(slot).getItem());
    }

    public String getTitle() {
        return this.title;
    }

    public String getOriginalTitle() {
        return this.originalTitle;
    }

    public void setTitle(String title) {
        sendInventoryTitleChange(this, title);
        this.title = title;
    }

    public boolean isInTop(int rawSlot) {
        return rawSlot < this.viewing.getSize();
    }

    public AbstractContainerMenu getHandle() {
        return this.container;
    }

    public static void sendInventoryTitleChange(InventoryView view, String title) {
        Preconditions.checkArgument(view != null, "InventoryView cannot be null");
        Preconditions.checkArgument(title != null, "Title cannot be null");
        Preconditions.checkArgument(view.getPlayer() instanceof Player, "NPCs are not currently supported for this function");
        Preconditions.checkArgument(view.getTopInventory().getType().isCreatable(), "Only creatable inventories can have their title changed");
        ServerPlayer entityPlayer = (ServerPlayer) ((CraftHumanEntity) view.getPlayer()).getHandle();
        int containerId = entityPlayer.containerMenu.containerId;
        MenuType windowType = CraftContainer.getNotchInventoryType(view.getTopInventory());

        entityPlayer.connection.send(new ClientboundOpenScreenPacket(containerId, windowType, CraftChatMessage.fromString(title)[0]));
        ((Player) view.getPlayer()).updateInventory();
    }
}
