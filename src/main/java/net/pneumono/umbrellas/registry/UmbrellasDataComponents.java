package net.pneumono.umbrellas.registry;

import com.mojang.serialization.Codec;
import net.fabricmc.fabric.api.item.v1.ComponentTooltipAppenderRegistry;
import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.pneumono.umbrellas.Umbrellas;
import net.pneumono.umbrellas.content.item.component.ProvidesUmbrellaPatterns;
import net.pneumono.umbrellas.content.item.component.UmbrellaPatternsComponent;

public class UmbrellasDataComponents {
    public static final ComponentType<Long> LAST_DAMAGE = registerComponent("last_damage", ComponentType.<Long>builder().codec(Codec.LONG).build());
    public static final ComponentType<ProvidesUmbrellaPatterns> PROVIDES_UMBRELLA_PATTERNS = registerComponent(
            "provides_umbrella_patterns",
            ComponentType.<ProvidesUmbrellaPatterns>builder().codec(ProvidesUmbrellaPatterns.CODEC).build()
    );
    public static final ComponentType<UmbrellaPatternsComponent> UMBRELLA_PATTERNS = registerComponent(
            "umbrella_patterns",
            ComponentType.<UmbrellaPatternsComponent>builder().codec(UmbrellaPatternsComponent.CODEC).packetCodec(UmbrellaPatternsComponent.PACKET_CODEC).build()
    );

    private static <T> ComponentType<T> registerComponent(String name, ComponentType<T> componentType) {
        return Registry.register(Registries.DATA_COMPONENT_TYPE, Umbrellas.id(name), componentType);
    }

    public static void registerUmbrellasDataComponents() {
        ComponentTooltipAppenderRegistry.addFirst(UmbrellasDataComponents.PROVIDES_UMBRELLA_PATTERNS);
        ComponentTooltipAppenderRegistry.addFirst(UmbrellasDataComponents.UMBRELLA_PATTERNS);
    }
}
