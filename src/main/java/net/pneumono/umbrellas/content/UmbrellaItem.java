package net.pneumono.umbrellas.content;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ToolItem;
import net.minecraft.item.Vanishable;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.pneumono.pneumonocore.enchantment.EnchantableItem;
import net.pneumono.umbrellas.Umbrellas;

import java.util.List;

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
        List<Enchantment> acceptable = List.of(
                UmbrellasRegistry.GLIDING,
                UmbrellasRegistry.WIND_CATCHING,
                Enchantments.UNBREAKING,
                Enchantments.MENDING
        );
        return acceptable.contains(enchantment);
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return stack.hasEnchantments() && Umbrellas.ENCHANTMENT_GLINT.getValue();
    }

    @Override
    public boolean allowNbtUpdateAnimation(PlayerEntity player, Hand hand, ItemStack oldStack, ItemStack newStack) {
        return false;
    }

    public static void damageUmbrella(ItemStack stack, World world, LivingEntity entity) {
        if (Umbrellas.DURABILITY.getValue()) {
            NbtCompound compound = stack.getNbt();
            long time = world.getTime();
            if (stack.getItem() instanceof UmbrellaItem) {
                boolean hasLastDamaged = compound != null && compound.contains("lastDamaged", NbtElement.LONG_TYPE);
                if (!hasLastDamaged || compound.getLong("lastDamaged") + 20 <= time) {
                    stack.getOrCreateNbt().putLong("lastDamaged", time);
                    stack.damage(1, entity, e -> e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND));
                }
            }
        }
    }
}