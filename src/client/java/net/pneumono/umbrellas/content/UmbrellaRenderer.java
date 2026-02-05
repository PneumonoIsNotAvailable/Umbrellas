//~ identifier_replacements

package net.pneumono.umbrellas.content;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.resources.model.Material;
import net.minecraft.core.Holder;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.DyeColor;
import net.pneumono.umbrellas.Umbrellas;
import net.pneumono.umbrellas.UmbrellasClient;
import net.pneumono.umbrellas.content.item.component.UmbrellaPatternsComponent;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

//? if >=1.21.11 {
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
//?} else {
/*import net.minecraft.client.renderer.RenderType;
*///?}

//? if >=1.21.11 {
import java.util.function.Consumer;
import org.joml.Vector3fc;
//?} else if >=1.21.6 {
/*import java.util.Set;
import org.joml.Vector3f;
*///?}

//? if >=1.21.9 {
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.resources.model.MaterialSet;
import org.jetbrains.annotations.Nullable;
//?} else {
/*import net.minecraft.client.renderer.MultiBufferSource;
import org.jetbrains.annotations.Nullable;
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
        return UMBRELLA_PATTERN_TEXTURES.computeIfAbsent(pattern.value().assetId(), UmbrellasClient::getUmbrellaMaterial);
    }

    public void submit(
            DyeColor baseColor,
            UmbrellaPatternsComponent patterns,
            PoseStack poseStack,
            int light, int overlay, boolean foil,
            //? if >=1.21.9 {
            SubmitNodeCollector collector, int k,
            @Nullable ModelFeatureRenderer.CrumblingOverlay crumblingOverlay
            //?} else {
            /*MultiBufferSource collector
            *///?}
    ) {
        poseStack.pushPose();
        poseStack.scale(1.0F, -1.0F, -1.0F);

        submitHandle(poseStack, light, overlay, foil, collector/*? if >=1.21.9 {*/, k, crumblingOverlay/*?}*/);
        submitCanopy(baseColor, patterns, poseStack, light, overlay, foil, collector/*? if >=1.21.9 {*/, k, crumblingOverlay/*?}*/);

        poseStack.popPose();
    }

    public void submitHandle(
            PoseStack poseStack,
            int light, int overlay, boolean foil,
            //? if >=1.21.9 {
            SubmitNodeCollector collector, int k,
            @Nullable ModelFeatureRenderer.CrumblingOverlay crumblingOverlay
            //?} else {
            /*MultiBufferSource collector
            *///?}
    ) {
        //? if >=1.21.9 {
        collector.submitModelPart(
                this.handleModel.root(), poseStack,
                UmbrellasClient.UMBRELLA_BASE.renderType(RenderTypes::entitySolid),
                light, overlay,
                materialSet.get(UmbrellasClient.UMBRELLA_BASE),
                false, foil, -1,
                crumblingOverlay, k
        );
        //?} else {
        /*this.handleModel.root().render(
                poseStack,
                UmbrellasClient.UMBRELLA_BASE.buffer(
                        collector,
                        RenderTypes::entitySolid,
                        /^? if >=1.21.6 {^/false, /^?}^/
                        foil
                ),
                light, overlay
        );
        *///?}
    }

    public void submitCanopy(
            DyeColor baseColor,
            UmbrellaPatternsComponent patterns,
            PoseStack poseStack,
            int light, int overlay, boolean foil,
            //? if >=1.21.9 {
            SubmitNodeCollector collector, int k,
            @Nullable ModelFeatureRenderer.CrumblingOverlay crumblingOverlay
            //?} else {
            /*MultiBufferSource collector
            *///?}
    ) {
        submitLayer(
                poseStack,
                light, overlay, foil, null,
                UmbrellasClient.UMBRELLA_BASE,
                RenderTypes::entitySolid,
                collector/*? if >=1.21.9 {*/, k, crumblingOverlay/*?}*/
        );
        submitLayer(
                poseStack,
                light, overlay, false, baseColor,
                UmbrellasClient.getUmbrellaMaterial(Umbrellas.id("base")),
                RenderTypes::entityNoOutline,
                collector/*? if >=1.21.9 {*/, 0, crumblingOverlay/*?}*/
        );

        for (int i = 0; i < 16 && i < patterns.layers().size(); i++) {
            UmbrellaPatternsComponent.Layer layer = patterns.layers().get(i);
            Holder<UmbrellaPattern> pattern = layer.pattern();

            DyeColor color = pattern.value().dyeable() ? layer.color() : null;
            submitLayer(
                    poseStack,
                    light, overlay, false, color,
                    getUmbrellaPatternTextureId(pattern),
                    RenderTypes::entityNoOutline,
                    collector/*? if >=1.21.9 {*/, 0, crumblingOverlay/*?}*/
            );
        }
    }

    private void submitLayer(
            PoseStack poseStack,
            int light, int overlay, boolean foil,
            @Nullable DyeColor color,
            Material material,
            Function<Identifier, RenderType> renderTypeFunction,
            //? if >=1.21.9 {
            SubmitNodeCollector collector, int k,
            @Nullable ModelFeatureRenderer.CrumblingOverlay crumblingOverlay
            //?} else {
            /*MultiBufferSource collector
            *///?}
    ) {
        //? if >=1.21.9 {
        int diffuseColor = color == null ? -1 : color.getTextureDiffuseColor();
        collector.submitModelPart(
                this.canopyModel.root(), poseStack,
                material.renderType(renderTypeFunction),
                light, overlay,
                this.materialSet.get(material),
                false, foil, diffuseColor,
                crumblingOverlay, k
        );
        //?} else if >=1.21 {
        /*int diffuseColor = color == null ? -1 : color.getTextureDiffuseColor();
        this.canopyModel.root().render(
                poseStack,
                material.buffer(
                        collector,
                        renderTypeFunction,
                        /^? if >=1.21.6 {^//^false, ^//^?}^/
                        foil
                ),
                light, overlay,
                diffuseColor
        );
        *///?} else {
        /*float[] diffuseColors = color == null ? new float[]{1, 1, 1} : color.getTextureDiffuseColors();
        this.canopyModel.root().render(
                poseStack,
                material.buffer(collector, renderTypeFunction, foil),
                light, overlay,
                diffuseColors[0], diffuseColors[1], diffuseColors[2], 1
        );
        *///?}
    }

    //? if >=1.21.6 {
    public void getExtentsForGui(/*? if >=1.21.11 {*/Consumer<Vector3fc> input/*?} else {*//*Set<Vector3f> input*//*?}*/) {
        PoseStack poseStack = new PoseStack();
        poseStack.scale(0.6666667F, -0.6666667F, -0.6666667F);
        this.handleModel.root().getExtentsForGui(poseStack, input);
        this.canopyModel.root().getExtentsForGui(poseStack, input);
    }
    //?}
}
