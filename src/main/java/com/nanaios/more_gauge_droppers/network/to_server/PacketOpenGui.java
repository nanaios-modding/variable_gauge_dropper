package com.nanaios.more_gauge_droppers.network.to_server;

import com.nanaios.more_gauge_droppers.MoreGaugeDroppers;
import com.nanaios.more_gauge_droppers.MoreGaugeDroppersLang;
import com.nanaios.more_gauge_droppers.container.ConfigurableItemContainer;
import com.nanaios.more_gauge_droppers.registries.MoreGaugeDroppersContainerTypes;
import io.netty.buffer.ByteBuf;
import mekanism.api.functions.ConstantPredicates;
import mekanism.common.inventory.container.ContainerProvider;
import mekanism.common.network.IMekanismPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.ByIdMap;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.Supplier;

public record PacketOpenGui(GuiType guiType) implements IMekanismPacket {

    public static final CustomPacketPayload.Type<PacketOpenGui> TYPE = new CustomPacketPayload.Type<>(MoreGaugeDroppers.rl("open_gui"));
    public static final StreamCodec<ByteBuf, PacketOpenGui> STREAM_CODEC = GuiType.STREAM_CODEC.map(
            PacketOpenGui::new, PacketOpenGui::guiType
    );

    @NotNull
    @Override
    public CustomPacketPayload.Type<PacketOpenGui> type() {
        return TYPE;
    }

    @Override
    public void handle(IPayloadContext context) {
        Player player = context.player();
        if (guiType.shouldOpenForPlayer.test(player)) {
            player.openMenu(guiType.containerSupplier.get());
        }
    }

    public enum GuiType {
        CONFIGURABLE(() -> new ContainerProvider(MoreGaugeDroppersLang.KEY_CONFIGURABLE,
                (id, inv, player) -> MoreGaugeDroppersContainerTypes.CONFIGURABLE_CONTAINER.create(id, inv)),
                ConfigurableItemContainer::hasConfigurableItem);

        public static final IntFunction<GuiType> BY_ID = ByIdMap.continuous(GuiType::ordinal, values(), ByIdMap.OutOfBoundsStrategy.WRAP);
        public static final StreamCodec<ByteBuf, GuiType> STREAM_CODEC = ByteBufCodecs.idMapper(BY_ID, GuiType::ordinal);

        private final Supplier<MenuProvider> containerSupplier;
        private final Predicate<Player> shouldOpenForPlayer;

        GuiType(Supplier<MenuProvider> containerSupplier) {
            this(containerSupplier, ConstantPredicates.alwaysTrue());
        }

        GuiType(Supplier<MenuProvider> containerSupplier, Predicate<Player> shouldOpenForPlayer) {
            this.containerSupplier = containerSupplier;
            this.shouldOpenForPlayer = shouldOpenForPlayer;
        }
    }
}