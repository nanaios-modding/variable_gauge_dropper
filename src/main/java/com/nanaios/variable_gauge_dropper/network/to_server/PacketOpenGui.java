package com.nanaios.variable_gauge_dropper.network.to_server;

import com.nanaios.variable_gauge_dropper.VariableGaugeDropperLang;
import com.nanaios.variable_gauge_dropper.container.ConfigurableItemContainer;
import com.nanaios.variable_gauge_dropper.registries.VariableGaugeDropperContainerTypes;
import mekanism.api.functions.ConstantPredicates;
import mekanism.common.inventory.container.ContainerProvider;
import mekanism.common.network.IMekanismPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkHooks;

import java.util.function.Predicate;
import java.util.function.Supplier;

public class PacketOpenGui implements IMekanismPacket {

    private final GuiType type;

    public PacketOpenGui(GuiType type) {
        this.type = type;
    }

    @Override
    public void handle(NetworkEvent.Context context) {
        ServerPlayer player = context.getSender();
        if (player != null && type.shouldOpenForPlayer.test(player)) {
            NetworkHooks.openScreen(player, type.containerSupplier.get());
        }
    }

    @Override
    public void encode(FriendlyByteBuf buffer) {
        buffer.writeEnum(type);
    }

    public static PacketOpenGui decode(FriendlyByteBuf buffer) {
        return new PacketOpenGui(buffer.readEnum(GuiType.class));
    }

    public enum GuiType {
        MODULE_TWEAKER(() -> new ContainerProvider(VariableGaugeDropperLang.KEY_CONFIGURABLE, (id, inv, player) -> VariableGaugeDropperContainerTypes.CONFIGURABLE_CONTAINER.get().create(id, inv)),
                ConfigurableItemContainer::hasConfigurableItem);

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