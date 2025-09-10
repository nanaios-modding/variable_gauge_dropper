package com.nanaios.more_gauge_droppers.capabilities;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;

public class Capabilities {
    public static final Capability<VariableGaugeDropperContentsHandler> VARIABLE_GAUGE_DROPPER_CONTENTS_HANDLER = CapabilityManager.get(new CapabilityToken<>() {});
}
