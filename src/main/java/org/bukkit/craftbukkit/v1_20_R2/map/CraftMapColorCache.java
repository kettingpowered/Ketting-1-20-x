package org.bukkit.craftbukkit.v1_20_R2.map;

import com.google.common.base.Preconditions;
import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Logger;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterInputStream;
import net.minecraft.Util;
import org.bukkit.map.MapPalette;
import org.bukkit.map.MapPalette.MapColorCache;

public class CraftMapColorCache implements MapColorCache {

    private static final String MD5_CACHE_HASH = "E88EDD068D12D39934B40E8B6B124C83";
    private static final File CACHE_FILE = new File("map-color-cache.dat");
    private byte[] cache;
    private final Logger logger;
    private boolean cached = false;
    private final AtomicBoolean running = new AtomicBoolean(false);

    public CraftMapColorCache(Logger logger) {
        this.logger = logger;
    }

    public static void main(String[] args) {
        CraftMapColorCache craftMapColorCache = new CraftMapColorCache(Logger.getGlobal());

        craftMapColorCache.buildCache();

        try {
            byte[] hash = MessageDigest.getInstance("MD5").digest(craftMapColorCache.cache);

            System.out.println("MD5_CACHE_HASH: " + bytesToString(hash));
        } catch (NoSuchAlgorithmException nosuchalgorithmexception) {
            nosuchalgorithmexception.printStackTrace();
        }

    }

    public static String bytesToString(byte[] bytes) {
        char[] chars = "0123456789ABCDEF".toCharArray();
        StringBuilder builder = new StringBuilder();
        byte[] abyte = bytes;
        int i = bytes.length;

        for (int j = 0; j < i; ++j) {
            byte value = abyte[j];
            int first = (value & 240) >> 4;
            int second = value & 15;

            builder.append(chars[first]);
            builder.append(chars[second]);
        }

        return builder.toString();
    }

    public CompletableFuture initCache() {
        Preconditions.checkState(!this.cached && !this.running.getAndSet(true), "Cache is already build or is currently being build");
        this.cache = new byte[16777216];
        if (CraftMapColorCache.CACHE_FILE.exists()) {
            byte[] fileContent;

            try {
                Throwable throwable = null;
                Object object = null;

                try {
                    InflaterInputStream inputStream = new InflaterInputStream(new FileInputStream(CraftMapColorCache.CACHE_FILE));

                    try {
                        fileContent = inputStream.readAllBytes();
                    } finally {
                        if (inputStream != null) {
                            inputStream.close();
                        }

                    }
                } catch (Throwable throwable1) {
                    if (throwable == null) {
                        throwable = throwable1;
                    } else if (throwable != throwable1) {
                        throwable.addSuppressed(throwable1);
                    }

                    throw throwable;
                }
            } catch (IOException ioexception) {
                this.logger.warning("Error while reading map color cache");
                ioexception.printStackTrace();
                return CompletableFuture.completedFuture((Object) null);
            }

            byte[] hash;

            try {
                hash = MessageDigest.getInstance("MD5").digest(fileContent);
            } catch (NoSuchAlgorithmException nosuchalgorithmexception) {
                this.logger.warning("Error while hashing map color cache");
                nosuchalgorithmexception.printStackTrace();
                return CompletableFuture.completedFuture((Object) null);
            }

            if (!"E88EDD068D12D39934B40E8B6B124C83".equals(bytesToString(hash))) {
                this.logger.info("Map color cache hash invalid, rebuilding cache in the background");
                return this.buildAndSaveCache();
            } else {
                System.arraycopy(fileContent, 0, this.cache, 0, fileContent.length);
                this.cached = true;
                return CompletableFuture.completedFuture((Object) null);
            }
        } else {
            this.logger.info("Map color cache not found, building it in the background");
            return this.buildAndSaveCache();
        }
    }

    private void buildCache() {
        for (int r = 0; r < 256; ++r) {
            for (int g = 0; g < 256; ++g) {
                for (int b = 0; b < 256; ++b) {
                    Color color = new Color(r, g, b);

                    this.cache[this.toInt(color)] = MapPalette.matchColor(color);
                }
            }
        }

    }

    private CompletableFuture buildAndSaveCache() {
        return CompletableFuture.runAsync(() -> {
            this.buildCache();
            if (!CraftMapColorCache.CACHE_FILE.exists()) {
                try {
                    if (!CraftMapColorCache.CACHE_FILE.createNewFile()) {
                        this.cached = true;
                        return;
                    }
                } catch (IOException ioexception) {
                    this.logger.warning("Error while building map color cache");
                    ioexception.printStackTrace();
                    this.cached = true;
                    return;
                }
            }

            try {
                Throwable throwable = null;
                Object object = null;

                try {
                    DeflaterOutputStream outputStream = new DeflaterOutputStream(new FileOutputStream(CraftMapColorCache.CACHE_FILE));

                    try {
                        outputStream.write(this.cache);
                    } finally {
                        if (outputStream != null) {
                            outputStream.close();
                        }

                    }
                } catch (Throwable throwable1) {
                    if (throwable == null) {
                        throwable = throwable1;
                    } else if (throwable != throwable1) {
                        throwable.addSuppressed(throwable1);
                    }

                    throw throwable;
                }
            } catch (IOException ioexception1) {
                this.logger.warning("Error while building map color cache");
                ioexception1.printStackTrace();
                this.cached = true;
                return;
            }

            this.cached = true;
            this.logger.info("Map color cache build successfully");
        }, Util.backgroundExecutor());
    }

    private int toInt(Color color) {
        return color.getRGB() & 16777215;
    }

    public boolean isCached() {
        return this.cached || !this.running.get() && this.initCache().isDone();
    }

    public byte matchColor(Color color) {
        Preconditions.checkState(this.isCached(), "Cache not build jet");
        return this.cache[this.toInt(color)];
    }
}
