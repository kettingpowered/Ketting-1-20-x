package org.bukkit.craftbukkit.v1_20_R2.attribute;

import com.google.common.base.Preconditions;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.attribute.AttributeModifier.Operation;
import org.bukkit.inventory.EquipmentSlot;

public class CraftAttributeInstance implements AttributeInstance {

    private final net.minecraft.world.entity.ai.attributes.AttributeInstance handle;
    private final Attribute attribute;

    public CraftAttributeInstance(net.minecraft.world.entity.ai.attributes.AttributeInstance handle, Attribute attribute) {
        this.handle = handle;
        this.attribute = attribute;
    }

    public Attribute getAttribute() {
        return this.attribute;
    }

    public double getBaseValue() {
        return this.handle.getBaseValue();
    }

    public void setBaseValue(double d) {
        this.handle.setBaseValue(d);
    }

    public Collection getModifiers() {
        ArrayList result = new ArrayList();
        Iterator iterator = this.handle.getModifiers().iterator();

        while (iterator.hasNext()) {
            AttributeModifier nms = (AttributeModifier) iterator.next();

            result.add(convert(nms));
        }

        return result;
    }

    public void addModifier(org.bukkit.attribute.AttributeModifier modifier) {
        Preconditions.checkArgument(modifier != null, "modifier");
        this.handle.addPermanentModifier(convert(modifier));
    }

    public void removeModifier(org.bukkit.attribute.AttributeModifier modifier) {
        Preconditions.checkArgument(modifier != null, "modifier");
        this.handle.removeModifier(convert(modifier));
    }

    public double getValue() {
        return this.handle.getValue();
    }

    public double getDefaultValue() {
        return this.handle.getAttribute().getDefaultValue();
    }

    public static AttributeModifier convert(org.bukkit.attribute.AttributeModifier bukkit) {
        return new AttributeModifier(bukkit.getUniqueId(), bukkit.getName(), bukkit.getAmount(), AttributeModifier.Operation.values()[bukkit.getOperation().ordinal()]);
    }

    public static org.bukkit.attribute.AttributeModifier convert(AttributeModifier nms) {
        return new org.bukkit.attribute.AttributeModifier(nms.getId(), nms.getName(), nms.getAmount(), Operation.values()[nms.getOperation().ordinal()]);
    }

    public static org.bukkit.attribute.AttributeModifier convert(AttributeModifier nms, EquipmentSlot slot) {
        return new org.bukkit.attribute.AttributeModifier(nms.getId(), nms.getName(), nms.getAmount(), Operation.values()[nms.getOperation().ordinal()], slot);
    }
}
