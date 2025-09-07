package com.nanaios.variable_gauge_dropper;

import mekanism.api.text.ILangEntry;
import net.minecraft.Util;
import org.jetbrains.annotations.NotNull;

public enum VariableGaugeDropperLang implements ILangEntry {
    VARIABLE_GAUGE_DROPPER("constants","mod_name"),
    KEY_CONFIGURABLE("key", "configurable");
    private final String key;

    VariableGaugeDropperLang(String type, String path) {
        this(Util.makeDescriptionId(type, VariableGaugeDropper.rl(path)));
    }

    VariableGaugeDropperLang(String key) {
        this.key = key;
    }

    @Override
    public @NotNull String getTranslationKey() {
        return key;
    }
}
