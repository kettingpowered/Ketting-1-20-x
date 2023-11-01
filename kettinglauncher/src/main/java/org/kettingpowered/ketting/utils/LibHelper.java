package org.kettingpowered.ketting.utils;

import dev.vankka.dependencydownload.DependencyManager;
import dev.vankka.dependencydownload.classpath.ClasspathAppender;
import dev.vankka.dependencydownload.dependency.Dependency;
import dev.vankka.dependencydownload.path.CleanupPathProvider;
import dev.vankka.dependencydownload.path.DependencyPathProvider;
import dev.vankka.dependencydownload.repository.Repository;
import org.kettingpowered.ketting.internal.utils.Hash;
import org.kettingpowered.ketting.internal.utils.JarTool;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;


// Code copied from package dev.vankka.dependencydownload, all credit goes to the author
// Check them out: https://github.com/Vankka/DependencyDownload
public class LibHelper {

    private static DependencyManager manager;

    public static void downloadDependency(Dependency dependency, List<Repository> repositories) throws IOException, NoSuchAlgorithmException {
        if (repositories.isEmpty()) {
            throw new RuntimeException("No repositories provided");
        }

        Path dependencyPath = getManager().getPathForDependency(dependency, false);

        if (!Files.exists(dependencyPath.getParent())) {
            Files.createDirectories(dependencyPath.getParent());
        }

        if (Files.exists(dependencyPath)) {
            String fileHash = Hash.getHash(dependencyPath.toFile(), dependency.getHashingAlgorithm());
            if (fileHash.equals(dependency.getHash())) {
                // This dependency is already downloaded & the hash matches
                return;
            } else {
                Files.delete(dependencyPath);
            }
        }
        Files.createFile(dependencyPath);

        RuntimeException failure = new RuntimeException("All provided repositories failed to download dependency");
        boolean anyFailures = false;
        for (Repository repository : repositories) {
            try {
                MessageDigest digest = MessageDigest.getInstance(dependency.getHashingAlgorithm());
                downloadFromRepository(dependency, repository, dependencyPath, digest);

                String hash = Hash.getHash(digest);
                String dependencyHash = dependency.getHash();
                if (!hash.equals(dependencyHash)) {
                    throw new RuntimeException("Failed to verify file hash: " + hash + " should've been: " + dependencyHash);
                }

                // Success
                return;
            } catch (Throwable e) {
                Files.deleteIfExists(dependencyPath);
                failure.addSuppressed(e);
                anyFailures = true;
            }
        }
        if (!anyFailures) {
            throw new RuntimeException("Nothing failed yet nothing passed");
        }
        throw failure;
    }

    public static void loadDependency(Dependency dependency, ClasspathAppender classpathAppender) throws IOException {
        classpathAppender.appendFileToClasspath(getManager().getPathForDependency(dependency, false));
    }

    private static void downloadFromRepository(Dependency dependency, Repository repository, Path dependencyPath, MessageDigest digest) throws Throwable {
        URLConnection connection = repository.openConnection(dependency);

        byte[] buffer = new byte[repository.getBufferSize()];
        try (BufferedInputStream inputStream = new BufferedInputStream(connection.getInputStream())) {
            try (BufferedOutputStream outputStream = new BufferedOutputStream(Files.newOutputStream(dependencyPath))) {
                int total;
                while ((total = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, total);
                    digest.update(buffer, 0, total);
                }
            }
        }
    }

    public static DependencyManager getManager() throws IOException {
        if (manager != null) return manager;

        DependencyPathProvider provider = new CleanupPathProvider() {

            public final Path baseDirPath = JarTool.getJarDir().toPath();

            @Override
            public Path getCleanupPath() {
                return baseDirPath;
            }

            @Override
            public Path getDependencyPath(Dependency dependency, boolean relocated) {
                return baseDirPath.resolve("libraries")
                        .resolve(dependency.getGroupId().replace(".", "/"))
                        .resolve(dependency.getArtifactId())
                        .resolve(dependency.getVersion())
                        .resolve(dependency.getFileName());
            }
        };

        manager = new DependencyManager(provider);
        manager.loadFromResource(new URL("jar:file:" + JarTool.getJar().getAbsolutePath() + "!/data/ketting_libraries.txt"));
        return manager;
    }
}
