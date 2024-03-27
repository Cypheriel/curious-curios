/*
 * Curious Curios — A Minecraft mod
 * Copyright (C) 2024  Cypheriel
 */

package mixin;

import dev.cypheriel.curious_curios.mixin.ExplosionMixinKt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Explosion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Explosion.class)
public class ExplosionMixinBinding {
    @Redirect(method = "explode", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;isSpectator()Z"))
    private boolean isSpectatorOrInertiaNull(Player instance) {
        return ExplosionMixinKt.isSpectatorOrInertiaNull(instance);
    }

    @Redirect(method = "explode", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/enchantment/ProtectionEnchantment;getExplosionKnockbackAfterDampener(Lnet/minecraft/world/entity/LivingEntity;D)D"))
    private double modifyExplosionKnockbackIfInertiaNull(LivingEntity pLivingEntity, double pDamage) {
        return ExplosionMixinKt.modifyExplosionKnockbackIfInertiaNull(pLivingEntity, pDamage);
    }
}
