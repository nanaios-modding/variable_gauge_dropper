package com.nanaios.variable_gauge_dropper.client.gui;

import com.nanaios.variable_gauge_dropper.container.ConfigurableItemContainer;
import mekanism.client.gui.GuiMekanism;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;

public class GuiConfigurable extends GuiMekanism<ConfigurableItemContainer> {

    public GuiConfigurable(ConfigurableItemContainer configurableItemContainer, Inventory inv, Component title) {
        super(configurableItemContainer, inv, title);
        imageWidth = 248;
        imageHeight += 20;
    }

    @Override
    protected void addGuiElements() {
        int size = menu.slots.size();
        for (int i = 0; i < size; i++) {
            Slot slot = menu.slots.get(i);
            final int index = i;
            // initialize selected item
            if (selected == -1 && isValidItem(index)) {
                select(index);
            }
            addRenderableWidget(new GuiSlot(SlotType.NORMAL, this, slot.x - 1, slot.y - 1)
                    .click((e, x, y) -> select(index), MekanismSounds.BEEP)
                    .overlayColor(isValidItem(index) ? null : () -> 0xCC333333)
                    .with(() -> index == selected ? SlotOverlay.SELECT : null));
        }
    }
}

