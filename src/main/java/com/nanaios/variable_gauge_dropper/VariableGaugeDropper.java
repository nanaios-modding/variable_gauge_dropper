package com.nanaios.variable_gauge_dropper;

import com.mojang.logging.LogUtils;
import com.nanaios.variable_gauge_dropper.registries.VariableGaugeDropperItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

@Mod(VariableGaugeDropper.MODID)
public class VariableGaugeDropper {
    public static final String MODID = "variable_gauge_dropper";
    private static final Logger LOGGER = LogUtils.getLogger();

    public VariableGaugeDropper(FMLJavaModLoadingContext context) {
        IEventBus modEventBus = context.getModEventBus();

        VariableGaugeDropperItems.ITEMS.register(modEventBus);
    }
    @SuppressWarnings("removal")
    public static ResourceLocation rl(@NotNull String path) {
        return new ResourceLocation(path);
    }
}
