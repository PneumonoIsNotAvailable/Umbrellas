package net.pneumono.umbrellas.registry;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
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

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class UmbrellasItems {
    public static final List<Item> UMBRELLAS = new ArrayList<>();
    private static final int MAX_UMBRELLA_DAMAGE = 600;

    public static final PatternableUmbrellaItem WHITE_UMBRELLA = registerPatternableUmbrella(DyeColor.WHITE);
    public static final PatternableUmbrellaItem ORANGE_UMBRELLA = registerPatternableUmbrella(DyeColor.ORANGE);
    public static final PatternableUmbrellaItem MAGENTA_UMBRELLA = registerPatternableUmbrella(DyeColor.MAGENTA);
    public static final PatternableUmbrellaItem LIGHT_BLUE_UMBRELLA = registerPatternableUmbrella(DyeColor.LIGHT_BLUE);
    public static final PatternableUmbrellaItem YELLOW_UMBRELLA = registerPatternableUmbrella(DyeColor.YELLOW);
    public static final PatternableUmbrellaItem LIME_UMBRELLA = registerPatternableUmbrella(DyeColor.LIME);
    public static final PatternableUmbrellaItem PINK_UMBRELLA = registerPatternableUmbrella(DyeColor.PINK);
    public static final PatternableUmbrellaItem GRAY_UMBRELLA = registerPatternableUmbrella(DyeColor.GRAY);
    public static final PatternableUmbrellaItem LIGHT_GRAY_UMBRELLA = registerPatternableUmbrella(DyeColor.LIGHT_GRAY);
    public static final PatternableUmbrellaItem CYAN_UMBRELLA = registerPatternableUmbrella(DyeColor.CYAN);
    public static final PatternableUmbrellaItem PURPLE_UMBRELLA = registerPatternableUmbrella(DyeColor.PURPLE);
    public static final PatternableUmbrellaItem BLUE_UMBRELLA = registerPatternableUmbrella(DyeColor.BLUE);
    public static final PatternableUmbrellaItem BROWN_UMBRELLA = registerPatternableUmbrella(DyeColor.BROWN);
    public static final PatternableUmbrellaItem GREEN_UMBRELLA = registerPatternableUmbrella(DyeColor.GREEN);
    public static final PatternableUmbrellaItem RED_UMBRELLA = registerPatternableUmbrella(DyeColor.RED);
    public static final PatternableUmbrellaItem BLACK_UMBRELLA = registerPatternableUmbrella(DyeColor.BLACK);

    // Most of these extra umbrellas are custom designs from a previous version of the mod used on a private server.
    // I figured it would be better to put them in the public mod than simply let these go to waste, so here they are!
    public static final Item ANIMALS_UMBRELLA = registerExtraUmbrella("animals");
    public static final Item AZALEA_UMBRELLA = registerExtraUmbrella("azalea");
    public static final Item GALACTIC_UMBRELLA = registerExtraUmbrella("galactic");
    public static final Item GOTHIC_UMBRELLA = registerExtraUmbrella("gothic");
    public static final Item JELLYFISH_UMBRELLA = registerExtraUmbrella("jellyfish");

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

    public static final Item OAK_UMBRELLA_STAND = registerItem("oak_umbrella_stand", settings -> new BlockItem(UmbrellasBlocks.OAK_UMBRELLA_STAND, settings), new Item.Settings());
    public static final Item SPRUCE_UMBRELLA_STAND = registerItem("spruce_umbrella_stand", settings -> new BlockItem(UmbrellasBlocks.SPRUCE_UMBRELLA_STAND, settings), new Item.Settings());
    public static final Item BIRCH_UMBRELLA_STAND = registerItem("birch_umbrella_stand", settings -> new BlockItem(UmbrellasBlocks.BIRCH_UMBRELLA_STAND, settings), new Item.Settings());
    public static final Item ACACIA_UMBRELLA_STAND = registerItem("acacia_umbrella_stand", settings -> new BlockItem(UmbrellasBlocks.ACACIA_UMBRELLA_STAND, settings), new Item.Settings());
    public static final Item CHERRY_UMBRELLA_STAND = registerItem("cherry_umbrella_stand", settings -> new BlockItem(UmbrellasBlocks.CHERRY_UMBRELLA_STAND, settings), new Item.Settings());
    public static final Item JUNGLE_UMBRELLA_STAND = registerItem("jungle_umbrella_stand", settings -> new BlockItem(UmbrellasBlocks.JUNGLE_UMBRELLA_STAND, settings), new Item.Settings());
    public static final Item DARK_OAK_UMBRELLA_STAND = registerItem("dark_oak_umbrella_stand", settings -> new BlockItem(UmbrellasBlocks.DARK_OAK_UMBRELLA_STAND, settings), new Item.Settings());
    public static final Item PALE_OAK_UMBRELLA_STAND = registerItem("pale_oak_umbrella_stand", settings -> new BlockItem(UmbrellasBlocks.PALE_OAK_UMBRELLA_STAND, settings), new Item.Settings());
    public static final Item CRIMSON_UMBRELLA_STAND = registerItem("crimson_umbrella_stand", settings -> new BlockItem(UmbrellasBlocks.CRIMSON_UMBRELLA_STAND, settings), new Item.Settings());
    public static final Item WARPED_UMBRELLA_STAND = registerItem("warped_umbrella_stand", settings -> new BlockItem(UmbrellasBlocks.WARPED_UMBRELLA_STAND, settings), new Item.Settings());
    public static final Item MANGROVE_UMBRELLA_STAND = registerItem("mangrove_umbrella_stand", settings -> new BlockItem(UmbrellasBlocks.MANGROVE_UMBRELLA_STAND, settings), new Item.Settings());
    public static final Item BAMBOO_UMBRELLA_STAND = registerItem("bamboo_umbrella_stand", settings -> new BlockItem(UmbrellasBlocks.BAMBOO_UMBRELLA_STAND, settings), new Item.Settings());

    public static final RegistryKey<ItemGroup> ITEM_GROUP = RegistryKey.of(RegistryKeys.ITEM_GROUP, Umbrellas.id(Umbrellas.MOD_ID));

    private static PatternableUmbrellaItem registerPatternableUmbrella(DyeColor color) {
        return registerUmbrella(color.getId(), settings -> new PatternableUmbrellaItem(settings, color), Rarity.COMMON);
    }

    private static UmbrellaItem registerExtraUmbrella(String name) {
        return registerUmbrella(name, UmbrellaItem::new, Rarity.UNCOMMON);
    }

    private static <T extends UmbrellaItem> T registerUmbrella(String name, Function<Item.Settings, T> factory, Rarity rarity) {
        Item.Settings settings = createDefaultUmbrellaSettings();
        if (rarity != Rarity.COMMON) settings.rarity(rarity);

        return registerItem(name + "_umbrella", factory, settings);
    }

    private static Item registerUmbrellaPatternItem(String name, Rarity rarity) {
        return registerUmbrellaPatternItem(name, rarity, true);
    }

    private static Item registerUmbrellaPatternItem(String name, Rarity rarity, boolean requiresDye) {
        return registerItem(
                name + "_umbrella_pattern",
                Item::new,
                new Item.Settings().maxCount(1).rarity(rarity)
                        .component(UmbrellasDataComponents.PROVIDES_UMBRELLA_PATTERNS, new ProvidesUmbrellaPatterns(
                                UmbrellasTags.pattern("pattern_item/" + name),
                                requiresDye
                        ))
        );
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

    public static void registerUmbrellasItems() {
        Item[] umbrellas = new Item[]{
                WHITE_UMBRELLA,
                LIGHT_GRAY_UMBRELLA,
                GRAY_UMBRELLA,
                BLACK_UMBRELLA,
                BROWN_UMBRELLA,
                RED_UMBRELLA,
                ORANGE_UMBRELLA,
                YELLOW_UMBRELLA,
                LIME_UMBRELLA,
                GREEN_UMBRELLA,
                CYAN_UMBRELLA,
                LIGHT_BLUE_UMBRELLA,
                BLUE_UMBRELLA,
                PURPLE_UMBRELLA,
                MAGENTA_UMBRELLA,
                PINK_UMBRELLA,
                ANIMALS_UMBRELLA,
                AZALEA_UMBRELLA,
                GALACTIC_UMBRELLA,
                GOTHIC_UMBRELLA,
                JELLYFISH_UMBRELLA
        };
        Item[] patterns = new Item[]{
                FIELD_MASONED_UMBRELLA_PATTERN,
                BORDURE_INDENTED_UMBRELLA_PATTERN,
                FLOWER_UMBRELLA_PATTERN,
                CREEPER_UMBRELLA_PATTERN,
                SKULL_UMBRELLA_PATTERN,
                MOJANG_UMBRELLA_PATTERN,
                GLOBE_UMBRELLA_PATTERN,
                PIGLIN_UMBRELLA_PATTERN,
                FLOW_UMBRELLA_PATTERN,
                GUSTER_UMBRELLA_PATTERN,
                PRIDE_UMBRELLA_PATTERN
        };
        Item[] stands = new Item[]{
                OAK_UMBRELLA_STAND,
                BIRCH_UMBRELLA_STAND,
                SPRUCE_UMBRELLA_STAND,
                ACACIA_UMBRELLA_STAND,
                CHERRY_UMBRELLA_STAND,
                JUNGLE_UMBRELLA_STAND,
                DARK_OAK_UMBRELLA_STAND,
                PALE_OAK_UMBRELLA_STAND,
                CRIMSON_UMBRELLA_STAND,
                WARPED_UMBRELLA_STAND,
                MANGROVE_UMBRELLA_STAND,
                BAMBOO_UMBRELLA_STAND
        };

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(content -> content.addAfter(Items.WARPED_FUNGUS_ON_A_STICK, umbrellas));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(content -> content.addAfter(Items.GUSTER_BANNER_PATTERN, patterns));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register(content -> content.addBefore(Items.OAK_SIGN, stands));

        Registry.register(Registries.ITEM_GROUP, ITEM_GROUP, FabricItemGroup.builder()
                .displayName(Text.translatable(ITEM_GROUP.getValue().toTranslationKey("itemGroup")))
                .icon(() -> new ItemStack(RED_UMBRELLA))
                .entries((displayContext, entries) -> {
                    for (Item item : umbrellas) {
                        entries.add(item);
                    }
                    for (Item item : patterns) {
                        entries.add(item);
                    }
                    for (Item item : stands) {
                        entries.add(item);
                    }
                }).build()
        );
    }
}
