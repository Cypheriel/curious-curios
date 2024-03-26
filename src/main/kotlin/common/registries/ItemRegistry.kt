/*
 * Curious Curios — A Minecraft mod
 * Copyright (C) 2024  Cypheriel
 */

package dev.cypheriel.curious_curios.common.registries

import dev.cypheriel.curious_curios.CuriousCurios
import dev.cypheriel.curious_curios.common.creative_tabs.CuriousCuriosTab
import dev.cypheriel.curious_curios.common.items.curios.RingOfEnchantedEyes
import dev.cypheriel.curious_curios.common.items.curios.StoneOfInertiaNull
import dev.cypheriel.curious_curios.common.items.curios.StoneOfTheSea
import net.minecraft.world.item.Item
import net.minecraftforge.eventbus.api.IEventBus
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries
import net.minecraftforge.registries.RegistryObject

@Suppress("unused")
object ItemRegistry {
    private val items: DeferredRegister<Item> = DeferredRegister.create(ForgeRegistries.ITEMS, CuriousCurios.MOD_ID)

    fun register(bus: IEventBus) {
        items.register(bus)
    }

    val glowingIngot: RegistryObject<Item> = items.register("glowing_ingot") {
        Item(Item.Properties().tab(CuriousCuriosTab))
    }

    val glowingNugget: RegistryObject<Item> =
        items.register("glowing_nugget") {
            Item(Item.Properties().tab(CuriousCuriosTab))
        }

    val glowingPowder: RegistryObject<Item> =
        items.register("glowing_powder") {
            Item(Item.Properties().tab(CuriousCuriosTab))
        }
    val glowingGem: RegistryObject<Item> =
        items.register("glowing_gem") {
            Item(Item.Properties().tab(CuriousCuriosTab))
        }

    // -- Curios --
    var ringOfEnchantedEyes: RegistryObject<Item> = items.register("ring_of_enchanted_eyes") { RingOfEnchantedEyes }
    val stoneOfInertialNull: RegistryObject<Item> = items.register("stone_of_inertia_null") { StoneOfInertiaNull }
    val stoneOfTheSea: RegistryObject<Item> = items.register("stone_of_the_sea") { StoneOfTheSea }
}
