package net.pneumono.umbrellas.registry;

import net.minecraft.core.BlockPos;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.pneumono.umbrellas.content.item.component.UmbrellaPatternsComponent;
import net.pneumono.umbrellas.util.data.VersionedComponents;

//? if >=1.21.6 || <1.21 {
import net.minecraft.world.InteractionResult;
//?} else {
/*import net.minecraft.world.ItemInteractionResult;
*///?}

public class UmbrellaCauldronInteraction {
    private static /*? if >=1.21.6 || <1.21 {*/InteractionResult/*?} else {*//*ItemInteractionResult*//*?}*/ cleanUmbrella(
            BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, ItemStack stack
    ) {
        UmbrellaPatternsComponent umbrellaPatternsComponent = VersionedComponents.getOrDefault(stack, UmbrellasDataComponents.UMBRELLA_PATTERNS, UmbrellaPatternsComponent.DEFAULT);
        if (umbrellaPatternsComponent.layers().isEmpty()) {
            //? if >=1.21.6 {
            return InteractionResult.TRY_WITH_EMPTY_HAND;
            //?} else if <1.21 {
            /*return InteractionResult.PASS;
            *///?} else {
            /*return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
            *///?}
        }
        if (!level.isClientSide()) {
            ItemStack itemStack = stack.copyWithCount(1);
            VersionedComponents.set(stack, UmbrellasDataComponents.UMBRELLA_PATTERNS, umbrellaPatternsComponent.withoutTopLayer());
            player.setItemInHand(hand, ItemUtils.createFilledResult(stack, player, itemStack, false));
            player.awardStat(UmbrellasMisc.CLEAN_UMBRELLA);
            LayeredCauldronBlock.lowerFillLevel(state, level, pos);
        }

        //? if >=1.21.6 || <1.21 {
        return InteractionResult.SUCCESS;
         //?} else {
        /*return ItemInteractionResult.SUCCESS;
        *///?}
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
            CauldronInteraction.WATER/*? if >=1.21 {*/.map()/*?}*/.put(item, UmbrellaCauldronInteraction::cleanUmbrella);
        }
    }
}
