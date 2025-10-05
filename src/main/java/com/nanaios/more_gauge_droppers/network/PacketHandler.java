package com.nanaios.more_gauge_droppers.network;

import com.nanaios.more_gauge_droppers.MoreGaugeDroppers;
import com.nanaios.more_gauge_droppers.network.to_server.PacketVariableGaugeData;
import com.nanaios.more_gauge_droppers.network.to_server.PacketOpenGui;
import mekanism.common.network.BasePacketHandler;
import net.minecraftforge.network.simple.SimpleChannel;

public class PacketHandler extends BasePacketHandler {
    private final SimpleChannel netHandler = createChannel(MoreGaugeDroppers.rl(MoreGaugeDroppers.MODID), MoreGaugeDroppers.instance.versionNumber);

    @Override
    protected SimpleChannel getChannel() {
        return netHandler;
    }

    @Override
    public void initialize() {
        registerClientToServer(PacketOpenGui.class, PacketOpenGui::decode);
        registerClientToServer(PacketVariableGaugeData.class, PacketVariableGaugeData::decode);
    }
}
