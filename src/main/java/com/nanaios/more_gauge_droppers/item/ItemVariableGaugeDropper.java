package com.nanaios.more_gauge_droppers.item;

import com.nanaios.more_gauge_droppers.MoreGaugeDroppersLang;
import com.nanaios.more_gauge_droppers.capabilities.VariableGaugeDropperContentsHandler;
import com.nanaios.more_gauge_droppers.client.key.VariableGaugeDropperKeyHandler;
import mekanism.api.text.EnumColor;
import mekanism.client.key.MekKeyHandler;
import mekanism.client.key.MekanismKeyHandler;
import mekanism.common.MekanismLang;
import mekanism.common.capabilities.ItemCapabilityWrapper;
import mekanism.common.item.ItemGaugeDropper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.fluids.FluidUtil;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ItemVariableGaugeDropper extends ItemGaugeDropper implements IConfigurableItem {
    public static final int MAX_CAPACITY = 64000;
    public static final String NBT_CAPACITY = "capacity";
    public int readCapacityFromNBT(ItemStack stack) {
        CompoundTag nbt = stack.getOrCreateTag();
        if(nbt.contains(NBT_CAPACITY)) {
          return nbt.getInt(NBT_CAPACITY);
        }
        return 16000; // default capacity
    }

    public ItemVariableGaugeDropper(Properties properties) {
        super(properties);
    }

    @Override
    protected void gatherCapabilities(List<ItemCapabilityWrapper.ItemCapability> capabilities, ItemStack stack, CompoundTag nbt) {
        capabilities.add(VariableGaugeDropperContentsHandler.create(readCapacityFromNBT(stack)));
    }

    public void setStackSize(ItemStack stack,int value) {
        CompoundTag nbt = stack.getOrCreateTag();
        nbt.putInt(NBT_CAPACITY, Mth.clamp(0,value,MAX_CAPACITY));
        FluidUtil.getFluidHandler(stack).ifPresent(h -> {
            if(h instanceof VariableGaugeDropperContentsHandler handler) {
                handler.setStackSize(value);
            }
        });
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, Level world, @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
        if (MekKeyHandler.isKeyPressed(MekanismKeyHandler.detailsKey)) {
            tooltip.add(MoreGaugeDroppersLang.DESCRIPTION_VARIABLE_GAUGE_DROPPER.translate(VariableGaugeDropperKeyHandler.configurableKey.getTranslatedKeyMessage()));
        } else {
            super.appendHoverText(stack, world, tooltip, flag);
            tooltip.add(MekanismLang.HOLD_FOR_DETAILS.translateColored(EnumColor.GRAY, EnumColor.INDIGO, MekanismKeyHandler.detailsKey.getTranslatedKeyMessage()));
        }
    }

    @Override
    public @NotNull Rarity getRarity(@NotNull ItemStack stack) {
        return Rarity.RARE;
    }
}
