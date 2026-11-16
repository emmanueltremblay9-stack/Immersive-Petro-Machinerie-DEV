# Immersive Machinery Reuse Inventory

This document is the audit trail for any Immersive Machinery material copied or
adapted into Immersive Petro-Machinery.

## Current Gate Status

- Project license: GPL-3.0-only.
- Full GPLv3 license text: `COPYING`.
- Project-specific license statement: `LICENSE`.
- Attribution and notice files: `NOTICE` and `CREDITS`.
- Permission context: `docs/external-references.md`.
- Current copied/adapted Immersive Machinery material: none.

The compliance structure is now present for Immersive Machinery GPLv3-compatible
reuse, but this file must be updated before or in the same commit as any copied
or adapted Immersive Machinery material.

## Runtime References Only

These entries are references or dependencies, not copied Immersive Machinery
material:

| Area | Reference | Repo location |
| --- | --- | --- |
| Gradle dependency | `net.conczin:immersive_machinery:0.2.0+1.21.1+neoforge` | `build.gradle`, `gradle.properties` |
| NeoForge dependency metadata | `immersive_machinery` required mod dependency | `src/main/resources/META-INF/neoforge.mods.toml` |
| Tunnel Digger detection | `immersive_machinery:tunnel_digger` entity id | `src/main/java/com/emman/immersivepetromachinery/docking/TunnelDiggerDockingDetector.java` |
| Player-facing documentation | Immersive Machinery Tunnel Digger compatibility text | `docs/`, manual resources |

## Copied Or Adapted Material

None.

When adding an entry, use this format:

| Status | Upstream material | Upstream license/notice | IPM destination | Modification summary | Attribution location |
| --- | --- | --- | --- | --- | --- |
| Planned/Imported/Removed | Path, asset id, or exact source reference | GPLv3 and upstream notices preserved? | Destination path | What changed and why | `NOTICE`, `CREDITS`, file header, or doc section |

## Import Checklist

Before importing Immersive Machinery material:

1. Confirm the material is from Immersive Machinery, not another dependency.
2. Confirm the material is compatible with GPL-3.0-only.
3. Record the planned import in this file.
4. Preserve upstream notices and copyright headers where present.
5. Add attribution to `NOTICE` and `CREDITS`.
6. Mark modified imported material with a date or commit context.
7. Keep the import within the current project phase boundaries in `AGENTS.md`.
8. Run the normal build and any relevant validation checks.

Do not use this process for Immersive Petroleum, Immersive Engineering,
Immersive Aircraft, Minecraft, NeoForge, or other third-party projects.
