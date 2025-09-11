package com.nanaios.more_gauge_droppers;

import com.mojang.logging.LogUtils;
import com.nanaios.more_gauge_droppers.registries.MoreGaugeDroppersContainerTypes;
import com.nanaios.more_gauge_droppers.registries.MoreGaugeDroppersCreativeTabs;
import com.nanaios.more_gauge_droppers.registries.MoreGaugeDroppersItems;
import mekanism.common.lib.Version;
import com.nanaios.more_gauge_droppers.network.PacketHandler;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

@Mod(MoreGaugeDroppers.MODID)
public class MoreGaugeDroppers {
    public static final String MODID = "more_gauge_droppers";
    public static final Logger LOGGER = LogUtils.getLogger();

    private final PacketHandler packetHandler;

    public static MoreGaugeDroppers instance;
    public final Version versionNumber;

    public MoreGaugeDroppers(ModContainer modContainer, IEventBus modEventBus) {
        instance = this;

        MoreGaugeDroppersItems.ITEMS.register(modEventBus);
        MoreGaugeDroppersContainerTypes.CONTAINER_TYPES.register(modEventBus);
        MoreGaugeDroppersCreativeTabs.CREATIVE_TABS.register(modEventBus);

        // versionNumber, packetHandler の初期化
        versionNumber = new Version(modContainer); // 必要に応じて修正
        packetHandler = new PacketHandler();
        modEventBus.addListener(this::commonSetup);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        packetHandler.initialize();
    }

    public static PacketHandler packetHandler() {
        return instance.packetHandler;
    }

    public static ResourceLocation rl(@NotNull String path) {
        return ResourceLocation.fromNamespaceAndPath(MoreGaugeDroppers.MODID, path);
    }
}
