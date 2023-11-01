package org.bukkit.craftbukkit.v1_20_R2.entity;

import com.google.common.base.Preconditions;
import java.util.stream.Collectors;
import org.bukkit.TreeSpecies;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Boat.Status;
import org.bukkit.entity.Boat.Type;

public class CraftBoat extends CraftVehicle implements Boat {

    private static volatile int[] $SWITCH_TABLE$net$minecraft$world$entity$vehicle$EntityBoat$EnumBoatType;
    private static volatile int[] $SWITCH_TABLE$org$bukkit$entity$Boat$Type;
    private static volatile int[] $SWITCH_TABLE$net$minecraft$world$entity$vehicle$EntityBoat$EnumStatus;
    private static volatile int[] $SWITCH_TABLE$org$bukkit$TreeSpecies;

    public CraftBoat(CraftServer server, net.minecraft.world.entity.vehicle.Boat entity) {
        super(server, entity);
    }

    public TreeSpecies getWoodType() {
        return getTreeSpecies(this.getHandle().getVariant());
    }

    public void setWoodType(TreeSpecies species) {
        this.getHandle().setVariant(getBoatType(species));
    }

    public Type getBoatType() {
        return boatTypeFromNms(this.getHandle().getVariant());
    }

    public void setBoatType(Type type) {
        Preconditions.checkArgument(type != null, "Boat.Type cannot be null");
        this.getHandle().setVariant(boatTypeToNms(type));
    }

    public double getMaxSpeed() {
        return this.getHandle().maxSpeed;
    }

    public void setMaxSpeed(double speed) {
        if (speed >= 0.0D) {
            this.getHandle().maxSpeed = speed;
        }

    }

    public double getOccupiedDeceleration() {
        return this.getHandle().occupiedDeceleration;
    }

    public void setOccupiedDeceleration(double speed) {
        if (speed >= 0.0D) {
            this.getHandle().occupiedDeceleration = speed;
        }

    }

    public double getUnoccupiedDeceleration() {
        return this.getHandle().unoccupiedDeceleration;
    }

    public void setUnoccupiedDeceleration(double speed) {
        this.getHandle().unoccupiedDeceleration = speed;
    }

    public boolean getWorkOnLand() {
        return this.getHandle().landBoats;
    }

    public void setWorkOnLand(boolean workOnLand) {
        this.getHandle().landBoats = workOnLand;
    }

    public Status getStatus() {
        return boatStatusFromNms(this.getHandle().status);
    }

    public net.minecraft.world.entity.vehicle.Boat getHandle() {
        return (net.minecraft.world.entity.vehicle.Boat) this.entity;
    }

    public String toString() {
        return "CraftBoat{boatType=" + this.getBoatType() + ",status=" + this.getStatus() + ",passengers=" + (String) this.getPassengers().stream().map(Object::toString).collect(Collectors.joining("-", "{", "}")) + "}";
    }

    public static Type boatTypeFromNms(net.minecraft.world.entity.vehicle.Boat.Type boatType) {
        Type type;

        switch ($SWITCH_TABLE$net$minecraft$world$entity$vehicle$EntityBoat$EnumBoatType()[boatType.ordinal()]) {
            case 1:
                type = Type.OAK;
                break;
            case 2:
                type = Type.SPRUCE;
                break;
            case 3:
                type = Type.BIRCH;
                break;
            case 4:
                type = Type.JUNGLE;
                break;
            case 5:
                type = Type.ACACIA;
                break;
            case 6:
                type = Type.CHERRY;
                break;
            case 7:
                type = Type.DARK_OAK;
                break;
            case 8:
                type = Type.MANGROVE;
                break;
            case 9:
                type = Type.BAMBOO;
                break;
            default:
                throw new EnumConstantNotPresentException(Type.class, boatType.name());
        }

        return type;
    }

    public static net.minecraft.world.entity.vehicle.Boat.Type boatTypeToNms(Type type) {
        net.minecraft.world.entity.vehicle.Boat.Type net_minecraft_world_entity_vehicle_boat_type;

        switch ($SWITCH_TABLE$org$bukkit$entity$Boat$Type()[type.ordinal()]) {
            case 1:
                net_minecraft_world_entity_vehicle_boat_type = net.minecraft.world.entity.vehicle.Boat.Type.OAK;
                break;
            case 2:
                net_minecraft_world_entity_vehicle_boat_type = net.minecraft.world.entity.vehicle.Boat.Type.SPRUCE;
                break;
            case 3:
                net_minecraft_world_entity_vehicle_boat_type = net.minecraft.world.entity.vehicle.Boat.Type.BIRCH;
                break;
            case 4:
                net_minecraft_world_entity_vehicle_boat_type = net.minecraft.world.entity.vehicle.Boat.Type.JUNGLE;
                break;
            case 5:
                net_minecraft_world_entity_vehicle_boat_type = net.minecraft.world.entity.vehicle.Boat.Type.ACACIA;
                break;
            case 6:
                net_minecraft_world_entity_vehicle_boat_type = net.minecraft.world.entity.vehicle.Boat.Type.CHERRY;
                break;
            case 7:
                net_minecraft_world_entity_vehicle_boat_type = net.minecraft.world.entity.vehicle.Boat.Type.DARK_OAK;
                break;
            case 8:
                net_minecraft_world_entity_vehicle_boat_type = net.minecraft.world.entity.vehicle.Boat.Type.MANGROVE;
                break;
            case 9:
                net_minecraft_world_entity_vehicle_boat_type = net.minecraft.world.entity.vehicle.Boat.Type.BAMBOO;
                break;
            default:
                throw new EnumConstantNotPresentException(net.minecraft.world.entity.vehicle.Boat.Type.class, type.name());
        }

        return net_minecraft_world_entity_vehicle_boat_type;
    }

    public static Status boatStatusFromNms(net.minecraft.world.entity.vehicle.Boat.Status enumStatus) {
        Status status;

        switch ($SWITCH_TABLE$net$minecraft$world$entity$vehicle$EntityBoat$EnumStatus()[enumStatus.ordinal()]) {
            case 1:
                status = Status.IN_WATER;
                break;
            case 2:
                status = Status.UNDER_WATER;
                break;
            case 3:
                status = Status.UNDER_FLOWING_WATER;
                break;
            case 4:
                status = Status.ON_LAND;
                break;
            case 5:
                status = Status.IN_AIR;
                break;
            default:
                throw new EnumConstantNotPresentException(Status.class, enumStatus.name());
        }

        return status;
    }

    /** @deprecated */
    @Deprecated
    public static TreeSpecies getTreeSpecies(net.minecraft.world.entity.vehicle.Boat.Type boatType) {
        switch ($SWITCH_TABLE$net$minecraft$world$entity$vehicle$EntityBoat$EnumBoatType()[boatType.ordinal()]) {
            case 1:
            case 6:
            default:
                return TreeSpecies.GENERIC;
            case 2:
                return TreeSpecies.REDWOOD;
            case 3:
                return TreeSpecies.BIRCH;
            case 4:
                return TreeSpecies.JUNGLE;
            case 5:
                return TreeSpecies.ACACIA;
            case 7:
                return TreeSpecies.DARK_OAK;
        }
    }

    /** @deprecated */
    @Deprecated
    public static net.minecraft.world.entity.vehicle.Boat.Type getBoatType(TreeSpecies species) {
        switch ($SWITCH_TABLE$org$bukkit$TreeSpecies()[species.ordinal()]) {
            case 1:
            default:
                return net.minecraft.world.entity.vehicle.Boat.Type.OAK;
            case 2:
                return net.minecraft.world.entity.vehicle.Boat.Type.SPRUCE;
            case 3:
                return net.minecraft.world.entity.vehicle.Boat.Type.BIRCH;
            case 4:
                return net.minecraft.world.entity.vehicle.Boat.Type.JUNGLE;
            case 5:
                return net.minecraft.world.entity.vehicle.Boat.Type.ACACIA;
            case 6:
                return net.minecraft.world.entity.vehicle.Boat.Type.DARK_OAK;
        }
    }

    static int[] $SWITCH_TABLE$net$minecraft$world$entity$vehicle$EntityBoat$EnumBoatType() {
        int[] aint = CraftBoat.$SWITCH_TABLE$net$minecraft$world$entity$vehicle$EntityBoat$EnumBoatType;

        if (aint != null) {
            return aint;
        } else {
            int[] aint1 = new int[net.minecraft.world.entity.vehicle.Boat.Type.values().length];

            try {
                aint1[net.minecraft.world.entity.vehicle.Boat.Type.ACACIA.ordinal()] = 5;
            } catch (NoSuchFieldError nosuchfielderror) {
                ;
            }

            try {
                aint1[net.minecraft.world.entity.vehicle.Boat.Type.BAMBOO.ordinal()] = 9;
            } catch (NoSuchFieldError nosuchfielderror1) {
                ;
            }

            try {
                aint1[net.minecraft.world.entity.vehicle.Boat.Type.BIRCH.ordinal()] = 3;
            } catch (NoSuchFieldError nosuchfielderror2) {
                ;
            }

            try {
                aint1[net.minecraft.world.entity.vehicle.Boat.Type.CHERRY.ordinal()] = 6;
            } catch (NoSuchFieldError nosuchfielderror3) {
                ;
            }

            try {
                aint1[net.minecraft.world.entity.vehicle.Boat.Type.DARK_OAK.ordinal()] = 7;
            } catch (NoSuchFieldError nosuchfielderror4) {
                ;
            }

            try {
                aint1[net.minecraft.world.entity.vehicle.Boat.Type.JUNGLE.ordinal()] = 4;
            } catch (NoSuchFieldError nosuchfielderror5) {
                ;
            }

            try {
                aint1[net.minecraft.world.entity.vehicle.Boat.Type.MANGROVE.ordinal()] = 8;
            } catch (NoSuchFieldError nosuchfielderror6) {
                ;
            }

            try {
                aint1[net.minecraft.world.entity.vehicle.Boat.Type.OAK.ordinal()] = 1;
            } catch (NoSuchFieldError nosuchfielderror7) {
                ;
            }

            try {
                aint1[net.minecraft.world.entity.vehicle.Boat.Type.SPRUCE.ordinal()] = 2;
            } catch (NoSuchFieldError nosuchfielderror8) {
                ;
            }

            CraftBoat.$SWITCH_TABLE$net$minecraft$world$entity$vehicle$EntityBoat$EnumBoatType = aint1;
            return aint1;
        }
    }

    static int[] $SWITCH_TABLE$org$bukkit$entity$Boat$Type() {
        int[] aint = CraftBoat.$SWITCH_TABLE$org$bukkit$entity$Boat$Type;

        if (aint != null) {
            return aint;
        } else {
            int[] aint1 = new int[Type.values().length];

            try {
                aint1[Type.ACACIA.ordinal()] = 5;
            } catch (NoSuchFieldError nosuchfielderror) {
                ;
            }

            try {
                aint1[Type.BAMBOO.ordinal()] = 9;
            } catch (NoSuchFieldError nosuchfielderror1) {
                ;
            }

            try {
                aint1[Type.BIRCH.ordinal()] = 3;
            } catch (NoSuchFieldError nosuchfielderror2) {
                ;
            }

            try {
                aint1[Type.CHERRY.ordinal()] = 6;
            } catch (NoSuchFieldError nosuchfielderror3) {
                ;
            }

            try {
                aint1[Type.DARK_OAK.ordinal()] = 7;
            } catch (NoSuchFieldError nosuchfielderror4) {
                ;
            }

            try {
                aint1[Type.JUNGLE.ordinal()] = 4;
            } catch (NoSuchFieldError nosuchfielderror5) {
                ;
            }

            try {
                aint1[Type.MANGROVE.ordinal()] = 8;
            } catch (NoSuchFieldError nosuchfielderror6) {
                ;
            }

            try {
                aint1[Type.OAK.ordinal()] = 1;
            } catch (NoSuchFieldError nosuchfielderror7) {
                ;
            }

            try {
                aint1[Type.SPRUCE.ordinal()] = 2;
            } catch (NoSuchFieldError nosuchfielderror8) {
                ;
            }

            CraftBoat.$SWITCH_TABLE$org$bukkit$entity$Boat$Type = aint1;
            return aint1;
        }
    }

    static int[] $SWITCH_TABLE$net$minecraft$world$entity$vehicle$EntityBoat$EnumStatus() {
        int[] aint = CraftBoat.$SWITCH_TABLE$net$minecraft$world$entity$vehicle$EntityBoat$EnumStatus;

        if (aint != null) {
            return aint;
        } else {
            int[] aint1 = new int[net.minecraft.world.entity.vehicle.Boat.Status.values().length];

            try {
                aint1[net.minecraft.world.entity.vehicle.Boat.Status.IN_AIR.ordinal()] = 5;
            } catch (NoSuchFieldError nosuchfielderror) {
                ;
            }

            try {
                aint1[net.minecraft.world.entity.vehicle.Boat.Status.IN_WATER.ordinal()] = 1;
            } catch (NoSuchFieldError nosuchfielderror1) {
                ;
            }

            try {
                aint1[net.minecraft.world.entity.vehicle.Boat.Status.ON_LAND.ordinal()] = 4;
            } catch (NoSuchFieldError nosuchfielderror2) {
                ;
            }

            try {
                aint1[net.minecraft.world.entity.vehicle.Boat.Status.UNDER_FLOWING_WATER.ordinal()] = 3;
            } catch (NoSuchFieldError nosuchfielderror3) {
                ;
            }

            try {
                aint1[net.minecraft.world.entity.vehicle.Boat.Status.UNDER_WATER.ordinal()] = 2;
            } catch (NoSuchFieldError nosuchfielderror4) {
                ;
            }

            CraftBoat.$SWITCH_TABLE$net$minecraft$world$entity$vehicle$EntityBoat$EnumStatus = aint1;
            return aint1;
        }
    }

    static int[] $SWITCH_TABLE$org$bukkit$TreeSpecies() {
        int[] aint = CraftBoat.$SWITCH_TABLE$org$bukkit$TreeSpecies;

        if (aint != null) {
            return aint;
        } else {
            int[] aint1 = new int[TreeSpecies.values().length];

            try {
                aint1[TreeSpecies.ACACIA.ordinal()] = 5;
            } catch (NoSuchFieldError nosuchfielderror) {
                ;
            }

            try {
                aint1[TreeSpecies.BIRCH.ordinal()] = 3;
            } catch (NoSuchFieldError nosuchfielderror1) {
                ;
            }

            try {
                aint1[TreeSpecies.DARK_OAK.ordinal()] = 6;
            } catch (NoSuchFieldError nosuchfielderror2) {
                ;
            }

            try {
                aint1[TreeSpecies.GENERIC.ordinal()] = 1;
            } catch (NoSuchFieldError nosuchfielderror3) {
                ;
            }

            try {
                aint1[TreeSpecies.JUNGLE.ordinal()] = 4;
            } catch (NoSuchFieldError nosuchfielderror4) {
                ;
            }

            try {
                aint1[TreeSpecies.REDWOOD.ordinal()] = 2;
            } catch (NoSuchFieldError nosuchfielderror5) {
                ;
            }

            CraftBoat.$SWITCH_TABLE$org$bukkit$TreeSpecies = aint1;
            return aint1;
        }
    }
}
