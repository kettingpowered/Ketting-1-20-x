package org.kettingpowered.ketting.common;

import java.util.Objects;

public class KettingConstants {

    private static final String fullVersion = (KettingConstants.class.getPackage().getImplementationVersion() != null) ? KettingConstants.class.getPackage().getImplementationVersion() : "dev-env";

    public static final String NAME = "Ketting";
    public static final String BRAND = "Kettingpowered";
    public static final String VERSION = !Objects.equals(fullVersion, "dev-env") ? fullVersion.split("-")[0] + "-" + fullVersion.split("-")[2] : "dev-env";
    public static final String BUKKIT_VERSION = "v1_20.2_R0.1";
    public static final String FORGE_VERSION_FULL = fullVersion;
    public static final String FORGE_VERSION = fullVersion.substring(0, fullVersion.lastIndexOf("-") - 1);
    public static final String NMS_PREFIX = "net/minecraft/server/";
    public static final String INSTALLER_LIBRARIES_FOLDER = "libraries";

}
