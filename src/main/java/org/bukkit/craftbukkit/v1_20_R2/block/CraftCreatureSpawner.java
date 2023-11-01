package org.bukkit.craftbukkit.v1_20_R2.block;

import com.google.common.base.Preconditions;
import java.util.Optional;
import net.minecraft.util.RandomSource;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.world.level.SpawnData;
import net.minecraft.world.level.block.entity.SpawnerBlockEntity;
import org.bukkit.World;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.craftbukkit.v1_20_R2.entity.CraftEntityType;
import org.bukkit.entity.EntityType;

public class CraftCreatureSpawner extends CraftBlockEntityState implements CreatureSpawner {

    public CraftCreatureSpawner(World world, SpawnerBlockEntity tileEntity) {
        super(world, tileEntity);
    }

    protected CraftCreatureSpawner(CraftCreatureSpawner state) {
        super((CraftBlockEntityState) state);
    }

    public EntityType getSpawnedType() {
        SpawnData spawnData = ((SpawnerBlockEntity) this.getSnapshot()).getSpawner().nextSpawnData;

        if (spawnData == null) {
            return null;
        } else {
            Optional type = net.minecraft.world.entity.EntityType.by(spawnData.getEntityToSpawn());

            return (EntityType) type.map(CraftEntityType::minecraftToBukkit).orElse((Object) null);
        }
    }

    public void setSpawnedType(EntityType entityType) {
        if (entityType == null) {
            ((SpawnerBlockEntity) this.getSnapshot()).getSpawner().spawnPotentials = SimpleWeightedRandomList.empty();
            ((SpawnerBlockEntity) this.getSnapshot()).getSpawner().nextSpawnData = new SpawnData();
        } else {
            Preconditions.checkArgument(entityType != EntityType.UNKNOWN, "Can't spawn EntityType %s from mob spawners!", entityType);
            RandomSource rand = this.isPlaced() ? this.getWorldHandle().getRandom() : RandomSource.create();

            ((SpawnerBlockEntity) this.getSnapshot()).setEntityId(CraftEntityType.bukkitToMinecraft(entityType), rand);
        }
    }

    public String getCreatureTypeName() {
        SpawnData spawnData = ((SpawnerBlockEntity) this.getSnapshot()).getSpawner().nextSpawnData;

        if (spawnData == null) {
            return null;
        } else {
            Optional type = net.minecraft.world.entity.EntityType.by(spawnData.getEntityToSpawn());

            return (String) type.map((entityTypesx) -> {
                return net.minecraft.world.entity.EntityType.getKey(entityTypesx).getPath();
            }).orElse((Object) null);
        }
    }

    public void setCreatureTypeByName(String creatureType) {
        EntityType type = EntityType.fromName(creatureType);

        if (type == null) {
            this.setSpawnedType((EntityType) null);
        } else {
            this.setSpawnedType(type);
        }
    }

    public int getDelay() {
        return ((SpawnerBlockEntity) this.getSnapshot()).getSpawner().spawnDelay;
    }

    public void setDelay(int delay) {
        ((SpawnerBlockEntity) this.getSnapshot()).getSpawner().spawnDelay = delay;
    }

    public int getMinSpawnDelay() {
        return ((SpawnerBlockEntity) this.getSnapshot()).getSpawner().minSpawnDelay;
    }

    public void setMinSpawnDelay(int spawnDelay) {
        Preconditions.checkArgument(spawnDelay <= this.getMaxSpawnDelay(), "Minimum Spawn Delay must be less than or equal to Maximum Spawn Delay");
        ((SpawnerBlockEntity) this.getSnapshot()).getSpawner().minSpawnDelay = spawnDelay;
    }

    public int getMaxSpawnDelay() {
        return ((SpawnerBlockEntity) this.getSnapshot()).getSpawner().maxSpawnDelay;
    }

    public void setMaxSpawnDelay(int spawnDelay) {
        Preconditions.checkArgument(spawnDelay > 0, "Maximum Spawn Delay must be greater than 0.");
        Preconditions.checkArgument(spawnDelay >= this.getMinSpawnDelay(), "Maximum Spawn Delay must be greater than or equal to Minimum Spawn Delay");
        ((SpawnerBlockEntity) this.getSnapshot()).getSpawner().maxSpawnDelay = spawnDelay;
    }

    public int getMaxNearbyEntities() {
        return ((SpawnerBlockEntity) this.getSnapshot()).getSpawner().maxNearbyEntities;
    }

    public void setMaxNearbyEntities(int maxNearbyEntities) {
        ((SpawnerBlockEntity) this.getSnapshot()).getSpawner().maxNearbyEntities = maxNearbyEntities;
    }

    public int getSpawnCount() {
        return ((SpawnerBlockEntity) this.getSnapshot()).getSpawner().spawnCount;
    }

    public void setSpawnCount(int count) {
        ((SpawnerBlockEntity) this.getSnapshot()).getSpawner().spawnCount = count;
    }

    public int getRequiredPlayerRange() {
        return ((SpawnerBlockEntity) this.getSnapshot()).getSpawner().requiredPlayerRange;
    }

    public void setRequiredPlayerRange(int requiredPlayerRange) {
        ((SpawnerBlockEntity) this.getSnapshot()).getSpawner().requiredPlayerRange = requiredPlayerRange;
    }

    public int getSpawnRange() {
        return ((SpawnerBlockEntity) this.getSnapshot()).getSpawner().spawnRange;
    }

    public void setSpawnRange(int spawnRange) {
        ((SpawnerBlockEntity) this.getSnapshot()).getSpawner().spawnRange = spawnRange;
    }

    public CraftCreatureSpawner copy() {
        return new CraftCreatureSpawner(this);
    }
}
