package com.nanaios.variable_gauge_dropper.network.to_server;

import com.nanaios.variable_gauge_dropper.VariableGaugeDropper;
import com.nanaios.variable_gauge_dropper.item.IConfigurableItem;
import mekanism.common.network.IMekanismPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

public class PacketConfigurableValue implements IMekanismPacket {
    private final int slot;   // プレイヤーのインベントリ内のスロット番号
    private final int value;

    public PacketConfigurableValue(int slot, int value) {
        this.slot = slot;
        this.value = value;
    }

    @Override
    public void handle(NetworkEvent.Context context) {
        Player player = context.getSender();
        if (player != null) {
            ItemStack stack = player.getInventory().getItem(slot);
            if (!stack.isEmpty()) {
                if (stack.getItem() instanceof IConfigurableItem configurableItem) {
                    VariableGaugeDropper.LOGGER.info("Setting configurable value to {} for slot {}", value, slot);
                    configurableItem.setStackSize(stack,value);
                }
            }
        }
    }

    @Override
    public void encode(FriendlyByteBuf buffer) {
        buffer.writeInt(slot);
        buffer.writeInt(value);
    }

    public static PacketConfigurableValue decode(FriendlyByteBuf buffer) {
        return new PacketConfigurableValue(buffer.readInt(),buffer.readInt());
    }
}
