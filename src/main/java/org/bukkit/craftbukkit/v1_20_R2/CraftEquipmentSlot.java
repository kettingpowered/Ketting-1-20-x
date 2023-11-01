package org.bukkit.craftbukkit.v1_20_R2;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;

public class CraftEquipmentSlot {

    private static final EquipmentSlot[] slots = new EquipmentSlot[org.bukkit.inventory.EquipmentSlot.values().length];
    private static final org.bukkit.inventory.EquipmentSlot[] enums = new org.bukkit.inventory.EquipmentSlot[EquipmentSlot.values().length];

    static {
        set(org.bukkit.inventory.EquipmentSlot.HAND, EquipmentSlot.MAINHAND);
        set(org.bukkit.inventory.EquipmentSlot.OFF_HAND, EquipmentSlot.OFFHAND);
        set(org.bukkit.inventory.EquipmentSlot.FEET, EquipmentSlot.FEET);
        set(org.bukkit.inventory.EquipmentSlot.LEGS, EquipmentSlot.LEGS);
        set(org.bukkit.inventory.EquipmentSlot.CHEST, EquipmentSlot.CHEST);
        set(org.bukkit.inventory.EquipmentSlot.HEAD, EquipmentSlot.HEAD);
    }

    private static void set(org.bukkit.inventory.EquipmentSlot type, EquipmentSlot value) {
        CraftEquipmentSlot.slots[type.ordinal()] = value;
        CraftEquipmentSlot.enums[value.ordinal()] = type;
    }

    public static org.bukkit.inventory.EquipmentSlot getSlot(EquipmentSlot nms) {
        return CraftEquipmentSlot.enums[nms.ordinal()];
    }

    public static EquipmentSlot getNMS(org.bukkit.inventory.EquipmentSlot slot) {
        return CraftEquipmentSlot.slots[slot.ordinal()];
    }

    public static org.bukkit.inventory.EquipmentSlot getHand(InteractionHand enumhand) {
        return enumhand == InteractionHand.MAIN_HAND ? org.bukkit.inventory.EquipmentSlot.HAND : org.bukkit.inventory.EquipmentSlot.OFF_HAND;
    }

    public static InteractionHand getHand(org.bukkit.inventory.EquipmentSlot hand) {
        if (hand == org.bukkit.inventory.EquipmentSlot.HAND) {
            return InteractionHand.MAIN_HAND;
        } else if (hand == org.bukkit.inventory.EquipmentSlot.OFF_HAND) {
            return InteractionHand.OFF_HAND;
        } else {
            throw new IllegalArgumentException("EquipmentSlot." + hand + " is not a hand");
        }
    }
}
