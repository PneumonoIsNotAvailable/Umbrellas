package net.pneumono.umbrellas.datagen;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.block.Block;
import net.minecraft.client.data.*;
import net.minecraft.client.render.item.model.ItemModel;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.pneumono.umbrellas.Umbrellas;
import net.pneumono.umbrellas.content.UmbrellaModelRenderer;
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
        registerUmbrellaStand(generator, UmbrellasBlocks.OAK_UMBRELLA_STAND);
        registerUmbrellaStand(generator, UmbrellasBlocks.SPRUCE_UMBRELLA_STAND);
        registerUmbrellaStand(generator, UmbrellasBlocks.BIRCH_UMBRELLA_STAND);
        registerUmbrellaStand(generator, UmbrellasBlocks.ACACIA_UMBRELLA_STAND);
        registerUmbrellaStand(generator, UmbrellasBlocks.CHERRY_UMBRELLA_STAND);
        registerUmbrellaStand(generator, UmbrellasBlocks.JUNGLE_UMBRELLA_STAND);
        registerUmbrellaStand(generator, UmbrellasBlocks.DARK_OAK_UMBRELLA_STAND);
        registerUmbrellaStand(generator, UmbrellasBlocks.PALE_OAK_UMBRELLA_STAND);
        registerUmbrellaStand(generator, UmbrellasBlocks.CRIMSON_UMBRELLA_STAND);
        registerUmbrellaStand(generator, UmbrellasBlocks.WARPED_UMBRELLA_STAND);
        registerUmbrellaStand(generator, UmbrellasBlocks.MANGROVE_UMBRELLA_STAND);
        registerUmbrellaStand(generator, UmbrellasBlocks.BAMBOO_UMBRELLA_STAND);
    }

    private void registerUmbrellaStand(BlockStateModelGenerator generator, Block block) {
        TextureMap textureMap = TextureMap.texture(block);
        Identifier modelId = TEMPLATE_UMBRELLA_STAND.upload(block, textureMap, generator.modelCollector);
        generator.blockStateCollector.accept(BlockStateModelGenerator.createSingletonBlockState(
                block,
                BlockStateModelGenerator.createWeightedVariant(
                        modelId
                ))
        );
        generator.registerParentedItemModel(block, modelId);
    }

    private static final Model TEMPLATE_UMBRELLA = new Model(Optional.of(Umbrellas.id("item/template_umbrella")), Optional.empty());

    @Override
    public void generateItemModels(ItemModelGenerator generator) {
        registerPatternableUmbrella(generator, UmbrellasItems.WHITE_UMBRELLA);
        registerPatternableUmbrella(generator, UmbrellasItems.ORANGE_UMBRELLA);
        registerPatternableUmbrella(generator, UmbrellasItems.MAGENTA_UMBRELLA);
        registerPatternableUmbrella(generator, UmbrellasItems.LIGHT_BLUE_UMBRELLA);
        registerPatternableUmbrella(generator, UmbrellasItems.YELLOW_UMBRELLA);
        registerPatternableUmbrella(generator, UmbrellasItems.LIME_UMBRELLA);
        registerPatternableUmbrella(generator, UmbrellasItems.PINK_UMBRELLA);
        registerPatternableUmbrella(generator, UmbrellasItems.GRAY_UMBRELLA);
        registerPatternableUmbrella(generator, UmbrellasItems.LIGHT_GRAY_UMBRELLA);
        registerPatternableUmbrella(generator, UmbrellasItems.CYAN_UMBRELLA);
        registerPatternableUmbrella(generator, UmbrellasItems.PURPLE_UMBRELLA);
        registerPatternableUmbrella(generator, UmbrellasItems.BLUE_UMBRELLA);
        registerPatternableUmbrella(generator, UmbrellasItems.BROWN_UMBRELLA);
        registerPatternableUmbrella(generator, UmbrellasItems.GREEN_UMBRELLA);
        registerPatternableUmbrella(generator, UmbrellasItems.RED_UMBRELLA);
        registerPatternableUmbrella(generator, UmbrellasItems.BLACK_UMBRELLA);

        registerExtraUmbrella(generator, UmbrellasItems.ANIMALS_UMBRELLA);
        registerExtraUmbrella(generator, UmbrellasItems.AZALEA_UMBRELLA);
        registerExtraUmbrella(generator, UmbrellasItems.GALACTIC_UMBRELLA);
        registerExtraUmbrella(generator, UmbrellasItems.GOTHIC_UMBRELLA);
        registerExtraUmbrella(generator, UmbrellasItems.JELLYFISH_UMBRELLA);

        generator.register(UmbrellasItems.FLOWER_UMBRELLA_PATTERN, Models.GENERATED);
        generator.register(UmbrellasItems.CREEPER_UMBRELLA_PATTERN, Models.GENERATED);
        generator.register(UmbrellasItems.SKULL_UMBRELLA_PATTERN, Models.GENERATED);
        generator.register(UmbrellasItems.MOJANG_UMBRELLA_PATTERN, Models.GENERATED);
        generator.register(UmbrellasItems.GLOBE_UMBRELLA_PATTERN, Models.GENERATED);
        generator.register(UmbrellasItems.PIGLIN_UMBRELLA_PATTERN, Models.GENERATED);
        generator.register(UmbrellasItems.FLOW_UMBRELLA_PATTERN, Models.GENERATED);
        generator.register(UmbrellasItems.GUSTER_UMBRELLA_PATTERN, Models.GENERATED);
        generator.register(UmbrellasItems.FIELD_MASONED_UMBRELLA_PATTERN, Models.GENERATED);
        generator.register(UmbrellasItems.BORDURE_INDENTED_UMBRELLA_PATTERN, Models.GENERATED);
        generator.register(UmbrellasItems.PRIDE_UMBRELLA_PATTERN, Models.GENERATED);
    }

    public void registerPatternableUmbrella(ItemModelGenerator generator, Item item) {
        ItemModel.Unbaked unbaked = ItemModels.special(ModelIds.getItemModelId(item), new UmbrellaModelRenderer.Unbaked());
        generator.output.accept(item, unbaked);
        generator.upload(item, TEMPLATE_UMBRELLA);
    }

    public void registerExtraUmbrella(ItemModelGenerator generator, Item item) {
        generator.output.accept(item, ItemModels.basic(ModelIds.getItemModelId(item)));
    }
}
