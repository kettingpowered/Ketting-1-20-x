package org.bukkit.craftbukkit.v1_20_R2.boss;

import net.minecraft.server.bossevents.BossBattleCustom;
import org.bukkit.NamespacedKey;
import org.bukkit.boss.KeyedBossBar;
import org.bukkit.craftbukkit.v1_20_R2.util.CraftNamespacedKey;

public class CraftKeyedBossbar extends CraftBossBar implements KeyedBossBar {

    public CraftKeyedBossbar(BossBattleCustom bossBattleCustom) {
        super(bossBattleCustom);
    }

    @Override
    public NamespacedKey getKey() {
        return CraftNamespacedKey.fromMinecraft(getHandle().getTextId());
    }

    @Override
    public BossBattleCustom getHandle() {
        return (BossBattleCustom) super.getHandle();
    }
}
