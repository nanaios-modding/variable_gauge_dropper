package com.nanaios.variable_gauge_dropper.client.gui;

import com.nanaios.variable_gauge_dropper.container.ConfigurableItemContainer;
import mekanism.client.gui.GuiMekanism;
import mekanism.client.gui.element.slot.GuiSlot;
import mekanism.client.gui.element.slot.SlotType;
import mekanism.common.inventory.container.slot.SlotOverlay;
import mekanism.common.registries.MekanismSounds;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class GuiConfigurable extends GuiMekanism<ConfigurableItemContainer> {

    private int selected = -1;

    public GuiConfigurable(ConfigurableItemContainer configurableItemContainer, Inventory inv, Component title) {
        super(configurableItemContainer, inv, title);
        imageWidth = 248;
        //imageHeight += 20;
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

    private boolean select(int index) {
        if (isValidItem(index)) {
            selected = index;
            return true;
        }
        return false;
    }


    private boolean isValidItem(int index) {
        return ConfigurableItemContainer.isConfigurableItem(getStack(index));
    }

    private ItemStack getStack(int index) {
        if (index == -1) {
            return ItemStack.EMPTY;
        }
        return menu.slots.get(index).getItem();
    }

}

