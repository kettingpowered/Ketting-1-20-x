package org.bukkit.craftbukkit.v1_20_R2.util;

import com.google.common.io.ByteStreams;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;
import java.util.zip.ZipEntry;
import joptsimple.ArgumentAcceptingOptionSpec;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import org.bukkit.Material;
import org.bukkit.plugin.AuthorNagException;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Handle;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

public class Commodore {

    private static final Set EVIL = new HashSet(Arrays.asList("org/bukkit/World (III)I getBlockTypeIdAt", "org/bukkit/World (Lorg/bukkit/Location;)I getBlockTypeIdAt", "org/bukkit/block/Block ()I getTypeId", "org/bukkit/block/Block (I)Z setTypeId", "org/bukkit/block/Block (IZ)Z setTypeId", "org/bukkit/block/Block (IBZ)Z setTypeIdAndData", "org/bukkit/block/Block (B)V setData", "org/bukkit/block/Block (BZ)V setData", "org/bukkit/inventory/ItemStack ()I getTypeId", "org/bukkit/inventory/ItemStack (I)V setTypeId"));

    public static void main(String[] args) {
        OptionParser parser = new OptionParser();
        ArgumentAcceptingOptionSpec inputFlag = parser.acceptsAll(Arrays.asList("i", "input")).withRequiredArg().ofType(File.class).required();
        ArgumentAcceptingOptionSpec outputFlag = parser.acceptsAll(Arrays.asList("o", "output")).withRequiredArg().ofType(File.class).required();
        OptionSet options = parser.parse(args);
        File input = (File) options.valueOf(inputFlag);
        File output = (File) options.valueOf(outputFlag);

        if (input.isDirectory()) {
            if (!output.isDirectory()) {
                System.err.println("If input directory specified, output directory required too");
                return;
            }

            File[] afile;
            int i = (afile = input.listFiles()).length;

            for (int j = 0; j < i; ++j) {
                File in = afile[j];

                if (in.getName().endsWith(".jar")) {
                    convert(in, new File(output, in.getName()));
                }
            }
        } else {
            convert(input, output);
        }

    }

    private static void convert(File in, File out) {
        System.out.println("Attempting to convert " + in + " to " + out);

        try {
            Throwable throwable = null;
            Object object = null;

            try {
                JarFile inJar = new JarFile(in, false);

                try {
                    JarEntry entry = inJar.getJarEntry(".commodore");

                    if (entry == null) {
                        Throwable throwable1 = null;
                        Object object1 = null;

                        try {
                            JarOutputStream outJar = new JarOutputStream(new FileOutputStream(out));

                            try {
                                Enumeration entries = inJar.entries();

                                while (entries.hasMoreElements()) {
                                    entry = (JarEntry) entries.nextElement();
                                    Throwable throwable2 = null;
                                    Object object2 = null;

                                    try {
                                        InputStream is = inJar.getInputStream(entry);

                                        try {
                                            byte[] b = ByteStreams.toByteArray(is);

                                            if (entry.getName().endsWith(".class")) {
                                                b = convert(b, false);
                                                entry = new JarEntry(entry.getName());
                                            }

                                            outJar.putNextEntry(entry);
                                            outJar.write(b);
                                        } finally {
                                            if (is != null) {
                                                is.close();
                                            }

                                        }
                                    } catch (Throwable throwable3) {
                                        if (throwable2 == null) {
                                            throwable2 = throwable3;
                                        } else if (throwable2 != throwable3) {
                                            throwable2.addSuppressed(throwable3);
                                        }

                                        throw throwable2;
                                    }
                                }

                                outJar.putNextEntry(new ZipEntry(".commodore"));
                                return;
                            } finally {
                                if (outJar != null) {
                                    outJar.close();
                                }

                            }
                        } catch (Throwable throwable4) {
                            if (throwable1 == null) {
                                throwable1 = throwable4;
                            } else if (throwable1 != throwable4) {
                                throwable1.addSuppressed(throwable4);
                            }

                            throw throwable1;
                        }
                    }
                } finally {
                    if (inJar != null) {
                        inJar.close();
                    }

                }

            } catch (Throwable throwable5) {
                if (throwable == null) {
                    throwable = throwable5;
                } else if (throwable != throwable5) {
                    throwable.addSuppressed(throwable5);
                }

                throw throwable;
            }
        } catch (Exception exception) {
            System.err.println("Fatal error trying to convert " + in);
            exception.printStackTrace();
        }
    }

    public static byte[] convert(byte[] b, final boolean modern) {
        ClassReader cr = new ClassReader(b);
        ClassWriter cw = new ClassWriter(cr, 0);

        cr.accept(new ClassVisitor(589824, cw) {
            public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
                return new MethodVisitor(this.api, super.visitMethod(access, name, desc, signature, exceptions)) {
                    public void visitFieldInsn(int opcode, String owner, String name, String desc) {
                        if (owner.equals("org/bukkit/block/Biome")) {
                            switch (name.hashCode()) {
                                case -1995596712:
                                    if (name.equals("NETHER")) {
                                        super.visitFieldInsn(opcode, owner, "NETHER_WASTES", desc);
                                        return;
                                    }
                                    break;
                                case -1979115441:
                                    if (name.equals("GIANT_TREE_TAIGA")) {
                                        super.visitFieldInsn(opcode, owner, "OLD_GROWTH_PINE_TAIGA", desc);
                                        return;
                                    }
                                    break;
                                case -1093212205:
                                    if (name.equals("STONE_SHORE")) {
                                        super.visitFieldInsn(opcode, owner, "STONY_SHORE", desc);
                                        return;
                                    }
                                    break;
                                case -1050917018:
                                    if (name.equals("MOUNTAINS")) {
                                        super.visitFieldInsn(opcode, owner, "WINDSWEPT_HILLS", desc);
                                        return;
                                    }
                                    break;
                                case -829731074:
                                    if (name.equals("TALL_BIRCH_FOREST")) {
                                        super.visitFieldInsn(opcode, owner, "OLD_GROWTH_BIRCH_FOREST", desc);
                                        return;
                                    }
                                    break;
                                case -307629759:
                                    if (name.equals("SHATTERED_SAVANNA")) {
                                        super.visitFieldInsn(opcode, owner, "WINDSWEPT_SAVANNA", desc);
                                        return;
                                    }
                                    break;
                                case -183194225:
                                    if (name.equals("SNOWY_TUNDRA")) {
                                        super.visitFieldInsn(opcode, owner, "SNOWY_PLAINS", desc);
                                        return;
                                    }
                                    break;
                                case -181952581:
                                    if (name.equals("GRAVELLY_MOUNTAINS")) {
                                        super.visitFieldInsn(opcode, owner, "WINDSWEPT_GRAVELLY_HILLS", desc);
                                        return;
                                    }
                                    break;
                                case 1379833855:
                                    if (name.equals("JUNGLE_EDGE")) {
                                        super.visitFieldInsn(opcode, owner, "SPARSE_JUNGLE", desc);
                                        return;
                                    }
                                    break;
                                case 1620049985:
                                    if (name.equals("WOODED_BADLANDS_PLATEAU")) {
                                        super.visitFieldInsn(opcode, owner, "WOODED_BADLANDS", desc);
                                        return;
                                    }
                                    break;
                                case 1683379443:
                                    if (name.equals("GIANT_SPRUCE_TAIGA")) {
                                        super.visitFieldInsn(opcode, owner, "OLD_GROWTH_SPRUCE_TAIGA", desc);
                                        return;
                                    }
                                    break;
                                case 2042983155:
                                    if (name.equals("WOODED_MOUNTAINS")) {
                                        super.visitFieldInsn(opcode, owner, "WINDSWEPT_FOREST", desc);
                                        return;
                                    }
                            }
                        }

                        if (owner.equals("org/bukkit/entity/EntityType")) {
                            switch (name.hashCode()) {
                                case -1385440745:
                                    if (name.equals("PIG_ZOMBIE")) {
                                        super.visitFieldInsn(opcode, owner, "ZOMBIFIED_PIGLIN", desc);
                                        return;
                                    }
                            }
                        }

                        if (owner.equals("org/bukkit/loot/LootTables")) {
                            switch (name.hashCode()) {
                                case 1927926085:
                                    if (name.equals("ZOMBIE_PIGMAN")) {
                                        super.visitFieldInsn(opcode, owner, "ZOMBIFIED_PIGLIN", desc);
                                        return;
                                    }
                            }
                        }

                        if (owner.equals("org/bukkit/entity/TextDisplay$TextAligment")) {
                            super.visitFieldInsn(opcode, "org/bukkit/entity/TextDisplay$TextAlignment", name, desc);
                        } else if (modern) {
                            if (owner.equals("org/bukkit/Material")) {
                                switch (name.hashCode()) {
                                    case -1311700911:
                                        if (name.equals("CACTUS_GREEN")) {
                                            name = "GREEN_DYE";
                                        }
                                        break;
                                    case -1301427577:
                                        if (name.equals("ZOMBIE_PIGMAN_SPAWN_EGG")) {
                                            name = "ZOMBIFIED_PIGLIN_SPAWN_EGG";
                                        }
                                        break;
                                    case -19295470:
                                        if (name.equals("WALL_SIGN")) {
                                            name = "OAK_WALL_SIGN";
                                        }
                                        break;
                                    case 2545085:
                                        if (name.equals("SIGN")) {
                                            name = "OAK_SIGN";
                                        }
                                        break;
                                    case 717727105:
                                        if (name.equals("ROSE_RED")) {
                                            name = "RED_DYE";
                                        }
                                        break;
                                    case 1478351150:
                                        if (name.equals("GRASS_PATH")) {
                                            name = "DIRT_PATH";
                                        }
                                        break;
                                    case 2089237253:
                                        if (name.equals("DANDELION_YELLOW")) {
                                            name = "YELLOW_DYE";
                                        }
                                }
                            }

                            super.visitFieldInsn(opcode, owner, name, desc);
                        } else if (owner.equals("org/bukkit/Material")) {
                            try {
                                Material.valueOf("LEGACY_" + name);
                            } catch (IllegalArgumentException illegalargumentexception) {
                                throw new AuthorNagException("No legacy enum constant for " + name + ". Did you forget to define a modern (1.13+) api-version in your plugin.yml?");
                            }

                            super.visitFieldInsn(opcode, owner, "LEGACY_" + name, desc);
                        } else {
                            if (owner.equals("org/bukkit/Art")) {
                                switch (name.hashCode()) {
                                    case 324335498:
                                        if (name.equals("BURNINGSKULL")) {
                                            super.visitFieldInsn(opcode, owner, "BURNING_SKULL", desc);
                                            return;
                                        }
                                        break;
                                    case 1165438553:
                                        if (name.equals("DONKEYKONG")) {
                                            super.visitFieldInsn(opcode, owner, "DONKEY_KONG", desc);
                                            return;
                                        }
                                }
                            }

                            if (owner.equals("org/bukkit/DyeColor")) {
                                switch (name.hashCode()) {
                                    case -1848981747:
                                        if (name.equals("SILVER")) {
                                            super.visitFieldInsn(opcode, owner, "LIGHT_GRAY", desc);
                                            return;
                                        }
                                }
                            }

                            if (owner.equals("org/bukkit/Particle")) {
                                label167:
                                {
                                    switch (name.hashCode()) {
                                        case -1365115510:
                                            if (!name.equals("FALLING_DUST")) {
                                                break label167;
                                            }
                                            break;
                                        case -13184184:
                                            if (!name.equals("BLOCK_CRACK")) {
                                                break label167;
                                            }
                                            break;
                                        case 1800723268:
                                            if (!name.equals("BLOCK_DUST")) {
                                                break label167;
                                            }
                                            break;
                                        default:
                                            break label167;
                                    }

                                    super.visitFieldInsn(opcode, owner, "LEGACY_" + name, desc);
                                    return;
                                }
                            }

                            super.visitFieldInsn(opcode, owner, name, desc);
                        }
                    }

                    public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
                        if (owner.equals("org/bukkit/map/MapView") && name.equals("getId") && desc.equals("()S")) {
                            super.visitMethodInsn(opcode, owner, name, "()I", itf);
                        } else if ((owner.equals("org/bukkit/Bukkit") || owner.equals("org/bukkit/Server")) && name.equals("getMap") && desc.equals("(S)Lorg/bukkit/map/MapView;")) {
                            super.visitMethodInsn(opcode, owner, name, "(I)Lorg/bukkit/map/MapView;", itf);
                        } else if (owner.equals("org/bukkit/entity/TextDisplay$TextAligment")) {
                            super.visitMethodInsn(opcode, "org/bukkit/entity/TextDisplay$TextAlignment", name, desc, itf);
                        } else if (desc.equals("(Lorg/bukkit/entity/TextDisplay$TextAligment;)V")) {
                            super.visitMethodInsn(opcode, owner, name, "(Lorg/bukkit/entity/TextDisplay$TextAlignment;)V", itf);
                        } else if (desc.equals("()Lorg/bukkit/entity/TextDisplay$TextAligment;")) {
                            super.visitMethodInsn(opcode, owner, name, "()Lorg/bukkit/entity/TextDisplay$TextAlignment;", itf);
                        } else if (owner.startsWith("org/bukkit") && desc.contains("org/bukkit/util/Consumer")) {
                            super.visitMethodInsn(opcode, owner, name, desc.replace("org/bukkit/util/Consumer", "java/util/function/Consumer"), itf);
                        } else if (modern) {
                            if (owner.equals("org/bukkit/Material")) {
                                switch (name.hashCode()) {
                                    case -1206994319:
                                        if (name.equals("ordinal")) {
                                            super.visitMethodInsn(184, "org/bukkit/craftbukkit/v1_20_R2/util/CraftLegacy", "modern_" + name, "(Lorg/bukkit/Material;)I", false);
                                            return;
                                        }
                                        break;
                                    case -823812830:
                                        if (name.equals("values")) {
                                            super.visitMethodInsn(opcode, "org/bukkit/craftbukkit/v1_20_R2/util/CraftLegacy", "modern_" + name, desc, itf);
                                            return;
                                        }
                                }
                            }

                            super.visitMethodInsn(opcode, owner, name, desc, itf);
                        } else if (owner.equals("org/bukkit/ChunkSnapshot") && name.equals("getBlockData") && desc.equals("(III)I")) {
                            super.visitMethodInsn(opcode, owner, "getData", desc, itf);
                        } else {
                            Type retType = Type.getReturnType(desc);

                            if (!Commodore.EVIL.contains(owner + " " + desc + " " + name) && (!owner.startsWith("org/bukkit/block/") || !(desc + " " + name).equals("()I getTypeId")) && (!owner.startsWith("org/bukkit/block/") || !(desc + " " + name).equals("(I)Z setTypeId")) && (!owner.startsWith("org/bukkit/block/") || !(desc + " " + name).equals("()Lorg/bukkit/Material; getType"))) {
                                if (owner.equals("org/bukkit/DyeColor") && name.equals("valueOf") && desc.equals("(Ljava/lang/String;)Lorg/bukkit/DyeColor;")) {
                                    super.visitMethodInsn(opcode, owner, "legacyValueOf", desc, itf);
                                } else {
                                    if (owner.equals("org/bukkit/Material")) {
                                        label170:
                                        {
                                            if (name.equals("getMaterial") && desc.equals("(I)Lorg/bukkit/Material;")) {
                                                super.visitMethodInsn(opcode, "org/bukkit/craftbukkit/v1_20_R2/legacy/CraftEvil", name, desc, itf);
                                                return;
                                            }

                                            label118:
                                            {
                                                switch (name.hashCode()) {
                                                    case -1918000483:
                                                        if (!name.equals("getMaterial")) {
                                                            break label170;
                                                        }
                                                        break label118;
                                                    case -1776922004:
                                                        if (!name.equals("toString")) {
                                                            break label170;
                                                        }
                                                        break;
                                                    case -1206994319:
                                                        if (name.equals("ordinal")) {
                                                            super.visitMethodInsn(184, "org/bukkit/craftbukkit/v1_20_R2/legacy/CraftLegacy", "ordinal", "(Lorg/bukkit/Material;)I", false);
                                                            return;
                                                        }
                                                        break label170;
                                                    case -1180955124:
                                                        if (!name.equals("matchMaterial")) {
                                                            break label170;
                                                        }
                                                        break label118;
                                                    case -823812830:
                                                        if (!name.equals("values")) {
                                                            break label170;
                                                        }
                                                        break label118;
                                                    case 3373707:
                                                        if (!name.equals("name")) {
                                                            break label170;
                                                        }
                                                        break;
                                                    case 231605032:
                                                        if (!name.equals("valueOf")) {
                                                            break label170;
                                                        }
                                                        break label118;
                                                    default:
                                                        break label170;
                                                }

                                                super.visitMethodInsn(184, "org/bukkit/craftbukkit/v1_20_R2/legacy/CraftLegacy", name, "(Lorg/bukkit/Material;)Ljava/lang/String;", false);
                                                return;
                                            }

                                            super.visitMethodInsn(opcode, "org/bukkit/craftbukkit/v1_20_R2/legacy/CraftLegacy", name, desc, itf);
                                            return;
                                        }
                                    }

                                    if (retType.getSort() == 10 && retType.getInternalName().equals("org/bukkit/Material") && owner.startsWith("org/bukkit")) {
                                        super.visitMethodInsn(opcode, owner, name, desc, itf);
                                        super.visitMethodInsn(184, "org/bukkit/craftbukkit/v1_20_R2/legacy/CraftLegacy", "toLegacy", "(Lorg/bukkit/Material;)Lorg/bukkit/Material;", false);
                                    } else {
                                        super.visitMethodInsn(opcode, owner, name, desc, itf);
                                    }
                                }
                            } else {
                                Type[] args = Type.getArgumentTypes(desc);
                                Type[] newArgs = new Type[args.length + 1];

                                newArgs[0] = Type.getObjectType(owner);
                                System.arraycopy(args, 0, newArgs, 1, args.length);
                                super.visitMethodInsn(184, "org/bukkit/craftbukkit/v1_20_R2/legacy/CraftEvil", name, Type.getMethodDescriptor(retType, newArgs), false);
                            }
                        }
                    }

                    public void visitLdcInsn(Object value) {
                        if (value instanceof String && ((String) value).equals("com.mysql.jdbc.Driver")) {
                            super.visitLdcInsn("com.mysql.cj.jdbc.Driver");
                        } else {
                            super.visitLdcInsn(value);
                        }
                    }

                    public void visitInvokeDynamicInsn(String name, String descriptor, Handle bootstrapMethodHandle, Object... bootstrapMethodArguments) {
                        if (bootstrapMethodHandle.getOwner().equals("java/lang/invoke/LambdaMetafactory") && bootstrapMethodHandle.getName().equals("metafactory") && bootstrapMethodArguments.length == 3) {
                            Type samMethodType = (Type) bootstrapMethodArguments[0];
                            Handle implMethod = (Handle) bootstrapMethodArguments[1];
                            Type instantiatedMethodType = (Type) bootstrapMethodArguments[2];
                            ArrayList newTypes = new ArrayList();

                            newTypes.add(samMethodType);
                            if (implMethod.getOwner().startsWith("org/bukkit") && implMethod.getDesc().contains("org/bukkit/util/Consumer")) {
                                implMethod = new Handle(implMethod.getTag(), implMethod.getOwner(), implMethod.getName(), implMethod.getDesc().replace("org/bukkit/util/Consumer", "java/util/function/Consumer"), implMethod.isInterface());
                            }

                            newTypes.add(implMethod);
                            newTypes.add(instantiatedMethodType);
                            super.visitInvokeDynamicInsn(name, descriptor, bootstrapMethodHandle, newTypes.toArray());
                        } else {
                            super.visitInvokeDynamicInsn(name, descriptor, bootstrapMethodHandle, bootstrapMethodArguments);
                        }
                    }
                };
            }
        }, 0);
        return cw.toByteArray();
    }
}
