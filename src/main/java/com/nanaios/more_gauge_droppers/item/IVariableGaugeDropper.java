package com.nanaios.more_gauge_droppers.item;

import com.nanaios.more_gauge_droppers.network.to_server.PacketVariableGaugeData.VariableGaugeDataType;
import net.minecraft.world.item.ItemStack;

public interface IVariableGaugeDropper {
    void setData(ItemStack stack,VariableGaugeDataType type,int value);

}
