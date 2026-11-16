# Changelog

---

## [2.1.0] - 2026-11-16

### Added
- Forge support
- Support for Minecraft 1.20 through 26.3 on Fabric, Forge and NeoForge
- Litematica now uses the stick automatically, with nothing to set up
  - A tool item you picked yourself is left as it is

### Fixed
- The stick can now be crafted on every supported version
  - The recipe had quietly failed to load on most versions, so it could not be made at all

## [2.0.1] - 2026-09-20

### Added
- Support for Minecraft 26.3

### Fixed
- The Stick can now be crafted (two sticks placed diagonally)

## [2.0.0] - 2026-07-04

Initial port to the moog publish system. No functional changes; adds Discord-gated release workflow, aligns gradle properties with the standard shape, restructures publishMods blocks.
