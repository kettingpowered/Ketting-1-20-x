package org.bukkit.craftbukkit.v1_20_R2.block;

import com.google.common.base.Preconditions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.StructureBlock;
import net.minecraft.world.level.block.entity.StructureBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.StructureMode;
import org.bukkit.World;
import org.bukkit.block.Structure;
import org.bukkit.block.structure.Mirror;
import org.bukkit.block.structure.StructureRotation;
import org.bukkit.block.structure.UsageMode;
import org.bukkit.craftbukkit.v1_20_R2.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_20_R2.util.CraftBlockVector;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.BlockVector;

public class CraftStructureBlock extends CraftBlockEntityState implements Structure {

    private static final int MAX_SIZE = 48;

    public CraftStructureBlock(World world, StructureBlockEntity tileEntity) {
        super(world, tileEntity);
    }

    protected CraftStructureBlock(CraftStructureBlock state) {
        super((CraftBlockEntityState) state);
    }

    public String getStructureName() {
        return ((StructureBlockEntity) this.getSnapshot()).getStructureName();
    }

    public void setStructureName(String name) {
        Preconditions.checkArgument(name != null, "Structure name cannot be null");
        ((StructureBlockEntity) this.getSnapshot()).setStructureName(name);
    }

    public String getAuthor() {
        return ((StructureBlockEntity) this.getSnapshot()).author;
    }

    public void setAuthor(String author) {
        Preconditions.checkArgument(author != null, "Author name cannot be null");
        Preconditions.checkArgument(!author.isEmpty(), "Author name cannot be empty");
        ((StructureBlockEntity) this.getSnapshot()).author = author;
    }

    public void setAuthor(LivingEntity entity) {
        Preconditions.checkArgument(entity != null, "Structure Block author entity cannot be null");
        ((StructureBlockEntity) this.getSnapshot()).createdBy(((CraftLivingEntity) entity).getHandle());
    }

    public BlockVector getRelativePosition() {
        return CraftBlockVector.toBukkit(((StructureBlockEntity) this.getSnapshot()).structurePos);
    }

    public void setRelativePosition(BlockVector vector) {
        Preconditions.checkArgument(isBetween(vector.getBlockX(), -48, 48), "Structure Size (X) must be between -%s and %s but got %s", 48, 48, vector.getBlockX());
        Preconditions.checkArgument(isBetween(vector.getBlockY(), -48, 48), "Structure Size (Y) must be between -%s and %s but got %s", 48, 48, vector.getBlockY());
        Preconditions.checkArgument(isBetween(vector.getBlockZ(), -48, 48), "Structure Size (Z) must be between -%s and %s but got %s", 48, 48, vector.getBlockZ());
        ((StructureBlockEntity) this.getSnapshot()).structurePos = CraftBlockVector.toBlockPosition(vector);
    }

    public BlockVector getStructureSize() {
        return CraftBlockVector.toBukkit(((StructureBlockEntity) this.getSnapshot()).structureSize);
    }

    public void setStructureSize(BlockVector vector) {
        Preconditions.checkArgument(isBetween(vector.getBlockX(), 0, 48), "Structure Size (X) must be between %s and %s but got %s", 0, 48, vector.getBlockX());
        Preconditions.checkArgument(isBetween(vector.getBlockY(), 0, 48), "Structure Size (Y) must be between %s and %s but got %s", 0, 48, vector.getBlockY());
        Preconditions.checkArgument(isBetween(vector.getBlockZ(), 0, 48), "Structure Size (Z) must be between %s and %s but got %s", 0, 48, vector.getBlockZ());
        ((StructureBlockEntity) this.getSnapshot()).structureSize = CraftBlockVector.toBlockPosition(vector);
    }

    public void setMirror(Mirror mirror) {
        Preconditions.checkArgument(mirror != null, "Mirror cannot be null");
        ((StructureBlockEntity) this.getSnapshot()).mirror = net.minecraft.world.level.block.Mirror.valueOf(mirror.name());
    }

    public Mirror getMirror() {
        return Mirror.valueOf(((StructureBlockEntity) this.getSnapshot()).mirror.name());
    }

    public void setRotation(StructureRotation rotation) {
        Preconditions.checkArgument(rotation != null, "StructureRotation cannot be null");
        ((StructureBlockEntity) this.getSnapshot()).rotation = Rotation.valueOf(rotation.name());
    }

    public StructureRotation getRotation() {
        return StructureRotation.valueOf(((StructureBlockEntity) this.getSnapshot()).rotation.name());
    }

    public void setUsageMode(UsageMode mode) {
        Preconditions.checkArgument(mode != null, "UsageMode cannot be null");
        ((StructureBlockEntity) this.getSnapshot()).mode = StructureMode.valueOf(mode.name());
    }

    public UsageMode getUsageMode() {
        return UsageMode.valueOf(((StructureBlockEntity) this.getSnapshot()).getMode().name());
    }

    public void setIgnoreEntities(boolean flag) {
        ((StructureBlockEntity) this.getSnapshot()).ignoreEntities = flag;
    }

    public boolean isIgnoreEntities() {
        return ((StructureBlockEntity) this.getSnapshot()).ignoreEntities;
    }

    public void setShowAir(boolean showAir) {
        ((StructureBlockEntity) this.getSnapshot()).showAir = showAir;
    }

    public boolean isShowAir() {
        return ((StructureBlockEntity) this.getSnapshot()).showAir;
    }

    public void setBoundingBoxVisible(boolean showBoundingBox) {
        ((StructureBlockEntity) this.getSnapshot()).showBoundingBox = showBoundingBox;
    }

    public boolean isBoundingBoxVisible() {
        return ((StructureBlockEntity) this.getSnapshot()).showBoundingBox;
    }

    public void setIntegrity(float integrity) {
        Preconditions.checkArgument(isBetween(integrity, 0.0F, 1.0F), "Integrity must be between 0.0f and 1.0f but got %s", integrity);
        ((StructureBlockEntity) this.getSnapshot()).integrity = integrity;
    }

    public float getIntegrity() {
        return ((StructureBlockEntity) this.getSnapshot()).integrity;
    }

    public void setSeed(long seed) {
        ((StructureBlockEntity) this.getSnapshot()).seed = seed;
    }

    public long getSeed() {
        return ((StructureBlockEntity) this.getSnapshot()).seed;
    }

    public void setMetadata(String metadata) {
        Preconditions.checkArgument(metadata != null, "Structure metadata cannot be null");
        if (this.getUsageMode() == UsageMode.DATA) {
            ((StructureBlockEntity) this.getSnapshot()).metaData = metadata;
        }

    }

    public String getMetadata() {
        return ((StructureBlockEntity) this.getSnapshot()).metaData;
    }

    protected void applyTo(StructureBlockEntity tileEntity) {
        super.applyTo(tileEntity);
        LevelAccessor access = this.getWorldHandle();

        if (access instanceof Level) {
            tileEntity.setMode(tileEntity.getMode());
        } else if (access != null) {
            BlockState data = access.getBlockState(this.getPosition());

            if (data.is(Blocks.STRUCTURE_BLOCK)) {
                access.setBlock(this.getPosition(), (BlockState) data.setValue(StructureBlock.MODE, tileEntity.getMode()), 2);
            }
        }

    }

    public CraftStructureBlock copy() {
        return new CraftStructureBlock(this);
    }

    private static boolean isBetween(int num, int min, int max) {
        return num >= min && num <= max;
    }

    private static boolean isBetween(float num, float min, float max) {
        return num >= min && num <= max;
    }
}
