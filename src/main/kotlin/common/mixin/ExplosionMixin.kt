/*
 * Curious Curios — A Minecraft mod
 * Copyright (C) 2024  Cypheriel
 */

package dev.cypheriel.curious_curios.mixin

import dev.cypheriel.curious_curios.common.items.curios.StoneOfInertiaNull
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.enchantment.ProtectionEnchantment
import top.theillusivec4.curios.api.CuriosApi

/**
 * Causes the player to always ignore an explosion's knockback in creative mode while the [Stone of Inertia Null][StoneOfInertiaNull] is equipped.
 *
 * @author ChromaNyan
 * @see net.minecraft.world.level.Explosion.explode
 */
fun isSpectatorOrInertiaNull(player: Player): Boolean {
    if (CuriosApi.getCuriosHelper().findFirstCurio(player, StoneOfInertiaNull).isPresent) {
        return false
    }

    return player.isSpectator
}

/**
 * The survival counterpart to [isSpectatorOrInertiaNull].
 *
 * @return 0.0 if the player has inertia null equipped, otherwise the knockback after dampener.
 * @author ChromaNyan
 */
fun modifyExplosionKnockbackIfInertiaNull(
    livingEntity: LivingEntity,
    damage: Double,
): Double {
    if (CuriosApi.getCuriosHelper().findFirstCurio(livingEntity, StoneOfInertiaNull).isPresent) {
        return 0.0
    }

    return ProtectionEnchantment.getExplosionKnockbackAfterDampener(livingEntity, damage)
}
