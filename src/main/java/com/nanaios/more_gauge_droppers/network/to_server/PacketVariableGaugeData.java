package com.nanaios.more_gauge_droppers.network.to_server;

import com.nanaios.more_gauge_droppers.item.IVariableGaugeDropper;
import mekanism.common.network.IMekanismPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

public class PacketVariableGaugeData implements IMekanismPacket {
    private final VariableGaugeDataType type;
    private final int slot;// プレイヤーのインベントリ内のスロット番号
    private final int value;

    public PacketVariableGaugeData(VariableGaugeDataType type,int slot, int value) {
        this.type = type;
        this.slot = slot;
        this.value = value;
    }

    @Override
    public void handle(NetworkEvent.Context context) {
        Player player = context.getSender();
        if(player == null) return;

        ItemStack stack = player.getInventory().getItem(slot);
        if (stack.isEmpty()) return;

        if (stack.getItem() instanceof IVariableGaugeDropper iVariableGaugeDropper) {
            iVariableGaugeDropper.setData(stack, type, value);
        }
    }

    @Override
    public void encode(FriendlyByteBuf buffer) {
        buffer.writeEnum(type);
        buffer.writeInt(slot);
        buffer.writeInt(value);
    }

    public static PacketVariableGaugeData decode(FriendlyByteBuf buffer) {
        return new PacketVariableGaugeData(buffer.readEnum(VariableGaugeDataType.class),buffer.readInt(),buffer.readInt());
    }

    public enum VariableGaugeDataType {
        TRANSFER_RATE,
        CAPACITY
    }
}
