import org.gradle.api.services.BuildService
import org.gradle.api.services.BuildServiceParameters

// Serialises NeoForm's (NeoForge) Minecraft setup across nodes. Without this, building several
// NeoForge nodes at once starts a full Minecraft decompile per node in parallel and will bring the
// machine to its knees.
//
// Forge (ForgeGradle 7) does NOT need a task-level mutex here, and deliberately doesn't get one:
// its Minecraft/Forge artifact setup runs eagerly during Gradle's project *configuration* phase,
// not as a lazy execution-phase task, so by the time task execution starts, every Forge node's
// setup is already done and cannot race a NeoForge node's.
interface MinecraftSetupMutex : BuildService<BuildServiceParameters.None>

val mutex = gradle.sharedServices.registerIfAbsent("createMinecraftArtifactsMutex", MinecraftSetupMutex::class.java) {
    maxParallelUsages.set(1)
}

tasks.named { it == "createMinecraftArtifacts" }.configureEach {
    usesService(mutex)
}
