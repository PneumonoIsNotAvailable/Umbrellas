package net.pneumono.umbrellas.content;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.resources.model.Material;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.pneumono.umbrellas.Umbrellas;
import net.pneumono.umbrellas.UmbrellasClient;
import net.pneumono.umbrellas.content.item.component.UmbrellaPatternsComponent;
import org.joml.Vector3f;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class UmbrellaRenderer {
    public static final Map<ResourceLocation, Material> UMBRELLA_PATTERN_TEXTURES = new HashMap<>();

    private final UmbrellaModel.Handle handleModel;
    private final UmbrellaModel.Canopy canopyModel;

    public UmbrellaRenderer(EntityModelSet models) {
        this.handleModel = new UmbrellaModel.Handle(models.bakeLayer(UmbrellasClient.UMBRELLA_HANDLE_LAYER));
        this.canopyModel = new UmbrellaModel.Canopy(models.bakeLayer(UmbrellasClient.UMBRELLA_CANOPY_LAYER));
    }

    public static Material getUmbrellaPatternTextureId(Holder<UmbrellaPattern> pattern) {
        return UMBRELLA_PATTERN_TEXTURES.computeIfAbsent(pattern.value().assetId(), UmbrellasClient.UMBRELLA_PATTERN_SPRITE_MAPPER::apply);
    }

    public void render(
            PoseStack poseStack,
            MultiBufferSource multiBufferSource,
            int light, int overlay, boolean glint,
            float rotation,
            UmbrellaPatternsComponent patterns
    ) {
        poseStack.pushPose();
        poseStack.mulPose(Axis.YP.rotationDegrees(rotation));
        poseStack.scale(1.0F, -1.0F, -1.0F);

        this.handleModel.renderToBuffer(poseStack, UmbrellasClient.UMBRELLA_BASE.buffer(multiBufferSource, RenderType::entitySolid), light, overlay);
        renderCanopy(poseStack, multiBufferSource, light, overlay, glint, false, this.canopyModel.root(), UmbrellasClient.UMBRELLA_BASE, patterns);

        poseStack.popPose();
    }

    public static void renderCanopy(
            PoseStack poseStack,
            MultiBufferSource multiBufferSource,
            int light, int overlay,
            boolean glint, boolean solid,
            ModelPart canopy,
            Material baseSprite,
            UmbrellaPatternsComponent patterns
    ) {
        canopy.render(poseStack, baseSprite.buffer(multiBufferSource, RenderType::entitySolid, solid, glint), light, overlay);
        renderLayer(
                poseStack, multiBufferSource,
                light, overlay,
                canopy,
                UmbrellasClient.UMBRELLA_PATTERN_SPRITE_MAPPER.apply(Umbrellas.id("base")),
                patterns.baseColor().getTextureDiffuseColor()
        );

        for (int i = 0; i < 16 && i < patterns.layers().size(); i++) {
            UmbrellaPatternsComponent.Layer layer = patterns.layers().get(i);
            Holder<UmbrellaPattern> pattern = layer.pattern();
            Material material = getUmbrellaPatternTextureId(pattern);

            renderLayer(poseStack, multiBufferSource, light, overlay, canopy, material, pattern.value().dyeable() ? layer.color().getTextureDiffuseColor() : -1);
        }
    }

    private static void renderLayer(
            PoseStack poseStack, MultiBufferSource multiBufferSource, int light, int overlay, ModelPart canopy, Material material, int color
    ) {
        canopy.render(
                poseStack,
                material.buffer(multiBufferSource, RenderType::entityNoOutline),
                light, overlay,
                color
        );
    }

    public void getExtentsForGui(Set<Vector3f> vertices) {
        PoseStack poseStack = new PoseStack();
        poseStack.scale(0.6666667F, -0.6666667F, -0.6666667F);
        this.handleModel.root().getExtentsForGui(poseStack, vertices);
        this.canopyModel.root().getExtentsForGui(poseStack, vertices);
    }
}
