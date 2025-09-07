package com.nanaios.variable_gauge_dropper.client;

import com.nanaios.variable_gauge_dropper.VariableGaugeDropper;
import com.nanaios.variable_gauge_dropper.client.key.VariableGaugeDropperKeyHandler;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD,modid = VariableGaugeDropper.MODID, value = Dist.CLIENT)
public class ClientRegistration {
    @SubscribeEvent
    public static void onRegisterKeyMappings(RegisterKeyMappingsEvent event) {
        VariableGaugeDropper.LOGGER.info("register key!");
        VariableGaugeDropperKeyHandler.registerKeybindings(event);
    }
}
