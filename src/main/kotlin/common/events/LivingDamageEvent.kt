package dev.cypheriel.curious_curios.common.events

import dev.cypheriel.curious_curios.CuriousCurios
import dev.cypheriel.curious_curios.common.items.curios.WitherRing
import net.minecraft.world.damagesource.DamageSource
import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.effect.MobEffects
import net.minecraftforge.event.entity.living.LivingDamageEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod
import top.theillusivec4.curios.api.CuriosApi
import kotlin.random.Random

@Mod.EventBusSubscriber(modid = CuriousCurios.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
object LivingDamageEvent {

    private val random = Random

    @SubscribeEvent
    fun onLivingDamage(event: LivingDamageEvent) {
        doWitherRingEffects(event)
    }

    // we might have more things that hook into LivingDamageEvent later hence why this is in its own method
    private fun doWitherRingEffects(event: LivingDamageEvent) {
        // we can't directly get the entity that inflicted wither, so we use the last mob that attacked instead
        val attacker = event.entity.lastHurtByMob ?: return

        // TODO: configurability for chance and duration
        if (!CuriosApi.getCuriosHelper().findFirstCurio(attacker, WitherRing).isPresent) return

        // add effect to attacked entity if attacker has wither ring
        if (random.nextInt(5) == 0) {
            // duration is 100 ticks because that's how long it takes for lastHurtByMob to be cleared
            event.entity.addEffect(MobEffectInstance(MobEffects.WITHER, 5 * 20, 1), attacker)
        }

        if (event.source != DamageSource.WITHER) return

        attacker.heal(event.amount)
    }
}