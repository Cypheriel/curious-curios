/*
 * Curious Curios — A Minecraft mod
 * Copyright (C) 2024  Cypheriel
 */

package dev.cypheriel.curious_curios.common.items.curios

import dev.cypheriel.curious_curios.common.creative_tabs.CuriousCuriosTab
import net.minecraft.world.effect.MobEffects
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import top.theillusivec4.curios.api.SlotContext
import top.theillusivec4.curios.api.type.capability.ICurioItem

object WitherRing : Item(Properties().stacksTo(1).tab(CuriousCuriosTab)), ICurioItem {
    override fun onEquip(slotContext: SlotContext?, prevStack: ItemStack?, stack: ItemStack?) {
        val entity = slotContext?.entity ?: return

        entity.removeEffect(MobEffects.WITHER)
    }
}