package net.pneumono.umbrellas.registry;

import net.minecraft.block.BlockState;
import net.minecraft.block.LeveledCauldronBlock;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.pneumono.umbrellas.content.item.component.UmbrellaPatternsComponent;

public class UmbrellaCauldronBehavior {
    private static ActionResult cleanBanner(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, ItemStack stack) {
        UmbrellaPatternsComponent umbrellaPatternsComponent = stack.getOrDefault(UmbrellasItems.UMBRELLA_PATTERNS, UmbrellaPatternsComponent.DEFAULT);
        if (umbrellaPatternsComponent.layers().isEmpty()) {
            return ActionResult.PASS_TO_DEFAULT_BLOCK_ACTION;
        } else {
            if (!world.isClient) {
                ItemStack itemStack = stack.copyWithCount(1);
                itemStack.set(UmbrellasItems.UMBRELLA_PATTERNS, umbrellaPatternsComponent.withoutTopLayer());
                player.setStackInHand(hand, ItemUsage.exchangeStack(stack, player, itemStack, false));
                player.incrementStat(UmbrellasMisc.CLEAN_UMBRELLA);
                LeveledCauldronBlock.decrementFluidLevel(state, world, pos);
            }

            return ActionResult.SUCCESS;
        }
    }

    public static void registerCauldronBehavior() {
        for (Item item : UmbrellasItems.PATTERNABLE_UMBRELLAS) {
            CauldronBehavior.WATER_CAULDRON_BEHAVIOR.map().put(item, UmbrellaCauldronBehavior::cleanBanner);
        }
    }
}
