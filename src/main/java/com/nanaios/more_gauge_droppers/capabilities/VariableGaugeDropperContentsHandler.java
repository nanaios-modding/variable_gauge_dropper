package com.nanaios.more_gauge_droppers.capabilities;

import mekanism.api.Action;
import mekanism.api.NBTConstants;
import mekanism.api.chemical.ChemicalTankBuilder;
import mekanism.api.chemical.gas.IGasTank;
import mekanism.api.chemical.infuse.IInfusionTank;
import mekanism.api.chemical.pigment.IPigmentTank;
import mekanism.api.chemical.slurry.ISlurryTank;
import mekanism.api.fluid.IExtendedFluidTank;
import mekanism.api.fluid.IMekanismFluidHandler;
import mekanism.common.capabilities.DynamicHandler;
import mekanism.common.capabilities.chemical.dynamic.DynamicChemicalHandler;
import mekanism.common.capabilities.chemical.variable.RateLimitChemicalTank;
import mekanism.common.capabilities.fluid.item.RateLimitFluidHandler;
import mekanism.common.capabilities.merged.MergedTank;
import mekanism.common.capabilities.merged.MergedTankContentsHandler;
import mekanism.common.capabilities.resolver.BasicCapabilityResolver;
import mekanism.common.capabilities.resolver.ICapabilityResolver;
import mekanism.common.util.ItemDataUtils;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

public class VariableGaugeDropperContentsHandler extends MergedTankContentsHandler<MergedTank> implements IMekanismFluidHandler, IFluidHandlerItem {

    private final int capacity;

    private final IExtendedFluidTank fluidTank;
    private final IGasTank gasTank;
    private final IInfusionTank infusionTank;
    private final IPigmentTank pigmentTank;
    private final ISlurryTank slurryTank;

    public static VariableGaugeDropperContentsHandler create(int capacity, int transferRate) {
        return new VariableGaugeDropperContentsHandler(capacity, transferRate);
    }

    protected final List<IExtendedFluidTank> fluidTanks;

    private VariableGaugeDropperContentsHandler(int capacity,int transferRate) {
        this.capacity = capacity;

        fluidTank = new RateLimitFluidHandler.RateLimitFluidTank(() -> transferRate, this::getCapacity, this);
        gasTank = new RateLimitChemicalTank.RateLimitGasTank(() -> transferRate, this::getCapacity, ChemicalTankBuilder.GAS.alwaysTrueBi, ChemicalTankBuilder.GAS.alwaysTrueBi,
                ChemicalTankBuilder.GAS.alwaysTrue, null, gasHandler = new DynamicChemicalHandler.DynamicGasHandler(side -> gasTanks, DynamicHandler.InteractPredicate.ALWAYS_TRUE,
                DynamicHandler.InteractPredicate.ALWAYS_TRUE, () -> onContentsChanged(NBTConstants.GAS_TANKS, gasTanks)));
        infusionTank = new RateLimitChemicalTank.RateLimitInfusionTank(() -> transferRate, this::getCapacity, ChemicalTankBuilder.INFUSION.alwaysTrueBi, ChemicalTankBuilder.INFUSION.alwaysTrueBi,
                ChemicalTankBuilder.INFUSION.alwaysTrue, infusionHandler = new DynamicChemicalHandler.DynamicInfusionHandler(side -> infusionTanks, DynamicHandler.InteractPredicate.ALWAYS_TRUE,
                DynamicHandler.InteractPredicate.ALWAYS_TRUE, () -> onContentsChanged(NBTConstants.INFUSION_TANKS, infusionTanks)));
        pigmentTank = new RateLimitChemicalTank.RateLimitPigmentTank(() -> transferRate, this::getCapacity, ChemicalTankBuilder.PIGMENT.alwaysTrueBi, ChemicalTankBuilder.PIGMENT.alwaysTrueBi,
                ChemicalTankBuilder.PIGMENT.alwaysTrue, pigmentHandler = new DynamicChemicalHandler.DynamicPigmentHandler(side -> pigmentTanks, DynamicHandler.InteractPredicate.ALWAYS_TRUE,
                DynamicHandler.InteractPredicate.ALWAYS_TRUE, () -> onContentsChanged(NBTConstants.PIGMENT_TANKS, pigmentTanks)));
        slurryTank = new RateLimitChemicalTank.RateLimitSlurryTank(() -> transferRate, this::getCapacity, ChemicalTankBuilder.SLURRY.alwaysTrueBi, ChemicalTankBuilder.SLURRY.alwaysTrueBi,
                ChemicalTankBuilder.SLURRY.alwaysTrue, slurryHandler = new DynamicChemicalHandler.DynamicSlurryHandler(side -> slurryTanks, DynamicHandler.InteractPredicate.ALWAYS_TRUE,
                DynamicHandler.InteractPredicate.ALWAYS_TRUE, () -> onContentsChanged(NBTConstants.SLURRY_TANKS, slurryTanks)));

        mergedTank = MergedTank.create(
                fluidTank,
                gasTank,
                infusionTank,
                pigmentTank,
                slurryTank
        );

        this.fluidTanks = Collections.singletonList(mergedTank.getFluidTank());
    }

    public int getCapacity() {
        return capacity;
    }

    public void setStackSize(int capacity) {
        fluidTank.setStackSize(Math.min(fluidTank.getFluidAmount(),capacity), Action.EXECUTE);
        gasTank.setStackSize(Math.min(gasTank.getStored(),capacity), Action.EXECUTE);
        infusionTank.setStackSize(Math.min(infusionTank.getStored(),capacity), Action.EXECUTE);
        pigmentTank.setStackSize(Math.min(pigmentTank.getStored(),capacity), Action.EXECUTE);
        slurryTank.setStackSize(Math.min(slurryTank.getStored(),capacity), Action.EXECUTE);
    }

    @Override
    protected void load() {
        super.load();
        ItemDataUtils.readContainers(getStack(), NBTConstants.FLUID_TANKS, getFluidTanks(null));
    }

    @NotNull
    @Override
    public List<IExtendedFluidTank> getFluidTanks(@Nullable Direction side) {
        return fluidTanks;
    }

    @Override
    public void onContentsChanged() {
        onContentsChanged(NBTConstants.FLUID_TANKS, fluidTanks);
    }

    @NotNull
    @Override
    public ItemStack getContainer() {
        return getStack();
    }

    @Override
    protected void gatherCapabilityResolvers(Consumer<ICapabilityResolver> consumer) {
        super.gatherCapabilityResolvers(consumer);
        consumer.accept(BasicCapabilityResolver.constant(ForgeCapabilities.FLUID_HANDLER_ITEM, this));
    }
}
