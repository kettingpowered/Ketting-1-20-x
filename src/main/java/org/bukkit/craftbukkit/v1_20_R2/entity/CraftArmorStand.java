package org.bukkit.craftbukkit.v1_20_R2.entity;

import net.minecraft.core.Rotations;
import net.minecraft.world.entity.LivingEntity;
import org.bukkit.craftbukkit.v1_20_R2.CraftEquipmentSlot;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.ArmorStand.LockType;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.EulerAngle;

public class CraftArmorStand extends CraftLivingEntity implements ArmorStand {

    public CraftArmorStand(CraftServer server, net.minecraft.world.entity.decoration.ArmorStand entity) {
        super(server, (LivingEntity) entity);
    }

    public String toString() {
        return "CraftArmorStand";
    }

    public net.minecraft.world.entity.decoration.ArmorStand getHandle() {
        return (net.minecraft.world.entity.decoration.ArmorStand) super.getHandle();
    }

    public ItemStack getItemInHand() {
        return this.getEquipment().getItemInHand();
    }

    public void setItemInHand(ItemStack item) {
        this.getEquipment().setItemInHand(item);
    }

    public ItemStack getBoots() {
        return this.getEquipment().getBoots();
    }

    public void setBoots(ItemStack item) {
        this.getEquipment().setBoots(item);
    }

    public ItemStack getLeggings() {
        return this.getEquipment().getLeggings();
    }

    public void setLeggings(ItemStack item) {
        this.getEquipment().setLeggings(item);
    }

    public ItemStack getChestplate() {
        return this.getEquipment().getChestplate();
    }

    public void setChestplate(ItemStack item) {
        this.getEquipment().setChestplate(item);
    }

    public ItemStack getHelmet() {
        return this.getEquipment().getHelmet();
    }

    public void setHelmet(ItemStack item) {
        this.getEquipment().setHelmet(item);
    }

    public EulerAngle getBodyPose() {
        return fromNMS(this.getHandle().bodyPose);
    }

    public void setBodyPose(EulerAngle pose) {
        this.getHandle().setBodyPose(toNMS(pose));
    }

    public EulerAngle getLeftArmPose() {
        return fromNMS(this.getHandle().leftArmPose);
    }

    public void setLeftArmPose(EulerAngle pose) {
        this.getHandle().setLeftArmPose(toNMS(pose));
    }

    public EulerAngle getRightArmPose() {
        return fromNMS(this.getHandle().rightArmPose);
    }

    public void setRightArmPose(EulerAngle pose) {
        this.getHandle().setRightArmPose(toNMS(pose));
    }

    public EulerAngle getLeftLegPose() {
        return fromNMS(this.getHandle().leftLegPose);
    }

    public void setLeftLegPose(EulerAngle pose) {
        this.getHandle().setLeftLegPose(toNMS(pose));
    }

    public EulerAngle getRightLegPose() {
        return fromNMS(this.getHandle().rightLegPose);
    }

    public void setRightLegPose(EulerAngle pose) {
        this.getHandle().setRightLegPose(toNMS(pose));
    }

    public EulerAngle getHeadPose() {
        return fromNMS(this.getHandle().headPose);
    }

    public void setHeadPose(EulerAngle pose) {
        this.getHandle().setHeadPose(toNMS(pose));
    }

    public boolean hasBasePlate() {
        return !this.getHandle().isNoBasePlate();
    }

    public void setBasePlate(boolean basePlate) {
        this.getHandle().setNoBasePlate(!basePlate);
    }

    public void setGravity(boolean gravity) {
        super.setGravity(gravity);
        this.getHandle().noPhysics = !gravity;
    }

    public boolean isVisible() {
        return !this.getHandle().isInvisible();
    }

    public void setVisible(boolean visible) {
        this.getHandle().setInvisible(!visible);
    }

    public boolean hasArms() {
        return this.getHandle().isShowArms();
    }

    public void setArms(boolean arms) {
        this.getHandle().setShowArms(arms);
    }

    public boolean isSmall() {
        return this.getHandle().isSmall();
    }

    public void setSmall(boolean small) {
        this.getHandle().setSmall(small);
    }

    private static EulerAngle fromNMS(Rotations old) {
        return new EulerAngle(Math.toRadians((double) old.getX()), Math.toRadians((double) old.getY()), Math.toRadians((double) old.getZ()));
    }

    private static Rotations toNMS(EulerAngle old) {
        return new Rotations((float) Math.toDegrees(old.getX()), (float) Math.toDegrees(old.getY()), (float) Math.toDegrees(old.getZ()));
    }

    public boolean isMarker() {
        return this.getHandle().isMarker();
    }

    public void setMarker(boolean marker) {
        this.getHandle().setMarker(marker);
    }

    public void addEquipmentLock(EquipmentSlot equipmentSlot, LockType lockType) {
        net.minecraft.world.entity.decoration.ArmorStand net_minecraft_world_entity_decoration_armorstand = this.getHandle();

        net_minecraft_world_entity_decoration_armorstand.disabledSlots |= 1 << CraftEquipmentSlot.getNMS(equipmentSlot).getFilterFlag() + lockType.ordinal() * 8;
    }

    public void removeEquipmentLock(EquipmentSlot equipmentSlot, LockType lockType) {
        net.minecraft.world.entity.decoration.ArmorStand net_minecraft_world_entity_decoration_armorstand = this.getHandle();

        net_minecraft_world_entity_decoration_armorstand.disabledSlots &= ~(1 << CraftEquipmentSlot.getNMS(equipmentSlot).getFilterFlag() + lockType.ordinal() * 8);
    }

    public boolean hasEquipmentLock(EquipmentSlot equipmentSlot, LockType lockType) {
        return (this.getHandle().disabledSlots & 1 << CraftEquipmentSlot.getNMS(equipmentSlot).getFilterFlag() + lockType.ordinal() * 8) != 0;
    }
}
