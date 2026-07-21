package org.kettingpowered.ketting.patches;

import io.izzel.arclight.api.PluginPatcher;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Set;

public class Denizen implements PluginPatcher {

    private static final Logger LOGGER = LoggerFactory.getLogger("DenizenPatcher");

    public void handleClass(ClassNode node, ClassRepo classRepo) {
        if (node.name.equals("com/denizenscript/denizen/nms/v1_20/helpers/ItemHelperImpl"))
            patchItemHelper(node);
    }

    private void patchItemHelper(ClassNode node) {
        node.methods.forEach(m -> {
            if (m.name.equals("clearDenizenRecipes"))
                patchRecipeMap(m);
            if (m.name.equals("getNMSRecipe"))
                patchRecipeMap(m);
        });
    }

    private static final String FASTUTIL_MAP = "it/unimi/dsi/fastutil/objects/Object2ObjectLinkedOpenHashMap";
    private static final String JAVA_MAP = Type.getInternalName(Map.class);
    private void patchRecipeMap(MethodNode method) {
        LOGGER.debug("Patching ItemHelperImpl.{}", method.name);
        method.instructions.forEach(insn -> {
            if (insn instanceof TypeInsnNode typeInsn
                    && typeInsn.getOpcode() == Opcodes.CHECKCAST
                    && typeInsn.desc.equals(FASTUTIL_MAP)) {

                typeInsn.desc = JAVA_MAP;
            }

            if (insn instanceof MethodInsnNode methodInsn
                    && methodInsn.getOpcode() == Opcodes.INVOKEVIRTUAL
                    && methodInsn.owner.equals(FASTUTIL_MAP)) {

                methodInsn.setOpcode(Opcodes.INVOKEINTERFACE);
                methodInsn.owner = JAVA_MAP;
                methodInsn.itf = true;

                if (methodInsn.name.equals("keySet"))
                    methodInsn.desc = Type.getMethodDescriptor(Type.getType(Set.class));
            }

            if (insn instanceof FrameNode frame) {
                patchFrame(frame);
            }
        });
    }

    private void patchFrame(FrameNode frame) {
        if (frame.local == null) return;
        for (int i = 0; i < frame.local.size(); i++) {
            Object local = frame.local.get(i);

            if (FASTUTIL_MAP.equals(local)) {
                frame.local.set(i, JAVA_MAP);
            }
        }
    }
}
