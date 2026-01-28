package net.pneumono.umbrellas.content;

//? if >=1.21.6 {
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

import java.util.Objects;

//? if >=1.21.11 {
import java.util.function.Consumer;
import org.joml.Vector3fc;
//?} else {
/*import java.util.Set;
import org.joml.Vector3f;
*///?}

//? if >=1.21.9 {
import net.minecraft.client.renderer.SubmitNodeCollector;
//?} else {
/*import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
*///?}

public class UmbrellaSpecialModelRenderer implements SpecialModelRenderer<UmbrellaPatternsComponent> {
    private final UmbrellaRenderer renderer;

    public UmbrellaSpecialModelRenderer(UmbrellaRenderer renderer) {
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
            PoseStack poseStack, SubmitNodeCollector collector,
            int light, int overlay, boolean foil, int k
    ) {
        this.renderer.submit(
                Objects.requireNonNullElse(data, UmbrellaPatternsComponent.DEFAULT),
                poseStack, light, overlay, foil, collector, k, null
        );
    }

    //?} else {
    /*@Override
    public void render(
            @Nullable UmbrellaPatternsComponent data,
            ItemDisplayContext displayContext,
            PoseStack poseStack, MultiBufferSource collector,
            int light, int overlay, boolean foil
    ) {
        this.renderer.submit(
                Objects.requireNonNullElse(data, UmbrellaPatternsComponent.DEFAULT),
                poseStack, light, overlay, foil, collector
        );
    }
    *///?}

    @Override
    public void getExtents(/*? if >=1.21.11 {*/Consumer<Vector3fc> input/*?} else {*//*Set<Vector3f> input*//*?}*/) {
        this.renderer.getExtentsForGui(input);
    }

    @Environment(EnvType.CLIENT)
    public static class Unbaked implements SpecialModelRenderer.Unbaked {
        public static final Unbaked INSTANCE = new Unbaked();
        public static final MapCodec<UmbrellaSpecialModelRenderer.Unbaked> CODEC = MapCodec.unit(INSTANCE);

        //? if >=1.21.9 {
        @Nullable
        @Override
        public SpecialModelRenderer<?> bake(BakingContext bakingContext) {
            return new UmbrellaSpecialModelRenderer(new UmbrellaRenderer(
                    bakingContext.entityModelSet()
                    /*? if >=1.21.9 {*/, bakingContext.materials()/*?}*/
            ));
        }
        //?} else {
        /*@Override
        public SpecialModelRenderer<?> bake(EntityModelSet entityModels) {
            return new UmbrellaSpecialModelRenderer(new UmbrellaRenderer(entityModels));
        }
        *///?}

        @Override
        public @NotNull MapCodec<UmbrellaSpecialModelRenderer.Unbaked> type() {
            return CODEC;
        }
    }
}
//?}
