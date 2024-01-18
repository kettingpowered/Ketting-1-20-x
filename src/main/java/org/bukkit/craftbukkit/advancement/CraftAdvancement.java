package org.bukkit.craftbukkit.advancement;

import java.util.Collection;
import java.util.Collections;
import net.minecraft.advancements.Advancement;
import org.bukkit.NamespacedKey;
import org.bukkit.advancement.AdvancementDisplay;
import org.bukkit.craftbukkit.util.CraftNamespacedKey;

public class CraftAdvancement implements org.bukkit.advancement.Advancement {

    private final Advancement handle;

    public CraftAdvancement(Advancement handle) {
        this.handle = handle;
    }

    public Advancement getHandle() {
        return handle;
    }

    @Override
    public NamespacedKey getKey() {
        return CraftNamespacedKey.fromMinecraft(handle.m_138327_());
    }

    @Override
    public Collection<String> getCriteria() {
        return Collections.unmodifiableCollection(handle.m_138325_().keySet());
    }

    @Override
    public AdvancementDisplay getDisplay() {
        if (handle.m_138320_() == null) {
            return null;
        }

        return new CraftAdvancementDisplay(handle.m_138320_());
    }
}
