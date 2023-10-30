package org.kettingpowered.ketting;

import dev.vankka.dependencydownload.dependency.Dependency;
import dev.vankka.dependencydownload.repository.Repository;
import dev.vankka.dependencydownload.repository.StandardRepository;
import me.tongfei.progressbar.ProgressBar;
import me.tongfei.progressbar.ProgressBarBuilder;
import me.tongfei.progressbar.ProgressBarStyle;
import org.kettingpowered.ketting.common.KettingConstants;
import org.kettingpowered.ketting.common.betterui.BetterUI;
import org.kettingpowered.ketting.common.utils.Hash;
import org.kettingpowered.ketting.common.utils.JarTool;
import org.kettingpowered.ketting.common.utils.NetworkUtils;
import org.kettingpowered.ketting.common.utils.ShortenedStackTrace;
import org.kettingpowered.ketting.utils.LibHelper;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Libraries {

    private static final List<URL> loadedLibs = new ArrayList<>();

    public static void setup() throws Exception {
        Lib[] libs = {
                new Lib("dev/vankka/dependencydownload-runtime/1.3.1/dependencydownload-runtime-1.3.1.jar", "65fbb417dd6898700906a45b3501dd1b"),
                new Lib("dev/vankka/dependencydownload-common/1.3.1/dependencydownload-common-1.3.1.jar", "024cfedc649ac942621120c1774896a7"),
                new Lib("me/tongfei/progressbar/0.10.0/progressbar-0.10.0.jar", "a66b941573c5e25ba6c8baf50610dc86"),
                new Lib("net/minecrell/terminalconsoleappender/1.2.0/terminalconsoleappender-1.2.0.jar", "679363fa893293791e55a21f81342f87"),
                new Lib("org/jline/jline-reader/3.12.1/jline-reader-3.12.1.jar", "a2e7b012cd9802f83321187409174a94"),
                new Lib("org/jline/jline-terminal/3.12.1/jline-terminal-3.12.1.jar", "3c52be5ab5e3847be6e62269de924cb0"),
        };

        List<URL> urls = new ArrayList<>();

        for (Lib lib : libs) {
            if (!lib.file().exists() || !lib.signature().equals(Hash.getHash(lib.file(), "MD5"))) {
                lib.file().getParentFile().mkdirs();
                lib.download();
            }
            urls.add(lib.url());
        }

        urls.add(JarTool.getJar().toURI().toURL());

        try (URLClassLoader loader = new URLClassLoader(urls.toArray(URL[]::new), null)) {
            Class<?> clazz = loader.loadClass(Libraries.class.getName());
            clazz.getDeclaredConstructor(List.class).newInstance(urls);
        } catch (InvocationTargetException e) {
            System.err.println("Something went wrong while trying to load libraries");
            ShortenedStackTrace.findCause(e).printStackTrace();
            System.exit(1);
        }
    }

    public Libraries(List<URL> internalLibs) throws IOException {
        loadedLibs.addAll(internalLibs);
        downloadExternal();
        downloadMcp();
        System.setProperty("libs", String.join(";", loadedLibs.stream().map(URL::toString).toArray(String[]::new)));
    }

    private void downloadExternal() throws IOException {
        List<Repository> standardRepositories = new ArrayList<>();
        standardRepositories.add(new StandardRepository("https://repo1.maven.org/maven2"));
        standardRepositories.add(new StandardRepository("https://libraries.minecraft.net"));
        standardRepositories.add(new StandardRepository("https://maven.minecraftforge.net"));

        List<Dependency> dependencies = LibHelper.getManager().getDependencies();

        ProgressBarBuilder builder = new ProgressBarBuilder()
                .setTaskName("Loading libs...")
                .hideEta()
                .setMaxRenderedLength(BetterUI.LOGO_LENGTH)
                .setStyle(ProgressBarStyle.ASCII)
                .setUpdateIntervalMillis(100)
                .setInitialMax(dependencies.size());

        ProgressBar.wrap(dependencies.stream(), builder).forEach(dep -> {
            try {
                LibHelper.downloadDependency(dep, standardRepositories);
                LibHelper.loadDependency(dep, path -> loadedLibs.add(path.toUri().toURL()));
            } catch (Exception e) {
                throw new RuntimeException("Something went wrong while trying to load dependencies", e);
            }
        });

        loadedLibs.add(JarTool.getJar().toURI().toURL()); //Make sure that the classloader has access to this code
    }

    private void downloadMcp() throws IOException {
        if (KettingFiles.MCP_ZIP.exists()) return;

        String mcMcp = KettingConstants.MINECRAFT_VERSION + "-" + KettingConstants.MCP_VERSION;

        try {
            KettingFiles.MCP_ZIP.getParentFile().mkdirs();

            String baseURL = "https://maven.minecraftforge.net/de/oceanlabs/mcp/mcp_config/" + mcMcp + "/mcp_config-" + mcMcp + ".zip";
            String hash = NetworkUtils.readFile(baseURL + ".md5");
            NetworkUtils.downloadFile(baseURL, KettingFiles.MCP_ZIP, hash);
        } catch (Exception e) {
            KettingFiles.MCP_ZIP.delete();
            throw new IOException("Failed to download MCP", e);
        }
    }

    public record Lib(File file, String path, String signature) {
        private Lib(String path, String signature) {
            this(new File(KettingFiles.LIBRARIES_PATH + (path.startsWith("/") ? path.substring(1) : path)), (path.startsWith("/") ? path.substring(1) : path), signature);
        }

        private void download() throws Exception {
            try {
                NetworkUtils.downloadFile("https://repo1.maven.org/maven2/" + path, file, this.signature());
            } catch (Throwable e) {
                System.err.println("Failed to download internal depencency https://repo1.maven.org/maven2/" + path + " to " + file.getAbsolutePath() + ", check your internet connection and try again.");
                throw e;
            }
        }

        private URL url() {
            try {
                return file.toURI().toURL();
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static URL[] getLoadedLibs() {
        String libs = System.getProperty("libs");
        if (libs == null || libs.isBlank()) {
            System.err.println("Libraries did not load correctly, please try again");
            System.exit(1);
        }

        URL[] urls = Arrays.stream(libs.split(";")).map(lib -> {
            try {
                return new URL(lib);
            } catch (Exception e) {
                System.err.println("Failed to load library: " + lib);
                return null;
            }
        }).toArray(URL[]::new);

        if (Arrays.stream(urls).anyMatch(Objects::isNull)) {
            System.err.println("Failed to load libraries, please try again");
            System.exit(1);
        }

        return urls;
    }
}
