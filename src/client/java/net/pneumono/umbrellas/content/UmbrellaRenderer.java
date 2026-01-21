package net.pneumono.umbrellas.content;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.resources.model.Material;
import net.minecraft.core.Holder;
import net.minecraft.resources.Identifier;
import net.pneumono.umbrellas.Umbrellas;
import net.pneumono.umbrellas.UmbrellasClient;
import net.pneumono.umbrellas.content.item.component.UmbrellaPatternsComponent;

import java.util.HashMap;
import java.util.Map;

//? if >=1.21.11 {
import net.minecraft.client.renderer.rendertype.RenderTypes;
import java.util.function.Consumer;
import org.joml.Vector3fc;
//?} else {
/*import net.minecraft.client.renderer.RenderType;
import java.util.Set;
import org.joml.Vector3f;
*///?}

//? if >=1.21.9 {
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.resources.model.MaterialSet;
import net.minecraft.util.Unit;
import org.jetbrains.annotations.Nullable;
//?} else {
/*import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
*///?}

public class UmbrellaRenderer {
    public static final Map<Identifier, Material> UMBRELLA_PATTERN_TEXTURES = new HashMap<>();

    //? if >=1.21.9
    private final MaterialSet materialSet;

    private final UmbrellaModel.Handle handleModel;
    private final UmbrellaModel.Canopy canopyModel;

    public UmbrellaRenderer(EntityModelSet models/*? if >=1.21.9 {*/, MaterialSet materialSet/*?}*/) {
        //? if >=1.21.9
        this.materialSet = materialSet;

        this.handleModel = new UmbrellaModel.Handle(models.bakeLayer(UmbrellasClient.UMBRELLA_HANDLE_LAYER));
        this.canopyModel = new UmbrellaModel.Canopy(models.bakeLayer(UmbrellasClient.UMBRELLA_CANOPY_LAYER));
    }

    public static Material getUmbrellaPatternTextureId(Holder<UmbrellaPattern> pattern) {
        return UMBRELLA_PATTERN_TEXTURES.computeIfAbsent(pattern.value().assetId(), UmbrellasClient.UMBRELLA_PATTERN_SPRITE_MAPPER::apply);
    }

    //? if >=1.21.9 {
    public void submit(
            UmbrellaPatternsComponent patterns,
            PoseStack poseStack,
            SubmitNodeCollector collector,
            int light, int overlay,
            float rotation,
            @Nullable ModelFeatureRenderer.CrumblingOverlay crumblingOverlay
    ) {
        poseStack.pushPose();
        poseStack.mulPose(Axis.YP.rotationDegrees(rotation));
        poseStack.scale(1.0F, -1.0F, -1.0F);

        Material material = UmbrellasClient.UMBRELLA_BASE;
        collector.submitModel(
                this.handleModel, Unit.INSTANCE, poseStack,
                material.renderType(/*? if >=1.21.11 {*/RenderTypes::entitySolid/*?} else {*//*RenderType::entitySolid*//*?}*/),
                light, overlay, -1,
                materialSet.get(material), 0,
                crumblingOverlay
        );
        submitCanopy(patterns, poseStack, collector, light, overlay, UmbrellasClient.UMBRELLA_BASE, crumblingOverlay);

        poseStack.popPose();
    }

    public void submitCanopy(
            UmbrellaPatternsComponent patterns,
            PoseStack poseStack,
            SubmitNodeCollector collector,
            int light, int overlay,
            Material material,
            @Nullable ModelFeatureRenderer.CrumblingOverlay crumblingOverlay
    ) {
        collector.submitModel(
                this.canopyModel, Unit.INSTANCE, poseStack,
                material.renderType(/*? if >=1.21.11 {*/RenderTypes::entitySolid/*?} else {*//*RenderType::entitySolid*//*?}*/),
                light, overlay, -1,
                this.materialSet.get(material), 0,
                crumblingOverlay
        );
        submitLayer(
                poseStack, collector,
                light, overlay,
                patterns.baseColor().getTextureDiffuseColor(),
                UmbrellasClient.UMBRELLA_PATTERN_SPRITE_MAPPER.apply(Umbrellas.id("base")),
                crumblingOverlay
        );

        for (int i = 0; i < 16 && i < patterns.layers().size(); i++) {
            UmbrellaPatternsComponent.Layer layer = patterns.layers().get(i);
            Holder<UmbrellaPattern> pattern = layer.pattern();
            Material layerMaterial = getUmbrellaPatternTextureId(pattern);

            submitLayer(
                    poseStack, collector,
                    light, overlay,
                    layer.color().getTextureDiffuseColor(),
                    layerMaterial,
                    crumblingOverlay
            );
        }
    }

    private void submitLayer(
            PoseStack poseStack, SubmitNodeCollector collector,
            int light, int overlay, int color, Material material,
            @Nullable ModelFeatureRenderer.CrumblingOverlay crumblingOverlay
    ) {
        collector.submitModel(
                this.canopyModel, Unit.INSTANCE, poseStack,
                material.renderType(/*? if >=1.21.11 {*/RenderTypes::entityNoOutline/*?} else {*//*RenderType::entityNoOutline*//*?}*/),
                light, overlay, color, this.materialSet.get(material), 0, crumblingOverlay
        );
    }

    //?} else {
    /*public void submit(
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
        submitCanopy(poseStack, multiBufferSource, light, overlay, glint, false, this.canopyModel.root(), UmbrellasClient.UMBRELLA_BASE, patterns);

        poseStack.popPose();
    }

    public static void submitCanopy(
            PoseStack poseStack,
            MultiBufferSource multiBufferSource,
            int light, int overlay,
            boolean glint, boolean solid,
            ModelPart canopy,
            Material baseSprite,
            UmbrellaPatternsComponent patterns
    ) {
        canopy.render(poseStack, baseSprite.buffer(multiBufferSource, RenderType::entitySolid, solid, glint), light, overlay);
        submitLayer(
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

            submitLayer(poseStack, multiBufferSource, light, overlay, canopy, material, pattern.value().dyeable() ? layer.color().getTextureDiffuseColor() : -1);
        }
    }

    private static void submitLayer(
            PoseStack poseStack, MultiBufferSource multiBufferSource, int light, int overlay, ModelPart canopy, Material material, int color
    ) {
        canopy.render(
                poseStack,
                material.buffer(multiBufferSource, RenderType::entityNoOutline),
                light, overlay,
                color
        );
    }
    *///?}

    public void getExtentsForGui(/*? if >=1.21.11 {*/Consumer<Vector3fc> input/*?} else {*//*Set<Vector3f> input*//*?}*/) {
        PoseStack poseStack = new PoseStack();
        poseStack.scale(0.6666667F, -0.6666667F, -0.6666667F);
        this.handleModel.root().getExtentsForGui(poseStack, input);
        this.canopyModel.root().getExtentsForGui(poseStack, input);
    }
}
