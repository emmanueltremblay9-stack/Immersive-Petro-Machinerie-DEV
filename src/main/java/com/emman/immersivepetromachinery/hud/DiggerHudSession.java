package com.emman.immersivepetromachinery.hud;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * Client-independent lifecycle gate, deliberately not registered with Minecraft yet.
 * A future adapter must derive the context from the actual Industrial Digger and its
 * controlling passenger. Neither context nor snapshot may come from trusted C2S data.
 * Freshness is supplied by the authoritative adapter; no expiry policy is invented here.
 */
public final class DiggerHudSession {
    private Context context;
    private DiggerHudSnapshot snapshot;

    public record Context(UUID vehicleId, UUID driverId, String dimension) {
        public Context {
            Objects.requireNonNull(vehicleId);
            Objects.requireNonNull(driverId);
            Objects.requireNonNull(dimension);
            if (dimension.isBlank()) throw new IllegalArgumentException("Missing dimension");
        }
    }

    /** Call with null on dismount, passenger role, death, logout, or non-Digger vehicle. */
    public void updateContext(Context actualDriverContext) {
        if (!Objects.equals(context, actualDriverContext)) {
            snapshot = null;
        }
        context = actualDriverContext;
    }

    /** Reject unrelated/late snapshots without discarding the current vehicle's data. */
    public boolean accept(DiggerHudSnapshot incoming) {
        Objects.requireNonNull(incoming);
        if (context == null || !context.vehicleId().equals(incoming.vehicleId())
                || !context.driverId().equals(incoming.driverId())
                || !context.dimension().equals(incoming.dimension())
                || (snapshot != null && incoming.revision() <= snapshot.revision())) {
            return false;
        }
        snapshot = incoming;
        return true;
    }

    public Optional<DiggerHudSnapshot> visibleSnapshot(boolean guiVisible,
                                                        boolean authoritativeDataFresh) {
        if (context == null || !guiVisible || !authoritativeDataFresh) {
            return Optional.empty();
        }
        return Optional.ofNullable(snapshot);
    }

    /** Explicitly clear retained data on world/session teardown, even for the same UUIDs. */
    public void clear() {
        snapshot = null;
        context = null;
    }
}
