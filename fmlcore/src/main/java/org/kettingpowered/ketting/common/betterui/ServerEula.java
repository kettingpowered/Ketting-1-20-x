package org.kettingpowered.ketting.common.betterui;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

//A straight-up copy of the minecraft ServerEula class (well except the SharedConstants). This is only needed because the fmllauncher does not have access to minecraft's classes
public class ServerEula {
    private final Path file;
    private final boolean agreed;

    public ServerEula(Path p_i50746_1_) {
        this.file = p_i50746_1_;
        this.agreed = this.readFile();
    }

    private boolean readFile() {
        try (InputStream inputstream = Files.newInputStream(this.file)) {
            Properties properties = new Properties();
            properties.load(inputstream);
            return Boolean.parseBoolean(properties.getProperty("eula", "false"));
        } catch (Exception exception) {
            this.saveDefaults();
            return false;
        }
    }

    public boolean hasAgreedToEULA() {
        return this.agreed;
    }

    private void saveDefaults() {
        try (OutputStream outputstream = Files.newOutputStream(this.file)) {
            Properties properties = new Properties();
            properties.setProperty("eula", "false");
            properties.store(outputstream, "By changing the setting below to TRUE you are indicating your agreement to our EULA (https://account.mojang.com/documents/minecraft_eula).");
        } catch (Exception exception) {
            System.err.println("Could not save " + this.file + ": " + exception.getMessage());
            exception.printStackTrace();
        }
    }
}

