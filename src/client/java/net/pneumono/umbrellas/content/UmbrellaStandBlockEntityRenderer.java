package net.pneumono.umbrellas.content;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.pneumono.umbrellas.content.block.UmbrellaStandBlock;
import net.pneumono.umbrellas.content.block.UmbrellaStandBlockEntity;
import net.pneumono.umbrellas.content.item.component.UmbrellaPatternsComponent;
import net.pneumono.umbrellas.registry.UmbrellasDataComponents;
import net.pneumono.umbrellas.registry.UmbrellasTags;

import java.util.Objects;

public class UmbrellaStandBlockEntityRenderer implements BlockEntityRenderer<UmbrellaStandBlockEntity> {
    private final UmbrellaRenderer renderer;

    public UmbrellaStandBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        this.renderer = new UmbrellaRenderer(context.getModelSet());
    }

    @Override
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
        this.renderer.render(
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
}
