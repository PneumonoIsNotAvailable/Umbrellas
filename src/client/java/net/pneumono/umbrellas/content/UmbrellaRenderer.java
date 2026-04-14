//~ identifier_replacements

package net.pneumono.umbrellas.content;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.resources.model.sprite.SpriteGetter;
import net.minecraft.core.Holder;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.DyeColor;
import net.pneumono.umbrellas.Umbrellas;
import net.pneumono.umbrellas.UmbrellasClient;
import net.pneumono.umbrellas.content.item.component.UmbrellaPatternsComponent;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

//? if >=26.1 {
import net.minecraft.client.resources.model.sprite.SpriteId;
//?} else {
/*import net.minecraft.client.resources.model.Material;
*///?}

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

//? if >=26.1 {
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.util.Unit;
import org.jetbrains.annotations.Nullable;
//?} else if >=1.21.9 {
/*import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.resources.model.MaterialSet;
import net.minecraft.util.Unit;
import org.jetbrains.annotations.Nullable;
*///?} else {
/*import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import org.jetbrains.annotations.Nullable;
*///?}

public class UmbrellaRenderer {
    //~ if >=26.1 'Material' -> 'SpriteId'
    public static final Map<Identifier, SpriteId> UMBRELLA_PATTERN_TEXTURES = new HashMap<>();

    //? if >=1.21.9
    private final /*? if >=26.1 {*/SpriteGetter/*?} else if >=1.21.9 {*//*MaterialSet*//*?}*/ spriteGetter;

    private final UmbrellaModel.Handle handleModel;
    private final UmbrellaModel.Canopy canopyModel;

    public UmbrellaRenderer(
            EntityModelSet models
            /*? if >=26.1 {*/, SpriteGetter spriteGetter/*?} else if >=1.21.9 {*//*, MaterialSet spriteGetter*//*?}*/
    ) {
        //? if >=1.21.9
        this.spriteGetter = spriteGetter;

        this.handleModel = new UmbrellaModel.Handle(models.bakeLayer(UmbrellasClient.UMBRELLA_HANDLE_LAYER));
        this.canopyModel = new UmbrellaModel.Canopy(models.bakeLayer(UmbrellasClient.UMBRELLA_CANOPY_LAYER));
    }

    //~ if >=26.1 'Material' -> 'SpriteId'
    public static SpriteId getUmbrellaPatternTextureId(Holder<UmbrellaPattern> pattern) {
        return UMBRELLA_PATTERN_TEXTURES.computeIfAbsent(pattern.value().assetId(), UmbrellasClient::getUmbrellaSpriteId);
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
        collector.submitModel(
                this.handleModel, Unit.INSTANCE,
                poseStack,
                UmbrellasClient.UMBRELLA_BASE.renderType(RenderTypes::entitySolid),
                light, overlay, -1,
                spriteGetter.get(UmbrellasClient.UMBRELLA_BASE),
                k, crumblingOverlay
        );

        if (foil) {
            collector.submitModel(
                    this.handleModel, Unit.INSTANCE,
                    poseStack,
                    RenderTypes.entityGlint(),
                    light, overlay, -1,
                    spriteGetter.get(UmbrellasClient.UMBRELLA_BASE),
                    k, crumblingOverlay
            );
        }
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
                light, overlay, null,
                UmbrellasClient.UMBRELLA_BASE,
                RenderTypes::entitySolid,
                collector,
                /*? if >=1.21.9 {*/k, crumblingOverlay/*?} else {*//*foil*//*?}*/
        );

        //? if >=1.21.9 {
        if (foil) {
            submitLayer(
                    poseStack,
                    light, overlay, null,
                    UmbrellasClient.UMBRELLA_BASE,
                    RenderTypes.entityGlint(),
                    collector,
                    k, crumblingOverlay
            );
        }
        //?}

        submitLayer(
                poseStack,
                light, overlay, baseColor,
                UmbrellasClient.getUmbrellaSpriteId(Umbrellas.id("base")),
                RenderTypes::/*? if >=26.1 {*/bannerPattern/*?} else {*//*entityNoOutline*//*?}*/,
                collector,
                /*? if >=1.21.9 {*/k, crumblingOverlay/*?} else {*//*false*//*?}*/
        );

        for (int i = 0; i < 16 && i < patterns.layers().size(); i++) {
            UmbrellaPatternsComponent.Layer layer = patterns.layers().get(i);
            Holder<UmbrellaPattern> pattern = layer.pattern();

            DyeColor color = pattern.value().dyeable() ? layer.color() : null;
            submitLayer(
                    poseStack,
                    light, overlay, color,
                    getUmbrellaPatternTextureId(pattern),
                    RenderTypes::/*? if >=26.1 {*/bannerPattern/*?} else {*//*entityNoOutline*//*?}*/,
                    collector,
                    /*? if >=1.21.9 {*/k, crumblingOverlay/*?} else {*//*false*//*?}*/
            );
        }
    }

    private void submitLayer(
            PoseStack poseStack,
            int light, int overlay,
            @Nullable DyeColor color,
            //~ if >=26.1 'Material' -> 'SpriteId'
            SpriteId material,
            Function<Identifier, RenderType> renderTypeFunction,
            //? if >=1.21.9 {
            SubmitNodeCollector collector, int k,
            @Nullable ModelFeatureRenderer.CrumblingOverlay crumblingOverlay
            //?} else {
            /*MultiBufferSource collector, boolean foil
            *///?}
    ) {
        submitLayer(
                poseStack, light, overlay, color, material,
                material.renderType(renderTypeFunction),
                collector,
                /*? if >=1.21.9 {*/k, crumblingOverlay/*?} else {*//*foil*//*?}*/
        );
    }

    private void submitLayer(
            PoseStack poseStack,
            int light, int overlay,
            @Nullable DyeColor color,
            //~ if >=26.1 'Material' -> 'SpriteId'
            SpriteId material,
            RenderType renderType,
            //? if >=1.21.9 {
            SubmitNodeCollector collector, int k,
            @Nullable ModelFeatureRenderer.CrumblingOverlay crumblingOverlay
            //?} else {
            /*MultiBufferSource collector, boolean foil
            *///?}
    ) {
        //? if >=1.21.9 {
        int diffuseColor = color == null ? -1 : color.getTextureDiffuseColor();
        collector.submitModel(
                this.canopyModel, Unit.INSTANCE,
                poseStack,
                renderType,
                light, overlay, diffuseColor,
                spriteGetter.get(material),
                k, crumblingOverlay
        );
        //?} else {
        /*//? if >=1.21 {
        int diffuseColor = color == null ? -1 : color.getTextureDiffuseColor();
        //?} else {
        /^float[] diffuseColors = color == null ? new float[]{1, 1, 1} : color.getTextureDiffuseColors();
        ^///?}
        this.canopyModel.root().render(
                poseStack,
                material.sprite().wrap(
                        ItemRenderer./^? if >=1.21.6 {^/getFoilBuffer/^?} else {^//^getFoilBufferDirect^//^?}^/(
                                collector, renderType, true, foil
                        )
                ),
                light, overlay,
                //? if >=1.21 {
                diffuseColor
                //?} else {
                /^diffuseColors[0], diffuseColors[1], diffuseColors[2], 1
                ^///?}
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
