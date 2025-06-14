package net.pneumono.umbrellas.datagen;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.block.Block;
import net.minecraft.client.data.*;
import net.minecraft.client.render.item.model.ItemModel;
import net.minecraft.item.Item;
import net.pneumono.umbrellas.Umbrellas;
import net.pneumono.umbrellas.content.UmbrellaModelRenderer;
import net.pneumono.umbrellas.content.item.PatternableUmbrellaItem;
import net.pneumono.umbrellas.registry.UmbrellasBlocks;
import net.pneumono.umbrellas.registry.UmbrellasItems;

import java.util.Optional;

public class UmbrellasModelProvider extends FabricModelProvider {
    public UmbrellasModelProvider(FabricDataOutput output) {
        super(output);
    }

    private static final Model TEMPLATE_UMBRELLA_STAND = new Model(Optional.of(Umbrellas.id("block/template_umbrella_stand")), Optional.empty(), TextureKey.TEXTURE);

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator generator) {
        registerUmbrellaStand(generator, UmbrellasBlocks.UMBRELLA_STAND);
    }

    private void registerUmbrellaStand(BlockStateModelGenerator generator, Block block) {
        TextureMap textureMap = TextureMap.texture(block);
        generator.blockStateCollector.accept(BlockStateModelGenerator.createSingletonBlockState(
                block,
                BlockStateModelGenerator.createWeightedVariant(
                        TEMPLATE_UMBRELLA_STAND.upload(block, textureMap, generator.modelCollector)
                )));
        generator.registerItemModel(block);
    }

    private static final Model TEMPLATE_UMBRELLA = new Model(Optional.of(Umbrellas.id("item/template_umbrella")), Optional.empty());

    @Override
    public void generateItemModels(ItemModelGenerator generator) {
        for (PatternableUmbrellaItem umbrella : UmbrellasItems.PATTERNABLE_UMBRELLAS) {
            registerPatternableUmbrella(generator, umbrella);
        }

        for (Item umbrella : UmbrellasItems.EXTRA_UMBRELLAS) {
            registerExtraUmbrella(generator, umbrella);
        }

        for (Item pattern : UmbrellasItems.PATTERNS) {
            generator.register(pattern, Models.GENERATED);
        }
    }

    public void registerPatternableUmbrella(ItemModelGenerator generator, PatternableUmbrellaItem item) {
        ItemModel.Unbaked unbaked = ItemModels.special(ModelIds.getItemModelId(item), new UmbrellaModelRenderer.Unbaked(item.getColor()));
        generator.output.accept(item, unbaked);
        generator.upload(item, TEMPLATE_UMBRELLA);
    }

    public void registerExtraUmbrella(ItemModelGenerator generator, Item item) {
        generator.output.accept(item, ItemModels.basic(ModelIds.getItemModelId(item)));
    }
}
