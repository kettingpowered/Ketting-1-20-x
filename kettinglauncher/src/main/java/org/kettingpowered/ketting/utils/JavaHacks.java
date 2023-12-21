package org.kettingpowered.ketting.utils;

import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandlerFactory;
import java.nio.file.spi.FileSystemProvider;
import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;
import java.util.Set;

public final class JavaHacks {

    //URL HACKS START - Fixes random crashes whenever a URL is created
    public static void setStreamFactory() {
        try {
            var factory = Unsafe.lookup().findStaticGetter(URL.class, "defaultFactory", URLStreamHandlerFactory.class).invoke();
            Unsafe.lookup().findStaticSetter(URL.class, "factory", URLStreamHandlerFactory.class).invoke(factory);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public static void removeStreamFactory() {
        try {
            Unsafe.lookup().findStaticSetter(URL.class, "factory", URLStreamHandlerFactory.class).invoke((Object) null);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
    //URL HACKS END

    //Loads the union file system (and others) from the classpath
    public static void loadExternalFileSystems(URLClassLoader loader) {
        try {
            ServerInitHelper.addOpens("java.base", "java.nio.file.spi", "ALL-UNNAMED");
            List<String> knownSchemes = FileSystemProvider.installedProviders().stream().map(FileSystemProvider::getScheme).toList();
            ServiceLoader<FileSystemProvider> sl = ServiceLoader.load(FileSystemProvider.class, loader);
            List<FileSystemProvider> newProviders = sl.stream().map(ServiceLoader.Provider::get).filter(provider -> !knownSchemes.contains(provider.getScheme())).toList();

            final Field installedProviders = FileSystemProvider.class.getDeclaredField("installedProviders");
            installedProviders.setAccessible(true);
            List<FileSystemProvider> providers = new ArrayList<>((List<FileSystemProvider>) installedProviders.get(null));
            providers.addAll(newProviders);
            installedProviders.set(null, providers);
        } catch (Exception e) {
            throw new RuntimeException("Could not load new file systems", e);
        }
    }

    //Fixes weird behaviour with org.apache.commons.lang.enum where enum would be seen as a java keyword
    public static void clearReservedIdentifiers() {
        try {
            Unsafe.lookup().findStaticSetter(Class.forName("jdk.internal.module.Checks"), "RESERVED", Set.class).invoke(Set.of());
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}
