import gg.essential.gradle.util.noServerRunConfigs
import org.apache.tools.zip.Zip64Mode
import org.jetbrains.kotlin.gradle.utils.`is`

plugins {
    kotlin("jvm") version "1.8.22" apply false
    id("gg.essential.multi-version")
    id("gg.essential.defaults")
    id("com.github.johnrengelman.shadow") version "8.1.1+"
}


val modGroup: String by project
val modBaseName: String by project
group = modGroup
base.archivesName.set("$modBaseName-${platform.mcVersionStr}")

loom {
    noServerRunConfigs()

    runConfigs{
        getByName("client"){
            programArgs("Dfile.encoding=UTF-8")
            property("mixin.debug.verbose", "true")
            property("mixin.debug.export", "true")
            property("mixin.dumpTargetOnFailure", "true")
            if (project.platform.isLegacyForge) {
                programArgs("--tweakClass", "org.spongepowered.asm.launch.MixinTweaker")
                programArgs("--mixin", "mixins.bingosplash.json")
            }
        }
    }
    if (project.platform.isForge) {
        forge {
            mixinConfig("mixins.bingosplash.json")
        }
    }
    mixin {
        defaultRefmapName.set("mixins.bingosplash.refmap.json")
    }
}

repositories {
    maven("https://repo.spongepowered.org/repository/maven-public/")
    maven("https://repo.essential.gg/repository/maven-public/")

}

val embed by configurations.creating
configurations.implementation.get().extendsFrom(embed)

dependencies {
    implementation("gg.essential:vigilance:${project.property("vigilanceVersion")}")
    implementation("org.java-websocket:Java-WebSocket:1.5.4")

    if (project.name.contains("1.8.9-forge")){
        embed("org.spongepowered:mixin:0.7-SNAPSHOT")
        shadow("org.java-websocket:Java-WebSocket:1.5.4")
        modImplementation("gg.essential:universalcraft-$platform:${project.property("ucVersion")}")
        shadow("gg.essential:vigilance:${project.property("vigilanceVersion")}")
        shadow("gg.essential:universalcraft-$platform:${project.property("ucVersion")}")
    }

    if(project.name.contains("1.16.5-forge")) {
        shadow("gg.essential:vigilance:${project.property("vigilanceVersion")}")
        implementation("gg.essential:universalcraft-1.16.2-forge:${project.property("ucVersion")}")
        shadow("gg.essential:universalcraft-1.16.2-forge:${project.property("ucVersion")}")
    }
    compileOnly("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.8.22")
    if(project.name.contains("1.16.5-fabric")) {
        modImplementation("gg.essential:universalcraft-1.16.2-fabric:${project.property("ucVersion")}")
        modImplementation("net.fabricmc.fabric-api:fabric-api:0.42.0+1.16")
    }
    else if (project.name.contains("1.21.5")) {
        modImplementation("gg.essential:universalcraft-$platform:${project.property("ucVersion")}")
        modImplementation("net.fabricmc.fabric-api:fabric-api:0.128.1+1.21.5")
        modImplementation ("net.fabricmc:fabric-loader:0.16.14")
    }
}

tasks.jar {
    dependsOn(tasks.shadowJar)
    if(project.name.contains("1.8.9")) {
        dependsOn(embed)

        duplicatesStrategy = DuplicatesStrategy.INCLUDE
        from(embed.files.map { zipTree(it) }) {
            exclude("META-INF/MUMFREY.*")
        }
        manifest.attributes(
            mapOf(
                "Main-Class" to "BingoSplash",
                "FMLCorePluginContainsFMLMod" to "true",
                "ForceLoadAsMod" to "true",
                "ModSide" to "CLIENT",
                "TweakClass" to "org.spongepowered.asm.launch.MixinTweaker",
                "TweakOrder" to "0",
                "MixinConfigs" to "mixins.bingosplash.json"
            )
        )
    }
}


tasks.shadowJar{
    archiveClassifier.set("")
    configurations = listOf(project.configurations.shadow.get())

    exclude("module-info.class", "META-INF/versions/**")
    relocate("gg.essential.vigilance", "com.heckvision.vigilance")
    // vigilance dependencies
    relocate("gg.essential.elementa", "com.heckvision.elementa")
    // elementa dependencies
    relocate("gg.essential.universal", "com.heckvision.universal")
}

tasks.named("remapJar") {
    dependsOn(tasks.shadowJar)
}

