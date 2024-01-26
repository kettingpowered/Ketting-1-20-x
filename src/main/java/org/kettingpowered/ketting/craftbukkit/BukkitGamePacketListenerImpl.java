package org.kettingpowered.ketting.craftbukkit;

import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.PacketUtils;
import net.minecraft.network.protocol.game.*;
import net.minecraft.resources.ResourceLocation;

public class BukkitGamePacketListenerImpl implements net.minecraft.network.protocol.game.ServerGamePacketListener {
    net.minecraft.server.network.ServerGamePacketListenerImpl listener;
    public BukkitGamePacketListenerImpl(net.minecraft.server.network.ServerGamePacketListenerImpl listener){
        this.listener = listener;
    }

    @Override
    public void onDisconnect(Component p_130552_) {}

    @Override
    public boolean isAcceptingMessages() {
        return true;
    }

    @Override
    public void handleAnimate(ServerboundSwingPacket p_133781_) {}

    @Override
    public void handleChat(ServerboundChatPacket p_133743_) {}

    @Override
    public void handleChatCommand(ServerboundChatCommandPacket p_237920_) {}

    @Override
    public void handleChatAck(ServerboundChatAckPacket p_242214_) {}

    @Override
    public void handleClientCommand(ServerboundClientCommandPacket p_133744_) {}

    @Override
    public void handleClientInformation(ServerboundClientInformationPacket p_133745_) {}

    @Override
    public void handleContainerButtonClick(ServerboundContainerButtonClickPacket p_133748_) {}

    @Override
    public void handleContainerClick(ServerboundContainerClickPacket p_133749_) {}

    @Override
    public void handlePlaceRecipe(ServerboundPlaceRecipePacket p_133762_) {}

    @Override
    public void handleContainerClose(ServerboundContainerClosePacket p_133750_) {}

    private static final ResourceLocation CUSTOM_REGISTER = new ResourceLocation("register");
    private static final ResourceLocation CUSTOM_UNREGISTER = new ResourceLocation("unregister");
    @Override
    public void handleCustomPayload(ServerboundCustomPayloadPacket p_9860_) {
        if (p_9860_.identifier.equals(CUSTOM_REGISTER)) {
            try {
                String channels = p_9860_.data.toString(com.google.common.base.Charsets.UTF_8);
                for (String channel : channels.split("\0")) {
                    if (channel == null || channel.isEmpty() || channel.isBlank()){
                        PacketUtils.LOGGER.warn("Ignoring channel \""+channel+"\" for bukkit, because it is obviously wrong");
                        continue;
                    }
                    listener.getCraftPlayer().addChannel(channel);
                }
            } catch (Exception ex) {
                PacketUtils.LOGGER.error("Couldn\'t register custom payload", ex);
                listener.disconnect("Invalid payload REGISTER!");
            }
        } else if (p_9860_.identifier.equals(CUSTOM_UNREGISTER)) {
            try {
                String channels = p_9860_.data.toString(com.google.common.base.Charsets.UTF_8);
                for (String channel : channels.split("\0")) {
                    listener.getCraftPlayer().removeChannel(channel);
                }
            } catch (Exception ex) {
                PacketUtils.LOGGER.error("Couldn\'t unregister custom payload", ex);
                listener.disconnect("Invalid payload UNREGISTER!");
            }
        } else {
            try {
                byte[] data = new byte[p_9860_.data.readableBytes()];
                p_9860_.data.readBytes(data);
                listener.cserver.getMessenger().dispatchIncomingMessage(listener.player.getBukkitEntity(), p_9860_.identifier.toString(), data);
            } catch (Exception ex) {
                PacketUtils.LOGGER.error("Couldn\'t dispatch custom payload", ex);
                listener.disconnect("Invalid custom payload!");
            }
        }

    }

    @Override
    public void handleInteract(ServerboundInteractPacket p_133754_) {}

    @Override
    public void handleKeepAlive(ServerboundKeepAlivePacket p_133756_) {}

    @Override
    public void handleMovePlayer(ServerboundMovePlayerPacket p_133758_) {}

    @Override
    public void handlePong(ServerboundPongPacket p_179536_) {}

    @Override
    public void handlePlayerAbilities(ServerboundPlayerAbilitiesPacket p_133763_) {}

    @Override
    public void handlePlayerAction(ServerboundPlayerActionPacket p_133764_) {}

    @Override
    public void handlePlayerCommand(ServerboundPlayerCommandPacket p_133765_) {}

    @Override
    public void handlePlayerInput(ServerboundPlayerInputPacket p_133766_) {}

    @Override
    public void handleSetCarriedItem(ServerboundSetCarriedItemPacket p_133774_) {}

    @Override
    public void handleSetCreativeModeSlot(ServerboundSetCreativeModeSlotPacket p_133777_) {}

    @Override
    public void handleSignUpdate(ServerboundSignUpdatePacket p_133780_) {}

    @Override
    public void handleUseItemOn(ServerboundUseItemOnPacket p_133783_) {}

    @Override
    public void handleUseItem(ServerboundUseItemPacket p_133784_) {}

    @Override
    public void handleTeleportToEntityPacket(ServerboundTeleportToEntityPacket p_133782_) {}

    @Override
    public void handleResourcePackResponse(ServerboundResourcePackPacket p_133770_) {}

    @Override
    public void handlePaddleBoat(ServerboundPaddleBoatPacket p_133760_) {}

    @Override
    public void handleMoveVehicle(ServerboundMoveVehiclePacket p_133759_) {}

    @Override
    public void handleAcceptTeleportPacket(ServerboundAcceptTeleportationPacket p_133740_) {}

    @Override
    public void handleRecipeBookSeenRecipePacket(ServerboundRecipeBookSeenRecipePacket p_133768_) {}

    @Override
    public void handleRecipeBookChangeSettingsPacket(ServerboundRecipeBookChangeSettingsPacket p_133767_) {}

    @Override
    public void handleSeenAdvancements(ServerboundSeenAdvancementsPacket p_133771_) {}

    @Override
    public void handleCustomCommandSuggestions(ServerboundCommandSuggestionPacket p_133746_) {}

    @Override
    public void handleSetCommandBlock(ServerboundSetCommandBlockPacket p_133775_) {}

    @Override
    public void handleSetCommandMinecart(ServerboundSetCommandMinecartPacket p_133776_) {}

    @Override
    public void handlePickItem(ServerboundPickItemPacket p_133761_) {}

    @Override
    public void handleRenameItem(ServerboundRenameItemPacket p_133769_) {}

    @Override
    public void handleSetBeaconPacket(ServerboundSetBeaconPacket p_133773_) {}

    @Override
    public void handleSetStructureBlock(ServerboundSetStructureBlockPacket p_133779_) {}

    @Override
    public void handleSelectTrade(ServerboundSelectTradePacket p_133772_) {}

    @Override
    public void handleEditBook(ServerboundEditBookPacket p_133752_) {}

    @Override
    public void handleEntityTagQuery(ServerboundEntityTagQuery p_133753_) {}

    @Override
    public void handleBlockEntityTagQuery(ServerboundBlockEntityTagQuery p_133741_) {}

    @Override
    public void handleSetJigsawBlock(ServerboundSetJigsawBlockPacket p_133778_){}

    @Override
    public void handleJigsawGenerate(ServerboundJigsawGeneratePacket p_133755_){}

    @Override
    public void handleChangeDifficulty(ServerboundChangeDifficultyPacket p_133742_){}

    @Override
    public void handleLockDifficulty(ServerboundLockDifficultyPacket p_133757_){}

    @Override
    public void handleChatSessionUpdate(ServerboundChatSessionUpdatePacket p_254226_){}
}
