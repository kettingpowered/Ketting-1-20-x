package org.bukkit.craftbukkit.v1_20_R2.structure;

import com.google.common.base.Preconditions;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockRotProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.RegionAccessor;
import org.bukkit.World;
import org.bukkit.block.structure.Mirror;
import org.bukkit.block.structure.StructureRotation;
import org.bukkit.craftbukkit.v1_20_R2.CraftRegionAccessor;
import org.bukkit.craftbukkit.v1_20_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_20_R2.util.CraftBlockVector;
import org.bukkit.craftbukkit.v1_20_R2.util.CraftLocation;
import org.bukkit.craftbukkit.v1_20_R2.util.RandomSourceWrapper;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.structure.Structure;
import org.bukkit.util.BlockVector;

public class CraftStructure implements Structure {

    private final StructureTemplate structure;

    public CraftStructure(StructureTemplate structure) {
        this.structure = structure;
    }

    public void place(Location location, boolean includeEntities, StructureRotation structureRotation, Mirror mirror, int palette, float integrity, Random random) {
        Preconditions.checkArgument(location != null, "Location cannot be null");
        location.checkFinite();
        World world = location.getWorld();

        Preconditions.checkArgument(world != null, "The World of Location cannot be null");
        BlockVector blockVector = new BlockVector(location.getBlockX(), location.getBlockY(), location.getBlockZ());

        this.place(world, blockVector, includeEntities, structureRotation, mirror, palette, integrity, random);
    }

    public void place(RegionAccessor regionAccessor, BlockVector location, boolean includeEntities, StructureRotation structureRotation, Mirror mirror, int palette, float integrity, Random random) {
        Preconditions.checkArgument(location != null, "Location cannot be null");
        Preconditions.checkArgument(regionAccessor != null, "RegionAccessor cannot be null");
        location.checkFinite();
        Preconditions.checkArgument(integrity >= 0.0F && integrity <= 1.0F, "Integrity value (%S) must be between 0 and 1 inclusive", integrity);
        RandomSourceWrapper randomSource = new RandomSourceWrapper(random);
        StructurePlaceSettings definedstructureinfo = (new StructurePlaceSettings()).setMirror(net.minecraft.world.level.block.Mirror.valueOf(mirror.name())).setRotation(Rotation.valueOf(structureRotation.name())).setIgnoreEntities(!includeEntities).addProcessor(new BlockRotProcessor(integrity)).setRandom(randomSource);

        definedstructureinfo.palette = palette;
        BlockPos blockPosition = CraftBlockVector.toBlockPosition(location);

        this.structure.placeInWorld(((CraftRegionAccessor) regionAccessor).getHandle(), blockPosition, blockPosition, definedstructureinfo, randomSource, 2);
    }

    public void fill(Location corner1, Location corner2, boolean includeEntities) {
        Preconditions.checkArgument(corner1 != null, "Location corner1 cannot be null");
        Preconditions.checkArgument(corner2 != null, "Location corner2 cannot be null");
        World world = corner1.getWorld();

        Preconditions.checkArgument(world != null, "World of corner1 Location cannot be null");
        Location origin = new Location(world, (double) Math.min(corner1.getBlockX(), corner2.getBlockX()), (double) Math.min(corner1.getBlockY(), corner2.getBlockY()), (double) Math.min(corner1.getBlockZ(), corner2.getBlockZ()));
        BlockVector size = new BlockVector(Math.abs(corner1.getBlockX() - corner2.getBlockX()), Math.abs(corner1.getBlockY() - corner2.getBlockY()), Math.abs(corner1.getBlockZ() - corner2.getBlockZ()));

        this.fill(origin, size, includeEntities);
    }

    public void fill(Location origin, BlockVector size, boolean includeEntities) {
        Preconditions.checkArgument(origin != null, "Location origin cannot be null");
        World world = origin.getWorld();

        Preconditions.checkArgument(world != null, "World of Location origin cannot be null");
        Preconditions.checkArgument(size != null, "BlockVector size cannot be null");
        Preconditions.checkArgument(size.getBlockX() >= 1 && size.getBlockY() >= 1 && size.getBlockZ() >= 1, "Size must be at least 1x1x1 but was %sx%sx%s", size.getBlockX(), size.getBlockY(), size.getBlockZ());
        this.structure.fillFromWorld(((CraftWorld) world).getHandle(), CraftLocation.toBlockPosition(origin), CraftBlockVector.toBlockPosition(size), includeEntities, Blocks.STRUCTURE_VOID);
    }

    public BlockVector getSize() {
        return CraftBlockVector.toBukkit(this.structure.getSize());
    }

    public List getEntities() {
        ArrayList entities = new ArrayList();
        Iterator iterator = this.structure.entityInfoList.iterator();

        while (iterator.hasNext()) {
            StructureTemplate.StructureEntityInfo entity = (StructureTemplate.StructureEntityInfo) iterator.next();

            EntityType.create(entity.nbt, ((CraftWorld) Bukkit.getServer().getWorlds().get(0)).getHandle()).ifPresent((dummyEntityx) -> {
                dummyEntityx.setPos(entity.pos.x, entity.pos.y, entity.pos.z);
                entities.add(dummyEntityx.getBukkitEntity());
            });
        }

        return Collections.unmodifiableList(entities);
    }

    public int getEntityCount() {
        return this.structure.entityInfoList.size();
    }

    public List getPalettes() {
        return (List) this.structure.palettes.stream().map(CraftPalette::new).collect(Collectors.toList());
    }

    public int getPaletteCount() {
        return this.structure.palettes.size();
    }

    public PersistentDataContainer getPersistentDataContainer() {
        return this.getHandle().persistentDataContainer;
    }

    public StructureTemplate getHandle() {
        return this.structure;
    }
}
