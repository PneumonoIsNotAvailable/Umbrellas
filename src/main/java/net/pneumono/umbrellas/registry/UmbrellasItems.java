package net.pneumono.umbrellas.registry;

import com.mojang.serialization.Codec;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.component.ComponentType;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Rarity;
import net.pneumono.umbrellas.Umbrellas;
import net.pneumono.umbrellas.content.item.PatternableUmbrellaItem;
import net.pneumono.umbrellas.content.item.UmbrellaItem;
import net.pneumono.umbrellas.content.item.component.ProvidesUmbrellaPatterns;
import net.pneumono.umbrellas.content.item.component.UmbrellaPatternsComponent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class UmbrellasItems {
    public static final List<Item> UMBRELLAS = new ArrayList<>();
    public static final List<PatternableUmbrellaItem> PATTERNABLE_UMBRELLAS = new ArrayList<>();
    public static final List<Item> EXTRA_UMBRELLAS = new ArrayList<>();
    public static final List<Item> PATTERNS = new ArrayList<>();
    private static final int MAX_UMBRELLA_DAMAGE = 600;

    public static final Item WHITE_UMBRELLA = registerPatternableUmbrella(DyeColor.WHITE);
    public static final Item ORANGE_UMBRELLA = registerPatternableUmbrella(DyeColor.ORANGE);
    public static final Item MAGENTA_UMBRELLA = registerPatternableUmbrella(DyeColor.MAGENTA);
    public static final Item LIGHT_BLUE_UMBRELLA = registerPatternableUmbrella(DyeColor.LIGHT_BLUE);
    public static final Item YELLOW_UMBRELLA = registerPatternableUmbrella(DyeColor.YELLOW);
    public static final Item LIME_UMBRELLA = registerPatternableUmbrella(DyeColor.LIME);
    public static final Item PINK_UMBRELLA = registerPatternableUmbrella(DyeColor.PINK);
    public static final Item GRAY_UMBRELLA = registerPatternableUmbrella(DyeColor.GRAY);
    public static final Item LIGHT_GRAY_UMBRELLA = registerPatternableUmbrella(DyeColor.LIGHT_GRAY);
    public static final Item CYAN_UMBRELLA = registerPatternableUmbrella(DyeColor.CYAN);
    public static final Item PURPLE_UMBRELLA = registerPatternableUmbrella(DyeColor.PURPLE);
    public static final Item BLUE_UMBRELLA = registerPatternableUmbrella(DyeColor.BLUE);
    public static final Item BROWN_UMBRELLA = registerPatternableUmbrella(DyeColor.BROWN);
    public static final Item GREEN_UMBRELLA = registerPatternableUmbrella(DyeColor.GREEN);
    public static final Item RED_UMBRELLA = registerPatternableUmbrella(DyeColor.RED);
    public static final Item BLACK_UMBRELLA = registerPatternableUmbrella(DyeColor.BLACK);

    // Most of these extra umbrellas are custom designs from a previous version of the mod used on a private server.
    // I figured it would be better to put them in the public mod than simply let these go to waste, so here they are!
    public static final Item ANIMALS_UMBRELLA = registerExtraUmbrella("animals", Rarity.UNCOMMON);
    public static final Item AZALEA_UMBRELLA = registerExtraUmbrella("azalea", Rarity.UNCOMMON);
    public static final Item GALACTIC_UMBRELLA = registerExtraUmbrella("galactic", Rarity.UNCOMMON);
    public static final Item GOTHIC_UMBRELLA = registerExtraUmbrella("gothic", Rarity.UNCOMMON);
    public static final Item JELLYFISH_UMBRELLA = registerExtraUmbrella("jellyfish", Rarity.UNCOMMON);

    public static final Item FLOWER_UMBRELLA_PATTERN = registerUmbrellaPatternItem("flower", Rarity.COMMON);
    public static final Item CREEPER_UMBRELLA_PATTERN = registerUmbrellaPatternItem("creeper", Rarity.UNCOMMON);
    public static final Item SKULL_UMBRELLA_PATTERN = registerUmbrellaPatternItem("skull", Rarity.UNCOMMON);
    public static final Item MOJANG_UMBRELLA_PATTERN = registerUmbrellaPatternItem("mojang", Rarity.EPIC);
    public static final Item GLOBE_UMBRELLA_PATTERN = registerUmbrellaPatternItem("globe", Rarity.COMMON);
    public static final Item PIGLIN_UMBRELLA_PATTERN = registerUmbrellaPatternItem("piglin", Rarity.COMMON);
    public static final Item FLOW_UMBRELLA_PATTERN = registerUmbrellaPatternItem("flow", Rarity.RARE);
    public static final Item GUSTER_UMBRELLA_PATTERN = registerUmbrellaPatternItem("guster", Rarity.RARE);
    public static final Item FIELD_MASONED_UMBRELLA_PATTERN = registerUmbrellaPatternItem("field_masoned", Rarity.COMMON);
    public static final Item BORDURE_INDENTED_UMBRELLA_PATTERN = registerUmbrellaPatternItem("bordure_indented", Rarity.COMMON);
    public static final Item PRIDE_UMBRELLA_PATTERN = registerUmbrellaPatternItem("pride", Rarity.EPIC, false);

    public static final Item UMBRELLA_STAND = registerItem("umbrella_stand", settings -> new BlockItem(UmbrellasBlocks.UMBRELLA_STAND, settings), new Item.Settings());

    public static final ComponentType<Long> LAST_DAMAGE = registerComponent("last_damage", ComponentType.<Long>builder().codec(Codec.LONG).build());
    public static final ComponentType<ProvidesUmbrellaPatterns> PROVIDES_UMBRELLAS_PATTERNS = registerComponent(
            "provides_banner_patterns",
            ComponentType.<ProvidesUmbrellaPatterns>builder().codec(ProvidesUmbrellaPatterns.CODEC).build()
    );
    public static final ComponentType<UmbrellaPatternsComponent> UMBRELLA_PATTERNS = registerComponent(
            "umbrella_patterns",
            ComponentType.<UmbrellaPatternsComponent>builder().codec(UmbrellaPatternsComponent.CODEC).packetCodec(UmbrellaPatternsComponent.PACKET_CODEC).build()
    );

    public static final RegistryKey<ItemGroup> ITEM_GROUP = RegistryKey.of(RegistryKeys.ITEM_GROUP, Umbrellas.id(Umbrellas.MOD_ID));

    private static PatternableUmbrellaItem registerPatternableUmbrella(DyeColor color) {
        PatternableUmbrellaItem item = registerUmbrella(color.getId(), settings -> new PatternableUmbrellaItem(settings, color), Rarity.COMMON);
        PATTERNABLE_UMBRELLAS.add(item);
        return item;
    }

    private static UmbrellaItem registerExtraUmbrella(String name, Rarity rarity) {
        UmbrellaItem item = registerUmbrella(name, UmbrellaItem::new, rarity);
        EXTRA_UMBRELLAS.add(item);
        return item;
    }

    private static <T extends UmbrellaItem> T registerUmbrella(String name, Function<Item.Settings, T> factory, Rarity rarity) {
        Item.Settings settings = createDefaultUmbrellaSettings();
        if (rarity != Rarity.COMMON) settings.rarity(rarity);

        T item = registerItem(name + "_umbrella", factory, settings);
        UMBRELLAS.add(item);
        return item;
    }

    private static Item registerUmbrellaPatternItem(String name, Rarity rarity) {
        return registerUmbrellaPatternItem(name, rarity, true);
    }

    private static Item registerUmbrellaPatternItem(String name, Rarity rarity, boolean requiresDye) {
        Item item =  registerItem(
                name + "_umbrella_pattern",
                Item::new,
                new Item.Settings().maxCount(1).rarity(rarity)
                        .component(PROVIDES_UMBRELLAS_PATTERNS, new ProvidesUmbrellaPatterns(
                                UmbrellasTags.pattern("pattern_item/" + name),
                                requiresDye
                        ))
        );
        PATTERNS.add(item);
        return item;
    }

    protected static <T extends Item> T registerItem(String name, Function<Item.Settings, T> factory, Item.Settings settings) {
        RegistryKey<Item> key = RegistryKey.of(RegistryKeys.ITEM, Umbrellas.id(name));
        return Registry.register(Registries.ITEM, key, factory.apply(settings.registryKey(key)));
    }

    private static Item.Settings createDefaultUmbrellaSettings() {
        return new Item.Settings()
                .maxCount(1)
                .maxDamage(MAX_UMBRELLA_DAMAGE)
                .enchantable(15)
                .repairable(UmbrellasTags.REPAIRS_UMBRELLAS);
    }

    private static <T> ComponentType<T> registerComponent(String name, ComponentType<T> componentType) {
        return Registry.register(Registries.DATA_COMPONENT_TYPE, Umbrellas.id(name), componentType);
    }

    public static void registerUmbrellasItems() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(content -> content.addAfter(Items.WARPED_FUNGUS_ON_A_STICK, UMBRELLAS.toArray(Item[]::new)));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(content -> content.addAfter(Items.GUSTER_BANNER_PATTERN, PATTERNS.toArray(Item[]::new)));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register(content -> content.add(UMBRELLA_STAND));

        Registry.register(Registries.ITEM_GROUP, ITEM_GROUP, FabricItemGroup.builder()
                .displayName(Text.translatable(ITEM_GROUP.getValue().toTranslationKey("itemGroup")))
                .icon(() -> new ItemStack(RED_UMBRELLA))
                .entries((displayContext, entries) -> {
                    entries.add(UMBRELLA_STAND);
                    for (Item item : UMBRELLAS) {
                        entries.add(item);
                    }
                    for (Item item : PATTERNS) {
                        entries.add(item);
                    }
                }).build()
        );
    }
}
