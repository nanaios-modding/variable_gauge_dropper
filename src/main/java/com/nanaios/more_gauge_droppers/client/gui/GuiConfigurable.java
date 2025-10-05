package com.nanaios.more_gauge_droppers.client.gui;

import com.nanaios.more_gauge_droppers.MoreGaugeDroppers;
import com.nanaios.more_gauge_droppers.MoreGaugeDroppersLang;
import com.nanaios.more_gauge_droppers.container.ConfigurableItemContainer;
import com.nanaios.more_gauge_droppers.item.ItemVariableGaugeDropper;
import com.nanaios.more_gauge_droppers.network.to_server.PacketVariableGaugeData;
import com.nanaios.more_gauge_droppers.network.to_server.PacketVariableGaugeData.VariableGaugeDataType;
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
    private int capacity = 0;
    private GuiTextField capacityField;
    private GuiTextField transferRateField;

    public GuiConfigurable(ConfigurableItemContainer configurableItemContainer, Inventory inv, Component title) {
        super(configurableItemContainer, inv, title);
        imageWidth = 250;
        imageHeight = 110;
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
        capacityField = addRenderableWidget(new GuiTextField(this, 100, 25, 50, 12));
        capacityField.setFocused(true);
        capacityField.setInputValidator(InputValidator.DIGIT)
                .setEnterHandler(this::setCapacity)
                .setMaxLength(6);

        transferRateField = addRenderableWidget(new GuiTextField(this, 100, 55, 50, 12));
        transferRateField.setInputValidator(InputValidator.DIGIT)
                .setEnterHandler(this::setTransferRate)
                .setMaxLength(6);
    }

    @Override
    protected void drawForegroundText(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY) {
        renderTitleText(guiGraphics);
        drawString(guiGraphics , MoreGaugeDroppersLang.GUI_CAPACITY_TEXT.translate(),18,27,titleTextColor());
        drawString(guiGraphics ,Component.literal("0mb ≦"),70,27,titleTextColor());
        drawString(guiGraphics ,Component.literal("≦ " + ItemVariableGaugeDropper.MAX_CAPACITY + "mb"),155,27,titleTextColor());
        drawString(guiGraphics , MoreGaugeDroppersLang.GUI_TRANSFER_TEXT.translate(),18,57,titleTextColor());
        drawString(guiGraphics ,Component.literal("0mb ≦"),70,57,titleTextColor());
        ItemStack stack = getStack(selected);
        if(stack.getItem() instanceof ItemVariableGaugeDropper) {
            capacity = ItemVariableGaugeDropper.readCapacityFromNBT(stack);
        }
        drawString(guiGraphics ,Component.literal("≦ " + capacity + "mb"),155,57,titleTextColor());
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
            MoreGaugeDroppers.packetHandler().sendToServer(new PacketVariableGaugeData(VariableGaugeDataType.CAPACITY,selected,Integer.parseInt(capacityField.getText())));
            capacityField.setText("");
        }
    }
    private void setTransferRate() {
        if(!transferRateField.getText().isEmpty()) {
            MoreGaugeDroppers.packetHandler().sendToServer(new PacketVariableGaugeData(VariableGaugeDataType.TRANSFER_RATE,selected,Integer.parseInt(transferRateField.getText())));
            transferRateField.setText("");
        }
    }
}

