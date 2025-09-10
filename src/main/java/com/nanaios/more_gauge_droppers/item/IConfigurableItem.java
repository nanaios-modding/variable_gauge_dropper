package com.nanaios.more_gauge_droppers.item;

import net.minecraft.world.item.ItemStack;

public interface IConfigurableItem {
    void setStackSize(ItemStack stack, int value);
}
