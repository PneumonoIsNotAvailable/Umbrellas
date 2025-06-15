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
    private static ActionResult cleanUmbrella(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, ItemStack stack) {
        UmbrellaPatternsComponent umbrellaPatternsComponent = stack.getOrDefault(UmbrellasDataComponents.UMBRELLA_PATTERNS, UmbrellaPatternsComponent.DEFAULT);
        if (umbrellaPatternsComponent.layers().isEmpty()) {
            return ActionResult.PASS_TO_DEFAULT_BLOCK_ACTION;
        }
        if (!world.isClient) {
            ItemStack itemStack = stack.copyWithCount(1);
            itemStack.set(UmbrellasDataComponents.UMBRELLA_PATTERNS, umbrellaPatternsComponent.withoutTopLayer());
            player.setStackInHand(hand, ItemUsage.exchangeStack(stack, player, itemStack, false));
            player.incrementStat(UmbrellasMisc.CLEAN_UMBRELLA);
            LeveledCauldronBlock.decrementFluidLevel(state, world, pos);
        }

        return ActionResult.SUCCESS;
    }

    public static void registerCauldronBehavior() {
        Item[] washableUmbrellas = new Item[]{
                UmbrellasItems.WHITE_UMBRELLA,
                UmbrellasItems.LIGHT_GRAY_UMBRELLA,
                UmbrellasItems.GRAY_UMBRELLA,
                UmbrellasItems.BLACK_UMBRELLA,
                UmbrellasItems.BROWN_UMBRELLA,
                UmbrellasItems.RED_UMBRELLA,
                UmbrellasItems.ORANGE_UMBRELLA,
                UmbrellasItems.YELLOW_UMBRELLA,
                UmbrellasItems.LIME_UMBRELLA,
                UmbrellasItems.GREEN_UMBRELLA,
                UmbrellasItems.CYAN_UMBRELLA,
                UmbrellasItems.LIGHT_BLUE_UMBRELLA,
                UmbrellasItems.BLUE_UMBRELLA,
                UmbrellasItems.PURPLE_UMBRELLA,
                UmbrellasItems.MAGENTA_UMBRELLA,
                UmbrellasItems.PINK_UMBRELLA
        };
        for (Item item : washableUmbrellas) {
            CauldronBehavior.WATER_CAULDRON_BEHAVIOR.map().put(item, UmbrellaCauldronBehavior::cleanUmbrella);
        }
    }
}
