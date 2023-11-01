package org.bukkit.craftbukkit.v1_20_R2.inventory;

import com.google.common.collect.ImmutableMap.Builder;
import com.google.common.collect.Sets;
import com.mojang.authlib.GameProfile;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.resources.ResourceLocation;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.serialization.DelegateDeserialization;
import org.bukkit.craftbukkit.v1_20_R2.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_20_R2.profile.CraftPlayerProfile;
import org.bukkit.craftbukkit.v1_20_R2.util.CraftNamespacedKey;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.profile.PlayerProfile;

@DelegateDeserialization(CraftMetaItem.SerializableMeta.class)
class CraftMetaSkull extends CraftMetaItem implements SkullMeta {

    private static final Set SKULL_MATERIALS = Sets.newHashSet(new Material[]{Material.CREEPER_HEAD, Material.CREEPER_WALL_HEAD, Material.DRAGON_HEAD, Material.DRAGON_WALL_HEAD, Material.PIGLIN_HEAD, Material.PIGLIN_WALL_HEAD, Material.PLAYER_HEAD, Material.PLAYER_WALL_HEAD, Material.SKELETON_SKULL, Material.SKELETON_WALL_SKULL, Material.WITHER_SKELETON_SKULL, Material.WITHER_SKELETON_WALL_SKULL, Material.ZOMBIE_HEAD, Material.ZOMBIE_WALL_HEAD});
    static final CraftMetaItem.ItemMetaKey SKULL_PROFILE = new CraftMetaItem.ItemMetaKey("SkullProfile");
    static final CraftMetaItem.ItemMetaKey SKULL_OWNER = new CraftMetaItem.ItemMetaKey("SkullOwner", "skull-owner");
    static final CraftMetaItem.ItemMetaKey BLOCK_ENTITY_TAG = new CraftMetaItem.ItemMetaKey("BlockEntityTag");
    static final CraftMetaItem.ItemMetaKey NOTE_BLOCK_SOUND = new CraftMetaItem.ItemMetaKey("note_block_sound");
    static final int MAX_OWNER_LENGTH = 16;
    private GameProfile profile;
    private CompoundTag serializedProfile;
    private ResourceLocation noteBlockSound;

    CraftMetaSkull(CraftMetaItem meta) {
        super(meta);
        if (meta instanceof CraftMetaSkull) {
            CraftMetaSkull skullMeta = (CraftMetaSkull) meta;

            this.setProfile(skullMeta.profile);
            this.noteBlockSound = skullMeta.noteBlockSound;
        }
    }

    CraftMetaSkull(CompoundTag tag) {
        super(tag);
        if (tag.contains(CraftMetaSkull.SKULL_OWNER.NBT, 10)) {
            this.setProfile(NbtUtils.readGameProfile(tag.getCompound(CraftMetaSkull.SKULL_OWNER.NBT)));
        } else if (tag.contains(CraftMetaSkull.SKULL_OWNER.NBT, 8) && !tag.getString(CraftMetaSkull.SKULL_OWNER.NBT).isEmpty()) {
            this.setProfile(new GameProfile(Util.NIL_UUID, tag.getString(CraftMetaSkull.SKULL_OWNER.NBT)));
        }

        if (tag.contains(CraftMetaSkull.BLOCK_ENTITY_TAG.NBT, 10)) {
            CompoundTag nbtTagCompound = tag.getCompound(CraftMetaSkull.BLOCK_ENTITY_TAG.NBT).copy();

            if (nbtTagCompound.contains(CraftMetaSkull.NOTE_BLOCK_SOUND.NBT, 8)) {
                this.noteBlockSound = ResourceLocation.tryParse(nbtTagCompound.getString(CraftMetaSkull.NOTE_BLOCK_SOUND.NBT));
            }
        }

    }

    CraftMetaSkull(Map map) {
        super(map);
        Object object;

        if (this.profile == null) {
            object = map.get(CraftMetaSkull.SKULL_OWNER.BUKKIT);
            if (object instanceof PlayerProfile) {
                this.setOwnerProfile((PlayerProfile) object);
            } else {
                this.setOwner(CraftMetaItem.SerializableMeta.getString(map, CraftMetaSkull.SKULL_OWNER.BUKKIT, true));
            }
        }

        if (this.noteBlockSound == null) {
            object = map.get(CraftMetaSkull.NOTE_BLOCK_SOUND.BUKKIT);
            if (object instanceof NamespacedKey) {
                this.setNoteBlockSound((NamespacedKey) object);
            } else {
                this.setNoteBlockSound((NamespacedKey) CraftMetaItem.SerializableMeta.getObject(NamespacedKey.class, map, CraftMetaSkull.NOTE_BLOCK_SOUND.BUKKIT, true));
            }
        }

    }

    void deserializeInternal(CompoundTag tag, Object context) {
        super.deserializeInternal(tag, context);
        CompoundTag nbtTagCompound;

        if (tag.contains(CraftMetaSkull.SKULL_PROFILE.NBT, 10)) {
            nbtTagCompound = tag.getCompound(CraftMetaSkull.SKULL_PROFILE.NBT);
            if (nbtTagCompound.contains("Id", 8)) {
                UUID uuid = UUID.fromString(nbtTagCompound.getString("Id"));

                nbtTagCompound.putUUID("Id", uuid);
            }

            this.setProfile(NbtUtils.readGameProfile(nbtTagCompound));
        }

        if (tag.contains(CraftMetaSkull.BLOCK_ENTITY_TAG.NBT, 10)) {
            nbtTagCompound = tag.getCompound(CraftMetaSkull.BLOCK_ENTITY_TAG.NBT).copy();
            if (nbtTagCompound.contains(CraftMetaSkull.NOTE_BLOCK_SOUND.NBT, 8)) {
                this.noteBlockSound = ResourceLocation.tryParse(nbtTagCompound.getString(CraftMetaSkull.NOTE_BLOCK_SOUND.NBT));
            }
        }

    }

    private void setProfile(GameProfile profile) {
        this.profile = profile;
        this.serializedProfile = profile == null ? null : NbtUtils.writeGameProfile(new CompoundTag(), profile);
    }

    void applyToItem(CompoundTag tag) {
        super.applyToItem(tag);
        if (this.profile != null) {
            this.checkForInconsistency();
            tag.put(CraftMetaSkull.SKULL_OWNER.NBT, this.serializedProfile);
            CraftPlayerProfile ownerProfile = new CraftPlayerProfile(this.profile);

            if (ownerProfile.getTextures().isEmpty()) {
                ownerProfile.update().thenAccept((filledProfilex) -> {
                    this.setOwnerProfile(filledProfilex);
                    tag.put(CraftMetaSkull.SKULL_OWNER.NBT, this.serializedProfile);
                });
            }
        }

        if (this.noteBlockSound != null) {
            CompoundTag nbtTagCompound = new CompoundTag();

            nbtTagCompound.putString(CraftMetaSkull.NOTE_BLOCK_SOUND.NBT, this.noteBlockSound.toString());
            tag.put(CraftMetaSkull.BLOCK_ENTITY_TAG.NBT, nbtTagCompound);
        }

    }

    boolean isEmpty() {
        return super.isEmpty() && this.isSkullEmpty();
    }

    boolean isSkullEmpty() {
        return this.profile == null && this.noteBlockSound == null;
    }

    boolean applicableTo(Material type) {
        return CraftMetaSkull.SKULL_MATERIALS.contains(type);
    }

    public CraftMetaSkull clone() {
        return (CraftMetaSkull) super.clone();
    }

    public boolean hasOwner() {
        return this.profile != null && !this.profile.getName().isEmpty();
    }

    public String getOwner() {
        return this.hasOwner() ? this.profile.getName() : null;
    }

    public OfflinePlayer getOwningPlayer() {
        if (this.hasOwner()) {
            if (!this.profile.getId().equals(Util.NIL_UUID)) {
                return Bukkit.getOfflinePlayer(this.profile.getId());
            }

            if (!this.profile.getName().isEmpty()) {
                return Bukkit.getOfflinePlayer(this.profile.getName());
            }
        }

        return null;
    }

    public boolean setOwner(String name) {
        if (name != null && name.length() > 16) {
            return false;
        } else {
            if (name == null) {
                this.setProfile((GameProfile) null);
            } else {
                this.setProfile(new GameProfile(Util.NIL_UUID, name));
            }

            return true;
        }
    }

    public boolean setOwningPlayer(OfflinePlayer owner) {
        if (owner == null) {
            this.setProfile((GameProfile) null);
        } else if (owner instanceof CraftPlayer) {
            this.setProfile(((CraftPlayer) owner).getProfile());
        } else {
            this.setProfile(new GameProfile(owner.getUniqueId(), owner.getName()));
        }

        return true;
    }

    public PlayerProfile getOwnerProfile() {
        return !this.hasOwner() ? null : new CraftPlayerProfile(this.profile);
    }

    public void setOwnerProfile(PlayerProfile profile) {
        if (profile == null) {
            this.setProfile((GameProfile) null);
        } else {
            this.setProfile(CraftPlayerProfile.validateSkullProfile(((CraftPlayerProfile) profile).buildGameProfile()));
        }

    }

    public void setNoteBlockSound(NamespacedKey noteBlockSound) {
        if (noteBlockSound == null) {
            this.noteBlockSound = null;
        } else {
            this.noteBlockSound = CraftNamespacedKey.toMinecraft(noteBlockSound);
        }

    }

    public NamespacedKey getNoteBlockSound() {
        return this.noteBlockSound == null ? null : CraftNamespacedKey.fromMinecraft(this.noteBlockSound);
    }

    int applyHash() {
        int original;
        int hash = original = super.applyHash();

        if (this.hasOwner()) {
            hash = 61 * hash + this.profile.hashCode();
        }

        if (this.noteBlockSound != null) {
            hash = 61 * hash + this.noteBlockSound.hashCode();
        }

        return original != hash ? CraftMetaSkull.class.hashCode() ^ hash : hash;
    }

    boolean equalsCommon(CraftMetaItem meta) {
        if (!super.equalsCommon(meta)) {
            return false;
        } else if (!(meta instanceof CraftMetaSkull)) {
            return true;
        } else {
            CraftMetaSkull that = (CraftMetaSkull) meta;

            this.checkForInconsistency();
            if (this.profile != null) {
                if (that.profile == null || !this.serializedProfile.equals(that.serializedProfile)) {
                    return false;
                }
            } else if (that.profile != null) {
                return false;
            }

            if (Objects.equals(this.noteBlockSound, that.noteBlockSound)) {
                return true;
            } else {
                return false;
            }
        }
    }

    boolean notUncommon(CraftMetaItem meta) {
        return super.notUncommon(meta) && (meta instanceof CraftMetaSkull || this.isSkullEmpty());
    }

    Builder serialize(Builder builder) {
        super.serialize(builder);
        if (this.profile != null) {
            return builder.put(CraftMetaSkull.SKULL_OWNER.BUKKIT, new CraftPlayerProfile(this.profile));
        } else {
            NamespacedKey namespacedKeyNB = this.getNoteBlockSound();

            return namespacedKeyNB != null ? builder.put(CraftMetaSkull.NOTE_BLOCK_SOUND.BUKKIT, namespacedKeyNB) : builder;
        }
    }

    private void checkForInconsistency() {
        if (this.profile != null && this.serializedProfile == null) {
            Bukkit.getLogger().warning("Found inconsistent skull meta, this should normally not happen and is not a Bukkit / Spigot issue, but one from a plugin you are using.\nBukkit will attempt to fix it this time for you, but may not be able to do this every time.\nIf you see this message after typing a command from a plugin, please report this to the plugin developer, they should use the api instead of relying on reflection (and doing it the wrong way).");
            this.serializedProfile = NbtUtils.writeGameProfile(new CompoundTag(), this.profile);
        }

    }
}
