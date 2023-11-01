package org.bukkit.craftbukkit.v1_20_R2.advancement;

import java.util.Collection;
import java.util.Collections;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.DisplayInfo;
import org.bukkit.NamespacedKey;
import org.bukkit.advancement.Advancement;
import org.bukkit.advancement.AdvancementDisplay;
import org.bukkit.craftbukkit.v1_20_R2.util.CraftNamespacedKey;

public class CraftAdvancement implements Advancement {

    private final AdvancementHolder handle;

    public CraftAdvancement(AdvancementHolder handle) {
        this.handle = handle;
    }

    public AdvancementHolder getHandle() {
        return this.handle;
    }

    public NamespacedKey getKey() {
        return CraftNamespacedKey.fromMinecraft(this.handle.id());
    }

    public Collection getCriteria() {
        return Collections.unmodifiableCollection(this.handle.value().criteria().keySet());
    }

    public AdvancementDisplay getDisplay() {
        return this.handle.value().display().isEmpty() ? null : new CraftAdvancementDisplay((DisplayInfo) this.handle.value().display().get());
    }
}
