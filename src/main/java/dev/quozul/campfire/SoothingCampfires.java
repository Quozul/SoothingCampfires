package dev.quozul.campfire;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.minecraft.world.GameRules;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SoothingCampfires implements ModInitializer {
	public static final String MOD_ID = "soothing-campfires";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static final GameRules.Key<GameRules.IntRule> CAMPFIRE_REGENERATION_DURATION =
			GameRuleRegistry.register("campfireRegenerationDuration", GameRules.Category.MISC, GameRuleFactory.createIntRule(100));

	public static final GameRules.Key<GameRules.IntRule> CAMPFIRE_REGENERATION_RADIUS =
			GameRuleRegistry.register("campfireRegenerationRadius", GameRules.Category.MISC, GameRuleFactory.createIntRule(5));

	public static final GameRules.Key<GameRules.IntRule> CAMPFIRE_REGENERATION_AMPLIFIER =
			GameRuleRegistry.register("campfireRegenerationAmplifier", GameRules.Category.MISC, GameRuleFactory.createIntRule(0));

	@Override
	public void onInitialize() {
	}
}
