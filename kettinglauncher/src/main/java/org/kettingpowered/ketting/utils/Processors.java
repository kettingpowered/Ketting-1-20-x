package org.kettingpowered.ketting.utils;

import java.io.IOException;

public final class Processors {

    public static void execute(String processor, String[] args) throws IOException {
        processor = processor.split(":")[1];

        switch (processor) {
            case "binarypatcher":
                net.minecraftforge.binarypatcher.ConsoleTool.main(args);
                break;
            case "installertools":
                net.minecraftforge.installertools.ConsoleTool.main(args);
                break;
            case "jarsplitter":
                net.minecraftforge.jarsplitter.ConsoleTool.main(args);
                break;
            case "ForgeAutoRenamingTool":
                net.minecraftforge.fart.Main.main(args);
                break;
            default:
                throw new IllegalArgumentException("Unknown processor: " + processor);
        }
    }
}
