package org.bukkit.craftbukkit.v1_20_R2.packs;

import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackCompatibility;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.util.InclusiveRange;
import org.bukkit.Bukkit;
import org.bukkit.FeatureFlag;
import org.bukkit.NamespacedKey;
import org.bukkit.craftbukkit.v1_20_R2.CraftFeatureFlag;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.craftbukkit.v1_20_R2.util.CraftChatMessage;
import org.bukkit.packs.DataPack;
import org.bukkit.packs.DataPack.Compatibility;
import org.bukkit.packs.DataPack.Source;

public class CraftDataPack implements DataPack {

    private final Pack handle;
    private final PackMetadataSection resourcePackInfo;
    private static volatile int[] $SWITCH_TABLE$net$minecraft$server$packs$repository$EnumResourcePackVersion;

    public CraftDataPack(Pack handler) {
        this.handle = handler;

        try {
            Throwable throwable = null;
            Object object = null;

            try {
                PackResources iresourcepack = this.handle.resources.openPrimary(this.handle.getId());

                try {
                    this.resourcePackInfo = (PackMetadataSection) iresourcepack.getMetadataSection(PackMetadataSection.TYPE);
                } finally {
                    if (iresourcepack != null) {
                        iresourcepack.close();
                    }

                }

            } catch (Throwable throwable1) {
                if (throwable == null) {
                    throwable = throwable1;
                } else if (throwable != throwable1) {
                    throwable.addSuppressed(throwable1);
                }

                throw throwable;
            }
        } catch (IOException ioexception) {
            throw new RuntimeException(ioexception);
        }
    }

    public Pack getHandle() {
        return this.handle;
    }

    public String getRawId() {
        return this.getHandle().getId();
    }

    public String getTitle() {
        return CraftChatMessage.fromComponent(this.getHandle().getTitle());
    }

    public String getDescription() {
        return CraftChatMessage.fromComponent(this.getHandle().getDescription());
    }

    public int getPackFormat() {
        return this.resourcePackInfo.packFormat();
    }

    public int getMinSupportedPackFormat() {
        return (Integer) ((InclusiveRange) this.resourcePackInfo.supportedFormats().orElse(new InclusiveRange(this.getPackFormat()))).minInclusive();
    }

    public int getMaxSupportedPackFormat() {
        return (Integer) ((InclusiveRange) this.resourcePackInfo.supportedFormats().orElse(new InclusiveRange(this.getPackFormat()))).maxInclusive();
    }

    public boolean isRequired() {
        return this.getHandle().isRequired();
    }

    public Compatibility getCompatibility() {
        Compatibility compatibility;

        switch ($SWITCH_TABLE$net$minecraft$server$packs$repository$EnumResourcePackVersion()[this.getHandle().getCompatibility().ordinal()]) {
            case 1:
                compatibility = Compatibility.OLD;
                break;
            case 2:
                compatibility = Compatibility.NEW;
                break;
            case 3:
                compatibility = Compatibility.COMPATIBLE;
                break;
            default:
                throw new IncompatibleClassChangeError();
        }

        return compatibility;
    }

    public boolean isEnabled() {
        return ((CraftServer) Bukkit.getServer()).getServer().getPackRepository().getSelectedIds().contains(this.getRawId());
    }

    public Source getSource() {
        return this.getHandle().getPackSource() == PackSource.BUILT_IN ? Source.BUILT_IN : (this.getHandle().getPackSource() == PackSource.FEATURE ? Source.FEATURE : (this.getHandle().getPackSource() == PackSource.WORLD ? Source.WORLD : (this.getHandle().getPackSource() == PackSource.SERVER ? Source.SERVER : Source.DEFAULT)));
    }

    public Set getRequestedFeatures() {
        Stream stream = CraftFeatureFlag.getFromNMS(this.getHandle().getRequestedFeatures()).stream();

        FeatureFlag.class.getClass();
        return (Set) stream.map(FeatureFlag.class::cast).collect(Collectors.toUnmodifiableSet());
    }

    public NamespacedKey getKey() {
        return NamespacedKey.fromString(this.getRawId());
    }

    public String toString() {
        String requestedFeatures = (String) this.getRequestedFeatures().stream().map((featureFlagx) -> {
            return featureFlagx.getKey().toString();
        }).collect(Collectors.joining(","));

        return "CraftDataPack{rawId=" + this.getRawId() + ",id=" + this.getKey() + ",title=" + this.getTitle() + ",description=" + this.getDescription() + ",packformat=" + this.getPackFormat() + ",minSupportedPackFormat=" + this.getMinSupportedPackFormat() + ",maxSupportedPackFormat=" + this.getMaxSupportedPackFormat() + ",compatibility=" + this.getCompatibility() + ",source=" + this.getSource() + ",enabled=" + this.isEnabled() + ",requestedFeatures=[" + requestedFeatures + "]}";
    }

    static int[] $SWITCH_TABLE$net$minecraft$server$packs$repository$EnumResourcePackVersion() {
        int[] aint = CraftDataPack.$SWITCH_TABLE$net$minecraft$server$packs$repository$EnumResourcePackVersion;

        if (aint != null) {
            return aint;
        } else {
            int[] aint1 = new int[PackCompatibility.values().length];

            try {
                aint1[PackCompatibility.COMPATIBLE.ordinal()] = 3;
            } catch (NoSuchFieldError nosuchfielderror) {
                ;
            }

            try {
                aint1[PackCompatibility.TOO_NEW.ordinal()] = 2;
            } catch (NoSuchFieldError nosuchfielderror1) {
                ;
            }

            try {
                aint1[PackCompatibility.TOO_OLD.ordinal()] = 1;
            } catch (NoSuchFieldError nosuchfielderror2) {
                ;
            }

            CraftDataPack.$SWITCH_TABLE$net$minecraft$server$packs$repository$EnumResourcePackVersion = aint1;
            return aint1;
        }
    }
}
