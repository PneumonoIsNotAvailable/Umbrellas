package net.pneumono.umbrellas.mixin;

//? if <1.21 {
/*import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.pneumono.umbrellas.content.enchantment.UmbrellaEnchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EnchantmentHelper.class)
public abstract class EnchantmentHelperMixin {
    @WrapOperation(
            method = "getAvailableEnchantmentResults",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/enchantment/EnchantmentCategory;canEnchant(Lnet/minecraft/world/item/Item;)Z"
            )
    )
    private static boolean canEnchantProperly(EnchantmentCategory instance, Item item, Operation<Boolean> original, @Local Enchantment enchantment) {
        if (enchantment instanceof UmbrellaEnchantment umbrellaEnchantment) {
            return umbrellaEnchantment.canEnchant(item);
        } else {
            return original.call(instance, item);
        }
    }
}
*///?}
