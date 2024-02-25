package net.pneumono.umbrellas;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.pneumono.umbrellas.content.UmbrellaModel;
import net.pneumono.umbrellas.content.UmbrellaStandBlockEntityRenderer;
import net.pneumono.umbrellas.content.UmbrellasRegistry;
import net.pneumono.umbrellas.patterns.PatternRegistry;
import net.pneumono.umbrellas.patterns.UmbrellaPattern;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class UmbrellasClient implements ClientModInitializer {
	public static final Identifier UMBRELLA_PATTERNS_ATLAS_TEXTURE = new Identifier(Umbrellas.MOD_ID, "textures/atlas/umbrella_patterns.png");
	public static final EntityModelLayer UMBRELLA = new EntityModelLayer(new Identifier(Umbrellas.MOD_ID, "umbrella"), "main");
	public static final SpriteIdentifier UMBRELLA_BASE = new SpriteIdentifier(UMBRELLA_PATTERNS_ATLAS_TEXTURE, new Identifier(Umbrellas.MOD_ID, "entity/umbrella_base"));
	public static final Map<RegistryKey<UmbrellaPattern>, SpriteIdentifier> UMBRELLA_PATTERN_TEXTURES = PatternRegistry.UMBRELLA_PATTERN.getKeys().stream().collect(Collectors.toMap(Function.identity(), UmbrellasClient::createUmbrellaPatternTextureId));

	@Override
	public void onInitializeClient() {
		BlockEntityRendererFactories.register(UmbrellasRegistry.UMBRELLA_STAND_ENTITY, UmbrellaStandBlockEntityRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(UMBRELLA, UmbrellaModel::getTexturedModelData);
		for (Item item : UmbrellasRegistry.UMBRELLAS) {
			BuiltinItemRendererRegistry.INSTANCE.register(item, (stack, mode, matrices, vertexConsumers, light, overlay) -> UmbrellaModel.render(stack, matrices, vertexConsumers, light, overlay));
		}
	}

	private static SpriteIdentifier createUmbrellaPatternTextureId(RegistryKey<UmbrellaPattern> umbrellaPattern) {
		return new SpriteIdentifier(UMBRELLA_PATTERNS_ATLAS_TEXTURE, umbrellaPattern.getValue().withPrefixedPath("entity/umbrella/"));
	}

	public static SpriteIdentifier getUmbrellaPatternTextureId(RegistryKey<UmbrellaPattern> umbrellaPattern) {
		return UMBRELLA_PATTERN_TEXTURES.get(umbrellaPattern);
	}
}