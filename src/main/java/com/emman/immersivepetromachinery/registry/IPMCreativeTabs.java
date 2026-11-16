package com.emman.immersivepetromachinery.registry;

import com.emman.immersivepetromachinery.ImmersivePetroMachinery;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class IPMCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ImmersivePetroMachinery.MOD_ID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MAIN = CREATIVE_TABS.register("main", () ->
            CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup." + ImmersivePetroMachinery.MOD_ID))
                    .icon(() -> new ItemStack(IPMBlocks.DOCKING_CONTROLLER.get()))
                    .displayItems((parameters, output) -> {
                        acceptMainTabItems(item -> output.accept(item));
                    })
                    .build()
    );

    private IPMCreativeTabs() {
    }

    public static void register(IEventBus modEventBus) {
        CREATIVE_TABS.register(modEventBus);
    }

    public static List<ItemStack> mainTabItemsForDiagnostics() {
        List<ItemStack> items = new ArrayList<>();
        acceptMainTabItems(item -> items.add(new ItemStack(item)));
        return List.copyOf(items);
    }

    private static void acceptMainTabItems(Consumer<ItemLike> output) {
        IPMBlocks.PLATFORM_BLOCK_ITEMS.forEach(item -> {
            if (item.get() != IPMBlocks.SURVEY_CONSOLE.get().asItem()) {
                output.accept(item.get());
            }
        });
        output.accept(IPMFormedMultiblocks.DOCKING_GANTRY.blockItem().get());
        output.accept(IPMBlocks.CORE_SAMPLING_UPGRADE.get());
        output.accept(IPMBlocks.BORE_HEAD_MK1.get());
        output.accept(IPMBlocks.BORE_HEAD_MK2.get());
        output.accept(IPMBlocks.BORE_HEAD_MK3.get());
    }
}
