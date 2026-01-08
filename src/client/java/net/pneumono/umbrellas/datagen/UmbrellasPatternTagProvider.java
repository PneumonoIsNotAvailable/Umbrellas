package net.pneumono.umbrellas.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.pneumono.umbrellas.content.UmbrellaPattern;
import net.pneumono.umbrellas.registry.UmbrellaPatterns;
import net.pneumono.umbrellas.registry.UmbrellasTags;

import java.util.concurrent.CompletableFuture;

public class UmbrellasPatternTagProvider extends FabricTagProvider<UmbrellaPattern> {
    public UmbrellasPatternTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, UmbrellaPatterns.UMBRELLA_PATTERN_KEY, registriesFuture);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.builder(UmbrellasTags.NO_ITEM_REQUIRED).add(
                UmbrellaPatterns.SQUARE_BOTTOM_LEFT,
                UmbrellaPatterns.SQUARE_BOTTOM_RIGHT,
                UmbrellaPatterns.SQUARE_TOP_LEFT,
                UmbrellaPatterns.SQUARE_TOP_RIGHT,
                UmbrellaPatterns.STRIPE_BOTTOM,
                UmbrellaPatterns.STRIPE_TOP,
                UmbrellaPatterns.STRIPE_LEFT,
                UmbrellaPatterns.STRIPE_RIGHT,
                UmbrellaPatterns.STRIPE_VERTICAL,
                UmbrellaPatterns.STRIPE_HORIZONTAL,
                UmbrellaPatterns.STRIPE_DOWN_LEFT,
                UmbrellaPatterns.STRIPE_DOWN_RIGHT,
                UmbrellaPatterns.STRIPES_VERTICAL,
                UmbrellaPatterns.STRIPES_HORIZONTAL,
                UmbrellaPatterns.CROSS_CARDINAL,
                UmbrellaPatterns.CROSS_DIAGONAL,
                UmbrellaPatterns.TRIANGLE_BOTTOM,
                UmbrellaPatterns.TRIANGLE_TOP,
                UmbrellaPatterns.TRIANGLE_LEFT,
                UmbrellaPatterns.TRIANGLE_RIGHT,
                UmbrellaPatterns.TRIANGLES_BOTTOM,
                UmbrellaPatterns.TRIANGLES_TOP,
                UmbrellaPatterns.TRIANGLES_LEFT,
                UmbrellaPatterns.TRIANGLES_RIGHT,
                UmbrellaPatterns.DIAGONAL_UP_LEFT,
                UmbrellaPatterns.DIAGONAL_UP_RIGHT,
                UmbrellaPatterns.DIAGONAL_DOWN_LEFT,
                UmbrellaPatterns.DIAGONAL_DOWN_RIGHT,
                UmbrellaPatterns.CIRCLE_FULL,
                UmbrellaPatterns.RHOMBUS_VERTICAL,
                UmbrellaPatterns.RHOMBUS_HORIZONTAL,
                UmbrellaPatterns.HALF_BOTTOM,
                UmbrellaPatterns.HALF_TOP,
                UmbrellaPatterns.HALF_LEFT,
                UmbrellaPatterns.HALF_RIGHT,
                UmbrellaPatterns.BORDER,
                UmbrellaPatterns.GRADIENT_BOTTOM,
                UmbrellaPatterns.GRADIENT_TOP,
                UmbrellaPatterns.GRADIENT_LEFT,
                UmbrellaPatterns.GRADIENT_RIGHT,
                UmbrellaPatterns.ACCENT,
                UmbrellaPatterns.SQUARE_FULL,
                UmbrellaPatterns.SQUARE_HOLLOW,
                UmbrellaPatterns.CIRCLE_HOLLOW,
                UmbrellaPatterns.HALF_GRADIENT_BOTTOM,
                UmbrellaPatterns.HALF_GRADIENT_TOP,
                UmbrellaPatterns.HALF_GRADIENT_LEFT,
                UmbrellaPatterns.HALF_GRADIENT_RIGHT,
                UmbrellaPatterns.THIRD_BOTTOM,
                UmbrellaPatterns.THIRD_TOP,
                UmbrellaPatterns.THIRD_LEFT,
                UmbrellaPatterns.THIRD_RIGHT,
                UmbrellaPatterns.THIRD_VERTICAL,
                UmbrellaPatterns.THIRD_HORIZONTAL
        );
        this.builder(UmbrellasTags.PRIDE).add(
                UmbrellaPatterns.FLAG_AROMANTIC,
                UmbrellaPatterns.FLAG_ASEXUAL,
                UmbrellaPatterns.FLAG_BISEXUAL,
                UmbrellaPatterns.FLAG_GENDERFLUID,
                UmbrellaPatterns.FLAG_INTERSEX,
                UmbrellaPatterns.FLAG_LESBIAN,
                UmbrellaPatterns.FLAG_MLM,
                UmbrellaPatterns.FLAG_NONBINARY,
                UmbrellaPatterns.FLAG_PANSEXUAL,
                UmbrellaPatterns.FLAG_PRIDE,
                UmbrellaPatterns.FLAG_TRANSGENDER,
                UmbrellaPatterns.HALF_FLAG_LEFT_AROMANTIC,
                UmbrellaPatterns.HALF_FLAG_LEFT_ASEXUAL,
                UmbrellaPatterns.HALF_FLAG_LEFT_BISEXUAL,
                UmbrellaPatterns.HALF_FLAG_LEFT_GENDERFLUID,
                UmbrellaPatterns.HALF_FLAG_LEFT_INTERSEX,
                UmbrellaPatterns.HALF_FLAG_LEFT_LESBIAN,
                UmbrellaPatterns.HALF_FLAG_LEFT_MLM,
                UmbrellaPatterns.HALF_FLAG_LEFT_NONBINARY,
                UmbrellaPatterns.HALF_FLAG_LEFT_PANSEXUAL,
                UmbrellaPatterns.HALF_FLAG_LEFT_PRIDE,
                UmbrellaPatterns.HALF_FLAG_LEFT_TRANSGENDER,
                UmbrellaPatterns.HALF_FLAG_RIGHT_AROMANTIC,
                UmbrellaPatterns.HALF_FLAG_RIGHT_ASEXUAL,
                UmbrellaPatterns.HALF_FLAG_RIGHT_BISEXUAL,
                UmbrellaPatterns.HALF_FLAG_RIGHT_GENDERFLUID,
                UmbrellaPatterns.HALF_FLAG_RIGHT_INTERSEX,
                UmbrellaPatterns.HALF_FLAG_RIGHT_LESBIAN,
                UmbrellaPatterns.HALF_FLAG_RIGHT_MLM,
                UmbrellaPatterns.HALF_FLAG_RIGHT_NONBINARY,
                UmbrellaPatterns.HALF_FLAG_RIGHT_PANSEXUAL,
                UmbrellaPatterns.HALF_FLAG_RIGHT_PRIDE,
                UmbrellaPatterns.HALF_FLAG_RIGHT_TRANSGENDER
        );
        this.builder(UmbrellasTags.FLOWER).add(UmbrellaPatterns.FLOWER);
        this.builder(UmbrellasTags.CREEPER).add(UmbrellaPatterns.CREEPER);
        this.builder(UmbrellasTags.SKULL).add(UmbrellaPatterns.SKULL);
        this.builder(UmbrellasTags.MOJANG).add(UmbrellaPatterns.MOJANG);
        this.builder(UmbrellasTags.GLOBE).add(UmbrellaPatterns.GLOBE);
        this.builder(UmbrellasTags.PIGLIN).add(UmbrellaPatterns.PIGLIN);
        this.builder(UmbrellasTags.FLOW).add(UmbrellaPatterns.FLOW);
        this.builder(UmbrellasTags.GUSTER).add(UmbrellaPatterns.GUSTER);
        this.builder(UmbrellasTags.FIELD_MASONED).add(UmbrellaPatterns.FIELD_MASONED);
        this.builder(UmbrellasTags.BORDURE_INDENTED).add(UmbrellaPatterns.BORDURE_INDENTED);
    }
}
