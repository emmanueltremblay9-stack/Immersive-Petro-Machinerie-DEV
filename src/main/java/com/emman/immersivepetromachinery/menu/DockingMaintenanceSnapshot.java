package com.emman.immersivepetromachinery.menu;

import com.emman.immersivepetromachinery.docking.TunnelDiggerDockingDetector;
import com.emman.immersivepetromachinery.mining.IndustrialDiggerMiningProfile;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;

public record DockingMaintenanceSnapshot(
        BlockPos controllerPos,
        boolean platformValid,
        String platformSummary,
        DetectionDisplayStatus detectionStatus,
        String detectionSummary,
        boolean softLocked,
        @Nullable UUID lockedTunnelDigger,
        String tunnelDiggerName,
        int fuelAmount,
        int fuelCapacity,
        String fuelFluidId,
        int lubricantAmount,
        int lubricantCapacity,
        String lubricantFluidId,
        String miningProfileName,
        int miningFootprintWidth,
        int miningBlocksPerLayer,
        int miningCycleTicks
) {
    private static final int MAX_SUMMARY_LENGTH = 256;
    private static final int MAX_FLUID_ID_LENGTH = 128;
    private static final int MAX_DIGGER_NAME_LENGTH = 128;
    private static final int MAX_PROFILE_NAME_LENGTH = 48;
    private static final String EMPTY_FLUID_ID = "empty";
    private static final String UNKNOWN_FLUID_ID = "unknown";
    private static final String NO_BORE_HEAD_PROFILE = "No bore head";

    public DockingMaintenanceSnapshot {
        controllerPos = controllerPos == null ? BlockPos.ZERO : controllerPos;
        platformSummary = trim(defaultText(platformSummary, "controller state unavailable"));
        detectionStatus = detectionStatus == null ? DetectionDisplayStatus.NOT_CHECKED : detectionStatus;
        detectionSummary = trim(defaultText(detectionSummary, "not checked"));
        softLocked = lockedTunnelDigger != null;
        tunnelDiggerName = trimName(defaultText(
                tunnelDiggerName,
                lockedTunnelDigger == null ? "none" : "Tunnel Digger"
        ));
        fuelCapacity = nonNegative(fuelCapacity);
        fuelAmount = clampAmount(fuelAmount, fuelCapacity);
        fuelFluidId = normalizeFluidId(fuelFluidId, fuelAmount);
        lubricantCapacity = nonNegative(lubricantCapacity);
        lubricantAmount = clampAmount(lubricantAmount, lubricantCapacity);
        lubricantFluidId = normalizeFluidId(lubricantFluidId, lubricantAmount);
        miningProfileName = trimProfileName(defaultText(miningProfileName, NO_BORE_HEAD_PROFILE));
        boolean hasMiningProfile = !NO_BORE_HEAD_PROFILE.equals(miningProfileName);
        miningFootprintWidth = hasMiningProfile ? nonNegative(miningFootprintWidth) : 0;
        miningBlocksPerLayer = miningFootprintWidth == 0 ? 0 : normalizeBlocksPerLayer(
                miningBlocksPerLayer,
                miningFootprintWidth
        );
        miningCycleTicks = miningFootprintWidth == 0 ? 0 : nonNegative(miningCycleTicks);
    }

    public static DockingMaintenanceSnapshot notAvailable() {
        return new DockingMaintenanceSnapshot(
                BlockPos.ZERO,
                false,
                "controller state unavailable",
                DetectionDisplayStatus.NOT_CHECKED,
                "not checked",
                false,
                null,
                "none",
                0,
                0,
                EMPTY_FLUID_ID,
                0,
                0,
                EMPTY_FLUID_ID,
                NO_BORE_HEAD_PROFILE,
                0,
                0,
                0
        );
    }

    public static DockingMaintenanceSnapshot create(
            BlockPos controllerPos,
            boolean platformValid,
            String platformSummary,
            @Nullable TunnelDiggerDockingDetector.DetectionResult detection,
            @Nullable UUID lockedTunnelDigger,
            String lockedTunnelDiggerName,
            int fuelAmount,
            int fuelCapacity,
            String fuelFluidId,
            int lubricantAmount,
            int lubricantCapacity,
            String lubricantFluidId,
            @Nullable IndustrialDiggerMiningProfile miningProfile
    ) {
        DetectionDisplayStatus status = DetectionDisplayStatus.from(detection);
        String tunnelDiggerName = lockedTunnelDigger != null
                ? lockedTunnelDiggerName
                : detection != null && detection.candidate() != null ? detection.candidate().displayName() : "none";
        String miningProfileName = miningProfile == null ? NO_BORE_HEAD_PROFILE : miningProfile.displayName();
        int miningFootprintWidth = miningProfile == null ? 0 : miningProfile.footprintWidth();
        int miningBlocksPerLayer = miningProfile == null ? 0 : miningProfile.blocksPerLayer();
        int miningCycleTicks = miningProfile == null ? 0 : miningProfile.cycleTicks();
        return new DockingMaintenanceSnapshot(
                controllerPos,
                platformValid,
                trim(platformSummary),
                status,
                trim(detection == null ? "not checked" : detection.summary()),
                lockedTunnelDigger != null,
                lockedTunnelDigger,
                trimName(tunnelDiggerName),
                fuelAmount,
                fuelCapacity,
                trimFluidId(fuelFluidId),
                lubricantAmount,
                lubricantCapacity,
                trimFluidId(lubricantFluidId),
                trimProfileName(miningProfileName),
                miningFootprintWidth,
                miningBlocksPerLayer,
                miningCycleTicks
        );
    }

    public static DockingMaintenanceSnapshot read(RegistryFriendlyByteBuf buffer) {
        if (buffer == null) {
            return notAvailable();
        }

        try {
            BlockPos controllerPos = buffer.readBlockPos();
            boolean platformValid = buffer.readBoolean();
            String platformSummary = buffer.readUtf(MAX_SUMMARY_LENGTH);
            DetectionDisplayStatus detectionStatus = buffer.readEnum(DetectionDisplayStatus.class);
            String detectionSummary = buffer.readUtf(MAX_SUMMARY_LENGTH);
            boolean softLocked = buffer.readBoolean();
            UUID lockedTunnelDigger = buffer.readBoolean() ? buffer.readUUID() : null;
            String tunnelDiggerName = buffer.readUtf(MAX_DIGGER_NAME_LENGTH);
            int fuelAmount = buffer.readVarInt();
            int fuelCapacity = buffer.readVarInt();
            String fuelFluidId = buffer.readUtf(MAX_FLUID_ID_LENGTH);
            int lubricantAmount = buffer.readVarInt();
            int lubricantCapacity = buffer.readVarInt();
            String lubricantFluidId = buffer.readUtf(MAX_FLUID_ID_LENGTH);
            String miningProfileName = buffer.readUtf(MAX_PROFILE_NAME_LENGTH);
            int miningFootprintWidth = buffer.readVarInt();
            int miningBlocksPerLayer = buffer.readVarInt();
            int miningCycleTicks = buffer.readVarInt();
            return new DockingMaintenanceSnapshot(
                    controllerPos,
                    platformValid,
                    platformSummary,
                    detectionStatus,
                    detectionSummary,
                    softLocked,
                    lockedTunnelDigger,
                    tunnelDiggerName,
                    fuelAmount,
                    fuelCapacity,
                    fuelFluidId,
                    lubricantAmount,
                    lubricantCapacity,
                    lubricantFluidId,
                    miningProfileName,
                    miningFootprintWidth,
                    miningBlocksPerLayer,
                    miningCycleTicks
            );
        } catch (RuntimeException exception) {
            return notAvailable();
        }
    }

    public void write(RegistryFriendlyByteBuf buffer) {
        buffer.writeBlockPos(controllerPos);
        buffer.writeBoolean(platformValid);
        buffer.writeUtf(trim(platformSummary), MAX_SUMMARY_LENGTH);
        buffer.writeEnum(detectionStatus);
        buffer.writeUtf(trim(detectionSummary), MAX_SUMMARY_LENGTH);
        buffer.writeBoolean(softLocked);
        buffer.writeBoolean(lockedTunnelDigger != null);

        if (lockedTunnelDigger != null) {
            buffer.writeUUID(lockedTunnelDigger);
        }

        buffer.writeUtf(trimName(tunnelDiggerName), MAX_DIGGER_NAME_LENGTH);
        buffer.writeVarInt(fuelAmount);
        buffer.writeVarInt(fuelCapacity);
        buffer.writeUtf(trimFluidId(fuelFluidId), MAX_FLUID_ID_LENGTH);
        buffer.writeVarInt(lubricantAmount);
        buffer.writeVarInt(lubricantCapacity);
        buffer.writeUtf(trimFluidId(lubricantFluidId), MAX_FLUID_ID_LENGTH);
        buffer.writeUtf(trimProfileName(miningProfileName), MAX_PROFILE_NAME_LENGTH);
        buffer.writeVarInt(miningFootprintWidth);
        buffer.writeVarInt(miningBlocksPerLayer);
        buffer.writeVarInt(miningCycleTicks);
    }

    public String lockedTunnelDiggerText() {
        return lockedTunnelDigger == null ? "none" : tunnelDiggerName;
    }

    public String miningProfileText() {
        if (miningFootprintWidth <= 0) {
            return miningProfileName;
        }

        return miningProfileName
                + " "
                + miningFootprintWidth
                + "x"
                + miningFootprintWidth
                + " ("
                + miningBlocksPerLayer
                + " blocks/layer)";
    }

    private static String trim(String value) {
        return value.length() <= MAX_SUMMARY_LENGTH ? value : value.substring(0, MAX_SUMMARY_LENGTH);
    }

    private static String trimFluidId(String value) {
        return value.length() <= MAX_FLUID_ID_LENGTH ? value : value.substring(0, MAX_FLUID_ID_LENGTH);
    }

    private static String trimName(String value) {
        return value.length() <= MAX_DIGGER_NAME_LENGTH ? value : value.substring(0, MAX_DIGGER_NAME_LENGTH);
    }

    private static String trimProfileName(String value) {
        return value.length() <= MAX_PROFILE_NAME_LENGTH ? value : value.substring(0, MAX_PROFILE_NAME_LENGTH);
    }

    private static String defaultText(String value, String fallback) {
        return value == null || value.isBlank() ? fallback : value;
    }

    private static int nonNegative(int value) {
        return Math.max(0, value);
    }

    private static int clampAmount(int amount, int capacity) {
        return Math.min(nonNegative(amount), capacity);
    }

    private static String normalizeFluidId(String fluidId, int amount) {
        if (amount <= 0) {
            return EMPTY_FLUID_ID;
        }

        String normalized = trimFluidId(defaultText(fluidId, UNKNOWN_FLUID_ID));
        return EMPTY_FLUID_ID.equals(normalized) ? UNKNOWN_FLUID_ID : normalized;
    }

    private static int normalizeBlocksPerLayer(int blocksPerLayer, int footprintWidth) {
        int expectedBlocks = squareBlockCount(footprintWidth);
        return blocksPerLayer == expectedBlocks ? blocksPerLayer : expectedBlocks;
    }

    private static int squareBlockCount(int footprintWidth) {
        long blocks = (long) footprintWidth * footprintWidth;
        return blocks > Integer.MAX_VALUE ? Integer.MAX_VALUE : (int) blocks;
    }

    public enum DetectionDisplayStatus {
        NOT_CHECKED,
        NONE,
        MISALIGNED,
        ALIGNED;

        private static DetectionDisplayStatus from(@Nullable TunnelDiggerDockingDetector.DetectionResult detection) {
            if (detection == null) {
                return NOT_CHECKED;
            }

            return switch (detection.status()) {
                case NONE -> NONE;
                case MISALIGNED -> MISALIGNED;
                case ALIGNED -> ALIGNED;
            };
        }
    }
}
