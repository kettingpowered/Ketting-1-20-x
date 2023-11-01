package org.bukkit.craftbukkit.v1_20_R2.util;

import com.google.common.base.Preconditions;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import net.minecraft.world.phys.AABB;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.VoxelShape;

public final class CraftVoxelShape implements VoxelShape {

    private final net.minecraft.world.phys.shapes.VoxelShape shape;

    public CraftVoxelShape(net.minecraft.world.phys.shapes.VoxelShape shape) {
        this.shape = shape;
    }

    public Collection getBoundingBoxes() {
        List boxes = this.shape.toAabbs();
        ArrayList craftBoxes = new ArrayList(boxes.size());
        Iterator iterator = boxes.iterator();

        while (iterator.hasNext()) {
            AABB aabb = (AABB) iterator.next();

            craftBoxes.add(new BoundingBox(aabb.minX, aabb.minY, aabb.minZ, aabb.maxX, aabb.maxY, aabb.maxZ));
        }

        return craftBoxes;
    }

    public boolean overlaps(BoundingBox other) {
        Preconditions.checkArgument(other != null, "Other cannot be null");
        Iterator iterator = this.getBoundingBoxes().iterator();

        while (iterator.hasNext()) {
            BoundingBox box = (BoundingBox) iterator.next();

            if (box.overlaps(other)) {
                return true;
            }
        }

        return false;
    }
}
