package net.pneumono.umbrellas.util;

import net.minecraft.block.LeveledCauldronBlock;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.ActionResult;
import net.pneumono.umbrellas.content.UmbrellasRegistry;

public interface UmbrellaCauldronBehavior {
    CauldronBehavior CLEAN_PATTERNABLE_UMBRELLA = (state, world, pos, player, hand, stack) -> {
        NbtCompound compound = stack.getNbt();
        if (compound == null || !compound.contains("Patterns", NbtElement.LIST_TYPE) || compound.getList("Patterns", NbtElement.COMPOUND_TYPE).size() == 0) {
            return ActionResult.PASS;
        }
        if (!world.isClient) {
            ItemStack itemStack = stack.copyWithCount(1);

            NbtCompound nbtCompound = itemStack.getNbt();
            if (nbtCompound != null && nbtCompound.contains("Patterns", NbtElement.LIST_TYPE)) {
                NbtList nbtList = nbtCompound.getList("Patterns", NbtElement.COMPOUND_TYPE);
                if (!nbtList.isEmpty()) {

                    nbtList.remove(nbtList.size() - 1);
                    if (nbtList.isEmpty()) {
                        nbtCompound.remove("Patterns");
                    }
                }
            }

            if (!player.getAbilities().creativeMode) {
                stack.decrement(1);
            }
            if (stack.isEmpty()) {
                player.setStackInHand(hand, itemStack);
            } else if (player.getInventory().insertStack(itemStack)) {
                player.playerScreenHandler.syncState();
            } else {
                player.dropItem(itemStack, false);
            }
            player.incrementStat(UmbrellasRegistry.CLEAN_UMBRELLA);
            LeveledCauldronBlock.decrementFluidLevel(state, world, pos);
        }
        return ActionResult.success(world.isClient);
    };

    static void registerCauldronBehavior() {
        for (Item item : UmbrellasRegistry.PATTERNABLE_UMBRELLAS) {
            CauldronBehavior.WATER_CAULDRON_BEHAVIOR.put(item, CLEAN_PATTERNABLE_UMBRELLA);
        }
        CauldronBehavior.WATER_CAULDRON_BEHAVIOR.put(UmbrellasRegistry.UMBRELLA, CauldronBehavior.CLEAN_DYEABLE_ITEM);
    }
}
