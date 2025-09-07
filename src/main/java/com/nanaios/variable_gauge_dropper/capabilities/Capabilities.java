package com.nanaios.variable_gauge_dropper.capabilities;

import mekanism.api.chemical.gas.IGasHandler;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;

public class Capabilities {
    public static final Capability<VariableGaugeDropperContentsHandler> VARIABLE_GAUGE_DROPPER_CONTENTS_HANDLER = CapabilityManager.get(new CapabilityToken<>() {});
}
