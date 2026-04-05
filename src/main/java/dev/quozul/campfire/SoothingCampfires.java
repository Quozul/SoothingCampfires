package dev.quozul.campfire;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.gamerule.v1.GameRuleBuilder;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.gamerules.GameRule;
import net.minecraft.world.level.gamerules.GameRuleCategory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SoothingCampfires implements ModInitializer {
    public static final String MOD_ID = "soothing_campfires";

    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static final GameRule<Integer> CAMPFIRE_REGENERATION_DURATION = GameRuleBuilder.forInteger(100)
            .category(GameRuleCategory.MISC)
            .buildAndRegister(Identifier.fromNamespaceAndPath(MOD_ID, "duration"));

    public static final GameRule<Integer> CAMPFIRE_REGENERATION_RADIUS = GameRuleBuilder.forInteger(5)
            .category(GameRuleCategory.MISC)
            .buildAndRegister(Identifier.fromNamespaceAndPath(MOD_ID, "radius"));

    public static final GameRule<Integer> CAMPFIRE_REGENERATION_RADIUS_SIGNAL = GameRuleBuilder.forInteger(10)
            .category(GameRuleCategory.MISC)
            .buildAndRegister(Identifier.fromNamespaceAndPath(MOD_ID, "signal_radius"));

    public static final GameRule<Integer> CAMPFIRE_REGENERATION_AMPLIFIER = GameRuleBuilder.forInteger(0)
            .category(GameRuleCategory.MISC)
            .buildAndRegister(Identifier.fromNamespaceAndPath(MOD_ID, "amplifier"));

    @Override
    public void onInitialize() {
    }
}
