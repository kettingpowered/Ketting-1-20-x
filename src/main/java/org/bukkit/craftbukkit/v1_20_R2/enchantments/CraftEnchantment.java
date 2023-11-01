package org.bukkit.craftbukkit.v1_20_R2.enchantments;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.enchantment.BindingCurseEnchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.VanishingCurseEnchantment;
import org.bukkit.craftbukkit.v1_20_R2.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_20_R2.util.CraftNamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.enchantments.EnchantmentWrapper;
import org.bukkit.inventory.ItemStack;

public class CraftEnchantment extends Enchantment {

    private final net.minecraft.world.item.enchantment.Enchantment target;
    private static volatile int[] $SWITCH_TABLE$net$minecraft$world$item$enchantment$EnchantmentSlotType;

    public CraftEnchantment(net.minecraft.world.item.enchantment.Enchantment target) {
        super(CraftNamespacedKey.fromMinecraft(BuiltInRegistries.ENCHANTMENT.getKey(target)));
        this.target = target;
    }

    public int getMaxLevel() {
        return this.target.getMaxLevel();
    }

    public int getStartLevel() {
        return this.target.getMinLevel();
    }

    public EnchantmentTarget getItemTarget() {
        switch ($SWITCH_TABLE$net$minecraft$world$item$enchantment$EnchantmentSlotType()[this.target.category.ordinal()]) {
            case 1:
                return EnchantmentTarget.ARMOR;
            case 2:
                return EnchantmentTarget.ARMOR_FEET;
            case 3:
                return EnchantmentTarget.ARMOR_LEGS;
            case 4:
                return EnchantmentTarget.ARMOR_TORSO;
            case 5:
                return EnchantmentTarget.ARMOR_HEAD;
            case 6:
                return EnchantmentTarget.WEAPON;
            case 7:
                return EnchantmentTarget.TOOL;
            case 8:
                return EnchantmentTarget.FISHING_ROD;
            case 9:
                return EnchantmentTarget.TRIDENT;
            case 10:
                return EnchantmentTarget.BREAKABLE;
            case 11:
                return EnchantmentTarget.BOW;
            case 12:
                return EnchantmentTarget.WEARABLE;
            case 13:
                return EnchantmentTarget.CROSSBOW;
            case 14:
                return EnchantmentTarget.VANISHABLE;
            default:
                return null;
        }
    }

    public boolean isTreasure() {
        return this.target.isTreasureOnly();
    }

    public boolean isCursed() {
        return this.target instanceof BindingCurseEnchantment || this.target instanceof VanishingCurseEnchantment;
    }

    public boolean canEnchantItem(ItemStack item) {
        return this.target.canEnchant(CraftItemStack.asNMSCopy(item));
    }

    public String getName() {
        switch (BuiltInRegistries.ENCHANTMENT.getId(this.target)) {
            case 0:
                return "PROTECTION_ENVIRONMENTAL";
            case 1:
                return "PROTECTION_FIRE";
            case 2:
                return "PROTECTION_FALL";
            case 3:
                return "PROTECTION_EXPLOSIONS";
            case 4:
                return "PROTECTION_PROJECTILE";
            case 5:
                return "OXYGEN";
            case 6:
                return "WATER_WORKER";
            case 7:
                return "THORNS";
            case 8:
                return "DEPTH_STRIDER";
            case 9:
                return "FROST_WALKER";
            case 10:
                return "BINDING_CURSE";
            case 11:
                return "SOUL_SPEED";
            case 12:
                return "SWIFT_SNEAK";
            case 13:
                return "DAMAGE_ALL";
            case 14:
                return "DAMAGE_UNDEAD";
            case 15:
                return "DAMAGE_ARTHROPODS";
            case 16:
                return "KNOCKBACK";
            case 17:
                return "FIRE_ASPECT";
            case 18:
                return "LOOT_BONUS_MOBS";
            case 19:
                return "SWEEPING_EDGE";
            case 20:
                return "DIG_SPEED";
            case 21:
                return "SILK_TOUCH";
            case 22:
                return "DURABILITY";
            case 23:
                return "LOOT_BONUS_BLOCKS";
            case 24:
                return "ARROW_DAMAGE";
            case 25:
                return "ARROW_KNOCKBACK";
            case 26:
                return "ARROW_FIRE";
            case 27:
                return "ARROW_INFINITE";
            case 28:
                return "LUCK";
            case 29:
                return "LURE";
            case 30:
                return "LOYALTY";
            case 31:
                return "IMPALING";
            case 32:
                return "RIPTIDE";
            case 33:
                return "CHANNELING";
            case 34:
                return "MULTISHOT";
            case 35:
                return "QUICK_CHARGE";
            case 36:
                return "PIERCING";
            case 37:
                return "MENDING";
            case 38:
                return "VANISHING_CURSE";
            default:
                return "UNKNOWN_ENCHANT_" + BuiltInRegistries.ENCHANTMENT.getId(this.target);
        }
    }

    public static net.minecraft.world.item.enchantment.Enchantment getRaw(Enchantment enchantment) {
        if (enchantment instanceof EnchantmentWrapper) {
            enchantment = ((EnchantmentWrapper) enchantment).getEnchantment();
        }

        return enchantment instanceof CraftEnchantment ? ((CraftEnchantment) enchantment).target : null;
    }

    public boolean conflictsWith(Enchantment other) {
        if (other instanceof EnchantmentWrapper) {
            other = ((EnchantmentWrapper) other).getEnchantment();
        }

        if (!(other instanceof CraftEnchantment)) {
            return false;
        } else {
            CraftEnchantment ench = (CraftEnchantment) other;

            return !this.target.isCompatibleWith(ench.target);
        }
    }

    public net.minecraft.world.item.enchantment.Enchantment getHandle() {
        return this.target;
    }

    static int[] $SWITCH_TABLE$net$minecraft$world$item$enchantment$EnchantmentSlotType() {
        int[] aint = CraftEnchantment.$SWITCH_TABLE$net$minecraft$world$item$enchantment$EnchantmentSlotType;

        if (aint != null) {
            return aint;
        } else {
            int[] aint1 = new int[EnchantmentCategory.values().length];

            try {
                aint1[EnchantmentCategory.ARMOR.ordinal()] = 1;
            } catch (NoSuchFieldError nosuchfielderror) {
                ;
            }

            try {
                aint1[EnchantmentCategory.ARMOR_CHEST.ordinal()] = 4;
            } catch (NoSuchFieldError nosuchfielderror1) {
                ;
            }

            try {
                aint1[EnchantmentCategory.ARMOR_FEET.ordinal()] = 2;
            } catch (NoSuchFieldError nosuchfielderror2) {
                ;
            }

            try {
                aint1[EnchantmentCategory.ARMOR_HEAD.ordinal()] = 5;
            } catch (NoSuchFieldError nosuchfielderror3) {
                ;
            }

            try {
                aint1[EnchantmentCategory.ARMOR_LEGS.ordinal()] = 3;
            } catch (NoSuchFieldError nosuchfielderror4) {
                ;
            }

            try {
                aint1[EnchantmentCategory.BOW.ordinal()] = 11;
            } catch (NoSuchFieldError nosuchfielderror5) {
                ;
            }

            try {
                aint1[EnchantmentCategory.BREAKABLE.ordinal()] = 10;
            } catch (NoSuchFieldError nosuchfielderror6) {
                ;
            }

            try {
                aint1[EnchantmentCategory.CROSSBOW.ordinal()] = 13;
            } catch (NoSuchFieldError nosuchfielderror7) {
                ;
            }

            try {
                aint1[EnchantmentCategory.DIGGER.ordinal()] = 7;
            } catch (NoSuchFieldError nosuchfielderror8) {
                ;
            }

            try {
                aint1[EnchantmentCategory.FISHING_ROD.ordinal()] = 8;
            } catch (NoSuchFieldError nosuchfielderror9) {
                ;
            }

            try {
                aint1[EnchantmentCategory.TRIDENT.ordinal()] = 9;
            } catch (NoSuchFieldError nosuchfielderror10) {
                ;
            }

            try {
                aint1[EnchantmentCategory.VANISHABLE.ordinal()] = 14;
            } catch (NoSuchFieldError nosuchfielderror11) {
                ;
            }

            try {
                aint1[EnchantmentCategory.WEAPON.ordinal()] = 6;
            } catch (NoSuchFieldError nosuchfielderror12) {
                ;
            }

            try {
                aint1[EnchantmentCategory.WEARABLE.ordinal()] = 12;
            } catch (NoSuchFieldError nosuchfielderror13) {
                ;
            }

            CraftEnchantment.$SWITCH_TABLE$net$minecraft$world$item$enchantment$EnchantmentSlotType = aint1;
            return aint1;
        }
    }
}
