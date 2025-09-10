package com.nanaios.more_gauge_droppers.registries;

import com.nanaios.more_gauge_droppers.MoreGaugeDroppers;
import com.nanaios.more_gauge_droppers.item.ItemRadioactiveGasGaugeDropper;
import com.nanaios.more_gauge_droppers.item.ItemVariableGaugeDropper;
import mekanism.common.registration.impl.ItemDeferredRegister;
import mekanism.common.registration.impl.ItemRegistryObject;

public class MoreGaugeDroppersItems {
    public static final ItemDeferredRegister ITEMS = new ItemDeferredRegister(MoreGaugeDroppers.MODID);
    public static final ItemRegistryObject<ItemVariableGaugeDropper> VARIABLE_GAUGE_DROPPER;
    public static final ItemRegistryObject<ItemRadioactiveGasGaugeDropper> RADIOACTIVE_GAS_GAUGE_DROPPER;

    static {
        VARIABLE_GAUGE_DROPPER = ITEMS.register("variable_gauge_dropper",ItemVariableGaugeDropper::new);
        RADIOACTIVE_GAS_GAUGE_DROPPER = ITEMS.register("radioactive_gas_gauge_dropper",ItemRadioactiveGasGaugeDropper::new);
    }
}
