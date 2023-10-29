package org.kettingpowered.ketting.common.utils;

import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.security.NoSuchAlgorithmException;

public class JarTool {

    public static File getJar() {
        return new File(JarTool.class.getProtectionDomain().getCodeSource().getLocation().getPath());
    }

    public static File getJarDir() {
        return getJar().getParentFile();
    }

    public static void extractJarContent(@NotNull String from, @NotNull File to) throws IOException {
        try (InputStream internalFile = JarTool.class.getClassLoader().getResourceAsStream(from)) {
            if (internalFile == null)
                throw new IOException("Failed to extract file '" + from + "', file not found");

            byte[] internalFileContent = internalFile.readAllBytes();
            if (!to.exists() || !Hash.getHash(to, "MD5").equals(Hash.getHash(internalFileContent, "MD5"))) {
                to.getParentFile().mkdirs();
                to.createNewFile();

                try (FileOutputStream fos = new FileOutputStream(to)) {
                    fos.write(internalFileContent);
                }
            }
        } catch (NoSuchAlgorithmException e) {
            throw new IOException("Failed to extract file '" + from + "', failed to get hash algorithm", e);
        }
    }
}
