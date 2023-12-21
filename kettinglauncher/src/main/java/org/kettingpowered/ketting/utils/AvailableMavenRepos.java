package org.kettingpowered.ketting.utils;

import java.util.List;

public final class AvailableMavenRepos {

    public static final List<String> INSTANCE = List.of(
            "https://nexus.c0d3m4513r.com/repository/Magma/",
            "https://nexus.c0d3m4513r.com/repository/Ketting/",
            "https://repo1.maven.org/maven2/",
            "https://libraries.minecraft.net/",
            "https://maven.minecraftforge.net/"
    );

    public static boolean isLast(String repo) {
        return INSTANCE.get(INSTANCE.size() - 1).equals(repo);
    }
}
