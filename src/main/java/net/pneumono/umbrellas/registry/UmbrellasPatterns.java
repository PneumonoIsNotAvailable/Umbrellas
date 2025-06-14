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

    public static void registerUmbrellasPatterns() {
        registerPattern("base", true);

        registerPattern("square_bottom_left", true);
        registerPattern("square_bottom_right", true);
        registerPattern("square_top_left", true);
        registerPattern("square_top_right", true);
        registerPattern("stripe_bottom", true);
        registerPattern("stripe_top", true);
        registerPattern("stripe_left", true);
        registerPattern("stripe_right", true);
        registerPattern("stripe_vertical", true);
        registerPattern("stripe_horizontal", true);
        registerPattern("stripe_up_left", true);
        registerPattern("stripe_up_right", true);
        registerPattern("stripes_vertical", true);
        registerPattern("stripes_horizontal", true);
        registerPattern("cross_cardinal", true);
        registerPattern("cross_diagonal", true);
        registerPattern("triangle_bottom", true);
        registerPattern("triangle_top", true);
        registerPattern("triangle_left", true);
        registerPattern("triangle_right", true);
        registerPattern("triangles_bottom", true);
        registerPattern("triangles_top", true);
        registerPattern("triangles_left", true);
        registerPattern("triangles_right", true);
        registerPattern("diagonal_up_left", true);
        registerPattern("diagonal_up_right", true);
        registerPattern("diagonal_down_left", true);
        registerPattern("diagonal_down_right", true);
        registerPattern("circle_full", true);
        registerPattern("rhombus_vertical", true);
        registerPattern("rhombus_horizontal", true);
        registerPattern("half_bottom", true);
        registerPattern("half_top", true);
        registerPattern("half_left", true);
        registerPattern("half_right", true);
        registerPattern("border", true);
        registerPattern("gradient_bottom", true);
        registerPattern("gradient_top", true);
        registerPattern("gradient_left", true);
        registerPattern("gradient_right", true);
        registerPattern("globe", true);
        registerPattern("creeper", true);
        registerPattern("skull", true);
        registerPattern("flower", true);
        registerPattern("mojang", true);
        registerPattern("piglin", true);
        registerPattern("flow", true);
        registerPattern("guster", true);
        registerPattern("field_masoned", true);
        registerPattern("bordure_indented", true);

        registerPattern("accent", true);
        registerPattern("square_full", true);
        registerPattern("square_hollow", true);
        registerPattern("circle_hollow", true);
        registerPattern("half_gradient_top", true);
        registerPattern("half_gradient_bottom", true);
        registerPattern("half_gradient_left", true);
        registerPattern("half_gradient_right", true);
        registerPattern("third_top", true);
        registerPattern("third_bottom", true);
        registerPattern("third_left", true);
        registerPattern("third_right", true);
        registerPattern("third_vertical", true);
        registerPattern("third_horizontal", true);

        registerPattern("flag_aromantic", false);
        registerPattern("flag_asexual", false);
        registerPattern("flag_bisexual", false);
        registerPattern("flag_genderfluid", false);
        registerPattern("flag_intersex", false);
        registerPattern("flag_lesbian", false);
        registerPattern("flag_mlm", false);
        registerPattern("flag_nonbinary", false);
        registerPattern("flag_pansexual", false);
        registerPattern("flag_pride", false);
        registerPattern("flag_transgender", false);
        registerPattern("half_flag_left_aromantic", false);
        registerPattern("half_flag_left_asexual", false);
        registerPattern("half_flag_left_bisexual", false);
        registerPattern("half_flag_left_genderfluid", false);
        registerPattern("half_flag_left_intersex", false);
        registerPattern("half_flag_left_lesbian", false);
        registerPattern("half_flag_left_mlm", false);
        registerPattern("half_flag_left_nonbinary", false);
        registerPattern("half_flag_left_pansexual", false);
        registerPattern("half_flag_left_pride", false);
        registerPattern("half_flag_left_transgender", false);
        registerPattern("half_flag_right_aromantic", false);
        registerPattern("half_flag_right_asexual", false);
        registerPattern("half_flag_right_bisexual", false);
        registerPattern("half_flag_right_genderfluid", false);
        registerPattern("half_flag_right_intersex", false);
        registerPattern("half_flag_right_lesbian", false);
        registerPattern("half_flag_right_mlm", false);
        registerPattern("half_flag_right_nonbinary", false);
        registerPattern("half_flag_right_pansexual", false);
        registerPattern("half_flag_right_pride", false);
        registerPattern("half_flag_right_transgender", false);
    }

    private static void registerPattern(String name, boolean dyeable) {
        Identifier id = Umbrellas.id(name);
        Registry.register(UMBRELLA_PATTERN,
                id,
                new UmbrellaPattern(id, id.toTranslationKey("umbrella_pattern"), dyeable)
        );
    }
}
