package org.kettingpowered.ketting.patches;

import io.izzel.arclight.api.PluginPatcher;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

@SuppressWarnings("unused")
public class ServerLevelConstructor implements PluginPatcher{
    @Override
    public void handleClass(ClassNode node, ClassRepo classRepo) {
        if(true) return; //breaks Wordedit for some reason.
        for(MethodNode method : node.methods){
            for(AbstractInsnNode instruction:method.instructions){
                if (instruction instanceof MethodInsnNode methodInsnNode &&
                        "<init>".equals(methodInsnNode.name) && 
                        "net/minecraft/server/level/ServerLevel".equals(methodInsnNode.owner)
                ){
                    //desc == "(Lnet/minecraft/server/MinecraftServer;Ljava/util/concurrent/Executor;Lnet/minecraft/world/level/storage/LevelStorageSource$LevelStorageAccess;Lnet/minecraft/world/level/storage/ServerLevelData;Lnet/minecraft/resources/ResourceKey;Lnet/minecraft/world/level/dimension/DimensionType;Lnet/minecraft/server/level/progress/ChunkProgressListener;Lnet/minecraft/world/level/chunk/ChunkGenerator;ZJLjava/util/List;ZLorg/bukkit/World$Environment;Lorg/bukkit/generator/ChunkGenerator;)V"
                    methodInsnNode.setOpcode(Opcodes.INVOKESTATIC);
                    methodInsnNode.name = "init";
                }
            }
        }
    }
}
