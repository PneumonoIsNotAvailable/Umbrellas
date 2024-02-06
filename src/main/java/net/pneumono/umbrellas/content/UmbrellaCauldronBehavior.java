package net.pneumono.umbrellas.content;

import net.minecraft.block.LeveledCauldronBlock;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;

public interface UmbrellaCauldronBehavior {
    CauldronBehavior CLEAN_PRIDE_OR_KOFI_UMBRELLA = (state, world, pos, player, hand, stack) -> {
        Item item = stack.getItem();
        if (!(item instanceof UmbrellaItem)) {
            return ActionResult.PASS;
        } else {
            if (!world.isClient) {
                ItemStack itemStack = new ItemStack(UmbrellasRegistry.UMBRELLA);
                if (stack.hasNbt() && stack.getNbt() != null) {
                    itemStack.setNbt(stack.getNbt().copy());
                }

                player.setStackInHand(hand, itemStack);
                player.incrementStat(UmbrellasRegistry.CLEAN_UMBRELLA);
                LeveledCauldronBlock.decrementFluidLevel(state, world, pos);
            }

            return ActionResult.success(world.isClient);
        }
    };

    static void registerCauldronBehavior() {
        for (Item item : UmbrellasRegistry.UMBRELLAS) {
            if (item != UmbrellasRegistry.UMBRELLA) {
                CauldronBehavior.WATER_CAULDRON_BEHAVIOR.put(item, CLEAN_PRIDE_OR_KOFI_UMBRELLA);
            }
        }
        CauldronBehavior.WATER_CAULDRON_BEHAVIOR.put(UmbrellasRegistry.UMBRELLA, CauldronBehavior.CLEAN_DYEABLE_ITEM);
    }
}
