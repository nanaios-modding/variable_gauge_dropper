package com.nanaios.more_gauge_droppers;

import com.mojang.logging.LogUtils;
import com.nanaios.more_gauge_droppers.registries.MoreGaugeDroppersContainerTypes;
import com.nanaios.more_gauge_droppers.registries.MoreGaugeDroppersCreativeTabs;
import com.nanaios.more_gauge_droppers.registries.MoreGaugeDroppersItems;
import mekanism.common.lib.Version;
import com.nanaios.more_gauge_droppers.network.PacketHandler;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

@Mod(MoreGaugeDroppers.MODID)
public class MoreGaugeDroppers {
    public static final String MODID = "more_gauge_droppers";
    public static final Logger LOGGER = LogUtils.getLogger();

    private final PacketHandler packetHandler;

    public static MoreGaugeDroppers instance;
    public final Version versionNumber;

    @SuppressWarnings("removal")
    public MoreGaugeDroppers(FMLJavaModLoadingContext context) {
        instance = this;

        IEventBus modEventBus = context.getModEventBus();

        MoreGaugeDroppersItems.ITEMS.register(modEventBus);
        MoreGaugeDroppersContainerTypes.CONTAINER_TYPES.register(modEventBus);
        MoreGaugeDroppersCreativeTabs.CREATIVE_TABS.register(modEventBus);

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
        return new ResourceLocation(MoreGaugeDroppers.MODID,path);
    }
}
