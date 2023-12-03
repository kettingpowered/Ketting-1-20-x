package org.kettingpowered.ketting;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.kettingpowered.ketting.common.utils.ShortenedStackTrace;
import org.kettingpowered.ketting.internal.KettingConstants;
import org.kettingpowered.ketting.internal.utils.JarTool;
import org.kettingpowered.ketting.internal.utils.NetworkUtils;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLClassLoader;

public class UpdateChecker {

    private static final String API_LAST_COMMIT = "https://api.github.com/repos/kettingpowered/Ketting-1-20-x/git/refs/heads/1.20.x";
    private static final String API_DOWNLOAD = "https://api.github.com/repos/kettingpowered/Ketting-1-20-x/releases/latest";

    public static void init() throws Exception {
        URL[] urls = Libraries.getLoadedLibs();

        try (URLClassLoader loader = new LibraryClassLoader(urls)) {
            Class<?> clazz = loader.loadClass(UpdateChecker.class.getName());
            clazz.getDeclaredConstructor().newInstance();
        } catch (InvocationTargetException e) {
            System.err.println("Something went wrong while trying to load the update checker");
            ShortenedStackTrace.findCause(e).printStackTrace();
            System.exit(1);
        } catch (IOException io) {
            System.err.println("Something went wrong while trying to check for updates");
            ShortenedStackTrace.findCause(io).printStackTrace();
            System.exit(1);
        }
    }

    public UpdateChecker() {
        if (checkForUpdates())
            downloadUpdate();
    }

    public boolean checkForUpdates() {
        System.out.println("Checking for %s updates...".formatted(KettingConstants.NAME));
        if (!verifyConnection()) {
            System.out.println("Failed to connect to GitHub API. Skipping update check.");
            return false;
        }

        try {
            String manifest = NetworkUtils.readFile(API_LAST_COMMIT);

            if (manifest == null) {
                System.out.println("Failed to read manifest. Skipping update check.");
                return false;
            }

            final JsonObject root = JsonParser.parseString(manifest).getAsJsonObject();
            String sha = root.get("object").getAsJsonObject().get("sha").getAsString();

            sha = sha.substring(0, 8);

            String currentSha = KettingConstants.VERSION.split("-")[1];

            if (!sha.equals(currentSha)) {
                System.out.println("New update found! Downloading...");
                return true;
            }
        } catch (Exception e) {
            System.out.println("Failed to check for updates.");
            e.printStackTrace();
        }

        return false;
    }

    public void downloadUpdate() {
        try {
            String manifest = NetworkUtils.readFile(API_DOWNLOAD);

            if (manifest == null) {
                System.out.println("Failed to read manifest. Skipping update.");
                return;
            }

            final JsonObject root = JsonParser.parseString(manifest).getAsJsonObject();

            String url = root.get("assets").getAsJsonArray().get(0).getAsJsonObject().get("browser_download_url").getAsString();
            NetworkUtils.downloadFile(url, JarTool.getJar());
            System.out.println("Update downloaded successfully! Please restart the launcher.");
        } catch (Exception e) {
            throw new RuntimeException("Failed to check for updates", e);
        }
    }

    private boolean verifyConnection() {
        try {
            return InetAddress.getByName("api.github.com").isReachable(20_000);
        } catch (IOException e) {
            return false;
        }
    }
}
