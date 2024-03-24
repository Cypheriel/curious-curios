/*
 * Curious Curios — A Minecraft mod
 * Copyright (C) 2024  Cypheriel
 */

package dev.cypheriel.curious_curios.common.creative_tabs

import dev.cypheriel.curious_curios.CuriousCurios.MOD_ID
import dev.cypheriel.curious_curios.common.registries.ItemRegistry
import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.item.ItemStack

object CuriousCuriosTab : CreativeModeTab(MOD_ID) {
    override fun makeIcon(): ItemStack {
        return ItemStack(ItemRegistry.glowingIngot.get())
    }
}