package com.nanaios.more_gauge_droppers.network.to_server;

import com.nanaios.more_gauge_droppers.MoreGaugeDroppers;
import com.nanaios.more_gauge_droppers.item.IConfigurableItem;
import io.netty.buffer.ByteBuf;
import mekanism.common.network.IMekanismPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class PacketConfigurableValue implements IMekanismPacket {
    private final int slot;   // プレイヤーのインベントリ内のスロット番号
    private final int value;

    public static final CustomPacketPayload.Type<PacketConfigurableValue> TYPE = new CustomPacketPayload.Type<>(MoreGaugeDroppers.rl("configurable_value"));
    public static final StreamCodec<ByteBuf, PacketConfigurableValue> STREAM_CODEC = StreamCodec.composite(
            BlockPos.STREAM_CODEC, PacketAddTrusted::pos,
            ByteBufCodecs.stringUtf8(SharedConstants.MAX_PLAYER_NAME_LENGTH), PacketAddTrusted::name,
            PacketAddTrusted::new
    );

    @NotNull
    @Override
    public CustomPacketPayload.Type<PacketAddTrusted> type() {
        return TYPE;
    }


    public PacketConfigurableValue(int slot, int value) {
        this.slot = slot;
        this.value = value;
    }

    @Override
    public void handle(IPayloadContext context) {
        Player player = context.player();
        ItemStack stack = player.getInventory().getItem(slot);
        if (!stack.isEmpty()) {
            if (stack.getItem() instanceof IConfigurableItem configurableItem) {
                configurableItem.setStackSize(stack,value);
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
