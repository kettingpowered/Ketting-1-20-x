package org.kettingpowered.ketting.adapters;

import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingStage;
import net.minecraftforge.forgespi.language.IModInfo;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.kettingpowered.ketting.adapter.ForgeAdapter;
import org.kettingpowered.ketting.types.Mod;
import org.kettingpowered.ketting.types.ModDependency;

import java.util.*;

public class Forge_1_20_2_Adapter implements ForgeAdapter {

    private final Map<String, Mod> modCache = new HashMap<>();
    private final Map<String, ModDependency> dependencyCache = new HashMap<>();

    public String getMcVersion() {
        return "1.20.2";
    }

    public @Nullable Mod getMod(String modId) {
        if (modCache.containsKey(modId))
            return modCache.get(modId);

        return ModList.get().getModContainerById(modId).map(modContainer -> {
            IModInfo modInfo = modContainer.getModInfo();
            Mod mod = new Mod(
                    modInfo.getModId(),
                    modInfo.getDescription(),
                    modInfo.getDisplayName(),
                    String.format("%s.%s.%s", modInfo.getVersion().getMajorVersion(), modInfo.getVersion().getMinorVersion(), modInfo.getVersion().getIncrementalVersion()),
                    ModList.get().isLoaded(modId),
                    Collections.emptyList()
            );
            mod.dependencies().addAll(getDependencies(modInfo, mod));
            modCache.put(modId, mod);
            return mod;
        }).orElse(null);
    }

    private List<ModDependency> getDependencies(IModInfo modInfo, Mod owner) {
        return modInfo.getDependencies().stream().map(dependency -> {
            final String modId = dependency.getModId();
            if (dependencyCache.containsKey(modId))
                return dependencyCache.get(modId);

            final ModDependency modDependency = new ModDependency(
                    modId,
                    dependency.getVersionRange().toString(),
                    dependency.isMandatory(),
                    getDependencyOrdering(dependency.getOrdering()),
                    getDependencySide(dependency.getSide()),
                    owner
            );
            dependencyCache.put(modId, modDependency);
            return modDependency;
        }).toList();
    }

    private ModDependency.Ordering getDependencyOrdering(IModInfo.Ordering ordering) {
        return switch (ordering) {
            case BEFORE -> ModDependency.Ordering.BEFORE;
            case AFTER -> ModDependency.Ordering.AFTER;
            default -> ModDependency.Ordering.NONE;
        };
    }

    private ModDependency.Side getDependencySide(IModInfo.DependencySide side) {
        return switch (side) {
            case CLIENT -> ModDependency.Side.CLIENT;
            case SERVER -> ModDependency.Side.SERVER;
            default -> ModDependency.Side.UNIVERSAL;
        };
    }

    public List<Mod> getMods() {
        return ModList.get().getMods().stream().map(IModInfo::getModId).map(this::getMod).filter(Objects::nonNull).toList();
    }

    public @Nullable String getModName(String modId) {
        final Mod mod = getMod(modId);
        return mod == null ? null : getModName(mod);
    }

    public @Nullable String getModName(@NotNull Mod mod) {
        return mod.displayName();
    }
}
