package net.pneumono.umbrellas.content;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.LoadedEntityModels;
import net.minecraft.client.render.item.model.special.SpecialModelRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemDisplayContext;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DyeColor;
import net.pneumono.umbrellas.content.item.component.UmbrellaPatternsComponent;
import net.pneumono.umbrellas.registry.UmbrellasItems;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class UmbrellaModelRenderer implements SpecialModelRenderer<UmbrellaPatternsComponent> {
    private final UmbrellaRenderer renderer;
    private final DyeColor baseColor;

    public UmbrellaModelRenderer(DyeColor baseColor, UmbrellaRenderer renderer) {
        this.renderer = renderer;
        this.baseColor = baseColor;
    }

    @Nullable
    @Override
    public UmbrellaPatternsComponent getData(ItemStack stack) {
        return stack.get(UmbrellasItems.UMBRELLA_PATTERNS);
    }

    @Override
    public void render(
            @Nullable UmbrellaPatternsComponent data,
            ItemDisplayContext displayContext,
            MatrixStack matrices,
            VertexConsumerProvider vertexConsumers,
            int light,
            int overlay,
            boolean glint
    ) {
        this.renderer.render(
                matrices,
                vertexConsumers,
                light,
                overlay,
                glint,
                0.0F,
                this.baseColor,
                Objects.requireNonNullElse(data, UmbrellaPatternsComponent.DEFAULT)
        );
    }

    @Environment(EnvType.CLIENT)
    public record Unbaked(DyeColor baseColor) implements SpecialModelRenderer.Unbaked {
        public static final MapCodec<UmbrellaModelRenderer.Unbaked> CODEC = RecordCodecBuilder.mapCodec(
                instance -> instance.group(DyeColor.CODEC.fieldOf("color").forGetter(UmbrellaModelRenderer.Unbaked::baseColor))
                        .apply(instance, UmbrellaModelRenderer.Unbaked::new)
        );

        @Override
        public MapCodec<UmbrellaModelRenderer.Unbaked> getCodec() {
            return CODEC;
        }

        @Override
        public SpecialModelRenderer<?> bake(LoadedEntityModels entityModels) {
            return new UmbrellaModelRenderer(this.baseColor, new UmbrellaRenderer(entityModels));
        }
    }
}
