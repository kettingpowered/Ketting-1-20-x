package org.bukkit.craftbukkit.v1_20_R2.block;

import com.google.common.base.Preconditions;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.level.block.AbstractBannerBlock;
import net.minecraft.world.level.block.entity.BannerBlockEntity;
import org.bukkit.DyeColor;
import org.bukkit.World;
import org.bukkit.block.Banner;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;

public class CraftBanner extends CraftBlockEntityState implements Banner {

    private DyeColor base;
    private List patterns;

    public CraftBanner(World world, BannerBlockEntity tileEntity) {
        super(world, tileEntity);
    }

    protected CraftBanner(CraftBanner state) {
        super((CraftBlockEntityState) state);
    }

    public void load(BannerBlockEntity banner) {
        super.load(banner);
        this.base = DyeColor.getByWoolData((byte) ((AbstractBannerBlock) this.data.getBlock()).getColor().getId());
        this.patterns = new ArrayList();
        if (banner.itemPatterns != null) {
            for (int i = 0; i < banner.itemPatterns.size(); ++i) {
                CompoundTag p = (CompoundTag) banner.itemPatterns.get(i);

                this.patterns.add(new Pattern(DyeColor.getByWoolData((byte) p.getInt("Color")), PatternType.getByIdentifier(p.getString("Pattern"))));
            }
        }

    }

    public DyeColor getBaseColor() {
        return this.base;
    }

    public void setBaseColor(DyeColor color) {
        Preconditions.checkArgument(color != null, "color");
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

    public void applyTo(BannerBlockEntity banner) {
        super.applyTo(banner);
        banner.baseColor = net.minecraft.world.item.DyeColor.byId(this.base.getWoolData());
        ListTag newPatterns = new ListTag();
        Iterator iterator = this.patterns.iterator();

        while (iterator.hasNext()) {
            Pattern p = (Pattern) iterator.next();
            CompoundTag compound = new CompoundTag();

            compound.putInt("Color", p.getColor().getWoolData());
            compound.putString("Pattern", p.getPattern().getIdentifier());
            newPatterns.add(compound);
        }

        banner.itemPatterns = newPatterns;
    }

    public CraftBanner copy() {
        return new CraftBanner(this);
    }
}
