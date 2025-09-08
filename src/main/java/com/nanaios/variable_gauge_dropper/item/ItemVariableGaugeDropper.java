package com.nanaios.variable_gauge_dropper.item;

import com.nanaios.variable_gauge_dropper.VariableGaugeDropper;
import com.nanaios.variable_gauge_dropper.capabilities.Capabilities;
import com.nanaios.variable_gauge_dropper.capabilities.VariableGaugeDropperContentsHandler;
import mekanism.common.capabilities.ItemCapabilityWrapper;
import mekanism.common.item.ItemGaugeDropper;
import mekanism.common.tags.MekanismTags;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;

import java.util.List;
import java.util.Optional;

public class ItemVariableGaugeDropper extends ItemGaugeDropper implements IConfigurableItem {
    public static final int MAX_CAPACITY = 64000;
    public static final String NBT_CAPACITY = "capacity";
    public int readCapacityFromNBT(ItemStack stack) {
        CompoundTag nbt = stack.getOrCreateTag();
        if(nbt.contains(NBT_CAPACITY)) {
          return nbt.getInt(NBT_CAPACITY);
        }
        return 16000; // default capacity
    }

    public ItemVariableGaugeDropper(Properties properties) {
        super(properties);
    }

    @Override
    protected void gatherCapabilities(List<ItemCapabilityWrapper.ItemCapability> capabilities, ItemStack stack, CompoundTag nbt) {
        capabilities.add(VariableGaugeDropperContentsHandler.create(readCapacityFromNBT(stack)));
    }

    public void setStackSize(ItemStack stack,int value) {
        CompoundTag nbt = stack.getOrCreateTag();
        nbt.putInt(NBT_CAPACITY, Mth.clamp(0,value,MAX_CAPACITY));
        FluidUtil.getFluidHandler(stack).ifPresent(h -> {
            if(h instanceof VariableGaugeDropperContentsHandler handler) {
                handler.setStackSize(value);
            }
        });
    }
}
