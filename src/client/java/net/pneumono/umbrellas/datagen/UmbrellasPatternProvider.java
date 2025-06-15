package net.pneumono.umbrellas.datagen;

import com.mojang.serialization.Codec;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricCodecDataProvider;
import net.minecraft.data.DataOutput;
import net.minecraft.registry.*;
import net.minecraft.util.Identifier;
import net.pneumono.umbrellas.content.UmbrellaPattern;
import net.pneumono.umbrellas.registry.UmbrellaPatterns;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public class UmbrellasPatternProvider extends FabricCodecDataProvider<UmbrellaPattern> {
    public UmbrellasPatternProvider(
            FabricDataOutput dataOutput,
            CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture,
            DataOutput.OutputType outputType,
            String directoryName,
            Codec<UmbrellaPattern> codec
    ) {
        super(dataOutput, registriesFuture, outputType, directoryName, codec);
    }
    
    private static final List<UmbrellaPattern> PATTERNS = List.of(
            create(UmbrellaPatterns.BASE),
            create(UmbrellaPatterns.SQUARE_BOTTOM_LEFT),
            create(UmbrellaPatterns.SQUARE_BOTTOM_RIGHT),
            create(UmbrellaPatterns.SQUARE_TOP_LEFT),
            create(UmbrellaPatterns.SQUARE_TOP_RIGHT),
            create(UmbrellaPatterns.STRIPE_BOTTOM),
            create(UmbrellaPatterns.STRIPE_TOP),
            create(UmbrellaPatterns.STRIPE_LEFT),
            create(UmbrellaPatterns.STRIPE_RIGHT),
            create(UmbrellaPatterns.STRIPE_VERTICAL),
            create(UmbrellaPatterns.STRIPE_HORIZONTAL),
            create(UmbrellaPatterns.STRIPE_DOWN_LEFT),
            create(UmbrellaPatterns.STRIPE_DOWN_RIGHT),
            create(UmbrellaPatterns.STRIPES_VERTICAL),
            create(UmbrellaPatterns.STRIPES_HORIZONTAL),
            create(UmbrellaPatterns.CROSS_CARDINAL),
            create(UmbrellaPatterns.CROSS_DIAGONAL),
            create(UmbrellaPatterns.TRIANGLE_BOTTOM),
            create(UmbrellaPatterns.TRIANGLE_TOP),
            create(UmbrellaPatterns.TRIANGLE_LEFT),
            create(UmbrellaPatterns.TRIANGLE_RIGHT),
            create(UmbrellaPatterns.TRIANGLES_BOTTOM),
            create(UmbrellaPatterns.TRIANGLES_TOP),
            create(UmbrellaPatterns.TRIANGLES_LEFT),
            create(UmbrellaPatterns.TRIANGLES_RIGHT),
            create(UmbrellaPatterns.DIAGONAL_UP_LEFT),
            create(UmbrellaPatterns.DIAGONAL_UP_RIGHT),
            create(UmbrellaPatterns.DIAGONAL_DOWN_LEFT),
            create(UmbrellaPatterns.DIAGONAL_DOWN_RIGHT),
            create(UmbrellaPatterns.CIRCLE_FULL),
            create(UmbrellaPatterns.RHOMBUS_VERTICAL),
            create(UmbrellaPatterns.RHOMBUS_HORIZONTAL),
            create(UmbrellaPatterns.HALF_BOTTOM),
            create(UmbrellaPatterns.HALF_TOP),
            create(UmbrellaPatterns.HALF_LEFT),
            create(UmbrellaPatterns.HALF_RIGHT),
            create(UmbrellaPatterns.BORDER),
            create(UmbrellaPatterns.GRADIENT_BOTTOM),
            create(UmbrellaPatterns.GRADIENT_TOP),
            create(UmbrellaPatterns.GRADIENT_LEFT),
            create(UmbrellaPatterns.GRADIENT_RIGHT),
            create(UmbrellaPatterns.GLOBE),
            create(UmbrellaPatterns.CREEPER),
            create(UmbrellaPatterns.SKULL),
            create(UmbrellaPatterns.FLOWER),
            create(UmbrellaPatterns.MOJANG),
            create(UmbrellaPatterns.PIGLIN),
            create(UmbrellaPatterns.FLOW),
            create(UmbrellaPatterns.GUSTER),
            create(UmbrellaPatterns.FIELD_MASONED),
            create(UmbrellaPatterns.BORDURE_INDENTED),
        
            create(UmbrellaPatterns.ACCENT),
            create(UmbrellaPatterns.SQUARE_FULL),
            create(UmbrellaPatterns.SQUARE_HOLLOW),
            create(UmbrellaPatterns.CIRCLE_HOLLOW),
            create(UmbrellaPatterns.HALF_GRADIENT_BOTTOM),
            create(UmbrellaPatterns.HALF_GRADIENT_TOP),
            create(UmbrellaPatterns.HALF_GRADIENT_LEFT),
            create(UmbrellaPatterns.HALF_GRADIENT_RIGHT),
            create(UmbrellaPatterns.THIRD_BOTTOM),
            create(UmbrellaPatterns.THIRD_TOP),
            create(UmbrellaPatterns.THIRD_LEFT),
            create(UmbrellaPatterns.THIRD_RIGHT),
            create(UmbrellaPatterns.THIRD_VERTICAL),
            create(UmbrellaPatterns.THIRD_HORIZONTAL),
        
            create(UmbrellaPatterns.FLAG_AROMANTIC, false),
            create(UmbrellaPatterns.FLAG_ASEXUAL, false),
            create(UmbrellaPatterns.FLAG_BISEXUAL, false),
            create(UmbrellaPatterns.FLAG_GENDERFLUID, false),
            create(UmbrellaPatterns.FLAG_INTERSEX, false),
            create(UmbrellaPatterns.FLAG_LESBIAN, false),
            create(UmbrellaPatterns.FLAG_MLM, false),
            create(UmbrellaPatterns.FLAG_NONBINARY, false),
            create(UmbrellaPatterns.FLAG_PANSEXUAL, false),
            create(UmbrellaPatterns.FLAG_PRIDE, false),
            create(UmbrellaPatterns.FLAG_TRANSGENDER, false),
            create(UmbrellaPatterns.HALF_FLAG_LEFT_AROMANTIC, false),
            create(UmbrellaPatterns.HALF_FLAG_LEFT_ASEXUAL, false),
            create(UmbrellaPatterns.HALF_FLAG_LEFT_BISEXUAL, false),
            create(UmbrellaPatterns.HALF_FLAG_LEFT_GENDERFLUID, false),
            create(UmbrellaPatterns.HALF_FLAG_LEFT_INTERSEX, false),
            create(UmbrellaPatterns.HALF_FLAG_LEFT_LESBIAN, false),
            create(UmbrellaPatterns.HALF_FLAG_LEFT_MLM, false),
            create(UmbrellaPatterns.HALF_FLAG_LEFT_NONBINARY, false),
            create(UmbrellaPatterns.HALF_FLAG_LEFT_PANSEXUAL, false),
            create(UmbrellaPatterns.HALF_FLAG_LEFT_PRIDE, false),
            create(UmbrellaPatterns.HALF_FLAG_LEFT_TRANSGENDER, false),
            create(UmbrellaPatterns.HALF_FLAG_RIGHT_AROMANTIC, false),
            create(UmbrellaPatterns.HALF_FLAG_RIGHT_ASEXUAL, false),
            create(UmbrellaPatterns.HALF_FLAG_RIGHT_BISEXUAL, false),
            create(UmbrellaPatterns.HALF_FLAG_RIGHT_GENDERFLUID, false),
            create(UmbrellaPatterns.HALF_FLAG_RIGHT_INTERSEX, false),
            create(UmbrellaPatterns.HALF_FLAG_RIGHT_LESBIAN, false),
            create(UmbrellaPatterns.HALF_FLAG_RIGHT_MLM, false),
            create(UmbrellaPatterns.HALF_FLAG_RIGHT_NONBINARY, false),
            create(UmbrellaPatterns.HALF_FLAG_RIGHT_PANSEXUAL, false),
            create(UmbrellaPatterns.HALF_FLAG_RIGHT_PRIDE, false),
            create(UmbrellaPatterns.HALF_FLAG_RIGHT_TRANSGENDER, false)
    );

    @Override
    protected void configure(BiConsumer<Identifier, UmbrellaPattern> provider, RegistryWrapper.WrapperLookup lookup) {
        // assetId() is very questionable here, but it's fine probably
        for (UmbrellaPattern pattern : PATTERNS) {
            provider.accept(pattern.assetId(), pattern);
        }
    }

    public static void bootstrap(Registerable<UmbrellaPattern> registry) {
        // assetId() is very questionable here, but it's fine probably
        for (UmbrellaPattern pattern : PATTERNS) {
            registry.register(RegistryKey.of(UmbrellaPatterns.UMBRELLA_PATTERN_KEY, pattern.assetId()), pattern);
        }
    }
    
    private static UmbrellaPattern create(RegistryKey<UmbrellaPattern> key) {
        return create(key, true);
    }
    
    private static UmbrellaPattern create(RegistryKey<UmbrellaPattern> key, boolean dyeable) {
        Identifier id = key.getValue();
        return new UmbrellaPattern(id, id.toTranslationKey("umbrella_pattern"), dyeable);
    }

    @Override
    public String getName() {
        return "Umbrella Patterns";
    }
}
