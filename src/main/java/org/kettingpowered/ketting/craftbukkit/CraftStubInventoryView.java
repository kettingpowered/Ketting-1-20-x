package org.kettingpowered.ketting.craftbukkit;

import com.mojang.authlib.GameProfile;
import net.minecraft.core.BlockPos;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.world.entity.player.Player;
import org.bukkit.craftbukkit.v1_20_R1.entity.CraftHumanEntity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class CraftStubInventoryView extends InventoryView {
    public static final CraftStubInventoryView INSTANCE = new CraftStubInventoryView();
    private CraftStubInventoryView(){}
    private static final HumanEntity StubEntity = 
            new CraftHumanEntity(
                    DedicatedServer.getServer().server, 
                    new Player(
                            DedicatedServer.getServer().overworld(), 
                            BlockPos.ZERO,
                            0.0F,
                            new GameProfile(new UUID(0, 0), "test-mock-player")
                    ) {
                        @Override
                        public boolean isSpectator() {
                            return false;
                        }

                        @Override
                        public boolean isCreative() {
                            return false;
                        }
                    });
    @Override
    public @NotNull Inventory getTopInventory() {
        return CraftStubInventory.INSTANCE;
    }

    @Override
    public @NotNull Inventory getBottomInventory() {
        return CraftStubInventory.INSTANCE;
    }

    @Override
    public @NotNull HumanEntity getPlayer() {
        return StubEntity;
    }

    @Override
    public @NotNull InventoryType getType() {
        return InventoryType.Stub;
    }

    @Override
    public @NotNull String getTitle() {
        return getType().getDefaultTitle();
    }

    @Override
    public @NotNull String getOriginalTitle() {
        return getType().getDefaultTitle();
    }

    @Override
    public void setTitle(@NotNull String title) {}
}
