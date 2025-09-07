package com.nanaios.variable_gauge_dropper.capabilities;

import mekanism.api.Action;
import mekanism.api.NBTConstants;
import mekanism.api.chemical.ChemicalTankBuilder;
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
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

public class VariableGaugeDropperContentsHandler extends MergedTankContentsHandler<MergedTank> implements IMekanismFluidHandler, IFluidHandlerItem {

    private int capacity = 16 * FluidType.BUCKET_VOLUME;
    private int transferRate = 256;

    public static VariableGaugeDropperContentsHandler create() {
        return new VariableGaugeDropperContentsHandler();
    }

    protected final List<IExtendedFluidTank> fluidTanks;

    private VariableGaugeDropperContentsHandler() {
        mergedTank = MergedTank.create(
                new RateLimitFluidHandler.RateLimitFluidTank(() -> transferRate, () -> capacity, this),
                new RateLimitChemicalTank.RateLimitGasTank(
                        () -> transferRate, () -> capacity,
                        ChemicalTankBuilder.GAS.alwaysTrueBi,
                        ChemicalTankBuilder.GAS.alwaysTrueBi,
                        ChemicalTankBuilder.GAS.alwaysTrue,
                        null,
                        gasHandler = new DynamicChemicalHandler.DynamicGasHandler(
                                side -> gasTanks, DynamicHandler.InteractPredicate.ALWAYS_TRUE,
                                DynamicHandler.InteractPredicate.ALWAYS_TRUE,
                                () -> onContentsChanged(NBTConstants.GAS_TANKS, gasTanks)
                        )
                ),

                new RateLimitChemicalTank.RateLimitInfusionTank(() -> transferRate, () -> capacity, ChemicalTankBuilder.INFUSION.alwaysTrueBi, ChemicalTankBuilder.INFUSION.alwaysTrueBi,
                        ChemicalTankBuilder.INFUSION.alwaysTrue, infusionHandler = new DynamicChemicalHandler.DynamicInfusionHandler(side -> infusionTanks, DynamicHandler.InteractPredicate.ALWAYS_TRUE,
                        DynamicHandler.InteractPredicate.ALWAYS_TRUE, () -> onContentsChanged(NBTConstants.INFUSION_TANKS, infusionTanks))),

                new RateLimitChemicalTank.RateLimitPigmentTank(() -> transferRate, () -> capacity, ChemicalTankBuilder.PIGMENT.alwaysTrueBi, ChemicalTankBuilder.PIGMENT.alwaysTrueBi,
                        ChemicalTankBuilder.PIGMENT.alwaysTrue, pigmentHandler = new DynamicChemicalHandler.DynamicPigmentHandler(side -> pigmentTanks, DynamicHandler.InteractPredicate.ALWAYS_TRUE,
                        DynamicHandler.InteractPredicate.ALWAYS_TRUE, () -> onContentsChanged(NBTConstants.PIGMENT_TANKS, pigmentTanks))),

                new RateLimitChemicalTank.RateLimitSlurryTank(() -> transferRate, () -> capacity, ChemicalTankBuilder.SLURRY.alwaysTrueBi, ChemicalTankBuilder.SLURRY.alwaysTrueBi,
                        ChemicalTankBuilder.SLURRY.alwaysTrue, slurryHandler = new DynamicChemicalHandler.DynamicSlurryHandler(side -> slurryTanks, DynamicHandler.InteractPredicate.ALWAYS_TRUE,
                        DynamicHandler.InteractPredicate.ALWAYS_TRUE, () -> onContentsChanged(NBTConstants.SLURRY_TANKS, slurryTanks)))
        );

        this.fluidTanks = Collections.singletonList(mergedTank.getFluidTank());
    }

    public void setStackSize(int value) {
        capacity = value;

        //mergedTank.getFluidTank().setStackSize(value, Action.EXECUTE);
        //mergedTank.getGasTank().setStackSize(value, Action.EXECUTE);
        //mergedTank.getInfusionTank().setStackSize(value, Action.EXECUTE);
        //mergedTank.getPigmentTank().setStackSize(value, Action.EXECUTE);
        //mergedTank.getSlurryTank().setStackSize(value, Action.EXECUTE);

        transferRate = (int) Math.max(1,value*0.016);
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
