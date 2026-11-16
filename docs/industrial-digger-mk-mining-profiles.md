# Industrial Digger MK Mining Profiles

This document records the approved Phase 8 mining-behavior profile slice for
Industrial Digger MK progression.

## Approval

- User approval: 2026-06-18
- Approved scope: change Industrial Digger mining behavior per MK model.
- Still blocked unless separately approved: mixins, native Tunnel Digger GUI
  modification, item or fluid transfer into the native Tunnel Digger, and reuse
  from non-Immersive Machinery projects.

## Current Implementation Slice

The first implementation is an IPM-owned behavior contract selected through the
Industrial Driller Garage bore-head slot:

| Model | Bore head item | Mining face | Blocks per layer | Cycle ticks | Fuel per layer | Lubricant per layer |
| --- | --- | ---: | ---: | ---: | ---: | ---: |
| MK1 | `immersive_petro_machinery:bore_head_mk1` | 5x5 | 25 | 80 | 250 mB | 25 mB |
| MK2 | `immersive_petro_machinery:bore_head_mk2` | 7x7 | 49 | 110 | 450 mB | 45 mB |
| MK3 | `immersive_petro_machinery:bore_head_mk3` | 9x9 | 81 | 150 | 700 mB | 70 mB |

The profile code defines deterministic target positions for a single mining
layer in front of the future Industrial Digger bore origin. This does not yet
spawn or replace an Industrial Digger entity and does not patch the native
Immersive Machinery Tunnel Digger.

## Visual Direction

The Industrial Digger visual lineage should read as:

- MK1: upgraded Tunnel Digger scale and silhouette.
- MK2: heavier intermediate body and reinforced bore assembly.
- MK3: largest Industrial Digger body with the 9x9 heavy bore role.

Any copied or adapted Immersive Machinery visual material must be recorded in
`docs/im-reuse-inventory.md`, `NOTICE`, and `CREDITS` in the same change.

