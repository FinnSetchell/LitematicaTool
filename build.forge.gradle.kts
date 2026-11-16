plugins {
    id("net.minecraftforge.gradle") version "[7.0.29,8.0)"
}

fun prop(key: String): String = sc.properties.get<String>(key)

val modId = property("mod_id").toString()
val modName = property("mod_name").toString()
val modAuthor = property("mod_author").toString()
val requiredJava: JavaVersion = JavaVersion.toVersion(prop("mod.java"))
// Where Forge skipped a release in this node's range, it compiles against the next one.
val mcBuild: String = prop("mod.mc_build")

version = property("mod_version").toString()
base.archivesName = "${property("archives_base_name")}-forge-$mcBuild"

sourceSets.main {
    java.exclude("**/fabric/**", "**/neoforge/**")
    resources.srcDir(rootProject.file("src/forge/resources"))
    // The recipe folder and format changed four times between 1.20 and 1.21.2.
    resources.srcDir(rootProject.file("src/main/recipe/${prop("mod.recipe")}"))
}

// Forge's dev runs treat every classpath entry as a mod file of its own, and Gradle puts classes
// and resources in two. Forge then finds a mods.toml naming this mod with no @Mod class beside it
// and refuses to start: "constructed 0 mods, but had 1 mods specified". Packaged jars are fine,
// since there both live in the one jar, so this only ever bites runClient and runServer. Sending
// both to a single directory is what makes a Forge dev run possible at all.
sourceSets.configureEach {
    val merged = layout.buildDirectory.dir("sourceSets/$name")
    output.setResourcesDir(merged)
    java.destinationDirectory.set(merged)
}

minecraft {
    mappings("official", mcBuild)

    runs {
        // Per-node game directory, so worlds are never opened by a different Minecraft version.
        configureEach {
            workingDir.set(rootProject.file("run/${project.name}"))
        }
        register("client")
        register("server") { args("--nogui") }
    }
}

repositories {
    minecraft.mavenizer(this)
    maven(fg.forgeMaven)
    maven(fg.minecraftLibsMaven)
    mavenCentral()
}

dependencies {
    implementation(minecraft.dependency("net.minecraftforge:forge:$mcBuild-${prop("deps.forge")}"))
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

    // Forge's Minecraft setup must not run before Stonecutter has written the processed sources.
    withType<JavaCompile>().configureEach { dependsOn("stonecutterGenerate") }

    register<Copy>("buildAndCollect") {
        group = "build"
        description = "Builds the mod jar and copies it to build/libs/{mod version}/"
        from(jar.flatMap { it.archiveFile })
        into(rootProject.layout.buildDirectory.dir("libs/$version"))
    }
}
