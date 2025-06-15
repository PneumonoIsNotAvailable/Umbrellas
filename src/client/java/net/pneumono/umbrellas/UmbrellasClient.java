package net.pneumono.umbrellas;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.item.model.special.SpecialModelTypes;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.SpriteMapper;
import net.minecraft.util.Identifier;
import net.pneumono.umbrellas.content.*;
import net.pneumono.umbrellas.registry.UmbrellasBlocks;

public class UmbrellasClient implements ClientModInitializer {
	public static final EntityModelLayer UMBRELLA_HANDLE_LAYER = new EntityModelLayer(Umbrellas.id("umbrella_handle"), "main");
	public static final EntityModelLayer UMBRELLA_CANOPY_LAYER = new EntityModelLayer(Umbrellas.id("umbrella_canopy"), "main");

	public static final Identifier UMBRELLA_PATTERNS_ATLAS_TEXTURE = Umbrellas.id("textures/atlas/umbrella_patterns.png");
	public static final SpriteMapper UMBRELLA_PATTERN_SPRITE_MAPPER = new SpriteMapper(UMBRELLA_PATTERNS_ATLAS_TEXTURE, "entity/umbrella");

	public static final SpriteIdentifier UMBRELLA_BASE = new SpriteIdentifier(UMBRELLA_PATTERNS_ATLAS_TEXTURE, Umbrellas.id("entity/umbrella_base"));

	@Override
	public void onInitializeClient() {
		BlockEntityRendererFactories.register(UmbrellasBlocks.UMBRELLA_STAND_BLOCK_ENTITY, UmbrellaStandBlockEntityRenderer::new);

		EntityModelLayerRegistry.registerModelLayer(UMBRELLA_HANDLE_LAYER, UmbrellaModel.Handle::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(UMBRELLA_CANOPY_LAYER, UmbrellaModel.Canopy::getTexturedModelData);

		SpecialModelTypes.ID_MAPPER.put(Umbrellas.id("umbrella"), UmbrellaModelRenderer.Unbaked.CODEC);
	}

	/*
	public static boolean shouldChangeArms(LivingEntity entity, Arm arm) {
		Arm mainArm = entity.getMainArm();
		ItemStack stack = mainArm == arm ? entity.getMainHandStack() : entity.getOffHandStack();
		boolean hasUmbrella = stack.getItem() instanceof UmbrellaItem;
		float minimumVelocity;
		if (PneumonoEnchantmentHelper.hasEnchantment(UmbrellasRegistry.GLIDING, stack)) {
			minimumVelocity = -0.19f;
		} else {
			minimumVelocity = -0.8f;
		}

		return hasUmbrella && entity.getVelocity().getY() < minimumVelocity;
	}
	 */
}