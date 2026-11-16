package com.emman.immersivepetromachinery.multiblock.formed;

import blusunrize.immersiveengineering.api.multiblocks.blocks.logic.IMultiblockState;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;

public final class DockingGantryFormedState implements IMultiblockState {
    private static final String DIAGNOSTICS_TAG = "Diagnostics";
    private static final String MASTER_X_TAG = "MasterX";
    private static final String MASTER_Y_TAG = "MasterY";
    private static final String MASTER_Z_TAG = "MasterZ";
    private static final String ORIENTATION_TAG = "Orientation";
    private static final String FORMED_VALID_TAG = "FormedValid";
    private static final String CLICK_X_TAG = "ClickX";
    private static final String CLICK_Y_TAG = "ClickY";
    private static final String CLICK_Z_TAG = "ClickZ";

    @Nullable
    private BlockPos masterPosition;
    private Direction orientation = Direction.NORTH;
    private boolean formedValid;
    @Nullable
    private BlockPos controllerRelativeClickPosition;

    public void recordDiagnostics(
            BlockPos masterPosition,
            Direction orientation,
            boolean formedValid,
            BlockPos controllerRelativeClickPosition
    ) {
        this.masterPosition = masterPosition.immutable();
        this.orientation = orientation;
        this.formedValid = formedValid;
        this.controllerRelativeClickPosition = controllerRelativeClickPosition.immutable();
    }

    @Nullable
    public BlockPos getMasterPosition() {
        return masterPosition;
    }

    public Direction getOrientation() {
        return orientation;
    }

    public boolean isFormedValid() {
        return formedValid;
    }

    @Nullable
    public BlockPos getControllerRelativeClickPosition() {
        return controllerRelativeClickPosition;
    }

    @Override
    public void writeSaveNBT(CompoundTag tag, HolderLookup.Provider registries) {
        if (masterPosition == null || controllerRelativeClickPosition == null) {
            return;
        }

        CompoundTag diagnostics = new CompoundTag();
        diagnostics.putInt(MASTER_X_TAG, masterPosition.getX());
        diagnostics.putInt(MASTER_Y_TAG, masterPosition.getY());
        diagnostics.putInt(MASTER_Z_TAG, masterPosition.getZ());
        diagnostics.putString(ORIENTATION_TAG, orientation.getName());
        diagnostics.putBoolean(FORMED_VALID_TAG, formedValid);
        diagnostics.putInt(CLICK_X_TAG, controllerRelativeClickPosition.getX());
        diagnostics.putInt(CLICK_Y_TAG, controllerRelativeClickPosition.getY());
        diagnostics.putInt(CLICK_Z_TAG, controllerRelativeClickPosition.getZ());
        tag.put(DIAGNOSTICS_TAG, diagnostics);
    }

    @Override
    public void readSaveNBT(CompoundTag tag, HolderLookup.Provider registries) {
        if (!tag.contains(DIAGNOSTICS_TAG)) {
            clearDiagnostics();
            return;
        }

        CompoundTag diagnostics = tag.getCompound(DIAGNOSTICS_TAG);
        if (!hasCompleteDiagnostics(diagnostics)) {
            clearDiagnostics();
            return;
        }

        Direction savedOrientation = Direction.byName(diagnostics.getString(ORIENTATION_TAG));
        if (savedOrientation == null || savedOrientation.getAxis().isVertical()) {
            clearDiagnostics();
            return;
        }

        masterPosition = new BlockPos(
                diagnostics.getInt(MASTER_X_TAG),
                diagnostics.getInt(MASTER_Y_TAG),
                diagnostics.getInt(MASTER_Z_TAG)
        );
        orientation = savedOrientation;
        formedValid = diagnostics.getBoolean(FORMED_VALID_TAG);
        controllerRelativeClickPosition = new BlockPos(
                diagnostics.getInt(CLICK_X_TAG),
                diagnostics.getInt(CLICK_Y_TAG),
                diagnostics.getInt(CLICK_Z_TAG)
        );
    }

    private static boolean hasCompleteDiagnostics(CompoundTag diagnostics) {
        return diagnostics.contains(MASTER_X_TAG, Tag.TAG_INT)
                && diagnostics.contains(MASTER_Y_TAG, Tag.TAG_INT)
                && diagnostics.contains(MASTER_Z_TAG, Tag.TAG_INT)
                && diagnostics.contains(ORIENTATION_TAG, Tag.TAG_STRING)
                && diagnostics.contains(FORMED_VALID_TAG, Tag.TAG_BYTE)
                && diagnostics.contains(CLICK_X_TAG, Tag.TAG_INT)
                && diagnostics.contains(CLICK_Y_TAG, Tag.TAG_INT)
                && diagnostics.contains(CLICK_Z_TAG, Tag.TAG_INT);
    }

    private void clearDiagnostics() {
        masterPosition = null;
        orientation = Direction.NORTH;
        formedValid = false;
        controllerRelativeClickPosition = null;
    }
}
