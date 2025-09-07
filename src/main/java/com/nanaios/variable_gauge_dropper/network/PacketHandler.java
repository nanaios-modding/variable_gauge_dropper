package com.nanaios.variable_gauge_dropper.network;

import com.nanaios.variable_gauge_dropper.VariableGaugeDropper;
import com.nanaios.variable_gauge_dropper.network.to_server.PacketConfigurableValue;
import com.nanaios.variable_gauge_dropper.network.to_server.PacketOpenGui;
import mekanism.common.network.BasePacketHandler;
import net.minecraftforge.network.simple.SimpleChannel;

public class PacketHandler extends BasePacketHandler {
    private final SimpleChannel netHandler = createChannel(VariableGaugeDropper.rl(VariableGaugeDropper.MODID), VariableGaugeDropper.instance.versionNumber);

    @Override
    protected SimpleChannel getChannel() {
        return netHandler;
    }

    @Override
    public void initialize() {
        registerClientToServer(PacketOpenGui.class, PacketOpenGui::decode);
        registerClientToServer(PacketConfigurableValue.class, PacketConfigurableValue::decode);
    }
}
