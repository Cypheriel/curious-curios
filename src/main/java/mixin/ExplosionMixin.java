/*
 * Curious Curios — A Minecraft mod
 * Copyright (C) 2024  Cypheriel
 */

package mixin;

import dev.cypheriel.curious_curios.common.items.curios.StoneOfInertiaNull;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.theillusivec4.curios.api.CuriosApi;

@Mixin(Explosion.class)
public class ExplosionMixin {
    @Inject(at = @At("RETURN"), method = "getSeenPercent", cancellable = true)
    private static void getSeenPercent(Vec3 pExplosionVector, Entity pEntity, CallbackInfoReturnable<Float> cir) {
        if (!(pEntity instanceof LivingEntity livingEntity)) {
            return;
        }

        if (CuriosApi.getCuriosHelper().findFirstCurio(livingEntity, StoneOfInertiaNull.INSTANCE).isPresent()) {
            cir.setReturnValue(0.0F);
        }
    }
}
