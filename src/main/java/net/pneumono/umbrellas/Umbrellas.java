package net.pneumono.umbrellas;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.entry.EmptyEntry;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.village.TradeOffers;
import net.minecraft.village.VillagerProfession;
import net.pneumono.pneumonocore.config.BooleanConfiguration;
import net.pneumono.pneumonocore.config.ConfigEnv;
import net.pneumono.pneumonocore.config.Configs;
import net.pneumono.pneumonocore.config.EnumConfiguration;
import net.pneumono.umbrellas.content.UmbrellasRegistry;
import net.pneumono.umbrellas.util.GlidingAbilityType;
import net.pneumono.umbrellas.util.UmbrellaCauldronBehavior;
import net.pneumono.umbrellas.util.WindCatchingAbilityType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Umbrellas implements ModInitializer {
	public static final String MOD_ID = "umbrellas";
    public static final Logger LOGGER = LoggerFactory.getLogger("Umbrellas");
	public static final BooleanConfiguration GLIDING = Configs.register(new BooleanConfiguration(MOD_ID, "gliding", ConfigEnv.SERVER, true));
	public static final BooleanConfiguration WIND_CATCHING = Configs.register(new BooleanConfiguration(MOD_ID, "wind_catching", ConfigEnv.SERVER, true));
	public static final EnumConfiguration<GlidingAbilityType> SLOW_FALLING = Configs.register(new EnumConfiguration<>(MOD_ID, "slow_falling",ConfigEnv.SERVER, GlidingAbilityType.GLIDING_ONLY));
	public static final EnumConfiguration<WindCatchingAbilityType> SMOKE_BOOSTING = Configs.register(new EnumConfiguration<>(MOD_ID, "smoke_boosting", ConfigEnv.SERVER, WindCatchingAbilityType.WIND_CATCHING_ONLY));
	public static final BooleanConfiguration ENCHANTMENT_GLINT = Configs.register(new BooleanConfiguration(MOD_ID, "enchantment_glint", ConfigEnv.CLIENT, false));

	@Override
	public void onInitialize() {
		LOGGER.info("Initialising Umbrellas");
		Configs.reload(MOD_ID);

		UmbrellasRegistry.registerModContent();
		UmbrellaCauldronBehavior.registerCauldronBehavior();

		LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
			if (LootTables.BASTION_OTHER_CHEST.equals(id) && source.isBuiltin()) {
				LootPool.Builder pool = LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0f))
								.with(EmptyEntry.builder().weight(11))
								.with(ItemEntry.builder(UmbrellasRegistry.PIGLIN_UMBRELLA_PATTERN).weight(1));
				tableBuilder.pool(pool);
			}
		});

		TradeOffers.SellItemFactory factory = new TradeOffers.SellItemFactory(UmbrellasRegistry.GLOBE_UMBRELLA_PATTERN, 8, 1, 30);
		TradeOfferHelper.registerVillagerOffers(VillagerProfession.CARTOGRAPHER, 5, factories -> factories.add(factory));
	}
}