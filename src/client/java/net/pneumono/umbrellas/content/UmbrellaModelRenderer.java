package net.pneumono.umbrellas.content;

import com.mojang.serialization.MapCodec;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.LoadedEntityModels;
import net.minecraft.client.render.item.model.special.SpecialModelRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemDisplayContext;
import net.minecraft.item.ItemStack;
import net.pneumono.umbrellas.content.item.component.UmbrellaPatternsComponent;
import net.pneumono.umbrellas.registry.UmbrellasDataComponents;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

import java.util.Objects;
import java.util.Set;

public class UmbrellaModelRenderer implements SpecialModelRenderer<UmbrellaPatternsComponent> {
    private final UmbrellaRenderer renderer;

    public UmbrellaModelRenderer(UmbrellaRenderer renderer) {
        this.renderer = renderer;
    }

    @Nullable
    @Override
    public UmbrellaPatternsComponent getData(ItemStack stack) {
        return stack.get(UmbrellasDataComponents.UMBRELLA_PATTERNS);
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
                Objects.requireNonNullElse(data, UmbrellaPatternsComponent.DEFAULT)
        );
    }

    @Override
    public void collectVertices(Set<Vector3f> vertices) {
        this.renderer.collectVertices(vertices);
    }

    @Environment(EnvType.CLIENT)
    public static class Unbaked implements SpecialModelRenderer.Unbaked {
        public static final Unbaked INSTANCE = new Unbaked();
        public static final MapCodec<UmbrellaModelRenderer.Unbaked> CODEC = MapCodec.unit(INSTANCE);

        @Override
        public MapCodec<UmbrellaModelRenderer.Unbaked> getCodec() {
            return CODEC;
        }

        @Override
        public SpecialModelRenderer<?> bake(LoadedEntityModels entityModels) {
            return new UmbrellaModelRenderer(new UmbrellaRenderer(entityModels));
        }
    }
}
