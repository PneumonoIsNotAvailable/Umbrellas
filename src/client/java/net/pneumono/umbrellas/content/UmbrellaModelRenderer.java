package net.pneumono.umbrellas.content;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.serialization.MapCodec;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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
import java.util.function.Consumer;

//? if >=1.21.9 {
import net.minecraft.client.renderer.SubmitNodeCollector;
import org.joml.Vector3fc;
//?} else {
/*import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
*///?}

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

    //? if >=1.21.9 {
    @Override
    public void submit(
            @Nullable UmbrellaPatternsComponent data,
            ItemDisplayContext itemDisplayContext,
            PoseStack poseStack,
            SubmitNodeCollector collector,
            int light, int overlay, boolean glint, int color
    ) {
        this.renderer.submit(
                Objects.requireNonNullElse(data, UmbrellaPatternsComponent.DEFAULT),
                poseStack, collector, light, overlay, 0.0F, null
        );
    }

    //?} else {
    /*@Override
    public void render(
            @Nullable UmbrellaPatternsComponent data,
            ItemDisplayContext displayContext,
            PoseStack poseStack,
            MultiBufferSource multiBufferSource,
            int light,
            int overlay,
            boolean glint
    ) {
        this.renderer.submit(
                poseStack,
                multiBufferSource,
                light,
                overlay,
                glint,
                0.0F,
                Objects.requireNonNullElse(data, UmbrellaPatternsComponent.DEFAULT)
        );
    }
    *///?}

    //? if >=1.21.11 {
    @Override
    public void getExtents(Consumer<Vector3fc> consumer) {
        this.renderer.getExtentsForGui(consumer);
    }
    //?} else {
    /*@Override
    public void getExtents(Set<Vector3f> vertices) {
        this.renderer.getExtentsForGui(vertices);
    }
    *///?}

    @Environment(EnvType.CLIENT)
    public static class Unbaked implements SpecialModelRenderer.Unbaked {
        public static final Unbaked INSTANCE = new Unbaked();
        public static final MapCodec<UmbrellaModelRenderer.Unbaked> CODEC = MapCodec.unit(INSTANCE);

        //? if >=1.21.9 {
        @Nullable
        @Override
        public SpecialModelRenderer<?> bake(BakingContext bakingContext) {
            return new UmbrellaModelRenderer(new UmbrellaRenderer(
                    bakingContext.entityModelSet()
                    /*? if >=1.21.9 {*/, bakingContext.materials()/*?}*/
            ));
        }
        //?} else {
        /*@Override
        public SpecialModelRenderer<?> bake(EntityModelSet entityModels) {
            return new UmbrellaModelRenderer(new UmbrellaRenderer(entityModels));
        }
        *///?}

        @Override
        public @NotNull MapCodec<UmbrellaModelRenderer.Unbaked> type() {
            return CODEC;
        }
    }
}
