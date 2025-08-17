package net.pneumono.umbrellas.content;

import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.texture.Sprite;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.DyeColor;
import net.pneumono.umbrellas.Umbrellas;
import net.pneumono.umbrellas.UmbrellasClient;
import net.pneumono.umbrellas.content.item.component.UmbrellaPatternsComponent;
import net.pneumono.umbrellas.registry.UmbrellasDataComponents;
import org.joml.Matrix3x2fStack;

import java.util.ArrayList;
import java.util.List;

public class LoomUmbrellaRendering {
    public static int TEXTURE_SIZE = 21;

    public static void drawResultUmbrella(DrawContext context, int x, int y, ItemStack stack) {
        Matrix3x2fStack matrices = context.getMatrices();
        matrices.pushMatrix();
        matrices.translate(x, y);
        matrices.scale(32F / TEXTURE_SIZE);

        UmbrellaPatternsComponent component = stack.getOrDefault(UmbrellasDataComponents.UMBRELLA_PATTERNS, UmbrellaPatternsComponent.DEFAULT);
        DyeColor baseColor = component.baseColor();

        List<Layer> layers = new ArrayList<>();
        layers.add(new Layer(UmbrellasClient.UMBRELLA_PATTERN_SPRITE_MAPPER.map(Umbrellas.id("base")).getSprite(), baseColor.getEntityColor()));
        layers.addAll(getLayers(component));

        draw2DUmbrellaCanopy(context, layers.toArray(Layer[]::new));

        matrices.popMatrix();
    }

    public static List<Layer> getLayers(UmbrellaPatternsComponent component) {
        return component.layers().stream().map(layer -> new Layer(UmbrellaRenderer.getUmbrellaPatternTextureId(layer.pattern()).getSprite(), layer.color().getEntityColor())).toList();
    }

    public static void drawPatternUmbrella(DrawContext context, int x, int y, RegistryEntry<UmbrellaPattern> pattern) {
        Matrix3x2fStack matrices = context.getMatrices();
        matrices.pushMatrix();
        matrices.translate(x + 2, y + 2);
        matrices.scale(10F / TEXTURE_SIZE);

        draw2DUmbrellaCanopy(context,
                new Layer(UmbrellasClient.UMBRELLA_PATTERN_SPRITE_MAPPER.map(Umbrellas.id("base")).getSprite(), DyeColor.GRAY.getEntityColor()),
                new Layer(UmbrellaRenderer.getUmbrellaPatternTextureId(pattern).getSprite(), DyeColor.WHITE.getEntityColor())
        );

        matrices.popMatrix();
    }

    public static void draw2DUmbrellaCanopy(DrawContext context, Layer... layers) {
        for (Layer layer : layers) {
            draw2DUmbrellaCanopyLayer(context, layer.sprite, layer.color);
        }
    }

    public static void draw2DUmbrellaCanopyLayer(DrawContext context, Sprite sprite, int color) {
        Matrix3x2fStack matrices = context.getMatrices();
        matrices.pushMatrix();

        float width = sprite.getMaxU() - sprite.getMinU();
        float height = sprite.getMaxV() - sprite.getMinV();

        float u1 = sprite.getMinU() + width * 21F / 128F;
        float u2 = u1 + width * 21F / 128F;

        float v1 = sprite.getMinV() + height * 0F / 128F;
        float v2 = v1 + height * 21F / 128F;

        context.drawTexturedQuad(RenderPipelines.GUI_TEXTURED, sprite.getAtlasId(), 0, 21, 0, 21, u1, u2, v1, v2, color);

        u1 = sprite.getMinU() + width * 19F / 128F;
        u2 = u1 + width * 19F / 128F;

        v1 = sprite.getMinV() + height * 24F / 128F;
        v2 = v1 + height * 19F / 128F;

        context.drawTexturedQuad(RenderPipelines.GUI_TEXTURED, sprite.getAtlasId(), 1, 20, 1, 20, u1, u2, v1, v2, color);

        u1 = sprite.getMinU() + width * 15F / 128F;
        u2 = u1 + width * 15F / 128F;

        v1 = sprite.getMinV() + height * 46F / 128F;
        v2 = v1 + height * 15F / 128F;

        context.drawTexturedQuad(RenderPipelines.GUI_TEXTURED, sprite.getAtlasId(), 3, 18, 3, 18, u1, u2, v1, v2, color);

        matrices.popMatrix();
    }

    public record Layer(Sprite sprite, int color) { }
}
