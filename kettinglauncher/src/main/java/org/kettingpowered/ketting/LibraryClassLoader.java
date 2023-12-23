package org.kettingpowered.ketting;

import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.stream.Collectors;

public class LibraryClassLoader extends URLClassLoader {

    public LibraryClassLoader() {
        this(Libraries.getLoadedLibs());
    }
    public LibraryClassLoader(ClassLoader parent) {
        super(Libraries.getLoadedLibs(), parent);
    }
    public LibraryClassLoader(URL[] libs) {
        super(libs, null);
    }

    protected Class<?> findClass(String name) throws ClassNotFoundException {
        try {
            return super.findClass(name);
        } catch (ClassNotFoundException e) {
            String name2 = name.replace(".", "/");
            if (Arrays.stream(KettingLauncher.MANUALLY_PATCHED_LIBS).anyMatch(name2::startsWith))
                return super.findClass(name); //ignore errors for manually patched libs

            return ClassLoader.getSystemClassLoader().loadClass(name);
        }
    }
}
