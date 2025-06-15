package net.pneumono.umbrellas.registry;

import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.pneumono.umbrellas.Umbrellas;
import net.pneumono.umbrellas.patterns.UmbrellaPattern;

public class UmbrellaPatterns {
    public static final RegistryKey<Registry<UmbrellaPattern>> UMBRELLA_PATTERN_KEY = RegistryKey.ofRegistry(Umbrellas.id("umbrella_pattern"));

    // Base
    public static final RegistryKey<UmbrellaPattern> BASE = of("base");

    // Vanilla Patterns (+ Variants)
    public static final RegistryKey<UmbrellaPattern> SQUARE_BOTTOM_LEFT = of("square_bottom_left");
    public static final RegistryKey<UmbrellaPattern> SQUARE_BOTTOM_RIGHT = of("square_bottom_right");
    public static final RegistryKey<UmbrellaPattern> SQUARE_TOP_LEFT = of("square_top_left");
    public static final RegistryKey<UmbrellaPattern> SQUARE_TOP_RIGHT = of("square_top_right");
    public static final RegistryKey<UmbrellaPattern> STRIPE_BOTTOM = of("stripe_bottom");
    public static final RegistryKey<UmbrellaPattern> STRIPE_TOP = of("stripe_top");
    public static final RegistryKey<UmbrellaPattern> STRIPE_LEFT = of("stripe_left");
    public static final RegistryKey<UmbrellaPattern> STRIPE_RIGHT = of("stripe_right");
    public static final RegistryKey<UmbrellaPattern> STRIPE_VERTICAL = of("stripe_vertical");
    public static final RegistryKey<UmbrellaPattern> STRIPE_HORIZONTAL = of("stripe_horizontal");
    public static final RegistryKey<UmbrellaPattern> STRIPE_DOWN_LEFT = of("stripe_down_left");
    public static final RegistryKey<UmbrellaPattern> STRIPE_DOWN_RIGHT = of("stripe_down_right");
    public static final RegistryKey<UmbrellaPattern> STRIPES_VERTICAL = of("stripes_vertical");
    public static final RegistryKey<UmbrellaPattern> STRIPES_HORIZONTAL = of("stripes_horizontal");
    public static final RegistryKey<UmbrellaPattern> CROSS_CARDINAL = of("cross_cardinal");
    public static final RegistryKey<UmbrellaPattern> CROSS_DIAGONAL = of("cross_diagonal");
    public static final RegistryKey<UmbrellaPattern> TRIANGLE_BOTTOM = of("triangle_bottom");
    public static final RegistryKey<UmbrellaPattern> TRIANGLE_TOP = of("triangle_top");
    public static final RegistryKey<UmbrellaPattern> TRIANGLE_LEFT = of("triangle_left");
    public static final RegistryKey<UmbrellaPattern> TRIANGLE_RIGHT = of("triangle_right");
    public static final RegistryKey<UmbrellaPattern> TRIANGLES_BOTTOM = of("triangles_bottom");
    public static final RegistryKey<UmbrellaPattern> TRIANGLES_TOP = of("triangles_top");
    public static final RegistryKey<UmbrellaPattern> TRIANGLES_LEFT = of("triangles_left");
    public static final RegistryKey<UmbrellaPattern> TRIANGLES_RIGHT = of("triangles_right");
    public static final RegistryKey<UmbrellaPattern> DIAGONAL_UP_LEFT = of("diagonal_up_left");
    public static final RegistryKey<UmbrellaPattern> DIAGONAL_UP_RIGHT = of("diagonal_up_right");
    public static final RegistryKey<UmbrellaPattern> DIAGONAL_DOWN_LEFT = of("diagonal_down_left");
    public static final RegistryKey<UmbrellaPattern> DIAGONAL_DOWN_RIGHT = of("diagonal_down_right");
    public static final RegistryKey<UmbrellaPattern> CIRCLE_FULL = of("circle_full");
    public static final RegistryKey<UmbrellaPattern> RHOMBUS_VERTICAL = of("rhombus_vertical");
    public static final RegistryKey<UmbrellaPattern> RHOMBUS_HORIZONTAL = of("rhombus_horizontal");
    public static final RegistryKey<UmbrellaPattern> HALF_BOTTOM = of("half_bottom");
    public static final RegistryKey<UmbrellaPattern> HALF_TOP = of("half_top");
    public static final RegistryKey<UmbrellaPattern> HALF_LEFT = of("half_left");
    public static final RegistryKey<UmbrellaPattern> HALF_RIGHT = of("half_right");
    public static final RegistryKey<UmbrellaPattern> BORDER = of("border");
    public static final RegistryKey<UmbrellaPattern> GRADIENT_BOTTOM = of("gradient_bottom");
    public static final RegistryKey<UmbrellaPattern> GRADIENT_TOP = of("gradient_top");
    public static final RegistryKey<UmbrellaPattern> GRADIENT_LEFT = of("gradient_left");
    public static final RegistryKey<UmbrellaPattern> GRADIENT_RIGHT = of("gradient_right");
    public static final RegistryKey<UmbrellaPattern> GLOBE = of("globe");
    public static final RegistryKey<UmbrellaPattern> CREEPER = of("creeper");
    public static final RegistryKey<UmbrellaPattern> SKULL = of("skull");
    public static final RegistryKey<UmbrellaPattern> FLOWER = of("flower");
    public static final RegistryKey<UmbrellaPattern> MOJANG = of("mojang");
    public static final RegistryKey<UmbrellaPattern> PIGLIN = of("piglin");
    public static final RegistryKey<UmbrellaPattern> FLOW = of("flow");
    public static final RegistryKey<UmbrellaPattern> GUSTER = of("guster");
    public static final RegistryKey<UmbrellaPattern> FIELD_MASONED = of("field_masoned");
    public static final RegistryKey<UmbrellaPattern> BORDURE_INDENTED = of("bordure_indented");

    // Original Patterns
    public static final RegistryKey<UmbrellaPattern> ACCENT = of("accent");
    public static final RegistryKey<UmbrellaPattern> SQUARE_FULL = of("square_full");
    public static final RegistryKey<UmbrellaPattern> SQUARE_HOLLOW = of("square_hollow");
    public static final RegistryKey<UmbrellaPattern> CIRCLE_HOLLOW = of("circle_hollow");
    public static final RegistryKey<UmbrellaPattern> HALF_GRADIENT_BOTTOM = of("half_gradient_bottom");
    public static final RegistryKey<UmbrellaPattern> HALF_GRADIENT_TOP = of("half_gradient_top");
    public static final RegistryKey<UmbrellaPattern> HALF_GRADIENT_LEFT = of("half_gradient_left");
    public static final RegistryKey<UmbrellaPattern> HALF_GRADIENT_RIGHT = of("half_gradient_right");
    public static final RegistryKey<UmbrellaPattern> THIRD_BOTTOM = of("third_bottom");
    public static final RegistryKey<UmbrellaPattern> THIRD_TOP = of("third_top");
    public static final RegistryKey<UmbrellaPattern> THIRD_LEFT = of("third_left");
    public static final RegistryKey<UmbrellaPattern> THIRD_RIGHT = of("third_right");
    public static final RegistryKey<UmbrellaPattern> THIRD_VERTICAL = of("third_vertical");
    public static final RegistryKey<UmbrellaPattern> THIRD_HORIZONTAL = of("third_horizontal");

    // Pride Flags
    public static final RegistryKey<UmbrellaPattern> FLAG_AROMANTIC = of("flag_aromantic");
    public static final RegistryKey<UmbrellaPattern> FLAG_ASEXUAL = of("flag_asexual");
    public static final RegistryKey<UmbrellaPattern> FLAG_BISEXUAL = of("flag_bisexual");
    public static final RegistryKey<UmbrellaPattern> FLAG_GENDERFLUID = of("flag_genderfluid");
    public static final RegistryKey<UmbrellaPattern> FLAG_INTERSEX = of("flag_intersex");
    public static final RegistryKey<UmbrellaPattern> FLAG_LESBIAN = of("flag_lesbian");
    public static final RegistryKey<UmbrellaPattern> FLAG_MLM = of("flag_mlm");
    public static final RegistryKey<UmbrellaPattern> FLAG_NONBINARY = of("flag_nonbinary");
    public static final RegistryKey<UmbrellaPattern> FLAG_PANSEXUAL = of("flag_pansexual");
    public static final RegistryKey<UmbrellaPattern> FLAG_PRIDE = of("flag_pride");
    public static final RegistryKey<UmbrellaPattern> FLAG_TRANSGENDER = of("flag_transgender");
    public static final RegistryKey<UmbrellaPattern> HALF_FLAG_LEFT_AROMANTIC = of("half_flag_left_aromantic");
    public static final RegistryKey<UmbrellaPattern> HALF_FLAG_LEFT_ASEXUAL = of("half_flag_left_asexual");
    public static final RegistryKey<UmbrellaPattern> HALF_FLAG_LEFT_BISEXUAL = of("half_flag_left_bisexual");
    public static final RegistryKey<UmbrellaPattern> HALF_FLAG_LEFT_GENDERFLUID = of("half_flag_left_genderfluid");
    public static final RegistryKey<UmbrellaPattern> HALF_FLAG_LEFT_INTERSEX = of("half_flag_left_intersex");
    public static final RegistryKey<UmbrellaPattern> HALF_FLAG_LEFT_LESBIAN = of("half_flag_left_lesbian");
    public static final RegistryKey<UmbrellaPattern> HALF_FLAG_LEFT_MLM = of("half_flag_left_mlm");
    public static final RegistryKey<UmbrellaPattern> HALF_FLAG_LEFT_NONBINARY = of("half_flag_left_nonbinary");
    public static final RegistryKey<UmbrellaPattern> HALF_FLAG_LEFT_PANSEXUAL = of("half_flag_left_pansexual");
    public static final RegistryKey<UmbrellaPattern> HALF_FLAG_LEFT_PRIDE = of("half_flag_left_pride");
    public static final RegistryKey<UmbrellaPattern> HALF_FLAG_LEFT_TRANSGENDER = of("half_flag_left_transgender");
    public static final RegistryKey<UmbrellaPattern> HALF_FLAG_RIGHT_AROMANTIC = of("half_flag_right_aromantic");
    public static final RegistryKey<UmbrellaPattern> HALF_FLAG_RIGHT_ASEXUAL = of("half_flag_right_asexual");
    public static final RegistryKey<UmbrellaPattern> HALF_FLAG_RIGHT_BISEXUAL = of("half_flag_right_bisexual");
    public static final RegistryKey<UmbrellaPattern> HALF_FLAG_RIGHT_GENDERFLUID = of("half_flag_right_genderfluid");
    public static final RegistryKey<UmbrellaPattern> HALF_FLAG_RIGHT_INTERSEX = of("half_flag_right_intersex");
    public static final RegistryKey<UmbrellaPattern> HALF_FLAG_RIGHT_LESBIAN = of("half_flag_right_lesbian");
    public static final RegistryKey<UmbrellaPattern> HALF_FLAG_RIGHT_MLM = of("half_flag_right_mlm");
    public static final RegistryKey<UmbrellaPattern> HALF_FLAG_RIGHT_NONBINARY = of("half_flag_right_nonbinary");
    public static final RegistryKey<UmbrellaPattern> HALF_FLAG_RIGHT_PANSEXUAL = of("half_flag_right_pansexual");
    public static final RegistryKey<UmbrellaPattern> HALF_FLAG_RIGHT_PRIDE = of("half_flag_right_pride");
    public static final RegistryKey<UmbrellaPattern> HALF_FLAG_RIGHT_TRANSGENDER = of("half_flag_right_transgender");

    private static RegistryKey<UmbrellaPattern> of(String name) {
        return RegistryKey.of(UMBRELLA_PATTERN_KEY, Umbrellas.id(name));
    }

    public static void registerUmbrellasPatterns() {

    }
}
