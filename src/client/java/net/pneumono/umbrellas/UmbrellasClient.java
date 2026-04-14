//~ identifier_replacements

package net.pneumono.umbrellas;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.resources.Identifier;
import net.pneumono.umbrellas.content.*;
import net.pneumono.umbrellas.registry.UmbrellasBlocks;

//~ if >=26.1 'EntityModelLayerRegistry' -> 'ModelLayerRegistry'
import net.fabricmc.fabric.api.client.rendering.v1.ModelLayerRegistry;

//? if >=26.1 {
import net.minecraft.client.renderer.SpriteMapper;
import net.minecraft.client.renderer.special.SpecialModelRenderers;
import net.minecraft.client.resources.model.sprite.SpriteId;
//?} else if >=1.21.6 {
/*import net.minecraft.client.renderer.MaterialMapper;
import net.minecraft.client.renderer.special.SpecialModelRenderers;
import net.minecraft.client.resources.model.Material;
*///?} else {
/*import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.minecraft.world.item.Item;
import net.pneumono.umbrellas.content.item.PatternableUmbrellaItem;
import net.pneumono.umbrellas.content.item.component.UmbrellaPatternsComponent;
import net.pneumono.umbrellas.registry.UmbrellasDataComponents;
import net.pneumono.umbrellas.registry.UmbrellasItems;
import net.pneumono.umbrellas.util.data.VersionedComponents;

import java.util.HashMap;
import java.util.Map;
*///?}

public class UmbrellasClient implements ClientModInitializer {
	public static final ModelLayerLocation UMBRELLA_HANDLE_LAYER = new ModelLayerLocation(Umbrellas.id("umbrella_handle"), "main");
	public static final ModelLayerLocation UMBRELLA_CANOPY_LAYER = new ModelLayerLocation(Umbrellas.id("umbrella_canopy"), "main");

	public static final Identifier UMBRELLA_PATTERNS_ATLAS_TEXTURE = Umbrellas.id("textures/atlas/umbrella_patterns.png");
	//? if >=26.1 {
	public static final SpriteMapper UMBRELLA_PATTERN_MATERIAL_MAPPER = new SpriteMapper(UMBRELLA_PATTERNS_ATLAS_TEXTURE, "entity/umbrella");
	//?} else if >=1.21.6 {
	/*public static final MaterialMapper UMBRELLA_PATTERN_MATERIAL_MAPPER = new MaterialMapper(UMBRELLA_PATTERNS_ATLAS_TEXTURE, "entity/umbrella");
	*///?} else {
	/*public static final Map<Identifier, Material> UMBRELLA_MATERIALS = new HashMap<>();
	public static UmbrellaRenderer UMBRELLA_RENDERER;
	*///?}

	//~ if >=26.1 'Material' -> 'SpriteId'
	public static final SpriteId UMBRELLA_BASE = new SpriteId(UMBRELLA_PATTERNS_ATLAS_TEXTURE, Umbrellas.id("entity/umbrella_base"));

	@Override
	public void onInitializeClient() {
		BlockEntityRenderers.register(UmbrellasBlocks.UMBRELLA_STAND_BLOCK_ENTITY, UmbrellaStandBlockEntityRenderer::new);

		//~ if >=26.1 'EntityModelLayerRegistry' -> 'ModelLayerRegistry' {
		ModelLayerRegistry.registerModelLayer(UMBRELLA_HANDLE_LAYER, UmbrellaModel.Handle::createLayer);
		ModelLayerRegistry.registerModelLayer(UMBRELLA_CANOPY_LAYER, UmbrellaModel.Canopy::createLayer);
		//~}

		//? if >=1.21.6 {
		SpecialModelRenderers.ID_MAPPER.put(Umbrellas.id("umbrella"), UmbrellaSpecialModelRenderer.Unbaked.CODEC);
		//?} else {
		/*BuiltinItemRendererRegistry.DynamicItemRenderer renderer = (
				itemStack, mode, poseStack, collector,
				light, overlay
		) -> UMBRELLA_RENDERER.submit(
				PatternableUmbrellaItem.getColor(itemStack),
				VersionedComponents.getOrDefault(itemStack, UmbrellasDataComponents.UMBRELLA_PATTERNS, UmbrellaPatternsComponent.DEFAULT),
				poseStack, light, overlay, itemStack.hasFoil(), collector
		);

		for (Item item : UmbrellasItems.PATTERNABLES) {
			BuiltinItemRendererRegistry.INSTANCE.register(item, renderer);
		}
		*///?}
	}

	public static /*? if >=26.1 {*/SpriteId/*?} else {*//*Material*//*?}*/ getUmbrellaSpriteId(Identifier id) {
		//? if >=1.21.6 {
		return UmbrellasClient.UMBRELLA_PATTERN_MATERIAL_MAPPER.apply(id);
		//?} else {
		/*return UMBRELLA_MATERIALS.computeIfAbsent(id, (identifier) ->
				new Material(UMBRELLA_PATTERNS_ATLAS_TEXTURE, identifier.withPrefix("entity/umbrella/"))
		);
		*///?}
	}
}