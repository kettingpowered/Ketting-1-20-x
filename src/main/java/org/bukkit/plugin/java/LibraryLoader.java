package org.bukkit.plugin.java;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.plugin.PluginDescriptionFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

class LibraryLoader {

    private final Logger logger;

    private static final ExecutorService executorService = Executors.newFixedThreadPool(20);

    public LibraryLoader(@NotNull Logger logger) {
        this.logger = logger;
    }

    @Nullable
    public ClassLoader createLoader(@NotNull PluginDescriptionFile desc) {
        if (desc.getLibraries().isEmpty()) {
            return null;
        }
        logger.log(Level.INFO, "[{0}] Loading {1} libraries... please wait", new Object[]
                {
                        desc.getName(), desc.getLibraries().size()
                });

        List<Dependency> dependencies = new ArrayList<>();
        for (String library : desc.getLibraries()) {
            String[] args = library.split(":");
            if (args.length >= 2) {
                Dependency dependency = new Dependency(args[0], args[1], args[2]);
                dependencies.add(dependency);
            }

        }

        List<File> downloaded = new ArrayList<>();

        for (Dependency dependency : dependencies) {
            String group = dependency.getGroup().replaceAll("\\.", "/");
            String fileName = dependency.getName() + "-"
                    + dependency.getVersion() + ".jar";
            String mavenUrl = "https://repo1.maven.org/maven2/" + group + "/"
                    + dependency.getName() + "/"
                    + dependency.getVersion() + "/" + fileName;

            File file = new File(new File("libraries"), group + "/"
                    + dependency.getName() + "/"
                    + dependency.getVersion() + "/" + fileName);

            if (file.exists()) {
                logger.log(Level.INFO, "[{0}] Found library {1}", new Object[]
                        {
                                desc.getName(), file
                        });
                downloaded.add(file);
                continue;
            }

            Future<Boolean> future = executorService.submit(() -> {
                file.getParentFile().mkdirs();
                file.createNewFile();

                try {
                    InputStream inputStream = new URL(mavenUrl).openStream();
                    writeInputStreamToFile(inputStream, file);
                    downloaded.add(file);
                    return true;
                } catch (IOException e) {
                    return false;
                }
            });


            try {
                boolean success = future.get();
                if (success) {
                    logger.log(Level.INFO, "[{0}] Downloading Library {1}", new Object[]
                            {
                                    desc.getName(), mavenUrl
                            });
                }

            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        }

        List<URL> jarFiles = new ArrayList<>();
        for (File file : downloaded) {
            URL url;
            try {
                url = file.toURI().toURL();
            } catch (MalformedURLException ex) {
                throw new AssertionError(ex);
            }

            jarFiles.add(url);
            logger.log(Level.INFO, "[{0}] Loaded library {1}", new Object[]
                    {
                            desc.getName(), file
                    });
        }

        return new URLClassLoader(jarFiles.toArray(new URL[0]), getClass().getClassLoader());
    }


    private static void writeInputStreamToFile(InputStream inputStream, File file) {
        try (inputStream) {
            try (FileOutputStream outputStream = new FileOutputStream(file)) {
                byte[] buffer = new byte[8 * 1024];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }


    public class Dependency {

        private final String group;
        private final String name;
        private final String version;

        public Dependency(String group, String name, String version) {
            this.group = group;
            this.name = name;
            this.version = version;
        }

        public String getGroup() {
            return group;
        }

        public String getName() {
            return name;
        }

        public String getVersion() {
            return version;
        }

        @Override
        public String toString() {
            return "Dependency{" +
                    "group='" + group + '\'' +
                    ", name='" + name + '\'' +
                    ", version='" + version + '\'' +
                    '}';
        }
    }

}