package org.kettingpowered.ketting;

import java.net.URL;
import java.net.URLClassLoader;

public class PatcherClassLoader extends URLClassLoader {

    public PatcherClassLoader(URL[] urls) {
        super(urls, null);
    }

    protected Class<?> findClass(String name) throws ClassNotFoundException {
        if (name.startsWith("java.sql")) //ugly hack to fix gson not loading
            return ClassLoader.getSystemClassLoader().loadClass(name);
        return super.findClass(name);
    }
}
