package com.nanaios.more_gauge_droppers.capabilities;

import mekanism.api.NBTConstants;
import mekanism.api.chemical.ChemicalTankBuilder;
import mekanism.api.chemical.attribute.ChemicalAttributeValidator;
import mekanism.api.chemical.gas.attribute.GasAttributes;
import mekanism.api.fluid.IExtendedFluidTank;
import mekanism.api.fluid.IMekanismFluidHandler;
import mekanism.common.capabilities.DynamicHandler;
import mekanism.common.capabilities.chemical.dynamic.DynamicChemicalHandler;
import mekanism.common.capabilities.chemical.variable.RateLimitChemicalTank;
import mekanism.common.capabilities.fluid.item.RateLimitFluidHandler;
import mekanism.common.capabilities.merged.GaugeDropperContentsHandler;
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
import java.util.function.IntSupplier;
import java.util.function.LongSupplier;

public class RadioactiveGasGaugeDropperContentsHandler  extends MergedTankContentsHandler<MergedTank> implements IMekanismFluidHandler, IFluidHandlerItem {

    private static final int CAPACITY = 16 * FluidType.BUCKET_VOLUME;
    private static final int TRANSFER_RATE = 256;
    private static final IntSupplier intZeroSupplier = () -> 0;
    private static final LongSupplier longZeroSupplier = () -> 0L;

    private static final ChemicalAttributeValidator ATTRIBUTE_VALIDATOR = ChemicalAttributeValidator.createStrict(GasAttributes.Radiation.class);

    public static RadioactiveGasGaugeDropperContentsHandler create() {
        return new RadioactiveGasGaugeDropperContentsHandler();
    }

    protected final List<IExtendedFluidTank> fluidTanks;

    private RadioactiveGasGaugeDropperContentsHandler() {
        mergedTank = MergedTank.create(
                new RateLimitFluidHandler.RateLimitFluidTank(intZeroSupplier, intZeroSupplier, this),
                new RateLimitChemicalTank.RateLimitGasTank(() -> TRANSFER_RATE, () -> CAPACITY, ChemicalTankBuilder.GAS.alwaysTrueBi, ChemicalTankBuilder.GAS.alwaysTrueBi,
                        ChemicalTankBuilder.GAS.alwaysTrue, ATTRIBUTE_VALIDATOR, gasHandler = new DynamicChemicalHandler.DynamicGasHandler(side -> gasTanks, DynamicHandler.InteractPredicate.ALWAYS_TRUE,
                        DynamicHandler.InteractPredicate.ALWAYS_TRUE, () -> onContentsChanged(NBTConstants.GAS_TANKS, gasTanks))),

                new RateLimitChemicalTank.RateLimitInfusionTank(longZeroSupplier,longZeroSupplier, ChemicalTankBuilder.INFUSION.alwaysTrueBi, ChemicalTankBuilder.INFUSION.alwaysTrueBi,
                        ChemicalTankBuilder.INFUSION.alwaysTrue, infusionHandler = new DynamicChemicalHandler.DynamicInfusionHandler(side -> infusionTanks, DynamicHandler.InteractPredicate.ALWAYS_TRUE,
                        DynamicHandler.InteractPredicate.ALWAYS_TRUE, () -> onContentsChanged(NBTConstants.INFUSION_TANKS, infusionTanks))),
                new RateLimitChemicalTank.RateLimitPigmentTank(longZeroSupplier,longZeroSupplier, ChemicalTankBuilder.PIGMENT.alwaysTrueBi, ChemicalTankBuilder.PIGMENT.alwaysTrueBi,
                        ChemicalTankBuilder.PIGMENT.alwaysTrue, pigmentHandler = new DynamicChemicalHandler.DynamicPigmentHandler(side -> pigmentTanks, DynamicHandler.InteractPredicate.ALWAYS_TRUE,
                        DynamicHandler.InteractPredicate.ALWAYS_TRUE, () -> onContentsChanged(NBTConstants.PIGMENT_TANKS, pigmentTanks))),
                new RateLimitChemicalTank.RateLimitSlurryTank(longZeroSupplier,longZeroSupplier, ChemicalTankBuilder.SLURRY.alwaysTrueBi, ChemicalTankBuilder.SLURRY.alwaysTrueBi,
                        ChemicalTankBuilder.SLURRY.alwaysTrue, slurryHandler = new DynamicChemicalHandler.DynamicSlurryHandler(side -> slurryTanks, DynamicHandler.InteractPredicate.ALWAYS_TRUE,
                        DynamicHandler.InteractPredicate.ALWAYS_TRUE, () -> onContentsChanged(NBTConstants.SLURRY_TANKS, slurryTanks)))
        );
        this.fluidTanks = Collections.singletonList(mergedTank.getFluidTank());
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
