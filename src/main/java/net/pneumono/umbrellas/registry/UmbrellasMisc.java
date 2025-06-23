package net.pneumono.umbrellas.registry;

import net.fabricmc.fabric.api.loot.v3.FabricLootTableBuilder;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.advancement.criterion.AbstractCriterion;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.entry.EmptyEntry;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.stat.StatFormatter;
import net.minecraft.stat.Stats;
import net.minecraft.util.Identifier;
import net.minecraft.village.TradeOffers;
import net.minecraft.village.VillagerProfession;
import net.pneumono.umbrellas.Umbrellas;
import net.pneumono.umbrellas.content.SmokeBoostCriterion;
import net.pneumono.umbrellas.content.TimeGlidingCriterion;

public class UmbrellasMisc {
    public static final SmokeBoostCriterion SMOKE_BOOST_CRITERION = registerCriterion("smoke_boost", new SmokeBoostCriterion());
    public static final TimeGlidingCriterion TIME_GLIDING_CRITERION = registerCriterion("time_gliding", new TimeGlidingCriterion());

    public static final Identifier CLEAN_UMBRELLA = registerStat("clean_umbrella", StatFormatter.DEFAULT);
    public static final Identifier TIME_UMBRELLA_GLIDING = registerStat("time_umbrella_gliding", StatFormatter.TIME);

    public static final SoundEvent UMBRELLA_STAND_INSERT_SOUND = registerSoundEvent("block.umbrella_stand.insert");
    public static final SoundEvent UMBRELLA_STAND_PICKUP_SOUND = registerSoundEvent("block.umbrella_stand.pickup");

    private static <T extends AbstractCriterion<?>> T registerCriterion(String name, T criterion) {
        return Registry.register(Registries.CRITERION, Umbrellas.id(name), criterion);
    }

    private static Identifier registerStat(String name, StatFormatter format) {
        Identifier id = Umbrellas.id(name);
        Registry.register(Registries.CUSTOM_STAT, name, id);
        Stats.CUSTOM.getOrCreateStat(id, format);
        return id;
    }

    private static SoundEvent registerSoundEvent(String name) {
        Identifier id = Umbrellas.id(name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
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
        LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) -> {
            if (LootTables.TRIAL_CHAMBERS_REWARD_OMINOUS_UNIQUE_CHEST.equals(key) && source.isBuiltin()) {
                LootPool.Builder pool = LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0f))
                        .with(EmptyEntry.builder().weight(11))
                        .with(ItemEntry.builder(UmbrellasItems.FLOW_UMBRELLA_PATTERN).weight(1));
                tableBuilder.pool(pool);
            }
        });
        LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) -> {
            if (LootTables.TRIAL_CHAMBERS_REWARD_UNIQUE_CHEST.equals(key) && source.isBuiltin()) {
                LootPool.Builder pool = LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0f))
                        .with(EmptyEntry.builder().weight(11))
                        .with(ItemEntry.builder(UmbrellasItems.GUSTER_UMBRELLA_PATTERN).weight(1));
                tableBuilder.pool(pool);
            }
        });

        TradeOffers.SellItemFactory factory = new TradeOffers.SellItemFactory(UmbrellasItems.GLOBE_UMBRELLA_PATTERN, 8, 1, 30);
        TradeOfferHelper.registerVillagerOffers(VillagerProfession.CARTOGRAPHER, 5, factories -> factories.add(factory));
    }
}
