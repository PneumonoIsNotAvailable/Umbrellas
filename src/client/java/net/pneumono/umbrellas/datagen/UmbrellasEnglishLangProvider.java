package net.pneumono.umbrellas.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.DyeColor;
import net.pneumono.pneumonocore.datagen.PneumonoDatagenUtil;
import net.pneumono.umbrellas.Umbrellas;
import net.pneumono.umbrellas.patterns.UmbrellaPattern;
import net.pneumono.umbrellas.registry.UmbrellasBlocks;
import net.pneumono.umbrellas.registry.UmbrellasItems;
import net.pneumono.umbrellas.registry.UmbrellasMisc;
import net.pneumono.umbrellas.registry.UmbrellasPatterns;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.BiConsumer;

import java.util.concurrent.CompletableFuture;

public class UmbrellasEnglishLangProvider extends FabricLanguageProvider {
    public UmbrellasEnglishLangProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generateTranslations(RegistryWrapper.WrapperLookup registryLookup, TranslationBuilder builder) {
        builder.add(UmbrellasItems.WHITE_UMBRELLA, "White Umbrella");
        builder.add(UmbrellasItems.ORANGE_UMBRELLA, "Orange Umbrella");
        builder.add(UmbrellasItems.MAGENTA_UMBRELLA, "Magenta Umbrella");
        builder.add(UmbrellasItems.LIGHT_BLUE_UMBRELLA, "Light_blue Umbrella");
        builder.add(UmbrellasItems.YELLOW_UMBRELLA, "Yellow Umbrella");
        builder.add(UmbrellasItems.LIME_UMBRELLA, "Lime Umbrella");
        builder.add(UmbrellasItems.PINK_UMBRELLA, "Pink Umbrella");
        builder.add(UmbrellasItems.GRAY_UMBRELLA, "Gray Umbrella");
        builder.add(UmbrellasItems.LIGHT_GRAY_UMBRELLA, "Light_gray Umbrella");
        builder.add(UmbrellasItems.CYAN_UMBRELLA, "Cyan Umbrella");
        builder.add(UmbrellasItems.PURPLE_UMBRELLA, "Purple Umbrella");
        builder.add(UmbrellasItems.BLUE_UMBRELLA, "Blue Umbrella");
        builder.add(UmbrellasItems.BROWN_UMBRELLA, "Brown Umbrella");
        builder.add(UmbrellasItems.GREEN_UMBRELLA, "Green Umbrella");
        builder.add(UmbrellasItems.RED_UMBRELLA, "Red Umbrella");
        builder.add(UmbrellasItems.BLACK_UMBRELLA, "Black Umbrella");

        builder.add(UmbrellasItems.ANIMALS_UMBRELLA, "Animals Umbrella");
        builder.add(UmbrellasItems.AZALEA_UMBRELLA, "Azalea Umbrella");
        builder.add(UmbrellasItems.GALACTIC_UMBRELLA, "Galactic Umbrella");
        builder.add(UmbrellasItems.GOTHIC_UMBRELLA, "Gothic Umbrella");
        builder.add(UmbrellasItems.JELLYFISH_UMBRELLA, "Jellyfish Umbrella");

        builder.add(UmbrellasItems.FLOWER_UMBRELLA_PATTERN, "Flower Umbrella Pattern");
        builder.add(UmbrellasItems.CREEPER_UMBRELLA_PATTERN, "Creeper Umbrella Pattern");
        builder.add(UmbrellasItems.SKULL_UMBRELLA_PATTERN, "Skull Umbrella Pattern");
        builder.add(UmbrellasItems.MOJANG_UMBRELLA_PATTERN, "Thing Umbrella Pattern");
        builder.add(UmbrellasItems.GLOBE_UMBRELLA_PATTERN, "Globe Umbrella Pattern");
        builder.add(UmbrellasItems.PIGLIN_UMBRELLA_PATTERN, "Piglin Umbrella Pattern");
        builder.add(UmbrellasItems.FLOW_UMBRELLA_PATTERN, "Flow Umbrella Pattern");
        builder.add(UmbrellasItems.GUSTER_UMBRELLA_PATTERN, "Guster Umbrella Pattern");
        builder.add(UmbrellasItems.FIELD_MASONED_UMBRELLA_PATTERN, "Field Masoned Umbrella Pattern");
        builder.add(UmbrellasItems.BORDURE_INDENTED_UMBRELLA_PATTERN, "Bordure Indented Umbrella Pattern");
        builder.add(UmbrellasItems.PRIDE_UMBRELLA_PATTERN, "Pride Umbrella Pattern");

        builder.add("item.umbrellas.umbrella_pattern.no_dye", "Does not require Dye");
        generateUmbrellaPatternTranslations(builder);

        builder.add(UmbrellasItems.UMBRELLA_STAND, "Umbrella Stand");
        builder.add(UmbrellasBlocks.UMBRELLA_STAND, "Umbrella Stand");

        builder.add(UmbrellasItems.ITEM_GROUP, "Umbrellas");

        builder.add(UmbrellasMisc.GLIDING.getValue().toTranslationKey("enchantment"), "Gliding");
        builder.add(UmbrellasMisc.WIND_CATCHING.getValue().toTranslationKey("enchantment"), "Wind Catching");

        builder.add(UmbrellasMisc.CLEAN_UMBRELLA.toTranslationKey("stat"), "Umbrellas Cleaned");

        builder.add("advancements.umbrellas.get_umbrella.name", "Rain, Rain, go Away");
        builder.add("advancements.umbrellas.get_umbrella.desc", "Construct or Find an Umbrella");
        builder.add("advancements.umbrellas.get_gliding_umbrella.name", "I'm Mary Poppins, Y'all!");
        builder.add("advancements.umbrellas.get_gliding_umbrella.desc", "Enchant an Umbrella with Gliding");
        builder.add("advancements.umbrellas.get_wind_catching_umbrella.name", "I Can See My House From Here!");
        builder.add("advancements.umbrellas.get_wind_catching_umbrella.desc", "Enchant an Umbrella with Wind Catching");

        PneumonoDatagenUtil.generateConfigScreenTranslation(builder, Umbrellas.MOD_ID, "Umbrellas Cleaned");
        PneumonoDatagenUtil.generateConfigTranslations(builder,
                Umbrellas.SLOW_FALLING,
                "Slow Falling",
                "Whether umbrellas grant players slow falling"
        );
        PneumonoDatagenUtil.generateConfigTranslations(builder,
                Umbrellas.SMOKE_BOOSTING,
                "Smoke Boosting",
                "Whether umbrellas boost players upwards when they are above sources of heat"
        );
        PneumonoDatagenUtil.generateConfigTranslations(builder,
                Umbrellas.DURABILITY,
                "Durability",
                "Whether umbrellas have limited durability"
        );
        PneumonoDatagenUtil.generateConfigTranslations(builder,
                Umbrellas.ENCHANTMENT_GLINT,
                "Enchantment Glint",
                "Whether enchantment glint is visible on enchanted umbrellas"
        );
    }

    private void generateUmbrellaPatternTranslations(TranslationBuilder translationBuilder) {
        for (DyeColor color : DyeColor.values()) {
            String colorId = color.getId();
            String colorName = StringUtils.capitalize(colorId);

            translationBuilder.add(
                    UmbrellasPatterns.BASE.translationKey() + "." + colorId,
                    "Fully " + colorName + " Field"
            );

            BiConsumer<UmbrellaPattern, String> builder = (umbrellaPattern, string) ->
                    translationBuilder.add(umbrellaPattern.translationKey() + "." + colorId, String.format(string, colorName));

            // These aren't consistent with vanilla's heraldry stuff, because I think it's too confusing for the average player (and a pain to learn for me)
            // I'll probably add a builtin resource pack to make it more consistent in the future
            builder.accept(UmbrellasPatterns.SQUARE_BOTTOM_LEFT, "%s Bottom-Left Quarter");
            builder.accept(UmbrellasPatterns.SQUARE_BOTTOM_RIGHT, "%s Bottom-Right Quarter");
            builder.accept(UmbrellasPatterns.SQUARE_TOP_LEFT, "%s Top-Left Quarter");
            builder.accept(UmbrellasPatterns.SQUARE_TOP_RIGHT, "%sTop-Right Quarter");
            builder.accept(UmbrellasPatterns.STRIPE_BOTTOM, "%s Bottom Stripe");
            builder.accept(UmbrellasPatterns.STRIPE_TOP, "%s Top Stripe");
            builder.accept(UmbrellasPatterns.STRIPE_LEFT, "%s Left Stripe");
            builder.accept(UmbrellasPatterns.STRIPE_RIGHT, "%s Right Stripe");
            builder.accept(UmbrellasPatterns.STRIPE_VERTICAL, "%s Vertical Stripe");
            builder.accept(UmbrellasPatterns.STRIPE_HORIZONTAL, "%s Horizontal Stripe");
            builder.accept(UmbrellasPatterns.STRIPE_DOWN_LEFT, "%s Down-Left Stripe");
            builder.accept(UmbrellasPatterns.STRIPE_DOWN_RIGHT, "%s Down-Right Stripe");
            builder.accept(UmbrellasPatterns.STRIPES_VERTICAL, "%s Vertical Stripes");
            builder.accept(UmbrellasPatterns.STRIPES_HORIZONTAL, "%s Horizontal Stripes");
            builder.accept(UmbrellasPatterns.CROSS_CARDINAL, "%s Cross");
            builder.accept(UmbrellasPatterns.CROSS_DIAGONAL, "Diagonal %s Cross");
            builder.accept(UmbrellasPatterns.TRIANGLE_BOTTOM, "%s Bottom Triangle");
            builder.accept(UmbrellasPatterns.TRIANGLE_TOP, "%s Top Triangle");
            builder.accept(UmbrellasPatterns.TRIANGLE_LEFT, "%s Left Triangle");
            builder.accept(UmbrellasPatterns.TRIANGLE_RIGHT, "%s Right Triangle");
            builder.accept(UmbrellasPatterns.TRIANGLES_BOTTOM, "%s Bottom Triangles");
            builder.accept(UmbrellasPatterns.TRIANGLES_TOP, "%s Top Triangles");
            builder.accept(UmbrellasPatterns.TRIANGLES_LEFT, "%s Left Triangles");
            builder.accept(UmbrellasPatterns.TRIANGLES_RIGHT, "%s Right Triangles");
            builder.accept(UmbrellasPatterns.DIAGONAL_UP_LEFT, "%s Top-Left Diagonal");
            builder.accept(UmbrellasPatterns.DIAGONAL_UP_RIGHT, "%s Top-Right Diagonal");
            builder.accept(UmbrellasPatterns.DIAGONAL_DOWN_LEFT, "%s Bottom-Left Diagonal");
            builder.accept(UmbrellasPatterns.DIAGONAL_DOWN_RIGHT, "%s Bottom-Right Diagonal");
            builder.accept(UmbrellasPatterns.CIRCLE_FULL, "%s Circle");
            builder.accept(UmbrellasPatterns.RHOMBUS_VERTICAL, "%s Vertical Rhombus");
            builder.accept(UmbrellasPatterns.RHOMBUS_HORIZONTAL, "%s Horizontal Rhombus");
            builder.accept(UmbrellasPatterns.HALF_BOTTOM, "%s Bottom Half");
            builder.accept(UmbrellasPatterns.HALF_TOP, "%s Top Half");
            builder.accept(UmbrellasPatterns.HALF_LEFT, "%s Left Half");
            builder.accept(UmbrellasPatterns.HALF_RIGHT, "%s Right Half");
            builder.accept(UmbrellasPatterns.BORDER, "%s Border");
            builder.accept(UmbrellasPatterns.GRADIENT_BOTTOM, "%s Bottom Gradient");
            builder.accept(UmbrellasPatterns.GRADIENT_TOP, "%s Top Gradient");
            builder.accept(UmbrellasPatterns.GRADIENT_LEFT, "%s Left Gradient");
            builder.accept(UmbrellasPatterns.GRADIENT_RIGHT, "%s Right Gradient");
            builder.accept(UmbrellasPatterns.GLOBE, "%s Globe");
            builder.accept(UmbrellasPatterns.CREEPER, "%s Creeper");
            builder.accept(UmbrellasPatterns.SKULL, "%s Skull");
            builder.accept(UmbrellasPatterns.FLOWER, "%s Flower");
            builder.accept(UmbrellasPatterns.MOJANG, "%s Thing");
            builder.accept(UmbrellasPatterns.PIGLIN, "%s Snout");
            builder.accept(UmbrellasPatterns.FLOW, "%s Flow");
            builder.accept(UmbrellasPatterns.GUSTER, "%s Guster");
            builder.accept(UmbrellasPatterns.FIELD_MASONED, "%s Field Masoned");
            builder.accept(UmbrellasPatterns.BORDURE_INDENTED, "%s Indented Border");

            builder.accept(UmbrellasPatterns.ACCENT, "%s Thin Border");
            builder.accept(UmbrellasPatterns.SQUARE_FULL, "%s Square");
            builder.accept(UmbrellasPatterns.SQUARE_HOLLOW, "Hollow %s Square");
            builder.accept(UmbrellasPatterns.CIRCLE_HOLLOW, "Hollow %s Circle");
            builder.accept(UmbrellasPatterns.HALF_GRADIENT_BOTTOM, "%s Bottom Half-Gradient");
            builder.accept(UmbrellasPatterns.HALF_GRADIENT_TOP, "%s Top Half-Gradient");
            builder.accept(UmbrellasPatterns.HALF_GRADIENT_LEFT, "%s Left Half-Gradient");
            builder.accept(UmbrellasPatterns.HALF_GRADIENT_RIGHT, "%s Right Half-Gradient");
            builder.accept(UmbrellasPatterns.THIRD_BOTTOM, "%s Bottom Third");
            builder.accept(UmbrellasPatterns.THIRD_TOP, "%s Top Third");
            builder.accept(UmbrellasPatterns.THIRD_LEFT, "%s Left Third");
            builder.accept(UmbrellasPatterns.THIRD_RIGHT, "%s Right Third");
            builder.accept(UmbrellasPatterns.THIRD_VERTICAL, "%s Vertical Third");
            builder.accept(UmbrellasPatterns.THIRD_HORIZONTAL, "%s Horizontal Third");
        }

        BiConsumer<UmbrellaPattern, String> builder = (umbrellaPattern, string) ->
                translationBuilder.add(umbrellaPattern.translationKey(), string);

        builder.accept(UmbrellasPatterns.FLAG_AROMANTIC, "Aromantic Flag");
        builder.accept(UmbrellasPatterns.FLAG_ASEXUAL, "Asexual Flag");
        builder.accept(UmbrellasPatterns.FLAG_BISEXUAL, "Bisexual Flag");
        builder.accept(UmbrellasPatterns.FLAG_GENDERFLUID, "Genderfluid Flag");
        builder.accept(UmbrellasPatterns.FLAG_INTERSEX, "Intersex Flag");
        builder.accept(UmbrellasPatterns.FLAG_LESBIAN, "Lesbian Flag");
        builder.accept(UmbrellasPatterns.FLAG_MLM, "MLM Flag");
        builder.accept(UmbrellasPatterns.FLAG_NONBINARY, "Non-Binary Flag");
        builder.accept(UmbrellasPatterns.FLAG_PANSEXUAL, "Pansexual Flag");
        builder.accept(UmbrellasPatterns.FLAG_PRIDE, "Pride Flag");
        builder.accept(UmbrellasPatterns.FLAG_TRANSGENDER, "Transgender Flag");

        builder.accept(UmbrellasPatterns.HALF_FLAG_LEFT_AROMANTIC, "Left Aromantic Half-Flag");
        builder.accept(UmbrellasPatterns.HALF_FLAG_LEFT_ASEXUAL, "Left Asexual Half-Flag");
        builder.accept(UmbrellasPatterns.HALF_FLAG_LEFT_BISEXUAL, "Left Bisexual Half-Flag");
        builder.accept(UmbrellasPatterns.HALF_FLAG_LEFT_GENDERFLUID, "Left Genderfluid Half-Flag");
        builder.accept(UmbrellasPatterns.HALF_FLAG_LEFT_INTERSEX, "Left Intersex Half-Flag");
        builder.accept(UmbrellasPatterns.HALF_FLAG_LEFT_LESBIAN, "Left Lesbian Half-Flag");
        builder.accept(UmbrellasPatterns.HALF_FLAG_LEFT_MLM, "Left MLM Half-Flag");
        builder.accept(UmbrellasPatterns.HALF_FLAG_LEFT_NONBINARY, "Left Non-Binary Half-Flag");
        builder.accept(UmbrellasPatterns.HALF_FLAG_LEFT_PANSEXUAL, "Left Pansexual Half-Flag");
        builder.accept(UmbrellasPatterns.HALF_FLAG_LEFT_PRIDE, "Left Pride Half-Flag");
        builder.accept(UmbrellasPatterns.HALF_FLAG_LEFT_TRANSGENDER, "Left Transgender Half-Flag");

        builder.accept(UmbrellasPatterns.HALF_FLAG_RIGHT_AROMANTIC, "Right Aromantic Half-Flag");
        builder.accept(UmbrellasPatterns.HALF_FLAG_RIGHT_ASEXUAL, "Right Asexual Half-Flag");
        builder.accept(UmbrellasPatterns.HALF_FLAG_RIGHT_BISEXUAL, "Right Bisexual Half-Flag");
        builder.accept(UmbrellasPatterns.HALF_FLAG_RIGHT_GENDERFLUID, "Right Genderfluid Half-Flag");
        builder.accept(UmbrellasPatterns.HALF_FLAG_RIGHT_INTERSEX, "Right Intersex Half-Flag");
        builder.accept(UmbrellasPatterns.HALF_FLAG_RIGHT_LESBIAN, "Right Lesbian Half-Flag");
        builder.accept(UmbrellasPatterns.HALF_FLAG_RIGHT_MLM, "Right MLM Half-Flag");
        builder.accept(UmbrellasPatterns.HALF_FLAG_RIGHT_NONBINARY, "Right Non-Binary Half-Flag");
        builder.accept(UmbrellasPatterns.HALF_FLAG_RIGHT_PANSEXUAL, "Right Pansexual Half-Flag");
        builder.accept(UmbrellasPatterns.HALF_FLAG_RIGHT_PRIDE, "Right Pride Half-Flag");
        builder.accept(UmbrellasPatterns.HALF_FLAG_RIGHT_TRANSGENDER, "Right Transgender Half-Flag");
    }
}
