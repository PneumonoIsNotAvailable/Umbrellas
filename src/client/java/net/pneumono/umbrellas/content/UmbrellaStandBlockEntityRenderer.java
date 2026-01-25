package net.pneumono.umbrellas.content;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.pneumono.umbrellas.content.block.UmbrellaStandBlock;
import net.pneumono.umbrellas.content.block.UmbrellaStandBlockEntity;
import net.pneumono.umbrellas.content.item.component.UmbrellaPatternsComponent;
import net.pneumono.umbrellas.registry.UmbrellasDataComponents;

//? if >=1.21.9 {
import it.unimi.dsi.fastutil.HashCommon;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
//?} else {
/*import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
*///?}

public class UmbrellaStandBlockEntityRenderer implements BlockEntityRenderer<UmbrellaStandBlockEntity/*? if >=1.21.9 {*/, UmbrellaStandBlockEntityRenderer.UmbrellaRenderState/*?}*/> {
    //? if >=1.21.9
    private final ItemModelResolver itemModelResolver;

    private final UmbrellaRenderer umbrellaRenderer;

    public UmbrellaStandBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        //? if >=1.21.9 {
        this.itemModelResolver = context.itemModelResolver();
        this.umbrellaRenderer = new UmbrellaRenderer(context.entityModelSet(), context.materials());
        //?} else {
        /*this.umbrellaRenderer = new UmbrellaRenderer(context.getModelSet());
        *///?}
    }

    //? if >=1.21.9 {
    @Override
    public @NotNull UmbrellaRenderState createRenderState() {
        return new UmbrellaRenderState();
    }

    @Override
    public void extractRenderState(UmbrellaStandBlockEntity entity, UmbrellaRenderState state, float f, Vec3 vec3, @Nullable ModelFeatureRenderer.CrumblingOverlay crumblingOverlay) {
        BlockEntityRenderer.super.extractRenderState(entity, state, f, vec3, crumblingOverlay);
        ItemStack stack = entity.getTheItem();
        state.patterns = stack.get(UmbrellasDataComponents.UMBRELLA_PATTERNS);
        state.foil = stack.hasFoil();
        state.item = new ItemStackRenderState();
        this.itemModelResolver.updateForTopItem(state.item, entity.getTheItem(), ItemDisplayContext.NONE, entity.getLevel(), null, HashCommon.long2int(entity.getBlockPos().asLong()));
    }

    public static class UmbrellaRenderState extends BlockEntityRenderState {
        public ItemStackRenderState item;
        public UmbrellaPatternsComponent patterns;
        public boolean foil;
    }

    @Override
    public void submit(UmbrellaRenderState state, PoseStack poseStack, SubmitNodeCollector collector, CameraRenderState cameraRenderState) {
        if (state.patterns == null) {
            poseStack.pushPose();
            translate(poseStack, state.blockState, true);
            state.item.submit(poseStack, collector, state.lightCoords, OverlayTexture.NO_OVERLAY, 0);
            poseStack.popPose();
        } else {
            submit(state.patterns, state.blockState, poseStack, state.lightCoords, OverlayTexture.NO_OVERLAY, state.foil, collector, state.breakProgress);
        }
    }

    //?} else {
    /*@Override
    public void render(UmbrellaStandBlockEntity entity, float tickProgress, PoseStack poseStack, MultiBufferSource collector, int light, int overlay, Vec3 vec3) {
        ItemStack stack = entity.getTheItem();
        UmbrellaPatternsComponent patterns = stack.get(UmbrellasDataComponents.UMBRELLA_PATTERNS);
        boolean foil = stack.hasFoil();

        if (patterns == null) {
            poseStack.pushPose();
            translate(poseStack, entity.getBlockState(), true);
            Minecraft.getInstance().getItemRenderer().renderStatic(
                    entity.getTheItem(),
                    ItemDisplayContext.NONE,
                    light, overlay,
                    poseStack, collector,
                    entity.getLevel(), 0
            );
            poseStack.popPose();
        } else {
            submit(patterns, entity.getBlockState(), poseStack, light, overlay, foil, collector);
        }
    }
    *///?}

    public void submit(
            UmbrellaPatternsComponent patterns, BlockState state, PoseStack poseStack,
            int light, int overlay, boolean foil,
            //? if >=1.21.9 {
            SubmitNodeCollector collector,
            @Nullable ModelFeatureRenderer.CrumblingOverlay crumblingOverlay
            //?} else {
            /*MultiBufferSource collector
            *///?}
    ) {
        poseStack.pushPose();

        translate(poseStack, state, false);

        this.umbrellaRenderer.submit(
                patterns, poseStack,
                light, overlay, foil,
                collector/*? if >=1.21.9 {*/, 0, crumblingOverlay/*?}*/
        );

        poseStack.popPose();
    }

    public void translate(PoseStack poseStack, BlockState state, boolean item) {
        poseStack.translate(0.5F, 0.0F, 0.5F);
        float rotation = state.getValue(UmbrellaStandBlock.FACING).toYRot();
        poseStack.mulPose(Axis.YP.rotationDegrees(-rotation));
        if (!item) poseStack.translate(-0.5F, -0.5F, -0.5F);

        poseStack.translate(0.03125F, 1.5F, 0.03125F);
    }
}
