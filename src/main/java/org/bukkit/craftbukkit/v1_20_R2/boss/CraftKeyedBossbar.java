package org.bukkit.craftbukkit.v1_20_R2.boss;

import net.minecraft.server.bossevents.CustomBossEvent;
import org.bukkit.NamespacedKey;
import org.bukkit.boss.KeyedBossBar;
import org.bukkit.craftbukkit.v1_20_R2.util.CraftNamespacedKey;

public class CraftKeyedBossbar extends CraftBossBar implements KeyedBossBar {

    public CraftKeyedBossbar(CustomBossEvent bossBattleCustom) {
        super(bossBattleCustom);
    }

    public NamespacedKey getKey() {
        return CraftNamespacedKey.fromMinecraft(this.getHandle().getTextId());
    }

    public CustomBossEvent getHandle() {
        return (CustomBossEvent) super.getHandle();
    }
}
