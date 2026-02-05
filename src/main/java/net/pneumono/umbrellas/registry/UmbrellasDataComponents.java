package net.pneumono.umbrellas.registry;

import com.mojang.serialization.Codec;
import net.pneumono.umbrellas.Umbrellas;
import net.pneumono.umbrellas.content.item.component.ProvidesUmbrellaPatterns;
import net.pneumono.umbrellas.content.item.component.UmbrellaPatternsComponent;
import net.pneumono.umbrellas.util.data.VersionedComponentType;

//? if >=1.21.6
import net.fabricmc.fabric.api.item.v1.ComponentTooltipAppenderRegistry;

//? if >=1.21 {
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
//?}

public class UmbrellasDataComponents {
    public static final VersionedComponentType<Long> LAST_DAMAGE = registerType(new VersionedComponentType<>(
            Codec.LONG, Umbrellas.id("last_damage")
    ));
    public static final VersionedComponentType<ProvidesUmbrellaPatterns> PROVIDES_UMBRELLA_PATTERNS = registerType(new VersionedComponentType<>(
            ProvidesUmbrellaPatterns.CODEC, Umbrellas.id("provides_umbrella_patterns")
    ));
    public static final VersionedComponentType<UmbrellaPatternsComponent> UMBRELLA_PATTERNS = registerType(new VersionedComponentType<>(
            UmbrellaPatternsComponent.CODEC, Umbrellas.id("umbrella_patterns")
            //? if >=1.21
            , UmbrellaPatternsComponent.STREAM_CODEC
    ));

    private static <T> VersionedComponentType<T> registerType(VersionedComponentType<T> type) {
        //? if >=1.21
        Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, type.getId(), type.getType());
        return type;
    }

    public static void registerUmbrellasDataComponents() {
        //? if >=1.21.6 {
        ComponentTooltipAppenderRegistry.addFirst(UmbrellasDataComponents.PROVIDES_UMBRELLA_PATTERNS.getType());
        ComponentTooltipAppenderRegistry.addFirst(UmbrellasDataComponents.UMBRELLA_PATTERNS.getType());
        //?}
    }
}
