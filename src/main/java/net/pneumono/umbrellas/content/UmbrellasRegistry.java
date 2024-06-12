package net.pneumono.umbrellas.content;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.stat.StatFormatter;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.pneumono.pneumonocore.migration.Migration;
import net.pneumono.umbrellas.Umbrellas;
import net.pneumono.umbrellas.patterns.PatternRegistry;
import net.pneumono.umbrellas.patterns.PrideUmbrellaPatternItem;
import net.pneumono.umbrellas.patterns.UmbrellaPatternItem;

import java.util.ArrayList;
import java.util.List;

public class UmbrellasRegistry {
    public static List<Item> UMBRELLAS = new ArrayList<>();
    public static List<Item> PATTERNABLE_UMBRELLAS = new ArrayList<>();
    public static List<Item> EXTRA_UMBRELLAS = new ArrayList<>();
    public static final List<Item> UMBRELLA_PATTERNS = new ArrayList<>();
    public static final int maxDamage = 600;

    public static final Item UMBRELLA = registerUmbrella("umbrella",
            new DyeableUmbrellaItem(new FabricItemSettings().maxCount(1).maxDamage(maxDamage)));

    // Most of these extra umbrellas are custom designs from a previous version of the mod used on a private server.
    // I figured it would be better to put them in the public mod than simply let these go to waste, so here they are!
    public static final Item ANIMALS_UMBRELLA = registerExtraUmbrella("animals");
    public static final Item AZALEA_UMBRELLA = registerExtraUmbrella("azalea");
    public static final Item GALACTIC_UMBRELLA = registerExtraUmbrella("galactic");
    public static final Item GOTHIC_UMBRELLA = registerExtraUmbrella("gothic");
    public static final Item JELLYFISH_UMBRELLA = registerExtraUmbrella("jellyfish");
    public static final Item GLOBE_UMBRELLA_PATTERN = registerUmbrellaPatternItem("globe", Rarity.COMMON);
    public static final Item CREEPER_UMBRELLA_PATTERN = registerUmbrellaPatternItem("creeper", Rarity.UNCOMMON);
    public static final Item SKULL_UMBRELLA_PATTERN = registerUmbrellaPatternItem("skull", Rarity.UNCOMMON);
    public static final Item FLOWER_UMBRELLA_PATTERN = registerUmbrellaPatternItem("flower", Rarity.COMMON);
    public static final Item MOJANG_UMBRELLA_PATTERN = registerUmbrellaPatternItem("mojang", Rarity.EPIC);
    public static final Item PIGLIN_UMBRELLA_PATTERN = registerUmbrellaPatternItem("piglin", Rarity.COMMON);
    public static final Item PRIDE_UMBRELLA_PATTERN = registerPridePatternItem();

    public static final Block UMBRELLA_STAND = registerUmbrellaStand();

    public static final BlockEntityType<UmbrellaStandBlockEntity> UMBRELLA_STAND_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(Umbrellas.MOD_ID, "umbrella_stand"),
            FabricBlockEntityTypeBuilder.create(UmbrellaStandBlockEntity::new, UMBRELLA_STAND).build()
    );

    public static Enchantment GLIDING = registerEnchantment(new UmbrellaEnchantment(Enchantment.Rarity.UNCOMMON, EquipmentSlot.MAINHAND), "gliding", Umbrellas.GLIDING.getValue());
    public static Enchantment WIND_CATCHING = registerEnchantment(new UmbrellaEnchantment(Enchantment.Rarity.COMMON, EquipmentSlot.MAINHAND), "wind_catching", Umbrellas.WIND_CATCHING.getValue());

    public static final TagKey<Item> TAG_UMBRELLAS = TagKey.of(RegistryKeys.ITEM, new Identifier(Umbrellas.MOD_ID, "umbrellas"));
    public static final TagKey<Block> TAG_BOOSTS_UMBRELLAS = TagKey.of(RegistryKeys.BLOCK, new Identifier(Umbrellas.MOD_ID, "boosts_umbrellas"));

    public static final Identifier CLEAN_UMBRELLA = registerStat(new Identifier(Umbrellas.MOD_ID, "clean_umbrella"));

    private static void registerDyedUmbrella(String name, DyeColor color) {
        Item item = new PatternableUmbrellaItem(new FabricItemSettings().maxCount(1).maxDamage(maxDamage), color);
        PATTERNABLE_UMBRELLAS.add(item);
        registerUmbrella(name, item);
    }

    private static Item registerExtraUmbrella(String name) {
        Item item = new UmbrellaItem(new FabricItemSettings().maxCount(1).maxDamage(maxDamage));
        EXTRA_UMBRELLAS.add(item);
        return registerUmbrella(name + "_umbrella", item);
    }

    private static Item registerUmbrella(String name, Item item) {
        UMBRELLAS.add(item);
        return Registry.register(Registries.ITEM, new Identifier(Umbrellas.MOD_ID, name), item);
    }

    private static Item registerUmbrellaPatternItem(String name, Rarity rarity) {
        Item item = new UmbrellaPatternItem(new FabricItemSettings().maxCount(1).rarity(rarity),
                TagKey.of(PatternRegistry.UMBRELLA_PATTERNS, new Identifier(Umbrellas.MOD_ID, "pattern_item/" + name)),
                "tooltip.umbrellas." + name
        );

        UMBRELLA_PATTERNS.add(item);
        return Registry.register(Registries.ITEM, new Identifier(Umbrellas.MOD_ID, name + "_umbrella_pattern"), item);
    }

    private static Item registerPridePatternItem() {
        Item item = new PrideUmbrellaPatternItem(new FabricItemSettings().maxCount(1).rarity(Rarity.EPIC),
                TagKey.of(PatternRegistry.UMBRELLA_PATTERNS, new Identifier(Umbrellas.MOD_ID, "pattern_item/pride")),
                "tooltip.umbrellas.pride"
        );

        UMBRELLA_PATTERNS.add(item);
        return Registry.register(Registries.ITEM, new Identifier(Umbrellas.MOD_ID, "pride_umbrella_pattern"), item);
    }

    private static Block registerUmbrellaStand() {
        Identifier id = new Identifier(Umbrellas.MOD_ID, "umbrella_stand");
        UmbrellaStandBlock block = new UmbrellaStandBlock(FabricBlockSettings.copyOf(Blocks.SPRUCE_FENCE).nonOpaque());

        Registry.register(Registries.ITEM, id, new BlockItem(block, new FabricItemSettings()));
        return Registry.register(Registries.BLOCK, id, block);
    }

    private static Enchantment registerEnchantment(Enchantment enchantment, String id, boolean enabled) {
        return enabled ? Registry.register(Registries.ENCHANTMENT, new Identifier(Umbrellas.MOD_ID, id), enchantment) : enchantment;
    }

    private static Identifier registerStat(Identifier name) {
        Registry.register(Registries.CUSTOM_STAT, name.getPath(), name);
        Stats.CUSTOM.getOrCreateStat(name, StatFormatter.DEFAULT);
        return name;
    }

    public static void registerModContent() {
        String[] removedUmbrellas = new String[]{"abrosexual", "agender", "aroace", "aromantic", "asexual", "bigender", "bisexual", "gay", "genderfluid", "intersex", "lesbian", "nonbinary", "omnisexual", "pangender", "pansexual", "polysexual", "pride", "transgender"};
        for (String identity : removedUmbrellas) {
            registerPrideUmbrellaMigration(identity);
        }

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register((content) -> content.addAfter(Items.FISHING_ROD, UMBRELLA));
        PatternRegistry.registerPatterns();

        for (DyeColor color : DyeColor.values()) {
            registerDyedUmbrella(color.getName() + "_umbrella", color);
        }

        Registry.register(Registries.ITEM_GROUP, new Identifier(Umbrellas.MOD_ID, Umbrellas.MOD_ID),
                FabricItemGroup.builder().displayName(Text.translatable("itemGroup." + Umbrellas.MOD_ID + "." + Umbrellas.MOD_ID)).icon(() -> new ItemStack(UmbrellasRegistry.UMBRELLA))
                        .entries(((displayContext, entries) -> {
                            entries.add(UMBRELLA_STAND);
                            entries.add(UMBRELLA);
                            for (ItemConvertible item : PATTERNABLE_UMBRELLAS) {
                                entries.add(item);
                            }
                            for (ItemConvertible item : EXTRA_UMBRELLAS) {
                                entries.add(item);
                            }
                            for (ItemConvertible item : UMBRELLA_PATTERNS) {
                                entries.add(item);
                            }
                        })).build()
        );
    }

    private static void registerPrideUmbrellaMigration(String identity) {
        Migration.registerItemMigration(new Identifier(Umbrellas.MOD_ID, "umbrella_" + identity), UMBRELLA);
    }
}
