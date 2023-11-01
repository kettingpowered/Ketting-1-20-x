package org.bukkit.craftbukkit.v1_20_R2.block;

import net.minecraft.network.chat.Component;
import net.minecraft.world.LockCode;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import org.bukkit.World;
import org.bukkit.block.Container;
import org.bukkit.craftbukkit.v1_20_R2.util.CraftChatMessage;

public abstract class CraftContainer extends CraftBlockEntityState implements Container {

    public CraftContainer(World world, BaseContainerBlockEntity tileEntity) {
        super(world, tileEntity);
    }

    protected CraftContainer(CraftContainer state) {
        super((CraftBlockEntityState) state);
    }

    public boolean isLocked() {
        return !((BaseContainerBlockEntity) this.getSnapshot()).lockKey.key.isEmpty();
    }

    public String getLock() {
        return ((BaseContainerBlockEntity) this.getSnapshot()).lockKey.key;
    }

    public void setLock(String key) {
        ((BaseContainerBlockEntity) this.getSnapshot()).lockKey = key == null ? LockCode.NO_LOCK : new LockCode(key);
    }

    public String getCustomName() {
        BaseContainerBlockEntity container = (BaseContainerBlockEntity) this.getSnapshot();

        return container.name != null ? CraftChatMessage.fromComponent(container.getCustomName()) : null;
    }

    public void setCustomName(String name) {
        ((BaseContainerBlockEntity) this.getSnapshot()).setCustomName(CraftChatMessage.fromStringOrNull(name));
    }

    public void applyTo(BaseContainerBlockEntity container) {
        super.applyTo(container);
        if (((BaseContainerBlockEntity) this.getSnapshot()).name == null) {
            container.setCustomName((Component) null);
        }

    }

    public abstract CraftContainer copy();
}
