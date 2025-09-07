package com.nanaios.variable_gauge_dropper.client.gui;

import com.nanaios.variable_gauge_dropper.container.ConfigurableItemContainer;
import mekanism.client.gui.GuiMekanism;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class GuiConfigurable extends GuiMekanism<ConfigurableItemContainer> {
    public GuiConfigurable(ConfigurableItemContainer configurableItemContainer, Inventory inv, Component title) {
        super(configurableItemContainer, inv, title);
    }
}

