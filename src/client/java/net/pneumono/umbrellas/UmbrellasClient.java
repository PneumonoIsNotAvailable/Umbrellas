//~ identifier_replacements

package net.pneumono.umbrellas;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.Identifier;
import net.pneumono.umbrellas.content.*;
import net.pneumono.umbrellas.registry.UmbrellasBlocks;

//? if >=1.21.6 {
import net.minecraft.client.renderer.MaterialMapper;
import net.minecraft.client.renderer.special.SpecialModelRenderers;
//?} else {
/*import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.minecraft.world.item.Item;
import net.pneumono.umbrellas.registry.UmbrellasDataComponents;
import net.pneumono.umbrellas.registry.UmbrellasItems;
import java.util.HashMap;
import java.util.Map;
*///?}

public class UmbrellasClient implements ClientModInitializer {
	public static final ModelLayerLocation UMBRELLA_HANDLE_LAYER = new ModelLayerLocation(Umbrellas.id("umbrella_handle"), "main");
	public static final ModelLayerLocation UMBRELLA_CANOPY_LAYER = new ModelLayerLocation(Umbrellas.id("umbrella_canopy"), "main");

	public static final Identifier UMBRELLA_PATTERNS_ATLAS_TEXTURE = Umbrellas.id("textures/atlas/umbrella_patterns.png");
	//? if >=1.21.6 {
	public static final MaterialMapper UMBRELLA_PATTERN_MATERIAL_MAPPER = new MaterialMapper(UMBRELLA_PATTERNS_ATLAS_TEXTURE, "entity/umbrella");
	//?} else {
	/*public static final Map<Identifier, Material> UMBRELLA_MATERIALS = new HashMap<>();
	public static UmbrellaRenderer UMBRELLA_RENDERER;
	*///?}

	public static final Material UMBRELLA_BASE = new Material(UMBRELLA_PATTERNS_ATLAS_TEXTURE, Umbrellas.id("entity/umbrella_base"));

	@Override
	public void onInitializeClient() {
		BlockEntityRenderers.register(UmbrellasBlocks.UMBRELLA_STAND_BLOCK_ENTITY, UmbrellaStandBlockEntityRenderer::new);

		EntityModelLayerRegistry.registerModelLayer(UMBRELLA_HANDLE_LAYER, UmbrellaModel.Handle::createLayer);
		EntityModelLayerRegistry.registerModelLayer(UMBRELLA_CANOPY_LAYER, UmbrellaModel.Canopy::getTexturedModelData);

		//? if >=1.21.6 {
		SpecialModelRenderers.ID_MAPPER.put(Umbrellas.id("umbrella"), UmbrellaSpecialModelRenderer.Unbaked.CODEC);
		//?} else {
		/*BuiltinItemRendererRegistry.DynamicItemRenderer renderer = (
				itemStack, mode, poseStack, collector,
				light, overlay
		) -> UMBRELLA_RENDERER.submit(
				itemStack.get(UmbrellasDataComponents.UMBRELLA_PATTERNS),
				poseStack, light, overlay, itemStack.hasFoil(), collector
		);

		for (Item item : UmbrellasItems.PATTERNABLES) {
			BuiltinItemRendererRegistry.INSTANCE.register(item, renderer);
		}
		*///?}
	}

	public static Material getUmbrellaMaterial(Identifier id) {
		//? if >=1.21.6 {
		return UmbrellasClient.UMBRELLA_PATTERN_MATERIAL_MAPPER.apply(id);
		//?} else {
		/*return UMBRELLA_MATERIALS.computeIfAbsent(id, (location) ->
				new Material(UMBRELLA_PATTERNS_ATLAS_TEXTURE, location.withPrefix("entity/umbrella/"))
		);
		*///?}
	}
}