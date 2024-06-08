package net.pneumono.umbrellas.util;

import net.minecraft.item.ItemStack;
import net.pneumono.pneumonocore.util.PneumonoEnchantmentHelper;
import net.pneumono.umbrellas.content.UmbrellaItem;
import net.pneumono.umbrellas.content.UmbrellasRegistry;

public enum WindCatchingAbilityType {
    ALWAYS,
    WIND_CATCHING_ONLY,
    @SuppressWarnings("unused")
    NEVER;

    public boolean shouldHaveAbility(ItemStack stack) {
        if (stack.getItem() instanceof UmbrellaItem) {
            if (this == ALWAYS) {
                return true;
            } else {
                return this == WIND_CATCHING_ONLY && PneumonoEnchantmentHelper.hasEnchantment(UmbrellasRegistry.WIND_CATCHING, stack);
            }
        } else {
            return false;
        }
    }
}
