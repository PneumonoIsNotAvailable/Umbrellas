package net.pneumono.umbrellas;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.registry.RegistryBuilder;
import net.minecraft.registry.RegistryKeys;
import net.pneumono.umbrellas.datagen.*;

public class UmbrellasDataGenerator implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
        pack.addProvider(UmbrellasItemTagProvider::new);
        pack.addProvider(UmbrellasBlockTagProvider::new);
        pack.addProvider(UmbrellasRecipeProvider::new);
        pack.addProvider(UmbrellasEnchantmentProvider::new);
        pack.addProvider(UmbrellasAdvancementProvider::new);
        pack.addProvider(UmbrellasModelProvider::new);
        pack.addProvider(UmbrellasEnglishLangProvider::new);
    }

    @Override
    public void buildRegistry(RegistryBuilder registryBuilder) {
        registryBuilder.addRegistry(RegistryKeys.ENCHANTMENT, UmbrellasEnchantmentProvider::bootstrap);
    }
}
