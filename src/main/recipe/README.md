# Recipe variants

One folder per shape the crafting recipe has to take. Each node picks its folder with `mod.recipe`
in `stonecutter.properties.toml`, and the four `build.<loader>.gradle.kts` scripts add it as a
resource directory, so exactly one of these files ever reaches a jar.

The recipe itself never changes: two sticks placed diagonally make one `litematicatool:the_stick`.
Only the folder name and the JSON shape change, and they changed four times between 1.20 and 1.21.2:

| folder   | used from | data folder | result key | ingredient                   |
|----------|-----------|-------------|------------|------------------------------|
| `1.20`   | 1.20      | `recipes/`  | `item`     | `{"item": "minecraft:stick"}` |
| `1.20.5` | 1.20.5    | `recipes/`  | `id`       | `{"item": "minecraft:stick"}` |
| `1.21`   | 1.21      | `recipe/`   | `id`       | `{"item": "minecraft:stick"}` |
| `1.21.2` | 1.21.2    | `recipe/`   | `id`       | `"minecraft:stick"`           |

Each boundary was read off the matching vanilla server jar's own `data/minecraft/recipe[s]/stick.json`,
not from documentation.

**Why this is split out rather than kept as one file.** A recipe in the wrong folder is not an error:
the game looks only in the folder its version expects, finds nothing, and logs nothing. A single
hand-maintained file drifted out of step with the branch it lived on, and 8 of the 10 published
branches shipped a recipe that silently never loaded. Picking the folder per node makes that drift
impossible rather than merely unlikely.

When adding a Minecraft version, set `mod.recipe` to the newest folder whose "used from" is at or
below it. There is no need for a new folder unless Mojang changes the shape again; if they do, check
the vanilla jar rather than guessing, and give the folder the version the change landed in.
