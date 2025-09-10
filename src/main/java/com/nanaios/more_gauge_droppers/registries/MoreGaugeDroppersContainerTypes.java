package com.nanaios.more_gauge_droppers.registries;

import com.nanaios.more_gauge_droppers.MoreGaugeDroppers;
import com.nanaios.more_gauge_droppers.container.ConfigurableItemContainer;
import mekanism.common.registration.impl.ContainerTypeDeferredRegister;
import mekanism.common.registration.impl.ContainerTypeRegistryObject;

public class MoreGaugeDroppersContainerTypes {
    public static final ContainerTypeDeferredRegister CONTAINER_TYPES = new ContainerTypeDeferredRegister(MoreGaugeDroppers.MODID);
    public static final ContainerTypeRegistryObject<ConfigurableItemContainer> CONFIGURABLE_CONTAINER = CONTAINER_TYPES.register("configurable_container", ConfigurableItemContainer::new);
}
