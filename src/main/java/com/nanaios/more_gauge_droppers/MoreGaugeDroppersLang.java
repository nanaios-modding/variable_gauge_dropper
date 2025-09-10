package com.nanaios.more_gauge_droppers;

import mekanism.api.text.ILangEntry;
import net.minecraft.Util;
import org.jetbrains.annotations.NotNull;

public enum MoreGaugeDroppersLang implements ILangEntry {
    MoreGaugeDroppers("constants","mod_name"),
    GUI_CAPACITY_TEXT("gui","text.capacity"),
    DESCRIPTION_VARIABLE_GAUGE_DROPPER("description","variable_gauge_dropper"),
    DESCRIPTION_RADIOACTIVE_GAS_GAUGE_DROPPER("description","radioactive_gas_gauge_dropper"),
    KEY_CONFIGURABLE("key", "configurable");
    private final String key;

    MoreGaugeDroppersLang(String type, String path) {
        this(Util.makeDescriptionId(type, com.nanaios.more_gauge_droppers.MoreGaugeDroppers.rl(path)));
    }

    MoreGaugeDroppersLang(String key) {
        this.key = key;
    }

    @Override
    public @NotNull String getTranslationKey() {
        return key;
    }
}
