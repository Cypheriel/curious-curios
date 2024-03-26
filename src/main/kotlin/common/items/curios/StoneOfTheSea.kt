/*
 * Curious Curios — A Minecraft mod
 * Copyright (C) 2024  Cypheriel
 */

package dev.cypheriel.curious_curios.common.items.curios

import dev.cypheriel.curious_curios.common.creative_tabs.CuriousCuriosTab
import net.minecraft.world.effect.MobEffects
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.enchantment.EnchantmentHelper
import net.minecraft.world.phys.Vec3
import top.theillusivec4.curios.api.SlotContext
import top.theillusivec4.curios.api.type.capability.ICurioItem

object StoneOfTheSea : Item(Properties().stacksTo(1).tab(CuriousCuriosTab)), ICurioItem {
    override fun curioTick(
        slotContext: SlotContext?,
        stack: ItemStack?,
    ) {
        val player = slotContext?.entity as? Player ?: return

        if (!player.isUnderWater && player.airSupply < player.maxAirSupply) {
            player.airSupply = (player.airSupply + 16).coerceIn(-20, player.maxAirSupply)
        }

        if (!player.isInWaterOrBubble || !player.isSwimming) {
            return
        }

        if (player.deltaMovement.length() > 0.20f) {
            player.airSupply = (player.airSupply + 2).coerceIn(-20, player.maxAirSupply)
        }

        player.deltaMovement = calculateMovement(player)
    }

    private fun calculateMovement(player: Player): Vec3 {
        // TODO: configurability for basically every constant value in this function

        val maxHorizontalVelocityMultiplier = 0.75
        val maxVerticalVelocityMultiplier = 0.75

        val depthStriderLevel = EnchantmentHelper.getDepthStrider(player).coerceIn(0, 3)
        val depthStriderValue = 0.25
        val depthStriderMultiplier = depthStriderLevel * depthStriderValue

        var horizontalVelocityMultiplier = 0.5 + depthStriderMultiplier
        var verticalVelocityMultiplier = 0.5 + depthStriderMultiplier

        if (player.hasEffect(MobEffects.DOLPHINS_GRACE)) {
            horizontalVelocityMultiplier += 0.5
            verticalVelocityMultiplier += 0.5
        }

        horizontalVelocityMultiplier *= maxHorizontalVelocityMultiplier
        verticalVelocityMultiplier *= maxVerticalVelocityMultiplier

        return Vec3(
            player.lookAngle.x * horizontalVelocityMultiplier,
            player.lookAngle.y * verticalVelocityMultiplier,
            player.lookAngle.z * horizontalVelocityMultiplier,
        )
    }
}
