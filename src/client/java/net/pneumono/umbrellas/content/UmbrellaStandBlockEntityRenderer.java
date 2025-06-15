package net.pneumono.umbrellas.content;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;
import net.pneumono.umbrellas.content.block.UmbrellaStandBlockEntity;
import net.pneumono.umbrellas.content.item.PatternableUmbrellaItem;
import net.pneumono.umbrellas.content.item.component.UmbrellaPatternsComponent;
import net.pneumono.umbrellas.registry.UmbrellasDataComponents;

import java.util.Objects;

public class UmbrellaStandBlockEntityRenderer implements BlockEntityRenderer<UmbrellaStandBlockEntity> {
    private final UmbrellaRenderer renderer;

    public UmbrellaStandBlockEntityRenderer(BlockEntityRendererFactory.Context context) {
        this.renderer = new UmbrellaRenderer(context.getLoadedEntityModels());
    }

    @Override
    public void render(UmbrellaStandBlockEntity entity, float tickProgress, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, Vec3d cameraPos) {
        ItemStack stack = entity.getStack();
        if (stack.isEmpty() || !(stack.getItem() instanceof PatternableUmbrellaItem umbrellaItem)) return;

        int lightAbove = WorldRenderer.getLightmapCoordinates(Objects.requireNonNull(entity.getWorld()), entity.getPos().up(2));

        matrices.push();

        matrices.translate(0.03125, 1.0, 0.03125);
        this.renderer.render(
                matrices, vertexConsumers,
                lightAbove, overlay,
                stack.hasGlint(), 0.0F,
                umbrellaItem.getColor(),
                stack.getOrDefault(UmbrellasDataComponents.UMBRELLA_PATTERNS, UmbrellaPatternsComponent.DEFAULT)
        );

        matrices.pop();
    }
}
