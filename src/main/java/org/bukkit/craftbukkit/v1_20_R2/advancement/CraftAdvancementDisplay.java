package org.bukkit.craftbukkit.v1_20_R2.advancement;

import net.minecraft.advancements.DisplayInfo;
import org.bukkit.advancement.AdvancementDisplay;
import org.bukkit.advancement.AdvancementDisplayType;
import org.bukkit.craftbukkit.v1_20_R2.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_20_R2.util.CraftChatMessage;
import org.bukkit.inventory.ItemStack;

public class CraftAdvancementDisplay implements AdvancementDisplay {

    private final DisplayInfo handle;

    public CraftAdvancementDisplay(DisplayInfo handle) {
        this.handle = handle;
    }

    public DisplayInfo getHandle() {
        return this.handle;
    }

    public String getTitle() {
        return CraftChatMessage.fromComponent(this.handle.getTitle());
    }

    public String getDescription() {
        return CraftChatMessage.fromComponent(this.handle.getDescription());
    }

    public ItemStack getIcon() {
        return CraftItemStack.asBukkitCopy(this.handle.getIcon());
    }

    public boolean shouldShowToast() {
        return this.handle.shouldShowToast();
    }

    public boolean shouldAnnounceChat() {
        return this.handle.shouldAnnounceChat();
    }

    public boolean isHidden() {
        return this.handle.isHidden();
    }

    public float getX() {
        return this.handle.getX();
    }

    public float getY() {
        return this.handle.getY();
    }

    public AdvancementDisplayType getType() {
        return AdvancementDisplayType.values()[this.handle.getFrame().ordinal()];
    }
}
