/*
 * Curious Curios — A Minecraft mod
 * Copyright (C) 2024  Cypheriel
 */

package mixin;

import dev.cypheriel.curious_curios.common.items.curios.StoneOfInertiaNull;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.ProtectionEnchantment;
import net.minecraft.world.level.Explosion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import top.theillusivec4.curios.api.CuriosApi;

@Mixin(Explosion.class)
public class ExplosionMixin {

    /**
     * @author ChromaNyan
     * This causes the player to always ignore an explosion with inertia null while specifically in creative.
     * It does this by injecting into the spectator check to return true if the stone is equipped. Survival is
     * unaffected by this mixin.
     */
    @Redirect(method = "explode", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;isSpectator()Z"))
    private boolean isSpectatorOrInertiaNull(Player instance) {
        if (CuriosApi.getCuriosHelper().findFirstCurio(instance, StoneOfInertiaNull.INSTANCE).isPresent()) {
            return true;
        }

        return instance.isSpectator();
    }

    /**
     * @author ChromaNyan
     * This is the survival counterpart to {@link ExplosionMixin#isSpectatorOrInertiaNull(Player)}. It returns 0 for the
     * explosion knockback if the Stone of Inertia Null is equipped, defaulting to the vanilla behaviour otherwise.
     */
    @Redirect(method = "explode", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/enchantment/ProtectionEnchantment;getExplosionKnockbackAfterDampener(Lnet/minecraft/world/entity/LivingEntity;D)D"))
    private double modifyExplosionKnockbackIfInertiaNull(LivingEntity pLivingEntity, double pDamage) {
        if (CuriosApi.getCuriosHelper().findFirstCurio(pLivingEntity, StoneOfInertiaNull.INSTANCE).isPresent()) {
            return 0;
        }

        return ProtectionEnchantment.getExplosionKnockbackAfterDampener(pLivingEntity, pDamage);
    }
}
