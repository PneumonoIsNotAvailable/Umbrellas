package net.pneumono.umbrellas.content;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.serialization.MapCodec;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.special.SpecialModelRenderer;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.pneumono.umbrellas.content.item.component.UmbrellaPatternsComponent;
import net.pneumono.umbrellas.registry.UmbrellasDataComponents;
import org.jetbrains.annotations.NotNull;
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
    public UmbrellaPatternsComponent extractArgument(ItemStack stack) {
        return stack.get(UmbrellasDataComponents.UMBRELLA_PATTERNS);
    }

    @Override
    public void render(
            @Nullable UmbrellaPatternsComponent data,
            ItemDisplayContext displayContext,
            PoseStack poseStack,
            MultiBufferSource multiBufferSource,
            int light,
            int overlay,
            boolean glint
    ) {
        this.renderer.render(
                poseStack,
                multiBufferSource,
                light,
                overlay,
                glint,
                0.0F,
                Objects.requireNonNullElse(data, UmbrellaPatternsComponent.DEFAULT)
        );
    }

    @Override
    public void getExtents(Set<Vector3f> vertices) {
        this.renderer.getExtentsForGui(vertices);
    }

    @Environment(EnvType.CLIENT)
    public static class Unbaked implements SpecialModelRenderer.Unbaked {
        public static final Unbaked INSTANCE = new Unbaked();
        public static final MapCodec<UmbrellaModelRenderer.Unbaked> CODEC = MapCodec.unit(INSTANCE);

        @Override
        public @NotNull MapCodec<UmbrellaModelRenderer.Unbaked> type() {
            return CODEC;
        }

        @Override
        public SpecialModelRenderer<?> bake(EntityModelSet entityModels) {
            return new UmbrellaModelRenderer(new UmbrellaRenderer(entityModels));
        }
    }
}
