package net.pneumono.umbrellas.content;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.DyeColor;
import net.pneumono.umbrellas.UmbrellasClient;
import net.pneumono.umbrellas.content.item.component.UmbrellaPatternsComponent;

public class LoomUmbrellaRendering {
    public static void drawOutputUmbrella(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int x, int y, ModelPart umbrella, DyeColor color, UmbrellaPatternsComponent patterns) {
        matrices.push();

        matrices.translate((float)(x + 139), (float)(y + 52), 0.0F);
        matrices.scale(1.0F, -1.0F, 1.0F);
        matrices.translate(0.6666667F, 35.3333333F, 0.0F);
        matrices.scale(24.0F, -24.0F, -1.0F);

        render(matrices, vertexConsumers, umbrella, color, patterns);

        matrices.pop();
    }
    
    public static void drawPatternUmbrella(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int x, int y, ModelPart umbrella, RegistryEntry<UmbrellaPattern> pattern) {
        matrices.push();

        matrices.translate(x, y, 0.0F);
        matrices.scale(0.5F, 0.5F, 1.0F);
        matrices.scale(0.3333333F, 0.3333333F, 1.0F);
        matrices.translate(19.5F, 19.5F, 0.0F);
        matrices.scale(3.0F, 3.0F, 1.0F);
        matrices.scale(16.0F, 16.0F, -1.0F);

        render(matrices, vertexConsumers, umbrella, DyeColor.GRAY, new UmbrellaPatternsComponent.Builder().add(pattern, DyeColor.WHITE).build());

        matrices.pop();
    }

    private static void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, ModelPart umbrella, DyeColor color, UmbrellaPatternsComponent patterns) {
        float prevPitch = umbrella.pitch;
        umbrella.pitch = (float)Math.toRadians(90);

        UmbrellaRenderer.renderCanopy(
                matrices,
                vertexConsumers,
                15728880,
                OverlayTexture.DEFAULT_UV,
                false, true,
                umbrella,
                UmbrellasClient.UMBRELLA_BASE,
                color,
                patterns
        );

        umbrella.pitch = prevPitch;
    }
}
