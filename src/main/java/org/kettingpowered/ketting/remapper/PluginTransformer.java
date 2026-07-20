package org.kettingpowered.ketting.remapper;

import org.objectweb.asm.tree.ClassNode;

public interface PluginTransformer {

    void handleClass(ClassNode node, ClassLoaderRemapper remapper, KettingRemapConfig config);

    default int priority() {
        return 0;
    }
}
