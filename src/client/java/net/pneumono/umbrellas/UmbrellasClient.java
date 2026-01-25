//~ identifier_replacements

package net.pneumono.umbrellas;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.MaterialMapper;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.special.SpecialModelRenderers;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.Identifier;
import net.pneumono.umbrellas.content.*;
import net.pneumono.umbrellas.registry.UmbrellasBlocks;

public class UmbrellasClient implements ClientModInitializer {
	public static final ModelLayerLocation UMBRELLA_HANDLE_LAYER = new ModelLayerLocation(Umbrellas.id("umbrella_handle"), "main");
	public static final ModelLayerLocation UMBRELLA_CANOPY_LAYER = new ModelLayerLocation(Umbrellas.id("umbrella_canopy"), "main");

	public static final Identifier UMBRELLA_PATTERNS_ATLAS_TEXTURE = Umbrellas.id("textures/atlas/umbrella_patterns.png");
	public static final MaterialMapper UMBRELLA_PATTERN_MATERIAL_MAPPER = new MaterialMapper(UMBRELLA_PATTERNS_ATLAS_TEXTURE, "entity/umbrella");

	public static final Material UMBRELLA_BASE = new Material(UMBRELLA_PATTERNS_ATLAS_TEXTURE, Umbrellas.id("entity/umbrella_base"));

	@Override
	public void onInitializeClient() {
		BlockEntityRenderers.register(UmbrellasBlocks.UMBRELLA_STAND_BLOCK_ENTITY, UmbrellaStandBlockEntityRenderer::new);

		EntityModelLayerRegistry.registerModelLayer(UMBRELLA_HANDLE_LAYER, UmbrellaModel.Handle::createLayer);
		EntityModelLayerRegistry.registerModelLayer(UMBRELLA_CANOPY_LAYER, UmbrellaModel.Canopy::getTexturedModelData);

		SpecialModelRenderers.ID_MAPPER.put(Umbrellas.id("umbrella"), UmbrellaSpecialModelRenderer.Unbaked.CODEC);
	}
}