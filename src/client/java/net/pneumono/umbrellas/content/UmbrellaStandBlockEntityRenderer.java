package net.pneumono.umbrellas.content;

import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;

import java.util.Objects;

public class UmbrellaStandBlockEntityRenderer implements BlockEntityRenderer<UmbrellaStandBlockEntity> {
    @SuppressWarnings("unused")
    public UmbrellaStandBlockEntityRenderer(BlockEntityRendererFactory.Context context) {}

    @Override
    public void render(UmbrellaStandBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        matrices.push();

        matrices.translate(0.53125, 1.5, 0.53125);
        BlockState state = Objects.requireNonNull(entity.getWorld()).getBlockState(entity.getPos());
        if (state.getBlock() instanceof UmbrellaStandBlock && state.get(UmbrellaStandBlock.HAS_UMBRELLA)) {
            int lightAbove = WorldRenderer.getLightmapCoordinates(Objects.requireNonNull(entity.getWorld()), entity.getPos().up(2));
            ItemStack entityStack = entity.getStack();
            MinecraftClient.getInstance().getItemRenderer().renderItem(entityStack, ModelTransformationMode.NONE, lightAbove, OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, entity.getWorld(), 0);
        }

        matrices.pop();
    }
}
