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
import net.minecraft.world.item.ItemStack;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.DelegateDeserialization;
import org.bukkit.inventory.meta.BundleMeta;

@DelegateDeserialization(CraftMetaItem.SerializableMeta.class)
public class CraftMetaBundle extends CraftMetaItem implements BundleMeta {

    static final CraftMetaItem.ItemMetaKey ITEMS = new CraftMetaItem.ItemMetaKey("Items", "items");
    private List items;

    CraftMetaBundle(CraftMetaItem meta) {
        super(meta);
        if (meta instanceof CraftMetaBundle) {
            CraftMetaBundle bundle = (CraftMetaBundle) meta;

            if (bundle.hasItems()) {
                this.items = new ArrayList(bundle.items);
            }

        }
    }

    CraftMetaBundle(CompoundTag tag) {
        super(tag);
        if (tag.contains(CraftMetaBundle.ITEMS.NBT, 9)) {
            ListTag list = tag.getList(CraftMetaBundle.ITEMS.NBT, 10);

            if (list != null && !list.isEmpty()) {
                this.items = new ArrayList();

                for (int i = 0; i < list.size(); ++i) {
                    CompoundTag nbttagcompound1 = list.getCompound(i);
                    CraftItemStack itemStack = CraftItemStack.asCraftMirror(ItemStack.of(nbttagcompound1));

                    if (!itemStack.getType().isAir()) {
                        this.addItem(itemStack);
                    }
                }
            }
        }

    }

    CraftMetaBundle(Map map) {
        super(map);
        Iterable items = (Iterable) CraftMetaItem.SerializableMeta.getObject(Iterable.class, map, CraftMetaBundle.ITEMS.BUKKIT, true);

        if (items != null) {
            Iterator iterator = items.iterator();

            while (iterator.hasNext()) {
                Object stack = iterator.next();
                org.bukkit.inventory.ItemStack itemStack;

                if (stack instanceof org.bukkit.inventory.ItemStack && (itemStack = (org.bukkit.inventory.ItemStack) stack) == (org.bukkit.inventory.ItemStack) stack && !itemStack.getType().isAir()) {
                    this.addItem(itemStack);
                }
            }
        }

    }

    void applyToItem(CompoundTag tag) {
        super.applyToItem(tag);
        if (this.hasItems()) {
            ListTag list = new ListTag();
            Iterator iterator = this.items.iterator();

            while (iterator.hasNext()) {
                org.bukkit.inventory.ItemStack item = (org.bukkit.inventory.ItemStack) iterator.next();
                CompoundTag saved = new CompoundTag();

                CraftItemStack.asNMSCopy(item).save(saved);
                list.add(saved);
            }

            tag.put(CraftMetaBundle.ITEMS.NBT, list);
        }

    }

    boolean applicableTo(Material type) {
        return type == Material.BUNDLE;
    }

    boolean isEmpty() {
        return super.isEmpty() && this.isBundleEmpty();
    }

    boolean isBundleEmpty() {
        return !this.hasItems();
    }

    public boolean hasItems() {
        return this.items != null && !this.items.isEmpty();
    }

    public List getItems() {
        return this.items == null ? ImmutableList.of() : ImmutableList.copyOf(this.items);
    }

    public void setItems(List items) {
        this.items = null;
        if (items != null) {
            Iterator iterator = items.iterator();

            while (iterator.hasNext()) {
                org.bukkit.inventory.ItemStack i = (org.bukkit.inventory.ItemStack) iterator.next();

                this.addItem(i);
            }

        }
    }

    public void addItem(org.bukkit.inventory.ItemStack item) {
        Preconditions.checkArgument(item != null && !item.getType().isAir(), "item is null or air");
        if (this.items == null) {
            this.items = new ArrayList();
        }

        this.items.add(item);
    }

    boolean equalsCommon(CraftMetaItem meta) {
        if (!super.equalsCommon(meta)) {
            return false;
        } else if (!(meta instanceof CraftMetaBundle)) {
            return true;
        } else {
            CraftMetaBundle that = (CraftMetaBundle) meta;

            return this.hasItems() ? that.hasItems() && this.items.equals(that.items) : !that.hasItems();
        }
    }

    boolean notUncommon(CraftMetaItem meta) {
        return super.notUncommon(meta) && (meta instanceof CraftMetaBundle || this.isBundleEmpty());
    }

    int applyHash() {
        int original;
        int hash = original = super.applyHash();

        if (this.hasItems()) {
            hash = 61 * hash + this.items.hashCode();
        }

        return original != hash ? CraftMetaBundle.class.hashCode() ^ hash : hash;
    }

    public CraftMetaBundle clone() {
        return (CraftMetaBundle) super.clone();
    }

    Builder serialize(Builder builder) {
        super.serialize(builder);
        if (this.hasItems()) {
            builder.put(CraftMetaBundle.ITEMS.BUKKIT, this.items);
        }

        return builder;
    }
}
