package com.nanaios.more_gauge_droppers.capabilities;
import org.jetbrains.annotations.Nullable;
import mekanism.api.AutomationType;
import mekanism.api.IContentsListener;
import mekanism.api.chemical.ChemicalTankBuilder;
import mekanism.api.chemical.attribute.ChemicalAttributeValidator;
import mekanism.api.chemical.gas.Gas;
import mekanism.api.chemical.gas.GasStack;
import mekanism.api.chemical.gas.IGasHandler;
import mekanism.api.chemical.gas.IGasTank;
import mekanism.api.chemical.gas.attribute.GasAttributes;
import mekanism.common.capabilities.chemical.variable.VariableCapacityChemicalTank;

import java.util.function.LongSupplier;

public class RateLimitRadioactiveTank extends VariableCapacityChemicalTank<Gas, GasStack> implements IGasHandler, IGasTank {
    private final LongSupplier rate;

    private static final ChemicalAttributeValidator ATTRIBUTE_VALIDATOR = ChemicalAttributeValidator.createStrict(GasAttributes.Radiation.class);

    public RateLimitRadioactiveTank(LongSupplier rate, LongSupplier capacity, @Nullable IContentsListener listener) {
        super(capacity, ChemicalTankBuilder.GAS.alwaysTrueBi, ChemicalTankBuilder.GAS.alwaysTrueBi,
                ChemicalTankBuilder.GAS.alwaysTrue, ATTRIBUTE_VALIDATOR, listener);
        this.rate = rate;
    }

    @Override
    protected long getRate(@Nullable AutomationType automationType) {
        //Allow unknown or manual interaction to bypass rate limit for the item
        return automationType == null || automationType == AutomationType.MANUAL ? super.getRate(automationType) : rate.getAsLong();
    }
}
