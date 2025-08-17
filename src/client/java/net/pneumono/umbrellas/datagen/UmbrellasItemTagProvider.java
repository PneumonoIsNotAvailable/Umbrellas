package net.pneumono.umbrellas.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import net.pneumono.umbrellas.registry.UmbrellasItems;
import net.pneumono.umbrellas.registry.UmbrellasTags;

import java.util.concurrent.CompletableFuture;

public class UmbrellasItemTagProvider extends FabricTagProvider.ItemTagProvider {
    public UmbrellasItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        valueLookupBuilder(UmbrellasTags.PATTERNABLE_UMBRELLAS).add(
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

        valueLookupBuilder(UmbrellasTags.EXTRA_UMBRELLAS).add(
                UmbrellasItems.ANIMALS_UMBRELLA,
                UmbrellasItems.AZALEA_UMBRELLA,
                UmbrellasItems.GALACTIC_UMBRELLA,
                UmbrellasItems.GOTHIC_UMBRELLA,
                UmbrellasItems.JELLYFISH_UMBRELLA
        );

        valueLookupBuilder(UmbrellasTags.UMBRELLAS)
                .addTag(UmbrellasTags.PATTERNABLE_UMBRELLAS)
                .addTag(UmbrellasTags.EXTRA_UMBRELLAS);

        valueLookupBuilder(UmbrellasTags.UMBRELLA_ENCHANTABLE).addTag(UmbrellasTags.UMBRELLAS);

        valueLookupBuilder(ItemTags.DURABILITY_ENCHANTABLE).addTag(UmbrellasTags.UMBRELLAS);
    }
}
