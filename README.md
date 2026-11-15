# LitematicaTool

A simple tool designed to work with Litematica for enhanced schematic management.

## Features

- **The Stick**: a dedicated tool for Litematica, so your normal stick stays a normal stick
- **Set up for you**: the mod points Litematica at the stick itself, with nothing to configure
- **Stack size**: limited to 1
- **Multi-language**: English, Chinese, German, Spanish and French

## How to Use

1. Craft **The Stick**: two sticks placed diagonally
2. Use it as you would Litematica's tool item

That is all. The mod sets Litematica's tool item to the stick and switches the tool item on the
first time you load a world, so it works straight out of the box, in a modpack or on its own.

It will not overrule you: each of those two settings is only set while it is still at Litematica's
own default, so if you have deliberately chosen a different tool item, yours is kept.

### Setting it by hand

Only needed if you have previously changed either setting yourself and now want the stick:

1. Press `M` in-game, then click **Configuration Menu** on the **Generic** tab
2. Search for `tool`
3. Set **toolItem** to `litematicatool:the_stick`
4. Turn **toolItemEnabled** on - this is off by default, and the stick does nothing without it
5. Save and exit

## Supported versions

Minecraft 1.20 through 26.3, on Fabric, Forge and NeoForge.

A few gaps are not ours but the loaders': NeoForge does not exist before 1.20.6, and Forge has no
build for 1.20.5, none for 1.21.2 (the 1.21.2 jar is built against 1.21.3, which it also runs on)
and none before 26.1.1.

## Downloads

- [CurseForge](https://www.curseforge.com/minecraft/mc-mods/litematica-tool)
- [Modrinth](https://modrinth.com/mod/litematicatool)

## Issues & Support

- [GitHub Issues](https://github.com/FinnSetchell/LitematicaTool/issues)
- [Discord](https://moogsmods.com/discord?r=readme-litematica)

---

**Note**: this mod needs Litematica to be useful. The stick is a replacement for the default stick
Litematica uses as its tool item, not a Litematica substitute.
