package net.pneumono.umbrellas.content;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.Material;
import net.minecraft.core.Holder;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.pneumono.umbrellas.Umbrellas;
import net.pneumono.umbrellas.UmbrellasClient;
import net.pneumono.umbrellas.content.item.PatternableUmbrellaItem;
import net.pneumono.umbrellas.content.item.component.UmbrellaPatternsComponent;
import net.pneumono.umbrellas.registry.UmbrellasDataComponents;
import net.pneumono.umbrellas.util.data.VersionedComponents;

import java.util.ArrayList;
import java.util.List;

//? if >=1.21.6 {
import net.minecraft.client.renderer.RenderPipelines;
import org.joml.Matrix3x2fStack;
//?} else {
/*import com.mojang.blaze3d.vertex.PoseStack;
*///?}

public class LoomUmbrellaRendering {
    public static int TEXTURE_SIZE = 21;

    public static void drawResultUmbrella(GuiGraphics graphics, int x, int y, ItemStack stack) {
        Pose pose = new Pose(graphics.pose());
        pose.push();
        pose.translate(x, y);
        pose.scale(32F / TEXTURE_SIZE);

        UmbrellaPatternsComponent component = VersionedComponents.getOrDefault(stack, UmbrellasDataComponents.UMBRELLA_PATTERNS, UmbrellaPatternsComponent.DEFAULT);
        DyeColor baseColor = PatternableUmbrellaItem.getColor(stack);

        List<Layer> layers = new ArrayList<>();
        layers.add(new Layer(graphics, UmbrellasClient.getUmbrellaMaterial(Umbrellas.id("base")), baseColor));
        layers.addAll(getLayers(graphics, component));

        draw2DUmbrellaCanopy(graphics, layers.toArray(Layer[]::new));

        pose.pop();
    }

    public static List<Layer> getLayers(GuiGraphics graphics, UmbrellaPatternsComponent component) {
        return component.layers().stream().map(layer -> new Layer(graphics, UmbrellaRenderer.getUmbrellaPatternTextureId(layer.pattern()), layer.color())).toList();
    }

    public static void drawPatternUmbrella(GuiGraphics graphics, int x, int y, Holder<UmbrellaPattern> pattern) {
        Pose pose = new Pose(graphics.pose());
        pose.push();
        pose.translate(x + 2, y + 2);
        pose.scale(10F / TEXTURE_SIZE);

        draw2DUmbrellaCanopy(graphics,
                new Layer(graphics, UmbrellasClient.getUmbrellaMaterial(Umbrellas.id("base")), DyeColor.GRAY),
                new Layer(graphics, UmbrellaRenderer.getUmbrellaPatternTextureId(pattern), DyeColor.WHITE)
        );

        pose.pop();
    }

    public static void draw2DUmbrellaCanopy(GuiGraphics graphics, Layer... layers) {
        for (Layer layer : layers) {
            draw2DUmbrellaCanopyLayer(graphics, layer.sprite, layer.color);
        }
    }

    public static void draw2DUmbrellaCanopyLayer(GuiGraphics graphics, TextureAtlasSprite sprite, /*? if >=1.21 {*/int/*?} else {*//*float[]*//*?}*/ color) {
        Pose pose = new Pose(graphics.pose());
        pose.push();

        float width = sprite.getU1() - sprite.getU0();
        float height = sprite.getV1() - sprite.getV0();

        float u1 = sprite.getU0() + width * 21F / 128F;
        float u2 = u1 + width * 21F / 128F;

        float v1 = sprite.getV0() + height * 0F / 128F;
        float v2 = v1 + height * 21F / 128;

        draw(graphics, sprite, 0, 21, 0, 21, u1, u2, v1, v2, color);

        u1 = sprite.getU0() + width * 19F / 128F;
        u2 = u1 + width * 19F / 128F;

        v1 = sprite.getV0() + height * 24F / 128F;
        v2 = v1 + height * 19F / 128F;

        draw(graphics, sprite, 1, 20, 1, 20, u1, u2, v1, v2, color);

        u1 = sprite.getU0() + width * 15F / 128F;
        u2 = u1 + width * 15F / 128F;

        v1 = sprite.getV0() + height * 46F / 128F;
        v2 = v1 + height * 15F / 128F;

        draw(graphics, sprite, 3, 18, 3, 18, u1, u2, v1, v2, color);

        pose.pop();
    }

    public static void draw(GuiGraphics graphics, TextureAtlasSprite sprite, int x1, int x2, int y1, int y2, float u1, float u2, float v1, float v2, /*? if >=1.21 {*/int/*?} else {*//*float[]*//*?}*/ color) {
        //? if >=1.21.6 {
        graphics.innerBlit(RenderPipelines.GUI_TEXTURED, sprite.atlasLocation(), x1, x2, y1, y2, u1, u2, v1, v2, color);
        //?} else if >=1.21 {
        /*float red = (color >> 16 & 0xFF) / 255F;
        float green = (color >> 8 & 0xFF) / 255F;
        float blue = (color & 0xFF) / 255F;
        graphics.innerBlit(sprite.atlasLocation(), x1, x2, y1, y2, 0, u1, u2, v1, v2, red, green, blue, 1);
        *///?} else {
        /*graphics.innerBlit(sprite.atlasLocation(), x1, x2, y1, y2, 0, u1, u2, v1, v2, color[0], color[1], color[2], 1);
        *///?}
    }

    public record Layer(TextureAtlasSprite sprite, /*? if >=1.21 {*/int/*?} else {*//*float[]*//*?}*/ color) {
        public Layer(GuiGraphics graphics, Material material, DyeColor color) {
            this(
                    //? if >=1.21.9 {
                    graphics.getSprite(material),
                    //?} else {
                    /*material.sprite(),
                    *///?}
                    //? if >=1.21 {
                    color.getTextureDiffuseColor()
                    //?} else {
                    /*color.getTextureDiffuseColors()
                    *///?}
            );
        }
    }

    private static class Pose {
        public final /*? if >=1.21.6 {*/Matrix3x2fStack/*?} else {*//*PoseStack*//*?}*/ pose;

        public Pose(
                /*? if >=1.21.6 {*/Matrix3x2fStack/*?} else {*//*PoseStack*//*?}*/ pose
        ) {
            this.pose = pose;
        }

        public void push() {
            //? if >=1.21.6 {
            this.pose.pushMatrix();
            //?} else {
            /*this.pose.pushPose();
            *///?}
        }

        public void pop() {
            //? if >=1.21.6 {
            this.pose.popMatrix();
            //?} else {
            /*this.pose.popPose();
            *///?}
        }

        public void translate(int x, int y) {
            //? if >=1.21.6 {
            this.pose.translate(x, y);
            //?} else {
            /*this.pose.translate(x, y, 0);
            *///?}
        }

        public void scale(float scale) {
            //? if >=1.21.6 {
            this.pose.scale(scale);
             //?} else {
            /*this.pose.scale(scale, scale, scale);
            *///?}
        }
    }
}
