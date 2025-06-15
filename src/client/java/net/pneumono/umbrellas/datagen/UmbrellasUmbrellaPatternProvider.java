package net.pneumono.umbrellas.datagen;

import com.mojang.serialization.Codec;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricCodecDataProvider;
import net.minecraft.data.DataOutput;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;
import net.pneumono.umbrellas.patterns.UmbrellaPattern;
import net.pneumono.umbrellas.registry.UmbrellaPatterns;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public class UmbrellasUmbrellaPatternProvider extends FabricCodecDataProvider<UmbrellaPattern> {
    public UmbrellasUmbrellaPatternProvider(
            FabricDataOutput dataOutput,
            CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture,
            DataOutput.OutputType outputType,
            String directoryName,
            Codec<UmbrellaPattern> codec
    ) {
        super(dataOutput, registriesFuture, outputType, directoryName, codec);
    }

    @Override
    protected void configure(BiConsumer<Identifier, UmbrellaPattern> provider, RegistryWrapper.WrapperLookup lookup) {
        generate(provider, UmbrellaPatterns.BASE);
        generate(provider, UmbrellaPatterns.SQUARE_BOTTOM_LEFT);
        generate(provider, UmbrellaPatterns.SQUARE_BOTTOM_RIGHT);
        generate(provider, UmbrellaPatterns.SQUARE_TOP_LEFT);
        generate(provider, UmbrellaPatterns.SQUARE_TOP_RIGHT);
        generate(provider, UmbrellaPatterns.STRIPE_BOTTOM);
        generate(provider, UmbrellaPatterns.STRIPE_TOP);
        generate(provider, UmbrellaPatterns.STRIPE_LEFT);
        generate(provider, UmbrellaPatterns.STRIPE_RIGHT);
        generate(provider, UmbrellaPatterns.STRIPE_VERTICAL);
        generate(provider, UmbrellaPatterns.STRIPE_HORIZONTAL);
        generate(provider, UmbrellaPatterns.STRIPE_DOWN_LEFT);
        generate(provider, UmbrellaPatterns.STRIPE_DOWN_RIGHT);
        generate(provider, UmbrellaPatterns.STRIPES_VERTICAL);
        generate(provider, UmbrellaPatterns.STRIPES_HORIZONTAL);
        generate(provider, UmbrellaPatterns.CROSS_CARDINAL);
        generate(provider, UmbrellaPatterns.CROSS_DIAGONAL);
        generate(provider, UmbrellaPatterns.TRIANGLE_BOTTOM);
        generate(provider, UmbrellaPatterns.TRIANGLE_TOP);
        generate(provider, UmbrellaPatterns.TRIANGLE_LEFT);
        generate(provider, UmbrellaPatterns.TRIANGLE_RIGHT);
        generate(provider, UmbrellaPatterns.TRIANGLES_BOTTOM);
        generate(provider, UmbrellaPatterns.TRIANGLES_TOP);
        generate(provider, UmbrellaPatterns.TRIANGLES_LEFT);
        generate(provider, UmbrellaPatterns.TRIANGLES_RIGHT);
        generate(provider, UmbrellaPatterns.DIAGONAL_UP_LEFT);
        generate(provider, UmbrellaPatterns.DIAGONAL_UP_RIGHT);
        generate(provider, UmbrellaPatterns.DIAGONAL_DOWN_LEFT);
        generate(provider, UmbrellaPatterns.DIAGONAL_DOWN_RIGHT);
        generate(provider, UmbrellaPatterns.CIRCLE_FULL);
        generate(provider, UmbrellaPatterns.RHOMBUS_VERTICAL);
        generate(provider, UmbrellaPatterns.RHOMBUS_HORIZONTAL);
        generate(provider, UmbrellaPatterns.HALF_BOTTOM);
        generate(provider, UmbrellaPatterns.HALF_TOP);
        generate(provider, UmbrellaPatterns.HALF_LEFT);
        generate(provider, UmbrellaPatterns.HALF_RIGHT);
        generate(provider, UmbrellaPatterns.BORDER);
        generate(provider, UmbrellaPatterns.GRADIENT_BOTTOM);
        generate(provider, UmbrellaPatterns.GRADIENT_TOP);
        generate(provider, UmbrellaPatterns.GRADIENT_LEFT);
        generate(provider, UmbrellaPatterns.GRADIENT_RIGHT);
        generate(provider, UmbrellaPatterns.GLOBE);
        generate(provider, UmbrellaPatterns.CREEPER);
        generate(provider, UmbrellaPatterns.SKULL);
        generate(provider, UmbrellaPatterns.FLOWER);
        generate(provider, UmbrellaPatterns.MOJANG);
        generate(provider, UmbrellaPatterns.PIGLIN);
        generate(provider, UmbrellaPatterns.FLOW);
        generate(provider, UmbrellaPatterns.GUSTER);
        generate(provider, UmbrellaPatterns.FIELD_MASONED);
        generate(provider, UmbrellaPatterns.BORDURE_INDENTED);

        generate(provider, UmbrellaPatterns.ACCENT);
        generate(provider, UmbrellaPatterns.SQUARE_FULL);
        generate(provider, UmbrellaPatterns.SQUARE_HOLLOW);
        generate(provider, UmbrellaPatterns.CIRCLE_HOLLOW);
        generate(provider, UmbrellaPatterns.HALF_GRADIENT_BOTTOM);
        generate(provider, UmbrellaPatterns.HALF_GRADIENT_TOP);
        generate(provider, UmbrellaPatterns.HALF_GRADIENT_LEFT);
        generate(provider, UmbrellaPatterns.HALF_GRADIENT_RIGHT);
        generate(provider, UmbrellaPatterns.THIRD_BOTTOM);
        generate(provider, UmbrellaPatterns.THIRD_TOP);
        generate(provider, UmbrellaPatterns.THIRD_LEFT);
        generate(provider, UmbrellaPatterns.THIRD_RIGHT);
        generate(provider, UmbrellaPatterns.THIRD_VERTICAL);
        generate(provider, UmbrellaPatterns.THIRD_HORIZONTAL);

        generate(provider, UmbrellaPatterns.FLAG_AROMANTIC, false);
        generate(provider, UmbrellaPatterns.FLAG_ASEXUAL, false);
        generate(provider, UmbrellaPatterns.FLAG_BISEXUAL, false);
        generate(provider, UmbrellaPatterns.FLAG_GENDERFLUID, false);
        generate(provider, UmbrellaPatterns.FLAG_INTERSEX, false);
        generate(provider, UmbrellaPatterns.FLAG_LESBIAN, false);
        generate(provider, UmbrellaPatterns.FLAG_MLM, false);
        generate(provider, UmbrellaPatterns.FLAG_NONBINARY, false);
        generate(provider, UmbrellaPatterns.FLAG_PANSEXUAL, false);
        generate(provider, UmbrellaPatterns.FLAG_PRIDE, false);
        generate(provider, UmbrellaPatterns.FLAG_TRANSGENDER, false);
        generate(provider, UmbrellaPatterns.HALF_FLAG_LEFT_AROMANTIC, false);
        generate(provider, UmbrellaPatterns.HALF_FLAG_LEFT_ASEXUAL, false);
        generate(provider, UmbrellaPatterns.HALF_FLAG_LEFT_BISEXUAL, false);
        generate(provider, UmbrellaPatterns.HALF_FLAG_LEFT_GENDERFLUID, false);
        generate(provider, UmbrellaPatterns.HALF_FLAG_LEFT_INTERSEX, false);
        generate(provider, UmbrellaPatterns.HALF_FLAG_LEFT_LESBIAN, false);
        generate(provider, UmbrellaPatterns.HALF_FLAG_LEFT_MLM, false);
        generate(provider, UmbrellaPatterns.HALF_FLAG_LEFT_NONBINARY, false);
        generate(provider, UmbrellaPatterns.HALF_FLAG_LEFT_PANSEXUAL, false);
        generate(provider, UmbrellaPatterns.HALF_FLAG_LEFT_PRIDE, false);
        generate(provider, UmbrellaPatterns.HALF_FLAG_LEFT_TRANSGENDER, false);
        generate(provider, UmbrellaPatterns.HALF_FLAG_RIGHT_AROMANTIC, false);
        generate(provider, UmbrellaPatterns.HALF_FLAG_RIGHT_ASEXUAL, false);
        generate(provider, UmbrellaPatterns.HALF_FLAG_RIGHT_BISEXUAL, false);
        generate(provider, UmbrellaPatterns.HALF_FLAG_RIGHT_GENDERFLUID, false);
        generate(provider, UmbrellaPatterns.HALF_FLAG_RIGHT_INTERSEX, false);
        generate(provider, UmbrellaPatterns.HALF_FLAG_RIGHT_LESBIAN, false);
        generate(provider, UmbrellaPatterns.HALF_FLAG_RIGHT_MLM, false);
        generate(provider, UmbrellaPatterns.HALF_FLAG_RIGHT_NONBINARY, false);
        generate(provider, UmbrellaPatterns.HALF_FLAG_RIGHT_PANSEXUAL, false);
        generate(provider, UmbrellaPatterns.HALF_FLAG_RIGHT_PRIDE, false);
        generate(provider, UmbrellaPatterns.HALF_FLAG_RIGHT_TRANSGENDER, false);
    }
    
    private void generate(BiConsumer<Identifier, UmbrellaPattern> provider, RegistryKey<UmbrellaPattern> key) {
        generate(provider, key, true);
    }
    
    private void generate(BiConsumer<Identifier, UmbrellaPattern> provider, RegistryKey<UmbrellaPattern> key, boolean dyeable) {
        Identifier id = key.getValue();
        provider.accept(id, new UmbrellaPattern(id, id.toTranslationKey("umbrella_pattern"), dyeable));
    }

    @Override
    public String getName() {
        return "Umbrella Patterns";
    }
}
