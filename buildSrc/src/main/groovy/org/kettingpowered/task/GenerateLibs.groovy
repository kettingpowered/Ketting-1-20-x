package org.kettingpowered.task

import net.minecraftforge.forge.tasks.Util
import org.gradle.api.DefaultTask
import org.gradle.api.artifacts.Configuration
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction

abstract class GenerateLibs extends DefaultTask {
    @OutputFile abstract RegularFileProperty getOutput()

    GenerateLibs(){
        outputs.upToDateWhen {false}
        outputs.cacheIf { false }
        output.convention(getProject().getLayout().getBuildDirectory().file("ketting_libraries.txt"))
    }

    @TaskAction
    void genActions() {
        def entries = new HashMap<GString, GString> ()
        getProject().configurations.installer.resolvedConfiguration.resolvedArtifacts.each { dep->
            def art = dep.moduleVersion.id
            if ('junit'.equals(art.name) && 'junit'.equals(art.group)) return;
            def mavenId = "$art.group:$art.name:$art.version" + (dep.classifier != null ? ":$dep.classifier" : "") + (dep.extension != null ? "@$dep.extension" : "")
            def key = "$art.group:$art.name:" + (dep.classifier != null ? ":$dep.classifier" : "") + (dep.extension != null ? "@$dep.extension" : "")
            entries.put(key,"$dep.file.sha512\tSHA3-512\t$mavenId")
        }

        output.get().asFile.text = entries.values().join('\n')
    }
}
