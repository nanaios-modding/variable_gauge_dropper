package com.nanaios.variable_gauge_dropper.client.key;

import com.nanaios.variable_gauge_dropper.VariableGaugeDropperLang;
import mekanism.client.ClientRegistrationUtil;
import mekanism.client.key.MekKeyBindingBuilder;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import org.lwjgl.glfw.GLFW;

public class VariableGaugeDropperKeyHandler {
    public static final KeyMapping moduleTweakerKey = new MekKeyBindingBuilder().description(VariableGaugeDropperLang.KEY_MODULE_TWEAKER).conflictInGame().keyCode(GLFW.GLFW_KEY_RIGHT_ALT)
            .onKeyDown((kb, isRepeat) -> {
                Player player = Minecraft.getInstance().player;
                if (player != null && ModuleTweakerContainer.hasTweakableItem(player)) {
                    //Mekanism.packetHandler().sendToServer(new PacketOpenGui(GuiType.MODULE_TWEAKER));
                }
            }).build();
    public static void registerKeybindings(RegisterKeyMappingsEvent event) {
        ClientRegistrationUtil.registerKeyBindings(event);
    }
}
