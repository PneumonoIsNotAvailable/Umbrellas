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
        return enchantment == ModContent.GLIDING;
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return false;
    }

    public static boolean isUnderUmbrella(World world, BlockPos pos) {
        Box box = new Box(new BlockPos(pos.getX() - 2, pos.getY(), pos.getZ() - 2), new BlockPos(pos.getX() + 2, pos.getY() + 10, pos.getZ() + 2));
        List<Entity> targettedEntities = world.getOtherEntities(null, box);
        for (Entity temp : targettedEntities) {
            if (temp instanceof LivingEntity friend && horizontalDistanceBetween(friend.getBlockPos(), pos) <= 2 && (friend.getMainHandStack().getItem() instanceof UmbrellaItem || friend.getOffHandStack().getItem() instanceof UmbrellaItem)) {
                return true;
            }
        }
        return false;
    }

    private static double horizontalDistanceBetween(BlockPos pos1, BlockPos pos2) {
        double xDifference = Math.abs(pos1.getX() - pos2.getX());
        double zDifference = Math.abs(pos1.getZ() - pos2.getZ());
        return Math.sqrt((xDifference * xDifference) + (zDifference * zDifference));
    }
}