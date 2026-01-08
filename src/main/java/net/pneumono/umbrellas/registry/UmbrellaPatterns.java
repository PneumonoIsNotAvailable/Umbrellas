package net.pneumono.umbrellas.registry;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.pneumono.umbrellas.Umbrellas;
import net.pneumono.umbrellas.content.UmbrellaPattern;

public class UmbrellaPatterns {
    public static final ResourceKey<Registry<UmbrellaPattern>> UMBRELLA_PATTERN_KEY = ResourceKey.createRegistryKey(Umbrellas.id("umbrella_pattern"));

    // Base
    public static final ResourceKey<UmbrellaPattern> BASE = of("base");

    // Vanilla Patterns (+ Variants)
    public static final ResourceKey<UmbrellaPattern> SQUARE_BOTTOM_LEFT = of("square_bottom_left");
    public static final ResourceKey<UmbrellaPattern> SQUARE_BOTTOM_RIGHT = of("square_bottom_right");
    public static final ResourceKey<UmbrellaPattern> SQUARE_TOP_LEFT = of("square_top_left");
    public static final ResourceKey<UmbrellaPattern> SQUARE_TOP_RIGHT = of("square_top_right");
    public static final ResourceKey<UmbrellaPattern> STRIPE_BOTTOM = of("stripe_bottom");
    public static final ResourceKey<UmbrellaPattern> STRIPE_TOP = of("stripe_top");
    public static final ResourceKey<UmbrellaPattern> STRIPE_LEFT = of("stripe_left");
    public static final ResourceKey<UmbrellaPattern> STRIPE_RIGHT = of("stripe_right");
    public static final ResourceKey<UmbrellaPattern> STRIPE_VERTICAL = of("stripe_vertical");
    public static final ResourceKey<UmbrellaPattern> STRIPE_HORIZONTAL = of("stripe_horizontal");
    public static final ResourceKey<UmbrellaPattern> STRIPE_DOWN_LEFT = of("stripe_down_left");
    public static final ResourceKey<UmbrellaPattern> STRIPE_DOWN_RIGHT = of("stripe_down_right");
    public static final ResourceKey<UmbrellaPattern> STRIPES_VERTICAL = of("stripes_vertical");
    public static final ResourceKey<UmbrellaPattern> STRIPES_HORIZONTAL = of("stripes_horizontal");
    public static final ResourceKey<UmbrellaPattern> CROSS_CARDINAL = of("cross_cardinal");
    public static final ResourceKey<UmbrellaPattern> CROSS_DIAGONAL = of("cross_diagonal");
    public static final ResourceKey<UmbrellaPattern> TRIANGLE_BOTTOM = of("triangle_bottom");
    public static final ResourceKey<UmbrellaPattern> TRIANGLE_TOP = of("triangle_top");
    public static final ResourceKey<UmbrellaPattern> TRIANGLE_LEFT = of("triangle_left");
    public static final ResourceKey<UmbrellaPattern> TRIANGLE_RIGHT = of("triangle_right");
    public static final ResourceKey<UmbrellaPattern> TRIANGLES_BOTTOM = of("triangles_bottom");
    public static final ResourceKey<UmbrellaPattern> TRIANGLES_TOP = of("triangles_top");
    public static final ResourceKey<UmbrellaPattern> TRIANGLES_LEFT = of("triangles_left");
    public static final ResourceKey<UmbrellaPattern> TRIANGLES_RIGHT = of("triangles_right");
    public static final ResourceKey<UmbrellaPattern> DIAGONAL_UP_LEFT = of("diagonal_up_left");
    public static final ResourceKey<UmbrellaPattern> DIAGONAL_UP_RIGHT = of("diagonal_up_right");
    public static final ResourceKey<UmbrellaPattern> DIAGONAL_DOWN_LEFT = of("diagonal_down_left");
    public static final ResourceKey<UmbrellaPattern> DIAGONAL_DOWN_RIGHT = of("diagonal_down_right");
    public static final ResourceKey<UmbrellaPattern> CIRCLE_FULL = of("circle_full");
    public static final ResourceKey<UmbrellaPattern> RHOMBUS_VERTICAL = of("rhombus_vertical");
    public static final ResourceKey<UmbrellaPattern> RHOMBUS_HORIZONTAL = of("rhombus_horizontal");
    public static final ResourceKey<UmbrellaPattern> HALF_BOTTOM = of("half_bottom");
    public static final ResourceKey<UmbrellaPattern> HALF_TOP = of("half_top");
    public static final ResourceKey<UmbrellaPattern> HALF_LEFT = of("half_left");
    public static final ResourceKey<UmbrellaPattern> HALF_RIGHT = of("half_right");
    public static final ResourceKey<UmbrellaPattern> BORDER = of("border");
    public static final ResourceKey<UmbrellaPattern> GRADIENT_BOTTOM = of("gradient_bottom");
    public static final ResourceKey<UmbrellaPattern> GRADIENT_TOP = of("gradient_top");
    public static final ResourceKey<UmbrellaPattern> GRADIENT_LEFT = of("gradient_left");
    public static final ResourceKey<UmbrellaPattern> GRADIENT_RIGHT = of("gradient_right");
    public static final ResourceKey<UmbrellaPattern> GLOBE = of("globe");
    public static final ResourceKey<UmbrellaPattern> CREEPER = of("creeper");
    public static final ResourceKey<UmbrellaPattern> SKULL = of("skull");
    public static final ResourceKey<UmbrellaPattern> FLOWER = of("flower");
    public static final ResourceKey<UmbrellaPattern> MOJANG = of("mojang");
    public static final ResourceKey<UmbrellaPattern> PIGLIN = of("piglin");
    public static final ResourceKey<UmbrellaPattern> FLOW = of("flow");
    public static final ResourceKey<UmbrellaPattern> GUSTER = of("guster");
    public static final ResourceKey<UmbrellaPattern> FIELD_MASONED = of("field_masoned");
    public static final ResourceKey<UmbrellaPattern> BORDURE_INDENTED = of("bordure_indented");

    // Original Patterns
    public static final ResourceKey<UmbrellaPattern> ACCENT = of("accent");
    public static final ResourceKey<UmbrellaPattern> SQUARE_FULL = of("square_full");
    public static final ResourceKey<UmbrellaPattern> SQUARE_HOLLOW = of("square_hollow");
    public static final ResourceKey<UmbrellaPattern> CIRCLE_HOLLOW = of("circle_hollow");
    public static final ResourceKey<UmbrellaPattern> HALF_GRADIENT_BOTTOM = of("half_gradient_bottom");
    public static final ResourceKey<UmbrellaPattern> HALF_GRADIENT_TOP = of("half_gradient_top");
    public static final ResourceKey<UmbrellaPattern> HALF_GRADIENT_LEFT = of("half_gradient_left");
    public static final ResourceKey<UmbrellaPattern> HALF_GRADIENT_RIGHT = of("half_gradient_right");
    public static final ResourceKey<UmbrellaPattern> THIRD_BOTTOM = of("third_bottom");
    public static final ResourceKey<UmbrellaPattern> THIRD_TOP = of("third_top");
    public static final ResourceKey<UmbrellaPattern> THIRD_LEFT = of("third_left");
    public static final ResourceKey<UmbrellaPattern> THIRD_RIGHT = of("third_right");
    public static final ResourceKey<UmbrellaPattern> THIRD_VERTICAL = of("third_vertical");
    public static final ResourceKey<UmbrellaPattern> THIRD_HORIZONTAL = of("third_horizontal");

    // Pride Flags
    public static final ResourceKey<UmbrellaPattern> FLAG_AROMANTIC = of("flag_aromantic");
    public static final ResourceKey<UmbrellaPattern> FLAG_ASEXUAL = of("flag_asexual");
    public static final ResourceKey<UmbrellaPattern> FLAG_BISEXUAL = of("flag_bisexual");
    public static final ResourceKey<UmbrellaPattern> FLAG_GENDERFLUID = of("flag_genderfluid");
    public static final ResourceKey<UmbrellaPattern> FLAG_INTERSEX = of("flag_intersex");
    public static final ResourceKey<UmbrellaPattern> FLAG_LESBIAN = of("flag_lesbian");
    public static final ResourceKey<UmbrellaPattern> FLAG_MLM = of("flag_mlm");
    public static final ResourceKey<UmbrellaPattern> FLAG_NONBINARY = of("flag_nonbinary");
    public static final ResourceKey<UmbrellaPattern> FLAG_PANSEXUAL = of("flag_pansexual");
    public static final ResourceKey<UmbrellaPattern> FLAG_PRIDE = of("flag_pride");
    public static final ResourceKey<UmbrellaPattern> FLAG_TRANSGENDER = of("flag_transgender");
    public static final ResourceKey<UmbrellaPattern> HALF_FLAG_LEFT_AROMANTIC = of("half_flag_left_aromantic");
    public static final ResourceKey<UmbrellaPattern> HALF_FLAG_LEFT_ASEXUAL = of("half_flag_left_asexual");
    public static final ResourceKey<UmbrellaPattern> HALF_FLAG_LEFT_BISEXUAL = of("half_flag_left_bisexual");
    public static final ResourceKey<UmbrellaPattern> HALF_FLAG_LEFT_GENDERFLUID = of("half_flag_left_genderfluid");
    public static final ResourceKey<UmbrellaPattern> HALF_FLAG_LEFT_INTERSEX = of("half_flag_left_intersex");
    public static final ResourceKey<UmbrellaPattern> HALF_FLAG_LEFT_LESBIAN = of("half_flag_left_lesbian");
    public static final ResourceKey<UmbrellaPattern> HALF_FLAG_LEFT_MLM = of("half_flag_left_mlm");
    public static final ResourceKey<UmbrellaPattern> HALF_FLAG_LEFT_NONBINARY = of("half_flag_left_nonbinary");
    public static final ResourceKey<UmbrellaPattern> HALF_FLAG_LEFT_PANSEXUAL = of("half_flag_left_pansexual");
    public static final ResourceKey<UmbrellaPattern> HALF_FLAG_LEFT_PRIDE = of("half_flag_left_pride");
    public static final ResourceKey<UmbrellaPattern> HALF_FLAG_LEFT_TRANSGENDER = of("half_flag_left_transgender");
    public static final ResourceKey<UmbrellaPattern> HALF_FLAG_RIGHT_AROMANTIC = of("half_flag_right_aromantic");
    public static final ResourceKey<UmbrellaPattern> HALF_FLAG_RIGHT_ASEXUAL = of("half_flag_right_asexual");
    public static final ResourceKey<UmbrellaPattern> HALF_FLAG_RIGHT_BISEXUAL = of("half_flag_right_bisexual");
    public static final ResourceKey<UmbrellaPattern> HALF_FLAG_RIGHT_GENDERFLUID = of("half_flag_right_genderfluid");
    public static final ResourceKey<UmbrellaPattern> HALF_FLAG_RIGHT_INTERSEX = of("half_flag_right_intersex");
    public static final ResourceKey<UmbrellaPattern> HALF_FLAG_RIGHT_LESBIAN = of("half_flag_right_lesbian");
    public static final ResourceKey<UmbrellaPattern> HALF_FLAG_RIGHT_MLM = of("half_flag_right_mlm");
    public static final ResourceKey<UmbrellaPattern> HALF_FLAG_RIGHT_NONBINARY = of("half_flag_right_nonbinary");
    public static final ResourceKey<UmbrellaPattern> HALF_FLAG_RIGHT_PANSEXUAL = of("half_flag_right_pansexual");
    public static final ResourceKey<UmbrellaPattern> HALF_FLAG_RIGHT_PRIDE = of("half_flag_right_pride");
    public static final ResourceKey<UmbrellaPattern> HALF_FLAG_RIGHT_TRANSGENDER = of("half_flag_right_transgender");

    private static ResourceKey<UmbrellaPattern> of(String name) {
        return ResourceKey.create(UMBRELLA_PATTERN_KEY, Umbrellas.id(name));
    }

    public static void registerUmbrellasPatterns() {

    }
}
