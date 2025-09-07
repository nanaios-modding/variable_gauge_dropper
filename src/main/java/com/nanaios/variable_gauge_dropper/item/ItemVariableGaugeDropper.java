package com.nanaios.variable_gauge_dropper.item;

import com.nanaios.variable_gauge_dropper.VariableGaugeDropper;
import com.nanaios.variable_gauge_dropper.capabilities.Capabilities;
import com.nanaios.variable_gauge_dropper.capabilities.VariableGaugeDropperContentsHandler;
import mekanism.common.capabilities.ItemCapabilityWrapper;
import mekanism.common.item.ItemGaugeDropper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;

import java.util.List;

public class ItemVariableGaugeDropper extends ItemGaugeDropper implements IConfigurableItem {
    public ItemVariableGaugeDropper(Properties properties) {
        super(properties);
    }

    @Override
    protected void gatherCapabilities(List<ItemCapabilityWrapper.ItemCapability> capabilities, ItemStack stack, CompoundTag nbt) {
        capabilities.add(VariableGaugeDropperContentsHandler.create());
    }

    public void setStackSize(ItemStack stack,int value) {
        VariableGaugeDropper.LOGGER.info("Setting stack size to {}", value);
        stack.getCapability(Capabilities.VARIABLE_GAUGE_DROPPER_CONTENTS_HANDLER).ifPresent(handler -> {
            VariableGaugeDropper.LOGGER.info("Found handler, setting stack size");
            handler.setStackSize(value);
        });
    }
}
