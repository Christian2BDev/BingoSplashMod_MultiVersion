import gg.essential.gradle.util.versionFromBuildIdAndBranch
import org.jetbrains.kotlin.gradle.targets.js.yarn.yarn

plugins {
    kotlin("jvm") version "1.8.22" apply false
    id("gg.essential.multi-version.root")
    id("gg.essential.loom")  version "1.9.31" apply false
}

// normal versions will be "1.x.x"
// betas will be "1.x.x+beta-y" / "1.x.x+branch_beta-y"
// rcs will be 1.x.x+rc-y
// extra branches will be 1.x.x+branch-y


preprocess {

    val fabric12105 = createNode("1.21.5-fabric", 12105, "yarn")
    val fabric11605 = createNode("1.16.5-fabric", 11605, "yarn")
    val forge11605 = createNode("1.16.5-forge", 11605, "mcp")
    val forge10809 = createNode("1.8.9-forge", 10809, "mcp")

    fabric12105.link(fabric11605, file("versions/mapping-1.21.5-1.16.5-fabric.txt"))
    fabric11605.link(forge11605, file("versions/mapping-1.16.5-fabric-1.16.5-forge.txt"))
    forge11605.link(forge10809, file("versions/mapping-1.16.5-forge-1.8.9.txt"))
}


