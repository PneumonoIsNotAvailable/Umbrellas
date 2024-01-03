package net.pneumono.umbrellas.content;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ToolItem;
import net.minecraft.item.Vanishable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import net.pneumono.pneumonocore.enchantment.EnchantableItem;
import net.pneumono.pneumonocore.util.PneumonoMathHelper;
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
        return enchantment == UmbrellasContent.GLIDING;
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return Umbrellas.ENCHANTMENT_GLINT.getValue();
    }

    public static boolean isUnderUmbrella(World world, BlockPos pos) {
        Box box = new Box(new BlockPos(pos.getX() - 2, pos.getY(), pos.getZ() - 2), new BlockPos(pos.getX() + 2, pos.getY() + 10, pos.getZ() + 2));
        for (Entity temp : world.getOtherEntities(null, box)) {
            if (temp instanceof LivingEntity friend && PneumonoMathHelper.horizontalDistanceBetween(friend.getBlockPos(), pos) <= 2 && (friend.getMainHandStack().getItem() instanceof UmbrellaItem || friend.getOffHandStack().getItem() instanceof UmbrellaItem)) {
                return true;
            }
        }
        return false;
    }
}