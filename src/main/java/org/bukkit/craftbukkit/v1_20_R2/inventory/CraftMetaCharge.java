package org.bukkit.craftbukkit.v1_20_R2.inventory;

import com.google.common.collect.ImmutableMap.Builder;
import java.util.Map;
import net.minecraft.nbt.CompoundTag;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.DelegateDeserialization;
import org.bukkit.inventory.meta.FireworkEffectMeta;

@DelegateDeserialization(CraftMetaItem.SerializableMeta.class)
class CraftMetaCharge extends CraftMetaItem implements FireworkEffectMeta {

    static final CraftMetaItem.ItemMetaKey EXPLOSION = new CraftMetaItem.ItemMetaKey("Explosion", "firework-effect");
    private FireworkEffect effect;

    CraftMetaCharge(CraftMetaItem meta) {
        super(meta);
        if (meta instanceof CraftMetaCharge) {
            this.effect = ((CraftMetaCharge) meta).effect;
        }

    }

    CraftMetaCharge(Map map) {
        super(map);
        this.setEffect((FireworkEffect) CraftMetaItem.SerializableMeta.getObject(FireworkEffect.class, map, CraftMetaCharge.EXPLOSION.BUKKIT, true));
    }

    CraftMetaCharge(CompoundTag tag) {
        super(tag);
        if (tag.contains(CraftMetaCharge.EXPLOSION.NBT)) {
            try {
                this.effect = CraftMetaFirework.getEffect(tag.getCompound(CraftMetaCharge.EXPLOSION.NBT));
            } catch (IllegalArgumentException illegalargumentexception) {
                ;
            }
        }

    }

    public void setEffect(FireworkEffect effect) {
        this.effect = effect;
    }

    public boolean hasEffect() {
        return this.effect != null;
    }

    public FireworkEffect getEffect() {
        return this.effect;
    }

    void applyToItem(CompoundTag itemTag) {
        super.applyToItem(itemTag);
        if (this.hasEffect()) {
            itemTag.put(CraftMetaCharge.EXPLOSION.NBT, CraftMetaFirework.getExplosion(this.effect));
        }

    }

    boolean applicableTo(Material type) {
        return type == Material.FIREWORK_STAR;
    }

    boolean isEmpty() {
        return super.isEmpty() && !this.hasChargeMeta();
    }

    boolean hasChargeMeta() {
        return this.hasEffect();
    }

    boolean equalsCommon(CraftMetaItem meta) {
        if (!super.equalsCommon(meta)) {
            return false;
        } else if (!(meta instanceof CraftMetaCharge)) {
            return true;
        } else {
            CraftMetaCharge that = (CraftMetaCharge) meta;

            return this.hasEffect() ? that.hasEffect() && this.effect.equals(that.effect) : !that.hasEffect();
        }
    }

    boolean notUncommon(CraftMetaItem meta) {
        return super.notUncommon(meta) && (meta instanceof CraftMetaCharge || !this.hasChargeMeta());
    }

    int applyHash() {
        int original;
        int hash = original = super.applyHash();

        if (this.hasEffect()) {
            hash = 61 * hash + this.effect.hashCode();
        }

        return hash != original ? CraftMetaCharge.class.hashCode() ^ hash : hash;
    }

    public CraftMetaCharge clone() {
        return (CraftMetaCharge) super.clone();
    }

    Builder serialize(Builder builder) {
        super.serialize(builder);
        if (this.hasEffect()) {
            builder.put(CraftMetaCharge.EXPLOSION.BUKKIT, this.effect);
        }

        return builder;
    }
}
