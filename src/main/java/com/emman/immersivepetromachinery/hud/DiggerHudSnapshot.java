package com.emman.immersivepetromachinery.hud;

import java.util.Objects;
import java.util.OptionalDouble;
import java.util.UUID;

/**
 * Read-only presentation data for the Industrial Digger only.
 * This is not a packet, an authority check, or a source of simulated fuel/heat.
 */
public record DiggerHudSnapshot(UUID vehicleId, UUID driverId, String dimension,
        long revision, Gauge heat, Gauge fuel, Gauge lubricant, Power power) {
    public DiggerHudSnapshot {
        Objects.requireNonNull(vehicleId);
        Objects.requireNonNull(driverId);
        Objects.requireNonNull(dimension);
        Objects.requireNonNull(heat);
        Objects.requireNonNull(fuel);
        Objects.requireNonNull(lubricant);
        Objects.requireNonNull(power);
        if (dimension.isBlank() || revision < 0) {
            throw new IllegalArgumentException("Invalid snapshot identity or revision");
        }
    }

    public enum Power { UNKNOWN, STALE, OFF, ON, FAULT, DISABLED }
    public enum Availability { UNKNOWN, STALE, KNOWN }

    /** No fallback zero: absence of a known reading must remain visible as absence. */
    public record Gauge(Availability availability, double amount, double capacity,
                        boolean confirmedFault) {
        public Gauge {
            Objects.requireNonNull(availability);
            if (availability == Availability.KNOWN) {
                if (!Double.isFinite(amount) || !Double.isFinite(capacity)
                        || capacity <= 0 || amount < 0 || amount > capacity) {
                    throw new IllegalArgumentException("Invalid authoritative gauge reading");
                }
            } else if (amount != 0 || capacity != 0 || confirmedFault) {
                throw new IllegalArgumentException("Unavailable reading cannot carry a value or fault");
            }
        }

        public static Gauge unknown() {
            return new Gauge(Availability.UNKNOWN, 0, 0, false);
        }

        public static Gauge stale() {
            return new Gauge(Availability.STALE, 0, 0, false);
        }

        public OptionalDouble fraction() {
            return availability == Availability.KNOWN
                    ? OptionalDouble.of(amount / capacity) : OptionalDouble.empty();
        }
    }
}
