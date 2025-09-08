package com.nanaios.variable_gauge_dropper.client.gui;

import com.nanaios.variable_gauge_dropper.VariableGaugeDropper;
import com.nanaios.variable_gauge_dropper.capabilities.VariableGaugeDropperContentsHandler;
import com.nanaios.variable_gauge_dropper.container.ConfigurableItemContainer;
import com.nanaios.variable_gauge_dropper.item.ItemVariableGaugeDropper;
import com.nanaios.variable_gauge_dropper.network.to_server.PacketConfigurableValue;
import mekanism.client.gui.GuiMekanism;
import mekanism.client.gui.element.slot.GuiSlot;
import mekanism.client.gui.element.slot.SlotType;
import mekanism.client.gui.element.text.GuiTextField;
import mekanism.common.inventory.container.slot.SlotOverlay;
import mekanism.common.registries.MekanismSounds;
import mekanism.common.util.text.InputValidator;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class GuiConfigurable extends GuiMekanism<ConfigurableItemContainer> {

    private int selected = -1;
    private GuiTextField capacityField;

    public GuiConfigurable(ConfigurableItemContainer configurableItemContainer, Inventory inv, Component title) {
        super(configurableItemContainer, inv, title);
        imageWidth = 200;
        imageHeight = 70;
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
        capacityField = addRenderableWidget(new GuiTextField(this, 75, 25, 50, 12));
        capacityField.setFocused(true);
        capacityField.setInputValidator(InputValidator.DIGIT)
                .setEnterHandler(this::setCapacity)
                .setMaxLength(6);
    }

    @Override
    protected void drawForegroundText(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY) {
        renderTitleText(guiGraphics);
        drawString(guiGraphics ,Component.literal("0mb ≦"),45,28,titleTextColor());
        drawString(guiGraphics ,Component.literal("≦ " + ItemVariableGaugeDropper.MAX_CAPACITY + "mb"),135,28,titleTextColor());
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

    private void setCapacity() {
        if(!capacityField.getText().isEmpty()) {
            VariableGaugeDropper.packetHandler().sendToServer(new PacketConfigurableValue(selected,Integer.parseInt(capacityField.getText())));
            capacityField.setText("");
        }
    }
}

