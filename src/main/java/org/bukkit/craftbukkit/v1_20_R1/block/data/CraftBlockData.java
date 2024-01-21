package org.bukkit.craftbukkit.v1_20_R1.block.data;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import net.minecraft.commands.arguments.blocks.BlockStateParser;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.EmptyBlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.StateHolder;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.SoundGroup;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.BlockSupport;
import org.bukkit.block.PistonMoveReaction;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.structure.Mirror;
import org.bukkit.block.structure.StructureRotation;
import org.bukkit.craftbukkit.v1_20_R1.CraftSoundGroup;
import org.bukkit.craftbukkit.v1_20_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_20_R1.block.CraftBlock;
import org.bukkit.craftbukkit.v1_20_R1.block.CraftBlockStates;
import org.bukkit.craftbukkit.v1_20_R1.block.CraftBlockSupport;
import org.bukkit.craftbukkit.v1_20_R1.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_20_R1.util.CraftLocation;
import org.bukkit.craftbukkit.v1_20_R1.util.CraftMagicNumbers;
import org.bukkit.craftbukkit.v1_20_R1.block.impl.*;
import org.bukkit.craftbukkit.v1_20_R1.block.impl.CraftBrushable;
import org.bukkit.craftbukkit.v1_20_R1.block.impl.CraftRotatable;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class CraftBlockData implements BlockData {

    private net.minecraft.world.level.block.state.BlockState state;
    private Map<Property<?>, Comparable<?>> parsedStates;

    protected CraftBlockData() {
        throw new AssertionError("Template Constructor");
    }

    protected CraftBlockData(net.minecraft.world.level.block.state.BlockState state) {
        this.state = state;
    }

    @Override
    public Material getMaterial() {
        return CraftMagicNumbers.getMaterial(state.getBlock());
    }

    public net.minecraft.world.level.block.state.BlockState getState() {
        return state;
    }

    /**
     * Get a given EnumProperty's value as its Bukkit counterpart.
     *
     * @param nms the NMS state to convert
     * @param bukkit the Bukkit class
     * @param <B> the type
     * @return the matching Bukkit type
     */
    protected <B extends Enum<B>> B get(EnumProperty<?> nms, Class<B> bukkit) {
        return toBukkit(state.getValue(nms), bukkit);
    }

    /**
     * Convert all values from the given EnumProperty to their appropriate
     * Bukkit counterpart.
     *
     * @param nms the NMS state to get values from
     * @param bukkit the bukkit class to convert the values to
     * @param <B> the bukkit class type
     * @return an immutable Set of values in their appropriate Bukkit type
     */
    @SuppressWarnings("unchecked")
    protected <B extends Enum<B>> Set<B> getValues(EnumProperty<?> nms, Class<B> bukkit) {
        ImmutableSet.Builder<B> values = ImmutableSet.builder();

        for (Enum<?> e : nms.getPossibleValues()) {
            values.add(toBukkit(e, bukkit));
        }

        return values.build();
    }

    /**
     * Set a given {@link EnumProperty} with the matching enum from Bukkit.
     *
     * @param nms the NMS EnumProperty to set
     * @param bukkit the matching Bukkit Enum
     * @param <B> the Bukkit type
     * @param <N> the NMS type
     */
    protected <B extends Enum<B>, N extends Enum<N> & StringRepresentable> void set(EnumProperty<N> nms, Enum<B> bukkit) {
        this.parsedStates = null;
        this.state = this.state.setValue(nms, toNMS(bukkit, nms.getValueClass()));
    }

    @Override
    public BlockData merge(BlockData data) {
        CraftBlockData craft = (CraftBlockData) data;
        Preconditions.checkArgument(craft.parsedStates != null, "Data not created via string parsing");
        Preconditions.checkArgument(this.state.getBlock() == craft.state.getBlock(), "States have different types (got %s, expected %s)", data, this);

        CraftBlockData clone = (CraftBlockData) this.clone();
        clone.parsedStates = null;

        for (Property parsed : craft.parsedStates.keySet()) {
            clone.state = clone.state.setValue(parsed, craft.state.getValue(parsed));
        }

        return clone;
    }

    @Override
    public boolean matches(BlockData data) {
        if (data == null) {
            return false;
        }
        if (!(data instanceof CraftBlockData)) {
            return false;
        }

        CraftBlockData craft = (CraftBlockData) data;
        if (this.state.getBlock() != craft.state.getBlock()) {
            return false;
        }

        // Fastpath an exact match
        boolean exactMatch = this.equals(data);

        // If that failed, do a merge and check
        if (!exactMatch && craft.parsedStates != null) {
            return this.merge(data).equals(this);
        }

        return exactMatch;
    }

    private static final Map<Class<? extends Enum<?>>, Enum<?>[]> ENUM_VALUES = new HashMap<>();

    /**
     * Convert an NMS Enum (usually a EnumProperty) to its appropriate Bukkit
     * enum from the given class.
     *
     * @throws IllegalStateException if the Enum could not be converted
     */
    @SuppressWarnings("unchecked")
    private static <B extends Enum<B>> B toBukkit(Enum<?> nms, Class<B> bukkit) {
        if (nms instanceof Direction) {
            return (B) CraftBlock.notchToBlockFace((Direction) nms);
        }
        return (B) ENUM_VALUES.computeIfAbsent(bukkit, Class::getEnumConstants)[nms.ordinal()];
    }

    /**
     * Convert a given Bukkit enum to its matching NMS enum type.
     *
     * @param bukkit the Bukkit enum to convert
     * @param nms the NMS class
     * @return the matching NMS type
     * @throws IllegalStateException if the Enum could not be converted
     */
    @SuppressWarnings("unchecked")
    private static <N extends Enum<N> & StringRepresentable> N toNMS(Enum<?> bukkit, Class<N> nms) {
        if (bukkit instanceof BlockFace) {
            return (N) CraftBlock.blockFaceToNotch((BlockFace) bukkit);
        }
        return (N) ENUM_VALUES.computeIfAbsent(nms, Class::getEnumConstants)[bukkit.ordinal()];
    }

    /**
     * Get the current value of a given state.
     *
     * @param ibs the state to check
     * @param <T> the type
     * @return the current value of the given state
     */
    protected <T extends Comparable<T>> T get(Property<T> ibs) {
        // Straight integer or boolean getter
        return this.state.getValue(ibs);
    }

    /**
     * Set the specified state's value.
     *
     * @param ibs the state to set
     * @param v the new value
     * @param <T> the state's type
     * @param <V> the value's type. Must match the state's type.
     */
    public <T extends Comparable<T>, V extends T> void set(Property<T> ibs, V v) {
        // Straight integer or boolean setter
        this.parsedStates = null;
        this.state = this.state.setValue(ibs, v);
    }

    @Override
    public String getAsString() {
        return toString(state.getValues());
    }

    @Override
    public String getAsString(boolean hideUnspecified) {
        return (hideUnspecified && parsedStates != null) ? toString(parsedStates) : getAsString();
    }

    @Override
    public BlockData clone() {
        try {
            return (BlockData) super.clone();
        } catch (CloneNotSupportedException ex) {
            throw new AssertionError("Clone not supported", ex);
        }
    }

    @Override
    public String toString() {
        return "CraftBlockData{" + getAsString() + "}";
    }

    // Mimicked from BlockDataAbstract#toString()
    public String toString(Map<Property<?>, Comparable<?>> states) {
        StringBuilder stateString = new StringBuilder(BuiltInRegistries.BLOCK.getKey(state.getBlock()).toString());

        if (!states.isEmpty()) {
            stateString.append('[');
            stateString.append(states.entrySet().stream().map(StateHolder.PROPERTY_ENTRY_TO_STRING_FUNCTION).collect(Collectors.joining(",")));
            stateString.append(']');
        }

        return stateString.toString();
    }

    public CompoundTag toStates() {
        CompoundTag compound = new CompoundTag();

        for (Map.Entry<Property<?>, Comparable<?>> entry : state.getValues().entrySet()) {
            Property iblockstate = (Property) entry.getKey();

            compound.putString(iblockstate.getName(), iblockstate.getName(entry.getValue()));
        }

        return compound;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof CraftBlockData && state.equals(((CraftBlockData) obj).state);
    }

    @Override
    public int hashCode() {
        return state.hashCode();
    }

    protected static BooleanProperty getBoolean(String name) {
        throw new AssertionError("Template Method");
    }

    protected static BooleanProperty getBoolean(String name, boolean optional) {
        throw new AssertionError("Template Method");
    }

    protected static EnumProperty<?> getEnum(String name) {
        throw new AssertionError("Template Method");
    }

    protected static IntegerProperty getInteger(String name) {
        throw new AssertionError("Template Method");
    }

    protected static BooleanProperty getBoolean(Class<? extends Block> block, String name) {
        return (BooleanProperty) getState(block, name, false);
    }

    protected static BooleanProperty getBoolean(Class<? extends Block> block, String name, boolean optional) {
        return (BooleanProperty) getState(block, name, optional);
    }

    protected static EnumProperty<?> getEnum(Class<? extends Block> block, String name) {
        return (EnumProperty<?>) getState(block, name, false);
    }

    protected static IntegerProperty getInteger(Class<? extends Block> block, String name) {
        return (IntegerProperty) getState(block, name, false);
    }

    /**
     * Get a specified {@link Property} from a given block's class with a
     * given name
     *
     * @param block the class to retrieve the state from
     * @param name the name of the state to retrieve
     * @param optional if the state can be null
     * @return the specified state or null
     * @throws IllegalStateException if the state is null and {@code optional}
     * is false.
     */
    private static Property<?> getState(Class<? extends Block> block, String name, boolean optional) {
        Property<?> state = null;

        for (Block instance : BuiltInRegistries.BLOCK) {
            if (instance.getClass() == block) {
                if (state == null) {
                    state = instance.getStateDefinition().getProperty(name);
                } else {
                    Property<?> newState = instance.getStateDefinition().getProperty(name);

                    Preconditions.checkState(state == newState, "State mistmatch %s,%s", state, newState);
                }
            }
        }

        Preconditions.checkState(optional || state != null, "Null state for %s,%s", block, name);

        return state;
    }

    /**
     * Get the minimum value allowed by the IntegerProperty.
     *
     * @param state the state to check
     * @return the minimum value allowed
     */
    protected static int getMin(IntegerProperty state) {
        return state.min;
    }

    /**
     * Get the maximum value allowed by the IntegerProperty.
     *
     * @param state the state to check
     * @return the maximum value allowed
     */
    protected static int getMax(IntegerProperty state) {
        return state.max;
    }

    //
    private static final Map<Class<? extends Block>, Function<net.minecraft.world.level.block.state.BlockState, CraftBlockData>> MAP = new HashMap<>();

    static {
        //<editor-fold desc="CraftBlockData Registration" defaultstate="collapsed">
        register(net.minecraft.world.level.block.AmethystClusterBlock.class, CraftAmethystCluster::new);
        register(net.minecraft.world.level.block.BigDripleafBlock.class, CraftBigDripleaf::new);
        register(net.minecraft.world.level.block.BigDripleafStemBlock.class, CraftBigDripleafStem::new);
        register(net.minecraft.world.level.block.AnvilBlock.class, CraftAnvil::new);
        register(net.minecraft.world.level.block.BambooStalkBlock.class, CraftBamboo::new);
        register(net.minecraft.world.level.block.BannerBlock.class, CraftBanner::new);
        register(net.minecraft.world.level.block.WallBannerBlock.class, CraftBannerWall::new);
        register(net.minecraft.world.level.block.BarrelBlock.class, CraftBarrel::new);
        register(net.minecraft.world.level.block.BedBlock.class, CraftBed::new);
        register(net.minecraft.world.level.block.BeehiveBlock.class, CraftBeehive::new);
        register(net.minecraft.world.level.block.BeetrootBlock.class, CraftBeetroot::new);
        register(net.minecraft.world.level.block.BellBlock.class, CraftBell::new);
        register(net.minecraft.world.level.block.BlastFurnaceBlock.class, CraftBlastFurnace::new);
        register(net.minecraft.world.level.block.BrewingStandBlock.class, CraftBrewingStand::new);
        register(net.minecraft.world.level.block.BubbleColumnBlock.class, CraftBubbleColumn::new);
        register(net.minecraft.world.level.block.ButtonBlock.class, CraftButtonAbstract::new);
        register(net.minecraft.world.level.block.CactusBlock.class, CraftCactus::new);
        register(net.minecraft.world.level.block.CakeBlock.class, CraftCake::new);
        register(net.minecraft.world.level.block.CampfireBlock.class, CraftCampfire::new);
        register(net.minecraft.world.level.block.CarrotBlock.class, CraftCarrots::new);
        register(net.minecraft.world.level.block.ChainBlock.class, CraftChain::new);
        register(net.minecraft.world.level.block.ChestBlock.class, CraftChest::new);
        register(net.minecraft.world.level.block.TrappedChestBlock.class, CraftChestTrapped::new);
        register(net.minecraft.world.level.block.ChorusFlowerBlock.class, CraftChorusFlower::new);
        register(net.minecraft.world.level.block.ChorusPlantBlock.class, CraftChorusFruit::new);
        register(net.minecraft.world.level.block.WallBlock.class, CraftCobbleWall::new);
        register(net.minecraft.world.level.block.CocoaBlock.class, CraftCocoa::new);
        register(net.minecraft.world.level.block.CommandBlock.class, CraftCommand::new);
        register(net.minecraft.world.level.block.ComposterBlock.class, CraftComposter::new);
        register(net.minecraft.world.level.block.ConduitBlock.class, CraftConduit::new);
        register(net.minecraft.world.level.block.BaseCoralPlantBlock.class, CraftCoralDead::new);
        register(net.minecraft.world.level.block.CoralFanBlock.class, CraftCoralFan::new);
        register(net.minecraft.world.level.block.BaseCoralFanBlock.class, CraftCoralFanAbstract::new);
        register(net.minecraft.world.level.block.CoralWallFanBlock.class, CraftCoralFanWall::new);
        register(net.minecraft.world.level.block.BaseCoralWallFanBlock.class, CraftCoralFanWallAbstract::new);
        register(net.minecraft.world.level.block.CoralPlantBlock.class, CraftCoralPlant::new);
        register(net.minecraft.world.level.block.CropBlock.class, CraftCrops::new);
        register(net.minecraft.world.level.block.DaylightDetectorBlock.class, CraftDaylightDetector::new);
        register(net.minecraft.world.level.block.SnowyDirtBlock.class, CraftDirtSnow::new);
        register(net.minecraft.world.level.block.DispenserBlock.class, CraftDispenser::new);
        register(net.minecraft.world.level.block.DoorBlock.class, CraftDoor::new);
        register(net.minecraft.world.level.block.DropperBlock.class, CraftDropper::new);
        register(net.minecraft.world.level.block.EndRodBlock.class, CraftEndRod::new);
        register(net.minecraft.world.level.block.EnderChestBlock.class, CraftEnderChest::new);
        register(net.minecraft.world.level.block.EndPortalFrameBlock.class, CraftEnderPortalFrame::new);
        register(net.minecraft.world.level.block.FenceBlock.class, CraftFence::new);
        register(net.minecraft.world.level.block.FenceGateBlock.class, CraftFenceGate::new);
        register(net.minecraft.world.level.block.FireBlock.class, CraftFire::new);
        register(net.minecraft.world.level.block.StandingSignBlock.class, CraftFloorSign::new);
        register(net.minecraft.world.level.block.LiquidBlock.class, CraftFluids::new);
        register(net.minecraft.world.level.block.FurnaceBlock.class, CraftFurnaceFurace::new);
        register(net.minecraft.world.level.block.GlazedTerracottaBlock.class, CraftGlazedTerracotta::new);
        register(net.minecraft.world.level.block.GrassBlock.class, CraftGrass::new);
        register(net.minecraft.world.level.block.GrindstoneBlock.class, CraftGrindstone::new);
        register(net.minecraft.world.level.block.HayBlock.class, CraftHay::new);
        register(net.minecraft.world.level.block.HopperBlock.class, CraftHopper::new);
        register(net.minecraft.world.level.block.HugeMushroomBlock.class, CraftHugeMushroom::new);
        register(net.minecraft.world.level.block.FrostedIceBlock.class, CraftIceFrost::new);
        register(net.minecraft.world.level.block.IronBarsBlock.class, CraftIronBars::new);
        register(net.minecraft.world.level.block.JigsawBlock.class, CraftJigsaw::new);
        register(net.minecraft.world.level.block.JukeboxBlock.class, CraftJukeBox::new);
        register(net.minecraft.world.level.block.KelpBlock.class, CraftKelp::new);
        register(net.minecraft.world.level.block.LadderBlock.class, CraftLadder::new);
        register(net.minecraft.world.level.block.LanternBlock.class, CraftLantern::new);
        register(net.minecraft.world.level.block.LeavesBlock.class, CraftLeaves::new);
        register(net.minecraft.world.level.block.LecternBlock.class, CraftLectern::new);
        register(net.minecraft.world.level.block.LeverBlock.class, CraftLever::new);
        register(net.minecraft.world.level.block.LoomBlock.class, CraftLoom::new);
        register(net.minecraft.world.level.block.DetectorRailBlock.class, CraftMinecartDetector::new);
        register(net.minecraft.world.level.block.RailBlock.class, CraftMinecartTrack::new);
        register(net.minecraft.world.level.block.MyceliumBlock.class, CraftMycel::new);
        register(net.minecraft.world.level.block.NetherWartBlock.class, CraftNetherWart::new);
        register(net.minecraft.world.level.block.NoteBlock.class, CraftNote::new);
        register(net.minecraft.world.level.block.ObserverBlock.class, CraftObserver::new);
        register(net.minecraft.world.level.block.NetherPortalBlock.class, CraftPortal::new);
        register(net.minecraft.world.level.block.PotatoBlock.class, CraftPotatoes::new);
        register(net.minecraft.world.level.block.PoweredRailBlock.class, CraftPoweredRail::new);
        register(net.minecraft.world.level.block.PressurePlateBlock.class, CraftPressurePlateBinary::new);
        register(net.minecraft.world.level.block.WeightedPressurePlateBlock.class, CraftPressurePlateWeighted::new);
        register(net.minecraft.world.level.block.CarvedPumpkinBlock.class, CraftPumpkinCarved::new);
        register(net.minecraft.world.level.block.ComparatorBlock.class, CraftRedstoneComparator::new);
        register(net.minecraft.world.level.block.RedstoneLampBlock.class, CraftRedstoneLamp::new);
        register(net.minecraft.world.level.block.RedStoneOreBlock.class, CraftRedstoneOre::new);
        register(net.minecraft.world.level.block.RedstoneTorchBlock.class, CraftRedstoneTorch::new);
        register(net.minecraft.world.level.block.RedstoneWallTorchBlock.class, CraftRedstoneTorchWall::new);
        register(net.minecraft.world.level.block.RedStoneWireBlock.class, CraftRedstoneWire::new);
        register(net.minecraft.world.level.block.SugarCaneBlock.class, CraftReed::new);
        register(net.minecraft.world.level.block.RepeaterBlock.class, CraftRepeater::new);
        register(net.minecraft.world.level.block.RespawnAnchorBlock.class, CraftRespawnAnchor::new);
        register(net.minecraft.world.level.block.RotatedPillarBlock.class, CraftRotatable::new);
        register(net.minecraft.world.level.block.SaplingBlock.class, CraftSapling::new);
        register(net.minecraft.world.level.block.ScaffoldingBlock.class, CraftScaffolding::new);
        register(net.minecraft.world.level.block.SeaPickleBlock.class, CraftSeaPickle::new);
        register(net.minecraft.world.level.block.ShulkerBoxBlock.class, CraftShulkerBox::new);
        register(net.minecraft.world.level.block.SkullBlock.class, CraftSkull::new);
        register(net.minecraft.world.level.block.PlayerHeadBlock.class, CraftSkullPlayer::new);
        register(net.minecraft.world.level.block.PlayerWallHeadBlock.class, CraftSkullPlayerWall::new);
        register(net.minecraft.world.level.block.WallSkullBlock.class, CraftSkullWall::new);
        register(net.minecraft.world.level.block.SmokerBlock.class, CraftSmoker::new);
        register(net.minecraft.world.level.block.SnowLayerBlock.class, CraftSnow::new);
        register(net.minecraft.world.level.block.FarmBlock.class, CraftSoil::new);
        register(net.minecraft.world.level.block.StainedGlassPaneBlock.class, CraftStainedGlassPane::new);
        register(net.minecraft.world.level.block.StairBlock.class, CraftStairs::new);
        register(net.minecraft.world.level.block.StemBlock.class, CraftStem::new);
        register(net.minecraft.world.level.block.AttachedStemBlock.class, CraftStemAttached::new);
        register(net.minecraft.world.level.block.SlabBlock.class, CraftStepAbstract::new);
        register(net.minecraft.world.level.block.StonecutterBlock.class, CraftStonecutter::new);
        register(net.minecraft.world.level.block.StructureBlock.class, CraftStructure::new);
        register(net.minecraft.world.level.block.SweetBerryBushBlock.class, CraftSweetBerryBush::new);
        register(net.minecraft.world.level.block.TntBlock.class, CraftTNT::new);
        register(net.minecraft.world.level.block.DoublePlantBlock.class, CraftTallPlant::new);
        register(net.minecraft.world.level.block.TallFlowerBlock.class, CraftTallPlantFlower::new);
        register(net.minecraft.world.level.block.TargetBlock.class, CraftTarget::new);
        register(net.minecraft.world.level.block.WallTorchBlock.class, CraftTorchWall::new);
        register(net.minecraft.world.level.block.TrapDoorBlock.class, CraftTrapdoor::new);
        register(net.minecraft.world.level.block.TripWireBlock.class, CraftTripwire::new);
        register(net.minecraft.world.level.block.TripWireHookBlock.class, CraftTripwireHook::new);
        register(net.minecraft.world.level.block.TurtleEggBlock.class, CraftTurtleEgg::new);
        register(net.minecraft.world.level.block.TwistingVinesBlock.class, CraftTwistingVines::new);
        register(net.minecraft.world.level.block.VineBlock.class, CraftVine::new);
        register(net.minecraft.world.level.block.WallSignBlock.class, CraftWallSign::new);
        register(net.minecraft.world.level.block.WeepingVinesBlock.class, CraftWeepingVines::new);
        register(net.minecraft.world.level.block.WitherSkullBlock.class, CraftWitherSkull::new);
        register(net.minecraft.world.level.block.WitherWallSkullBlock.class, CraftWitherSkullWall::new);
        register(net.minecraft.world.level.block.BrushableBlock.class, CraftBrushable::new);
        register(net.minecraft.world.level.block.CalibratedSculkSensorBlock.class, CraftCalibratedSculkSensor::new);
        register(net.minecraft.world.level.block.CandleBlock.class, CraftCandle::new);
        register(net.minecraft.world.level.block.CandleCakeBlock.class, CraftCandleCake::new);
        register(net.minecraft.world.level.block.CaveVinesBlock.class, CraftCaveVines::new);
        register(net.minecraft.world.level.block.CaveVinesPlantBlock.class, CraftCaveVinesPlant::new);
        register(net.minecraft.world.level.block.CeilingHangingSignBlock.class, CraftCeilingHangingSign::new);
        register(net.minecraft.world.level.block.CherryLeavesBlock.class, CraftCherryLeaves::new);
        register(net.minecraft.world.level.block.ChiseledBookShelfBlock.class, CraftChiseledBookShelf::new);
        register(net.minecraft.world.level.block.DecoratedPotBlock.class, CraftDecoratedPot::new);
        register(net.minecraft.world.level.block.EquipableCarvedPumpkinBlock.class, CraftEquipableCarvedPumpkin::new);
        register(net.minecraft.world.level.block.GlowLichenBlock.class, CraftGlowLichen::new);
        register(net.minecraft.world.level.block.HangingRootsBlock.class, CraftHangingRoots::new);
        register(net.minecraft.world.level.block.InfestedRotatedPillarBlock.class, CraftInfestedRotatedPillar::new);
        register(net.minecraft.world.level.block.LayeredCauldronBlock.class, CraftLayeredCauldron::new);
        register(net.minecraft.world.level.block.LightBlock.class, CraftLight::new);
        register(net.minecraft.world.level.block.LightningRodBlock.class, CraftLightningRod::new);
        register(net.minecraft.world.level.block.MangroveLeavesBlock.class, CraftMangroveLeaves::new);
        register(net.minecraft.world.level.block.MangrovePropaguleBlock.class, CraftMangrovePropagule::new);
        register(net.minecraft.world.level.block.MangroveRootsBlock.class, CraftMangroveRoots::new);
        register(net.minecraft.world.level.block.PiglinWallSkullBlock.class, CraftPiglinWallSkull::new);
        register(net.minecraft.world.level.block.PinkPetalsBlock.class, CraftPinkPetals::new);
        register(net.minecraft.world.level.block.PitcherCropBlock.class, CraftPitcherCrop::new);
        register(net.minecraft.world.level.block.PointedDripstoneBlock.class, CraftPointedDripstone::new);
        register(net.minecraft.world.level.block.PowderSnowCauldronBlock.class, CraftPowderSnowCauldron::new);
        register(net.minecraft.world.level.block.SculkCatalystBlock.class, CraftSculkCatalyst::new);
        register(net.minecraft.world.level.block.SculkSensorBlock.class, CraftSculkSensor::new);
        register(net.minecraft.world.level.block.SculkShriekerBlock.class, CraftSculkShrieker::new);
        register(net.minecraft.world.level.block.SculkVeinBlock.class, CraftSculkVein::new);
        register(net.minecraft.world.level.block.SmallDripleafBlock.class, CraftSmallDripleaf::new);
        register(net.minecraft.world.level.block.SnifferEggBlock.class, CraftSnifferEgg::new);
        register(net.minecraft.world.level.block.TallSeagrassBlock.class, CraftTallSeagrass::new);
        register(net.minecraft.world.level.block.TorchflowerCropBlock.class, CraftTorchflowerCrop::new);
        register(net.minecraft.world.level.block.WallHangingSignBlock.class, CraftWallHangingSign::new);
        register(net.minecraft.world.level.block.WeatheringCopperSlabBlock.class, CraftWeatheringCopperSlab::new);
        register(net.minecraft.world.level.block.WeatheringCopperStairBlock.class, CraftWeatheringCopperStair::new);
        register(net.minecraft.world.level.block.piston.PistonBaseBlock.class, CraftPiston::new);
        register(net.minecraft.world.level.block.piston.PistonHeadBlock.class, CraftPistonExtension::new);
        register(net.minecraft.world.level.block.piston.MovingPistonBlock.class, CraftPistonMoving::new);
        //</editor-fold>
    }

    private static void register(Class<? extends Block> nms, Function<net.minecraft.world.level.block.state.BlockState, CraftBlockData> bukkit) {
        Preconditions.checkState(MAP.put(nms, bukkit) == null, "Duplicate mapping %s->%s", nms, bukkit);
    }

    public static CraftBlockData newData(Material material, String data) {
        Preconditions.checkArgument(material == null || material.isBlock(), "Cannot get data for not block %s", material);

        net.minecraft.world.level.block.state.BlockState blockData;
        Block block = CraftMagicNumbers.getBlock(material);
        Map<Property<?>, Comparable<?>> parsed = null;

        // Data provided, use it
        if (data != null) {
            try {
                // Material provided, force that material in
                if (block != null) {
                    data = BuiltInRegistries.BLOCK.getKey(block) + data;
                }

                StringReader reader = new StringReader(data);
                BlockStateParser.BlockResult arg = BlockStateParser.parseForBlock(BuiltInRegistries.BLOCK.asLookup(), reader, false);
                Preconditions.checkArgument(!reader.canRead(), "Spurious trailing data: " + data);

                blockData = arg.blockState();
                parsed = arg.properties();
            } catch (CommandSyntaxException ex) {
                throw new IllegalArgumentException("Could not parse data: " + data, ex);
            }
        } else {
            blockData = block.defaultBlockState();
        }

        CraftBlockData craft = fromData(blockData);
        craft.parsedStates = parsed;
        return craft;
    }

    public static CraftBlockData fromData(net.minecraft.world.level.block.state.BlockState data) {
        return MAP.getOrDefault(data.getBlock().getClass(), CraftBlockData::new).apply(data);
    }

    //Ketting start - from Magma
    public static Class<?> getClosestBlockDataClass(Class<? extends Block> blockClass) {
        if (MAP.containsKey(blockClass))
            return MAP.get(blockClass).apply(null).getClass();

        // Try obtaining closest CraftBlockData subclass
        Class<?> superClass = blockClass.getSuperclass();
        Class<?> matchedClass = null;
        Function<net.minecraft.world.level.block.state.BlockState, CraftBlockData> matchedFunction = null;

        while (superClass != null) {
            if (MAP.containsKey(superClass)) {
                matchedFunction = MAP.get(superClass);
                matchedClass = matchedFunction.apply(null).getClass();
                break;
            }
            superClass = superClass.getSuperclass();
        }
        if (matchedClass == null)
            return null;
        register(blockClass, matchedFunction);
        return matchedClass;
    }
    //Ketting end

    @Override
    public SoundGroup getSoundGroup() {
        return CraftSoundGroup.getSoundGroup(state.getSoundType());
    }

    @Override
    public int getLightEmission() {
        return state.getLightEmission();
    }

    @Override
    public boolean isOccluding() {
        return state.canOcclude();
    }

    @Override
    public boolean requiresCorrectToolForDrops() {
        return state.requiresCorrectToolForDrops();
    }

    @Override
    public boolean isPreferredTool(ItemStack tool) {
        Preconditions.checkArgument(tool != null, "tool must not be null");

        net.minecraft.world.item.ItemStack nms = CraftItemStack.asNMSCopy(tool);
        return isPreferredTool(state, nms);
    }

    public static boolean isPreferredTool(net.minecraft.world.level.block.state.BlockState iblockdata, net.minecraft.world.item.ItemStack nmsItem) {
        return !iblockdata.requiresCorrectToolForDrops() || nmsItem.isCorrectToolForDrops(iblockdata);
    }

    @Override
    public PistonMoveReaction getPistonMoveReaction() {
        return PistonMoveReaction.getById(state.getPistonPushReaction().ordinal());
    }

    @Override
    public boolean isSupported(org.bukkit.block.Block block) {
        Preconditions.checkArgument(block != null, "block must not be null");

        CraftBlock craftBlock = (CraftBlock) block;
        return state.canSurvive(craftBlock.getCraftWorld().getHandle(), craftBlock.getPosition());
    }

    @Override
    public boolean isSupported(Location location) {
        Preconditions.checkArgument(location != null, "location must not be null");

        CraftWorld world = (CraftWorld) location.getWorld();
        Preconditions.checkArgument(world != null, "location must not have a null world");

        BlockPos position = CraftLocation.toBlockPosition(location);
        return state.canSurvive(world.getHandle(), position);
    }

    @Override
    public boolean isFaceSturdy(BlockFace face, BlockSupport support) {
        Preconditions.checkArgument(face != null, "face must not be null");
        Preconditions.checkArgument(support != null, "support must not be null");

        return state.isFaceSturdy(EmptyBlockGetter.INSTANCE, BlockPos.ZERO, CraftBlock.blockFaceToNotch(face), CraftBlockSupport.toNMS(support));
    }

    @Override
    public Material getPlacementMaterial() {
        return CraftMagicNumbers.getMaterial(state.getBlock().asItem());
    }

    @Override
    public void rotate(StructureRotation rotation) {
        this.state = state.rotate(Rotation.valueOf(rotation.name()));
    }

    @Override
    public void mirror(Mirror mirror) {
        this.state = state.mirror(net.minecraft.world.level.block.Mirror.valueOf(mirror.name()));
    }

    @NotNull
    @Override
    public BlockState createBlockState() {
        return CraftBlockStates.getBlockState(this.state, null);
    }
}
