package com.nanaios.more_gauge_droppers.registries;

import com.nanaios.more_gauge_droppers.MoreGaugeDroppers;
import com.nanaios.more_gauge_droppers.MoreGaugeDroppersLang;
import mekanism.common.registration.MekanismDeferredHolder;
import mekanism.common.registration.impl.CreativeTabDeferredRegister;
import mekanism.common.registries.MekanismCreativeTabs;
import net.minecraft.world.item.CreativeModeTab;

public class MoreGaugeDroppersCreativeTabs {
    public static final CreativeTabDeferredRegister CREATIVE_TABS = new CreativeTabDeferredRegister(MoreGaugeDroppers.MODID);

    public static final MekanismDeferredHolder<CreativeModeTab, CreativeModeTab> TAB;

    static {
        TAB = CREATIVE_TABS.registerMain(MoreGaugeDroppersLang.MoreGaugeDroppers, MoreGaugeDroppersItems.VARIABLE_GAUGE_DROPPER, builder ->
                builder.withTabsBefore(MekanismCreativeTabs.MEKANISM.getKey())
                        .displayItems((displayParameters, output) -> {
                            CreativeTabDeferredRegister.addToDisplay(MoreGaugeDroppersItems.ITEMS, output);
                        })
        );
    }
}
