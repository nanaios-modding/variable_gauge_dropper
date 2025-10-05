package com.nanaios.more_gauge_droppers.client.key;

import com.nanaios.more_gauge_droppers.MoreGaugeDroppers;
import com.nanaios.more_gauge_droppers.MoreGaugeDroppersLang;
import com.nanaios.more_gauge_droppers.container.ConfigurableItemContainer;
import com.nanaios.more_gauge_droppers.network.to_server.PacketOpenGui;
import com.nanaios.more_gauge_droppers.network.to_server.PacketOpenGui.GuiType;
import mekanism.client.ClientRegistrationUtil;
import mekanism.client.key.MekKeyBindingBuilder;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import org.lwjgl.glfw.GLFW;

public class VariableGaugeDropperKeyHandler {
    public static final KeyMapping configurableKey = new MekKeyBindingBuilder()
            .category(MoreGaugeDroppersLang.MORE_GAUGE_DROPPERS)
            .description(MoreGaugeDroppersLang.KEY_CONFIGURABLE)
            .conflictInGame().keyCode(GLFW.GLFW_KEY_V)
            .onKeyDown((kb, isRepeat) -> {
                Player player = Minecraft.getInstance().player;
                if (player != null && ConfigurableItemContainer.hasConfigurableItem(player)) {
                    MoreGaugeDroppers.packetHandler().sendToServer(new PacketOpenGui(GuiType.CONFIGURABLE));
                }
            }).build();
    public static void registerKeybindings(RegisterKeyMappingsEvent event) {
        ClientRegistrationUtil.registerKeyBindings(event, configurableKey);
    }
}
