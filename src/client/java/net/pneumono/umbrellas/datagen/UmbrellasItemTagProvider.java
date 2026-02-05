package net.pneumono.umbrellas.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.pneumono.umbrellas.registry.UmbrellasItems;
import net.pneumono.umbrellas.registry.UmbrellasTags;

import java.util.concurrent.CompletableFuture;

//? if >=1.21.6
import net.minecraft.data.tags.TagAppender;

public class UmbrellasItemTagProvider extends FabricTagProvider.ItemTagProvider {
    public UmbrellasItemTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        create(UmbrellasTags.PATTERNABLE_UMBRELLAS).add(
                UmbrellasItems.WHITE_UMBRELLA,
                UmbrellasItems.ORANGE_UMBRELLA,
                UmbrellasItems.MAGENTA_UMBRELLA,
                UmbrellasItems.LIGHT_BLUE_UMBRELLA,
                UmbrellasItems.YELLOW_UMBRELLA,
                UmbrellasItems.LIME_UMBRELLA,
                UmbrellasItems.PINK_UMBRELLA,
                UmbrellasItems.GRAY_UMBRELLA,
                UmbrellasItems.LIGHT_GRAY_UMBRELLA,
                UmbrellasItems.CYAN_UMBRELLA,
                UmbrellasItems.PURPLE_UMBRELLA,
                UmbrellasItems.BLUE_UMBRELLA,
                UmbrellasItems.BROWN_UMBRELLA,
                UmbrellasItems.GREEN_UMBRELLA,
                UmbrellasItems.RED_UMBRELLA,
                UmbrellasItems.BLACK_UMBRELLA
        );

        create(UmbrellasTags.EXTRA_UMBRELLAS).add(
                UmbrellasItems.ANIMALS_UMBRELLA,
                UmbrellasItems.AZALEA_UMBRELLA,
                UmbrellasItems.GALACTIC_UMBRELLA,
                UmbrellasItems.GOTHIC_UMBRELLA,
                UmbrellasItems.JELLYFISH_UMBRELLA
        );

        create(UmbrellasTags.UMBRELLAS)
                .addTag(UmbrellasTags.PATTERNABLE_UMBRELLAS)
                .addTag(UmbrellasTags.EXTRA_UMBRELLAS);

        create(UmbrellasTags.UMBRELLA_ENCHANTABLE).addTag(UmbrellasTags.UMBRELLAS);

        //? if >=1.21
        create(ItemTags.DURABILITY_ENCHANTABLE).addTag(UmbrellasTags.UMBRELLAS);
    }

    private /*? if >=1.21.6 {*/TagAppender<Item, Item>/*?} else {*//*ItemTagProvider.FabricTagBuilder*//*?}*/ create(TagKey<Item> tag) {
        //? if >=1.21.6 {
        return valueLookupBuilder(tag);
        //?} else {
        /*return getOrCreateTagBuilder(tag);
        *///?}
    }
}
