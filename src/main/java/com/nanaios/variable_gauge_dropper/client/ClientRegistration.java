package com.nanaios.variable_gauge_dropper.client;

import com.nanaios.variable_gauge_dropper.VariableGaugeDropper;
import com.nanaios.variable_gauge_dropper.client.gui.GuiConfigurable;
import com.nanaios.variable_gauge_dropper.client.key.VariableGaugeDropperKeyHandler;
import com.nanaios.variable_gauge_dropper.registries.VariableGaugeDropperContainerTypes;
import mekanism.client.ClientRegistrationUtil;
import net.minecraft.core.registries.Registries;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegisterEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD,modid = VariableGaugeDropper.MODID, value = Dist.CLIENT)
public class ClientRegistration {
    @SubscribeEvent
    public static void onRegisterKeyMappings(RegisterKeyMappingsEvent event) {
        VariableGaugeDropper.LOGGER.info("register key!");
        VariableGaugeDropperKeyHandler.registerKeybindings(event);
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public static void registerContainers(RegisterEvent event) {
        event.register(Registries.MENU, helper -> {
            ClientRegistrationUtil.registerScreen(VariableGaugeDropperContainerTypes.CONFIGURABLE_CONTAINER, GuiConfigurable::new);
        });
    }
}
