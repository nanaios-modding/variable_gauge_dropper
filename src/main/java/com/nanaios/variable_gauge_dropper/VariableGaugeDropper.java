package com.nanaios.variable_gauge_dropper;

import com.mojang.logging.LogUtils;
import com.nanaios.variable_gauge_dropper.registries.VariableGaugeDropperContainerTypes;
import com.nanaios.variable_gauge_dropper.registries.VariableGaugeDropperItems;
import mekanism.common.lib.Version;
import com.nanaios.variable_gauge_dropper.network.PacketHandler;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

@Mod(VariableGaugeDropper.MODID)
public class VariableGaugeDropper {
    public static final String MODID = "variable_gauge_dropper";
    public static final Logger LOGGER = LogUtils.getLogger();

    private final PacketHandler packetHandler;

    public static VariableGaugeDropper instance;
    public final Version versionNumber;

    @SuppressWarnings("removal")
    public VariableGaugeDropper(FMLJavaModLoadingContext context) {
        instance = this;

        IEventBus modEventBus = context.getModEventBus();

        VariableGaugeDropperItems.ITEMS.register(modEventBus);
        VariableGaugeDropperContainerTypes.CONTAINER_TYPES.register(modEventBus);

        versionNumber = new Version(ModLoadingContext.get().getActiveContainer());

        packetHandler = new PacketHandler();
        modEventBus.addListener(this::commonSetup);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        packetHandler.initialize();
    }

    public static PacketHandler packetHandler() {
        return instance.packetHandler;
    }

    @SuppressWarnings("removal")
    public static ResourceLocation rl(@NotNull String path) {
        return new ResourceLocation(path);
    }
}
