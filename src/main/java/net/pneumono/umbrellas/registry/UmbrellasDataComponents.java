package net.pneumono.umbrellas.registry;

import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.pneumono.umbrellas.Umbrellas;
import net.pneumono.umbrellas.content.item.component.ProvidesUmbrellaPatterns;
import net.pneumono.umbrellas.content.item.component.UmbrellaPatternsComponent;

//? if >=1.21.6
import net.fabricmc.fabric.api.item.v1.ComponentTooltipAppenderRegistry;

public class UmbrellasDataComponents {
    public static final DataComponentType<Long> LAST_DAMAGE = registerComponent("last_damage", DataComponentType.<Long>builder().persistent(Codec.LONG).build());
    public static final DataComponentType<ProvidesUmbrellaPatterns> PROVIDES_UMBRELLA_PATTERNS = registerComponent(
            "provides_umbrella_patterns",
            DataComponentType.<ProvidesUmbrellaPatterns>builder().persistent(ProvidesUmbrellaPatterns.CODEC).build()
    );
    public static final DataComponentType<UmbrellaPatternsComponent> UMBRELLA_PATTERNS = registerComponent(
            "umbrella_patterns",
            DataComponentType.<UmbrellaPatternsComponent>builder().persistent(UmbrellaPatternsComponent.CODEC).networkSynchronized(UmbrellaPatternsComponent.STREAM_CODEC).build()
    );

    private static <T> DataComponentType<T> registerComponent(String name, DataComponentType<T> componentType) {
        return Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, Umbrellas.id(name), componentType);
    }

    public static void registerUmbrellasDataComponents() {
        //? if >=1.21.6 {
        ComponentTooltipAppenderRegistry.addFirst(UmbrellasDataComponents.PROVIDES_UMBRELLA_PATTERNS);
        ComponentTooltipAppenderRegistry.addFirst(UmbrellasDataComponents.UMBRELLA_PATTERNS);
        //?}
    }
}
