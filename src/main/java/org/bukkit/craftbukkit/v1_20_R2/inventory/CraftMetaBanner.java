package org.bukkit.craftbukkit.v1_20_R2.inventory;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap.Builder;
import com.google.common.collect.Sets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.configuration.serialization.DelegateDeserialization;
import org.bukkit.inventory.meta.BannerMeta;

@DelegateDeserialization(CraftMetaItem.SerializableMeta.class)
public class CraftMetaBanner extends CraftMetaItem implements BannerMeta {

    private static final Set BANNER_MATERIALS = Sets.newHashSet(new Material[]{Material.BLACK_BANNER, Material.BLACK_WALL_BANNER, Material.BLUE_BANNER, Material.BLUE_WALL_BANNER, Material.BROWN_BANNER, Material.BROWN_WALL_BANNER, Material.CYAN_BANNER, Material.CYAN_WALL_BANNER, Material.GRAY_BANNER, Material.GRAY_WALL_BANNER, Material.GREEN_BANNER, Material.GREEN_WALL_BANNER, Material.LIGHT_BLUE_BANNER, Material.LIGHT_BLUE_WALL_BANNER, Material.LIGHT_GRAY_BANNER, Material.LIGHT_GRAY_WALL_BANNER, Material.LIME_BANNER, Material.LIME_WALL_BANNER, Material.MAGENTA_BANNER, Material.MAGENTA_WALL_BANNER, Material.ORANGE_BANNER, Material.ORANGE_WALL_BANNER, Material.PINK_BANNER, Material.PINK_WALL_BANNER, Material.PURPLE_BANNER, Material.PURPLE_WALL_BANNER, Material.RED_BANNER, Material.RED_WALL_BANNER, Material.WHITE_BANNER, Material.WHITE_WALL_BANNER, Material.YELLOW_BANNER, Material.YELLOW_WALL_BANNER});
    static final CraftMetaItem.ItemMetaKey BASE = new CraftMetaItem.ItemMetaKey("Base", "base-color");
    static final CraftMetaItem.ItemMetaKey PATTERNS = new CraftMetaItem.ItemMetaKey("Patterns", "patterns");
    static final CraftMetaItem.ItemMetaKey COLOR = new CraftMetaItem.ItemMetaKey("Color", "color");
    static final CraftMetaItem.ItemMetaKey PATTERN = new CraftMetaItem.ItemMetaKey("Pattern", "pattern");
    private DyeColor base;
    private List patterns = new ArrayList();

    CraftMetaBanner(CraftMetaItem meta) {
        super(meta);
        if (meta instanceof CraftMetaBanner) {
            CraftMetaBanner banner = (CraftMetaBanner) meta;

            this.base = banner.base;
            this.patterns = new ArrayList(banner.patterns);
        }
    }

    CraftMetaBanner(CompoundTag tag) {
        super(tag);
        if (tag.contains("BlockEntityTag")) {
            CompoundTag entityTag = tag.getCompound("BlockEntityTag");

            this.base = entityTag.contains(CraftMetaBanner.BASE.NBT) ? DyeColor.getByWoolData((byte) entityTag.getInt(CraftMetaBanner.BASE.NBT)) : null;
            if (entityTag.contains(CraftMetaBanner.PATTERNS.NBT)) {
                ListTag patterns = entityTag.getList(CraftMetaBanner.PATTERNS.NBT, 10);

                for (int i = 0; i < Math.min(patterns.size(), 20); ++i) {
                    CompoundTag p = patterns.getCompound(i);
                    DyeColor color = DyeColor.getByWoolData((byte) p.getInt(CraftMetaBanner.COLOR.NBT));
                    PatternType pattern = PatternType.getByIdentifier(p.getString(CraftMetaBanner.PATTERN.NBT));

                    if (color != null && pattern != null) {
                        this.patterns.add(new Pattern(color, pattern));
                    }
                }
            }

        }
    }

    CraftMetaBanner(Map map) {
        super(map);
        String baseStr = CraftMetaItem.SerializableMeta.getString(map, CraftMetaBanner.BASE.BUKKIT, true);

        if (baseStr != null) {
            this.base = DyeColor.legacyValueOf(baseStr);
        }

        Iterable rawPatternList = (Iterable) CraftMetaItem.SerializableMeta.getObject(Iterable.class, map, CraftMetaBanner.PATTERNS.BUKKIT, true);

        if (rawPatternList != null) {
            Iterator iterator = rawPatternList.iterator();

            while (iterator.hasNext()) {
                Object obj = iterator.next();

                Preconditions.checkArgument(obj instanceof Pattern, "Object (%s) in pattern list is not valid", obj.getClass());
                this.addPattern((Pattern) obj);
            }

        }
    }

    void applyToItem(CompoundTag tag) {
        super.applyToItem(tag);
        CompoundTag entityTag = new CompoundTag();

        if (this.base != null) {
            entityTag.putInt(CraftMetaBanner.BASE.NBT, this.base.getWoolData());
        }

        ListTag newPatterns = new ListTag();
        Iterator iterator = this.patterns.iterator();

        while (iterator.hasNext()) {
            Pattern p = (Pattern) iterator.next();
            CompoundTag compound = new CompoundTag();

            compound.putInt(CraftMetaBanner.COLOR.NBT, p.getColor().getWoolData());
            compound.putString(CraftMetaBanner.PATTERN.NBT, p.getPattern().getIdentifier());
            newPatterns.add(compound);
        }

        entityTag.put(CraftMetaBanner.PATTERNS.NBT, newPatterns);
        tag.put("BlockEntityTag", entityTag);
    }

    public DyeColor getBaseColor() {
        return this.base;
    }

    public void setBaseColor(DyeColor color) {
        this.base = color;
    }

    public List getPatterns() {
        return new ArrayList(this.patterns);
    }

    public void setPatterns(List patterns) {
        this.patterns = new ArrayList(patterns);
    }

    public void addPattern(Pattern pattern) {
        this.patterns.add(pattern);
    }

    public Pattern getPattern(int i) {
        return (Pattern) this.patterns.get(i);
    }

    public Pattern removePattern(int i) {
        return (Pattern) this.patterns.remove(i);
    }

    public void setPattern(int i, Pattern pattern) {
        this.patterns.set(i, pattern);
    }

    public int numberOfPatterns() {
        return this.patterns.size();
    }

    Builder serialize(Builder builder) {
        super.serialize(builder);
        if (this.base != null) {
            builder.put(CraftMetaBanner.BASE.BUKKIT, this.base.toString());
        }

        if (!this.patterns.isEmpty()) {
            builder.put(CraftMetaBanner.PATTERNS.BUKKIT, ImmutableList.copyOf(this.patterns));
        }

        return builder;
    }

    int applyHash() {
        int original;
        int hash = original = super.applyHash();

        if (this.base != null) {
            hash = 31 * hash + this.base.hashCode();
        }

        if (!this.patterns.isEmpty()) {
            hash = 31 * hash + this.patterns.hashCode();
        }

        return original != hash ? CraftMetaBanner.class.hashCode() ^ hash : hash;
    }

    public boolean equalsCommon(CraftMetaItem meta) {
        if (!super.equalsCommon(meta)) {
            return false;
        } else if (meta instanceof CraftMetaBanner) {
            CraftMetaBanner that = (CraftMetaBanner) meta;

            return this.base == that.base && this.patterns.equals(that.patterns);
        } else {
            return true;
        }
    }

    boolean notUncommon(CraftMetaItem meta) {
        return super.notUncommon(meta) && (meta instanceof CraftMetaBanner || this.patterns.isEmpty() && this.base == null);
    }

    boolean isEmpty() {
        return super.isEmpty() && this.patterns.isEmpty() && this.base == null;
    }

    boolean applicableTo(Material type) {
        return CraftMetaBanner.BANNER_MATERIALS.contains(type);
    }

    public CraftMetaBanner clone() {
        CraftMetaBanner meta = (CraftMetaBanner) super.clone();

        meta.patterns = new ArrayList(this.patterns);
        return meta;
    }
}
