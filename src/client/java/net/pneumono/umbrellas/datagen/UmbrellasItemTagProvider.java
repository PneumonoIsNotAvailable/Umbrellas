package net.pneumono.umbrellas.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryWrapper;
import net.pneumono.umbrellas.registry.UmbrellasItems;
import net.pneumono.umbrellas.registry.UmbrellasTags;

import java.util.concurrent.CompletableFuture;

public class UmbrellasItemTagProvider extends FabricTagProvider.ItemTagProvider {
    public UmbrellasItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        FabricTagBuilder umbrellasBuilder = getOrCreateTagBuilder(UmbrellasTags.UMBRELLAS);

        for (Item item : UmbrellasItems.UMBRELLAS) {
            umbrellasBuilder.add(item);
        }

        getOrCreateTagBuilder(UmbrellasTags.UMBRELLA_ENCHANTABLE).addTag(UmbrellasTags.UMBRELLAS);
    }
}
