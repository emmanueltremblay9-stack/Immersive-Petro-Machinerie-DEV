package com.emman.immersivepetromachinery.hud;

import java.util.UUID;

/** Dependency-free contract checks. Not a GameTest or proof of in-game integration. */
public final class DiggerHudContractTest {
    private static int checks;
    private static void check(boolean value) {
        checks++;
        if (!value) throw new AssertionError("check " + checks);
    }
    private static void rejects(Runnable action) {
        try { action.run(); } catch (IllegalArgumentException expected) { checks++; return; }
        throw new AssertionError("expected invalid reading");
    }
    private static DiggerHudSnapshot sample(UUID vehicle, UUID driver, String world, long revision) {
        var unknown = DiggerHudSnapshot.Gauge.unknown();
        return new DiggerHudSnapshot(vehicle, driver, world, revision,
                unknown, unknown, unknown, DiggerHudSnapshot.Power.UNKNOWN);
    }
    public static void main(String[] args) {
        var vehicle = UUID.randomUUID();
        var driver = UUID.randomUUID();
        var other = UUID.randomUUID();
        var context = new DiggerHudSession.Context(vehicle, driver, "minecraft:overworld");
        var s = new DiggerHudSession();
        var first = sample(vehicle, driver, context.dimension(), 1);
        check(!s.accept(first));
        s.updateContext(context);
        check(s.visibleSnapshot(true, true).isEmpty());
        check(s.accept(first));
        check(s.visibleSnapshot(true, true).orElseThrow().equals(first));
        check(s.visibleSnapshot(false, true).isEmpty());
        check(s.visibleSnapshot(true, false).isEmpty());
        check(!s.accept(first));
        check(!s.accept(sample(vehicle, driver, context.dimension(), 0)));
        check(!s.accept(sample(other, driver, context.dimension(), 2)));
        check(!s.accept(sample(vehicle, other, context.dimension(), 2)));
        check(!s.accept(sample(vehicle, driver, "minecraft:the_nether", 2)));
        check(s.visibleSnapshot(true, true).orElseThrow().equals(first));
        s.updateContext(new DiggerHudSession.Context(other, driver, context.dimension()));
        check(s.visibleSnapshot(true, true).isEmpty());
        check(!s.accept(first));
        s.updateContext(context);
        check(s.accept(first));
        s.updateContext(null);
        check(s.visibleSnapshot(true, true).isEmpty());
        s.updateContext(context);
        check(s.visibleSnapshot(true, true).isEmpty());
        check(s.accept(first));
        s.clear();
        check(!s.accept(first));
        check(DiggerHudSnapshot.Gauge.unknown().fraction().isEmpty());
        check(DiggerHudSnapshot.Gauge.stale().fraction().isEmpty());
        var known = DiggerHudSnapshot.Availability.KNOWN;
        check(new DiggerHudSnapshot.Gauge(known, 0, 100, false).fraction().orElseThrow() == 0);
        check(new DiggerHudSnapshot.Gauge(known, 50, 100, false).fraction().orElseThrow() == .5);
        rejects(() -> new DiggerHudSnapshot.Gauge(known, Double.NaN, 100, false));
        rejects(() -> new DiggerHudSnapshot.Gauge(known, 0, 0, false));
        rejects(() -> new DiggerHudSnapshot.Gauge(known, 101, 100, false));
        rejects(() -> new DiggerHudSnapshot.Gauge(known, -1, 100, false));
        rejects(() -> new DiggerHudSnapshot.Gauge(DiggerHudSnapshot.Availability.UNKNOWN, 0, 0, true));
        System.out.println(checks + " HUD contract checks passed (Java only, not in-game)");
    }
}
