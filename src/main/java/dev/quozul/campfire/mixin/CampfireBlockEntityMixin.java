package dev.quozul.campfire.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import dev.quozul.campfire.SoothingCampfires;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.CampfireBlockEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.recipe.CampfireCookingRecipe;
import net.minecraft.recipe.ServerRecipeManager;
import net.minecraft.recipe.input.SingleStackRecipeInput;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(CampfireBlockEntity.class)
public class CampfireBlockEntityMixin {
    @Inject(method = "litServerTick", at = @At("RETURN"))
    private static void addCampfireRegenerationEffect(ServerWorld world, BlockPos pos, BlockState state, CampfireBlockEntity blockEntity, ServerRecipeManager.MatchGetter<SingleStackRecipeInput, CampfireCookingRecipe> recipeMatchGetter, CallbackInfo ci, @Local boolean bl) {
        if (bl) {
            GameRules gameRules = world.getGameRules();
            int radius = gameRules.getInt(SoothingCampfires.CAMPFIRE_REGENERATION_RADIUS);
            Box effectArea = new Box(pos).expand(radius);
            List<PlayerEntity> players = world.getNonSpectatingEntities(PlayerEntity.class, effectArea);

            for (PlayerEntity player : players) {
                StatusEffectInstance currentEffect = player.getStatusEffect(StatusEffects.REGENERATION);
                if (currentEffect != null && currentEffect.getAmplifier() > 0) {
                    continue;
                }

                if (currentEffect == null || currentEffect.getDuration() < 20) {
                    int duration = gameRules.getInt(SoothingCampfires.CAMPFIRE_REGENERATION_DURATION);
                    int amplifier = gameRules.getInt(SoothingCampfires.CAMPFIRE_REGENERATION_AMPLIFIER);
                    player.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, duration, amplifier, true, true));
                }
            }
        }
    }
}
