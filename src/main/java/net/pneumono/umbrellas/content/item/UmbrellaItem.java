package net.pneumono.umbrellas.content.item;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.pneumono.umbrellas.Umbrellas;
import net.pneumono.umbrellas.UmbrellasConfig;
import net.pneumono.umbrellas.registry.UmbrellasDataComponents;

public class UmbrellaItem extends Item {
    public UmbrellaItem(Settings settings) {
        super(settings);
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return super.hasGlint(stack) && UmbrellasConfig.ENCHANTMENT_GLINT.getValue();
    }

    @Override
    public boolean allowComponentsUpdateAnimation(PlayerEntity player, Hand hand, ItemStack oldStack, ItemStack newStack) {
        ItemStack oldWithoutIgnored = oldStack.copy();
        oldWithoutIgnored.remove(UmbrellasDataComponents.LAST_DAMAGE);
        oldWithoutIgnored.remove(DataComponentTypes.DAMAGE);
        ItemStack newWithoutIgnored = oldStack.copy();
        newWithoutIgnored.remove(UmbrellasDataComponents.LAST_DAMAGE);
        newWithoutIgnored.remove(DataComponentTypes.DAMAGE);
        return !ItemStack.areItemsAndComponentsEqual(oldWithoutIgnored, newWithoutIgnored);
    }
}