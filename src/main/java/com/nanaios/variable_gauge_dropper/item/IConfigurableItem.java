package com.nanaios.variable_gauge_dropper.item;

import net.minecraft.world.item.ItemStack;

public interface IConfigurableItem {
    void setStackSize(ItemStack stack, int value);
}
