package org.kettingpowered.ketting.remapper.patcher;

import com.google.common.reflect.ClassPath;
import io.izzel.arclight.api.PluginPatcher;
import org.kettingpowered.ketting.core.Ketting;
import org.kettingpowered.ketting.remapper.ClassLoaderRemapper;
import org.kettingpowered.ketting.remapper.GlobalClassRepo;
import org.kettingpowered.ketting.remapper.PluginTransformer;
import org.objectweb.asm.tree.ClassNode;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class KettingPluginPatcher implements PluginTransformer {

    private final List<PluginPatcher> list;

    public KettingPluginPatcher(List<PluginPatcher> list) {
        this.list = list;
    }

    @Override
    public void handleClass(ClassNode node, ClassLoaderRemapper remapper) {
        for (PluginPatcher patcher : list) {
            patcher.handleClass(node, GlobalClassRepo.INSTANCE);
        }
    }

    public static List<PluginPatcher> load(List<PluginTransformer> transformerList) {
        List<PluginPatcher> list = new ArrayList<>();
        Ketting.LOGGER.info("Loading patches...");

        getPatchesFromPackage(list, "org.kettingpowered.ketting.patches");

        list.sort(Comparator.comparing(PluginPatcher::priority));
        transformerList.add(new KettingPluginPatcher(list));
        Ketting.LOGGER.info("Loaded {} patches", list.size());
        return list;
    }

    private static void getPatchesFromPackage(List<PluginPatcher> list, String packageName) {
        try {
            ClassPath.from(ClassLoader.getSystemClassLoader())
                    .getTopLevelClassesRecursive(packageName)
                    .forEach(info -> {
                        try {
                            final Class<?> aClass = Class.forName(info.getName());
                            if (PluginPatcher.class.isAssignableFrom(aClass)) {
                                PluginPatcher patcher = (PluginPatcher) aClass.newInstance();
                                list.add(patcher);
                            }
                        } catch (Exception e) {
                            Ketting.LOGGER.warn("Failed to load patcher {}", info.getName(), e);
                        }
                    });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
