package org.bukkit.craftbukkit.v1_20_R2.boss;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;
import net.minecraft.network.protocol.game.ClientboundBossEventPacket;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.BossEvent;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.craftbukkit.v1_20_R2.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_20_R2.util.CraftChatMessage;
import org.bukkit.entity.Player;

public class CraftBossBar implements BossBar {

    private final ServerBossEvent handle;
    private Map flags;
    private static volatile int[] $SWITCH_TABLE$org$bukkit$boss$BarStyle;
    private static volatile int[] $SWITCH_TABLE$net$minecraft$world$BossBattle$BarStyle;

    public CraftBossBar(String title, BarColor color, BarStyle style, BarFlag... flags) {
        this.handle = new ServerBossEvent(CraftChatMessage.fromString(title, true)[0], this.convertColor(color), this.convertStyle(style));
        this.initialize();
        BarFlag[] abarflag = flags;
        int i = flags.length;

        for (int j = 0; j < i; ++j) {
            BarFlag flag = abarflag[j];

            this.addFlag(flag);
        }

        this.setColor(color);
        this.setStyle(style);
    }

    public CraftBossBar(ServerBossEvent bossBattleServer) {
        this.handle = bossBattleServer;
        this.initialize();
    }

    private void initialize() {
        this.flags = new HashMap();
        Map map = this.flags;
        BarFlag barflag = BarFlag.DARKEN_SKY;
        ServerBossEvent serverbossevent = this.handle;

        this.handle.getClass();
        Supplier supplier = serverbossevent::shouldDarkenScreen;
        ServerBossEvent serverbossevent1 = this.handle;

        this.handle.getClass();
        map.put(barflag, new CraftBossBar.FlagContainer(supplier, serverbossevent1::setDarkenScreen));
        map = this.flags;
        barflag = BarFlag.PLAY_BOSS_MUSIC;
        serverbossevent = this.handle;
        this.handle.getClass();
        supplier = serverbossevent::shouldPlayBossMusic;
        serverbossevent1 = this.handle;
        this.handle.getClass();
        map.put(barflag, new CraftBossBar.FlagContainer(supplier, serverbossevent1::setPlayBossMusic));
        map = this.flags;
        barflag = BarFlag.CREATE_FOG;
        serverbossevent = this.handle;
        this.handle.getClass();
        supplier = serverbossevent::shouldCreateWorldFog;
        serverbossevent1 = this.handle;
        this.handle.getClass();
        map.put(barflag, new CraftBossBar.FlagContainer(supplier, serverbossevent1::setCreateWorldFog));
    }

    private BarColor convertColor(BossEvent.BossBarColor color) {
        BarColor bukkitColor = BarColor.valueOf(color.name());

        return bukkitColor == null ? BarColor.WHITE : bukkitColor;
    }

    private BossEvent.BossBarColor convertColor(BarColor color) {
        BossEvent.BossBarColor nmsColor = BossEvent.BossBarColor.valueOf(color.name());

        return nmsColor == null ? BossEvent.BossBarColor.WHITE : nmsColor;
    }

    private BossEvent.BossBarOverlay convertStyle(BarStyle style) {
        switch ($SWITCH_TABLE$org$bukkit$boss$BarStyle()[style.ordinal()]) {
            case 1:
            default:
                return BossEvent.BossBarOverlay.PROGRESS;
            case 2:
                return BossEvent.BossBarOverlay.NOTCHED_6;
            case 3:
                return BossEvent.BossBarOverlay.NOTCHED_10;
            case 4:
                return BossEvent.BossBarOverlay.NOTCHED_12;
            case 5:
                return BossEvent.BossBarOverlay.NOTCHED_20;
        }
    }

    private BarStyle convertStyle(BossEvent.BossBarOverlay style) {
        switch ($SWITCH_TABLE$net$minecraft$world$BossBattle$BarStyle()[style.ordinal()]) {
            case 1:
            default:
                return BarStyle.SOLID;
            case 2:
                return BarStyle.SEGMENTED_6;
            case 3:
                return BarStyle.SEGMENTED_10;
            case 4:
                return BarStyle.SEGMENTED_12;
            case 5:
                return BarStyle.SEGMENTED_20;
        }
    }

    public String getTitle() {
        return CraftChatMessage.fromComponent(this.handle.name);
    }

    public void setTitle(String title) {
        this.handle.name = CraftChatMessage.fromString(title, true)[0];
        this.handle.broadcast(ClientboundBossEventPacket::createUpdateNamePacket);
    }

    public BarColor getColor() {
        return this.convertColor(this.handle.color);
    }

    public void setColor(BarColor color) {
        this.handle.color = this.convertColor(color);
        this.handle.broadcast(ClientboundBossEventPacket::createUpdateStylePacket);
    }

    public BarStyle getStyle() {
        return this.convertStyle(this.handle.overlay);
    }

    public void setStyle(BarStyle style) {
        this.handle.overlay = this.convertStyle(style);
        this.handle.broadcast(ClientboundBossEventPacket::createUpdateStylePacket);
    }

    public void addFlag(BarFlag flag) {
        CraftBossBar.FlagContainer flagContainer = (CraftBossBar.FlagContainer) this.flags.get(flag);

        if (flagContainer != null) {
            flagContainer.set.accept(true);
        }

    }

    public void removeFlag(BarFlag flag) {
        CraftBossBar.FlagContainer flagContainer = (CraftBossBar.FlagContainer) this.flags.get(flag);

        if (flagContainer != null) {
            flagContainer.set.accept(false);
        }

    }

    public boolean hasFlag(BarFlag flag) {
        CraftBossBar.FlagContainer flagContainer = (CraftBossBar.FlagContainer) this.flags.get(flag);

        return flagContainer != null ? (Boolean) flagContainer.get.get() : false;
    }

    public void setProgress(double progress) {
        Preconditions.checkArgument(progress >= 0.0D && progress <= 1.0D, "Progress must be between 0.0 and 1.0 (%s)", progress);
        this.handle.setProgress((float) progress);
    }

    public double getProgress() {
        return (double) this.handle.getProgress();
    }

    public void addPlayer(Player player) {
        Preconditions.checkArgument(player != null, "player == null");
        Preconditions.checkArgument(((CraftPlayer) player).getHandle().connection != null, "player is not fully connected (wait for PlayerJoinEvent)");
        this.handle.addPlayer(((CraftPlayer) player).getHandle());
    }

    public void removePlayer(Player player) {
        Preconditions.checkArgument(player != null, "player == null");
        this.handle.removePlayer(((CraftPlayer) player).getHandle());
    }

    public List getPlayers() {
        Builder players = ImmutableList.builder();
        Iterator iterator = this.handle.getPlayers().iterator();

        while (iterator.hasNext()) {
            ServerPlayer p = (ServerPlayer) iterator.next();

            players.add(p.getBukkitEntity());
        }

        return players.build();
    }

    public void setVisible(boolean visible) {
        this.handle.setVisible(visible);
    }

    public boolean isVisible() {
        return this.handle.visible;
    }

    public void show() {
        this.handle.setVisible(true);
    }

    public void hide() {
        this.handle.setVisible(false);
    }

    public void removeAll() {
        Iterator iterator = this.getPlayers().iterator();

        while (iterator.hasNext()) {
            Player player = (Player) iterator.next();

            this.removePlayer(player);
        }

    }

    public ServerBossEvent getHandle() {
        return this.handle;
    }

    static int[] $SWITCH_TABLE$org$bukkit$boss$BarStyle() {
        int[] aint = CraftBossBar.$SWITCH_TABLE$org$bukkit$boss$BarStyle;

        if (aint != null) {
            return aint;
        } else {
            int[] aint1 = new int[BarStyle.values().length];

            try {
                aint1[BarStyle.SEGMENTED_10.ordinal()] = 3;
            } catch (NoSuchFieldError nosuchfielderror) {
                ;
            }

            try {
                aint1[BarStyle.SEGMENTED_12.ordinal()] = 4;
            } catch (NoSuchFieldError nosuchfielderror1) {
                ;
            }

            try {
                aint1[BarStyle.SEGMENTED_20.ordinal()] = 5;
            } catch (NoSuchFieldError nosuchfielderror2) {
                ;
            }

            try {
                aint1[BarStyle.SEGMENTED_6.ordinal()] = 2;
            } catch (NoSuchFieldError nosuchfielderror3) {
                ;
            }

            try {
                aint1[BarStyle.SOLID.ordinal()] = 1;
            } catch (NoSuchFieldError nosuchfielderror4) {
                ;
            }

            CraftBossBar.$SWITCH_TABLE$org$bukkit$boss$BarStyle = aint1;
            return aint1;
        }
    }

    static int[] $SWITCH_TABLE$net$minecraft$world$BossBattle$BarStyle() {
        int[] aint = CraftBossBar.$SWITCH_TABLE$net$minecraft$world$BossBattle$BarStyle;

        if (aint != null) {
            return aint;
        } else {
            int[] aint1 = new int[BossEvent.BossBarOverlay.values().length];

            try {
                aint1[BossEvent.BossBarOverlay.NOTCHED_10.ordinal()] = 3;
            } catch (NoSuchFieldError nosuchfielderror) {
                ;
            }

            try {
                aint1[BossEvent.BossBarOverlay.NOTCHED_12.ordinal()] = 4;
            } catch (NoSuchFieldError nosuchfielderror1) {
                ;
            }

            try {
                aint1[BossEvent.BossBarOverlay.NOTCHED_20.ordinal()] = 5;
            } catch (NoSuchFieldError nosuchfielderror2) {
                ;
            }

            try {
                aint1[BossEvent.BossBarOverlay.NOTCHED_6.ordinal()] = 2;
            } catch (NoSuchFieldError nosuchfielderror3) {
                ;
            }

            try {
                aint1[BossEvent.BossBarOverlay.PROGRESS.ordinal()] = 1;
            } catch (NoSuchFieldError nosuchfielderror4) {
                ;
            }

            CraftBossBar.$SWITCH_TABLE$net$minecraft$world$BossBattle$BarStyle = aint1;
            return aint1;
        }
    }

    private final class FlagContainer {

        private Supplier get;
        private Consumer set;

        private FlagContainer(Supplier get, Consumer set) {
            this.get = get;
            this.set = set;
        }
    }
}
