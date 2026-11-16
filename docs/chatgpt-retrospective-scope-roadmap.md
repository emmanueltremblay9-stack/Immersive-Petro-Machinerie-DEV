# ChatGPT Context Package: IPM Retrospective, Scope, And Roadmap

> **SUPERSEDED:** This retrospective is preserved as historical context. The
> Final Design Paper governs current work: the standalone Survey Console is
> removed, and Core Sampling belongs to the future Industrial Digger path.
> Instructions below that reactivate or require the Survey Console are not
> active project direction.

Generated: 2026-06-18

Use this as a compact briefing for another ChatGPT/Codex session. The repository
files remain the source of truth. Before editing, reopen `AGENTS.md`,
`gradle.properties`, `build.gradle`, `src/main/resources/META-INF/neoforge.mods.toml`,
and the relevant Java/docs files.

## Project Identity

- Project: Immersive Petro-Machinery.
- Mod id: `immersive_petro_machinery`.
- Loader/platform: NeoForge `1.21.1`.
- Java: `21`.
- Build system: Gradle wrapper, Groovy `build.gradle`, ModDevGradle.
- Current workspace root:
  `<owner-local-IPM-workspace>`.
- Main concept: an unofficial compatibility addon around the Immersive Machinery
  Tunnel Digger, Immersive Petroleum fluids/survey concepts, and Immersive
  Engineering multiblock/manual conventions.

## Hard Boundaries

- Do not copy code/assets from Immersive Petroleum, Immersive Engineering,
  Immersive Aircraft, Minecraft, NeoForge, or other third-party projects.
- Immersive Machinery material may be copied/adapted only under the GPLv3 gate:
  record the exact import in `docs/im-reuse-inventory.md`, preserve notices, and
  update `NOTICE` and `CREDITS`.
- Current copied/adapted Immersive Machinery material: none.
- Do not implement mixins unless explicitly approved.
- Do not modify the native Tunnel Digger GUI.
- Do not transfer fluids or items into the native Tunnel Digger.
- Do not re-enable Survey Console final output generation until approved.
- Do not implement final Core Sampling Scan activation until approved.
- Do not rewrite the whole mod.
- Do not implement custom rendering before gameplay foundations are validated.
- Phase 8 approval is narrow: IPM-owned MK1/MK2/MK3 Industrial Digger profile
  behavior selected by IPM bore heads. It does not authorize upstream mining
  code reuse, native GUI modification, mixins, or native Tunnel Digger transfer.

## Current Repository State Snapshot

- Git branch: `main`.
- Local branch is ahead of `origin/main` by 2 commits.
- Existing commits include:
  - `Initial IPM project import`.
  - `Document CurseForge private message reference`.
  - `Document Immersive Machinery permission gate`.
  - `Add GPL compliance structure for Immersive Machinery reuse`.
  - `Add Immersive Machinery audit baseline`.
- Working tree currently contains uncommitted implementation/doc changes around
  version `0.1.10`, Garage upgrade slots, MK bore heads, Phase 8 profile docs,
  GameTests, and UI/snapshot changes. Treat these as in-progress until built,
  tested, installed, and committed.
- Existing local install report says `0.1.10` was built and installed into Prism
  LAB with matching source/installed SHA-256 and one remaining IPM jar. Reverify
  this before relying on it in a new session.

## Retrospective: What Is Done

### Phase 1: Skeleton And Dependency Stack

- Gradle/NeoForge skeleton exists and builds against Java 21.
- Verified dependency stack is documented:
  - Immersive Engineering `1.21.1-12.4.2-194`.
  - Immersive Petroleum `4.4.1-37`.
  - Immersive Aircraft `1.2.2+1.21.1+neoforge`.
  - Immersive Machinery `0.2.0+1.21.1+neoforge`.
  - DualCodecs `0.1.2`.
- Java 21 startup/toolchain guidance is documented in `docs/build.md`.

### Phase 2A/2B: Initial Dock Blocks And Validation

- IPM block set exists: Docking Controller, Docking Frame, Locking Rail, Fuel
  Port, Lubricant Port, Output Port, Repair Bay, Upgrade Bay, Survey Console,
  and Docking Pipe/Cable.
- Initial compact docking-platform work has been superseded by the current
  Industrial Driller Garage layout.
- Data generation-style resource coverage exists manually: blockstates, models,
  loot tables, recipes, advancements, item models, textures, and language keys.

### Phase 3: Tunnel Digger Detection

- `TunnelDiggerDockingDetector` detects entities with id
  `immersive_machinery:tunnel_digger`.
- Detection uses a controller-relative 3D search box and converts between local
  and world coordinates by controller facing.
- It distinguishes no candidate, misaligned candidate, and aligned candidate.
- Alignment checks lane position, local Z range, and yaw compatibility.
- This detection does not control, patch, or modify the Tunnel Digger.

### Phase 4: Soft Lock/Unlock

- Docking Controller can soft-lock to an aligned Tunnel Digger UUID/name.
- Sneak-use toggles lock/unlock.
- If Garage validation fails, the controller clears soft lock.
- Soft lock state is persisted in controller block entity NBT.

### Phase 5: Maintenance GUI

- `DockingMaintenanceMenu`, `DockingMaintenanceSnapshot`, and client screen
  exist for the Garage maintenance surface.
- The GUI exposes status/diagnostic information: Garage validation, Tunnel
  Digger detection, soft lock, fuel/lubricant tanks, and upgrade slots.
- Current upgrade inventory has five 1-item slots:
  - Bore Head.
  - Core Sampling.
  - Fuel System.
  - Lubricant System.
  - Utility.
- Slot validation rejects mismatched items and has migration logic for legacy
  upgrade placement.

### Phase 6: Fuel And Lubricant Ports

- Docking Controller owns two 16,000 mB tanks: fuel and lubricant.
- Fuel Port and Lubricant Port expose fill-only NeoForge fluid capabilities.
- Current accepted fuel/lubricant filters:
  - fuel: Immersive Petroleum diesel, gasoline, or `neoforge:gasoline` tag.
  - lubricant: Immersive Petroleum lubricant or `neoforge:lubricant` tag.
- These are Garage tanks only. They do not transfer into the Tunnel Digger.

### Phase 7: Survey Console And Containment

- Survey service code exists for future IE/IP survey integration planning.
- Active Survey Console interaction is deliberately contained.
- Current behavior validates the Garage and then reports that Core Sampling
  output is under review.
- It does not generate IE core sample items, IP survey result items, diesel
  drain, or final scan targeting.

### Garage Refactor And Redesign

- Active structure is the Industrial Driller Garage, not the old compact dock.
- Current validated contract:
  - total size `11x13x10`;
  - controller origin `(0,0,0)`;
  - local bounds `x=-5..5`, `y=-2..7`, `z=-12..0`;
  - front is negative Z;
  - lower hempcrete foundation at `y=-2`;
  - raised service floor and ramp;
  - open hollow center bay;
  - two rear `3x3x3` tanks;
  - side support pillars and top armature.
- Validation uses a required block pattern plus hollow bay clearance checks.
- Immersive Engineering manual/projector integration is present for preview and
  creative real-block placement.
- Engineer's Hammer path is validation-only in normal mode.

### Formed Multiblock Safety Spike

- Experimental formed shell creation is disabled by default.
- Test-only gate:
  `immersive_petro_machinery.enableFormedShellCreation=true`.
- Formation is blocked if controller protected state exists:
  - locked Tunnel Digger;
  - fuel tank contents;
  - lubricant tank contents;
  - any Garage upgrade inventory item.
- This keeps real-block controller state authoritative until formed-state
  ownership, capability routing, and GUI/fluid/survey migration are designed.

### License And Compliance

- Repository now has GPL-3.0-only compliance structure:
  `LICENSE`, `COPYING`, `NOTICE`, and `CREDITS`.
- `docs/external-references.md` records the private CurseForge permission
  reference and a public-safe summary.
- `docs/im-reuse-inventory.md` records that no Immersive Machinery material has
  been copied or adapted yet.
- `docs/im-audit-plan.md` lists Immersive Machinery audit candidates, with
  explicit exclusions for Java classes, mining behavior, GUI, mixins, native
  control code, fluid/item transfer, and private message text.

### Phase 8 Profile Slice

- Approved slice: IPM-owned MK mining profile behavior selected by bore heads.
- New items exist in the working tree:
  - `bore_head_mk1`;
  - `bore_head_mk2`;
  - `bore_head_mk3`.
- Profile contract:
  - MK1: `5x5`, 25 blocks/layer, 80 ticks, 250 mB fuel, 25 mB lubricant.
  - MK2: `7x7`, 49 blocks/layer, 110 ticks, 450 mB fuel, 45 mB lubricant.
  - MK3: `9x9`, 81 blocks/layer, 150 ticks, 700 mB fuel, 70 mB lubricant.
- Current implementation calculates deterministic target positions for one
  future mining layer. It does not yet break blocks or spawn/replace an
  Industrial Digger entity.

### Diagnostics

- Dev-only GameTests exist for:
  - formed shell disabled in normal runtime;
  - protected state blocking experimental formation;
  - formed diagnostics NBT reset;
  - contained Survey Console output;
  - Garage upgrade inventory persistence and slot migration;
  - stale soft-lock name cleanup;
  - fluid tank persistence when IP fluids are available;
  - MK mining profile sizes and target uniqueness;
  - bore-head slot/profile selection;
  - slot mismatch rejection;
  - maintenance snapshot preserving locked digger display.

## What Is Not Done

- Manual runtime validation of the redesigned `11x13x10` Garage is still marked
  pending.
- No final Core Sampling Scan activation.
- No real mining loop.
- No block breaking by Industrial Digger profiles.
- No fuel/lubricant consumption by mining runtime.
- No item or fluid transfer into native Tunnel Digger.
- No native Tunnel Digger GUI modification.
- No mixins.
- No custom rendering.
- No copied/adapted Immersive Machinery assets or code.
- Formed multiblock runtime is not production-ready; normal gameplay remains
  real-block controller based.
- Output Port, Repair Bay, Upgrade Bay system effects are mostly reserved.

## Detailed Roadmap

### 0. Stabilize Current Working Tree

Goal: prove the current `0.1.10` working tree before building new features.

- Review all dirty files with `git diff`.
- Confirm version sync between `gradle.properties` and `neoforge.mods.toml`.
- Run `.\gradlew.bat clean build`.
- Run `.\gradlew.bat runGameTestServer --no-daemon --console=plain`.
- Inspect the built jar for changed classes/resources:
  - `DockingControllerBlockEntity.class`;
  - `DockingMaintenanceMenu.class`;
  - `DockingMaintenanceSnapshot.class`;
  - `IndustrialDiggerMiningProfile.class`;
  - bore head recipes/models/tags;
  - `META-INF/neoforge.mods.toml`.
- Install the verified production jar into the Prism LAB mods directory and
  hash-check source/installed jars.
- Commit only after build/test/install evidence is recorded.

### 1. Manual Runtime Validation In Prism LAB

Goal: confirm the redesigned Garage behaves in a real client/server runtime.

- Launch Prism LAB with the verified jar and dependency stack.
- Build or projector-place the `11x13x10` Garage.
- Validate all four horizontal controller facings.
- Confirm validation failure messages report useful local/world coordinates.
- Confirm hollow bay clearance is enforced.
- Drive/place the Tunnel Digger into the ramp/lane and test detection.
- Test aligned, misaligned, and absent Tunnel Digger states.
- Test sneak-use soft lock and unlock.
- Open Maintenance GUI and verify status, fluid readouts, upgrade slots, and
  profile text.
- Fill Fuel Port and Lubricant Port with allowed and disallowed fluids.
- Confirm no native Tunnel Digger transfer occurs.
- Confirm Survey Console shows containment message and creates no output.

### 2. Expand Automated Diagnostics

Goal: cover the risky state and contract edges before deeper runtime work.

- Add validator orientation tests for all facings.
- Add structure-entry count and clearance regression tests.
- Add fuel/lubricant port capability tests for valid Garage vs invalid Garage.
- Add menu quick-move tests for bore heads, core sampling upgrade, and reserved
  slots.
- Add NBT backward-compatibility tests for older 1-slot upgrade data.
- Add mining target-position tests for all cardinal directions.
- Add tests that vertical mining direction is rejected.

### 3. Decide Formed Multiblock Ownership

Goal: avoid data loss before enabling any real formed shell.

- Choose one architecture:
  - keep real-block controller authoritative permanently; or
  - migrate state to a formed master block entity.
- If migrating, explicitly design:
  - controller state handoff;
  - soft-lock ownership;
  - fuel/lubricant tank ownership;
  - upgrade inventory ownership;
  - capability routing from ports;
  - GUI opening location;
  - disassembly restore behavior;
  - invalidation/revalidation behavior.
- Keep formed shell creation disabled until this is proven.

### 4. Phase 8 Runtime Mining Profile Integration

Goal: turn the current profile contract into IPM-owned runtime behavior without
native Tunnel Digger patching.

- Define the Industrial Digger runtime owner separate from native Tunnel Digger.
- Decide whether the Garage spawns an IPM Industrial Digger, upgrades a locked
  Tunnel Digger conceptually, or stages a separate runtime controller.
- Implement a dry-run planner first:
  - selected profile;
  - bore origin;
  - target layer positions;
  - fuel/lubricant requirements;
  - output estimate.
- Add safety checks:
  - Garage valid;
  - Tunnel Digger locked/aligned if required;
  - bore head installed;
  - enough fuel/lubricant;
  - output capacity path decided.
- Only then implement block-breaking behavior, still without mixins unless
  explicitly approved.

### 5. Core Sampling And Survey Flow

Goal: re-enable Survey Console output only through the Garage upgrade path.

- Require valid Garage.
- Require Core Sampling Upgrade installed in the correct slot.
- Require locked/aligned Tunnel Digger if that remains part of the design.
- Decide fuel/lubricant cost.
- Produce native IE core sample and IP-compatible reservoir info only for the
  current chunk.
- Add GameTests for "no upgrade means no output" and "valid upgraded flow
  produces expected result".

### 6. Maintenance Systems

Goal: give the reserved Garage blocks real, scoped behavior.

- Repair Bay:
  - define repairable state and costs;
  - avoid touching native Tunnel Digger internals unless approved.
- Upgrade Bay:
  - define install/remove rules for IPM-owned upgrades;
  - preserve inventory migration and slot validation.
- Output Port:
  - decide item output buffer ownership;
  - add sided capability if needed;
  - keep it separate from native Tunnel Digger inventory.

### 7. Assets, Manual, And UX Polish

Goal: improve player readability without introducing legal or scope risk.

- Keep placeholder/simple IPM-owned assets until GPL-tracked imports are needed.
- Update manual after each validated gameplay change.
- Add clear status strings for every blocked/reserved behavior.
- If using Immersive Machinery visual references, record exact provenance in the
  reuse inventory, `NOTICE`, and `CREDITS` in the same change.

### 8. Release And Install Workflow

Goal: make every test build reproducible.

- Add or maintain a deterministic Windows install script if absent.
- Version-bump every user-visible test build.
- Build with Gradle wrapper and Java 21.
- Select only the production jar from `build/libs`.
- Remove only same-mod old jars from Prism LAB.
- Copy exactly one verified jar.
- Record source/installed size and SHA-256.
- Inspect embedded metadata for mod id/version/dependencies.
- Confirm exactly one IPM jar remains in the target mods folder.

## Recommended Next Prompt For ChatGPT

Continue from the current IPM repository. First, read `AGENTS.md`, then inspect
`git status`, `gradle.properties`, `build.gradle`, `META-INF/neoforge.mods.toml`,
`docs/docking-platform-layout.md`, `docs/industrial-digger-mk-mining-profiles.md`,
`DockingControllerBlockEntity.java`, `DockingMaintenanceMenu.java`,
`DockingMaintenanceSnapshot.java`, `IndustrialDiggerMiningProfile.java`, and
`IPMGarageSafetyGameTests.java`.

Do not start by adding new gameplay. First stabilize the current dirty working
tree: verify version sync, run build and GameTests, inspect the jar, install the
verified production jar into Prism LAB if the task is runtime-facing, and report
exact evidence. Preserve all AGENTS.md boundaries: no mixins, no native Tunnel
Digger GUI modification, no native item/fluid transfer, no Survey Console final
output, no copied upstream material unless the GPL inventory and attribution
work is done in the same change.
