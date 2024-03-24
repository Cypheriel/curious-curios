/*
 * Curious Curios — A Minecraft mod
 * Copyright (C) 2024  Cypheriel
 */

package dev.cypheriel.curious_curios.common.events

import dev.cypheriel.curious_curios.CuriousCurios
import dev.cypheriel.curious_curios.common.items.curios.RingOfEnchantedEyes
import net.minecraft.world.effect.MobEffects
import net.minecraftforge.event.entity.living.MobEffectEvent
import net.minecraftforge.eventbus.api.Event
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod
import top.theillusivec4.curios.api.CuriosApi

@Mod.EventBusSubscriber(modid = CuriousCurios.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
object MobEffectApplicableEvent {
    @SubscribeEvent
    fun mobEffectApplicable(event: MobEffectEvent.Applicable) {
        val entity = event.entity ?: return
        val effect = event.effectInstance.effect
        val immunities = setOf(MobEffects.BLINDNESS, MobEffects.DARKNESS)

        if (
            immunities.contains(effect)
            && CuriosApi.getCuriosHelper().findFirstCurio(entity, RingOfEnchantedEyes).isPresent
        ) {
            event.result = Event.Result.DENY
        }
    }
}