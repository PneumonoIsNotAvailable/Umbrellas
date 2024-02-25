package net.pneumono.umbrellas.content;

import com.mojang.datafixers.util.Pair;
import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.DyeableItem;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.DyeColor;
import net.pneumono.umbrellas.UmbrellasClient;
import net.pneumono.umbrellas.patterns.UmbrellaPattern;

import java.util.List;
import java.util.Objects;

public class UmbrellaModel extends Model {
    private static UmbrellaModel umbrellaModel;

    private static final String UMBRELLA = "umbrella";
    private static final String HANDLE = "handle";

    private final ModelPart root;
    private final ModelPart umbrella;
    private final ModelPart handle;

    public UmbrellaModel(ModelPart root) {
        super(RenderLayer::getEntitySolid);
        this.root = root;
        this.umbrella = root.getChild(UMBRELLA);
        this.handle = root.getChild(HANDLE);
    }

    // Made with BlockBench
    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        modelPartData.addChild(HANDLE, ModelPartBuilder.create().uv(0, 62).cuboid(8.0F, -21.0F, -9.0F, 1.0F, 29.0F, 1.0F), ModelTransform.pivot(-1.0F, -8.0F, 1.0F));
        modelPartData.addChild(UMBRELLA, ModelPartBuilder.create().uv(0, 46).cuboid(1.0F, -21.0F, -16.0F, 15.0F, 1.0F, 15.0F)
                .uv(0, 24).cuboid(-1.0F, -20.0F, -18.0F, 19.0F, 3.0F, 19.0F)
                .uv(0, 0).cuboid(-2.0F, -17.0F, -19.0F, 21.0F, 3.0F, 21.0F), ModelTransform.pivot(-1.0F, -8.0F, 1.0F));

        return TexturedModelData.of(modelData, 128, 128);
    }

    public ModelPart getUmbrella() {
        return umbrella;
    }

    public ModelPart getHandle() {
        return handle;
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
        root.render(matrices, vertices, light, overlay, red, green, blue, alpha);
    }

    public static void loadModel(EntityModelLoader loader) {
        umbrellaModel = new UmbrellaModel(loader.getModelPart(UmbrellasClient.UMBRELLA));
    }

    public static void render(ItemStack stack, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        boolean renderPatterns = stack.getItem() instanceof PatternableUmbrellaItem;
        matrices.push();
        matrices.scale(1.0f, -1.0f, -1.0f);

        SpriteIdentifier spriteIdentifier = UmbrellasClient.UMBRELLA_BASE;

        VertexConsumer vertexConsumer = spriteIdentifier.getSprite().getTextureSpecificVertexConsumer(ItemRenderer.getDirectItemGlintConsumer(vertexConsumers, umbrellaModel.getLayer(spriteIdentifier.getAtlasId()), true, stack.hasGlint()));
        umbrellaModel.getHandle().render(matrices, vertexConsumer, light, overlay, 1.0f, 1.0f, 1.0f, 1.0f);

        if (renderPatterns) {
            List<Pair<RegistryEntry<UmbrellaPattern>, DyeColor>> list = PatternableUmbrellaItem.getPatternsFromNbt(stack);
            renderUmbrella(matrices, vertexConsumers, light, overlay, umbrellaModel.getUmbrella(), spriteIdentifier, list, stack.hasGlint());
        } else {
            float[] colors = new float[]{1, 1, 1};

            if (stack.getItem() instanceof DyeableItem dyeableItem) {
                int color = dyeableItem.getColor(stack);
                colors[0] = ((color & 0xFF0000) >> 16) / 256F;
                colors[1] = ((color & 0xFF00) >> 8) / 256F;
                colors[2] = ((color & 0xFF)) / 256F;
            }
            umbrellaModel.getUmbrella().render(matrices, spriteIdentifier.getVertexConsumer(vertexConsumers, RenderLayer::getEntitySolid, stack.hasGlint()), light, overlay);
            Objects.requireNonNull(UmbrellaPattern.byId("base")).getKey().ifPresent(sprite ->
                umbrellaModel.getUmbrella().render(matrices, UmbrellasClient.getUmbrellaPatternTextureId(sprite).getVertexConsumer(vertexConsumers, RenderLayer::getEntityNoOutline), light, overlay, colors[0], colors[1], colors[2], 1.0f)
            );
        }

        matrices.pop();
    }

    public static void renderUmbrella(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, ModelPart canvas, SpriteIdentifier baseSprite, List<Pair<RegistryEntry<UmbrellaPattern>, DyeColor>> patterns, boolean glint) {
        canvas.render(matrices, baseSprite.getVertexConsumer(vertexConsumers, RenderLayer::getEntitySolid, glint), light, overlay);

        for (int i = 0; i < 17 && i < patterns.size(); ++i) {
            Pair<RegistryEntry<UmbrellaPattern>, DyeColor> pair = patterns.get(i);
            float[] colors = pair.getFirst().value().colored() ? pair.getSecond().getColorComponents() : DyeColor.WHITE.getColorComponents();
            pair.getFirst().getKey().map(UmbrellasClient::getUmbrellaPatternTextureId).ifPresent(sprite -> canvas.render(matrices, sprite.getVertexConsumer(vertexConsumers, RenderLayer::getEntityNoOutline), light, overlay, colors[0], colors[1], colors[2], 1.0f));
        }
    }
}
