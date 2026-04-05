package dev.quozul.campfire.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import dev.quozul.campfire.SoothingCampfires;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.crafting.CampfireCookingRecipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.CampfireBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gamerules.GameRules;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(CampfireBlockEntity.class)
public class CampfireBlockEntityMixin {
    @Inject(method = "cookTick", at = @At("RETURN"))
    private static void addCampfireRegenerationEffect(ServerLevel level, BlockPos pos, BlockState state, CampfireBlockEntity entity, RecipeManager.CachedCheck<SingleRecipeInput, CampfireCookingRecipe> recipeCache, CallbackInfo ci, @Local(name = "changed") boolean changed) {
        if (changed) {
            AABB effectArea = getEffectArea(level, pos);
            List<Player> players = level.getEntitiesOfClass(Player.class, effectArea);

            for (Player player : players) {
                MobEffectInstance currentEffect = player.getEffect(MobEffects.REGENERATION);
                if (currentEffect != null && currentEffect.getAmplifier() > 0) {
                    continue;
                }

                if (currentEffect == null || currentEffect.getDuration() < 20) {
                    GameRules gameRules = level.getGameRules();
                    int duration = gameRules.get(SoothingCampfires.CAMPFIRE_REGENERATION_DURATION);
                    int amplifier = gameRules.get(SoothingCampfires.CAMPFIRE_REGENERATION_AMPLIFIER);
                    player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, duration, amplifier, true, true));
                }
            }
        }
    }

    @Unique
    private static AABB getEffectArea(ServerLevel level, BlockPos pos) {
        boolean isSignal = level.getBlockState(pos.below()).is(Blocks.HAY_BLOCK);
        GameRules gameRules = level.getGameRules();
        int radius = gameRules.get(isSignal ? SoothingCampfires.CAMPFIRE_REGENERATION_RADIUS_SIGNAL : SoothingCampfires.CAMPFIRE_REGENERATION_RADIUS);
        BoundingBox boundingBox = new BoundingBox(pos).inflatedBy(radius);
        return AABB.of(boundingBox);
    }
}
