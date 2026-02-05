package net.pneumono.umbrellas.content.enchantment;

//? if <1.21 {
/*import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.pneumono.umbrellas.content.item.UmbrellaItem;

public abstract class UmbrellaEnchantment extends Enchantment {
    protected UmbrellaEnchantment(Rarity rarity) {
        super(rarity, EnchantmentCategory.VANISHABLE, new EquipmentSlot[]{EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND});
    }

    @Override
    public boolean canEnchant(ItemStack itemStack) {
        return canEnchant(itemStack.getItem());
    }

    public boolean canEnchant(Item item) {
        return item instanceof UmbrellaItem;
    }
}
*///?}