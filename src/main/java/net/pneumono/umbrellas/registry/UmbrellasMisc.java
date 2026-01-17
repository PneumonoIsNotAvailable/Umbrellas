package net.pneumono.umbrellas.registry;

import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.stats.StatFormatter;
import net.minecraft.stats.Stats;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.EmptyLootItem;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.pneumono.umbrellas.Umbrellas;
import net.pneumono.umbrellas.content.SmokeBoostCriterion;
import net.pneumono.umbrellas.content.TimeGlidingCriterion;

//? if >=1.21.11 {
import net.minecraft.world.entity.npc.villager.VillagerProfession;
import net.minecraft.world.entity.npc.villager.VillagerTrades;
//?} else {
/*import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
*///?}

public class UmbrellasMisc {
    public static final SmokeBoostCriterion SMOKE_BOOST_CRITERION = registerCriterion("smoke_boost", new SmokeBoostCriterion());
    public static final TimeGlidingCriterion TIME_GLIDING_CRITERION = registerCriterion("time_gliding", new TimeGlidingCriterion());

    public static final Identifier CLEAN_UMBRELLA = registerStat("clean_umbrella", StatFormatter.DEFAULT);
    public static final Identifier TIME_UMBRELLA_GLIDING = registerStat("time_umbrella_gliding", StatFormatter.TIME);

    public static final SoundEvent UMBRELLA_STAND_INSERT_SOUND = registerSoundEvent("block.umbrella_stand.insert");
    public static final SoundEvent UMBRELLA_STAND_PICKUP_SOUND = registerSoundEvent("block.umbrella_stand.pickup");

    private static <T extends CriterionTrigger<?>> T registerCriterion(String name, T criterion) {
        return Registry.register(BuiltInRegistries.TRIGGER_TYPES, Umbrellas.id(name), criterion);
    }

    private static Identifier registerStat(String name, StatFormatter format) {
        Identifier id = Umbrellas.id(name);
        Registry.register(BuiltInRegistries.CUSTOM_STAT, name, id);
        Stats.CUSTOM.get(id, format);
        return id;
    }

    private static SoundEvent registerSoundEvent(String name) {
        Identifier id = Umbrellas.id(name);
        return Registry.register(BuiltInRegistries.SOUND_EVENT, id, SoundEvent.createVariableRangeEvent(id));
    }

    public static void registerUmbrellasMisc() {
        LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) -> {
            if (BuiltInLootTables.BASTION_OTHER.equals(key) && source.isBuiltin()) {
                LootPool.Builder pool = LootPool.lootPool().setRolls(ConstantValue.exactly(1.0f))
                        .with(EmptyLootItem.emptyItem().setWeight(11).build())
                        .with(LootItem.lootTableItem(UmbrellasItems.PIGLIN_UMBRELLA_PATTERN).setWeight(1).build());
                tableBuilder.pool(pool.build());
            }

            if (BuiltInLootTables.TRIAL_CHAMBERS_REWARD_OMINOUS_UNIQUE.equals(key) && source.isBuiltin()) {
                LootPool.Builder pool = LootPool.lootPool().setRolls(ConstantValue.exactly(1.0f))
                        .with(EmptyLootItem.emptyItem().setWeight(11).build())
                        .with(LootItem.lootTableItem(UmbrellasItems.FLOW_UMBRELLA_PATTERN).setWeight(1).build());
                tableBuilder.pool(pool.build());
            }

            if (BuiltInLootTables.TRIAL_CHAMBERS_REWARD_UNIQUE.equals(key) && source.isBuiltin()) {
                LootPool.Builder pool = LootPool.lootPool().setRolls(ConstantValue.exactly(1.0f))
                        .with(EmptyLootItem.emptyItem().setWeight(11).build())
                        .with(LootItem.lootTableItem(UmbrellasItems.GUSTER_UMBRELLA_PATTERN).setWeight(1).build());
                tableBuilder.pool(pool.build());
            }
        });

        VillagerTrades.EmeraldForItems factory = new VillagerTrades.EmeraldForItems(UmbrellasItems.GLOBE_UMBRELLA_PATTERN, 8, 1, 30);
        TradeOfferHelper.registerVillagerOffers(VillagerProfession.CARTOGRAPHER, 5, factories -> factories.add(factory));
    }
}
