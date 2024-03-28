/*
 * Curious Curios — A Minecraft mod
 * Copyright (C) 2024  Cypheriel
 */

package dev.cypheriel.curious_curios.common.events

import dev.cypheriel.curious_curios.CuriousCurios
import dev.cypheriel.curious_curios.common.items.curios.RingOfEnchantedEyes
import dev.cypheriel.curious_curios.common.items.curios.WitherRing
import net.minecraft.world.effect.MobEffects
import net.minecraftforge.event.entity.living.MobEffectEvent
import net.minecraftforge.eventbus.api.Event
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod.EventBusSubscriber
import top.theillusivec4.curios.api.CuriosApi

@EventBusSubscriber(modid = CuriousCurios.MOD_ID, bus = EventBusSubscriber.Bus.FORGE)
object MobEffectApplicableEvent {
    @SubscribeEvent
    fun mobEffectApplicable(event: MobEffectEvent.Applicable) {
        val entity = event.entity ?: return
        val effect = event.effectInstance.effect
        val immunities = setOf(MobEffects.BLINDNESS, MobEffects.DARKNESS)

        // TODO: use some other system for checking effect immunities, this will get ugly fast with 5+ immunity items

        if (
            immunities.contains(effect)
            && CuriosApi.getCuriosHelper().findFirstCurio(entity, RingOfEnchantedEyes).isPresent
        ) {
            event.result = Event.Result.DENY
        }

        if (effect == MobEffects.WITHER && CuriosApi.getCuriosHelper().findFirstCurio(entity, WitherRing).isPresent) {
            event.result = Event.Result.DENY
        }
    }
}