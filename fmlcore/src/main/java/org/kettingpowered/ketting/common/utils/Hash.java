package org.kettingpowered.ketting.common.utils;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

// Code copied from package dev.vankka.dependencydownload, all credit goes to the author
// Check them out: https://github.com/Vankka/DependencyDownload
public class Hash {

    public static String getHash(File file, String algorithm) throws NoSuchAlgorithmException, IOException {
        try (FileInputStream stream = new FileInputStream(file)) {
            return getHash(stream, algorithm);
        }
    }

    public static String getHash(InputStream stream, String algorithm) throws NoSuchAlgorithmException, IOException {
        MessageDigest digest = MessageDigest.getInstance(algorithm);

        byte[] buffer = new byte[1024];
        int total;
        while ((total = stream.read(buffer)) != -1) {
            digest.update(buffer, 0, total);
        }

        return getHash(digest);
    }

    public static String getHash(byte[] bytes, String algorithm) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance(algorithm);
        digest.update(bytes);
        return getHash(digest);
    }

    public static String getHash(MessageDigest digest) {
        StringBuilder result = new StringBuilder();
        for (byte b : digest.digest()) {
            result.append(String.format("%02x", b));
        }
        return result.toString();
    }
}
