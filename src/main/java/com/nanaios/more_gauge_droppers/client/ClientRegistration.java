package com.nanaios.more_gauge_droppers.client;

import com.nanaios.more_gauge_droppers.MoreGaugeDroppers;
import com.nanaios.more_gauge_droppers.client.gui.GuiConfigurable;
import com.nanaios.more_gauge_droppers.client.key.VariableGaugeDropperKeyHandler;
import com.nanaios.more_gauge_droppers.registries.MoreGaugeDroppersContainerTypes;
import mekanism.client.ClientRegistrationUtil;
import net.minecraft.core.registries.Registries;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegisterEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD,modid = MoreGaugeDroppers.MODID, value = Dist.CLIENT)
public class ClientRegistration {
    @SubscribeEvent
    public static void onRegisterKeyMappings(RegisterKeyMappingsEvent event) {
        VariableGaugeDropperKeyHandler.registerKeybindings(event);
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public static void registerContainers(RegisterEvent event) {
        event.register(Registries.MENU, helper -> {
            ClientRegistrationUtil.registerScreen(MoreGaugeDroppersContainerTypes.CONFIGURABLE_CONTAINER, GuiConfigurable::new);
        });
    }
}
