//~ identifier_replacements

package net.pneumono.umbrellas.datagen;

import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.ResourceManager;
import net.pneumono.umbrellas.Umbrellas;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class UmbrellasTextureProvider extends TextureProvider {
    public UmbrellasTextureProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> registries) {
        super(packOutput, registries);
    }

    public void generate(ResourceManager manager, HolderLookup.Provider registries, BiConsumer<NativeImage, Identifier> output) {
        String id = Umbrellas.MOD_ID;
        Identifier baseTexture = texture(id, "entity/umbrella_base");
        Consumer<String> alphaApplier = pattern -> applyAlpha(
                manager, output, baseTexture,
                template(id, "dyeable/" + pattern),
                texture(id, "entity/umbrella/" + pattern)
        );

        alphaApplier.accept("accent");
        alphaApplier.accept("base");
        alphaApplier.accept("border");
        alphaApplier.accept("bordure_indented");
        alphaApplier.accept("circle_full");
        alphaApplier.accept("circle_hollow");
        alphaApplier.accept("creeper");
        alphaApplier.accept("cross_cardinal");
        alphaApplier.accept("cross_diagonal");
        alphaApplier.accept("diagonal_down_left");
        alphaApplier.accept("diagonal_down_right");
        alphaApplier.accept("diagonal_up_left");
        alphaApplier.accept("diagonal_up_right");
        alphaApplier.accept("field_masoned");
        alphaApplier.accept("flow");
        alphaApplier.accept("flower");
        alphaApplier.accept("globe");
        alphaApplier.accept("gradient_bottom");
        alphaApplier.accept("gradient_left");
        alphaApplier.accept("gradient_right");
        alphaApplier.accept("gradient_top");
        alphaApplier.accept("guster");
        alphaApplier.accept("half_bottom");
        alphaApplier.accept("half_left");
        alphaApplier.accept("half_right");
        alphaApplier.accept("half_top");
        alphaApplier.accept("half_gradient_bottom");
        alphaApplier.accept("half_gradient_left");
        alphaApplier.accept("half_gradient_right");
        alphaApplier.accept("half_gradient_top");
        alphaApplier.accept("mojang");
        alphaApplier.accept("piglin");
        alphaApplier.accept("rhombus_horizontal");
        alphaApplier.accept("rhombus_vertical");
        alphaApplier.accept("skull");
        alphaApplier.accept("square_bottom_left");
        alphaApplier.accept("square_bottom_right");
        alphaApplier.accept("square_top_left");
        alphaApplier.accept("square_top_right");
        alphaApplier.accept("square_full");
        alphaApplier.accept("square_hollow");
        alphaApplier.accept("stripe_bottom");
        alphaApplier.accept("stripe_left");
        alphaApplier.accept("stripe_right");
        alphaApplier.accept("stripe_top");
        alphaApplier.accept("stripe_down_left");
        alphaApplier.accept("stripe_down_right");
        alphaApplier.accept("stripe_horizontal");
        alphaApplier.accept("stripe_vertical");
        alphaApplier.accept("stripes_horizontal");
        alphaApplier.accept("stripes_vertical");
        alphaApplier.accept("third_bottom");
        alphaApplier.accept("third_left");
        alphaApplier.accept("third_right");
        alphaApplier.accept("third_top");
        alphaApplier.accept("third_horizontal");
        alphaApplier.accept("third_vertical");
        alphaApplier.accept("triangle_bottom");
        alphaApplier.accept("triangle_left");
        alphaApplier.accept("triangle_right");
        alphaApplier.accept("triangle_top");
        alphaApplier.accept("triangles_bottom");
        alphaApplier.accept("triangles_left");
        alphaApplier.accept("triangles_right");
        alphaApplier.accept("triangles_top");

        Consumer<String> flagPatternsCreator = (flag) -> {
            Identifier colorSource = template(id, "pride/color/" + flag);
            applyColorAndAlpha(
                    manager, output, baseTexture, colorSource,
                    texture(id, "entity/umbrella/base"),
                    texture(id, "entity/umbrella/flag_" + flag)
            );
            applyColorAndAlpha(
                    manager, output, baseTexture, colorSource,
                    template(id, "pride/half_flag_left"),
                    texture(id, "entity/umbrella/half_flag_left_" + flag)
            );
            applyColorAndAlpha(
                    manager, output, baseTexture, colorSource,
                    template(id, "pride/half_flag_right"),
                    texture(id, "entity/umbrella/half_flag_right_" + flag)
            );
        };

        flagPatternsCreator.accept("aromantic");
        flagPatternsCreator.accept("asexual");
        flagPatternsCreator.accept("bisexual");
        flagPatternsCreator.accept("genderfluid");
        flagPatternsCreator.accept("intersex");
        flagPatternsCreator.accept("lesbian");
        flagPatternsCreator.accept("mlm");
        flagPatternsCreator.accept("nonbinary");
        flagPatternsCreator.accept("pansexual");
        flagPatternsCreator.accept("pride");
        flagPatternsCreator.accept("transgender");
    }
}
