//~ identifier_replacements

package net.pneumono.umbrellas.datagen;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.*;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.pneumono.umbrellas.Umbrellas;
import net.pneumono.umbrellas.content.UmbrellaSpecialModelRenderer;
import net.pneumono.umbrellas.registry.UmbrellasBlocks;
import net.pneumono.umbrellas.registry.UmbrellasItems;

import java.util.Optional;

public class UmbrellasModelProvider extends FabricModelProvider {
    public UmbrellasModelProvider(FabricDataOutput output) {
        super(output);
    }

    private static final ModelTemplate TEMPLATE_UMBRELLA_STAND = new ModelTemplate(Optional.of(Umbrellas.id("block/template_umbrella_stand")), Optional.empty(), TextureSlot.TEXTURE);

    @Override
    public void generateBlockStateModels(BlockModelGenerators generators) {
        registerUmbrellaStand(generators, UmbrellasBlocks.OAK_UMBRELLA_STAND);
        registerUmbrellaStand(generators, UmbrellasBlocks.SPRUCE_UMBRELLA_STAND);
        registerUmbrellaStand(generators, UmbrellasBlocks.BIRCH_UMBRELLA_STAND);
        registerUmbrellaStand(generators, UmbrellasBlocks.ACACIA_UMBRELLA_STAND);
        registerUmbrellaStand(generators, UmbrellasBlocks.CHERRY_UMBRELLA_STAND);
        registerUmbrellaStand(generators, UmbrellasBlocks.JUNGLE_UMBRELLA_STAND);
        registerUmbrellaStand(generators, UmbrellasBlocks.DARK_OAK_UMBRELLA_STAND);
        registerUmbrellaStand(generators, UmbrellasBlocks.PALE_OAK_UMBRELLA_STAND);
        registerUmbrellaStand(generators, UmbrellasBlocks.CRIMSON_UMBRELLA_STAND);
        registerUmbrellaStand(generators, UmbrellasBlocks.WARPED_UMBRELLA_STAND);
        registerUmbrellaStand(generators, UmbrellasBlocks.MANGROVE_UMBRELLA_STAND);
        registerUmbrellaStand(generators, UmbrellasBlocks.BAMBOO_UMBRELLA_STAND);
    }

    private void registerUmbrellaStand(BlockModelGenerators generators, Block block) {
        TextureMapping textureMap = TextureMapping.defaultTexture(block);
        Identifier modelId = TEMPLATE_UMBRELLA_STAND.create(block, textureMap, generators.modelOutput);
        generators.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(
                block,
                BlockModelGenerators.plainVariant(modelId))
        );
        generators.registerSimpleItemModel(block, modelId);
    }

    private static final ModelTemplate TEMPLATE_UMBRELLA = new ModelTemplate(Optional.of(Umbrellas.id("item/template_umbrella")), Optional.empty());

    @Override
    public void generateItemModels(ItemModelGenerators generators) {
        registerPatternableUmbrella(generators, UmbrellasItems.WHITE_UMBRELLA);
        registerPatternableUmbrella(generators, UmbrellasItems.ORANGE_UMBRELLA);
        registerPatternableUmbrella(generators, UmbrellasItems.MAGENTA_UMBRELLA);
        registerPatternableUmbrella(generators, UmbrellasItems.LIGHT_BLUE_UMBRELLA);
        registerPatternableUmbrella(generators, UmbrellasItems.YELLOW_UMBRELLA);
        registerPatternableUmbrella(generators, UmbrellasItems.LIME_UMBRELLA);
        registerPatternableUmbrella(generators, UmbrellasItems.PINK_UMBRELLA);
        registerPatternableUmbrella(generators, UmbrellasItems.GRAY_UMBRELLA);
        registerPatternableUmbrella(generators, UmbrellasItems.LIGHT_GRAY_UMBRELLA);
        registerPatternableUmbrella(generators, UmbrellasItems.CYAN_UMBRELLA);
        registerPatternableUmbrella(generators, UmbrellasItems.PURPLE_UMBRELLA);
        registerPatternableUmbrella(generators, UmbrellasItems.BLUE_UMBRELLA);
        registerPatternableUmbrella(generators, UmbrellasItems.BROWN_UMBRELLA);
        registerPatternableUmbrella(generators, UmbrellasItems.GREEN_UMBRELLA);
        registerPatternableUmbrella(generators, UmbrellasItems.RED_UMBRELLA);
        registerPatternableUmbrella(generators, UmbrellasItems.BLACK_UMBRELLA);

        registerExtraUmbrella(generators, UmbrellasItems.ANIMALS_UMBRELLA);
        registerExtraUmbrella(generators, UmbrellasItems.AZALEA_UMBRELLA);
        registerExtraUmbrella(generators, UmbrellasItems.GALACTIC_UMBRELLA);
        registerExtraUmbrella(generators, UmbrellasItems.GOTHIC_UMBRELLA);
        registerExtraUmbrella(generators, UmbrellasItems.JELLYFISH_UMBRELLA);

        generators.generateFlatItem(UmbrellasItems.FLOWER_UMBRELLA_PATTERN, ModelTemplates.FLAT_ITEM);
        generators.generateFlatItem(UmbrellasItems.CREEPER_UMBRELLA_PATTERN, ModelTemplates.FLAT_ITEM);
        generators.generateFlatItem(UmbrellasItems.SKULL_UMBRELLA_PATTERN, ModelTemplates.FLAT_ITEM);
        generators.generateFlatItem(UmbrellasItems.MOJANG_UMBRELLA_PATTERN, ModelTemplates.FLAT_ITEM);
        generators.generateFlatItem(UmbrellasItems.GLOBE_UMBRELLA_PATTERN, ModelTemplates.FLAT_ITEM);
        generators.generateFlatItem(UmbrellasItems.PIGLIN_UMBRELLA_PATTERN, ModelTemplates.FLAT_ITEM);
        generators.generateFlatItem(UmbrellasItems.FLOW_UMBRELLA_PATTERN, ModelTemplates.FLAT_ITEM);
        generators.generateFlatItem(UmbrellasItems.GUSTER_UMBRELLA_PATTERN, ModelTemplates.FLAT_ITEM);
        generators.generateFlatItem(UmbrellasItems.FIELD_MASONED_UMBRELLA_PATTERN, ModelTemplates.FLAT_ITEM);
        generators.generateFlatItem(UmbrellasItems.BORDURE_INDENTED_UMBRELLA_PATTERN, ModelTemplates.FLAT_ITEM);
        generators.generateFlatItem(UmbrellasItems.PRIDE_UMBRELLA_PATTERN, ModelTemplates.FLAT_ITEM);
    }

    public void registerPatternableUmbrella(ItemModelGenerators generators, Item item) {
        ItemModel.Unbaked unbaked = ItemModelUtils.specialModel(ModelLocationUtils.getModelLocation(item), new UmbrellaSpecialModelRenderer.Unbaked());
        generators.itemModelOutput.accept(item, unbaked);
        TEMPLATE_UMBRELLA.create(ModelLocationUtils.getModelLocation(item), TextureMapping.layer0(item), generators.modelOutput);
    }

    public void registerExtraUmbrella(ItemModelGenerators generators, Item item) {
        generators.itemModelOutput.accept(item, ItemModelUtils.plainModel(ModelLocationUtils.getModelLocation(item)));
    }
}
