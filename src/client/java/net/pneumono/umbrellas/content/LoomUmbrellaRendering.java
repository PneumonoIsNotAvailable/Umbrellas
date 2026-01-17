package net.pneumono.umbrellas.content;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.Material;
import net.minecraft.core.Holder;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.pneumono.umbrellas.Umbrellas;
import net.pneumono.umbrellas.UmbrellasClient;
import net.pneumono.umbrellas.content.item.component.UmbrellaPatternsComponent;
import net.pneumono.umbrellas.registry.UmbrellasDataComponents;
import org.joml.Matrix3x2fStack;

import java.util.ArrayList;
import java.util.List;

public class LoomUmbrellaRendering {
    public static int TEXTURE_SIZE = 21;

    public static void drawResultUmbrella(GuiGraphics graphics, int x, int y, ItemStack stack) {
        Matrix3x2fStack matrices = graphics.pose();
        matrices.pushMatrix();
        matrices.translate(x, y);
        matrices.scale(32F / TEXTURE_SIZE);

        UmbrellaPatternsComponent component = stack.getOrDefault(UmbrellasDataComponents.UMBRELLA_PATTERNS, UmbrellaPatternsComponent.DEFAULT);
        DyeColor baseColor = component.baseColor();

        List<Layer> layers = new ArrayList<>();
        layers.add(new Layer(graphics, UmbrellasClient.UMBRELLA_PATTERN_SPRITE_MAPPER.apply(Umbrellas.id("base")), baseColor.getTextureDiffuseColor()));
        layers.addAll(getLayers(graphics, component));

        draw2DUmbrellaCanopy(graphics, layers.toArray(Layer[]::new));

        matrices.popMatrix();
    }

    public static List<Layer> getLayers(GuiGraphics graphics, UmbrellaPatternsComponent component) {
        return component.layers().stream().map(layer -> new Layer(graphics, UmbrellaRenderer.getUmbrellaPatternTextureId(layer.pattern()), layer.color().getTextureDiffuseColor())).toList();
    }

    public static void drawPatternUmbrella(GuiGraphics graphics, int x, int y, Holder<UmbrellaPattern> pattern) {
        Matrix3x2fStack matrices = graphics.pose();
        matrices.pushMatrix();
        matrices.translate(x + 2, y + 2);
        matrices.scale(10F / TEXTURE_SIZE);

        draw2DUmbrellaCanopy(graphics,
                new Layer(graphics, UmbrellasClient.UMBRELLA_PATTERN_SPRITE_MAPPER.apply(Umbrellas.id("base")), DyeColor.GRAY.getTextureDiffuseColor()),
                new Layer(graphics, UmbrellaRenderer.getUmbrellaPatternTextureId(pattern), DyeColor.WHITE.getTextureDiffuseColor())
        );

        matrices.popMatrix();
    }

    public static void draw2DUmbrellaCanopy(GuiGraphics graphics, Layer... layers) {
        for (Layer layer : layers) {
            draw2DUmbrellaCanopyLayer(graphics, layer.sprite, layer.color);
        }
    }

    public static void draw2DUmbrellaCanopyLayer(GuiGraphics graphics, TextureAtlasSprite sprite, int color) {
        Matrix3x2fStack matrices = graphics.pose();
        matrices.pushMatrix();

        float width = sprite.getU1() - sprite.getU0();
        float height = sprite.getV1() - sprite.getV0();

        float u1 = sprite.getU0() + width * 21F / 128F;
        float u2 = u1 + width * 21F / 128F;

        float v1 = sprite.getV0() + height * 0F / 128F;
        float v2 = v1 + height * 21F / 128;

        graphics.innerBlit(RenderPipelines.GUI_TEXTURED, sprite.atlasLocation(), 0, 21, 0, 21, u1, u2, v1, v2, color);

        u1 = sprite.getU0() + width * 19F / 128F;
        u2 = u1 + width * 19F / 128F;

        v1 = sprite.getV0() + height * 24F / 128F;
        v2 = v1 + height * 19F / 128F;

        graphics.innerBlit(RenderPipelines.GUI_TEXTURED, sprite.atlasLocation(), 1, 20, 1, 20, u1, u2, v1, v2, color);

        u1 = sprite.getU0() + width * 15F / 128F;
        u2 = u1 + width * 15F / 128F;

        v1 = sprite.getV0() + height * 46F / 128F;
        v2 = v1 + height * 15F / 128F;

        graphics.innerBlit(RenderPipelines.GUI_TEXTURED, sprite.atlasLocation(), 3, 18, 3, 18, u1, u2, v1, v2, color);

        matrices.popMatrix();
    }

    public record Layer(TextureAtlasSprite sprite, int color) {
        //? if >=1.21.9 {
        public Layer(GuiGraphics graphics, Material material, int color) {
            this(graphics.getSprite(material), color);
        }
        //?} else {
        /*public Layer(GuiGraphics graphics, Material material, int color) {
            this(material.sprite(), color);
        }
        *///?}
    }
}
