package org.kettingpowered.ketting.common.utils;

import java.io.File;

public class JarTool {

    public static File getJar() {
        return new File(JarTool.class.getProtectionDomain().getCodeSource().getLocation().getPath());
    }

    public static File getJarDir() {
        return getJar().getParentFile();
    }
}
