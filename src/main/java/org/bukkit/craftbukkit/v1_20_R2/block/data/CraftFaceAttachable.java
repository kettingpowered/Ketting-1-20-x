package org.bukkit.craftbukkit.v1_20_R2.block.data;

import net.minecraft.world.level.block.state.properties.EnumProperty;
import org.bukkit.block.data.FaceAttachable;
import org.bukkit.block.data.FaceAttachable.AttachedFace;

public abstract class CraftFaceAttachable extends CraftBlockData implements FaceAttachable {

    private static final EnumProperty ATTACH_FACE = getEnum("face");

    public AttachedFace getAttachedFace() {
        return (AttachedFace) this.get(CraftFaceAttachable.ATTACH_FACE, AttachedFace.class);
    }

    public void setAttachedFace(AttachedFace face) {
        this.set(CraftFaceAttachable.ATTACH_FACE, (Enum) face);
    }
}
