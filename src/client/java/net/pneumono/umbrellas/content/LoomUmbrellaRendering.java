package net.pneumono.umbrellas.content;

import net.fabricmc.fabric.api.client.rendering.v1.SpecialGuiElementRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.ScreenRect;
import net.minecraft.client.gui.render.SpecialGuiElementRenderer;
import net.minecraft.client.gui.render.state.special.SpecialGuiElementRenderState;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.DiffuseLighting;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.DyeColor;
import net.pneumono.umbrellas.Umbrellas;
import net.pneumono.umbrellas.UmbrellasClient;
import net.pneumono.umbrellas.content.item.component.UmbrellaPatternsComponent;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix3x2fStack;

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
    public static void drawPatternUmbrella(DrawContext context, int x, int y, Sprite sprite) {
        draw2DUmbrellaCanopy(context, x, y, UmbrellasClient.UMBRELLA_PATTERN_SPRITE_MAPPER.map(Umbrellas.id("base")).getSprite(), DyeColor.GRAY);
        draw2DUmbrellaCanopy(context, x, y, sprite, DyeColor.WHITE);
    }

    public static void draw2DUmbrellaCanopy(DrawContext context, int x, int y, Sprite sprite, DyeColor color) {
        Matrix3x2fStack matrices = context.getMatrices();
        matrices.pushMatrix();

        float width = sprite.getMaxU() - sprite.getMinU();
        float height = sprite.getMaxV() - sprite.getMinV();

        matrices.translate(x + 2, y + 2);
        matrices.scale(10F/21F);

        float u1 = sprite.getMinU() + width * 21F / 128F;
        float u2 = u1 + width * 21F / 128F;

        float v1 = sprite.getMinV() + height * 0F / 128F;
        float v2 = v1 + height * 21F / 128F;

        context.drawTexturedQuad(RenderPipelines.GUI_TEXTURED, sprite.getAtlasId(), 0, 21, 0, 21, u1, u2, v1, v2, color.getEntityColor());

        matrices.translate(1, 1);
        u1 = sprite.getMinU() + width * 19F / 128F;
        u2 = u1 + width * 19F / 128F;

        v1 = sprite.getMinV() + height * 24F / 128F;
        v2 = v1 + height * 19F / 128F;

        context.drawTexturedQuad(RenderPipelines.GUI_TEXTURED, sprite.getAtlasId(), 0, 19, 0, 19, u1, u2, v1, v2, color.getEntityColor());

        matrices.translate(2, 2);
        u1 = sprite.getMinU() + width * 15F / 128F;
        u2 = u1 + width * 15F / 128F;

        v1 = sprite.getMinV() + height * 46F / 128F;
        v2 = v1 + height * 15F / 128F;

        context.drawTexturedQuad(RenderPipelines.GUI_TEXTURED, sprite.getAtlasId(), 0, 15, 0, 15, u1, u2, v1, v2, color.getEntityColor());

        matrices.popMatrix();
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

    public record ResultGuiElementRenderState(
            ModelPart canopy,
            DyeColor baseColor,
            UmbrellaPatternsComponent resultUmbrellaPatterns,
            int x1,
            int y1,
            int x2,
            int y2,
            @Nullable ScreenRect scissorArea,
            @Nullable ScreenRect bounds
    ) implements SpecialGuiElementRenderState {
        public ResultGuiElementRenderState(
                ModelPart flag, DyeColor color, UmbrellaPatternsComponent umbrellaPatterns, int x1, int y1, int x2, int y2, @Nullable ScreenRect scissorArea
        ) {
            this(flag, color, umbrellaPatterns, x1, y1, x2, y2, scissorArea, SpecialGuiElementRenderState.createBounds(x1, y1, x2, y2, scissorArea));
        }

        @Override
        public float scale() {
            return 16.0F;
        }
    }

    public static class UmbrellaResultGuiElementRenderer extends SpecialGuiElementRenderer<ResultGuiElementRenderState> {
        public UmbrellaResultGuiElementRenderer(VertexConsumerProvider.Immediate immediate) {
            super(immediate);
        }

        public UmbrellaResultGuiElementRenderer(SpecialGuiElementRegistry.Context context) {
            this(context.vertexConsumers());
        }

        @Override
        public Class<ResultGuiElementRenderState> getElementClass() {
            return ResultGuiElementRenderState.class;
        }

        protected void render(ResultGuiElementRenderState resultGuiElementRenderState, MatrixStack matrixStack) {
            MinecraftClient.getInstance().gameRenderer.getDiffuseLighting().setShaderLights(DiffuseLighting.Type.ITEMS_FLAT);
            matrixStack.translate(0.0F, 0.25F, 0.0F);
            LoomUmbrellaRendering.drawOutputUmbrella(
                    matrixStack,
                    this.vertexConsumers,
                    15728880,
                    OverlayTexture.DEFAULT_UV,
                    resultGuiElementRenderState.canopy(),
                    resultGuiElementRenderState.baseColor(),
                    resultGuiElementRenderState.resultUmbrellaPatterns()
            );
        }

        @Override
        protected String getName() {
            return "banner result";
        }
    }
}
