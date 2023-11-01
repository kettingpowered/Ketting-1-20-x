package org.bukkit.craftbukkit.v1_20_R2.entity;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.world.entity.animal.AbstractFish;
import org.bukkit.DyeColor;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.entity.TropicalFish;
import org.bukkit.entity.TropicalFish.Pattern;

public class CraftTropicalFish extends CraftFish implements TropicalFish {

    public CraftTropicalFish(CraftServer server, net.minecraft.world.entity.animal.TropicalFish entity) {
        super(server, (AbstractFish) entity);
    }

    public net.minecraft.world.entity.animal.TropicalFish getHandle() {
        return (net.minecraft.world.entity.animal.TropicalFish) this.entity;
    }

    public String toString() {
        return "CraftTropicalFish";
    }

    public DyeColor getPatternColor() {
        return getPatternColor(this.getHandle().getPackedVariant());
    }

    public void setPatternColor(DyeColor color) {
        this.getHandle().setPackedVariant(getData(color, this.getBodyColor(), this.getPattern()));
    }

    public DyeColor getBodyColor() {
        return getBodyColor(this.getHandle().getPackedVariant());
    }

    public void setBodyColor(DyeColor color) {
        this.getHandle().setPackedVariant(getData(this.getPatternColor(), color, this.getPattern()));
    }

    public Pattern getPattern() {
        return getPattern(this.getHandle().getPackedVariant());
    }

    public void setPattern(Pattern pattern) {
        this.getHandle().setPackedVariant(getData(this.getPatternColor(), this.getBodyColor(), pattern));
    }

    public static int getData(DyeColor patternColor, DyeColor bodyColor, Pattern type) {
        return patternColor.getWoolData() << 24 | bodyColor.getWoolData() << 16 | CraftTropicalFish.CraftPattern.values()[type.ordinal()].getDataValue();
    }

    public static DyeColor getPatternColor(int data) {
        return DyeColor.getByWoolData((byte) (data >> 24 & 255));
    }

    public static DyeColor getBodyColor(int data) {
        return DyeColor.getByWoolData((byte) (data >> 16 & 255));
    }

    public static Pattern getPattern(int data) {
        return CraftTropicalFish.CraftPattern.fromData(data & '\uffff');
    }

    public static enum CraftPattern {

        KOB(0, false), SUNSTREAK(1, false), SNOOPER(2, false), DASHER(3, false), BRINELY(4, false), SPOTTY(5, false), FLOPPER(0, true), STRIPEY(1, true), GLITTER(2, true), BLOCKFISH(3, true), BETTY(4, true), CLAYFISH(5, true);

        private final int variant;
        private final boolean large;
        private static final Map BY_DATA = new HashMap();

        static {
            CraftTropicalFish.CraftPattern[] acrafttropicalfish_craftpattern;
            int i = (acrafttropicalfish_craftpattern = values()).length;

            for (int j = 0; j < i; ++j) {
                CraftTropicalFish.CraftPattern type = acrafttropicalfish_craftpattern[j];

                CraftTropicalFish.CraftPattern.BY_DATA.put(type.getDataValue(), Pattern.values()[type.ordinal()]);
            }

        }

        public static Pattern fromData(int data) {
            return (Pattern) CraftTropicalFish.CraftPattern.BY_DATA.get(data);
        }

        private CraftPattern(int i, boolean large) {
            this.variant = i;
            this.large = large;
        }

        public int getDataValue() {
            return this.variant << 8 | (this.large ? 1 : 0);
        }
    }
}
