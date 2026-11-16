pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
        maven("https://maven.fabricmc.net/") { name = "FabricMC" }
        maven("https://maven.neoforged.net/releases/") { name = "NeoForged" }
        maven("https://maven.minecraftforge.net/") { name = "MinecraftForge" }
        maven("https://maven.kikugie.dev/releases") { name = "KikuGie Releases" }
        maven("https://maven.kikugie.dev/snapshots") { name = "KikuGie Snapshots" }
    }
}

plugins {
    id("dev.kikugie.stonecutter") version "0.9.7"
    // Picks the Loom variant per node: remapping Loom for obfuscated Minecraft, plain Loom from 26.1.
    id("dev.kikugie.loom-back-compat") version "0.4"
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

stonecutter {
    create(rootProject) {
        /**
         * One node per loader for a Minecraft version, as `versions/{mc}-{loader}`, built by
         * `build.{loader}.gradle.kts`. Pass the version separately: as a single argument SemVer would
         * read `fabric` as a pre-release tag and order `1.21.1-fabric` below `1.21.1`.
         */
        fun match(mc: String, vararg loaders: String) {
            for (loader in loaders) {
                version("$mc-$loader", mc).buildscript("build.$loader.gradle.kts")
            }
        }

        // A node per format-compatible Minecraft range, named after the version it builds against.
        // Gaps are upstream: NeoForge starts at 1.20.2, so the 1.20-1.20.4 range is Fabric and Forge
        // only. Forge has no 1.20.5, 1.21.2 or 26.1.0 build, so those nodes compile against the next
        // release in their range and declare a narrower range than the Fabric node (see
        // stonecutter.properties.toml).
        match("1.20", "fabric")
        // Forge before 1.20.5 runs on SRG-named Minecraft and needs a reobfuscating build.
        version("1.20-forge", "1.20").buildscript("build.forge-legacy.gradle.kts")
        match("1.20.6", "fabric", "forge", "neoforge")
        match("1.21.1", "fabric", "forge", "neoforge")
        match("1.21.2", "fabric", "neoforge")
        // Forge never shipped for 1.21.2, so its build for that range targets 1.21.3. The node is
        // named after the version it builds and is published for, so the storefront listing cannot
        // advertise it for a 1.21.2 that no Forge user can be on.
        version("1.21.3-forge", "1.21.3").buildscript("build.forge.gradle.kts")
        match("1.21.4", "fabric", "forge", "neoforge")
        match("1.21.5", "fabric", "forge", "neoforge")
        match("1.21.10", "fabric", "forge", "neoforge")
        match("1.21.11", "fabric", "forge", "neoforge")
        match("26.1.2", "fabric", "forge", "neoforge")
        match("26.2", "fabric", "forge", "neoforge")
        match("26.3", "fabric", "forge", "neoforge")

        vcsVersion = "1.21.1-fabric"
    }
}

rootProject.name = "LitematicaTool"
