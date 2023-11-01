package org.bukkit.craftbukkit.v1_20_R2.block.data.type;

import net.minecraft.world.level.block.state.properties.EnumProperty;
import org.bukkit.block.data.type.Bell;
import org.bukkit.block.data.type.Bell.Attachment;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;

public abstract class CraftBell extends CraftBlockData implements Bell {

    private static final EnumProperty ATTACHMENT = getEnum("attachment");

    public Attachment getAttachment() {
        return (Attachment) this.get(CraftBell.ATTACHMENT, Attachment.class);
    }

    public void setAttachment(Attachment leaves) {
        this.set(CraftBell.ATTACHMENT, (Enum) leaves);
    }
}
