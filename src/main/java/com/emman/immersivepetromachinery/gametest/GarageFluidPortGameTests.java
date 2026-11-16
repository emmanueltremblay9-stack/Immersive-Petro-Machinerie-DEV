package com.emman.immersivepetromachinery.gametest;

import com.emman.immersivepetromachinery.ImmersivePetroMachinery;
import com.emman.immersivepetromachinery.block.DockingControllerBlock;
import com.emman.immersivepetromachinery.fluid.DockFluidPortAccess;
import com.emman.immersivepetromachinery.multiblock.DockingPlatformValidator;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

@GameTestHolder(ImmersivePetroMachinery.MOD_ID)
@PrefixGameTestTemplate(false)
public final class GarageFluidPortGameTests {
    // The GameTest structure block is one block below the template origin.
    private static final BlockPos CONTROLLER = new BlockPos(5, 3, 12);
    private static final int TRANSFER_MB = 25;

    private GarageFluidPortGameTests() {
    }

    @GameTest(template = "multiblocks/docking_platform", timeoutTicks = 180)
    public static void cached_ie_pipe_route_tracks_garage_validity_and_removal(GameTestHelper helper) {
        ServerLevel level = helper.getLevel();
        BlockPos controller = helper.absolutePos(CONTROLLER);
        Direction facing = level.getBlockState(controller).getValue(DockingControllerBlock.FACING);
        BlockPos port = DockFluidPortAccess.fuelPortPos(controller, facing);
        BlockPos pipe = port.relative(facing.getOpposite());
        BlockPos required = DockingPlatformValidator.toWorld(controller, facing, 5, 7, -10);
        BlockState original = level.getBlockState(required);
        BlockState originalPipeSite = level.getBlockState(pipe);
        Block pipeBlock = BuiltInRegistries.BLOCK
                .getOptional(ResourceLocation.fromNamespaceAndPath("immersiveengineering", "fluid_pipe"))
                .orElseThrow();
        Fluid diesel = BuiltInRegistries.FLUID
                .getOptional(ResourceLocation.fromNamespaceAndPath("immersivepetroleum", "diesel"))
                .orElseThrow();

        DockingPlatformValidator.ValidationResult layout = DockingPlatformValidator.validate(level, controller, facing);
        helper.assertTrue(layout.valid(), layout.summary());
        level.scheduleTick(controller, level.getBlockState(controller).getBlock(), 1);

        helper.startSequence()
                .thenWaitUntil(() -> helper.assertTrue(
                        level.getBlockState(controller).getValue(DockingControllerBlock.VALID), "Garage is not assembled"))
                .thenExecute(() -> level.setBlockAndUpdate(pipe, pipeBlock.defaultBlockState()))
                .thenExecuteAfter(2, () -> {
                    helper.assertValueEqual(feedPipe(helper, pipe, facing, diesel), TRANSFER_MB, "Initial feed");
                    level.removeBlock(required, false);
                })
                .thenWaitUntil(() -> helper.assertFalse(
                        level.getBlockState(controller).getValue(DockingControllerBlock.VALID), "Garage is still valid"))
                .thenExecute(() -> {
                    helper.assertValueEqual(feedPipe(helper, pipe, facing, diesel), 0, "Feed after structural failure");
                    level.setBlockAndUpdate(required, original);
                })
                .thenWaitUntil(() -> helper.assertTrue(
                        level.getBlockState(controller).getValue(DockingControllerBlock.VALID), "Garage did not revalidate"))
                .thenExecute(() -> {
                    helper.assertValueEqual(feedPipe(helper, pipe, facing, diesel), TRANSFER_MB, "Feed after restoration");
                    level.removeBlock(controller, false);
                    helper.assertValueEqual(feedPipe(helper, pipe, facing, diesel), 0, "Feed after controller removal");
                    level.setBlockAndUpdate(pipe, originalPipeSite);
                })
                .thenSucceed();
    }

    private static int feedPipe(GameTestHelper helper, BlockPos pipe, Direction facing, Fluid diesel) {
        IFluidHandler inlet = helper.getLevel().getCapability(Capabilities.FluidHandler.BLOCK, pipe, facing.getOpposite());
        helper.assertTrue(inlet != null, "Missing IE pipe capability");
        return inlet.fill(new FluidStack(diesel, TRANSFER_MB), IFluidHandler.FluidAction.EXECUTE);
    }
}
