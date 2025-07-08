import gg.essential.gradle.util.noServerRunConfigs

plugins {
    kotlin("jvm") version "1.8.22" apply false
    id("gg.essential.multi-version")
    id("gg.essential.defaults")
}

val modGroup: String by project
val modBaseName: String by project
group = modGroup
base.archivesName.set("$modBaseName-${platform.mcVersionStr}")

loom {
    noServerRunConfigs()

}



repositories {
    maven("https://repo.essential.gg/repository/maven-public/")
}

val embed by configurations.creating
configurations.implementation.get().extendsFrom(embed)

dependencies {
    //compileOnly("gg.essential:essential-$platform:4246+g8be73312c")
    compileOnly("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.8.22")
    if(project.name.contains("1.16.5")) {
        modImplementation("net.fabricmc.fabric-api:fabric-api:0.42.0+1.16")
    }
    else if (project.name.contains("1.21.5")) {
        modImplementation("net.fabricmc.fabric-api:fabric-api:0.128.1+1.21.5")
    }
    //embed("gg.essential:loader-launchwrapper:1.1.3")

}
