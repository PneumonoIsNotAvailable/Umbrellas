package net.pneumono.umbrellas.content;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.LoadedEntityModels;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;
import net.pneumono.umbrellas.Umbrellas;
import net.pneumono.umbrellas.UmbrellasClient;
import net.pneumono.umbrellas.content.item.component.UmbrellaPatternsComponent;
import org.joml.Vector3f;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class UmbrellaRenderer {
    public static final Map<Identifier, SpriteIdentifier> UMBRELLA_PATTERN_TEXTURES = new HashMap<>();

    private final UmbrellaModel.Handle handleModel;
    private final UmbrellaModel.Canopy canopyModel;

    public UmbrellaRenderer(LoadedEntityModels models) {
        this.handleModel = new UmbrellaModel.Handle(models.getModelPart(UmbrellasClient.UMBRELLA_HANDLE_LAYER));
        this.canopyModel = new UmbrellaModel.Canopy(models.getModelPart(UmbrellasClient.UMBRELLA_CANOPY_LAYER));
    }

    public static SpriteIdentifier getUmbrellaPatternTextureId(RegistryEntry<UmbrellaPattern> pattern) {
        return UMBRELLA_PATTERN_TEXTURES.computeIfAbsent(pattern.value().assetId(), UmbrellasClient.UMBRELLA_PATTERN_SPRITE_MAPPER::map);
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

        this.handleModel.render(matrices, UmbrellasClient.UMBRELLA_BASE.getVertexConsumer(vertexConsumers, RenderLayer::getEntitySolid), light, overlay);
        renderCanopy(matrices, vertexConsumers, light, overlay, glint, false, this.canopyModel.getRootPart(), UmbrellasClient.UMBRELLA_BASE, baseColor, patterns);

        matrices.pop();
    }

    public static void renderCanopy(
            MatrixStack matrices,
            VertexConsumerProvider vertexConsumers,
            int light, int overlay,
            boolean glint, boolean solid,
            ModelPart canopy,
            SpriteIdentifier baseSprite,
            DyeColor baseColor,
            UmbrellaPatternsComponent patterns
    ) {
        canopy.render(matrices, baseSprite.getVertexConsumer(vertexConsumers, RenderLayer::getEntitySolid, solid, glint), light, overlay);
        renderLayer(
                matrices, vertexConsumers,
                light, overlay,
                canopy,
                UmbrellasClient.UMBRELLA_PATTERN_SPRITE_MAPPER.map(Umbrellas.id("base")),
                baseColor.getEntityColor()
        );

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

    public void collectVertices(Set<Vector3f> vertices) {
        MatrixStack matrixStack = new MatrixStack();
        matrixStack.scale(0.6666667F, -0.6666667F, -0.6666667F);
        this.handleModel.getRootPart().collectVertices(matrixStack, vertices);
        this.canopyModel.getRootPart().collectVertices(matrixStack, vertices);
    }
}
