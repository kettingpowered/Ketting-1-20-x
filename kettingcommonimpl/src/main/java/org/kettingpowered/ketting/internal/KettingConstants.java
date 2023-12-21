package org.kettingpowered.ketting.internal;

import java.util.Objects;

public class KettingConstants {

    private static final String fullVersion = (KettingConstants.class.getPackage().getImplementationVersion() != null) ? KettingConstants.class.getPackage().getImplementationVersion() : "dev-env";

    public static final String NAME = "Ketting";
    public static final String BRAND = "Kettingpowered";
    public static final String SITE_LINK = "https://github.com/kettingpowered/";
    public static final String VERSION = !Objects.equals(fullVersion, "dev-env") ? fullVersion.split("-")[0] + "-" + fullVersion.split("-")[2] : "dev-env";
    public static final String MINECRAFT_VERSION = !Objects.equals(fullVersion, "dev-env") ? fullVersion.split("-")[0] : "dev-env";
    public static final String BUKKIT_PACKAGE_VERSION = "v1_20_R2";
    public static final String BUKKIT_VERSION = "1.20.2-R0.1-SNAPSHOT";
    public static final String FORGE_VERSION = !Objects.equals(fullVersion, "dev-env") ? fullVersion.split("-")[1] : "dev-env";
    public static final String MCP_VERSION = !Objects.equals(fullVersion, "dev-env") ? fullVersion.split("-")[3] : "dev-env";
    public static final String INSTALLER_LIBRARIES_FOLDER = "libraries";

}
