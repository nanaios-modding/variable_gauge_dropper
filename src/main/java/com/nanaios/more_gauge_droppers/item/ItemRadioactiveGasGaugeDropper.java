package com.nanaios.more_gauge_droppers.item;

import com.nanaios.more_gauge_droppers.MoreGaugeDroppersLang;
import com.nanaios.more_gauge_droppers.capabilities.RadioactiveGasGaugeDropperContentsHandler;
import mekanism.api.Coord4D;
import mekanism.api.chemical.IChemicalHandler;
import mekanism.api.chemical.gas.Gas;
import mekanism.api.chemical.gas.GasStack;
import mekanism.api.chemical.gas.attribute.GasAttributes.Radiation;
import mekanism.api.math.MathUtils;
import mekanism.api.text.EnumColor;
import mekanism.client.key.MekKeyHandler;
import mekanism.client.key.MekanismKeyHandler;
import mekanism.common.MekanismLang;
import mekanism.common.capabilities.Capabilities;
import mekanism.common.capabilities.ItemCapabilityWrapper;
import mekanism.common.item.ItemGaugeDropper;
import mekanism.common.lib.radiation.RadiationManager;
import mekanism.common.util.ChemicalUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

import static mekanism.common.util.StorageUtils.getRatio;

public class ItemRadioactiveGasGaugeDropper extends ItemGaugeDropper {
    public ItemRadioactiveGasGaugeDropper(Properties properties) {
        super(properties);
    }

    @Override
    public void inventoryTick(@NotNull ItemStack stack, @NotNull Level level, @NotNull Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, level, entity, slot, selected);
        if (!level.isClientSide) {
            if(entity instanceof LivingEntity livingEntity) {
                Optional<? extends IChemicalHandler<Gas, GasStack>> cap = stack.getCapability(ChemicalUtil.getCapabilityForChemical(GasStack.EMPTY)).resolve();
                cap.ifPresent(handler -> forceRadiate(livingEntity,calcMagnitude(handler) / 10000d));
            }
        }
    }

    private void forceRadiate(LivingEntity entity, double magnitude) {
        entity.getCapability(Capabilities.RADIATION_ENTITY).ifPresent(c -> c.radiate(magnitude));
    }

    @Override
    public @NotNull Rarity getRarity(@NotNull ItemStack stack) {
        return Rarity.RARE;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, Level world, @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
        if (MekKeyHandler.isKeyPressed(MekanismKeyHandler.detailsKey)) {
            tooltip.add(MoreGaugeDroppersLang.DESCRIPTION_RADIOACTIVE_GAS_GAUGE_DROPPER.translate());
        } else {
            super.appendHoverText(stack, world, tooltip, flag);

            stack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).ifPresent(handler -> {
                if(handler instanceof RadioactiveGasGaugeDropperContentsHandler radioactiveGasGaugeDropperContentsHandler) {
                    radioactiveGasGaugeDropperContentsHandler.setRadioactiveTooltip(tooltip);
                }
            });

            tooltip.add(MekanismLang.HOLD_FOR_DETAILS.translateColored(EnumColor.GRAY, EnumColor.INDIGO, MekanismKeyHandler.detailsKey.getTranslatedKeyMessage()));
        }
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level world, Player player, @NotNull InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (player.isShiftKeyDown()) {
            if (!world.isClientSide) {
                clearChemicalTanks(stack, player);
            }
            return InteractionResultHolder.sidedSuccess(stack, world.isClientSide);
        }
        return InteractionResultHolder.pass(stack);
    }

    private void clearChemicalTanks(ItemStack stack,Player player) {
        Optional<IChemicalHandler<Gas, GasStack>> cap = stack.getCapability(ChemicalUtil.getCapabilityForChemical(GasStack.EMPTY)).resolve();
        if (cap.isPresent()) {
            IChemicalHandler<Gas, GasStack> handler = cap.get();
            for (int tank = 0; tank < handler.getTanks(); tank++) {
                handler.setChemicalInTank(tank, GasStack.EMPTY);
            }

            Coord4D coord4d = new Coord4D(player);
            RadiationManager.get().radiate(coord4d, calcMagnitude(handler));
        }
    }

    private double calcMagnitude(IChemicalHandler<Gas, GasStack> handler) {
        long amount = 0;
        double radioactivity = 0.0;
        Radiation radiation = handler.getChemicalInTank(0).getType().get(Radiation.class);
        if(radiation !=null) {
            radioactivity = radiation.getRadioactivity();
        }
        for (int tank = 0; tank < handler.getTanks(); tank++) {
            amount += handler.getChemicalInTank(tank).getAmount();
        }
        return amount * radioactivity;
    }

    @Override
    protected void gatherCapabilities(List<ItemCapabilityWrapper.ItemCapability> capabilities, ItemStack stack, CompoundTag nbt) {
        capabilities.add(RadioactiveGasGaugeDropperContentsHandler.create());
    }

    @Override
    public int getBarWidth(@NotNull ItemStack stack) {
        return MathUtils.clampToInt(Math.round(13.0F - 13.0F * getDurabilityForDisplay(stack)));
    }

    private double getDurabilityForDisplay(ItemStack stack) {
        double bestRatio = 0;
        bestRatio = calculateRatio(stack, bestRatio);
        return 1 - bestRatio;
    }

    private static double calculateRatio(ItemStack stack, double bestRatio) {
        Optional<? extends IChemicalHandler<?, ?>> cap = stack.getCapability(Capabilities.GAS_HANDLER).resolve();
        if (cap.isPresent()) {
            IChemicalHandler<?, ?> handler = cap.get();
            for (int tank = 0, tanks = handler.getTanks(); tank < tanks; tank++) {
                bestRatio = Math.max(bestRatio, getRatio(handler.getChemicalInTank(tank).getAmount(), handler.getTankCapacity(tank)));
            }
        }
        return bestRatio;
    }
}
