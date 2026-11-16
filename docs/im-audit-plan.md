# Immersive Machinery Audit Plan

This document records candidate Immersive Machinery material for later review.
It is audit-only and does not import, copy, adapt, or redistribute Immersive
Machinery content.

## Current Scope

- Copied Or Adapted Material

  None.

- Scope chosen: audit only.
- Current IPM phase boundary: MK1/MK2/MK3 IPM-owned mining profile behavior is
  approved; upstream Immersive Machinery mining behavior remains audit-excluded.
- Future imports must update `docs/im-reuse-inventory.md`, `NOTICE`, and
  `CREDITS` in the same change as the copied or adapted material.
- Runtime references and dependency declarations remain references only.

## Verified Immersive Machinery Artifact

- Local artifact: `immersive_machinery-0.2.0+1.21.1+neoforge.jar`
- Local cache path: `<GRADLE_USER_HOME>/caches\modules-2\files-2.1\net.conczin\immersive_machinery\0.2.0+1.21.1+neoforge\479c940b7e869dc4637a7f1d291ef310e8749c7f\immersive_machinery-0.2.0+1.21.1+neoforge.jar`
- SHA-256: `470DFA22F21F9FE4E78F0090581A5AF5C1DBADA9C1159BC1AF59C2F633FEC1FD`
- Published metadata: `META-INF/neoforge.mods.toml` declares
  `license = "GPL-3.0-only"`.
- Upstream repository branch:
  [Luke100000/ImmersiveMachinery `1.21.1`](https://github.com/Luke100000/ImmersiveMachinery/tree/1.21.1)

## Candidate Tunnel Digger Audit Groups

These are candidates for later review only. They are not approved imports until
an implementation task records the exact item in `docs/im-reuse-inventory.md`.

| Group | Artifact entry | Review intent |
| --- | --- | --- |
| Item model | `assets/immersive_machinery/models/item/tunnel_digger.json` | Check whether an IPM-owned visual reference or compatibility icon is needed. |
| Blockbench model | `assets/immersive_machinery/objects/tunnel_digger.bbmodel` | Inspect proportions for garage clearances or manual visual planning. |
| Entity texture | `assets/immersive_machinery/textures/entity/tunnel_digger.png` | Evaluate whether any attributed visual reference is needed for IPM docs or preview work. |
| Item texture | `assets/immersive_machinery/textures/item/tunnel_digger.png` | Evaluate whether an IPM-owned compatibility visual is needed. |
| Sounds | `assets/immersive_machinery/sounds/tunnel_digger.ogg`; `assets/immersive_machinery/sounds/tunnel_digger_drilling.ogg` | Audit only; no reuse planned for the current garage validation phase. |
| Aircraft data | `data/immersive_machinery/aircraft/tunnel_digger.json` | Reference dimensions/configuration only if needed for non-mining garage detection validation. |
| Recipe data | `data/immersive_machinery/recipe/tunnel_digger.json` | Reference crafting relationship only if future docs require it. |

## Explicit Exclusions

Do not import, copy, adapt, decompile, or reimplement from these areas in the
next pass:

- Java classes or decompiled Java output.
- Tunnel Digger GUI or screen code.
- Controls/network code.
- Upstream Immersive Machinery mining behavior, digging size, speed, or
  block-breaking logic.
- Mixins.
- Native Tunnel Digger GUI modification.
- Fluid or item transfer into the Tunnel Digger.
- Phase 8 work that imports or adapts upstream Immersive Machinery mining code.
- Private CurseForge message text.
- Any Immersive Petroleum, Immersive Engineering, Immersive Aircraft,
  Minecraft, NeoForge, or other non-IM material.

## Recommended First Future Import

If a later task explicitly approves an import, start with a visual/reference
asset group, not behavior:

- Preferred first candidate: Tunnel Digger visual reference for garage/manual
  planning.
- Required guardrail: create a small IPM-owned deliverable and record exact
  attribution instead of silently copying upstream assets.
- Required validation: build, inspect the jar for legal files, confirm the audit
  inventory entry, and keep Phase 8/mining/mixin exclusions intact.
