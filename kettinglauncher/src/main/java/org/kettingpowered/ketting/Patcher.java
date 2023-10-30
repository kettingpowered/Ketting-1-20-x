package org.kettingpowered.ketting;

import com.google.gson.*;
import me.tongfei.progressbar.ProgressBar;
import me.tongfei.progressbar.ProgressBarBuilder;
import me.tongfei.progressbar.ProgressBarStyle;
import org.kettingpowered.ketting.common.KettingConstants;
import org.kettingpowered.ketting.common.betterui.BetterUI;
import org.kettingpowered.ketting.common.utils.Hash;
import org.kettingpowered.ketting.common.utils.JarTool;
import org.kettingpowered.ketting.common.utils.NetworkUtils;
import org.kettingpowered.ketting.common.utils.ShortenedStackTrace;
import org.kettingpowered.ketting.utils.Processors;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;
import java.security.NoSuchAlgorithmException;
import java.util.*;

import static org.kettingpowered.ketting.common.utils.JarTool.extractJarContent;

public class Patcher {

    public static void init() throws Exception {
        URL[] urls = Libraries.getLoadedLibs();

        try (URLClassLoader loader = new PatcherClassLoader(urls)) {
            Class<?> clazz = loader.loadClass(Patcher.class.getName());
            clazz.getDeclaredConstructor().newInstance();
        } catch (InvocationTargetException e) {
            System.err.println("Something went wrong while trying to load the patcher");
            ShortenedStackTrace.findCause(e).printStackTrace();
            System.exit(1);
        } catch (IOException io) {
            System.err.println("Something went wrong while trying to patch");
            ShortenedStackTrace.findCause(io).printStackTrace();
            System.exit(1);
        }
    }

    private final String dataDir = "data/";
    private final List<JsonObject> processors = new ArrayList<>();
    private final Map<String, String> tokens = new HashMap<>();

    private final PrintStream out = System.out;
    private PrintStream log;

    public Patcher() throws IOException, NoSuchAlgorithmException {
        downloadServer();
        readInstallScript();
        extractJarContents();
        prepareTokens();
        readAndExecuteProcessors();
    }

    private void downloadServer() throws IOException {
        if (KettingFiles.SERVER_JAR.exists()) return;

        String manifest = NetworkUtils.readFile("https://launchermeta.mojang.com/mc/game/version_manifest.json");
        if (manifest == null) {
            System.err.println("Failed to download version_manifest.json");
            System.exit(1);
        }

        final JsonArray releases = JsonParser.parseString(manifest).getAsJsonObject().getAsJsonArray("versions");

        JsonObject currentRelease = null;
        for (JsonElement release : releases) {
            JsonObject releaseObject = release.getAsJsonObject();
            if (releaseObject.get("id").getAsString().equals(KettingConstants.MINECRAFT_VERSION)) {
                currentRelease = releaseObject;
                break;
            }
        }

        if (currentRelease == null) {
            System.err.println("Failed to find release for version " + KettingConstants.MINECRAFT_VERSION);
            System.exit(1);
        }

        String releaseManifest = NetworkUtils.readFile(currentRelease.get("url").getAsString());
        if (releaseManifest == null) {
            System.err.println("Failed to download release manifest");
            System.exit(1);
        }

        final JsonObject releaseObject = JsonParser.parseString(releaseManifest).getAsJsonObject();
        final JsonObject serverComponents = releaseObject.getAsJsonObject("downloads").getAsJsonObject("server");
        final String serverUrl = serverComponents.get("url").getAsString();
        final String serverHash = serverComponents.get("sha1").getAsString();

        KettingFiles.SERVER_JAR.getParentFile().mkdirs();
        try {
            NetworkUtils.downloadFile(serverUrl, KettingFiles.SERVER_JAR, serverHash, "sha1");
        } catch (Exception e) {
            throw new IOException("Failed to download server jar", e);
        }
    }

    private void readInstallScript() {
        InputStream stream = getClass().getClassLoader().getResourceAsStream(dataDir + "installscript.json");
        if (stream == null) {
            System.err.println("Failed to load installscript.json");
            System.exit(1);
        }

        final JsonObject object = JsonParser.parseReader(new InputStreamReader(stream)).getAsJsonObject();
        JsonArray rawProcessors = object.getAsJsonArray("processors");

        rawProcessors.forEach(p -> {
            JsonObject processor = p.getAsJsonObject();
            if (!processor.has("sides") || processor.get("sides").getAsString().contains("server"))
                processors.add(processor);
        });

        processors.remove(0); //Remove the extracting processor, we'll handle that ourselves
    }

    private void extractJarContents() throws IOException {
        extractJarContent(dataDir + KettingFiles.FORGE_UNIVERSAL_NAME, KettingFiles.FORGE_UNIVERSAL_JAR);
        extractJarContent(dataDir + KettingFiles.FMLCORE_NAME, KettingFiles.FMLCORE);
        extractJarContent(dataDir + KettingFiles.FMLEARLYDISPLAY_NAME, KettingFiles.FMLEARLYDISPLAY);
        extractJarContent(dataDir + KettingFiles.FMLLOADER_NAME, KettingFiles.FMLLOADER);
        extractJarContent(dataDir + KettingFiles.JAVAFMLLANGUAGE_NAME, KettingFiles.JAVAFMLLANGUAGE);
        extractJarContent(dataDir + KettingFiles.LOWCODELANGUAGE_NAME, KettingFiles.LOWCODELANGUAGE);
        extractJarContent(dataDir + KettingFiles.MCLANGUAGE_NAME, KettingFiles.MCLANGUAGE);
        extractJarContent(dataDir + "server.lzma", KettingFiles.SERVER_LZMA);
    }

    private void prepareTokens() {
        tokens.put("{SIDE}", "server");
        tokens.put("{ROOT}/libraries/", KettingFiles.LIBRARIES_PATH); //Change the libraries folder to our custom one
        tokens.put("{MINECRAFT_JAR}", KettingFiles.SERVER_JAR.getAbsolutePath());
        tokens.put("{MC_UNPACKED}", KettingFiles.SERVER_UNPACKED.getAbsolutePath());
        tokens.put("{MAPPINGS}", KettingFiles.MCP_MAPPINGS.getAbsolutePath());
        tokens.put("{MOJMAPS}", KettingFiles.MOJANG_MAPPINGS.getAbsolutePath());
        tokens.put("{MERGED_MAPPINGS}", KettingFiles.MERGED_MAPPINGS.getAbsolutePath());
        tokens.put("{MC_SLIM}", KettingFiles.SERVER_SLIM.getAbsolutePath());
        tokens.put("{MC_EXTRA}", KettingFiles.SERVER_EXTRA.getAbsolutePath());
        tokens.put("{MC_SRG}", KettingFiles.SERVER_SRG.getAbsolutePath());
        tokens.put("{PATCHED}", KettingFiles.FORGE_PATCHED_JAR.getAbsolutePath());
        tokens.put("{BINPATCH}", KettingFiles.SERVER_LZMA.getAbsolutePath());
    }

    private void readAndExecuteProcessors() throws NoSuchAlgorithmException, IOException {
        if (!updateNeeded()) return;

        final File logFile = KettingFiles.PATCHER_LOGS;
        if (!logFile.exists()) {
            try {
                logFile.getParentFile().mkdirs();
                logFile.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        log = new PrintStream(new FileOutputStream(KettingFiles.PATCHER_LOGS) {
            @Override
            public void write(int b) {
                out.write(b);
            }
        });

        ProgressBar progressBar = new ProgressBarBuilder()
                .setTaskName("Patching...")
                .hideEta()
                .setMaxRenderedLength(BetterUI.LOGO_LENGTH)
                .setStyle(ProgressBarStyle.ASCII)
                .setUpdateIntervalMillis(100)
                .setInitialMax(processors.size())
                .build();

        processors.forEach(processorData -> {
            String jar = processorData.get("jar").getAsString();
            JsonArray args = processorData.getAsJsonArray("args");
            List<String> parsedArgs = new ArrayList<>();

            args.forEach(arg -> {
                String argString = arg.getAsString();
                if (argString.startsWith("[de.oceanlabs.mcp:mcp_config:")) {
                    argString = KettingFiles.MCP_ZIP.getAbsolutePath();
                    parsedArgs.add(argString);
                    return;
                }

                for (Map.Entry<String, String> token : tokens.entrySet()) {
                    if (argString.equals(token.getKey())) {
                        argString = argString.replace(token.getKey(), token.getValue());
                        break;
                    }
                }

                parsedArgs.add(argString);
            });

            try {
                mute();
                Processors.execute(jar, parsedArgs.toArray(String[]::new));
                unmute();
                progressBar.step();
            } catch (IOException e) {
                unmute();
                throw new RuntimeException("A processor ran into an error", e);
            }
        });

        final File hashes = KettingFiles.STORED_HASHES;
        hashes.getParentFile().mkdirs();
        hashes.createNewFile();

        try (FileWriter writer = new FileWriter(hashes)) {
            writer.write("serverjar=" + Hash.getHash(JarTool.getJar(), "sha1"));
        }
    }

    private void mute() {
        System.setOut(log);
    }

    private void unmute() {
        System.setOut(out);
    }

    public static boolean updateNeeded() throws NoSuchAlgorithmException, IOException {
        final File hashes = KettingFiles.STORED_HASHES;
        if (hashes.exists()) {
            final String serverHash = Hash.getHash(JarTool.getJar(), "sha1");

            try (FileReader reader = new FileReader(hashes)) {
                final Properties properties = new Properties();
                properties.load(reader);

                final String storedServerHash = properties.getProperty("serverjar");
                if (storedServerHash != null && storedServerHash.equals(serverHash))
                    return false;
            }
        }
        return true;
    }
}
