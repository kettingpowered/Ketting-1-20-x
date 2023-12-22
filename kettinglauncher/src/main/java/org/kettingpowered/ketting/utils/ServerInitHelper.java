package org.kettingpowered.ketting.utils;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

import java.util.*;

public class ServerInitHelper {

    private static final MethodHandles.Lookup IMPL_LOOKUP = Unsafe.lookup();

    public static void addOpens(String module, String pkg, String target) {
        if(target == null) target = "ALL-UNNAMED";

        try {
            addOpens(List.of(module + "/" + pkg + "=" + target));
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private static void addOpens(List<String> opens) throws Throwable {
        MethodHandle implAddOpensMH = IMPL_LOOKUP.findVirtual(Module.class, "implAddOpens", MethodType.methodType(void.class, String.class, Module.class));
        MethodHandle implAddOpensToAllUnnamedMH = IMPL_LOOKUP.findVirtual(Module.class, "implAddOpensToAllUnnamed", MethodType.methodType(void.class, String.class));

        addExtra(opens, implAddOpensMH, implAddOpensToAllUnnamedMH);
    }

    private static void addExtra(List<String> extras, MethodHandle implAddExtraMH, MethodHandle implAddExtraToAllUnnamedMH) {
        extras.parallelStream().forEach(extra -> {
            ParserData data = parseModuleExtra(extra);
            if(data != null) {
                ModuleLayer.boot().findModule(data.module).ifPresent(m -> {
                    try {
                        if("ALL-UNNAMED".equals(data.target)) {
                            implAddExtraToAllUnnamedMH.invokeWithArguments(m, data.packages);
                            //System.out.println("Added extra to all unnamed modules: " + data);
                        } else {
                            ModuleLayer.boot().findModule(data.target).ifPresent(tm -> {
                                try {
                                    implAddExtraMH.invokeWithArguments(m, data.packages, tm);
                                    //System.out.println("Added extra: " + data);
                                } catch (Throwable t) {
                                    throw new RuntimeException(t);
                                }
                            });
                        }
                    } catch (Throwable t) {
                        throw new RuntimeException(t);
                    }
                });
            }
        });
    }

    private static ParserData parseModuleExtra(String extra) {
        String[] all = extra.split("=", 2);
        if(all.length < 2) {
            return null;
        }

        String[] source = all[0].split("/", 2);
        if(source.length < 2) {
            return null;
        }
        return new ParserData(source[0], source[1], all[1]);
    }

    private record ParserData(String module, String packages, String target) {

        @Override
        public boolean equals(Object obj) {
            if(obj == this) return true;
            if(obj == null || obj.getClass() != this.getClass()) return false;
            var that = (ParserData) obj;
            return Objects.equals(this.module, that.module) && Objects.equals(this.packages, that.packages) && Objects.equals(this.target, that.target);
        }

        @Override
        public String toString() {
            return "ParserData[" + "module=" + module + ", " + "packages=" + packages + ", " + "target=" + target + ']';
        }

    }
}
