package net.pneumono.umbrellas;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.data.DataOutput;
import net.minecraft.registry.RegistryBuilder;
import net.minecraft.registry.RegistryKeys;
import net.pneumono.umbrellas.datagen.*;
import net.pneumono.umbrellas.patterns.UmbrellaPattern;
import net.pneumono.umbrellas.registry.UmbrellaPatterns;

public class UmbrellasDataGenerator implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
        pack.addProvider((output, registriesFuture) -> new UmbrellasUmbrellaPatternProvider(
                output,
                registriesFuture,
                DataOutput.OutputType.DATA_PACK,
                "umbrellas/umbrella_pattern",
                UmbrellaPattern.CODEC
        ));
        pack.addProvider(UmbrellasItemTagProvider::new);
        pack.addProvider(UmbrellasBlockTagProvider::new);
        pack.addProvider(UmbrellasPatternTagProvider::new);
        pack.addProvider(UmbrellasRecipeProvider::new);
        pack.addProvider(UmbrellasEnchantmentProvider::new);
        pack.addProvider(UmbrellasAdvancementProvider::new);
        pack.addProvider(UmbrellasModelProvider::new);
        pack.addProvider(UmbrellasEnglishLangProvider::new);
    }

    @Override
    public void buildRegistry(RegistryBuilder registryBuilder) {
        registryBuilder.addRegistry(RegistryKeys.ENCHANTMENT, UmbrellasEnchantmentProvider::bootstrap);
        registryBuilder.addRegistry(UmbrellaPatterns.UMBRELLA_PATTERN_KEY, UmbrellasUmbrellaPatternProvider::bootstrap);
    }
}
