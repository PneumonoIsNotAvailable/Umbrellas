package net.pneumono.umbrellas.registry;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.level.block.Block;
import net.pneumono.umbrellas.Umbrellas;
import net.pneumono.umbrellas.content.item.UmbrellaItem;
import net.pneumono.umbrellas.content.item.UmbrellaPatternItem;
import net.pneumono.umbrellas.content.item.component.ProvidesUmbrellaPatterns;
import net.pneumono.umbrellas.content.item.component.UmbrellaPatternsComponent;
import net.pneumono.umbrellas.util.VersionUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

//? if >=1.21.6
import net.minecraft.world.item.enchantment.EnchantmentHelper;

public class UmbrellasItems {
    public static final List<Item> PATTERNABLES = new ArrayList<>();
    private static final int MAX_UMBRELLA_DAMAGE = 600;

    public static final UmbrellaItem WHITE_UMBRELLA = registerPatternableUmbrella(DyeColor.WHITE);
    public static final UmbrellaItem ORANGE_UMBRELLA = registerPatternableUmbrella(DyeColor.ORANGE);
    public static final UmbrellaItem MAGENTA_UMBRELLA = registerPatternableUmbrella(DyeColor.MAGENTA);
    public static final UmbrellaItem LIGHT_BLUE_UMBRELLA = registerPatternableUmbrella(DyeColor.LIGHT_BLUE);
    public static final UmbrellaItem YELLOW_UMBRELLA = registerPatternableUmbrella(DyeColor.YELLOW);
    public static final UmbrellaItem LIME_UMBRELLA = registerPatternableUmbrella(DyeColor.LIME);
    public static final UmbrellaItem PINK_UMBRELLA = registerPatternableUmbrella(DyeColor.PINK);
    public static final UmbrellaItem GRAY_UMBRELLA = registerPatternableUmbrella(DyeColor.GRAY);
    public static final UmbrellaItem LIGHT_GRAY_UMBRELLA = registerPatternableUmbrella(DyeColor.LIGHT_GRAY);
    public static final UmbrellaItem CYAN_UMBRELLA = registerPatternableUmbrella(DyeColor.CYAN);
    public static final UmbrellaItem PURPLE_UMBRELLA = registerPatternableUmbrella(DyeColor.PURPLE);
    public static final UmbrellaItem BLUE_UMBRELLA = registerPatternableUmbrella(DyeColor.BLUE);
    public static final UmbrellaItem BROWN_UMBRELLA = registerPatternableUmbrella(DyeColor.BROWN);
    public static final UmbrellaItem GREEN_UMBRELLA = registerPatternableUmbrella(DyeColor.GREEN);
    public static final UmbrellaItem RED_UMBRELLA = registerPatternableUmbrella(DyeColor.RED);
    public static final UmbrellaItem BLACK_UMBRELLA = registerPatternableUmbrella(DyeColor.BLACK);

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

    public static final Item OAK_UMBRELLA_STAND = registerBlockItem(UmbrellasBlocks.OAK_UMBRELLA_STAND);
    public static final Item SPRUCE_UMBRELLA_STAND = registerBlockItem(UmbrellasBlocks.SPRUCE_UMBRELLA_STAND);
    public static final Item BIRCH_UMBRELLA_STAND = registerBlockItem(UmbrellasBlocks.BIRCH_UMBRELLA_STAND);
    public static final Item ACACIA_UMBRELLA_STAND = registerBlockItem(UmbrellasBlocks.ACACIA_UMBRELLA_STAND);
    public static final Item CHERRY_UMBRELLA_STAND = registerBlockItem(UmbrellasBlocks.CHERRY_UMBRELLA_STAND);
    public static final Item JUNGLE_UMBRELLA_STAND = registerBlockItem(UmbrellasBlocks.JUNGLE_UMBRELLA_STAND);
    public static final Item DARK_OAK_UMBRELLA_STAND = registerBlockItem(UmbrellasBlocks.DARK_OAK_UMBRELLA_STAND);
    //? if >=1.21.6
    public static final Item PALE_OAK_UMBRELLA_STAND = registerBlockItem(UmbrellasBlocks.PALE_OAK_UMBRELLA_STAND);
    public static final Item CRIMSON_UMBRELLA_STAND = registerBlockItem(UmbrellasBlocks.CRIMSON_UMBRELLA_STAND);
    public static final Item WARPED_UMBRELLA_STAND = registerBlockItem(UmbrellasBlocks.WARPED_UMBRELLA_STAND);
    public static final Item MANGROVE_UMBRELLA_STAND = registerBlockItem(UmbrellasBlocks.MANGROVE_UMBRELLA_STAND);
    public static final Item BAMBOO_UMBRELLA_STAND = registerBlockItem(UmbrellasBlocks.BAMBOO_UMBRELLA_STAND);

    public static final ResourceKey<CreativeModeTab> CREATIVE_MODE_TAB = ResourceKey.create(Registries.CREATIVE_MODE_TAB, Umbrellas.id(Umbrellas.MOD_ID));

    private static UmbrellaItem registerPatternableUmbrella(DyeColor color) {
        UmbrellaItem item = registerUmbrella(color.getName(), settings -> new UmbrellaItem(settings.component(
                UmbrellasDataComponents.UMBRELLA_PATTERNS,
                new UmbrellaPatternsComponent(color, List.of())
        )), Rarity.COMMON);
        PATTERNABLES.add(item);
        return item;
    }

    private static UmbrellaItem registerExtraUmbrella(String name) {
        return registerUmbrella(name, UmbrellaItem::new, Rarity.UNCOMMON);
    }

    private static <T extends UmbrellaItem> T registerUmbrella(String name, Function<Item.Properties, T> factory, Rarity rarity) {
        Item.Properties properties = createDefaultUmbrellaSettings();
        if (rarity != Rarity.COMMON) properties.rarity(rarity);

        return registerItem(name + "_umbrella", factory, properties);
    }

    private static Item registerUmbrellaPatternItem(String name, Rarity rarity) {
        return registerUmbrellaPatternItem(name, rarity, true);
    }

    private static Item registerUmbrellaPatternItem(String name, Rarity rarity, boolean requiresDye) {
        return registerItem(
                name + "_umbrella_pattern",
                UmbrellaPatternItem::new,
                new Item.Properties().stacksTo(1).rarity(rarity)
                        .component(UmbrellasDataComponents.PROVIDES_UMBRELLA_PATTERNS, new ProvidesUmbrellaPatterns(
                                UmbrellasTags.pattern("pattern_item/" + name),
                                requiresDye
                        ))
        );
    }

    @SuppressWarnings("deprecation")
    private static BlockItem registerBlockItem(Block block) {
        return registerItem(
                VersionUtil.identifier(block.builtInRegistryHolder().key()).getPath(),
                settings -> new BlockItem(block, settings),
                new Item.Properties()/*? if >=1.21.6 {*/.useBlockDescriptionPrefix()/*?}*/
        );
    }

    protected static <T extends Item> T registerItem(String name, Function<Item.Properties, T> factory, Item.Properties settings) {
        ResourceKey<Item> key = ResourceKey.create(Registries.ITEM, Umbrellas.id(name));
        return Registry.register(
                BuiltInRegistries.ITEM, key,
                factory.apply(settings/*? if >=1.21.6 {*/.setId(key)/*?}*/)
        );
    }

    private static Item.Properties createDefaultUmbrellaSettings() {
        return new Item.Properties()
                .stacksTo(1)
                .durability(MAX_UMBRELLA_DAMAGE)
                //? if >=1.21.6
                .enchantable(15).repairable(UmbrellasTags.REPAIRS_UMBRELLAS)
                ;
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
                //? if >=1.21.6
                PALE_OAK_UMBRELLA_STAND,
                CRIMSON_UMBRELLA_STAND,
                WARPED_UMBRELLA_STAND,
                MANGROVE_UMBRELLA_STAND,
                BAMBOO_UMBRELLA_STAND
        };

        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.TOOLS_AND_UTILITIES).register(content -> content.addAfter(Items.WARPED_FUNGUS_ON_A_STICK, umbrellas));
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.INGREDIENTS).register(content -> content.addAfter(Items.GUSTER_BANNER_PATTERN, patterns));
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.FUNCTIONAL_BLOCKS).register(content -> content.addBefore(Items.OAK_SIGN, stands));

        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, CREATIVE_MODE_TAB, FabricItemGroup.builder()
                .title(Component.translatable(VersionUtil.identifier(CREATIVE_MODE_TAB).toLanguageKey("itemGroup")))
                .icon(() -> new ItemStack(RED_UMBRELLA))
                .displayItems((displayContext, entries) -> {
                    for (Item item : umbrellas) {
                        entries.accept(item);
                    }
                    for (Item item : patterns) {
                        entries.accept(item);
                    }
                    for (Item item : stands) {
                        entries.accept(item);
                    }
                    displayContext.holders().lookup(Registries.ENCHANTMENT).ifPresent(registryWrapper -> {
                        entries.accept(
                                //? if >=1.21.6 {
                                EnchantmentHelper.createBook(
                                //?} else {
                                /*EnchantedBookItem.createForEnchantment(
                                *///?}
                                        new EnchantmentInstance(registryWrapper.getOrThrow(UmbrellasEnchantments.GLIDING), 3)
                                )
                        );
                        entries.accept(
                                //? if >=1.21.6 {
                                EnchantmentHelper.createBook(
                                //?} else {
                                /*EnchantedBookItem.createForEnchantment(
                                *///?}
                                        new EnchantmentInstance(registryWrapper.getOrThrow(UmbrellasEnchantments.BILLOWING), 1)
                                )
                        );
                    });
                }).build()
        );
    }
}
