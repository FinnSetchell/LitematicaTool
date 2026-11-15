plugins {
    id("net.neoforged.moddev") version "2.0.147"
    id("minecraft-mutex")
}

fun prop(key: String): String = sc.properties.get<String>(key)

val modId = property("mod_id").toString()
val modName = property("mod_name").toString()
val modAuthor = property("mod_author").toString()
val requiredJava: JavaVersion = JavaVersion.toVersion(prop("mod.java"))
val mcBuild: String = prop("mod.mc_build")

version = property("mod_version").toString()
base.archivesName = "${property("archives_base_name")}-neoforge-$mcBuild"

sourceSets.main {
    java.exclude("**/fabric/**", "**/forge/**")
    resources.srcDir(rootProject.file("src/neoforge/resources"))
    // The recipe folder and format changed four times between 1.20 and 1.21.2.
    resources.srcDir(rootProject.file("src/main/recipe/${prop("mod.recipe")}"))
}

neoForge {
    version = prop("deps.neoforge")

    runs {
        // Per-node game directory, so worlds are never opened by a different Minecraft version.
        register("client") {
            client()
            gameDirectory = rootProject.file("run/${project.name}")
        }
        register("server") {
            server()
            gameDirectory = rootProject.file("run/${project.name}")
        }
    }

    mods {
        register(modId) {
            sourceSet(sourceSets.main.get())
        }
    }
}

java {
    withSourcesJar()
    targetCompatibility = requiredJava
    sourceCompatibility = requiredJava
    toolchain { languageVersion = JavaLanguageVersion.of(requiredJava.majorVersion) }
}

tasks {
    processResources {
        val props = mapOf(
            "version" to version.toString(),
            "mod_id" to modId,
            "mod_name" to modName,
            "mod_author" to modAuthor,
            "description" to project.property("description").toString(),
            "license" to project.property("license").toString(),
            "credits" to project.property("credits").toString(),
            "neoforge_range" to prop("deps.neoforge_range"),
            "neoforge_min" to "[${prop("deps.neoforge_min")},)",
            "mc_compat" to prop("mod.mc_compat"),
            // 26.3 renamed the mods.toml logo key to iconFile.
            "neo_icon_key" to prop("mod.neo_icon_key"),
            "java_version" to requiredJava.majorVersion,
        )
        props.forEach { (k, v) -> inputs.property(k, v) }
        filesMatching("META-INF/neoforge.mods.toml") { expand(props) }
    }

    jar {
        from(rootProject.file("LICENSE")) { rename { "${it}_${modName}" } }
        manifest {
            attributes(
                "Specification-Title" to modName,
                "Specification-Vendor" to modAuthor,
                "Specification-Version" to version,
                "Implementation-Title" to "neoforge",
                "Implementation-Version" to version,
                "Implementation-Vendor" to modAuthor,
                "Built-On-Minecraft" to mcBuild,
            )
        }
    }

    // NeoForge's Minecraft artifacts must not be created before Stonecutter has written the sources.
    named("createMinecraftArtifacts") { dependsOn("stonecutterGenerate") }
    withType<JavaCompile>().configureEach { dependsOn("stonecutterGenerate") }

    register<Copy>("buildAndCollect") {
        group = "build"
        description = "Builds the mod jar and copies it to build/libs/{mod version}/"
        from(jar.flatMap { it.archiveFile })
        into(rootProject.layout.buildDirectory.dir("libs/$version"))
    }
}
