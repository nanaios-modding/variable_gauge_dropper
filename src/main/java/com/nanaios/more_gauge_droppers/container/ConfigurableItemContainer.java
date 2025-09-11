package com.nanaios.more_gauge_droppers.container;

import com.nanaios.more_gauge_droppers.item.IConfigurableItem;
import com.nanaios.more_gauge_droppers.registries.MoreGaugeDroppersContainerTypes;
import mekanism.common.inventory.container.MekanismContainer;
import mekanism.common.inventory.container.slot.HotBarSlot;
import mekanism.common.inventory.container.slot.OffhandSlot;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ConfigurableItemContainer extends MekanismContainer {
    public ConfigurableItemContainer(int id, Inventory inv) {
        super(MoreGaugeDroppersContainerTypes.CONFIGURABLE_CONTAINER, id, inv);
        addSlotsAndOpen();
    }

    public ConfigurableItemContainer(int id, Inventory inv, FriendlyByteBuf buf) {
        this(id, inv);
    }

    @Override
    protected void addInventorySlots(@NotNull Inventory inv) {
        for (int slotY = 0; slotY < Inventory.getSelectionSize(); slotY++) {
            addSlot(new HotBarSlot(inv, slotY, 33 + slotY * 18, 47) {
                @Override
                public boolean mayPickup(@NotNull Player player) {
                    return false;
                }

                @Override
                public boolean mayPlace(@NotNull ItemStack stack) {
                    return false;
                }
            });
        }
        addSlot(new OffhandSlot(inv, 40, 8, 47, inv.player) {
            @Override
            public boolean mayPickup(@NotNull Player player) {
                return false;
            }

            @Override
            public boolean mayPlace(@NotNull ItemStack stack) {
                return false;
            }
        });
    }

    public static boolean isConfigurableItem(ItemStack stack) {
        return !stack.isEmpty() && stack.getItem() instanceof IConfigurableItem;
    }

    public static boolean hasConfigurableItem(Player player) {
        for (int slot = 0; slot < Inventory.getSelectionSize(); slot++) {
            if(isConfigurableItem(player.getInventory().items.get(slot))) {
                return true;
            }
        }
        return player.getInventory().offhand.stream().anyMatch(ConfigurableItemContainer::isConfigurableItem);
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        return true;
    }

    @Override
    public boolean canPlayerAccess(@NotNull Player player) {
        return false;
    }
}
