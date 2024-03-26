/*
 * Curious Curios — A Minecraft mod
 * Copyright (C) 2024  Cypheriel
 */

package dev.cypheriel.curious_curios.common.events

import dev.cypheriel.curious_curios.CuriousCurios.MOD_ID
import dev.cypheriel.curious_curios.common.items.curios.StoneOfGreaterInertia
import net.minecraftforge.event.entity.living.LivingEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod.EventBusSubscriber
import top.theillusivec4.curios.api.CuriosApi

@EventBusSubscriber(modid = MOD_ID, bus = EventBusSubscriber.Bus.FORGE)
object LivingJumpEvent {
    @SubscribeEvent
    fun onLivingJump(event: LivingEvent.LivingJumpEvent) {
        val entity = event.entity ?: return

        if (CuriosApi.getCuriosHelper().findFirstCurio(entity, StoneOfGreaterInertia).isPresent) {
            entity.deltaMovement = entity.deltaMovement.multiply(1.0, 1.2, 1.0)
        }
    }
}
