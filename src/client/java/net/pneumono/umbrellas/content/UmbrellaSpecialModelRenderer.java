package net.pneumono.umbrellas.content;

//? if >=1.21.6 {
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.serialization.MapCodec;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.special.SpecialModelRenderer;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.pneumono.umbrellas.content.item.PatternableUmbrellaItem;
import net.pneumono.umbrellas.content.item.component.UmbrellaPatternsComponent;
import net.pneumono.umbrellas.registry.UmbrellasDataComponents;
import net.pneumono.umbrellas.util.data.VersionedComponents;
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

public class UmbrellaSpecialModelRenderer implements SpecialModelRenderer<UmbrellaSpecialModelRenderer.Data> {
    private final UmbrellaRenderer renderer;

    public UmbrellaSpecialModelRenderer(UmbrellaRenderer renderer) {
        this.renderer = renderer;
    }

    @Nullable
    @Override
    public Data extractArgument(ItemStack stack) {
        return new Data(stack);
    }

    @Override
    public void /*? if >=1.21.9 {*/submit/*?} else {*//*render*//*?}*/(
            @Nullable Data data,
            ItemDisplayContext itemDisplayContext,
            PoseStack poseStack,
            /*? if >=1.21.9 {*/SubmitNodeCollector collector/*?} else {*//*MultiBufferSource collector*//*?}*/,
            int light, int overlay, boolean foil
            /*? if >=1.21.9 {*/, int k/*?}*/
    ) {
        Data submitted = Objects.requireNonNullElse(data, Data.DEFAULT);
        this.renderer.submit(
                submitted.baseColor(), submitted.patterns(),
                poseStack, light, overlay, foil, collector
                /*? if >=1.21.9 {*/, k, null/*?}*/
        );
    }

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

    public record Data(DyeColor baseColor, UmbrellaPatternsComponent patterns) {
        public static final Data DEFAULT = new Data(DyeColor.WHITE, UmbrellaPatternsComponent.DEFAULT);

        public Data(ItemStack stack) {
            this(PatternableUmbrellaItem.getColor(stack), VersionedComponents.get(stack, UmbrellasDataComponents.UMBRELLA_PATTERNS));
        }
    }
}
//?}
