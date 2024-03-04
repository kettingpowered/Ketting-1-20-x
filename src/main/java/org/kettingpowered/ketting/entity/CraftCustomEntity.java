package org.kettingpowered.ketting.entity;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import org.bukkit.craftbukkit.v1_20_R1.CraftServer;
import org.bukkit.craftbukkit.v1_20_R1.entity.CraftEntity;
import org.bukkit.entity.LivingEntity;

public class CraftCustomEntity extends CraftEntity {

    private String entityName;

    public CraftCustomEntity(CraftServer server, Entity entity) {
        super(server, entity);
        entityName = entity.getName().getString();
    }

    public LivingEntity asLivingEntity() {
        try {
            return (LivingEntity) entity;
        } catch (ClassCastException e) {
            System.err.println("Attempted to call asLivingEntity() on a non-LivingEntity entity");
            System.err.println("Entity name: " + entityName);
            System.err.println("Entity type: " + entity.getType());
            System.err.println("Entity class: " + entity.getClass());
            return null;
        }
    }

    @Override
    public String toString() {
        return entityName;
    }

    @Override
    public String getCustomName() {
        Component component = this.getHandle().getCustomName();
        if (component == null) return this.entity.getName().getString();
        return component.getString();
    }
}
