/*
 * Curious Curios — A Minecraft mod
 * Copyright (C) 2024  Cypheriel
 */

package dev.cypheriel.curious_curios.common.items.curios

import dev.cypheriel.curious_curios.common.creative_tabs.CuriousCuriosTab
import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.effect.MobEffects
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import top.theillusivec4.curios.api.SlotContext
import top.theillusivec4.curios.api.type.capability.ICurioItem

object RingOfEnchantedEyes : Item(Properties().stacksTo(1).tab(CuriousCuriosTab)), ICurioItem {
    override fun onEquip(slotContext: SlotContext?, prevStack: ItemStack?, stack: ItemStack?) {
        val entity = slotContext?.entity ?: return

        entity.removeEffect(MobEffects.BLINDNESS)
        entity.removeEffect(MobEffects.DARKNESS)
    }

    override fun curioTick(slotContext: SlotContext?, stack: ItemStack?) {
        val entity = slotContext?.entity ?: return

        if (entity.tickCount % 20 != 0) {
            return
        }

        slotContext.entity.addEffect(MobEffectInstance(MobEffects.NIGHT_VISION, 15 * 20 + 19))
    }

    override fun onUnequip(slotContext: SlotContext?, newStack: ItemStack?, stack: ItemStack?) {
        val entity = slotContext?.entity ?: return

        for (effect in entity.activeEffects) {
            if (effect.effect == MobEffects.NIGHT_VISION && effect.duration <= 16 * 20) {
                entity.forceAddEffect(MobEffectInstance(MobEffects.NIGHT_VISION, 5 * 20), null)
                return
            }
        }
    }
}