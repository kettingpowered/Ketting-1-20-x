package org.kettingpowered.ketting.patches;

import io.izzel.arclight.api.PluginPatcher;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerLevelPatcher implements PluginPatcher {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServerLevelPatcher.class);

    public void handleClass(ClassNode node, ClassRepo classRepo) {
        if (true) return; //TODO: fix classes not being loaded
        for (MethodNode method : node.methods) {
            for (AbstractInsnNode instruction : method.instructions) {
                if (instruction instanceof MethodInsnNode ins) {
                    if (ins.getOpcode() == Opcodes.INVOKESPECIAL && ins.owner.equals("net/minecraft/server/level/ServerLevel") && ins.name.equals("<init>")) {
                        LOGGER.warn("Rewriting ServerLevel constructor call in " + node.name + "." + method.name + method.desc);

                        String newDesc = ins.desc.substring(0, ins.desc.length() - 1).concat(ins.owner); //return type is the same as the owner
                        method.instructions.set(ins, new MethodInsnNode(Opcodes.INVOKESTATIC, ins.owner, "init", newDesc));
                    }
                }
            }
        }
    }

    public int priority() {
        return 9999;
    }
}
