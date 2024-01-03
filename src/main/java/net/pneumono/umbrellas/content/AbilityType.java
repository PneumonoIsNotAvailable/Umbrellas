package net.pneumono.umbrellas.content;

import net.minecraft.item.ItemStack;
import net.pneumono.pneumonocore.util.PneumonoEnchantmentHelper;

public enum AbilityType {
    ALWAYS,
    GLIDING_ONLY,
    @SuppressWarnings("unused")
    NEVER;

    public boolean shouldHaveAbility(ItemStack stack) {
        if (stack.getItem() instanceof UmbrellaItem) {
            if (this == ALWAYS) {
                return true;
            } else {
                return this == GLIDING_ONLY && PneumonoEnchantmentHelper.hasEnchantment(UmbrellasContent.GLIDING, stack);
            }
        } else {
            return false;
        }
    }
}
