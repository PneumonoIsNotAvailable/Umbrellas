package net.pneumono.umbrellas.content;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.pneumono.umbrellas.content.block.UmbrellaStandBlock;
import net.pneumono.umbrellas.content.block.UmbrellaStandBlockEntity;

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

    public UmbrellaStandBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        //? if >=1.21.9
        this.itemModelResolver = context.itemModelResolver();
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

    public static class UmbrellaRenderState extends BlockEntityRenderState {
        public ItemStackRenderState item;
    }

    @Override
    public void submit(UmbrellaRenderState state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState cameraRenderState) {
        poseStack.pushPose();
        adjustPoseStack(poseStack, state.blockState);
        state.item.submit(poseStack, submitNodeCollector, state.lightCoords, OverlayTexture.NO_OVERLAY, 0);
        poseStack.popPose();
    }

    //?} else {
    /*@Override
    public void render(UmbrellaStandBlockEntity entity, float tickProgress, PoseStack poseStack, MultiBufferSource multiBufferSource, int light, int overlay, Vec3 vec3) {
        poseStack.pushPose();
        adjustPoseStack(poseStack, entity.getBlockState());

        Minecraft.getInstance().getItemRenderer().renderStatic(
                entity.getTheItem(),
                ItemDisplayContext.NONE,
                light, overlay,
                poseStack, multiBufferSource,
                entity.getLevel(), 0
        );
        poseStack.popPose();
    }
    *///?}

    public static void adjustPoseStack(PoseStack poseStack, BlockState state) {
        poseStack.translate(0.5, 0.0, 0.5);
        float g = state.getValue(UmbrellaStandBlock.FACING).toYRot();
        poseStack.mulPose(Axis.YP.rotationDegrees(-g));
        poseStack.translate(0.03125, 1.5, 0.03125);
    }
}
