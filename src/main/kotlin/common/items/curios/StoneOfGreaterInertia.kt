/*
 * Curious Curios — A Minecraft mod
 * Copyright (C) 2024  Cypheriel
 */

package dev.cypheriel.curious_curios.common.items.curios

import dev.cypheriel.curious_curios.common.creative_tabs.CuriousCuriosTab
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.phys.Vec3
import top.theillusivec4.curios.api.SlotContext
import top.theillusivec4.curios.api.type.capability.ICurioItem

object StoneOfGreaterInertia : Item(Properties().stacksTo(1).tab(CuriousCuriosTab)), ICurioItem {
    override fun curioTick(
        slotContext: SlotContext?,
        stack: ItemStack?,
    ) {
        val player = slotContext?.entity as? Player ?: return

        if (player.isSwimming) {
            return
        }

        if (player.tickCount % 4 != 0) {
            return
        }

        player.deltaMovement = calculateMovement(player)
        player.fallDistance -= 0.25f
    }

    private fun calculateMovement(player: Player): Vec3 {
        var horizontalVelocityMultiplier = 1.35
        var verticalVelocityMultiplier = 1.50
        var limit = 2.0

        if (player.isFallFlying) {
            verticalVelocityMultiplier = 1.0
            horizontalVelocityMultiplier = 1.01
            limit = 1.25
        }

        return player.deltaMovement.multiply(
            horizontalVelocityMultiplier.coerceIn(-limit, limit),
            verticalVelocityMultiplier.coerceIn(-limit * 1.5, limit),
            horizontalVelocityMultiplier.coerceIn(-limit, limit),
        )
    }
}
