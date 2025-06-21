package net.pneumono.umbrellas.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.DyeColor;
import net.pneumono.pneumonocore.datagen.PneumonoCoreTranslationBuilder;
import net.pneumono.umbrellas.Umbrellas;
import net.pneumono.umbrellas.UmbrellasConfig;
import net.pneumono.umbrellas.content.UmbrellaPattern;
import net.pneumono.umbrellas.registry.*;
import org.apache.commons.lang3.StringUtils;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public class UmbrellasEnglishLangProvider extends FabricLanguageProvider {
    public UmbrellasEnglishLangProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generateTranslations(RegistryWrapper.WrapperLookup registryLookup, TranslationBuilder translationBuilder) {
        PneumonoCoreTranslationBuilder builder = new PneumonoCoreTranslationBuilder(translationBuilder);

        builder.add(UmbrellasItems.WHITE_UMBRELLA, "White Umbrella");
        builder.add(UmbrellasItems.ORANGE_UMBRELLA, "Orange Umbrella");
        builder.add(UmbrellasItems.MAGENTA_UMBRELLA, "Magenta Umbrella");
        builder.add(UmbrellasItems.LIGHT_BLUE_UMBRELLA, "Light Blue Umbrella");
        builder.add(UmbrellasItems.YELLOW_UMBRELLA, "Yellow Umbrella");
        builder.add(UmbrellasItems.LIME_UMBRELLA, "Lime Umbrella");
        builder.add(UmbrellasItems.PINK_UMBRELLA, "Pink Umbrella");
        builder.add(UmbrellasItems.GRAY_UMBRELLA, "Gray Umbrella");
        builder.add(UmbrellasItems.LIGHT_GRAY_UMBRELLA, "Light Gray Umbrella");
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

        builder.addItemGroup(UmbrellasItems.ITEM_GROUP, "Umbrellas");

        builder.add(UmbrellasEnchantments.GLIDING.getValue().toTranslationKey("enchantment"), "Gliding");
        builder.add(UmbrellasEnchantments.BILLOWING.getValue().toTranslationKey("enchantment"), "Billowing");

        builder.add(UmbrellasMisc.CLEAN_UMBRELLA.toTranslationKey("stat"), "Umbrellas Cleaned");

        builder.addAdvancement(Umbrellas.id("get_umbrella"), "Rain, Rain, go Away", "Construct or Find an Umbrella");
        builder.addAdvancement(Umbrellas.id("get_gliding_umbrella"), "I'm Mary Poppins, Y'all!", "Enchant an Umbrella with Gliding");
        builder.addAdvancement(Umbrellas.id("get_billowing_umbrella"), "I Can See My House From Here!", "Enchant an Umbrella with Billowing");

        builder.add(UmbrellasTags.REPAIRS_UMBRELLAS, "Repairs Umbrellas");
        builder.add(UmbrellasTags.UMBRELLAS, "Umbrellas");
        builder.add(UmbrellasTags.UMBRELLA_ENCHANTABLE, "Umbrella Enchantable");

        builder.add(UmbrellasTags.BOOSTS_UMBRELLAS, "Boosts Umbrellas");
        builder.add(UmbrellasTags.UMBRELLA_BOOSTING_TOGGLEABLE, "Has Configurable Umbrella Boosting");
        builder.add(UmbrellasTags.SMOKE_PASSES_THROUGH, "Smoke Passes Through");

        builder.add(UmbrellasTags.NO_ITEM_REQUIRED, "No Umbrella Pattern Required");
        builder.add(UmbrellasTags.PRIDE, "Pride Pattern");
        builder.add(UmbrellasTags.FLOWER, "Flower Pattern");
        builder.add(UmbrellasTags.CREEPER, "Creeper Pattern");
        builder.add(UmbrellasTags.SKULL, "Skull Pattern");
        builder.add(UmbrellasTags.MOJANG, "Thing Pattern");
        builder.add(UmbrellasTags.GLOBE, "Globe Pattern");
        builder.add(UmbrellasTags.PIGLIN, "Piglin Pattern");
        builder.add(UmbrellasTags.FLOW, "Flow Pattern");
        builder.add(UmbrellasTags.GUSTER, "Guster Pattern");
        builder.add(UmbrellasTags.FIELD_MASONED, "Field Masoned Pattern");
        builder.add(UmbrellasTags.BORDURE_INDENTED, "Bordure Indented Pattern");

        builder.addConfigScreenTitle(Umbrellas.MOD_ID, "Umbrellas Configs");
        builder.addEnumConfig(
                UmbrellasConfig.SLOW_FALLING,
                "Slow Falling",
                "Whether umbrellas grant players slow falling",
                "Always", "Enchanted Only", "Never"
        );
        builder.addEnumConfig(
                UmbrellasConfig.SMOKE_BOOSTING,
                "Smoke Boosting",
                "Whether umbrellas boost players upwards when they are above sources of heat",
                "Always", "Enchanted Only", "Never"
        );
        builder.addConfig(
                UmbrellasConfig.STRICT_SMOKE_BOOSTING,
                "Strict Smoke Boosting",
                "Whether umbrellas only boost players upwards above campfires and fires. If disabled, players can also boost above lava"
        );
        builder.addConfig(
                UmbrellasConfig.DURABILITY,
                "Durability",
                "Whether umbrellas have limited durability"
        );
        builder.addConfig(
                UmbrellasConfig.DISABLE_FIREWORK_BOOSTING,
                "Disable Firework Boosting",
                "Whether Fireworks can be used to boost Elytra. This can be disabled in favor of using Campfires and a Billowing Umbrella"
        );
        builder.addConfig(
                UmbrellasConfig.ELYTRA_SMOKE_BOOSTING,
                "Elytra Smoke Boosting",
                "Whether Elytra are able to boost upwards in smoke without the use of a Billowing Umbrella"
        );
        builder.addConfig(
                UmbrellasConfig.ENCHANTMENT_GLINT,
                "Enchantment Glint",
                "Whether enchantment glint is visible on enchanted umbrellas"
        );
        builder.add("configs.category.umbrellas.umbrellas", "Umbrellas");
        builder.add("configs.category.umbrellas.elytra_changes", "Elytra Changes");
    }

    private void generateUmbrellaPatternTranslations(PneumonoCoreTranslationBuilder translationBuilder) {
        for (DyeColor color : DyeColor.values()) {
            String colorId = color.getId();
            String colorName = StringUtils.capitalize(colorId);

            BiConsumer<RegistryKey<UmbrellaPattern>, String> baseBuilder = translationBuilder.createBuilder(pattern -> pattern.getValue().toTranslationKey("umbrella_pattern", colorId));
            BiConsumer<RegistryKey<UmbrellaPattern>, String> builder = (pattern, string) -> baseBuilder.accept(pattern, String.format(string, colorName));

            // These aren't consistent with vanilla's heraldry stuff, because I think it's too confusing for the average player (and a pain to learn for me)
            // I'll probably add a builtin resource pack to make it more consistent in the future
            builder.accept(UmbrellaPatterns.BASE, "Fully %s Field");
            builder.accept(UmbrellaPatterns.SQUARE_BOTTOM_LEFT, "%s Bottom-Left Quarter");
            builder.accept(UmbrellaPatterns.SQUARE_BOTTOM_RIGHT, "%s Bottom-Right Quarter");
            builder.accept(UmbrellaPatterns.SQUARE_TOP_LEFT, "%s Top-Left Quarter");
            builder.accept(UmbrellaPatterns.SQUARE_TOP_RIGHT, "%sTop-Right Quarter");
            builder.accept(UmbrellaPatterns.STRIPE_BOTTOM, "%s Bottom Stripe");
            builder.accept(UmbrellaPatterns.STRIPE_TOP, "%s Top Stripe");
            builder.accept(UmbrellaPatterns.STRIPE_LEFT, "%s Left Stripe");
            builder.accept(UmbrellaPatterns.STRIPE_RIGHT, "%s Right Stripe");
            builder.accept(UmbrellaPatterns.STRIPE_VERTICAL, "%s Vertical Stripe");
            builder.accept(UmbrellaPatterns.STRIPE_HORIZONTAL, "%s Horizontal Stripe");
            builder.accept(UmbrellaPatterns.STRIPE_DOWN_LEFT, "%s Down-Left Stripe");
            builder.accept(UmbrellaPatterns.STRIPE_DOWN_RIGHT, "%s Down-Right Stripe");
            builder.accept(UmbrellaPatterns.STRIPES_VERTICAL, "%s Vertical Stripes");
            builder.accept(UmbrellaPatterns.STRIPES_HORIZONTAL, "%s Horizontal Stripes");
            builder.accept(UmbrellaPatterns.CROSS_CARDINAL, "%s Cross");
            builder.accept(UmbrellaPatterns.CROSS_DIAGONAL, "Diagonal %s Cross");
            builder.accept(UmbrellaPatterns.TRIANGLE_BOTTOM, "%s Bottom Triangle");
            builder.accept(UmbrellaPatterns.TRIANGLE_TOP, "%s Top Triangle");
            builder.accept(UmbrellaPatterns.TRIANGLE_LEFT, "%s Left Triangle");
            builder.accept(UmbrellaPatterns.TRIANGLE_RIGHT, "%s Right Triangle");
            builder.accept(UmbrellaPatterns.TRIANGLES_BOTTOM, "%s Bottom Triangles");
            builder.accept(UmbrellaPatterns.TRIANGLES_TOP, "%s Top Triangles");
            builder.accept(UmbrellaPatterns.TRIANGLES_LEFT, "%s Left Triangles");
            builder.accept(UmbrellaPatterns.TRIANGLES_RIGHT, "%s Right Triangles");
            builder.accept(UmbrellaPatterns.DIAGONAL_UP_LEFT, "%s Top-Left Diagonal");
            builder.accept(UmbrellaPatterns.DIAGONAL_UP_RIGHT, "%s Top-Right Diagonal");
            builder.accept(UmbrellaPatterns.DIAGONAL_DOWN_LEFT, "%s Bottom-Left Diagonal");
            builder.accept(UmbrellaPatterns.DIAGONAL_DOWN_RIGHT, "%s Bottom-Right Diagonal");
            builder.accept(UmbrellaPatterns.CIRCLE_FULL, "%s Circle");
            builder.accept(UmbrellaPatterns.RHOMBUS_VERTICAL, "%s Vertical Rhombus");
            builder.accept(UmbrellaPatterns.RHOMBUS_HORIZONTAL, "%s Horizontal Rhombus");
            builder.accept(UmbrellaPatterns.HALF_BOTTOM, "%s Bottom Half");
            builder.accept(UmbrellaPatterns.HALF_TOP, "%s Top Half");
            builder.accept(UmbrellaPatterns.HALF_LEFT, "%s Left Half");
            builder.accept(UmbrellaPatterns.HALF_RIGHT, "%s Right Half");
            builder.accept(UmbrellaPatterns.BORDER, "%s Border");
            builder.accept(UmbrellaPatterns.GRADIENT_BOTTOM, "%s Bottom Gradient");
            builder.accept(UmbrellaPatterns.GRADIENT_TOP, "%s Top Gradient");
            builder.accept(UmbrellaPatterns.GRADIENT_LEFT, "%s Left Gradient");
            builder.accept(UmbrellaPatterns.GRADIENT_RIGHT, "%s Right Gradient");
            builder.accept(UmbrellaPatterns.GLOBE, "%s Globe");
            builder.accept(UmbrellaPatterns.CREEPER, "%s Creeper");
            builder.accept(UmbrellaPatterns.SKULL, "%s Skull");
            builder.accept(UmbrellaPatterns.FLOWER, "%s Flower");
            builder.accept(UmbrellaPatterns.MOJANG, "%s Thing");
            builder.accept(UmbrellaPatterns.PIGLIN, "%s Snout");
            builder.accept(UmbrellaPatterns.FLOW, "%s Flow");
            builder.accept(UmbrellaPatterns.GUSTER, "%s Guster");
            builder.accept(UmbrellaPatterns.FIELD_MASONED, "%s Field Masoned");
            builder.accept(UmbrellaPatterns.BORDURE_INDENTED, "%s Bordure Indented");

            builder.accept(UmbrellaPatterns.ACCENT, "%s Thin Border");
            builder.accept(UmbrellaPatterns.SQUARE_FULL, "%s Square");
            builder.accept(UmbrellaPatterns.SQUARE_HOLLOW, "Hollow %s Square");
            builder.accept(UmbrellaPatterns.CIRCLE_HOLLOW, "Hollow %s Circle");
            builder.accept(UmbrellaPatterns.HALF_GRADIENT_BOTTOM, "%s Bottom Half-Gradient");
            builder.accept(UmbrellaPatterns.HALF_GRADIENT_TOP, "%s Top Half-Gradient");
            builder.accept(UmbrellaPatterns.HALF_GRADIENT_LEFT, "%s Left Half-Gradient");
            builder.accept(UmbrellaPatterns.HALF_GRADIENT_RIGHT, "%s Right Half-Gradient");
            builder.accept(UmbrellaPatterns.THIRD_BOTTOM, "%s Bottom Third");
            builder.accept(UmbrellaPatterns.THIRD_TOP, "%s Top Third");
            builder.accept(UmbrellaPatterns.THIRD_LEFT, "%s Left Third");
            builder.accept(UmbrellaPatterns.THIRD_RIGHT, "%s Right Third");
            builder.accept(UmbrellaPatterns.THIRD_VERTICAL, "%s Vertical Third");
            builder.accept(UmbrellaPatterns.THIRD_HORIZONTAL, "%s Horizontal Third");
        }

        BiConsumer<RegistryKey<UmbrellaPattern>, String> builder = translationBuilder.createBuilder(pattern -> pattern.getValue().toTranslationKey("umbrella_pattern"));

        builder.accept(UmbrellaPatterns.FLAG_AROMANTIC, "Aromantic Flag");
        builder.accept(UmbrellaPatterns.FLAG_ASEXUAL, "Asexual Flag");
        builder.accept(UmbrellaPatterns.FLAG_BISEXUAL, "Bisexual Flag");
        builder.accept(UmbrellaPatterns.FLAG_GENDERFLUID, "Genderfluid Flag");
        builder.accept(UmbrellaPatterns.FLAG_INTERSEX, "Intersex Flag");
        builder.accept(UmbrellaPatterns.FLAG_LESBIAN, "Lesbian Flag");
        builder.accept(UmbrellaPatterns.FLAG_MLM, "MLM Flag");
        builder.accept(UmbrellaPatterns.FLAG_NONBINARY, "Non-Binary Flag");
        builder.accept(UmbrellaPatterns.FLAG_PANSEXUAL, "Pansexual Flag");
        builder.accept(UmbrellaPatterns.FLAG_PRIDE, "Pride Flag");
        builder.accept(UmbrellaPatterns.FLAG_TRANSGENDER, "Transgender Flag");

        builder.accept(UmbrellaPatterns.HALF_FLAG_LEFT_AROMANTIC, "Left Aromantic Half-Flag");
        builder.accept(UmbrellaPatterns.HALF_FLAG_LEFT_ASEXUAL, "Left Asexual Half-Flag");
        builder.accept(UmbrellaPatterns.HALF_FLAG_LEFT_BISEXUAL, "Left Bisexual Half-Flag");
        builder.accept(UmbrellaPatterns.HALF_FLAG_LEFT_GENDERFLUID, "Left Genderfluid Half-Flag");
        builder.accept(UmbrellaPatterns.HALF_FLAG_LEFT_INTERSEX, "Left Intersex Half-Flag");
        builder.accept(UmbrellaPatterns.HALF_FLAG_LEFT_LESBIAN, "Left Lesbian Half-Flag");
        builder.accept(UmbrellaPatterns.HALF_FLAG_LEFT_MLM, "Left MLM Half-Flag");
        builder.accept(UmbrellaPatterns.HALF_FLAG_LEFT_NONBINARY, "Left Non-Binary Half-Flag");
        builder.accept(UmbrellaPatterns.HALF_FLAG_LEFT_PANSEXUAL, "Left Pansexual Half-Flag");
        builder.accept(UmbrellaPatterns.HALF_FLAG_LEFT_PRIDE, "Left Pride Half-Flag");
        builder.accept(UmbrellaPatterns.HALF_FLAG_LEFT_TRANSGENDER, "Left Transgender Half-Flag");

        builder.accept(UmbrellaPatterns.HALF_FLAG_RIGHT_AROMANTIC, "Right Aromantic Half-Flag");
        builder.accept(UmbrellaPatterns.HALF_FLAG_RIGHT_ASEXUAL, "Right Asexual Half-Flag");
        builder.accept(UmbrellaPatterns.HALF_FLAG_RIGHT_BISEXUAL, "Right Bisexual Half-Flag");
        builder.accept(UmbrellaPatterns.HALF_FLAG_RIGHT_GENDERFLUID, "Right Genderfluid Half-Flag");
        builder.accept(UmbrellaPatterns.HALF_FLAG_RIGHT_INTERSEX, "Right Intersex Half-Flag");
        builder.accept(UmbrellaPatterns.HALF_FLAG_RIGHT_LESBIAN, "Right Lesbian Half-Flag");
        builder.accept(UmbrellaPatterns.HALF_FLAG_RIGHT_MLM, "Right MLM Half-Flag");
        builder.accept(UmbrellaPatterns.HALF_FLAG_RIGHT_NONBINARY, "Right Non-Binary Half-Flag");
        builder.accept(UmbrellaPatterns.HALF_FLAG_RIGHT_PANSEXUAL, "Right Pansexual Half-Flag");
        builder.accept(UmbrellaPatterns.HALF_FLAG_RIGHT_PRIDE, "Right Pride Half-Flag");
        builder.accept(UmbrellaPatterns.HALF_FLAG_RIGHT_TRANSGENDER, "Right Transgender Half-Flag");
    }
}
