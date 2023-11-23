package org.kettingpowered.ketting.patches;

import io.izzel.arclight.api.PluginPatcher;
import org.objectweb.asm.tree.ClassNode;

import java.util.Objects;

public class TestPatch implements PluginPatcher {

    public void handleClass(ClassNode node, ClassRepo classRepo) {
        if (Objects.equals(node.name, "net/minecraft/server/MinecraftServer")) {
            System.out.println("patcher Found MinecraftServer class!");
        }
    }
}
