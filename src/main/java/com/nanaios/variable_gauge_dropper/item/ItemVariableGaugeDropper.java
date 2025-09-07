package com.nanaios.variable_gauge_dropper.item;

import com.nanaios.variable_gauge_dropper.VariableGaugeDropper;
import com.nanaios.variable_gauge_dropper.capabilities.Capabilities;
import com.nanaios.variable_gauge_dropper.capabilities.VariableGaugeDropperContentsHandler;
import mekanism.common.capabilities.ItemCapabilityWrapper;
import mekanism.common.item.ItemGaugeDropper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;

import java.util.List;
import java.util.Optional;

public class ItemVariableGaugeDropper extends ItemGaugeDropper implements IConfigurableItem {
    public ItemVariableGaugeDropper(Properties properties) {
        super(properties);
    }

    @Override
    protected void gatherCapabilities(List<ItemCapabilityWrapper.ItemCapability> capabilities, ItemStack stack, CompoundTag nbt) {
        capabilities.add(VariableGaugeDropperContentsHandler.create());
    }

    public void setStackSize(ItemStack stack,int value) {
        FluidUtil.getFluidHandler(stack).ifPresent(cap -> {
            if (cap instanceof VariableGaugeDropperContentsHandler) {
                ((VariableGaugeDropperContentsHandler) cap).setStackSize(value);
            }
        });
    }
}
