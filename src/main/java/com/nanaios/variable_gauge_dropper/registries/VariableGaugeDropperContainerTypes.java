package com.nanaios.variable_gauge_dropper.registries;

import com.nanaios.variable_gauge_dropper.VariableGaugeDropper;
import com.nanaios.variable_gauge_dropper.container.ConfigurableItemContainer;
import mekanism.common.registration.impl.ContainerTypeDeferredRegister;
import mekanism.common.registration.impl.ContainerTypeRegistryObject;

public class VariableGaugeDropperContainerTypes {
    public static final ContainerTypeDeferredRegister CONTAINER_TYPES = new ContainerTypeDeferredRegister(VariableGaugeDropper.MODID);
    public static final ContainerTypeRegistryObject<ConfigurableItemContainer> CONFIGURABLE_CONTAINER = CONTAINER_TYPES.register("configurable_container", ConfigurableItemContainer::new);
}
