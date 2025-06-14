package net.pneumono.umbrellas.content.item;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.pneumono.umbrellas.Umbrellas;
import net.pneumono.umbrellas.registry.UmbrellasItems;

public class UmbrellaItem extends Item {
    public UmbrellaItem(Settings settings) {
        super(settings);
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return stack.hasEnchantments() && Umbrellas.ENCHANTMENT_GLINT.getValue();
    }

    @Override
    public boolean allowComponentsUpdateAnimation(PlayerEntity player, Hand hand, ItemStack oldStack, ItemStack newStack) {
        return false;
    }

    public static void damageUmbrella(ItemStack stack, World world, LivingEntity entity, EquipmentSlot slot) {
        if (Umbrellas.DURABILITY.getValue()) {
            long time = world.getTime();
            long lastDamaged = stack.getOrDefault(UmbrellasItems.LAST_DAMAGE, time);
            if (lastDamaged + 20 <= time) {
                stack.set(UmbrellasItems.LAST_DAMAGE, time);
                stack.damage(1, entity, slot);
            }
        }

    }
}