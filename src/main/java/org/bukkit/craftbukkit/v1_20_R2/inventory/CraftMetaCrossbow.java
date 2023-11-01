package org.bukkit.craftbukkit.v1_20_R2.inventory;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap.Builder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.DelegateDeserialization;
import org.bukkit.craftbukkit.v1_20_R2.util.CraftMagicNumbers;
import org.bukkit.inventory.meta.CrossbowMeta;

@DelegateDeserialization(CraftMetaItem.SerializableMeta.class)
public class CraftMetaCrossbow extends CraftMetaItem implements CrossbowMeta {

    static final CraftMetaItem.ItemMetaKey CHARGED = new CraftMetaItem.ItemMetaKey("Charged", "charged");
    static final CraftMetaItem.ItemMetaKey CHARGED_PROJECTILES = new CraftMetaItem.ItemMetaKey("ChargedProjectiles", "charged-projectiles");
    private boolean charged;
    private List chargedProjectiles;

    CraftMetaCrossbow(CraftMetaItem meta) {
        super(meta);
        if (meta instanceof CraftMetaCrossbow) {
            CraftMetaCrossbow crossbow = (CraftMetaCrossbow) meta;

            this.charged = crossbow.charged;
            if (crossbow.hasChargedProjectiles()) {
                this.chargedProjectiles = new ArrayList(crossbow.chargedProjectiles);
            }

        }
    }

    CraftMetaCrossbow(CompoundTag tag) {
        super(tag);
        this.charged = tag.getBoolean(CraftMetaCrossbow.CHARGED.NBT);
        if (tag.contains(CraftMetaCrossbow.CHARGED_PROJECTILES.NBT, 9)) {
            ListTag list = tag.getList(CraftMetaCrossbow.CHARGED_PROJECTILES.NBT, 10);

            if (list != null && !list.isEmpty()) {
                this.chargedProjectiles = new ArrayList();

                for (int i = 0; i < list.size(); ++i) {
                    CompoundTag nbttagcompound1 = list.getCompound(i);

                    this.chargedProjectiles.add(CraftItemStack.asCraftMirror(ItemStack.of(nbttagcompound1)));
                }
            }
        }

    }

    CraftMetaCrossbow(Map map) {
        super(map);
        Boolean charged = (Boolean) CraftMetaItem.SerializableMeta.getObject(Boolean.class, map, CraftMetaCrossbow.CHARGED.BUKKIT, true);

        if (charged != null) {
            this.charged = charged;
        }

        Iterable projectiles = (Iterable) CraftMetaItem.SerializableMeta.getObject(Iterable.class, map, CraftMetaCrossbow.CHARGED_PROJECTILES.BUKKIT, true);

        if (projectiles != null) {
            Iterator iterator = projectiles.iterator();

            while (iterator.hasNext()) {
                Object stack = iterator.next();

                if (stack instanceof org.bukkit.inventory.ItemStack) {
                    this.addChargedProjectile((org.bukkit.inventory.ItemStack) stack);
                }
            }
        }

    }

    void applyToItem(CompoundTag tag) {
        super.applyToItem(tag);
        tag.putBoolean(CraftMetaCrossbow.CHARGED.NBT, this.charged);
        if (this.hasChargedProjectiles()) {
            ListTag list = new ListTag();
            Iterator iterator = this.chargedProjectiles.iterator();

            while (iterator.hasNext()) {
                org.bukkit.inventory.ItemStack item = (org.bukkit.inventory.ItemStack) iterator.next();
                CompoundTag saved = new CompoundTag();

                CraftItemStack.asNMSCopy(item).save(saved);
                list.add(saved);
            }

            tag.put(CraftMetaCrossbow.CHARGED_PROJECTILES.NBT, list);
        }

    }

    boolean applicableTo(Material type) {
        return type == Material.CROSSBOW;
    }

    boolean isEmpty() {
        return super.isEmpty() && this.isCrossbowEmpty();
    }

    boolean isCrossbowEmpty() {
        return !this.hasChargedProjectiles();
    }

    public boolean hasChargedProjectiles() {
        return this.chargedProjectiles != null;
    }

    public List getChargedProjectiles() {
        return this.chargedProjectiles == null ? ImmutableList.of() : ImmutableList.copyOf(this.chargedProjectiles);
    }

    public void setChargedProjectiles(List projectiles) {
        this.chargedProjectiles = null;
        this.charged = false;
        if (projectiles != null) {
            Iterator iterator = projectiles.iterator();

            while (iterator.hasNext()) {
                org.bukkit.inventory.ItemStack i = (org.bukkit.inventory.ItemStack) iterator.next();

                this.addChargedProjectile(i);
            }

        }
    }

    public void addChargedProjectile(org.bukkit.inventory.ItemStack item) {
        Preconditions.checkArgument(item != null, "item");
        Preconditions.checkArgument(item.getType() == Material.FIREWORK_ROCKET || CraftMagicNumbers.getItem(item.getType()) instanceof ArrowItem, "Item %s is not an arrow or firework rocket", item);
        if (this.chargedProjectiles == null) {
            this.chargedProjectiles = new ArrayList();
        }

        this.charged = true;
        this.chargedProjectiles.add(item);
    }

    boolean equalsCommon(CraftMetaItem meta) {
        if (!super.equalsCommon(meta)) {
            return false;
        } else if (!(meta instanceof CraftMetaCrossbow)) {
            return true;
        } else {
            CraftMetaCrossbow that = (CraftMetaCrossbow) meta;

            if (this.charged == that.charged) {
                if (this.hasChargedProjectiles()) {
                    if (!that.hasChargedProjectiles() || !this.chargedProjectiles.equals(that.chargedProjectiles)) {
                        return false;
                    }
                } else if (that.hasChargedProjectiles()) {
                    return false;
                }

                return true;
            } else {
                return false;
            }
        }
    }

    boolean notUncommon(CraftMetaItem meta) {
        return super.notUncommon(meta) && (meta instanceof CraftMetaCrossbow || this.isCrossbowEmpty());
    }

    int applyHash() {
        int original;
        int hash = original = super.applyHash();

        if (this.hasChargedProjectiles()) {
            hash = 61 * hash + (this.charged ? 1 : 0);
            hash = 61 * hash + this.chargedProjectiles.hashCode();
        }

        return original != hash ? CraftMetaCrossbow.class.hashCode() ^ hash : hash;
    }

    public CraftMetaCrossbow clone() {
        return (CraftMetaCrossbow) super.clone();
    }

    Builder serialize(Builder builder) {
        super.serialize(builder);
        builder.put(CraftMetaCrossbow.CHARGED.BUKKIT, this.charged);
        if (this.hasChargedProjectiles()) {
            builder.put(CraftMetaCrossbow.CHARGED_PROJECTILES.BUKKIT, this.chargedProjectiles);
        }

        return builder;
    }
}
