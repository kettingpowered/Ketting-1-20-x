package org.bukkit.craftbukkit.v1_20_R2;

import java.util.HashMap;
import net.minecraft.world.level.block.SoundType;
import org.bukkit.Sound;
import org.bukkit.SoundGroup;

public class CraftSoundGroup implements SoundGroup {

    private final SoundType handle;
    private static final HashMap SOUND_GROUPS = new HashMap();

    public static SoundGroup getSoundGroup(SoundType soundEffectType) {
        return (SoundGroup) CraftSoundGroup.SOUND_GROUPS.computeIfAbsent(soundEffectType, CraftSoundGroup::new);
    }

    private CraftSoundGroup(SoundType soundEffectType) {
        this.handle = soundEffectType;
    }

    public SoundType getHandle() {
        return this.handle;
    }

    public float getVolume() {
        return this.getHandle().getVolume();
    }

    public float getPitch() {
        return this.getHandle().getPitch();
    }

    public Sound getBreakSound() {
        return CraftSound.minecraftToBukkit(this.getHandle().breakSound);
    }

    public Sound getStepSound() {
        return CraftSound.minecraftToBukkit(this.getHandle().getStepSound());
    }

    public Sound getPlaceSound() {
        return CraftSound.minecraftToBukkit(this.getHandle().getPlaceSound());
    }

    public Sound getHitSound() {
        return CraftSound.minecraftToBukkit(this.getHandle().hitSound);
    }

    public Sound getFallSound() {
        return CraftSound.minecraftToBukkit(this.getHandle().getFallSound());
    }
}
