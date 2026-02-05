package net.pneumono.umbrellas;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.pneumono.umbrellas.datagen.*;
import net.pneumono.umbrellas.content.UmbrellaPattern;
import net.pneumono.umbrellas.registry.UmbrellaPatterns;

public class UmbrellasDataGenerator implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
        pack.addProvider((output, registriesFuture) -> new UmbrellasPatternProvider(
                output,
                registriesFuture,
                PackOutput.Target.DATA_PACK,
                "umbrellas/umbrella_pattern",
                UmbrellaPattern.CODEC
        ));
        pack.addProvider(UmbrellasItemTagProvider::new);
        pack.addProvider(UmbrellasBlockTagProvider::new);
        pack.addProvider(UmbrellasPatternTagProvider::new);
        pack.addProvider(UmbrellasRecipeProvider::new);
        //? if >=1.21
        pack.addProvider(UmbrellasEnchantmentProvider::new);
        pack.addProvider(UmbrellasAdvancementProvider::new);
        pack.addProvider(UmbrellasModelProvider::new);
        pack.addProvider(UmbrellasEnglishLangProvider::new);
        pack.addProvider(UmbrellasBlockLootTableProvider::new);
    }

    @Override
    public void buildRegistry(RegistrySetBuilder registryBuilder) {
        //? if >=1.21
        registryBuilder.add(Registries.ENCHANTMENT, UmbrellasEnchantmentProvider::bootstrap);
        registryBuilder.add(UmbrellaPatterns.UMBRELLA_PATTERN_KEY, UmbrellasPatternProvider::bootstrap);
    }
}
