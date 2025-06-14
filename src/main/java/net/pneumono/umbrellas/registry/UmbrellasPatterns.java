package net.pneumono.umbrellas.registry;

import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.fabricmc.fabric.api.event.registry.RegistryAttribute;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.pneumono.umbrellas.Umbrellas;
import net.pneumono.umbrellas.patterns.UmbrellaPattern;

public class UmbrellasPatterns {
    public static final RegistryKey<Registry<UmbrellaPattern>> UMBRELLA_PATTERN_KEY = RegistryKey.ofRegistry(Umbrellas.id("umbrella_pattern"));
    public static final Registry<UmbrellaPattern> UMBRELLA_PATTERN = FabricRegistryBuilder.createSimple(UMBRELLA_PATTERN_KEY).attribute(RegistryAttribute.SYNCED).buildAndRegister();

    // Base
    public static final UmbrellaPattern BASE = register("base");

    // Vanilla Patterns (+ Variants)
    public static final UmbrellaPattern SQUARE_BOTTOM_LEFT = register("square_bottom_left");
    public static final UmbrellaPattern SQUARE_BOTTOM_RIGHT = register("square_bottom_right");
    public static final UmbrellaPattern SQUARE_TOP_LEFT = register("square_top_left");
    public static final UmbrellaPattern SQUARE_TOP_RIGHT = register("square_top_right");
    public static final UmbrellaPattern STRIPE_BOTTOM = register("stripe_bottom");
    public static final UmbrellaPattern STRIPE_TOP = register("stripe_top");
    public static final UmbrellaPattern STRIPE_LEFT = register("stripe_left");
    public static final UmbrellaPattern STRIPE_RIGHT = register("stripe_right");
    public static final UmbrellaPattern STRIPE_VERTICAL = register("stripe_vertical");
    public static final UmbrellaPattern STRIPE_HORIZONTAL = register("stripe_horizontal");
    public static final UmbrellaPattern STRIPE_DOWN_LEFT = register("stripe_down_left");
    public static final UmbrellaPattern STRIPE_DOWN_RIGHT = register("stripe_down_right");
    public static final UmbrellaPattern STRIPES_VERTICAL = register("stripes_vertical");
    public static final UmbrellaPattern STRIPES_HORIZONTAL = register("stripes_horizontal");
    public static final UmbrellaPattern CROSS_CARDINAL = register("cross_cardinal");
    public static final UmbrellaPattern CROSS_DIAGONAL = register("cross_diagonal");
    public static final UmbrellaPattern TRIANGLE_BOTTOM = register("triangle_bottom");
    public static final UmbrellaPattern TRIANGLE_TOP = register("triangle_top");
    public static final UmbrellaPattern TRIANGLE_LEFT = register("triangle_left");
    public static final UmbrellaPattern TRIANGLE_RIGHT = register("triangle_right");
    public static final UmbrellaPattern TRIANGLES_BOTTOM = register("triangles_bottom");
    public static final UmbrellaPattern TRIANGLES_TOP = register("triangles_top");
    public static final UmbrellaPattern TRIANGLES_LEFT = register("triangles_left");
    public static final UmbrellaPattern TRIANGLES_RIGHT = register("triangles_right");
    public static final UmbrellaPattern DIAGONAL_UP_LEFT = register("diagonal_up_left");
    public static final UmbrellaPattern DIAGONAL_UP_RIGHT = register("diagonal_up_right");
    public static final UmbrellaPattern DIAGONAL_DOWN_LEFT = register("diagonal_down_left");
    public static final UmbrellaPattern DIAGONAL_DOWN_RIGHT = register("diagonal_down_right");
    public static final UmbrellaPattern CIRCLE_FULL = register("circle_full");
    public static final UmbrellaPattern RHOMBUS_VERTICAL = register("rhombus_vertical");
    public static final UmbrellaPattern RHOMBUS_HORIZONTAL = register("rhombus_horizontal");
    public static final UmbrellaPattern HALF_BOTTOM = register("half_bottom");
    public static final UmbrellaPattern HALF_TOP = register("half_top");
    public static final UmbrellaPattern HALF_LEFT = register("half_left");
    public static final UmbrellaPattern HALF_RIGHT = register("half_right");
    public static final UmbrellaPattern BORDER = register("border");
    public static final UmbrellaPattern GRADIENT_BOTTOM = register("gradient_bottom");
    public static final UmbrellaPattern GRADIENT_TOP = register("gradient_top");
    public static final UmbrellaPattern GRADIENT_LEFT = register("gradient_left");
    public static final UmbrellaPattern GRADIENT_RIGHT = register("gradient_right");
    public static final UmbrellaPattern GLOBE = register("globe");
    public static final UmbrellaPattern CREEPER = register("creeper");
    public static final UmbrellaPattern SKULL = register("skull");
    public static final UmbrellaPattern FLOWER = register("flower");
    public static final UmbrellaPattern MOJANG = register("mojang");
    public static final UmbrellaPattern PIGLIN = register("piglin");
    public static final UmbrellaPattern FLOW = register("flow");
    public static final UmbrellaPattern GUSTER = register("guster");
    public static final UmbrellaPattern FIELD_MASONED = register("field_masoned");
    public static final UmbrellaPattern BORDURE_INDENTED = register("bordure_indented");

    // Original Patterns
    public static final UmbrellaPattern ACCENT = register("accent");
    public static final UmbrellaPattern SQUARE_FULL = register("square_full");
    public static final UmbrellaPattern SQUARE_HOLLOW = register("square_hollow");
    public static final UmbrellaPattern CIRCLE_HOLLOW = register("circle_hollow");
    public static final UmbrellaPattern HALF_GRADIENT_BOTTOM = register("half_gradient_bottom");
    public static final UmbrellaPattern HALF_GRADIENT_TOP = register("half_gradient_top");
    public static final UmbrellaPattern HALF_GRADIENT_LEFT = register("half_gradient_left");
    public static final UmbrellaPattern HALF_GRADIENT_RIGHT = register("half_gradient_right");
    public static final UmbrellaPattern THIRD_BOTTOM = register("third_bottom");
    public static final UmbrellaPattern THIRD_TOP = register("third_top");
    public static final UmbrellaPattern THIRD_LEFT = register("third_left");
    public static final UmbrellaPattern THIRD_RIGHT = register("third_right");
    public static final UmbrellaPattern THIRD_VERTICAL = register("third_vertical");
    public static final UmbrellaPattern THIRD_HORIZONTAL = register("third_horizontal");

    // Pride Flags
    public static final UmbrellaPattern FLAG_AROMANTIC = register("flag_aromantic", false);
    public static final UmbrellaPattern FLAG_ASEXUAL = register("flag_asexual", false);
    public static final UmbrellaPattern FLAG_BISEXUAL = register("flag_bisexual", false);
    public static final UmbrellaPattern FLAG_GENDERFLUID = register("flag_genderfluid", false);
    public static final UmbrellaPattern FLAG_INTERSEX = register("flag_intersex", false);
    public static final UmbrellaPattern FLAG_LESBIAN = register("flag_lesbian", false);
    public static final UmbrellaPattern FLAG_MLM = register("flag_mlm", false);
    public static final UmbrellaPattern FLAG_NONBINARY = register("flag_nonbinary", false);
    public static final UmbrellaPattern FLAG_PANSEXUAL = register("flag_pansexual", false);
    public static final UmbrellaPattern FLAG_PRIDE = register("flag_pride", false);
    public static final UmbrellaPattern FLAG_TRANSGENDER = register("flag_transgender", false);
    public static final UmbrellaPattern HALF_FLAG_LEFT_AROMANTIC = register("half_flag_left_aromantic", false);
    public static final UmbrellaPattern HALF_FLAG_LEFT_ASEXUAL = register("half_flag_left_asexual", false);
    public static final UmbrellaPattern HALF_FLAG_LEFT_BISEXUAL = register("half_flag_left_bisexual", false);
    public static final UmbrellaPattern HALF_FLAG_LEFT_GENDERFLUID = register("half_flag_left_genderfluid", false);
    public static final UmbrellaPattern HALF_FLAG_LEFT_INTERSEX = register("half_flag_left_intersex", false);
    public static final UmbrellaPattern HALF_FLAG_LEFT_LESBIAN = register("half_flag_left_lesbian", false);
    public static final UmbrellaPattern HALF_FLAG_LEFT_MLM = register("half_flag_left_mlm", false);
    public static final UmbrellaPattern HALF_FLAG_LEFT_NONBINARY = register("half_flag_left_nonbinary", false);
    public static final UmbrellaPattern HALF_FLAG_LEFT_PANSEXUAL = register("half_flag_left_pansexual", false);
    public static final UmbrellaPattern HALF_FLAG_LEFT_PRIDE = register("half_flag_left_pride", false);
    public static final UmbrellaPattern HALF_FLAG_LEFT_TRANSGENDER = register("half_flag_left_transgender", false);
    public static final UmbrellaPattern HALF_FLAG_RIGHT_AROMANTIC = register("half_flag_right_aromantic", false);
    public static final UmbrellaPattern HALF_FLAG_RIGHT_ASEXUAL = register("half_flag_right_asexual", false);
    public static final UmbrellaPattern HALF_FLAG_RIGHT_BISEXUAL = register("half_flag_right_bisexual", false);
    public static final UmbrellaPattern HALF_FLAG_RIGHT_GENDERFLUID = register("half_flag_right_genderfluid", false);
    public static final UmbrellaPattern HALF_FLAG_RIGHT_INTERSEX = register("half_flag_right_intersex", false);
    public static final UmbrellaPattern HALF_FLAG_RIGHT_LESBIAN = register("half_flag_right_lesbian", false);
    public static final UmbrellaPattern HALF_FLAG_RIGHT_MLM = register("half_flag_right_mlm", false);
    public static final UmbrellaPattern HALF_FLAG_RIGHT_NONBINARY = register("half_flag_right_nonbinary", false);
    public static final UmbrellaPattern HALF_FLAG_RIGHT_PANSEXUAL = register("half_flag_right_pansexual", false);
    public static final UmbrellaPattern HALF_FLAG_RIGHT_PRIDE = register("half_flag_right_pride", false);
    public static final UmbrellaPattern HALF_FLAG_RIGHT_TRANSGENDER = register("half_flag_right_transgender", false);

    private static UmbrellaPattern register(String name) {
        return register(name, true);
    }

    private static UmbrellaPattern register(String name, boolean dyeable) {
        Identifier id = Umbrellas.id(name);
        return Registry.register(UMBRELLA_PATTERN,
                id,
                new UmbrellaPattern(id, id.toTranslationKey("umbrella_pattern"), dyeable)
        );
    }

    public static void registerUmbrellasPatterns() {

    }
}
