package com.nanaios.more_gauge_droppers.registries;

import com.nanaios.more_gauge_droppers.MoreGaugeDroppers;
import com.nanaios.more_gauge_droppers.MoreGaugeDroppersLang;
import mekanism.common.registration.impl.CreativeTabDeferredRegister;
import mekanism.common.registration.impl.CreativeTabRegistryObject;
import mekanism.common.registries.MekanismCreativeTabs;

public class MoreGaugeDroppersCreativeTabs {
    public static final CreativeTabDeferredRegister CREATIVE_TABS = new CreativeTabDeferredRegister(MoreGaugeDroppers.MODID);

    public static final CreativeTabRegistryObject TAB;

    static {
        TAB = CREATIVE_TABS.registerMain(MoreGaugeDroppersLang.MORE_GAUGE_DROPPERS, MoreGaugeDroppersItems.VARIABLE_GAUGE_DROPPER, builder ->
                builder.withTabsBefore(MekanismCreativeTabs.MEKANISM.key())
                        .displayItems((displayParameters, output) -> {
                            CreativeTabDeferredRegister.addToDisplay(MoreGaugeDroppersItems.ITEMS, output);
                        })
        );
    }
}
