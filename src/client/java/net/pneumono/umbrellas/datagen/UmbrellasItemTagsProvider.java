//~ datagen_replacements

package net.pneumono.umbrellas.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.pneumono.umbrellas.registry.UmbrellasItems;
import net.pneumono.umbrellas.registry.UmbrellasTags;

import java.util.concurrent.CompletableFuture;

//? if >=26.2 {
import net.minecraft.data.tags.BlockItemTagAppender;
import net.minecraft.references.ItemIds;
//?} else if >=1.21.6 {
/*import net.minecraft.data.tags.TagAppender;
import net.minecraft.world.item.Items;
*///?}

public class UmbrellasItemTagsProvider extends FabricTagsProvider./*? if >=26.1 {*/ItemTagsProvider/*?} else {*//*ItemTagProvider*//*?}*/ {
    public UmbrellasItemTagsProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        //? if >=26.2 {
        create(UmbrellasTags.PATTERNABLE_UMBRELLAS).add(
                UmbrellasItems.WHITE_UMBRELLA.builtInRegistryHolder().key(),
                UmbrellasItems.ORANGE_UMBRELLA.builtInRegistryHolder().key(),
                UmbrellasItems.MAGENTA_UMBRELLA.builtInRegistryHolder().key(),
                UmbrellasItems.LIGHT_BLUE_UMBRELLA.builtInRegistryHolder().key(),
                UmbrellasItems.YELLOW_UMBRELLA.builtInRegistryHolder().key(),
                UmbrellasItems.LIME_UMBRELLA.builtInRegistryHolder().key(),
                UmbrellasItems.PINK_UMBRELLA.builtInRegistryHolder().key(),
                UmbrellasItems.GRAY_UMBRELLA.builtInRegistryHolder().key(),
                UmbrellasItems.LIGHT_GRAY_UMBRELLA.builtInRegistryHolder().key(),
                UmbrellasItems.CYAN_UMBRELLA.builtInRegistryHolder().key(),
                UmbrellasItems.PURPLE_UMBRELLA.builtInRegistryHolder().key(),
                UmbrellasItems.BLUE_UMBRELLA.builtInRegistryHolder().key(),
                UmbrellasItems.BROWN_UMBRELLA.builtInRegistryHolder().key(),
                UmbrellasItems.GREEN_UMBRELLA.builtInRegistryHolder().key(),
                UmbrellasItems.RED_UMBRELLA.builtInRegistryHolder().key(),
                UmbrellasItems.BLACK_UMBRELLA.builtInRegistryHolder().key()
        );

        create(UmbrellasTags.EXTRA_UMBRELLAS).add(
                UmbrellasItems.ANIMALS_UMBRELLA.builtInRegistryHolder().key(),
                UmbrellasItems.AZALEA_UMBRELLA.builtInRegistryHolder().key(),
                UmbrellasItems.GALACTIC_UMBRELLA.builtInRegistryHolder().key(),
                UmbrellasItems.GOTHIC_UMBRELLA.builtInRegistryHolder().key(),
                UmbrellasItems.JELLYFISH_UMBRELLA.builtInRegistryHolder().key()
        );
        //?} else {
        /*create(UmbrellasTags.PATTERNABLE_UMBRELLAS).add(
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
        *///?}

        create(UmbrellasTags.UMBRELLAS)
                .addTag(UmbrellasTags.PATTERNABLE_UMBRELLAS)
                .addTag(UmbrellasTags.EXTRA_UMBRELLAS);

        create(UmbrellasTags.UMBRELLA_ENCHANTABLE).addTag(UmbrellasTags.UMBRELLAS);

        //? if >=26.2 {
        create(UmbrellasTags.REPAIRS_UMBRELLAS).add(ItemIds.LEATHER);
        //?} else {
        /*create(UmbrellasTags.REPAIRS_UMBRELLAS).add(Items.LEATHER);
        *///?}

        //? if >=1.21
        create(ItemTags.DURABILITY_ENCHANTABLE).addTag(UmbrellasTags.UMBRELLAS);

        //? if >=26.2 {
        create(ItemTags.LOOM_PATTERNS).add(
                UmbrellasItems.FLOWER_UMBRELLA_PATTERN.builtInRegistryHolder().key(),
                UmbrellasItems.CREEPER_UMBRELLA_PATTERN.builtInRegistryHolder().key(),
                UmbrellasItems.SKULL_UMBRELLA_PATTERN.builtInRegistryHolder().key(),
                UmbrellasItems.MOJANG_UMBRELLA_PATTERN.builtInRegistryHolder().key(),
                UmbrellasItems.GLOBE_UMBRELLA_PATTERN.builtInRegistryHolder().key(),
                UmbrellasItems.PIGLIN_UMBRELLA_PATTERN.builtInRegistryHolder().key(),
                UmbrellasItems.FLOW_UMBRELLA_PATTERN.builtInRegistryHolder().key(),
                UmbrellasItems.GUSTER_UMBRELLA_PATTERN.builtInRegistryHolder().key(),
                UmbrellasItems.FIELD_MASONED_UMBRELLA_PATTERN.builtInRegistryHolder().key(),
                UmbrellasItems.BORDURE_INDENTED_UMBRELLA_PATTERN.builtInRegistryHolder().key(),
                UmbrellasItems.PRIDE_UMBRELLA_PATTERN.builtInRegistryHolder().key()
        );
        //?} else if >=26.1 {
        /*create(ItemTags.LOOM_PATTERNS).add(
                UmbrellasItems.FLOWER_UMBRELLA_PATTERN,
                UmbrellasItems.CREEPER_UMBRELLA_PATTERN,
                UmbrellasItems.SKULL_UMBRELLA_PATTERN,
                UmbrellasItems.MOJANG_UMBRELLA_PATTERN,
                UmbrellasItems.GLOBE_UMBRELLA_PATTERN,
                UmbrellasItems.PIGLIN_UMBRELLA_PATTERN,
                UmbrellasItems.FLOW_UMBRELLA_PATTERN,
                UmbrellasItems.GUSTER_UMBRELLA_PATTERN,
                UmbrellasItems.FIELD_MASONED_UMBRELLA_PATTERN,
                UmbrellasItems.BORDURE_INDENTED_UMBRELLA_PATTERN,
                UmbrellasItems.PRIDE_UMBRELLA_PATTERN
        );
        *///?}
    }

    private /*? if >=26.2 {*/ BlockItemTagAppender<Item>/*?} else if >=1.21.6 {*//*TagAppender<Item, Item>*//*?} else {*//*ItemTagProvider.FabricTagBuilder*//*?}*/ create(TagKey<Item> tag) {
        //? if >=26.2 {
        return builder(tag);
        //?} else if >=1.21.6 {
        /*return valueLookupBuilder(tag);
        *///?} else {
        /*return getOrCreateTagBuilder(tag);
        *///?}
    }
}
