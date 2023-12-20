package org.kettingpowered.ketting;

import org.kettingpowered.ketting.internal.KettingConstants;
import org.kettingpowered.ketting.internal.utils.JarTool;

import java.io.File;

public class KettingFiles {
    public static final String DATA_DIR = "data/";

    private static final String
            MC = KettingConstants.MINECRAFT_VERSION,
            FORGE = KettingConstants.FORGE_VERSION,
            MCP = KettingConstants.MCP_VERSION,
            MC_FORGE = MC + "-" + FORGE,
            MC_MCP = MC + "-" + MCP;

    public static final String LIBRARIES_PATH = new File(JarTool.getJarDir(), KettingConstants.INSTALLER_LIBRARIES_FOLDER).getAbsolutePath() + "/";
    public static final String KETTING_PATH = new File(LIBRARIES_PATH, "org/kettingpowered/ketting").getAbsolutePath() + "/";
    public static final String LOGS_PATH = new File(System.getProperty("user.dir"), "logs").getAbsolutePath() + "/";

    public static final File INSTALL_DIR = new File(KETTING_PATH, "install/");

    public static final File FORGE_BASE_DIR = new File(LIBRARIES_PATH, "net/minecraftforge/");
    public static final File MCP_BASE_DIR = new File(LIBRARIES_PATH, "de/oceanlabs/mcp_config/" + MC_MCP + "/");
    public static final File NMS_BASE_DIR = new File(LIBRARIES_PATH, "net/minecraft/server/" + MC + "/");
    public static final File NMS_PATCHES_DIR = new File(LIBRARIES_PATH, "net/minecraft/server/" + MC_MCP + "/");

    public static final String
            FMLCORE_NAME = "fmlcore-" + MC_FORGE + ".jar",
            FMLLOADER_NAME = "fmlloader-" + MC_FORGE + ".jar",
            JAVAFMLLANGUAGE_NAME = "javafmllanguage-" + MC_FORGE + ".jar",
            LOWCODELANGUAGE_NAME = "lowcodelanguage-" + MC_FORGE + ".jar",
            MCLANGUAGE_NAME = "mclanguage-" + MC_FORGE + ".jar";

    public static final File
            FMLCORE = new File(FORGE_BASE_DIR, "fmlcore/" + MC_FORGE + "/" + FMLCORE_NAME),
            FMLLOADER = new File(FORGE_BASE_DIR, "fmlloader/" + MC_FORGE + "/" + FMLLOADER_NAME),
            JAVAFMLLANGUAGE = new File(FORGE_BASE_DIR, "javafmllanguage/" + MC_FORGE + "/" + JAVAFMLLANGUAGE_NAME),
            LOWCODELANGUAGE = new File(FORGE_BASE_DIR, "lowcodelanguage/" + MC_FORGE + "/" + LOWCODELANGUAGE_NAME),
            MCLANGUAGE = new File(FORGE_BASE_DIR, "mclanguage/" + MC_FORGE + "/" + MCLANGUAGE_NAME);

    public static final String
            FORGE_UNIVERSAL_NAME = "forge-" + MC_FORGE + "-universal.jar";

    public static final File
            FORGE_UNIVERSAL_JAR = new File(FORGE_BASE_DIR, "forge/" + MC_FORGE + "/" + FORGE_UNIVERSAL_NAME),
            FORGE_PATCHED_JAR = new File(FORGE_BASE_DIR, "forge/" + MC_FORGE + "/forge-" + MC_FORGE + "-server.jar");

    public static final File
            MCP_ZIP = new File(MCP_BASE_DIR, "mcp_config-" + MC_MCP + ".zip"),
            SERVER_JAR = new File(NMS_BASE_DIR, "server-" + MC + ".jar"),
            SERVER_UNPACKED = new File(NMS_PATCHES_DIR, "server-" + MC_MCP + "-unpacked.jar"),
            SERVER_SLIM = new File(NMS_PATCHES_DIR, "server-" + MC_MCP + "-slim.jar"),
            SERVER_EXTRA = new File(NMS_PATCHES_DIR, "server-" + MC_MCP + "-extra.jar"),
            SERVER_SRG = new File(NMS_PATCHES_DIR, "server-" + MC_MCP + "-srg.jar"),
            MCP_MAPPINGS = new File(MCP_BASE_DIR, "mappings.txt"),
            MOJANG_MAPPINGS = new File(NMS_PATCHES_DIR, "mappings.txt"),
            MERGED_MAPPINGS = new File(MCP_BASE_DIR, "mappings-merged.txt"),
            SERVER_LZMA = new File(INSTALL_DIR, "server.lzma");

    public static final File
            STORED_HASHES = new File(INSTALL_DIR, "hashes.txt"),
            PATCHER_LOGS = new File(LOGS_PATH, "install.txt");
}
