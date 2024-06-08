package net.pneumono.umbrellas.util;

import net.minecraft.item.ItemStack;
import net.pneumono.pneumonocore.util.PneumonoEnchantmentHelper;
import net.pneumono.umbrellas.content.UmbrellaItem;
import net.pneumono.umbrellas.content.UmbrellasRegistry;

public enum GlidingAbilityType {
    ALWAYS,
    GLIDING_ONLY,
    @SuppressWarnings("unused")
    NEVER;

    public boolean shouldHaveAbility(ItemStack stack) {
        if (stack.getItem() instanceof UmbrellaItem) {
            if (this == ALWAYS) {
                return true;
            } else {
                return this == GLIDING_ONLY && PneumonoEnchantmentHelper.hasEnchantment(UmbrellasRegistry.GLIDING, stack);
            }
        } else {
            return false;
        }
    }
}
