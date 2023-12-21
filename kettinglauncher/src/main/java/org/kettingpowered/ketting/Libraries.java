package org.kettingpowered.ketting;

import dev.vankka.dependencydownload.dependency.Dependency;
import dev.vankka.dependencydownload.repository.Repository;
import dev.vankka.dependencydownload.repository.StandardRepository;
import me.tongfei.progressbar.ProgressBar;
import me.tongfei.progressbar.ProgressBarBuilder;
import me.tongfei.progressbar.ProgressBarStyle;
import org.kettingpowered.ketting.internal.KettingConstants;
import org.kettingpowered.ketting.common.betterui.BetterUI;
import org.kettingpowered.ketting.internal.utils.Hash;
import org.kettingpowered.ketting.internal.utils.JarTool;
import org.kettingpowered.ketting.internal.utils.NetworkUtils;
import org.kettingpowered.ketting.common.utils.ShortenedStackTrace;
import org.kettingpowered.ketting.utils.AvailableMavenRepos;
import org.kettingpowered.ketting.utils.LibHelper;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Libraries {

    @SuppressWarnings("FieldMayBeFinal") //If this class is loaded in a child-class loader we may need to set this.
    private static List<URL> loadedLibs = new ArrayList<>();

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
            passthrough_kettingLauncher(loader, true);
            passthrough_libraries(loader).getDeclaredConstructor(List.class).newInstance(urls);
            passthrough_kettingLauncher(loader, false);
        } catch (InvocationTargetException e) {
            System.err.println("Something went wrong while trying to load libraries");
            ShortenedStackTrace.findCause(e).printStackTrace();
            System.exit(1);
        }
    }
    private static Class<?> passthrough_libraries(ClassLoader loader) throws IllegalAccessException, ClassNotFoundException, NoSuchFieldException {
        return passthrough_static_field(Libraries.class, "loadedLibs", loader, true);
    }
    private static Class<?> passthrough_kettingLauncher(ClassLoader loader, boolean forward) throws IllegalAccessException, ClassNotFoundException, NoSuchFieldException {
        passthrough_static_field(KettingLauncher.class, "args", loader, forward);
        passthrough_static_field(KettingLauncher.class, "target", loader, forward);
        return passthrough_static_field(KettingLauncher.class, "enableUpdate", loader, forward);
    }

    private static Class<?> passthrough_static_field(Class<?> clazz, String field, ClassLoader loader, boolean forward) throws IllegalAccessException, ClassNotFoundException, NoSuchFieldException {
        Class<?> clazz_loader = loader.loadClass(clazz.getName());
        //Pass through the loadedLibs instance from this class loaded by the current class loader
        // to the class instance loaded by the new class loader
        Field loaderField = clazz_loader.getDeclaredField(field);
        loaderField.setAccessible(true);
        
        Field origField = clazz.getDeclaredField(field);
        origField.setAccessible(true);

        if (forward) loaderField.set(null, origField.get(null));
        else origField.set(null, loaderField.get(null));
        return clazz_loader;
    }

    public Libraries(List<URL> internalLibs) throws IOException {
        loadedLibs.addAll(internalLibs);
        downloadMcp();
        downloadExternal();
    }

    private void downloadExternal() throws IOException {
        List<Dependency> dependencies = LibHelper.getManager().getDependencies();
        dependencies.stream()
                .filter(dependency -> dependency.getGroupId().equals("org.kettingpowered") && dependency.getArtifactId().equals("kettingcore"))
                .forEach(this::loadDep);

        final URL self = JarTool.getJar().toURI().toURL();
        callback_main("core_init", self);
        
        ProgressBarBuilder builder = new ProgressBarBuilder()
                .setTaskName("Loading libs...")
                .hideEta()
                .setMaxRenderedLength(BetterUI.LOGO_LENGTH)
                .setStyle(ProgressBarStyle.ASCII)
                .setUpdateIntervalMillis(100)
                .setInitialMax(dependencies.size());

        ProgressBar.wrap(dependencies.stream(), builder)
                .forEach(this::loadDep);

        callback_main("lib_init", self);
        loadedLibs.add(self); //Make sure that the classloader has access to this code
    }
    
    private void callback_main(String method, URL self) {
        List<URL> init_libs = new ArrayList<>(loadedLibs);
        init_libs.add(self); //Make sure that the classloader has access to this code

        try (URLClassLoader loader = new LibraryClassLoader(init_libs.toArray(URL[]::new))) {
            passthrough_libraries(loader);
            passthrough_kettingLauncher(loader, true).getDeclaredMethod(method).invoke(null);
            passthrough_kettingLauncher(loader, false);
        } catch (Exception e) {
            System.err.println("Something went wrong while trying to init KettingLauncher");
            e.printStackTrace();
            System.exit(1);
        }
    }
    
    private void loadDep(Dependency dep){
        try {
            LibHelper.downloadDependency(dep);
            LibHelper.loadDependency(dep, path -> loadedLibs.add(path.toUri().toURL()));
        } catch (Exception e) {
            throw new RuntimeException("Something went wrong while trying to load dependencies", e);
        }
    }

    private void downloadMcp() throws IOException {
        if (KettingFiles.MCP_ZIP.exists()) return;

        String mcMcp = KettingConstants.MINECRAFT_VERSION + "-" + KettingConstants.MCP_VERSION;

        try {
            KettingFiles.MCP_ZIP.getParentFile().mkdirs();
            String mavenBasePath = "de/oceanlabs/mcp/mcp_config/" + mcMcp + "/mcp_config-" + mcMcp + ".zip";

            for (String repo : AvailableMavenRepos.INSTANCE) {
                try {
                    String fullPath = repo + mavenBasePath;
                    String hash = NetworkUtils.readFile(fullPath + ".md5");
                    NetworkUtils.downloadFile(fullPath, KettingFiles.MCP_ZIP, hash);
                    break;
                } catch (Throwable ignored) {
                    if (AvailableMavenRepos.isLast(repo)) {
                        System.err.println("Failed to download mcp_config from any repo, check your internet connection and try again.");
                        System.exit(1);
                    }

                    System.err.println("Failed to download " + mavenBasePath + " from " + repo + ", trying next repo");
                }
            }
        } catch (Exception e) {
            KettingFiles.MCP_ZIP.delete();
            throw new IOException("Failed to download MCP", e);
        }
    }

    public record Lib(File file, String path, String signature) {
        private Lib(String path, String signature) {
            this(new File(KettingFiles.LIBRARIES_PATH + (path.startsWith("/") ? path.substring(1) : path)), (path.startsWith("/") ? path.substring(1) : path), signature);
        }

        private void download() {
            for (String repo : AvailableMavenRepos.INSTANCE) {
                try {
                    String fullPath = repo + path;
                    NetworkUtils.downloadFile(fullPath, file, signature);
                    return;
                } catch (Throwable ignored) {
                    if (AvailableMavenRepos.isLast(repo)) {
                        System.err.println("Failed to download " + path + " from any repo, check your internet connection and try again.");
                        System.exit(1);
                    }

                    System.err.println("Failed to download " + path + " from " + repo + ", trying next repo");
                }
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

    static void addLoadedLib(URL url){
        if (url == null) return;
        loadedLibs.add(url);
    }
    
    public static URL[] getLoadedLibs() {
        if (loadedLibs.stream().anyMatch(Objects::isNull)) {
            System.err.println("Failed to load libraries, please try again");
            System.exit(1);
        }

        return loadedLibs.toArray(new URL[0]);
    }
}
