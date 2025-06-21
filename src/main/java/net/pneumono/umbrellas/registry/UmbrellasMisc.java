package net.pneumono.umbrellas.registry;

import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.entry.EmptyEntry;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.stat.StatFormatter;
import net.minecraft.stat.Stats;
import net.minecraft.util.Identifier;
import net.minecraft.village.TradeOffers;
import net.minecraft.village.VillagerProfession;
import net.pneumono.umbrellas.Umbrellas;

public class UmbrellasMisc {
    public static final Identifier CLEAN_UMBRELLA = registerStat(Identifier.of(Umbrellas.MOD_ID, "clean_umbrella"));

    private static Identifier registerStat(Identifier name) {
        Registry.register(Registries.CUSTOM_STAT, name.getPath(), name);
        Stats.CUSTOM.getOrCreateStat(name, StatFormatter.DEFAULT);
        return name;
    }

    public static void registerUmbrellasMisc() {
        LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) -> {
            if (LootTables.BASTION_OTHER_CHEST.equals(key) && source.isBuiltin()) {
                LootPool.Builder pool = LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0f))
                        .with(EmptyEntry.builder().weight(11))
                        .with(ItemEntry.builder(UmbrellasItems.PIGLIN_UMBRELLA_PATTERN).weight(1));
                tableBuilder.pool(pool);
            }
        });

        TradeOffers.SellItemFactory factory = new TradeOffers.SellItemFactory(UmbrellasItems.GLOBE_UMBRELLA_PATTERN, 8, 1, 30);
        TradeOfferHelper.registerVillagerOffers(VillagerProfession.CARTOGRAPHER, 5, factories -> factories.add(factory));
    }
}
