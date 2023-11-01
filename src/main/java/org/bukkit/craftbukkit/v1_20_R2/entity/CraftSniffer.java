package org.bukkit.craftbukkit.v1_20_R2.entity;

import com.google.common.base.Preconditions;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.Level;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.craftbukkit.v1_20_R2.util.CraftLocation;
import org.bukkit.entity.Sniffer;
import org.bukkit.entity.Sniffer.State;

public class CraftSniffer extends CraftAnimals implements Sniffer {

    private static volatile int[] $SWITCH_TABLE$org$bukkit$entity$Sniffer$State;
    private static volatile int[] $SWITCH_TABLE$net$minecraft$world$entity$animal$sniffer$Sniffer$State;

    public CraftSniffer(CraftServer server, net.minecraft.world.entity.animal.sniffer.Sniffer entity) {
        super(server, (Animal) entity);
    }

    public net.minecraft.world.entity.animal.sniffer.Sniffer getHandle() {
        return (net.minecraft.world.entity.animal.sniffer.Sniffer) super.getHandle();
    }

    public String toString() {
        return "CraftSniffer";
    }

    public Collection getExploredLocations() {
        return (Collection) this.getHandle().getExploredPositions().map((blockPositionx) -> {
            return CraftLocation.toBukkit(blockPositionx.pos(), (Level) this.server.getServer().getLevel(blockPositionx.dimension()));
        }).collect(Collectors.toList());
    }

    public void removeExploredLocation(Location location) {
        Preconditions.checkArgument(location != null, "location cannot be null");
        if (location.getWorld() == this.getWorld()) {
            BlockPos blockPosition = CraftLocation.toBlockPosition(location);

            this.getHandle().getBrain().setMemory(MemoryModuleType.SNIFFER_EXPLORED_POSITIONS, (Object) ((List) this.getHandle().getExploredPositions().filter((blockPositionExploredx) -> {
                return !blockPositionExploredx.equals(blockPosition);
            }).collect(Collectors.toList())));
        }
    }

    public void addExploredLocation(Location location) {
        Preconditions.checkArgument(location != null, "location cannot be null");
        if (location.getWorld() == this.getWorld()) {
            this.getHandle().storeExploredPosition(CraftLocation.toBlockPosition(location));
        }
    }

    public State getState() {
        return this.stateToBukkit(this.getHandle().getState());
    }

    public void setState(State state) {
        Preconditions.checkArgument(state != null, "state cannot be null");
        this.getHandle().transitionTo(this.stateToNMS(state));
    }

    public Location findPossibleDigLocation() {
        return (Location) this.getHandle().calculateDigPosition().map((blockPositionx) -> {
            return CraftLocation.toBukkit(blockPositionx, this.getLocation().getWorld());
        }).orElse((Object) null);
    }

    public boolean canDig() {
        return this.getHandle().canDig();
    }

    private net.minecraft.world.entity.animal.sniffer.Sniffer.State stateToNMS(State state) {
        net.minecraft.world.entity.animal.sniffer.Sniffer.State net_minecraft_world_entity_animal_sniffer_sniffer_state;

        switch ($SWITCH_TABLE$org$bukkit$entity$Sniffer$State()[state.ordinal()]) {
            case 1:
                net_minecraft_world_entity_animal_sniffer_sniffer_state = net.minecraft.world.entity.animal.sniffer.Sniffer.State.IDLING;
                break;
            case 2:
                net_minecraft_world_entity_animal_sniffer_sniffer_state = net.minecraft.world.entity.animal.sniffer.Sniffer.State.FEELING_HAPPY;
                break;
            case 3:
                net_minecraft_world_entity_animal_sniffer_sniffer_state = net.minecraft.world.entity.animal.sniffer.Sniffer.State.SCENTING;
                break;
            case 4:
                net_minecraft_world_entity_animal_sniffer_sniffer_state = net.minecraft.world.entity.animal.sniffer.Sniffer.State.SNIFFING;
                break;
            case 5:
                net_minecraft_world_entity_animal_sniffer_sniffer_state = net.minecraft.world.entity.animal.sniffer.Sniffer.State.SEARCHING;
                break;
            case 6:
                net_minecraft_world_entity_animal_sniffer_sniffer_state = net.minecraft.world.entity.animal.sniffer.Sniffer.State.DIGGING;
                break;
            case 7:
                net_minecraft_world_entity_animal_sniffer_sniffer_state = net.minecraft.world.entity.animal.sniffer.Sniffer.State.RISING;
                break;
            default:
                throw new IncompatibleClassChangeError();
        }

        return net_minecraft_world_entity_animal_sniffer_sniffer_state;
    }

    private State stateToBukkit(net.minecraft.world.entity.animal.sniffer.Sniffer.State state) {
        State state;

        switch ($SWITCH_TABLE$net$minecraft$world$entity$animal$sniffer$Sniffer$State()[state.ordinal()]) {
            case 1:
                state = State.IDLING;
                break;
            case 2:
                state = State.FEELING_HAPPY;
                break;
            case 3:
                state = State.SCENTING;
                break;
            case 4:
                state = State.SNIFFING;
                break;
            case 5:
                state = State.SEARCHING;
                break;
            case 6:
                state = State.DIGGING;
                break;
            case 7:
                state = State.RISING;
                break;
            default:
                throw new IncompatibleClassChangeError();
        }

        return state;
    }

    static int[] $SWITCH_TABLE$org$bukkit$entity$Sniffer$State() {
        int[] aint = CraftSniffer.$SWITCH_TABLE$org$bukkit$entity$Sniffer$State;

        if (aint != null) {
            return aint;
        } else {
            int[] aint1 = new int[State.values().length];

            try {
                aint1[State.DIGGING.ordinal()] = 6;
            } catch (NoSuchFieldError nosuchfielderror) {
                ;
            }

            try {
                aint1[State.FEELING_HAPPY.ordinal()] = 2;
            } catch (NoSuchFieldError nosuchfielderror1) {
                ;
            }

            try {
                aint1[State.IDLING.ordinal()] = 1;
            } catch (NoSuchFieldError nosuchfielderror2) {
                ;
            }

            try {
                aint1[State.RISING.ordinal()] = 7;
            } catch (NoSuchFieldError nosuchfielderror3) {
                ;
            }

            try {
                aint1[State.SCENTING.ordinal()] = 3;
            } catch (NoSuchFieldError nosuchfielderror4) {
                ;
            }

            try {
                aint1[State.SEARCHING.ordinal()] = 5;
            } catch (NoSuchFieldError nosuchfielderror5) {
                ;
            }

            try {
                aint1[State.SNIFFING.ordinal()] = 4;
            } catch (NoSuchFieldError nosuchfielderror6) {
                ;
            }

            CraftSniffer.$SWITCH_TABLE$org$bukkit$entity$Sniffer$State = aint1;
            return aint1;
        }
    }

    static int[] $SWITCH_TABLE$net$minecraft$world$entity$animal$sniffer$Sniffer$State() {
        int[] aint = CraftSniffer.$SWITCH_TABLE$net$minecraft$world$entity$animal$sniffer$Sniffer$State;

        if (aint != null) {
            return aint;
        } else {
            int[] aint1 = new int[net.minecraft.world.entity.animal.sniffer.Sniffer.State.values().length];

            try {
                aint1[net.minecraft.world.entity.animal.sniffer.Sniffer.State.DIGGING.ordinal()] = 6;
            } catch (NoSuchFieldError nosuchfielderror) {
                ;
            }

            try {
                aint1[net.minecraft.world.entity.animal.sniffer.Sniffer.State.FEELING_HAPPY.ordinal()] = 2;
            } catch (NoSuchFieldError nosuchfielderror1) {
                ;
            }

            try {
                aint1[net.minecraft.world.entity.animal.sniffer.Sniffer.State.IDLING.ordinal()] = 1;
            } catch (NoSuchFieldError nosuchfielderror2) {
                ;
            }

            try {
                aint1[net.minecraft.world.entity.animal.sniffer.Sniffer.State.RISING.ordinal()] = 7;
            } catch (NoSuchFieldError nosuchfielderror3) {
                ;
            }

            try {
                aint1[net.minecraft.world.entity.animal.sniffer.Sniffer.State.SCENTING.ordinal()] = 3;
            } catch (NoSuchFieldError nosuchfielderror4) {
                ;
            }

            try {
                aint1[net.minecraft.world.entity.animal.sniffer.Sniffer.State.SEARCHING.ordinal()] = 5;
            } catch (NoSuchFieldError nosuchfielderror5) {
                ;
            }

            try {
                aint1[net.minecraft.world.entity.animal.sniffer.Sniffer.State.SNIFFING.ordinal()] = 4;
            } catch (NoSuchFieldError nosuchfielderror6) {
                ;
            }

            CraftSniffer.$SWITCH_TABLE$net$minecraft$world$entity$animal$sniffer$Sniffer$State = aint1;
            return aint1;
        }
    }
}
