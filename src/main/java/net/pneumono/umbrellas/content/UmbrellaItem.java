package net.pneumono.umbrellas.content;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ToolItem;
import net.minecraft.item.Vanishable;
import net.pneumono.pneumonocore.enchantment.EnchantableItem;
import net.pneumono.umbrellas.Umbrellas;

import static net.minecraft.item.ToolMaterials.WOOD;

public class UmbrellaItem extends ToolItem implements Vanishable, EnchantableItem {
    public UmbrellaItem(Settings settings) {
        super(WOOD, settings);
    }

    @Override
    public boolean canRepair(ItemStack stack, ItemStack ingredient) {
        return ingredient.isOf(Items.LEATHER);
    }

    @Override
    public boolean isAcceptableEnchantment(Enchantment enchantment) {
        return enchantment == UmbrellasRegistry.GLIDING;
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return stack.hasEnchantments() && Umbrellas.ENCHANTMENT_GLINT.getValue();
    }
}