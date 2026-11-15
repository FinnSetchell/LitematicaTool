plugins {
    // Forge before 1.20.5 runs on SRG-named Minecraft, so the jar has to be reobfuscated out of the
    // official names the source is written in. ForgeGradle 7 cannot reobfuscate; ModDevGradle's
    // legacy Forge plugin can, and wires it into the jar. Everything else matches
    // build.forge.gradle.kts.
    id("net.neoforged.moddev.legacyforge") version "2.0.147"
    id("minecraft-mutex")
}

fun prop(key: String): String = sc.properties.get<String>(key)

val modId = property("mod_id").toString()
val modName = property("mod_name").toString()
val modAuthor = property("mod_author").toString()
val requiredJava: JavaVersion = JavaVersion.toVersion(prop("mod.java"))
val mcBuild: String = prop("mod.mc_build")

version = property("mod_version").toString()
base.archivesName = "${property("archives_base_name")}-forge-$mcBuild"

sourceSets.main {
    java.exclude("**/fabric/**", "**/neoforge/**")
    resources.srcDir(rootProject.file("src/forge/resources"))
    // The recipe folder and format changed four times between 1.20 and 1.21.2.
    resources.srcDir(rootProject.file("src/main/recipe/${prop("mod.recipe")}"))
}

legacyForge {
    version = "$mcBuild-${prop("deps.forge")}"

    mods {
        register(modId) { sourceSet(sourceSets.main.get()) }
    }

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
            "forge_range" to prop("deps.forge_range"),
            "mc_compat" to prop("mod.mc_compat"),
            "java_version" to requiredJava.majorVersion,
        )
        props.forEach { (k, v) -> inputs.property(k, v) }
        filesMatching("META-INF/mods.toml") { expand(props) }
    }

    jar {
        from(rootProject.file("LICENSE")) { rename { "${it}_${modName}" } }
        manifest {
            attributes(
                "Specification-Title" to modName,
                "Specification-Vendor" to modAuthor,
                "Specification-Version" to version,
                "Implementation-Title" to "forge",
                "Implementation-Version" to version,
                "Implementation-Vendor" to modAuthor,
                "Built-On-Minecraft" to mcBuild,
            )
        }
    }

    named("createMinecraftArtifacts") { dependsOn("stonecutterGenerate") }
    withType<JavaCompile>().configureEach { dependsOn("stonecutterGenerate") }

    register<Copy>("buildAndCollect") {
        group = "build"
        description = "Builds the mod jar and copies it to build/libs/{mod version}/"
        // The reobfuscated jar, not `jar`: that one keeps official names and dies on a real server.
        val reobf = named("reobfJar")
        dependsOn(reobf)
        from(reobf.map { it.outputs.files })
        into(rootProject.layout.buildDirectory.dir("libs/$version"))
    }
}
