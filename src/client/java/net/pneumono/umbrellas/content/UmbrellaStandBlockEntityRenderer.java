package net.pneumono.umbrellas.content;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.phys.Vec3;
import net.pneumono.umbrellas.content.block.UmbrellaStandBlock;
import net.pneumono.umbrellas.content.block.UmbrellaStandBlockEntity;

//? if >=1.21.9 {
import it.unimi.dsi.fastutil.HashCommon;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
//?} else {
/*import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.pneumono.umbrellas.content.item.component.UmbrellaPatternsComponent;
import net.pneumono.umbrellas.registry.UmbrellasDataComponents;
import net.pneumono.umbrellas.registry.UmbrellasTags;
import java.util.Objects;
*///?}

public class UmbrellaStandBlockEntityRenderer implements BlockEntityRenderer<UmbrellaStandBlockEntity/*? if >=1.21.9 {*/, UmbrellaRenderState/*?}*/> {
    //? if >=1.21.9 {
    private final ItemModelResolver itemModelResolver;
    //?} else {
    /*private final UmbrellaRenderer renderer;
    *///?}

    public UmbrellaStandBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        //? if >=1.21.9 {
        this.itemModelResolver = context.itemModelResolver();
        //?} else {
        /*EntityModelSet set = context.getModelSet();
        this.renderer = new UmbrellaRenderer(set);
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
        state.item = new ItemStackRenderState();
        this.itemModelResolver.updateForTopItem(state.item, entity.getTheItem(), ItemDisplayContext.NONE, entity.getLevel(), null, HashCommon.long2int(entity.getBlockPos().asLong()));
    }

    @Override
    public void submit(UmbrellaRenderState state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState cameraRenderState) {
        poseStack.pushPose();
        poseStack.translate(0.5, 0.0, 0.5);
        float g = state.blockState.getValue(UmbrellaStandBlock.FACING).toYRot();
        poseStack.mulPose(Axis.YP.rotationDegrees(-g));
        //poseStack.translate(-0.5, 0.0, -0.5);
        poseStack.translate(0.03125, 1.5, 0.03125);
        state.item.submit(poseStack, submitNodeCollector, state.lightCoords, OverlayTexture.NO_OVERLAY, 0);
        poseStack.popPose();
    }

    //?} else {
    /*@Override
    public void render(UmbrellaStandBlockEntity entity, float tickProgress, PoseStack poseStack, MultiBufferSource multiBufferSource, int light, int overlay, Vec3 vec3) {
        ItemStack stack = entity.getTheItem();
        if (stack.isEmpty() || !stack.is(UmbrellasTags.UMBRELLAS)) return;

        int lightAbove = LevelRenderer.getLightColor(Objects.requireNonNull(entity.getLevel()), entity.getBlockPos().above(2));

        poseStack.pushPose();
        poseStack.translate(0.5, 0.0, 0.5);
        float g = entity.getBlockState().getValue(UmbrellaStandBlock.FACING).toYRot();
        poseStack.mulPose(Axis.YP.rotationDegrees(-g));
        poseStack.translate(-0.5, 0.0, -0.5);
        poseStack.translate(0.03125, 1.0, 0.03125);
        if (stack.getComponents().has(UmbrellasDataComponents.UMBRELLA_PATTERNS)) {
            renderPatternable(poseStack, multiBufferSource, lightAbove, overlay, stack);
        } else {
            renderNonPatternable(poseStack, multiBufferSource, lightAbove, overlay, stack, entity.getLevel());
        }
        poseStack.popPose();
    }

    private void renderPatternable(PoseStack poseStack, MultiBufferSource multiBufferSource, int light, int overlay, ItemStack stack) {
        this.renderer.submit(
                poseStack, multiBufferSource,
                light, overlay,
                stack.hasFoil(), 0.0F,
                stack.getOrDefault(UmbrellasDataComponents.UMBRELLA_PATTERNS, UmbrellaPatternsComponent.DEFAULT)
        );
    }

    private void renderNonPatternable(PoseStack poseStack, MultiBufferSource multiBufferSource, int light, int overlay, ItemStack stack, Level level) {
        poseStack.translate(0.5, 0.5, 0.5);
        Minecraft.getInstance().getItemRenderer().renderStatic(
                stack,
                ItemDisplayContext.NONE,
                light, overlay,
                poseStack, multiBufferSource,
                level, 0
        );
    }
    *///?}
}
