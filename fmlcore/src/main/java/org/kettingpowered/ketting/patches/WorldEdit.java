package org.kettingpowered.ketting.patches;

import io.izzel.arclight.api.PluginPatcher;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.GeneratorAdapter;
import org.objectweb.asm.commons.Method;
import org.objectweb.asm.tree.*;

import java.util.Locale;

@SuppressWarnings("unused")
public class WorldEdit implements PluginPatcher {

    public void handleClass(ClassNode node, ClassRepo classRepo) {
        if (node.name.equals("com/sk89q/worldedit/bukkit/BukkitAdapter"))
            patchBukkitAdapter(node);

        if (node.name.equals("com/sk89q/worldedit/bukkit/adapter/Refraction"))
            handlePickName(node);
    }

    private void patchBukkitAdapter(ClassNode node) {
        MethodNode standardize = new MethodNode(Opcodes.ACC_PRIVATE | Opcodes.ACC_STATIC | Opcodes.ACC_SYNTHETIC, "patcher$standardize",
                Type.getMethodDescriptor(Type.getType(String.class), Type.getType(String.class)), null, null);
        try {
            GeneratorAdapter adapter = new GeneratorAdapter(standardize, standardize.access, standardize.name, standardize.desc);
            adapter.loadArg(0);
            adapter.push(':');
            adapter.push('_');
            adapter.invokeVirtual(Type.getType(String.class), Method.getMethod(String.class.getMethod("replace", char.class, char.class)));
            adapter.push("\\s+");
            adapter.push("_");
            adapter.invokeVirtual(Type.getType(String.class), Method.getMethod(String.class.getMethod("replaceAll", String.class, String.class)));
            adapter.push("\\W");
            adapter.push("");
            adapter.invokeVirtual(Type.getType(String.class), Method.getMethod(String.class.getMethod("replaceAll", String.class, String.class)));
            adapter.getStatic(Type.getType(Locale.class), "ENGLISH", Type.getType(Locale.class));
            adapter.invokeVirtual(Type.getType(String.class), Method.getMethod(String.class.getMethod("toUpperCase", Locale.class)));
            adapter.returnValue();
            adapter.endMethod();
        } catch (Throwable t) {
            t.printStackTrace();
        }
        node.methods.add(standardize);

        for (MethodNode method : node.methods) {
            if (method.name.equals("adapt")) {
                handleAdapt(node, standardize, method);
            }
        }
    }

    private void handleAdapt(ClassNode node, MethodNode standardize, MethodNode method) {
        switch (method.desc) {
            case "(Lcom/sk89q/worldedit/world/item/ItemType;)Lorg/bukkit/Material;":
            case "(Lcom/sk89q/worldedit/world/block/BlockType;)Lorg/bukkit/Material;":
            case "(Lcom/sk89q/worldedit/world/biome/BiomeType;)Lorg/bukkit/block/Biome;":
            case "(Lcom/sk89q/worldedit/world/entity/EntityType;)Lorg/bukkit/entity/EntityType;": {
                for (AbstractInsnNode instruction : method.instructions) {
                    if (instruction.getOpcode() == Opcodes.ATHROW) {
                        InsnList list = new InsnList();
                        list.add(new VarInsnNode(Opcodes.ALOAD, 0));
                        list.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, Type.getMethodType(method.desc).getArgumentTypes()[0].getInternalName(), "getId", "()Ljava/lang/String;", false));
                        list.add(new MethodInsnNode(Opcodes.INVOKESTATIC, node.name, standardize.name, standardize.desc, false));
                        switch (Type.getMethodType(method.desc).getReturnType().getInternalName()) {
                            case "org/bukkit/Material":
                                list.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "org/bukkit/Material", "getMaterial", "(Ljava/lang/String;)Lorg/bukkit/Material;", false));
                                break;
                            case "org/bukkit/block/Biome":
                                list.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "org/bukkit/block/Biome", "valueOf", "(Ljava/lang/String;)Lorg/bukkit/block/Biome;", false));
                                break;
                            case "org/bukkit/entity/EntityType":
                                list.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "org/bukkit/entity/EntityType", "fromName", "(Ljava/lang/String;)Lorg/bukkit/entity/EntityType;", false));
                                break;
                        }
                        list.add(new InsnNode(Opcodes.ARETURN));
                        method.instructions.insert(instruction, list);
                        method.instructions.set(instruction, new InsnNode(Opcodes.POP));
                        return;
                    }
                }
                break;
            }
        }
    }

    private void handlePickName(ClassNode node) {
        for (MethodNode method : node.methods) {
            if (method.name.equals("pickName")) {
                method.instructions.clear();
                method.instructions.add(new VarInsnNode(Opcodes.ALOAD, 1));
                method.instructions.add(new InsnNode(Opcodes.ARETURN));
                return;
            }
        }
    }
}
