package org.kettingpowered.ketting.remapper;

import cpw.mods.modlauncher.TransformingClassLoader;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.*;

public interface RemappingClassLoader {

    ClassLoaderRemapper getRemapper();

    KettingRemapConfig getRemapConfig();

    static ClassLoader tryRedirect(ClassLoader classLoader) {
        return classLoader instanceof TransformingClassLoader ? RemappingClassLoader.class.getClassLoader() : classLoader;
    }

    static boolean needRemap(ClassLoader cl) {
        for (; cl != null; cl = cl.getParent()) {
            if (cl instanceof TransformingClassLoader) {
                return true;
            }
        }
        return false;
    }

    // Bytecode version of the above code
    static void implementNeedRemap(ClassNode node) {
        MethodNode needRemap = new MethodNode(
                Opcodes.ACC_PRIVATE | Opcodes.ACC_STATIC | Opcodes.ACC_SYNTHETIC,
                "needRemap",
                Type.getMethodDescriptor(Type.getType(boolean.class), Type.getType(ClassLoader.class)),
                null, null
        );
        var l = needRemap.instructions;
        {
            var label0 = new LabelNode();
            l.add(label0);
            l.add(new LineNumberNode(-101, label0));
            if (node.version >= Opcodes.V1_6) {
                l.add(new FrameNode(Opcodes.F_SAME, 0, null, 0, null));
            }
            l.add(new VarInsnNode(Opcodes.ALOAD, 0));

            var label1 = new LabelNode();
            l.add(new JumpInsnNode(Opcodes.IFNULL, label1));

            var label2 = new LabelNode();
            l.add(label2);
            l.add(new LineNumberNode(-102, label2));
            l.add(new VarInsnNode(Opcodes.ALOAD, 0));
            l.add(new TypeInsnNode(Opcodes.INSTANCEOF, Type.getInternalName(TransformingClassLoader.class)));

            var label3 = new LabelNode();
            l.add(new JumpInsnNode(Opcodes.IFEQ, label3));

            var label4 = new LabelNode();
            l.add(label4);
            l.add(new LineNumberNode(-103, label4));
            l.add(new InsnNode(Opcodes.ICONST_1));
            l.add(new InsnNode(Opcodes.IRETURN));

            l.add(label3);
            l.add(new LineNumberNode(-101, label3));
            if (node.version >= Opcodes.V1_6) {
                l.add(new FrameNode(Opcodes.F_SAME, 0, null, 0, null));
            }
            l.add(new VarInsnNode(Opcodes.ALOAD, 0));
            l.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "java/lang/ClassLoader", "getParent", "()Ljava/lang/ClassLoader;", false));
            l.add(new VarInsnNode(Opcodes.ASTORE, 0));
            l.add(new JumpInsnNode(Opcodes.GOTO, label0));

            l.add(label1);
            l.add(new LineNumberNode(-106, label1));
            if (node.version >= Opcodes.V1_6) {
                l.add(new FrameNode(Opcodes.F_SAME, 0, null, 0, null));
            }
            l.add(new InsnNode(Opcodes.ICONST_0));
            l.add(new InsnNode(Opcodes.IRETURN));

            var label5 = new LabelNode();
            l.add(label5);
            needRemap.localVariables.add(new LocalVariableNode("cl", "Ljava/lang/ClassLoader;", null, label0, label5, 0));
        }
        needRemap.visitMaxs(1, 1);
        node.methods.add(needRemap);
    }
}
