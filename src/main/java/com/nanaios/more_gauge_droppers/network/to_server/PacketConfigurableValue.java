package com.nanaios.more_gauge_droppers.network.to_server;

import com.nanaios.more_gauge_droppers.item.IConfigurableItem;
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
