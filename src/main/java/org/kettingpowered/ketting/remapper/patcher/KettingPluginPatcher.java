package org.kettingpowered.ketting.remapper.patcher;

import io.izzel.arclight.api.PluginPatcher;
import org.kettingpowered.ketting.core.Ketting;
import org.kettingpowered.ketting.remapper.ClassLoaderRemapper;
import org.kettingpowered.ketting.remapper.GlobalClassRepo;
import org.kettingpowered.ketting.remapper.PluginTransformer;
import org.objectweb.asm.tree.ClassNode;

import java.io.File;
import java.net.URL;
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

        //TODO: this does not work yet
        getPatchesFromPackage(list, "org.kettingpowered.ketting.patches");

        list.sort(Comparator.comparing(PluginPatcher::priority));
        transformerList.add(new KettingPluginPatcher(list));
        Ketting.LOGGER.info("Loaded {} patches", list.size());
        return list;
    }

    private static void getPatchesFromPackage(List<PluginPatcher> list, String packageName) {
        try {
            packageName = packageName.replace('.', '/');
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            final URL pkgResource = classLoader.getResource(packageName);

            if (pkgResource == null) {
                Ketting.LOGGER.warn("Patch package {} does not exist", packageName);
                return;
            }

            File packageDir = new File(pkgResource.getFile());
            if (!packageDir.isDirectory()) {
                Ketting.LOGGER.warn("Patch package {} is not a directory", packageName);
                return;
            }

            final File[] files = packageDir.listFiles();
            if (files == null || files.length == 0) {
                Ketting.LOGGER.warn("Patch package {} is empty", packageName);
                return;
            }

            for (File file : files) {
                if (file.isFile() && file.getName().endsWith(".class")) {
                    // Extract class name and load the class
                    String className = packageName + "." + file.getName().substring(0, file.getName().lastIndexOf('.'));
                    Class<?> clazz;

                    try {
                        clazz = Class.forName(className);
                    } catch (ClassNotFoundException e) {
                        Ketting.LOGGER.warn("Failed to load class: " + className);
                        continue;
                    }

                    if (!PluginPatcher.class.isAssignableFrom(clazz)) {
                        Ketting.LOGGER.warn("Skipped non-patcher class: " + className);
                        continue;
                    }

                    PluginPatcher patcher = (PluginPatcher) clazz.newInstance();
                    list.add(patcher);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
