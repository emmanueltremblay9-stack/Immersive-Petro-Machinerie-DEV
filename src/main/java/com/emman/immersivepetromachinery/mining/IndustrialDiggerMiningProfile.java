package com.emman.immersivepetromachinery.mining;

import com.emman.immersivepetromachinery.registry.IPMBlocks;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;

public enum IndustrialDiggerMiningProfile {
    MK1("mk1", "MK1", 5, 80, 250, 25),
    MK2("mk2", "MK2", 7, 110, 450, 45),
    MK3("mk3", "MK3", 9, 150, 700, 70);

    private final String id;
    private final String displayName;
    private final int footprintWidth;
    private final int cycleTicks;
    private final int fuelPerLayerMb;
    private final int lubricantPerLayerMb;

    IndustrialDiggerMiningProfile(
            String id,
            String displayName,
            int footprintWidth,
            int cycleTicks,
            int fuelPerLayerMb,
            int lubricantPerLayerMb
    ) {
        this.id = id;
        this.displayName = displayName;
        this.footprintWidth = footprintWidth;
        this.cycleTicks = cycleTicks;
        this.fuelPerLayerMb = fuelPerLayerMb;
        this.lubricantPerLayerMb = lubricantPerLayerMb;
    }

    public String id() {
        return id;
    }

    public String displayName() {
        return displayName;
    }

    public int footprintWidth() {
        return footprintWidth;
    }

    public int radius() {
        return footprintWidth / 2;
    }

    public int blocksPerLayer() {
        return footprintWidth * footprintWidth;
    }

    public int cycleTicks() {
        return cycleTicks;
    }

    public int fuelPerLayerMb() {
        return fuelPerLayerMb;
    }

    public int lubricantPerLayerMb() {
        return lubricantPerLayerMb;
    }

    public List<BlockPos> targetPositions(BlockPos boreOrigin, Direction forward) {
        if (forward.getAxis() == Direction.Axis.Y) {
            throw new IllegalArgumentException("Industrial Digger mining direction must be horizontal");
        }

        Direction right = forward.getClockWise();
        List<BlockPos> targets = new ArrayList<>(blocksPerLayer());
        for (int vertical = -radius(); vertical <= radius(); vertical++) {
            for (int horizontal = -radius(); horizontal <= radius(); horizontal++) {
                BlockPos target = boreOrigin
                        .relative(forward)
                        .relative(right, horizontal)
                        .above(vertical);
                targets.add(target);
            }
        }
        return List.copyOf(targets);
    }

    public static Optional<IndustrialDiggerMiningProfile> fromBoreHead(ItemStack stack) {
        if (stack.isEmpty()) {
            return Optional.empty();
        }

        if (stack.is(IPMBlocks.BORE_HEAD_MK1.get())) {
            return Optional.of(MK1);
        }
        if (stack.is(IPMBlocks.BORE_HEAD_MK2.get())) {
            return Optional.of(MK2);
        }
        if (stack.is(IPMBlocks.BORE_HEAD_MK3.get())) {
            return Optional.of(MK3);
        }
        return Optional.empty();
    }
}
