package com.nanaios.variable_gauge_dropper.registries;

import com.nanaios.variable_gauge_dropper.VariableGaugeDropper;
import com.nanaios.variable_gauge_dropper.VariableGaugeDropperLang;
import mekanism.common.registration.impl.CreativeTabDeferredRegister;
import mekanism.common.registration.impl.CreativeTabRegistryObject;
import mekanism.common.registries.MekanismCreativeTabs;

public class VariableGaugeDropperCreativeTabs {
    public static final CreativeTabDeferredRegister CREATIVE_TABS = new CreativeTabDeferredRegister(VariableGaugeDropper.MODID);

    public static final CreativeTabRegistryObject TAB;

    static {
        TAB = CREATIVE_TABS.registerMain(VariableGaugeDropperLang.VARIABLE_GAUGE_DROPPER,VariableGaugeDropperItems.VARIABLE_GAUGE_DROPPER,builder ->
                builder.withTabsBefore(MekanismCreativeTabs.MEKANISM.key())
                        .displayItems((displayParameters, output) -> {
                            CreativeTabDeferredRegister.addToDisplay(VariableGaugeDropperItems.ITEMS, output);
                        })
        );
    }
}
