# IPM 0.1.24 source reconciliation and HUD foundation

This single draft reconciles the owner's existing 0.1.24 sources with the older remote repository and adds a bounded HUD presentation foundation. It is NOT the completed Digger/Cart/HUD integration.

## Provenance

- Owner source HEAD: b0861152e7acfb62e397ece51e90a3288b5909a5, with uncommitted changes.
- Transfer archive SHA-256: 0fe73ea8589a6d46b57dec2aa88f42f39e771232b9485a9e195a7c16913c7fb1. All 150 files and the five declared deletions verified. Original source unchanged.
- Remote baseline: d5ec9ad9bea31f950306755bc9368c56a381bbe1. Remote-only governance/reference documents preserved. Owner-local replacement of the host-specific duplicate AGENTS file excluded; pre-existing remote file preserved. Wrapper text line endings normalized only.
- This includes existing Garage/maintenance, MK bore-head profiles and recipes, formed-state guards and Survey Console containment. These are recovered owner changes, not newly completed gameplay.
- HUD original archive: dd7722d0ad3f1d87c478a61dd4ee4968e74e99a77d6d63505d78af1c80968c72. Two original PNGs only, 1515193 bytes; region map and provenance alongside them. No redesign or generated duplication.
- Cart v4.1 source verified separately: 1b671bc6b3f14ec2b71996c6f56f825a4f84f995e4c3bd106013d821bdc90bfd. No runtime Cart/model binder is claimed. Industrial Digger model candidate P14 is not GOV QA/migration lot P14.

## New code and limits

DiggerHudSnapshot and DiggerHudSession preserve unknown/stale/known-zero states and filter vehicle, driver, dimension and revision. Dismount/context changes clear presentation. They do not register an overlay, packet or command, allocate an entity, or simulate heat/fuel. A future authoritative adapter must establish real driver identity, freshness and connection/session boundaries; the current pure gate is not a security boundary.

## Build strategy

ModDevGradle moves from 2.0.30-beta to 2.0.148 using the supported disableRecompilation pipeline. Gradle remains 8.8, Java remains 21, NeoForge remains 21.1.164; gameplay dependency versions unchanged. Compilation and tests are NOT disabled. Minecraft decompiled debug sources are unavailable in this mode. The old pipeline exhausted cloud memory before mod compilation, so binary equivalence is not claimed.

Reproduce with a real Java 21 JDK: ./gradlew compileJava processResources test build. hudContractTest is dependency-free and included in check. The cloud used an existing Adoptium JDK21 compiler selected through an external init script; no machine-specific path is committed. 28 contract checks passed on the isolated migration copy; these are not GameTests. The ordinary Gradle test task produced no additional XML testcase reports.

## Outstanding gates

- Actual Industrial Digger entity/data owner, engine/heat and embedded tank states are absent. Do not use Garage tank contents or native Tunnel Digger as invented substitutes.
- P01 towing choice/qualification and P02 Habitat backend remain unresolved; no new traction physics, dimension, spatial budget or damage value is silently adopted.
- Client/driver/passenger/logout, dedicated server, two-client, GameTests and graphical scaling/overlay qualification remain required.
- No release, no merge, no CI success claimed unless separately observed on the final commit. No CI workflow added without authorization.

## Review gate: recovered save normalization

The recovered normalization risk is now corrected for load/save and formation: unrepresentable, malformed, invalid or excessive tank/inventory data is preserved verbatim under RecoveryQuarantine. The affected active container is empty; no partial activation or replay duplicates the archived contents. Existing quarantine survives repeated loads, including the same controller instance, and blocks formed conversion. Obsolete truncation/recovery helpers were removed.

Quarantine is intentionally opaque and non-operational: no automatic restoration, no invented conversion, and no new GUI/command to manufacture items. Qualified recovery procedures and physical controller destruction/drop handling are NOT covered by this correction. Preserve backups and do not treat this draft as a stable save-migration release.

Final branch build: compileJava/processResources/test/build passed (14s); hudContractTest 28 checks passed. Candidate JAR SHA-256: 9dbc4626bb6b29c88ddb076c678cf84ea14d1e0bff325272da99137ab1f7f705. Isolated migration JAR had a different archive hash; no deterministic byte-for-byte build claim is made. GameTest execution is tracked separately and must not be inferred from this build.

## Fresh runtime result for this recovered candidate

runGameTestServer completed successfully in 1m5s after downloading required official Minecraft assets through the existing cloud proxy. All 29 required GameTests passed. This covers the existing Garage/profile/safety harness, not Cart traction or the new HUD in-game. No historical count was reused. No interactive client or two-client qualification performed. The recovered save-normalization risk above remains despite these tests.

## NBT conservation correction — latest qualification

Independent review found no loss/duplication in the load-save/formation paths. Final compileJava/processResources/test/build/runGameTestServer completed successfully in 27s after removing obsolete destructive helpers. 28 HUD contract checks and all 31 required GameTests passed. Round-trip tests cover whole overcapacity tanks, oversized/negative inventory declarations, excess stack counts, duplicate slots, unresolved item IDs, unknown fields, malformed tag types, repeated same-instance loads, source immutability and no automatic reactivation. Original source snapshot remains untouched.

Final corrected JAR SHA-256: 6fb3caa8ddc11a2cac61793c05c41cddc439e7ce6360b7d397bedb38804bc237. Expected test diagnostic: unresolved-item fixture logs an invalid-item message. Offline Yggdrasil/update-check failures did not prevent GameTests. Cart traction and interactive HUD/client qualification remain NOT_RUN.
