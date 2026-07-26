package net.pneumono.umbrellas.compat;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.BlockTags;
import net.pneumono.umbrellas.Umbrellas;
import net.pneumono.umbrellas.content.block.UmbrellaStandBlock;
import net.pneumono.umbrellas.registry.UmbrellasBlocks;
import net.pneumono.umbrellas.registry.UmbrellasItems;

//? if every_compat {
/*import net.mehvahdjukaar.every_compat.api.EveryCompatAPI;
import net.mehvahdjukaar.every_compat.api.PaletteStrategies;
import net.mehvahdjukaar.every_compat.api.SimpleEntrySet;
import net.mehvahdjukaar.every_compat.modules.EveryCompatModule;
import net.mehvahdjukaar.moonlight.api.set.wood.VanillaWoodChildKeys;
import net.mehvahdjukaar.moonlight.api.set.wood.VanillaWoodTypes;
import net.mehvahdjukaar.moonlight.api.set.wood.WoodType;
import net.mehvahdjukaar.moonlight.api.util.Utils;
*///?}

public class EveryCompat {
    public static void register() {
        //? if every_compat {
        /*EveryCompatAPI.registerModule(new Module());
        *///?}
    }

    //? if every_compat {
    /*private static class Module extends EveryCompatModule {
        public Module() {
            super(Umbrellas.MOD_ID, "umb");
            this.addEntry(SimpleEntrySet.builder(
                    WoodType.class,
                    "umbrella_stand",
                    () -> UmbrellasBlocks.OAK_UMBRELLA_STAND,
                    () -> VanillaWoodTypes.OAK,
                    woodType -> new UmbrellaStandBlock(Utils.copyPropertySafe(woodType.planks).noOcclusion())
            )
                    .requiresChildren(VanillaWoodChildKeys.STRIPPED_LOG)
                    .setTab(getTab(UmbrellasItems.CREATIVE_MODE_TAB))
                    .addTile(() -> UmbrellasBlocks.UMBRELLA_STAND_BLOCK_ENTITY)
                    .addTexture(modRes("block/oak_umbrella_stand"), PaletteStrategies.STRIPPED_LOG_SIDE_STANDARD)
                    .addTag(BlockTags.MINEABLE_WITH_AXE, Registries.BLOCK)
                    .defaultRecipe()
                    .build()
            );
        }
    }
    *///?}
}
