package com.nanaios.variable_gauge_dropper.item;

import com.nanaios.variable_gauge_dropper.capabilities.VariableGaugeDropperContentsHandler;
import mekanism.common.capabilities.ItemCapabilityWrapper;
import mekanism.common.item.ItemGaugeDropper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class ItemVariableGaugeDropper extends ItemGaugeDropper {
    public ItemVariableGaugeDropper(Properties properties) {
        super(properties);
    }

    @Override
    protected void gatherCapabilities(List<ItemCapabilityWrapper.ItemCapability> capabilities, ItemStack stack, CompoundTag nbt) {
        capabilities.add(VariableGaugeDropperContentsHandler.create());
    }
}
