package org.bukkit.craftbukkit.v1_20_R2.util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class WorldUUID {

    private static final Logger LOGGER = LogManager.getLogger();

    private WorldUUID() {}

    public static UUID getUUID(File baseDir) {
        File file1 = new File(baseDir, "uid.dat");

        if (file1.exists()) {
            label189:
            {
                DataInputStream dis = null;

                UUID uuid;

                try {
                    dis = new DataInputStream(new FileInputStream(file1));
                    uuid = new UUID(dis.readLong(), dis.readLong());
                } catch (IOException ioexception) {
                    WorldUUID.LOGGER.warn("Failed to read " + file1 + ", generating new random UUID", ioexception);
                    break label189;
                } finally {
                    if (dis != null) {
                        try {
                            dis.close();
                        } catch (IOException ioexception1) {
                            ;
                        }
                    }

                }

                return uuid;
            }
        }

        UUID uuid = UUID.randomUUID();
        DataOutputStream dos = null;

        try {
            dos = new DataOutputStream(new FileOutputStream(file1));
            dos.writeLong(uuid.getMostSignificantBits());
            dos.writeLong(uuid.getLeastSignificantBits());
        } catch (IOException ioexception2) {
            WorldUUID.LOGGER.warn("Failed to write " + file1, ioexception2);
        } finally {
            if (dos != null) {
                try {
                    dos.close();
                } catch (IOException ioexception3) {
                    ;
                }
            }

        }

        return uuid;
    }
}
