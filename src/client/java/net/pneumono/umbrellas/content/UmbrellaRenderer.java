package net.pneumono.umbrellas.content;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.LoadedEntityModels;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.RotationAxis;
import net.pneumono.umbrellas.UmbrellasClient;
import net.pneumono.umbrellas.content.item.component.UmbrellaPatternsComponent;

public class UmbrellaRenderer {
    private final UmbrellaModel model;

    public UmbrellaRenderer(LoadedEntityModels models) {
        this.model = new UmbrellaModel(models.getModelPart(UmbrellasClient.UMBRELLA_MODEL_LAYER));
    }

    public static SpriteIdentifier getUmbrellaPatternTextureId(RegistryEntry<UmbrellaPattern> pattern) {
        return UmbrellasClient.UMBRELLA_PATTERN_TEXTURES.computeIfAbsent(pattern.value().assetId(), UmbrellasClient.UMBRELLA_PATTERN_SPRITE_MAPPER::map);
    }

    public void render(
            MatrixStack matrices,
            VertexConsumerProvider vertexConsumers,
            int light, int overlay, boolean glint,
            float rotation,
            DyeColor baseColor,
            UmbrellaPatternsComponent patterns
    ) {
        matrices.push();
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(rotation));
        matrices.scale(1.0F, -1.0F, -1.0F);

        this.model.render(matrices, UmbrellasClient.UMBRELLA_BASE_BAKER.getVertexConsumer(vertexConsumers, RenderLayer::getEntitySolid), light, overlay);
        renderCanopy(matrices, vertexConsumers, light, overlay, glint, this.model.getRootPart(), UmbrellasClient.UMBRELLA_BASE_BAKER, baseColor, patterns);

        matrices.pop();
    }

    public static void renderCanopy(
            MatrixStack matrices,
            VertexConsumerProvider vertexConsumers,
            int light, int overlay,
            boolean glint,
            ModelPart canopy,
            SpriteIdentifier baseSprite,
            DyeColor baseColor,
            UmbrellaPatternsComponent patterns
    ) {
        canopy.render(matrices, baseSprite.getVertexConsumer(vertexConsumers, RenderLayer::getEntitySolid, false, glint), light, overlay);
        renderLayer(matrices, vertexConsumers, light, overlay, canopy, UmbrellasClient.UMBRELLA_BASE, baseColor.getEntityColor());

        for (int i = 0; i < 16 && i < patterns.layers().size(); i++) {
            UmbrellaPatternsComponent.Layer layer = patterns.layers().get(i);
            RegistryEntry<UmbrellaPattern> pattern = layer.pattern();
            SpriteIdentifier spriteIdentifier = getUmbrellaPatternTextureId(pattern);

            renderLayer(matrices, vertexConsumers, light, overlay, canopy, spriteIdentifier, pattern.value().dyeable() ? layer.color().getEntityColor() : -1);
        }
    }

    private static void renderLayer(
            MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, ModelPart canopy, SpriteIdentifier textureId, int color
    ) {
        canopy.render(
                matrices,
                textureId.getVertexConsumer(vertexConsumers, RenderLayer::getEntityNoOutline),
                light, overlay,
                color
        );
    }
}
