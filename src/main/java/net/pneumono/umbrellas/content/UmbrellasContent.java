package net.pneumono.umbrellas.content;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
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
import net.pneumono.umbrellas.Umbrellas;

import java.util.ArrayList;
import java.util.List;

public class UmbrellasContent {
    public static List<Item> UMBRELLAS = new ArrayList<>();
    public static List<Item> WASHABLE_UMBRELLAS = new ArrayList<>();
    public static List<Item> PRIDE_UMBRELLAS = new ArrayList<>();

    public static final Item UMBRELLA = registerUmbrella("umbrella",
            new DyeableUmbrellaItem(new FabricItemSettings().maxCount(1)));
    public static final Item UMBRELLA_ABROSEXUAL = registerPrideUmbrella("umbrella_abrosexual",
            new PrideUmbrellaItem(new FabricItemSettings().maxCount(1).rarity(Rarity.RARE), "abrosexual",
                    new DyeColor[]{DyeColor.LIME, DyeColor.WHITE, DyeColor.RED},
                    0x75ca91, 0xb3e4c7, 0xffffff, 0xe695b5, 0xd9446c
            ));
    public static final Item UMBRELLA_AGENDER = registerPrideUmbrella("umbrella_agender",
            new PrideUmbrellaItem(new FabricItemSettings().maxCount(1).rarity(Rarity.RARE), "agender",
                    new DyeColor[]{DyeColor.BLACK, DyeColor.LIGHT_GRAY, DyeColor.WHITE, DyeColor.LIME},
                    0x2c2c2c, 0xbcc4c7, 0xffffff, 0xb7f684, 0xffffff, 0xbcc4c7, 0x2c2c2c
            ));
    public static final Item UMBRELLA_AROACE = registerPrideUmbrella("umbrella_aroace",
            new PrideUmbrellaItem(new FabricItemSettings().maxCount(1).rarity(Rarity.RARE), "aroace",
                    new DyeColor[]{DyeColor.ORANGE, DyeColor.WHITE, DyeColor.LIGHT_BLUE},
                    0xe38d00, 0xe7c601, 0xffffff, 0x5faad7, 0x1f3554
            ));
    public static final Item UMBRELLA_AROMANTIC = registerPrideUmbrella("umbrella_aromantic",
            new PrideUmbrellaItem(new FabricItemSettings().maxCount(1).rarity(Rarity.RARE), "aromantic",
                    new DyeColor[]{DyeColor.GREEN, DyeColor.WHITE, DyeColor.LIGHT_GRAY, DyeColor.BLACK},
                    0x3ba740, 0xa8d47a, 0xffffff, 0xababab, 0x2c2c2c
            ));
    public static final Item UMBRELLA_ASEXUAL = registerPrideUmbrella("umbrella_asexual",
            new PrideUmbrellaItem(new FabricItemSettings().maxCount(1).rarity(Rarity.RARE), "asexual",
                    new DyeColor[]{DyeColor.PURPLE, DyeColor.WHITE, DyeColor.LIGHT_GRAY, DyeColor.BLACK},
                    0x2c2c2c, 0xa4a4a4, 0xffffff, 0x810081
            ));
    public static final Item UMBRELLA_BIGENDER = registerPrideUmbrella("umbrella_bigender",
            new PrideUmbrellaItem(new FabricItemSettings().maxCount(1).rarity(Rarity.RARE), "bigender",
                    new DyeColor[]{DyeColor.PINK, DyeColor.WHITE, DyeColor.LIGHT_BLUE},
                    0xc479a2, 0xeda5cd, 0xd6c7e8, 0xffffff, 0xd6c7e8, 0x9ac7e8, 0x6d82d1
            ));
    public static final Item UMBRELLA_BISEXUAL = registerPrideUmbrella("umbrella_bisexual",
            new PrideUmbrellaItem(new FabricItemSettings().maxCount(1).rarity(Rarity.RARE), "bisexual",
                    new DyeColor[]{DyeColor.MAGENTA, DyeColor.PURPLE, DyeColor.BLUE},
                    0xd60270, 0xd60270, 0x9b4f96, 0x0038a8, 0x0038a8
            ));
    public static final Item UMBRELLA_GAY = registerPrideUmbrella("umbrella_gay",
            new PrideUmbrellaItem(new FabricItemSettings().maxCount(1).rarity(Rarity.RARE), "gay",
                    new DyeColor[]{DyeColor.CYAN, DyeColor.WHITE, DyeColor.BLUE},
                    0x078d70, 0x26ceaa, 0x98e8c1, 0xffffff, 0x7bade2, 0x5049cc, 0x3d1a78
            ));
    public static final Item UMBRELLA_GENDERFLUID = registerPrideUmbrella("umbrella_genderfluid",
            new PrideUmbrellaItem(new FabricItemSettings().maxCount(1).rarity(Rarity.RARE), "genderfluid",
                    new DyeColor[]{DyeColor.PINK, DyeColor.WHITE, DyeColor.MAGENTA, DyeColor.BLACK, DyeColor.BLUE},
                    0xff76a4, 0xffffff, 0xc011d7, 0x2c2c2c, 0x2f3cbe
            ));
    @SuppressWarnings("unused")
    public static final Item UMBRELLA_INTERSEX = registerPrideUmbrella("umbrella_intersex",
            new PrideUmbrellaItem(new FabricItemSettings().maxCount(1).rarity(Rarity.RARE), "intersex",
                    new DyeColor[]{DyeColor.YELLOW, DyeColor.PURPLE}
                    // No colours, since the Intersex flag isn't a striped flag, and therefore doesn't work with the stripe generator thingy
            ));
    public static final Item UMBRELLA_LESBIAN = registerPrideUmbrella("umbrella_lesbian",
            new PrideUmbrellaItem(new FabricItemSettings().maxCount(1).rarity(Rarity.RARE), "lesbian",
                    new DyeColor[]{DyeColor.ORANGE, DyeColor.WHITE, DyeColor.MAGENTA},
                    0xd52d00, 0xef7627, 0xff9a56, 0xffffff, 0xd162a4, 0xb55690, 0xa30262
            ));
    public static final Item UMBRELLA_NONBINARY = registerPrideUmbrella("umbrella_nonbinary",
            new PrideUmbrellaItem(new FabricItemSettings().maxCount(1).rarity(Rarity.RARE), "nonbinary",
                    new DyeColor[]{DyeColor.YELLOW, DyeColor.WHITE, DyeColor.PURPLE, DyeColor.BLACK},
                    0xfcf434, 0xffffff, 0x9c59d1, 0x2c2c2c
            ));
    public static final Item UMBRELLA_OMNISEXUAL = registerPrideUmbrella("umbrella_omnisexual",
            new PrideUmbrellaItem(new FabricItemSettings().maxCount(1).rarity(Rarity.RARE), "omnisexual",
                    new DyeColor[]{DyeColor.PINK, DyeColor.BLACK, DyeColor.BLUE},
                    0xfe9ace, 0xff53bf, 0x200044, 0x6760fe, 0x8ea6ff
            ));
    public static final Item UMBRELLA_PANGENDER = registerPrideUmbrella("umbrella_pangender",
            new PrideUmbrellaItem(new FabricItemSettings().maxCount(1).rarity(Rarity.RARE), "pangender",
                    new DyeColor[]{DyeColor.YELLOW, DyeColor.PINK, DyeColor.WHITE},
                    0xfff798, 0xffddcd, 0xffebfb, 0xffffff, 0xffebfb, 0xffddcd, 0xfff798
            ));
    public static final Item UMBRELLA_PANSEXUAL = registerPrideUmbrella("umbrella_pansexual",
            new PrideUmbrellaItem(new FabricItemSettings().maxCount(1).rarity(Rarity.RARE), "pansexual",
                    new DyeColor[]{DyeColor.MAGENTA, DyeColor.YELLOW, DyeColor.LIGHT_BLUE},
                    0xff218c, 0xffd800, 0x21b1ff
            ));
    public static final Item UMBRELLA_POLYSEXUAL = registerPrideUmbrella("umbrella_polysexual",
            new PrideUmbrellaItem(new FabricItemSettings().maxCount(1).rarity(Rarity.RARE), "polysexual",
                    new DyeColor[]{DyeColor.MAGENTA, DyeColor.GREEN, DyeColor.MAGENTA},
                    0xf714ba, 0x01d66a, 0x1594f6
            ));
    public static final Item UMBRELLA_PRIDE = registerPrideUmbrella("umbrella_pride",
            new PrideUmbrellaItem(new FabricItemSettings().maxCount(1).rarity(Rarity.RARE), "pride",
                    new DyeColor[]{DyeColor.RED, DyeColor.ORANGE, DyeColor.YELLOW, DyeColor.GREEN, DyeColor.BLUE, DyeColor.PURPLE},
                    0xe40303, 0xff8c00, 0xffed00, 0x008026, 0x24408e, 0x732982
            ));
    public static final Item UMBRELLA_TRANSGENDER = registerPrideUmbrella("umbrella_transgender",
            new PrideUmbrellaItem(new FabricItemSettings().maxCount(1).rarity(Rarity.RARE), "transgender",
                    new DyeColor[]{DyeColor.LIGHT_BLUE, DyeColor.PINK, DyeColor.WHITE},
                    0x5bcefa, 0xf5a9b8, 0xffffff, 0xf5a9b8, 0x5bcefa
            ));

    public static Enchantment GLIDING = registerGliding(new GlidingEnchantment(Enchantment.Rarity.COMMON, EquipmentSlot.MAINHAND));

    public static final TagKey<Item> TAG_UMBRELLAS = TagKey.of(RegistryKeys.ITEM, new Identifier(Umbrellas.MOD_ID, "umbrellas"));
    public static final TagKey<Item> TAG_WASHABLE_UMBRELLAS = TagKey.of(RegistryKeys.ITEM, new Identifier(Umbrellas.MOD_ID, "washable_umbrellas"));
    public static final TagKey<Item> TAG_PRIDE_UMBRELLAS = TagKey.of(RegistryKeys.ITEM, new Identifier(Umbrellas.MOD_ID, "pride_umbrellas"));

    public static final Identifier CLEAN_UMBRELLA = registerStat(new Identifier(Umbrellas.MOD_ID, "clean_umbrella"), StatFormatter.DEFAULT);

    private static Item registerPrideUmbrella(String name, Item item) {
        PRIDE_UMBRELLAS.add(item);
        return registerWashableUmbrella(name, item);
    }

    private static Item registerWashableUmbrella(String name, Item item) {
        WASHABLE_UMBRELLAS.add(item);
        return registerUmbrella(name, item);
    }

    private static Item registerUmbrella(String name, Item item) {
        UMBRELLAS.add(item);
        return Registry.register(Registries.ITEM, new Identifier(Umbrellas.MOD_ID, name), item);
    }

    private static Enchantment registerGliding(Enchantment enchantment) {
        return Umbrellas.GLIDING.getValue() ? Registry.register(Registries.ENCHANTMENT, new Identifier(Umbrellas.MOD_ID, "gliding"), enchantment) : enchantment;
    }

    public static Identifier registerStat(Identifier name, StatFormatter formatter) {
        Registry.register(Registries.CUSTOM_STAT, name.getPath(), name);
        Stats.CUSTOM.getOrCreateStat(name, formatter);
        return name;
    }

    public static void registerModContent() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register((content) -> content.addAfter(Items.FISHING_ROD, UMBRELLA));

        Registry.register(Registries.ITEM_GROUP, new Identifier(Umbrellas.MOD_ID, Umbrellas.MOD_ID),
                FabricItemGroup.builder().displayName(Text.translatable("itemGroup." + Umbrellas.MOD_ID + "." + Umbrellas.MOD_ID)).icon(() -> new ItemStack(UmbrellasContent.UMBRELLA))
                        .entries(((displayContext, entries) -> {
                            for (ItemConvertible item : UMBRELLAS) {
                                entries.add(item);
                            }
                            if (UmbrellasContent.UMBRELLA instanceof DyeableUmbrellaItem dyeableUmbrella) {
                                // Vanilla dye colors. For some reason DyeColor uses some "colorComponents" stuff far beyond my comprehension, so I figured this is just easier
                                int[] colors = new int[]{16383998, 16351261, 13061821, 3847130, 16701501, 8439583, 15961002, 4673362, 10329495, 1481884, 8991416, 3949738, 8606770, 6192150, 11546150, 1908001};
                                for (int color : colors) {
                                    ItemStack stack = UmbrellasContent.UMBRELLA.getDefaultStack();
                                    dyeableUmbrella.setColor(stack, color);
                                    entries.add(stack);
                                }
                            }
                        })).build()
        );
    }
}
