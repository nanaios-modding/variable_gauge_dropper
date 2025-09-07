package com.nanaios.variable_gauge_dropper.registries;

import com.nanaios.variable_gauge_dropper.VariableGaugeDropper;
import com.nanaios.variable_gauge_dropper.item.ItemVariableGaugeDropper;
import mekanism.common.registration.impl.ItemDeferredRegister;
import mekanism.common.registration.impl.ItemRegistryObject;

public class VariableGaugeDropperItems {
    public static final ItemDeferredRegister ITEMS = new ItemDeferredRegister(VariableGaugeDropper.MODID);
    public static final ItemRegistryObject<ItemVariableGaugeDropper> VARIABLE_GAUGE_DROPPER;

    static {
        VARIABLE_GAUGE_DROPPER = ITEMS.register("variable_gauge_dropper",ItemVariableGaugeDropper::new);
    }
}
